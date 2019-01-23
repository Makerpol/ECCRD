package com.zip.action.manage.cnt;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.primitives.Ints;
import com.zip.action.BaseAction;
import com.zip.service.ClInfoService;
import com.zip.util.DictUtil;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("clMgr")
public class ClMgrAction extends BaseAction {

	@Autowired private ClInfoService clInfoService;
	
	@RequestMapping("index")
	public String index() {
		request.setAttribute("types", DictUtil.get("CL_TYPE"));
		return "/manage/cnt/cl/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception {
		Map<String, String> param = getParameterMapWithPageInfo();
		if (!SysUtil.isNull(param.get("title"))) {
			param.put("title", URLDecoder.decode(param.get("title"), SysUtil.ENCODE));
		}
	
		return () -> {
			JSONObject json = new JSONObject();
			List<Map<String, Object>> list = clInfoService.selectClInfoBySearch(param);
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
		request.setAttribute("types", DictUtil.get("CL_TYPE"));
		return "/manage/cnt/cl/add.jsp";
	}
	
	@RequestMapping("add")
	public @ResponseBody JSONObject add() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("type")) || !DictUtil.contain("CL_TYPE", param.get("type"))) {
			return JsonUtil.getFailJson("模块不能为空");
		}
		if (SysUtil.isNull(param.get("fileIds"))) {
			return JsonUtil.getFailJson("内容不能为空");
		}
		clInfoService.insertClList(param.get("type"), Stream.of(param.get("fileIds")
				.split(","))
				.filter(s -> !SysUtil.isNull(s))
				.map(Ints::tryParse)
				.distinct()
				.collect(Collectors.toList()));
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("editPage")
	public String editPage() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("clId")) || !SysUtil.isNum(param.get("clId"))) {
			JsonUtil.writeStr(response, "ID不能为空");
			return null;
		}
		request.setAttribute("data", clInfoService.selectClInfoById(param));
		request.setAttribute("types", DictUtil.get("CL_TYPE"));
		return "/manage/cnt/cl/edit.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("clId")) || !SysUtil.isNum(param.get("clId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		if (!SysUtil.betweenLength(param.get("title"), 1, 80)) {
			return JsonUtil.getFailJson("标题不能为空，且在1~80个字之间");
		}
		if (SysUtil.isNull(param.get("type")) || !DictUtil.contain("CL_TYPE", param.get("type"))) {
			return JsonUtil.getFailJson("模块不能为空");
		}
		if (!SysUtil.betweenLength(param.get("url"), 0, 80)) {
			return JsonUtil.getFailJson("引用连接在1~80个字之间");
		}
		clInfoService.updateClInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("del")
	public @ResponseBody JSONObject del() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("clId")) || !SysUtil.isNum(param.get("clId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		clInfoService.deleteClInfoById(param);
		return JsonUtil.getSucJson();
	}
}
