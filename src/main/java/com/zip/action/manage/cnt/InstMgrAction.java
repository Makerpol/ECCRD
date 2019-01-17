package com.zip.action.manage.cnt;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zip.action.BaseAction;
import com.zip.service.InstInfoService;
import com.zip.util.DictUtil;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("instMgr")
public class InstMgrAction extends BaseAction{
	
	@Autowired private InstInfoService instInfoService;
	
	@RequestMapping("index")
	public String index() {
		request.setAttribute("types", DictUtil.get("INST_TYPE"));
		return "/manage/cnt/inst/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception {
		Map<String, String> param = getParameterMap();
		if (!SysUtil.isNull(param.get("title"))) {
			param.put("title", URLDecoder.decode(param.get("title"), SysUtil.ENCODE));
		}
		return () -> {
			JSONObject json = new JSONObject();
			List<Map<String, Object>> list = instInfoService.selectInstBySearch(param);
			if (SysUtil.isNull(list)) {
				return JsonUtil.getFailJson("没有匹配的数据");
			}
			json.put("data", list);
			json.put("count", param.get("total"));
			json.putAll(JsonUtil.getSucJson());
			return json;
		};
	}
	
	@RequestMapping("addPage")
	public String addPage() {
		request.setAttribute("types", DictUtil.get("INST_TYPE"));
		return "/manage/cnt/inst/add.jsp";
	}
	
	@RequestMapping("editPage")
	public String editPage() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("instId")) || !SysUtil.isNum(param.get("instId"))) {
			JsonUtil.writeStr(response, "ID不能为空");
			return null;
		}
		request.setAttribute("types", DictUtil.get("INST_TYPE"));
		request.setAttribute("data", instInfoService.selectInstById(param));
		return "/manage/cnt/inst/edit.jsp";
	}
	
	@RequestMapping("add")
	public @ResponseBody JSONObject add(){
		Map<String, String> param = getParameterMap();
		if (!SysUtil.betweenLength(param.get("title"), 1, 120)) {
			return JsonUtil.getFailJson("标题不能为空，且在1~120个字之间");
		}
		if (SysUtil.isNull(param.get("type")) || !SysUtil.isNum(param.get("type"))) {
			return JsonUtil.getFailJson("类型不能为空");
		}
		if (SysUtil.isNull(param.get("content"))) {
			return JsonUtil.getFailJson("内容不能为空");
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) getSession().getAttribute(SysUtil.LOGIN_SESSION);
		param.put("userId", user.get("USER_ID").toString());
		instInfoService.insertInstInfo(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("instId")) || !SysUtil.isNum(param.get("instId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		if (!SysUtil.betweenLength(param.get("title"), 1, 120)) {
			return JsonUtil.getFailJson("标题不能为空，且在1~120个字之间");
		}
		if (SysUtil.isNull(param.get("type")) || !SysUtil.isNum(param.get("type"))) {
			return JsonUtil.getFailJson("类型不能为空");
		}
		if (SysUtil.isNull(param.get("content"))) {
			return JsonUtil.getFailJson("内容不能为空");
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) getSession().getAttribute(SysUtil.LOGIN_SESSION);
		param.put("userId", user.get("USER_ID").toString());
		instInfoService.updateInstInfoById(param);
		return JsonUtil.getSucJson();
	}
}
