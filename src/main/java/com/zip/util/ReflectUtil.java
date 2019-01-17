package com.zip.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectUtil {

	private static Logger log = LoggerFactory.getLogger(ReflectUtil.class);
	
	/**
	 * 通过字段名获取对象中的值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Object result = null;
		Field field = getField(obj, fieldName);
		if (field != null) {
			try {
				if (field.isAccessible()) {
					result = field.get(obj);
				} else {
					field.setAccessible(true);
					result = field.get(obj);
					field.setAccessible(false);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("获取对象字段值异常", e);
			}
		}
		return result;
	}
	
	/**
	 * 通过字段名获取对象中的字段
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	private static Field getField(Object obj, String fieldName) {
		Field field = null;
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (Exception e) {
				// 如果取不到字段，则继续循环，从父类获取
			}
		}
		return field;
	}
	
	/**
	 * 通过字段名设置对象中的字段的值
	 * @param obj
	 * @param fieldName
	 * @param fieldValue
	 */
	public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
		Field field = getField(obj, fieldName);
		if (field != null) {
			try {
				if (field.isAccessible()) {
					field.set(obj, fieldValue);
				} else {
					field.setAccessible(true);
					field.set(obj, fieldValue);
					field.setAccessible(false);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("设置对象字段值异常", e);
			}
		}
	}
}
