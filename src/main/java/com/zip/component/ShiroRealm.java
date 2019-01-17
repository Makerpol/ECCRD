package com.zip.component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.zip.service.PermissionInfoService;
import com.zip.service.RoleInfoService;
import com.zip.service.UserInfoService;
import com.zip.util.SysUtil;

/**
 * 
 * @title ShiroRealm.java
 * @author ssk
 * @date 2017年12月18日
 * @description shiro 自定义realm的认证阶段属于filter，当时的spring bean还没有读取进来。不能被spring自动注入，所以获取各种spring的类，都需要用SysUtil的getBean来实现
 *
 */
public class ShiroRealm extends AuthorizingRealm {
	
	public static final String ROLE_NAME_LIST = "ROLE_NAME_LIST";
	public static final String PER_NAME_LIST = "PER_NAME_LIST";
	public static final String ROLE_ID_LIST = "ROLE_ID_LIST";

	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		Map<String, Object> user = (Map<String, Object>) principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		
		Subject currentUser = SecurityUtils.getSubject();
		// 加载角色列表，和所有角色的ID
		List<String> roleNameList = (List<String>) currentUser.getSession().getAttribute(ROLE_NAME_LIST);
		List<Integer> roleIdList = (List<Integer>) currentUser.getSession().getAttribute(ROLE_ID_LIST);
		if (SysUtil.isNull(roleNameList)) {
			Map<String, String> param = Maps.newHashMap();
			param.put("userName", user.get("USER_NAME").toString());
			
			RoleInfoService roleInfoService = SysUtil.getBean(RoleInfoService.class);
			List<Map<String, Object>> roleList = roleInfoService.selectRoleByName(param);
			roleNameList = roleList.stream().map(role -> role.get("ROLE_NAME").toString()).collect(Collectors.toList());
			currentUser.getSession().setAttribute(ROLE_NAME_LIST, roleNameList);
			
			roleIdList = roleList.stream().map(role -> Ints.tryParse(role.get("ROLE_ID").toString())).collect(Collectors.toList());
			currentUser.getSession().setAttribute(ROLE_ID_LIST, roleIdList);
		}
		simpleAuthorizationInfo.addRoles(roleNameList);
		
		// 加载权限列表
		List<String> perNameList = (List<String>) currentUser.getSession().getAttribute(PER_NAME_LIST);
		if (SysUtil.isNull(perNameList)) {
			if (roleNameList.contains("admin")) {	// 超级管理员不需要获取权限列表
				perNameList = Lists.newArrayList();
			} else {
				PermissionInfoService permissionInfoService = SysUtil.getBean(PermissionInfoService.class);
				List<Map<String, Object>> perList = permissionInfoService.selectPermissionByRoleList(roleIdList);
				perNameList = perList.stream().map(per -> per.get("PER_NAME").toString()).collect(Collectors.toList());
				currentUser.getSession().setAttribute(PER_NAME_LIST, perNameList);
			}
		}
		simpleAuthorizationInfo.addStringPermissions(perNameList);
		
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String name = token.getPrincipal().toString();
		Map<String, String> param = Maps.newHashMap();
		param.put("userName", name);
		UserInfoService userInfoService = SysUtil.getBean(UserInfoService.class);
		Map<String, Object> user = userInfoService.selectUserByName(param);
		if (user == null) {
			throw new UnknownAccountException("用户信息不存在");
		}
		switch (Ints.tryParse(user.get("USER_STATUS").toString())) {
		case 0:
			user.put("SESSIONID", SecurityUtils.getSubject().getSession().getId().toString());
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, 
					user.get("USER_PASS"), user.get("USER_NAME").toString());
			return simpleAuthenticationInfo;
		case 1:
			throw new LockedAccountException("当前账号已锁定");
		default:
			throw new DisabledAccountException("无效用户");
		}
	}

	/**
	 * 清除用户信息
	 * @param principal
	 */
	public void clearCachedAuth(String principal) {
		SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principalCollection);
	}
}
