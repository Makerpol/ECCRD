package com.zip.util;

import java.net.URLEncoder;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zip.action.BaseAction;

public class JsoupUtil {

	private static Logger log = LoggerFactory.getLogger(JsoupUtil.class);
	
	/**
	 * 自定义规则
	 */
	private final static Whitelist user_content_filter = Whitelist.relaxed();
	
	private Document document = null;
	
	static {
		log.debug("初始化自定义过滤规则");
		// 受信任的标签，以及属性，防止jsoup深度过滤，去掉必需的标签及属性
		user_content_filter.addTags("embed", "object", "param", "span", "div", "label", "hr");
		user_content_filter.addAttributes(":all", "style", "class", "id", "name", "label");
		user_content_filter.addAttributes("object", "width", "height", "classid", "codebase");
		user_content_filter.addAttributes("param", "name", "value");
		user_content_filter.addAttributes("embed", "src", "quality", "width", "height", "allowFullScreen", "allowScriptAccess", "flashvars", "name", "type", "pluginspage");
	}

	
	/**
	 * 过滤request参数中的html不安全代码
	 */
	public static void requestSafeFilter(Map<String, String> param, HttpServletRequest request) {
		// 网站基础访问路径
		String baseUrl = BaseAction.getBaseImageUrl(request);
		log.debug("转换request参数中，不安全的字符，baseUrl：" + baseUrl);
		param.keySet().forEach(key -> {
			String val = Optional.of(param.get(key)).map(k -> Jsoup.clean(k, baseUrl, user_content_filter)).get();
			log.debug("param(" + key + ")，转换前：" + param.get(key) + "  <===>  转换后：" + val);
			request.setAttribute(key, val);
		});
	}
	
	/**
	 * 去除字符串中的html代码
	 * @param html
	 * @return
	 */
	public static String cleanHtmlTag(String html) {
		String val = Optional.ofNullable(html).map(h -> Jsoup.clean(html, Whitelist.none())).orElse("");
		log.debug("原文：" + html + "  <===>  转换后：" + val);
		return val;
	}
	
	/**
	 * 获取远程html
	 * @param url
	 */
	public JsoupUtil(String url) {
		try {
			document = Jsoup.connect(url).get();
			Elements elements = document.getElementsByAttribute("href");
			elements.iterator().forEachRemaining(element -> {
				element.removeAttr("target");
			});
		} catch (Exception e) {
			// TODO: handle exception
			log.error("初始化JsoupUtil出错", e);
		}
	}
	
	public static void main(String[] args) throws Exception {
//		String unsafe = "<hr><p style=\"display:none;\" data-background=\"background-repeat:no-repeat; background-position:center center; background-color:#8DB3E2;\">" +
//				"<br/><sup>sdfasdfsadfsdf</sup>" +
//				"<span style=\"border: 1px solid rgb(0, 0, 0);\">asdfasdfasdfasdfsadfasdf</span>" +
//				"<b></b><h1></h1><i></i><strong></strong><table><tr><td>1</td></tr></table>" +
//				"<img src='/ceshi/upload/img/78PqK6E.jpg' alt='' onerror=\"stealCookies()\" onload='javascript:alert(123);' />" +
//				"<p><a href='/ceshi/upload/img/78PqK6E.jpg' onclick='stealCookies()' onfocus='javascript:alert(1)'>Link</a>" +
//				"<object></object>" +
//				"<script>alert(1);</script>" +
//				"</p><div onload='stealCookies()'>div</div><span>span</span>";
//		System.out.println(Jsoup.clean(unsafe, "http://localhost/ceshi", user_content_filter));
		System.out.println(URLEncoder.encode("http://tianqi.2345.com/plugin/widget/index.htm?s=1&z=1&t=1&v=0&d=1&bd=0&k=&f=&q=1&e=0&a=1&c=57083&w=255&h=98&align=left", "utf-8"));
	}
}
