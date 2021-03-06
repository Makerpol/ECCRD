package com.zip.action.manage.ps;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zip.action.BaseAction;
import com.zip.service.PsInfoService;
import com.zip.util.DictUtil;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

/**
 * 培训服务
 * @author 赵巍
 *
 */
@Controller
@RequestMapping("tsMgr")
public class TsMgrAction extends BaseAction{
	@Autowired private PsInfoService psInfoService;
	
	@RequestMapping("index")
	public String index() {
		Map<String, String> param = Maps.newHashMap();
		param.put("type", "7");
		request.setAttribute("data", psInfoService.selectPsInfoByType(param));
		request.setAttribute("type", DictUtil.get("PS_TYPE").get("7"));
		request.setAttribute("action", "tsMgr");
		return "/manage/ps/index.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		param.put("type", "7");
		Map<String, Object> result = psInfoService.selectPsInfoByType(param);
		if(SysUtil.isNull(param.get("id"))&&result.size()==0) {
			psInfoService.insertPsInfo(param);
			return JsonUtil.getSucJson();
		}
		psInfoService.updatePsInfo(param);
		return JsonUtil.getSucJson();
	}
}
