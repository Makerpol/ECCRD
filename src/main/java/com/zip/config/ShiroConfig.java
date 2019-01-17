package com.zip.config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zip.component.ShiroRealm;

@Configuration
@Order(5)
public class ShiroConfig {
	
	/**
	 * 生命周期
	 * @return
	 */
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean
	public EnterpriseCacheSessionDAO sessionDAO(EhCacheManager cacheManager) {
		EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterpriseCacheSessionDAO.setCacheManager(cacheManager);
		enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		enterpriseCacheSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
		return enterpriseCacheSessionDAO;
	}

	public EhCacheManagerFactoryBean ehCacheManager() {
		EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
		cacheManager.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:ehcache.xml"));
		return cacheManager;
	}
	
	@Bean
	public EhCacheManager shiroCacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManager(ehCacheManager().getObject());
		return cacheManager;
	}
	
	@Bean
	public DefaultSessionManager defaultSessionManager(EhCacheManager cacheManager) {
		DefaultWebSessionManager manager = new DefaultWebSessionManager();
		manager.setGlobalSessionTimeout(30 * 60 * 1000);
		manager.setSessionDAO(sessionDAO(cacheManager));
		manager.setCacheManager(shiroCacheManager());
		manager.setSessionIdCookie(simpleCookie());
		manager.setSessionValidationSchedulerEnabled(true);
		return manager;
	}
	
	/**
	 * cookie保存sessionID
	 * @return
	 */
	@Bean
	public SimpleCookie simpleCookie() {
		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setName("JSESSID");
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(60 * 60 * 24 * 3);
		return simpleCookie;
	}
	
	/**
	 * 验证模块
	 * @return
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(EhCacheManager cacheManager) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(new ShiroRealm());
		defaultWebSecurityManager.setSessionManager(defaultSessionManager(cacheManager));
		defaultWebSecurityManager.setCacheManager(shiroCacheManager());
		return defaultWebSecurityManager;
	}
	
	/**
	 * 过滤器
	 * @return
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(EhCacheManager cacheManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager(cacheManager));
		// 登录页面
		shiroFilterFactoryBean.setLoginUrl("/sys/index.shtml");
		// 认证失败
//		shiroFilterFactoryBean.setUnauthorizedUrl("/error");
		// 过滤掉空字符串，将正常的url用\n拼接成新的字符串，交给springmvc拦截器实现
//		List<String> urlList = Arrays.asList("/sys/index.do = anon", 
//				"/sys/login.do = anon", 
//				"/sys/logout.do = anon", 
//				"/sys/main.do = anon", 
//				// 静态内容
////				"/common/** = anon", 
////				"/easyui/** = anon", 
////				"/static/** = anon", 
////				"/upload/** = anon", 
//				// Restfull接口
//				"/rest/Hc/** = anon", 
//				"/** = authc");
//		shiroFilterFactoryBean.setFilterChainDefinitions(urlList.stream().filter(url -> !SysUtil.isNull(url)).
//				map(url -> url.trim()).collect(Collectors.joining("\n")).trim());
		return shiroFilterFactoryBean;
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setArguments(securityManager);
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		return methodInvokingFactoryBean;
	}
}
