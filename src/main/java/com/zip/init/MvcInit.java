package com.zip.init;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.IntrospectorCleanupListener;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import com.zip.component.InitParams;
import com.zip.service.DictInfoService;
import com.zip.service.PermissionInfoService;
import com.zip.util.ClassUtil;
import com.zip.util.DictUtil;
import com.zip.util.PermissionUtil;
import com.zip.util.SysUtil;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;

/**
 * 
 * @title MvcInit.java
 * @author ssk
 * @date 2017年12月5日
 * @description spring启动类，添加过滤器和Servlet
 *
 */
public class MvcInit implements WebApplicationInitializer {
	
	private static Logger log = LoggerFactory.getLogger(MvcInit.class);

	@SuppressWarnings("static-access")
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// 先启动spring容器
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setServletContext(servletContext);
		
		// 注册配置类
		context.register(ClassUtil.scanClass("com.**.config", true, Configuration.class, Configurable.class));
		log.debug("加载排序后的各配置类");
		
		// 初始化SysUtil的context
		SysUtil.setContext(context);
		
		// 等spring初始化结束后
		context.afterPropertiesSet();
		
		InitParams initParams = context.getBean(InitParams.class);

		// 添加应用名称
		servletContext.setInitParameter("webAppRootKey", initParams.CONTEXTPATH);
		// 添加logback文件路径配置
		servletContext.setInitParameter("logbackConfigLocation", "classpath:logback.xml");
		log.debug("添加系统变量，logback配置文件");
		
		// 添加logback的监听
		servletContext.addListener(LogbackConfigListener.class);
		// 防止内存泄漏
		servletContext.addListener(IntrospectorCleanupListener.class);
		log.debug("添加系统监听");
		
		EnumSet<DispatcherType> shiroTypes = EnumSet.allOf(DispatcherType.class);
		shiroTypes.add(DispatcherType.REQUEST);
		shiroTypes.add(DispatcherType.FORWARD);
		// Servlet支持异步的话，filter也必须配置异步
		shiroTypes.add(DispatcherType.ASYNC);

		// 添加字符过滤Filter，设置为异步
		FilterRegistration.Dynamic charFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		charFilter.setInitParameter("encoding", "UTF-8");
		charFilter.setInitParameter("forceEncoding", "true");
		charFilter.setAsyncSupported(true);
		charFilter.addMappingForUrlPatterns(shiroTypes, true, "/*");
		
		// urlrewrite
		FilterRegistration.Dynamic urlrewriteFilter = servletContext.addFilter("UrlRewriteFilter", UrlRewriteFilter.class);
		urlrewriteFilter.setInitParameter("confPath", "/WEB-INF/urlrewrite.xml");
		urlrewriteFilter.setAsyncSupported(true);
		urlrewriteFilter.addMappingForUrlPatterns(shiroTypes, true, "*.shtml");
		
		// 配置shiro，与shiro内部的ShiroFilterFactoryBean名称一致
		FilterRegistration.Dynamic shiroFilter = servletContext.addFilter("shiroFilter", DelegatingFilterProxy.class);
		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		shiroFilter.setInitParameter("targetFilterLifecycle", "true");
		shiroFilter.setInitParameter("targetBeanName", "shiroFilter");
		shiroFilter.setAsyncSupported(true);
		shiroFilter.addMappingForUrlPatterns(shiroTypes, true, "*.do");

		// 配置spring的servlet
		ServletRegistration.Dynamic springServlet = servletContext.addServlet("springmvc", new DispatcherServlet(context));
		springServlet.addMapping("*.do");
		springServlet.setAsyncSupported(true);
		springServlet.setLoadOnStartup(1);
		log.debug("初始化Filter和Servlet");
		
		// 初始化字典
		DictInfoService dictInfoService = context.getBean(DictInfoService.class);
		List<Map<String, Object>> dictList = dictInfoService.selectDictInfo();
		DictUtil.initDictMap(dictList);
		log.info("初始化字典信息");
		
		// 初始化权限信息
		PermissionInfoService permissionInfoService = context.getBean(PermissionInfoService.class);
		List<Map<String, Object>> perList = permissionInfoService.selectPermission();
		PermissionUtil.initDictMap(perList);
		log.info("初始化权限菜单信息");
	}
}
