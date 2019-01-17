package com.zip.action.manage;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zip.action.BaseAction;
import com.zip.service.CntInfoService;
import com.zip.service.MtInfoService;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("mtUtil")
public class MtUtilAction extends BaseAction {
	
	@Autowired private MtInfoService mtInfoService;
	@Autowired private CntInfoService cntInfoService;
	
	@RequestMapping("index")
	public String index() {
		request.setAttribute("parent", mtInfoService.selectAllParentMt());
		return "/manage/sys/mt/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception {
		Map<String, String> param = getParameterMap();
		if(!SysUtil.isNull(param.get("mtName"))) {
			param.put("mtName", URLDecoder.decode(param.get("mtName"), SysUtil.ENCODE));
		}
		return () -> {
			JSONObject json = JsonUtil.getSucJson();
			List<Map<String, Object>> list= mtInfoService.selectMtInfoBySearch(param);
			json.put("data", list);
			json.put("list", list);
			return json;
		};
	}
	
	@RequestMapping("addPage")
	public String addPage() {
		Map<String, String> param = getParameterMap();
		if (!SysUtil.isNull(param.get("parent")) || SysUtil.isNum(param.get("parent"))) {
			param.put("mtId", param.get("parent"));
			request.setAttribute("parent",mtInfoService.selectMtInfoById(param));
		}
		request.setAttribute("data", mtInfoService.selectAllParentMt());

		return "/manage/sys/mt/add.jsp";
	}
	
	@RequestMapping("add")
	public @ResponseBody JSONObject add() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("mtName")) || !SysUtil.betweenLength(param.get("mtName"), 1, 20)) {
			return JsonUtil.getFailJson("模块名不能为空，并且长度在1~20之间");
		}
		if(SysUtil.isNull(param.get("parent")) || !SysUtil.isNum(param.get("parent"))) {
			param.put("parent", "0");
		}
		if(!SysUtil.betweenLength(param.get("mtNtote"), 0, 500)){
			return JsonUtil.getFailJson("备注长度在0~500之间");
		}
		int mtId = mtInfoService.insertMtInfo(param);
		param.put("mtId", mtId+"");
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("editPage")
	public String editPage() {
		Map<String,String> param = getParameterMap();
		if(SysUtil.isNull(param.get("mtId")) || !SysUtil.isNum(param.get("mtId"))) {
			JsonUtil.writeJson(response, JsonUtil.getFailJson("ID不能为空"));
			return null; 
		}
		if(!SysUtil.isNum(param.get("parent"))) {
			JsonUtil.writeJson(response, JsonUtil.getFailJson("父栏目参数错误!"));
			return null;
		}
		request.setAttribute("list", mtInfoService.selectAllParentMt());
		request.setAttribute("data", mtInfoService.selectMtInfoById(param));
		return "/manage/sys/mt/edit.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("mtName")) || !SysUtil.betweenLength(param.get("mtName"), 1, 20)) {
			return JsonUtil.getFailJson("模块名不能为空，并且长度在1~20之间");
		}
		if(SysUtil.isNull(param.get("parent")) || !SysUtil.isNum(param.get("parent"))) {
			param.put("parent", "0");
		}
		if(!SysUtil.betweenLength(param.get("mtNtote"), 0, 500)){
			return JsonUtil.getFailJson("备注长度在0~500之间");
		}
		mtInfoService.updateMtInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("del")
	public @ResponseBody JSONObject del() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("mtId")) || !SysUtil.isNum(param.get("mtId"))) {
			return JsonUtil.getFailJson("模块ID不能为空！");
		}
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("type", param.get("mtId"));
		List<Map<String,Object>> list = cntInfoService.selectCntBySearch(temp);
		if(list.size()>0) {
			return JsonUtil.getFailJson("當前模塊正在使用，不能刪除！");
		}
		mtInfoService.deleteMtInfoById(param);
		return JsonUtil.getSucJson();
	}
	
}
