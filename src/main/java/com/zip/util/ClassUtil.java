package com.zip.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @title ClassUtil.java
 * @author ssk
 * @date 2017年12月11日
 * @description 为了自动加载所有的配置类
 *
 */
public class ClassUtil {
	
	private static final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	
	/**
	 * 通过字符串扫描类
	 * @param packagePatten 包名，支持通配符
	 * @return
	 * @throws Exception
	 */
	public static Class<?>[] scanClass(String packagePatten) {
		try {
			Resource resources[] = resourcePatternResolver.getResources("classpath:" + packagePatten.replace(".", File.separator) + "/*.class");
			List<String> classNameList = Stream.of(resources).map(str -> getFilePath(str)).map(str -> getClassName(getBasePath(), str)).collect(Collectors.toList());
			List<Class<?>> list = classNameList.stream().map(cls -> getClassOfName(cls)).collect(Collectors.toSet()).stream().collect(Collectors.toList());
			Class<?>[] cls = new Class[list.size()];
			IntStream.range(0, list.size()).forEach(i -> cls[i] = list.get(i));
			return cls;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/**
	 * 通过字符串扫描类，并选择包含该注解的类
	 * @param packagePatten 包名，支持通配符
	 * @param annoCls 注解
	 * @return
	 */
	public static Class<?>[] scanClass(String packagePatten, Class<?>... annoCls) {
		Class<?>[] tempCls = scanClass(packagePatten);
		List<Class<?>> list = Stream.of(tempCls).filter(cls -> containAnno(cls, annoCls)).collect(Collectors.toList());
		Class<?>[] cls = new Class[list.size()];
		IntStream.range(0, list.size()).forEach(i -> cls[i] = list.get(i));
		return cls;
	}
	
	/**
	 * 排序
	 * @param packagePatten 包名
	 * @param sort 正反序
	 * @param annoCls 注解
	 * @return
	 */
	public static Class<?>[] scanClass(String packagePatten, boolean sort, Class<?>... annoCls) {
		Class<?>[] tempCls = scanClass(packagePatten, annoCls);
		List<Class<?>> list = Arrays.asList(tempCls);
		list.sort((c1, c2) -> (sort ? getOrderNum(c1) - getOrderNum(c2) : getOrderNum(c2) - getOrderNum(c1)));
		Class<?>[] cls = new Class[list.size()];
		IntStream.range(0, list.size()).forEach(i -> cls[i] = list.get(i));
		return cls;
	}
	
	/**
	 * 排序
	 * @param packagePatten 包名
	 * @param sort 正反序
	 * @return
	 */
	public static Class<?>[] scanClass(String packagePatten, boolean sort) {
		Class<?>[] tempCls = scanClass(packagePatten);
		List<Class<?>> list = Arrays.asList(tempCls);
		list.sort((c1, c2) -> (sort ? getOrderNum(c1) - getOrderNum(c2) : getOrderNum(c2) - getOrderNum(c1)));
		Class<?>[] cls = new Class[list.size()];
		IntStream.range(0, list.size()).forEach(i -> cls[i] = list.get(i));
		return cls;
	}
	
	/**
	 * 获取配置类的排序值
	 * @param cls
	 * @return
	 */
	private static int getOrderNum(Class<?> cls) {
		Order anno = cls.getDeclaredAnnotation(Order.class);
		return Optional.ofNullable(anno).map(val -> anno.value()).orElse(100);
	}
	
	/**
	 * 当前类是否包含注解类
	 * @param curCls 当前类
	 * @param annoCls 注解类
	 * @return
	 */
	public static boolean containAnno(Class<?> curCls, Class<?>... annoCls) {
		List<String> annoList = Stream.of(annoCls).map(anno -> anno.getName()).collect(Collectors.toList());
		Predicate<Class<?>> predicate = (cls) -> {
			Annotation[] annos = cls.getAnnotations();
			return Stream.of(annos).filter(anno -> annoList.contains(anno.annotationType().getName())).count() > 0;
		};
		return predicate.test(curCls);
	}
	
	/**
	 * 
	 * @param basePath 基础路径
	 * @param realPath 真实路径
	 * @return
	 */
	private static String getClassName(String basePath, String realPath) {
		// 两个分隔符字符串是斜杠的正则
		return Optional.of(realPath).map(str -> str.substring(basePath.length() + 1, str.length()))
				.map(str -> str.substring(0, str.lastIndexOf(".")))
				.map(str -> replaceFileSeparator(str)).get();
	}
	
	/**
	 * 通过类名获取class
	 * @param className
	 * @return
	 */
	private static Class<?> getClassOfName(String className) {
		try {
			return Class.forName(className);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/***
	 * 获取文件绝对路径
	 * @param resource
	 * @return
	 */
	private static String getFilePath(Resource resource) {
		try {
			return resource.getFile().getAbsolutePath();
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	
	/**
	 * 替换路径中的特殊字符
	 * @param str
	 * @return
	 */
	private static String replaceFileSeparator(String str) {
		return Optional.of(str).map(s -> s.replaceAll("//", "/").replaceAll(File.separator + File.separator, "/").replaceAll("/", ".")).get();
	}
	
	/**
	 * 获取class的路径
	 * @return
	 * @throws Exception
	 */
	public static String getBasePath() {
		try {
			Resource resource = resourcePatternResolver.getResource("classpath:/");
			return resource.getFile().getAbsolutePath();
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	
	public static void main(String[] args) throws IOException {
		FileSystemResourceLoader f = new FileSystemResourceLoader();
		Resource resource = f.getResource("/upload/20180115/a.txt");
		String string = resource.getFile().getAbsolutePath();
		System.out.println(string);
		//Resource resource =new PathResource("\\upload\\20180115\\a.txt");
		String filePath = getFilePath(resource);
		System.out.println(filePath);
		Stream.of(ClassUtil.scanClass("com.**.config", true, Configuration.class, Configurable.class)).
		forEach(cls -> System.out.println(cls.getName()));
	}
}
