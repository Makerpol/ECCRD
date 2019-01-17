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
 * 产业专题
 * @author 赵巍
 *
 */
@Controller
@RequestMapping("ciMgr")
public class CiMgrAction extends BaseAction{
	@Autowired private PsInfoService psInfoService;
	
	@RequestMapping("index")
	public String index() {
		Map<String, String> param = Maps.newHashMap();
		param.put("type", "4");
		request.setAttribute("data", psInfoService.selectPsInfoByType(param));
		request.setAttribute("type", DictUtil.get("PS_TYPE").get("4"));
		request.setAttribute("action", "ciMgr");
		return "/manage/ps/index.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		param.put("type", "4");
		Map<String, Object> result = psInfoService.selectPsInfoByType(param);
		if(SysUtil.isNull(param.get("id"))&&result.size()==0) {
			psInfoService.insertPsInfo(param);
			return JsonUtil.getSucJson();
		}
		psInfoService.updatePsInfo(param);
		return JsonUtil.getSucJson();
	}
}
