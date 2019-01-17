package com.zip.action.manage.cnt;

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
import com.zip.service.PdfInfoService;
import com.zip.util.DictUtil;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("pdfMgr")
public class PdfMgrAction extends BaseAction{
	
	@Autowired private PdfInfoService pdfInfoService;
	
	@RequestMapping("index")
	public String index() {
		request.setAttribute("types", DictUtil.get("PDF_TYPE"));
		request.setAttribute("status", DictUtil.get("PDF_STATUS"));
		return "/manage/cnt/pdf/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception{
		Map<String,String> param = getParameterMapWithPageInfo();
		if(!SysUtil.isNull(param.get("title"))) {
			param.put("title", URLDecoder.decode(param.get("title"),SysUtil.ENCODE));
		}
		
		return () -> {
			JSONObject json = new JSONObject();
			List<Map<String, Object>> list = pdfInfoService.selectPdfInfoBySearch(param);
			if(SysUtil.isNull(list)) {
				return JsonUtil.getFailJson("没有匹配的数据");
			}
			json.put("data", list);
			json.put("count", param.get("total"));
			json.putAll(JsonUtil.getSucJson());
			return json;
		};
	}
	
	@RequestMapping("addPage")
	public String addPage() {
		request.setAttribute("types", DictUtil.get("PDF_TYPE"));
		return "/manage/cnt/pdf/add.jsp";
	}
	
	@RequestMapping("add")
	public @ResponseBody JSONObject add(){
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("type")) || !DictUtil.contain("PDF_TYPE", param.get("type"))) {
			return JsonUtil.getFailJson("模块不能为空");
		}
		if(SysUtil.isNull(param.get("fileIds"))) {
			return JsonUtil.getFailJson("内容不能为空");
		}
		if(!SysUtil.isNull(param.get("imgPath"))){
			param.put("path", param.get("imgPath"));
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>)getSession().getAttribute(SysUtil.LOGIN_SESSION);
		param.put("userId", user.get("USER_ID").toString());
		param.put("fileId", param.get("fileIds"));
		pdfInfoService.insertPdfInfo(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("editPage")
	public String editPage() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("pdfId")) || !SysUtil.isNum(param.get("pdfId"))) {
			JsonUtil.writeStr(response, "ID不能为空");
			return null;
		}
		request.setAttribute("data", pdfInfoService.selectPdfInfoById(param));
		request.setAttribute("types", DictUtil.get("PDF_TYPE"));
		return "/manage/cnt/pdf/edit.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("pdfId")) || !SysUtil.isNum(param.get("pdfId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		if(!SysUtil.betweenLength(param.get("title"), 1, 80)) {
			return JsonUtil.getFailJson("标题不能为空，并且在1-80个字之间");
		}
		if(SysUtil.isNull(param.get("type")) || !DictUtil.contain("PDF_TYPE", param.get("type"))) {
			return JsonUtil.getFailJson("模块不能为空");
		}
		if(!SysUtil.betweenLength(param.get("url"), 0, 80)) {
			return JsonUtil.getFailJson("引用连接在1~80个字之间");
		}
		pdfInfoService.updatePdfInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("del")
	public @ResponseBody JSONObject del() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("pdfId")) || !SysUtil.isNum(param.get("pdfId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		pdfInfoService.deletePdfInfoById(param);
		return JsonUtil.getSucJson();
	}
}
