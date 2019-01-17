package com.zip.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zip.action.BaseAction;
import com.zip.util.JsoupUtil;
import com.zip.util.SysUtil;

/**
 * spring全局拦截器，主要验证参数是否合法，
 * @ClassName: OverallInterceptor
 * @Description: spring全局拦截器
 * @author sunsk
 * @date 2012-12-26 下午1:21:17
 *
 */
public class GlobalDefaultInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(GlobalDefaultInterceptor.class);
	
	/**
	 * 需要验证token的action
	 */
	private List<String> tokenActionList = new ArrayList<>();
	
	public List<String> getTokenActionList() {
		return tokenActionList;
	}

	public void setTokenActionList(List<String> tokenActionList) {
		this.tokenActionList = tokenActionList;
	}

	/**
	 * 返回true通过，false不通过
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// 获取访问路径
		String uri = request.getRequestURI();
		// 去掉项目名称，获取到最终的action名称
		if (!request.getContextPath().equals("/")) {
			uri = Optional.of(uri).map(u -> u.substring(request.getContextPath().length(), u.length())).get();
			uri = Optional.of(uri).filter(u -> u.endsWith("/")).map(u -> u.substring(0, u.length() - 1)).orElse(uri);
		}
		log.debug("全局拦截器，处理uri：" + uri);
		
		// 清理XSS代码等操作
		JsoupUtil.requestSafeFilter(BaseAction.getParameterMapBeforeClear(request), request);
		log.debug("过滤xss字符");
		
		// 获取请求方式
		int type = 0;
		if (SysUtil.isNull(request.getHeader("x-requested-with")) || 
				!request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
			type = 2;
		} else {
			type = 1;
		}
		log.debug("请求方式：" + type + "，x-requested-with：" + request.getHeader("x-requested-with"));
		
		// 判断token
//		if (tokenActionList.contains(uri.trim())) {
//			log.debug("拦截器处理token信息：" + uri + "，返回模式：" + type);
//			TokenUtil tokenUtil = new TokenUtil();
//			tokenUtil.setRedisSession(redisSession);
//			if (!tokenUtil.checkToken(request)) {
//				switch (type) {
//					case 1:		// ajax返回token错误信息
//						JSONObject json = new JSONObject();
//						json.put("code", SysUtil.FAILURE);
//						json.put("msg", "token值错误，本页面不能重复提交");
//						log.error("token错误的url：" + uri + " & json返回值：" + json.toString() + " & 参数值：" + param.toString());
//						BaseAction.writeJson(json, response);
//						
//						break;
//					case 2:		// token抛异常
//					default:
//						Exception e = new Exception("token值错误，本页面不能重复提交");
//						log.error("token错误的url：" + uri + " & 参数值：" + param.toString(), e);
//						throw e;
//				}
//				return false;
//			}
//		}
		return true;
	}
}
