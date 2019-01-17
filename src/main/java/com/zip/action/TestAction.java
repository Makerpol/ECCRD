package com.zip.action;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.alibaba.fastjson.JSONObject;
import com.zip.util.JsonUtil;
import com.zip.util.MysqlUtil;

@Controller
@RequestMapping("test")
public class TestAction extends BaseAction {

	@Autowired private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 同步返回页面
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "/test.jsp";
	}
	
	/**
	 * 异步返回结果
	 * 当setResblt的时候返回
	 * @return
	 */
	@RequestMapping("asyncDefIndex")
	public DeferredResult<String> asyncDefIndex() {
		// 请求参数获取，在异步之外
		Map<String, String> param = getParameterMap();
		DeferredResult<String> deferredResult = new DeferredResult<>(2000l);
		deferredResult.onCompletion(() -> {
			System.out.println("完成");
		});
		deferredResult.onTimeout(() -> {
			deferredResult.setResult("/error.jsp");
		});
		threadPool.execute(() -> {
			System.out.println(param.toString());
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			deferredResult.setResult("/test.jsp");
		});
		return deferredResult;
	}
	
	/**
	 * 异步
	 * @return
	 */
	@RequestMapping("asyncCallIndex")
	public Callable<String> asyncCallIndex() {
		// 请求参数获取，在异步之外
		Map<String, String> param = getParameterMap();
		return () -> {
			System.out.println(param.toString());
			return "/test.jsp";
		};
	}
	
	/**
	 * 通过异步计算
	 * 同步返回结果
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@RequestMapping("otherIndex")
	public String otherIndex() throws InterruptedException, ExecutionException {
		Map<String, String> param = getParameterMap();
		Future<String> future = threadPool.submit(() -> {
			System.out.println(param.toString());
			return "/test.jsp";
		});
		try {
			return future.get(2000, TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			// TODO: handle exception
			return "/error.jsp";
		}
	}
	
	/**
	 * 返回json实体
	 * @return
	 */
	@RequestMapping("json")
	public @ResponseBody JSONObject json() {
		return JsonUtil.getSucJson();
	}
	
	/**
	 * 异步返回json实体
	 * @return
	 */
	@RequestMapping("asyncJson")
	public @ResponseBody Callable<JSONObject> asyncJson() {
		return () -> JsonUtil.getSucJson();
	}
	
	/**
	 * 接收客户端直接POST到服务端的json实体
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "receiveJson", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Callable<JSONObject> receiveJson(@RequestBody JSONObject param) {
		return () -> param;
	}
	
	@RequestMapping("tableDetail")
	public @ResponseBody JSONObject tableDetail() throws Exception {
		return JsonUtil.getSucJson(MysqlUtil.getTableDetail("CUSTOM_INFO", sqlSessionFactory.openSession().getConnection()).toString());
	}
}
