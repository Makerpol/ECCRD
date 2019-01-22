package com.zip.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zip.service.VideoInfoService;

@Controller
@RequestMapping("video")
public class VideoAction extends BaseAction{
	@Autowired private VideoInfoService videoInfoService;
	
	@RequestMapping("title")
	public String title() {
		return "/video/title.jsp";
	}
	
	@RequestMapping("list")
	public String list() {
		Map<String, String> param = getParameterMapWithPageInfo();
		
		param.put("sort", "INSERT_TIME");
		param.put("order", "DESC");
		
		request.setAttribute("list", videoInfoService.selectVideoInfoBySearch(param));
		request.setAttribute("total", param.get("total"));
		return "/video/list.jsp";
	}
}