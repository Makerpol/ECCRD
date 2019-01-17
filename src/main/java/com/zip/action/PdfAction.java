package com.zip.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zip.service.PdfInfoService;
import com.zip.util.DictUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("pdf")
public class PdfAction extends BaseAction{
	
	@Autowired private PdfInfoService pdfInfoService;
	
	@RequestMapping("title")
	public String title() {
		return "/pfd/title.jsp";
	}
	
	@RequestMapping("list")
	public String list() {
		Map<String, String> param = getParameterMapWithPageInfo();
		if(SysUtil.isNull(param.get("type"))) {
			param.put("type", "1");
		}
		Map<String, Map<String, Object>> map = DictUtil.get("PDF_TYPE");
		map.forEach((k,v)->{
			if(param.get("type").equals(k)) {
				request.setAttribute("currentType", v);
			}		
		});
		param.put("sort", "INSERT_TIME");
		param.put("order", "DESC");
		
		request.setAttribute("types", map);
		request.setAttribute("list", pdfInfoService.selectPdfInfoBySearch(param));
		request.setAttribute("total", param.get("total"));
		return "/pdf/list.jsp";
	}
	
	@RequestMapping("detail")
	public String detail() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("id"))) {
			request.setAttribute("msg", "访问文章不存在");
		}
		request.setAttribute("data", pdfInfoService.selectPdfInfoById(param));
		return "/pdf/detail.jsp";
	}
}
