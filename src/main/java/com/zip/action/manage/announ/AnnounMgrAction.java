package com.zip.action.manage.announ;

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
import com.zip.service.AnnounInfoService;
import com.zip.util.DictUtil;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("annoMgr")
public class AnnounMgrAction extends BaseAction{
	
	@Autowired private AnnounInfoService announInfoService;
	
	@RequestMapping("index")
	public String index() {
		request.setAttribute("status", DictUtil.get("ANNOUN_STATUS"));
		return "/manage/announ/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception{
		Map<String, String> param = getParameterMapWithPageInfo();
		if(!SysUtil.isNull(param.get("tilte"))) {
			param.put("title", URLDecoder.decode(param.get("title"),SysUtil.ENCODE));
		}
		if(!SysUtil.isNull(param.get("userName"))) {
			param.put("userName", URLDecoder.decode(param.get("title"),SysUtil.ENCODE));
		}
		return () ->{
			JSONObject json = new JSONObject();
			List<Map<String, Object>> list = announInfoService.selectAnnounInfoBySearch(param);
			if(SysUtil.isNull(list)) {
				return JsonUtil.getFailJson("没有匹配的数据");
			}
			json.put("data", list);
			json.put("count", param.get("total"));
			json.putAll(JsonUtil.getSucJson());
			return json;
		};
	}
	
	/**
	 * 添加页面
	 */
	@RequestMapping("addPage")
	public String addPage() {
		return "/manage/announ/add.jsp";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("add")
	public @ResponseBody JSONObject add() {
		Map<String, String> param = getParameterMap();
		if(!SysUtil.betweenLength(param.get("title"), 1, 120)) {
			return JsonUtil.getFailJson("标题不能为空，并且长度在1-120之间");
		}
		if(SysUtil.isNum(param.get("content"))) {
			return JsonUtil.getFailJson("内容不能为空");
		}
		if(!SysUtil.betweenLength(param.get("from"), 0, 80)) {
			return JsonUtil.getFailJson("出处在1-80个字之间");
		}
		if(!SysUtil.betweenLength(param.get("url"), 0, 80)) {
			return JsonUtil.getFailJson("引用链接在1-80个字之间");
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>)getSession().getAttribute(SysUtil.LOGIN_SESSION);
		param.put("userId", user.get("USER_ID").toString());
		announInfoService.insertAnnounInfo(param);
		return JsonUtil.getSucJson();
	}
	
	/**
	 * 编辑页面
	 */
	@RequestMapping("editPage")
	public String editPage() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("announId")) || !SysUtil.isNum(param.get("announId"))) {
			JsonUtil.writeStr(response, "ID不能为空");
			return null;
		}
		request.setAttribute("data", announInfoService.selectAnnounInfoById(param));
		return "/manage/announ/edit.jsp";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("announId")) ||!SysUtil.isNum(param.get("announId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		if(!SysUtil.betweenLength(param.get("title"), 1, 120)) {
			return JsonUtil.getFailJson("标题不能为空，并且长度在1-120之间");
		}
		if(SysUtil.isNum(param.get("content"))) {
			return JsonUtil.getFailJson("内容不能为空");
		}
		if(!SysUtil.betweenLength(param.get("from"), 0, 80)) {
			return JsonUtil.getFailJson("出处在1-80个字之间");
		}
		if(!SysUtil.betweenLength(param.get("url"), 0, 80)) {
			return JsonUtil.getFailJson("引用链接在1-80个字之间");
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>)getSession().getAttribute(SysUtil.LOGIN_SESSION);
		param.put("userId", user.get("USER_ID").toString());
		announInfoService.updateAnnounInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("del")
	public @ResponseBody JSONObject del() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("announId")) ||!SysUtil.isNum(param.get("announId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		announInfoService.deleteAnnounInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	/**
	 * 前端页面获取通知公告列表
	 */
}
