package com.zip.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.zip.service.AnnounInfoService;
import com.zip.service.ClInfoService;
import com.zip.service.CntInfoService;
import com.zip.service.LinkInfoService;
import com.zip.service.MtInfoService;
import com.zip.service.PdfInfoService;
import com.zip.util.DictUtil;

@Controller
public class IndexAction extends BaseAction {
	
	@Autowired private ClInfoService clInfoService;
	@Autowired private CntInfoService cntInfoService;
	@Autowired private AnnounInfoService announInfoService;
	@Autowired private PdfInfoService pdfInfoService;
	@Autowired private LinkInfoService linkInfoService;
	@Autowired private MtInfoService mtInfoService;

	@RequestMapping("title")
	public String title() {
		Map<String, String> param = Maps.newHashMap();
		//获取资讯动态子栏目信息
		param.put("model", "8");
		request.setAttribute("zxdt", mtInfoService.selectMtInfoByParentId(param));
		param.clear();
		//获取研究成果子栏目信息
		param.put("model", "13");
		request.setAttribute("zlyj", mtInfoService.selectMtInfoByParentId(param));
		//获取机构概况子栏目信息
		request.setAttribute("jggk", DictUtil.get("INST_TYPE"));
		//获取出版物类型信息
		request.setAttribute("cbw", DictUtil.get("PDF_TYPE"));
		return "/title.jsp";
	}
	
	@RequestMapping("contact")
	public String contact() {
		return "/contact.jsp";
	}
	
	@RequestMapping("link")
	public String link() {
		Map<String, String> param = getParameterMap();
		request.setAttribute("links", linkInfoService.selectLinkInfoBySearch(param));
		return "/link.jsp";
	}
	
	@RequestMapping("bottom")
	public String bottom() {
		return "/bottom.jsp";
	}
	
	@RequestMapping("index")
	public String index() {
		Map<String, String> param = getParameterMap();
		// 首页轮播
		param.put("type", "1");
		// 加载轮播
		List<Map<String, Object>> clList = clInfoService.selectClInfoByType(param);
		request.setAttribute("clList", clList);
		param.clear();
		param.put("type", "2");
		List<Map<String, Object>> clList2 = clInfoService.selectClInfoByType(param);
		request.setAttribute("clList2", clList2);
		// 加载文章信息
		this.getCntList(param);
		// 加载公告
		param.clear();
		param.put("max", "7");
		request.setAttribute("anno", announInfoService.selectAnnounByList(param));
		// 加载PDF
		param.clear();
		request.setAttribute("pdf", pdfInfoService.selectPdfInfoByList(param));
		return "/index.jsp";
	}
	
	/**
	 * 获取所有模块文章信息
	 * @param param
	 */
	public void getCntList(Map<String, String> param) {
		List<Map<String, Object>> pList = mtInfoService.selectAllParentMt();
		pList.stream().forEach(pMt ->{
			param.put("model", pMt.get("MT_ID").toString());
			//根据父节点获取所有子节点
			List<Map<String, Object>> list = mtInfoService.selectMtInfoByParentId(param);
			//遍历子节点，获取文章信息
			list.stream().forEach(mt -> {
				Map<String, String> mParam = Maps.newHashMap();
				mParam.put("model", param.get("model"));
				mParam.put("type", mt.get("MT_ID").toString());
				mParam.put("max", "9");
				request.setAttribute("list"+mParam.get("type"), cntInfoService.selectCntByList(mParam));
			});
		});
	}
}
