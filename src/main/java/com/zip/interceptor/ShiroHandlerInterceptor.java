package com.zip.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zip.component.InitParams;
import com.zip.util.JsonUtil;
import com.zip.util.PermissionUtil;
import com.zip.util.SysUtil;

/**
 * shiro验证权限的拦截器
 * @title ShiroHandlerInterceptor.java
 * @author ssk
 * @date 2017年12月18日
 * @description 
 *
 */
public class ShiroHandlerInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(ShiroHandlerInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		// 先获取请求的url
		String uri = request.getRequestURI();
		// 去掉项目名称，获取到最终的action名称
		if (!request.getContextPath().equals("/")) {
			uri = Optional.of(uri).map(u -> u.substring(request.getContextPath().length(), u.length())).get();
			uri = Optional.of(uri).filter(u -> u.endsWith("/")).map(u -> u.substring(0, u.length() - 1)).orElse(uri);
		}
		log.debug("Shiro拦截器，处理uri：" + uri);
		
		// 获取请求方式
		int type = 0;
		if (SysUtil.isNull(request.getHeader("x-requested-with")) || 
				!request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
			type = 2;
		} else {
			type = 1;
		}
		
		Subject currentUser = SecurityUtils.getSubject();
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) currentUser.getSession().getAttribute(SysUtil.LOGIN_SESSION);
		if (SysUtil.isNull(user)) {
			// 用户信息不存在则退出，并清空其他session
			if (currentUser.isAuthenticated()) {
				currentUser.logout();
				currentUser.getSession().setTimeout(-1);
			}
			banAccess("未登录或session已过期，请重新登录", type, response);
			return false;
		}
		
		// 不是超级管理员
		if (!currentUser.hasRole("admin")) {
			// 获取当前权限名称
			String permiss = PermissionUtil.getPermissionByUrl(uri);
			// 没有找到权限但是已经登录
			// 一些页面不需要权限，只要登录后即可访问
			if (SysUtil.isNull(permiss) && currentUser.isAuthenticated()) {
				return true;
			}
			// 没有权限
			if (!currentUser.isPermitted(permiss)) {
				banAccess("没有权限", type, response, "/common/nopower.jsp");
				return false;
			}
		}
		return true;
	}

	/**
	 * 禁止访问
	 * json则范围msg，url则跳转到登录页面
	 * @param msg 禁止的明细
	 * @param type 访问类型
	 * @throws IOException 
	 */
	public void banAccess(String errMsg, int type, HttpServletResponse response) throws IOException {
		banAccess(errMsg, type, response, "/sys/index.shtml");
	}
	
	/**
	 * 禁止访问
	 * json则范围msg，url则跳转到指定页面
	 * @param errMsg
	 * @param type
	 * @param response
	 * @param url
	 * @throws IOException
	 */
	public void banAccess(String errMsg, int type, HttpServletResponse response, String url) throws IOException {
		String basePath = (SysUtil.isNull(InitParams.CONTEXTPATH) || InitParams.CONTEXTPATH.equals("/")) ? "" : InitParams.CONTEXTPATH;
		log.debug(errMsg);
		switch (type) {
		case 1:		// ajax返回session过期
			JsonUtil.writeJson(response, JsonUtil.getFailJson(errMsg));
			break;
		case 2:		// 重定向到登录页面
		default:
			response.sendRedirect(basePath + url);
			break;
		}
	}
}
