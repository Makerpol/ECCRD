package com.zip.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * 
 * @title PermissionUtil.java
 * @author ssk
 * @date 2017年12月19日
 * @description 权限工具类
 *
 */
public class PermissionUtil {
	
	private static Logger log = LoggerFactory.getLogger(PermissionUtil.class);

	private static Map<String, Map<String, Object>> PER_MAP = Maps.newLinkedHashMap();
	
	/**
	 * 初始化权限
	 * Map{
	 * 		PER_ID:{
	 * 			PER_ID
	 * 			PER_URL
	 * 			PER_TITLE
	 * 			PER_NAME
	 * 			PER_STATUS
	 * 			PER_NOTE
	 * 			PER_PARENT
	 * 			PER_PARAMS
	 *		}, 
	 *		PER_ID:{
	 * 			PER_ID
	 * 			PER_URL
	 * 			PER_TITLE
	 * 			PER_NAME
	 * 			PER_STATUS
	 * 			PER_NOTE
	 * 			PER_PARENT
	 * 			PER_PARAMS
	 *		}, ...
	 * }
	 * @param list
	 */
	public static void initDictMap(List<Map<String, Object>> list) {
		try {
			Lock.lock();
			log.debug("锁定初始化方法，防止重复初始化");
			PER_MAP.clear();
			for (Map<String, Object> map : list) {
				PER_MAP.put(map.get("PER_ID").toString(), map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("初始化字典信息异常", e);
		} finally {
			Lock.unlock();
			log.debug("解除锁定");
		}
	}
	
	/**
	 * 通过url获取权限名称
	 * @param url
	 * @return
	 */
	public static String getPermissionByUrl(String url) {
		return PER_MAP.entrySet().stream().filter(e -> !SysUtil.isNull(e.getValue().get("PER_URL")))
			.filter(e -> e.getValue().get("PER_URL").equals(url))
			.findFirst().map(s -> s.getValue().get("PER_NAME").toString())
			.orElse(null);
	}
	
	/**
	 * 获取所有的权限菜单信息
	 * @return
	 */
	public static List<Map<String, Object>> getAllPermission() {
		return PER_MAP.entrySet().stream().map(x -> x.getValue()).collect(Collectors.toList());
	}
}
