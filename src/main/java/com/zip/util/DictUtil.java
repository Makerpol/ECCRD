package com.zip.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * 
 * @title DictUtil.java
 * @author ssk
 * @date 2017年12月19日
 * @description 字典工具类
 *
 */
public class DictUtil {
	
	private static Logger log = LoggerFactory.getLogger(DictUtil.class);
	
	/**
	 * 字典缓存
	 */
	private static Map<String, Map<String, Map<String, Object>>> DICT_MAP = Maps.newLinkedHashMap();
	
	/**
	 * 整理字典List
	 * 把字典List，整理成特定格式，例如：
	 * Map{
	 * 		DICT_COLUMN:{
	 * 			DICT_VALUE:{
	 * 				DICT_ID:value, 
	 * 				DICT_NAME:value, 
	 * 				DICT_COLUMN:value, 
	 * 				DICT_VALUE:value
	 * 			}, 
	 * 			DICT_VALUE:{
	 * 				DICT_ID:value, 
	 * 				DICT_NAME:value, 
	 * 				DICT_DICT_COLUMN:value, 
	 * 				DICT_VALUE:value
	 * 			}, ...
	 *		}, 
	 *		DICT_COLUMN:{
	 * 			DICT_VALUE:{
	 * 				DICT_ID:value, 
	 * 				DICT_NAME:value, 
	 * 				DICT_DICT_COLUMN:value, 
	 * 				DICT_VALUE:value
	 * 			}, 
	 * 			DICT_VALUE:{
	 * 				DICT_ID:value, 
	 * 				DICT_NAME:value, 
	 * 				DICT_DICT_COLUMN:value, 
	 * 				DICT_VALUE:value
	 * 			}, ...
	 *		}, ...
	 * }
	 * @param list
	 * @return
	 */
	public static void initDictMap(List<Map<String, Object>> list) {
		try {
			Lock.lock();
			log.debug("锁定初始化方法，防止重复初始化");
			DICT_MAP.clear();
			for (Map<String, Object> map : list) {
				Map<String, Map<String, Object>> tempMap = null;
				if (SysUtil.isNull(DICT_MAP.get(map.get("DICT_COLUMN")))) {
					tempMap = new LinkedHashMap<String, Map<String, Object>>();
				} else {
					tempMap = DICT_MAP.get(map.get("DICT_COLUMN"));
				}
				tempMap.put(map.get("DICT_VALUE").toString(), map);
				DICT_MAP.put(map.get("DICT_COLUMN").toString(), tempMap);
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
	 * 通过列名，获取对应的字典信息，格式：
	 * Map:{
	 * 		DICT_VALUE:{
	 * 			DICT_ID:value, 
	 * 			DICT_NAME:value, 
	 * 			DICT_COLUMN:value, 
	 * 			DICT_VALUE:value
	 * 		}, 
	 * 		DICT_VALUE:{
	 * 			DICT_ID:value, 
	 * 			DICT_NAME:value, 
	 * 			DICT_DICT_COLUMN:value, 
	 * 			DICT_VALUE:value
	 * 		}, ...
	 *	}
	 * @param context
	 * @param column
	 * @return
	 */
	public static Map<String, Map<String, Object>> get(String column) {
		return DICT_MAP.get(column);
	}
	
	/**
	 * 通过列名和值，获取对应的字典信息，格式：
	 * DICT_ID:value, 
	 * DICT_NAME:value, 
	 * DICT_DICT_COLUMN:value, 
	 * DICT_VALUE:value
	 * @param column
	 * @param value
	 * @return
	 */
	public static Map<String, Object> get(String column, String value) {
		return DICT_MAP.get(column).get(value);
	}
	
	/**
	 * 缓存是否为空
	 * @return
	 */
	public static boolean isEmpty() {
		return SysUtil.isNull(DICT_MAP);
	}
	
	/**
	 * 字典中是否包含当前值
	 * @param column
	 * @param val
	 * @return
	 */
	public static boolean contain(String column, String val) {
		return get(column).entrySet().stream().map(e -> e.getValue()).map(s -> s.get("DICT_VALUE").toString().trim()).collect(Collectors.toList()).contains(val.trim());
	}
}
