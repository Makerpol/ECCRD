package com.zip.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import com.zip.interceptor.GlobalDefaultInterceptor;
import com.zip.interceptor.ShiroHandlerInterceptor;
import com.zip.util.SysUtil;

/**
 * 
 * @title MvcConfig.java
 * @author ssk
 * @date 2017年12月5日
 * @description Servlet3.0配置类
 */
@Order(1)
@Configuration
@EnableWebMvc
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = {"com.**.action", "com.**.dao", "com.**.service", "com.**.component", "com.**.scheduling"})
//@ComponentScans({@ComponentScan(basePackages = "com.**.action"), @ComponentScan(basePackages = "com.**.dao"), 
//	@ComponentScan(basePackages = "com.**.service"), @ComponentScan(basePackages = "com.**.scheduling"), 
//	@ComponentScan(basePackages = "com.**.component"), @ComponentScan(basePackages = "com.**.inf")})
public class MvcConfig implements WebMvcConfigurer {

	/**
	 * 如果你想使用@Autowired注解，那么就必须事先在 Spring 容器中声明 AutowiredAnnotationBeanPostProcessor Bean
	 * @return
	 */
	@Bean
	public AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
		AutowiredAnnotationBeanPostProcessor annotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
		annotationBeanPostProcessor.setAutowiredAnnotationType(Autowired.class);
		return annotationBeanPostProcessor;
	}
	
	/**
	 * 如果想使用@Required 注解就必须声明RequiredAnnotationBeanPostProcessor
	 * @return
	 */
	@Bean
	public RequiredAnnotationBeanPostProcessor requiredAnnotationBeanPostProcessor() {
		RequiredAnnotationBeanPostProcessor requiredAnnotationBeanPostProcessor = new RequiredAnnotationBeanPostProcessor();
		requiredAnnotationBeanPostProcessor.setRequiredAnnotationType(Required.class);
		return requiredAnnotationBeanPostProcessor;
	}
	
	/**
	 * 如果想使用@ Resource 、@ PostConstruct、@ PreDestroy等注解就必须声明CommonAnnotationBeanPostProcessor
	 * @return
	 */
	@Bean
	public CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor() {
		CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor = new CommonAnnotationBeanPostProcessor();
		commonAnnotationBeanPostProcessor.setDestroyAnnotationType(PreDestroy.class);
		commonAnnotationBeanPostProcessor.setInitAnnotationType(PostConstruct.class);
		return commonAnnotationBeanPostProcessor;
	}
	
	/**
	 * 加载自定义配置文件
	 * @return
	 * @throws IOException
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertiesFactoryBean() throws IOException {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		propertySourcesPlaceholderConfigurer.setLocations(resolver.getResources("classpath:*.properties"));
		return propertySourcesPlaceholderConfigurer;
	}

	/**
	 * restfull返回值的格式转换
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 字符串处理方法
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		stringHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.TEXT_XML));
		stringHttpMessageConverter.setDefaultCharset(Charset.forName(SysUtil.ENCODE));
		converters.add(stringHttpMessageConverter);
		
		// json处理方法
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		fastJsonHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON));
		stringHttpMessageConverter.setDefaultCharset(Charset.forName(SysUtil.ENCODE));
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName(SysUtil.ENCODE));
		fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty);
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//		fastJsonHttpMessageConverter.setFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.QuoteFieldNames);
		converters.add(fastJsonHttpMessageConverter);
	}
	
	/**
	 * mvc页面路径
	 * 
	 * @return
	 */
	@Bean(name = "internalResourceViewResolver")
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/pages");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}

	/**
	 * 线程池
	 * 
	 * @return
	 */
	@Bean(destroyMethod = "shutdown", initMethod="initialize")
	public ThreadPoolTaskExecutor threadPool() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		// 最大
		threadPoolTaskExecutor.setMaxPoolSize(100);
		// 默认
		threadPoolTaskExecutor.setCorePoolSize(10);
		// 线程池维护线程所允许的空闲时间
		threadPoolTaskExecutor.setKeepAliveSeconds(3000);
		// 队列最大长度
		threadPoolTaskExecutor.setQueueCapacity(200);
		threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return threadPoolTaskExecutor;
	}

	/**
	 * 拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		// 全局拦截器
		registry.addInterceptor(new GlobalDefaultInterceptor()).addPathPatterns("/**");
		String url[] = null;
		try {
			url = Files.lines(resourcePatternResolver.getResource("classpath:loginUrl.txt").getFile().toPath(), StandardCharsets.UTF_8)
					.filter(s -> !SysUtil.isNull(s))
					.toArray(String[]::new);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// 权限拦截器，自动权限验证，不拦截登录相关和Restfull接口
		registry.addInterceptor(new ShiroHandlerInterceptor()).addPathPatterns(url)
			.excludePathPatterns("/sys/index.do", 
					"/sys/login.do", 
//					"/sys/main.do", 
					"/sys/logout.do");
	}

	/**
	 * jsp路径跳转
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
		// 顶部页面
//		registry.addViewController("/sys/top.do").setViewName("/top.jsp");
//		registry.addViewController("/sys/mainIndex.do").setViewName("/mainIndex.jsp");
		// registry.addViewController("/toUpload").setViewName("/upload.jsp");
		// registry.addViewController("/converter").setViewName("/converter.jsp");
		// registry.addViewController("/sse").setViewName("/sse.jsp");
		// registry.addViewController("/async").setViewName("/async.jsp");
	}
	
	/**
	 * 异常处理
	 */
//	@Override
//	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//		// TODO Auto-generated method stub
//		SimpleMappingExceptionResolver defaultResolver = new SimpleMappingExceptionResolver();
//		// 错误跳转页面
//		Properties prepMapping = new Properties();
//		// 默认错误跳转页面
//		prepMapping.put("defaultErrorView", "/common/error.jsp");
//		// 没有权限
//		prepMapping.put("UnauthorizedException", "/common/unauthorized.jsp");
//		// 授权异常
//		prepMapping.put("UnauthenticatedException", "forward:/sys/index.shtml");
//		defaultResolver.setExceptionMappings(prepMapping);
//		// 错误码
////		Properties prepStatus = new Properties();
////		
////		defaultResolver.setStatusCodes(prepStatus);
//		resolvers.add(defaultResolver);
//		WebMvcConfigurer.super.extendHandlerExceptionResolvers(resolvers);
//	}

}
