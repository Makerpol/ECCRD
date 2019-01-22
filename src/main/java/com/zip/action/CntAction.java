package com.zip.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.zip.service.CntInfoService;
import com.zip.service.MtInfoService;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("cnt")
public class CntAction extends BaseAction {
	
	@Autowired private MtInfoService mtInfoService;
	@Autowired private CntInfoService cntInfoService;

	@RequestMapping("title")
	public String title() {
		request.setAttribute("models", mtInfoService.selectAllParentMt());
		return "/cnt/title.jsp";
	}
	
	@RequestMapping("list")
	public String list() {
		Map<String, String> param = getParameterMapWithPageInfo();
		
		if (SysUtil.isNull(param.get("model"))) {
			param.put("model", "1");
		} 
		
		Map<String, String> mtParam = Maps.newHashMap();
		mtParam.put("mtId", param.get("model"));
		request.setAttribute("model", mtInfoService.selectMtInfoById(mtParam));
		
		param.put("status", "1");		// 只取正常的
		param.put("sort", "UPDATE_TIME");		// 设置排序
		param.put("order", "DESC");
		request.setAttribute("list", cntInfoService.selectCntBySearch(param));
		request.setAttribute("currentPage", param.get("page"));
		request.setAttribute("total", param.get("total"));
		return "/cnt/list.jsp";
	}
	
	@RequestMapping("detail")
	public String detail() {
		Map<String, String> param = getParameterMap();
		if (!SysUtil.isNull(param.get("id"))) {
			param.put("cntId", param.get("id"));
		}
		Map<String, Object> map = cntInfoService.selectCntById(param);
		request.setAttribute("data", map);
		
		param.clear();
		//更新浏览次数
		if(SysUtil.isNull(map.get("CNT_COUNT"))) {
			param.put("count", "1");
		}else {
			Integer count = Ints.tryParse(map.get("CNT_COUNT").toString())+1;
			param.put("count", count.toString());
		}
		param.put("cntId", map.get("CNT_ID").toString());
		cntInfoService.updateCountById(param);
		return "/cnt/detail.jsp";
	}
}
