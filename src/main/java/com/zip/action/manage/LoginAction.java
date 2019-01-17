package com.zip.action.manage;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.zip.action.BaseAction;
import com.zip.service.UserInfoService;
import com.zip.util.JsonUtil;
import com.zip.util.MD5Util;
import com.zip.util.PermissionUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("sys")
public class LoginAction extends BaseAction {
	
	@Autowired private UserInfoService userInfoService;

	@RequestMapping("index")
	public String index() {
		Subject currentUser = SecurityUtils.getSubject();
		// 已登录，则直接跳转到主页面
		if (currentUser.isAuthenticated() && !SysUtil.isNull(getSession()) && !SysUtil.isNull(getSession().getAttribute(SysUtil.LOGIN_SESSION))) {
			return "redirect:/sys/main.shtml";
		}
		return "/manage/index.jsp";
	}
	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login() {
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 没有登录过，则直接跳转到主页面
			Map<String, String> param = getParameterMap();
			if (SysUtil.isNull(param.get("userName")) || SysUtil.isNull(param.get("pass"))) {
				request.setAttribute("errorInfo", "用户名密码均不能为空");
				return "/manage/index.jsp";
			}
			// 再用shiro处理登录信息
			UsernamePasswordToken token = new UsernamePasswordToken(param.get("userName"), 
					MD5Util.md5(param.get("pass")));
			currentUser.login(token);
			@SuppressWarnings("unchecked")
			Map<String, Object> user = (Map<String, Object>) currentUser.getPrincipal();
			user.put("CURRENT_TIME", SysUtil.getCurrentTime(1));
			param.put("userId", user.get("USER_ID").toString());
			userInfoService.updateUserLastLoginTime(param);
			// 已经存在登录信息，表示用户在其他地方登陆中，则顶替掉旧session
			List<Session> oldSession = getLoginSessionByUserId(Ints.tryParse(user.get("USER_ID").toString()));
			Optional.ofNullable(oldSession).orElse(Lists.newArrayList()).stream().filter(s -> !SysUtil.isNull(s)).forEach(s -> {
				getSubject(s).logout();
				enterpriseCacheSessionDAO.delete(s);
			});
			
			getSession().setAttribute(SysUtil.LOGIN_SESSION, user);
			return "redirect:/sys/main.shtml";
		} catch (UnknownAccountException e) {
			// 账号不存在
			request.setAttribute("errorInfo", "该账号不存在");
			return "/manage/index.jsp";
		} catch (LockedAccountException e) {
			// 账号被禁用
			request.setAttribute("errorInfo", "该账号被禁用，请联系管理员");
			return "/manage/index.jsp";
		} catch (IncorrectCredentialsException e) {
			// 密码错误
			request.setAttribute("errorInfo", "密码错误");
			return "/manage/index.jsp";
		}
	}
	
	/**
	 * 登陆后的主页面
	 * 改页面不需要权限，登陆后即可访问
	 * @return
	 */
	@RequestMapping("main")
	public String main() {
		Subject currentUser = SecurityUtils.getSubject();
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) currentUser.getSession().getAttribute(SysUtil.LOGIN_SESSION);
		if (SysUtil.isNull(user)) {
			return logout();
		}
		request.setAttribute("menus", PermissionUtil.getAllPermission());
		return "/manage/main.jsp";
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping("logout")
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		enterpriseCacheSessionDAO.delete(currentUser.getSession());
		request.setAttribute("errorInfo", "退出登录");
		return "/manage/index.jsp";
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("welcome")
	public String welcome() {
		return "/manage/welcome.jsp";
	}
	
	@RequestMapping("onlineSize")
	public @ResponseBody Callable<JSONObject> onlineSize() {
		return () -> {
			JSONObject json = JsonUtil.getSucJson();
			json.put("size", getLoginSessionSize());
			return json;
		};
	}
}
