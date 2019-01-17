package com.zip.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.primitives.Ints;
import com.zip.service.AnnounInfoService;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("anno")
public class AnnounAction extends BaseAction{
	
	@Autowired private AnnounInfoService announInfoService;
	
	@RequestMapping("title")
	public String title() {
		return "/anno/title.jsp";
	}
	
	@RequestMapping("list")
	public String list() {
		Map<String, String> param = getParameterMapWithPageInfo();
		param.put("status", "1");		// 只取正常的
		param.put("sort", "UPDATE_TIME");		// 设置排序
		param.put("order", "DESC");
		request.setAttribute("list", announInfoService.selectAnnounInfoBySearch(param));
		request.setAttribute("currentPage", param.get("page"));
		request.setAttribute("total", param.get("total"));
		return "/anno/list.jsp";
	}
	
	@RequestMapping("detail")
	public String detail() {
		Map<String, String> param = getParameterMap();
		if(!SysUtil.isNull(param.get("id"))) {
			param.put("announId", param.get("id"));
		}
		Map<String, Object> map = announInfoService.selectAnnounInfoById(param);
		request.setAttribute("data", map);
		
		param.clear();
		//更新浏览次数
		if(SysUtil.isNull(map.get("ANNOUN_COUNT"))) {
			param.put("count", "1");
		}else {
			Integer count = Ints.tryParse(map.get("ANNOUN_COUNT").toString())+1;
			param.put("count", count.toString());
		}
		param.put("announId", map.get("ANNOUN_ID").toString());
		announInfoService.updateCountById(param);
		return "/anno/detail.jsp";
	}
}
