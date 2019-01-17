package com.zip.util;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

public class JsonUtil {
	
	private static Logger log = LoggerFactory.getLogger(JsonUtil.class);
	
	/**
	 * 响应函数
	 * 判断json是否为成功
	 */
	private final static Predicate<JSONObject> predicate = (json) -> {
		return Optional.of(json).map(j -> j.getInteger("code")).map(b -> b == 0).orElse(false);
	};
	
	/**
	 * 响应函数
	 */
	private final static Function<Integer, String> func = (code) -> {
		if (code == 0) {
			// 成功
			return "成功";
		} else {
			// 失败
			return "失败";
		}
	};
	
	/**
	 * 将字符串转化为map
	 * @param paramStr
	 * @return
	 */
	public static Map<String, String> getParam(String paramStr) {
		JSONObject jsonParam = Optional.ofNullable(paramStr).map(str -> JSONObject.parseObject(paramStr)).orElse(new JSONObject());
		Map<String, String> param = Maps.newHashMap();
		jsonParam.keySet().forEach(key -> {
			param.put(key, jsonParam.getString(key));
		});
		return param;
	}
	
	/**
	 * 判断json是否为成功
	 * @param json
	 * @return
	 */
	public static boolean isSucess(JSONObject json) {
		return predicate.test(json);
	}

	/**
	 * 获取json
	 * @param code
	 * @param msg
	 * @return
	 */
	private static JSONObject getRtnJson(int code, String msg) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", Optional.ofNullable(msg).filter(s -> !SysUtil.isNull(s)).orElse(func.apply(code)));
		return json;
	}
	
	/**
	 * 获取成功的json
	 * @param msg
	 * @return
	 */
	public static JSONObject getSucJson(String msg) {
		return getRtnJson(0, msg);
	}
	
	/**
	 * 获取成功的json
	 * @param msg
	 * @return
	 */
	public static JSONObject getSucJson() {
		return getSucJson(null);
	}
	
	/**
	 * 获取失败的json
	 * @param msg
	 * @return
	 */
	public static JSONObject getFailJson(String msg) {
		return getRtnJson(1, msg);
	}
	
	/**
	 * 获取失败的json
	 * @param msg
	 * @return
	 */
	public static JSONObject getFailJson() {
		return getFailJson(null);
	}
	
	/**
	 * 输出字符串
	 * @param response
	 * @param val
	 */
	public static void writeStr(HttpServletResponse response, String val) {
		response.setContentType("text/html;charset=" + SysUtil.ENCODE);
		response.setCharacterEncoding(SysUtil.ENCODE);
		try (PrintWriter out = response.getWriter()) {
			out.write(val);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("response输出信息异常", e);
		}
	}
	
	/**
	 * 输出json信息
	 * @param response
	 * @param json
	 */
	public static void writeJson(HttpServletResponse response, JSON json) {
		writeStr(response, json.toJSONString());
	}
}
