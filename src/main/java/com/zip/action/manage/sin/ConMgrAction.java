package com.zip.action.manage.sin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zip.action.BaseAction;
import com.zip.service.ConInfoService;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("conMgr")
public class ConMgrAction extends BaseAction{
	@Autowired private ConInfoService conInfoService;
	
	@RequestMapping("index")
	public String index() {
		request.setAttribute("contact", conInfoService.selectConInfo());
		return "/manage/sin/contact.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit(){
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("conId"))) {
			conInfoService.insertConInfo(param);
			return JsonUtil.getSucJson();
		}
		conInfoService.updateConInfo(param);
		return JsonUtil.getSucJson();
	}
}
