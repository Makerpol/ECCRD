package com.zip.action;

import static com.zip.util.SysUtil.isNull;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.Subject.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.zip.component.InitParams;
import com.zip.util.SysUtil;

/**
 * 
 * @title BaseAction.java
 * @author ssk
 * @date 2017年12月6日
 * @description action的基础配置，所有的action都需要集成该类
 *
 */
public class BaseAction {
	
	private static Logger log = LoggerFactory.getLogger(BaseAction.class);
	
	protected Base64 base64 = new Base64();
	
	@Autowired protected HttpServletRequest request;
	@Autowired protected HttpServletResponse response;
	
	@Autowired protected ThreadPoolTaskExecutor threadPool;
	
	@Autowired protected InitParams initParams;
	
	@Autowired protected EnterpriseCacheSessionDAO enterpriseCacheSessionDAO;
	
	/**
	 * 进入各个action之前，先进行参数过滤
	 * 根据参数判断设置用户操作日志
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@ModelAttribute
	protected final void init() throws InterruptedException, ExecutionException {
		
	}
	
	/**
	 * 通过request获取所有传递的参数和值
	 * @param request
	 * @return
	 */
	private Map<String, String> getParameterMapByRequest() {
		Map<String, String> param = Maps.newHashMap();
		// 获取request的所有参数
		// 将Enumeration转化为list，用stream进行过滤和循环
		Enumeration<String> enu = request.getAttributeNames();
		Collections.list(enu).stream().filter(key -> !isNull(request.getParameter(key))).forEach(key -> {
			param.put(key, request.getParameter(key));
		});
		log.debug("接收过滤后的参数：" + param.toString());
		return param;
	}
	
	
	
	/**
	 * 通过request中获取所有原始参数和值
	 * @param request
	 * @return
	 */
	public static Map<String, String> getParameterMapBeforeClear(HttpServletRequest request) {
		Map<String, String> param = Maps.newHashMap();
		// 获取request的所有参数
		Enumeration<String> enu = request.getParameterNames();
		// 获取request的所有值
		Collections.list(enu).stream().filter(key -> !isNull(request.getParameter(key))).forEach(key -> {
			param.put(key, request.getParameter(key));
		});
		log.debug("接收原始参数：" + param.toString());
		return param;
	}
	
	/**
	 * 通过request获取转换后的页面参数
	 * @param request
	 * @return
	 */
	protected Map<String, String> getParameterMap() {
		Map<String, String> param = getParameterMapByRequest();
		param.remove("page");
		return param;
	}
	
	/**
	 * 通过request获取转换后的页面参数，包含分页信息
	 * @return
	 */
	protected Map<String, String> getParameterMapWithPageInfo() {
		Map<String, String> param = getParameterMapByRequest();
		// 自动添加分页信息
		param.putAll(getPageInfo(param.get("page"), param.get("rows")));
		// 将所有参数存入request，方便页面调取
		setAttributeAll(param);
		return param;
	}
	
	/**
	 * 将map中的值全部存入到request中
	 * @param param
	 */
	protected void setAttributeAll(Map<String, String> param) {
		param.keySet().stream().forEach(key -> {
			request.setAttribute(key, param.get(key));
		});
	}
	
	/**
	 * 获取分页参数
	 * @param pageStr 当前页码
	 * @param rowsStr 最大行数
	 * @return
	 */
	private Map<String, String> getPageInfo(String pageStr, String rowsStr) {
		Map<String, String> pageMap = Maps.newHashMap();
		Integer page = Optional.ofNullable(pageStr).filter(p -> !isNull(p)).map(Ints::tryParse).filter(p -> p > 0).orElse(1);
		pageMap.put("page", page.toString());
		Integer rows = Optional.ofNullable(rowsStr).filter(p -> !isNull(p)).map(Ints::tryParse).filter(p -> p > 0).orElse(initParams.maxRow);
		pageMap.put("rows", rows.toString());
		return pageMap;
	}
	
	/**
	 * 获取项目当前的绝对路径
	 * @return
	 */
	protected String getRealPath() {
		ServletContext context = request.getServletContext();
		// 取项目的绝对路径
		String path = context.getRealPath("/");
		return path;
	}
	
	/**
	 * 取head信息
	 * x-forwarded-for表示远程请求的IP
	 * 
	 * @return
	 */
	protected Map<String, String> getHeads() {
		Map<String, String> heads = Maps.newHashMap();
		Enumeration<String> enume = request.getHeaderNames();
		Collections.list(enume).stream().forEach(name -> {
			heads.put(name, request.getHeader(name).trim());
		});
		return heads;
	}
	
	/**
	 * 获取项目的基础URL路径
	 * @return
	 */
	public static String getBaseUrl() {
		return InitParams.CONTEXTPATH.equals("/") ? "" : InitParams.CONTEXTPATH;
	}
	
	/**
	 * 获取访问action的URL路径
	 * @return
	 */
	public String getActionUrl() {
		 // 获取访问路径
		String url = request.getRequestURI();
		// 去掉项目名称，获取到最终的action名称
		if (!request.getContextPath().equals("/")) {
			url = Optional.of(url).map(u -> u.substring(request.getContextPath().length(), u.length())).get();
			url = Optional.of(url).filter(u -> u.endsWith("/")).map(u -> u.substring(0, u.length() - 1)).orElse(url);
		}
		return url;
	}
	
	/**
	 * 取request的请求信息
	 * @return
	 */
	protected Map<String, String> getRequestInfo() {
		Map<String, String> map = Maps.newHashMap();
		map.put("AuthType", request.getAuthType());
		map.put("CharacterEncoding", request.getCharacterEncoding());
		map.put("ContentType", request.getContentType());
		map.put("ContextPath", request.getContextPath());
		map.put("LocalAddr", request.getLocalAddr());
		map.put("LocalName", request.getLocalName());
		map.put("Method", request.getMethod());
		map.put("PathInfo", request.getPathInfo());
		map.put("PathTranslated", request.getPathTranslated());
		map.put("Protocol", request.getProtocol());
		map.put("QueryString", request.getQueryString());
		map.put("RemoteAddr", request.getRemoteAddr());
		map.put("RemoteUser", request.getRemoteUser());
		map.put("RequestedSessionId", request.getRequestedSessionId());
		map.put("RequestURI", request.getRequestURI());
		map.put("Scheme", request.getScheme());
		map.put("ServerName", request.getServerName());
		map.put("ServletPath", request.getServletPath());
		map.put("ContentLength", request.getContentLength() + "");
		map.put("LocalPort", request.getLocalPort() + "");
		map.put("RemotePort", request.getRemotePort() + "");
		map.put("ServerPort", request.getServerPort() + "");
		return map;
	}
	
	/**
	 * 获取项目的基础URL路径
	 * 防止Jsoup不能识别域名，过滤掉内容图片的src地址信息，导致图片上传无法保存
	 * @param request
	 * @return
	 */
	public static String getBaseImageUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + 
				(request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + getBaseUrl();
	}
	
	/**
	 * 获取shiro的session
	 * @return
	 */
	protected Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	
	protected Session getSession(String sessionId) {
		return enterpriseCacheSessionDAO
				.getActiveSessions()
				.stream()
				.filter(s -> s.getId().toString().equals(sessionId))
				.findFirst()
				.orElse(null);
	}
	
	/**
	 * 通过sessionId，获取该sessionId对应的已登录的用户数据
	 * 方便操作该用户的session等功能
	 * @param sessionId
	 */
	protected Subject getSubject(String sessionId) {
		if (isNull(sessionId)) {
			return null;
		}
		Builder builder = new Subject.Builder();
		builder.session(getSession(sessionId));
		
		return builder.buildSubject();
	}
	
	protected Subject getSubject(Session session) {
		if (isNull(session)) {
			return null;
		}
		Builder builder = new Subject.Builder();
		builder.session(session);
		
		return builder.buildSubject();
	}
	
	/**
	 * 获取所有的cookie
	 * @return
	 */
	protected Cookie[] getCookies() {
		return request.getCookies();
	}
	
	/**
	 * 获取spring的ApplicationContext
	 * @return
	 */
	protected ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
	}
	
	/**
	 * 通过用户ID获取用户的登录信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<Session> getLoginSessionByUserId(int userId) {
		List<Session> list = getAllLoginSession();
		if (SysUtil.isNull(list)) {
			return null;
		}
		List<Map<String, Object>> userList = list.stream()
			.map(s -> (Map<String, Object>) s.getAttribute(SysUtil.LOGIN_SESSION))
			.filter(s -> {
				if (SysUtil.isNull(s)) {
					return false;
				}
				return Ints.tryParse(s.get("USER_ID").toString()) == userId;
			})
			.collect(Collectors.toList());
		if (SysUtil.isNull(userList)) {
			return null;
		}
		return userList.stream().map(s -> getSession(s.get("SESSIONID").toString())).collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getLoginUserByUserId(int userId) {
		List<Session> list = getAllLoginSession();
		if (SysUtil.isNull(list)) {
			return null;
		}
		return list.stream()
			.map(s -> (Map<String, Object>) s.getAttribute(SysUtil.LOGIN_SESSION))
			.filter(s -> {
				if (SysUtil.isNull(s)) {
					return false;
				}
				return Ints.tryParse(s.get("USER_ID").toString()) == userId;
			}).findFirst().orElse(null);
	}
	
	/**
	 * 获取所有已登录的用户
	 * @return
	 */
	protected List<Session> getAllLoginSession() {
		return enterpriseCacheSessionDAO.getActiveSessions().stream().filter(s -> !SysUtil.isNull(s.getAttribute(SysUtil.LOGIN_SESSION))).collect(Collectors.toList());
	}
	
	/**
	 * 获取在线人数
	 * @return
	 */
	protected long getLoginSessionSize() {
		return enterpriseCacheSessionDAO.getActiveSessions().stream().filter(s -> !SysUtil.isNull(s.getAttribute(SysUtil.LOGIN_SESSION))).count();
	}

}
