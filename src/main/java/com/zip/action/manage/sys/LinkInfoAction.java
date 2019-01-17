package com.zip.action.manage.sys;

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
import com.zip.service.LinkInfoService;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("link")
public class LinkInfoAction extends BaseAction{
	
	@Autowired private LinkInfoService linkInfoService;
	
	@RequestMapping("index")
	public String index() {
		return "/manage/sys/link/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception{
		Map<String, String> param = getParameterMap();
		if(!SysUtil.isNull(param.get("title"))) {
			param.put("title", URLDecoder.decode(param.get("title"),SysUtil.ENCODE));
		}
		return ()->{
			JSONObject json = new JSONObject();
			List<Map<String, Object>> list = linkInfoService.selectLinkInfoBySearch(param);
			if(SysUtil.isNull(list)) {
				return JsonUtil.getFailJson("没有匹配的数据");
			}
			json.put("data", list);
			json.putAll(JsonUtil.getSucJson());
			return json;
		};
	}
	
	@RequestMapping("addPage")
	public String addPage() {
		return "/manage/sys/link/add.jsp";
	}
	
	@RequestMapping("add")
	public @ResponseBody JSONObject add(){
		Map<String, String> param = getParameterMap();
		if(!SysUtil.betweenLength(param.get("title"), 1, 80)) {
			return JsonUtil.getFailJson("标题不能为空，长度在1-80个字之间");
		}
		if(SysUtil.isNull(param.get("url"))) {
			return JsonUtil.getFailJson("地址不能为空");
		}
		linkInfoService.insertLinkInfo(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("editPage")
	public String editPage() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("linkId")) || !SysUtil.isNum(param.get("linkId"))) {
			JsonUtil.writeStr(response, "ID不能为空");
		}
		request.setAttribute("data", linkInfoService.selectLinkInfoById(param));
		return "/manage/sys/link/edit.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("linkId")) || !SysUtil.isNum(param.get("linkId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		if(!SysUtil.betweenLength(param.get("title"), 1, 80)) {
			return JsonUtil.getFailJson("标题不能为空，长度在1-80个字之间");
		}
		if(SysUtil.isNull(param.get("url"))) {
			return JsonUtil.getFailJson("地址不能为空");
		}
		linkInfoService.updateLinkInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("del")
	public @ResponseBody JSONObject del(){
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("linkId")) || !SysUtil.isNum(param.get("linkId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		linkInfoService.deleteLinkInfoById(param);
		return JsonUtil.getSucJson();
	}
}
