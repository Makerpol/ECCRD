package com.zip.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zip.service.InstInfoService;
import com.zip.util.DictUtil;

@Controller
@RequestMapping("inst")
public class InstAction extends BaseAction{
	
	@Autowired private InstInfoService instInfoService;
	
	@RequestMapping("index")
	public String index() {
		this.getData();
		return "/inst/index.jsp";
	}
	
	@RequestMapping("indexEN")
	public String indexEN() {
		this.getData();
		return "/inst/index_EN.jsp";
	}
	
	private void getData() {
		Map<String, String> param = getParameterMap();
		request.setAttribute("types", DictUtil.get("INST_TYPE"));
		request.setAttribute("list", instInfoService.selectInstBySearch(param));
	}
}
