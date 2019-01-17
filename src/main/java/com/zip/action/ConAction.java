package com.zip.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zip.service.ConInfoService;

@Controller
@RequestMapping("con")
public class ConAction extends BaseAction{
	@Autowired private ConInfoService conInfoService;
	
	@RequestMapping("index")
	public String index() {
		request.setAttribute("contact", conInfoService.selectConInfo());
		return "/contact.jsp";
	}
}
