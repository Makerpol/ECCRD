package com.zip.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zip.service.PsInfoService;
import com.zip.util.DictUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("ps")
public class PsAction extends BaseAction{
	@Autowired private PsInfoService psInfoService;
	
	@RequestMapping("index")
	public String index() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("type"))) {
			param.put("type", "1");
		}
		request.setAttribute("data", psInfoService.selectPsInfoByType(param));
		request.setAttribute("type", DictUtil.get("PS_TYPE").get(param.get("type")));
		return "/ps/index.jsp";
	}
}
