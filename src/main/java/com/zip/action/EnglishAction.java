package com.zip.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zip.service.ClInfoService;
import com.zip.service.CntInfoService;
import com.zip.service.LinkInfoService;
import com.zip.service.MtInfoService;
import com.zip.service.VideoInfoService;
import com.zip.util.DictUtil;

@Controller
public class EnglishAction extends BaseAction{
	
	@Autowired private ClInfoService clInfoService;
	@Autowired private CntInfoService cntInfoService;
	@Autowired private LinkInfoService linkInfoService;
	@Autowired private MtInfoService mtInfoService;
	@Autowired private VideoInfoService videoInfoService;

	@RequestMapping("title_EN")
	public String title() {
		//获取ECCRD子栏目信息
		request.setAttribute("eccrd", DictUtil.get("INST_TYPE"));
		return "/title_EN.jsp";
	}
	
	@RequestMapping("contact_EN")
	public String contact() {
		return "/contact_EN.jsp";
	}
	
	@RequestMapping("link_EN")
	public String link() {
		Map<String, String> param = getParameterMap();
		request.setAttribute("links", linkInfoService.selectLinkInfoBySearch(param));
		return "/link_EN.jsp";
	}
	
	@RequestMapping("bottom_EN")
	public String bottom() {
		return "/bottom_EN.jsp";
	}
	
	@RequestMapping("index_EN")
	public String index() {
		Map<String, String> param = getParameterMap();
		// 首页轮播
		param.put("type", "1");
		// 加载轮播
		List<Map<String, Object>> clList = clInfoService.selectClInfoByType(param);
		request.setAttribute("clList", clList);
		param.clear();
		//其他轮播
		param.put("type", "2");
		List<Map<String, Object>> clList2 = clInfoService.selectClInfoByType(param);
		request.setAttribute("clList2", clList2);
		// 加载文章信息
		param.clear();
		this.getCntList(param);
		//视频信息
		param.clear();
		param.put("videoId", videoInfoService.selectLastInsertID()+"");
		request.setAttribute("video", videoInfoService.selectVideoInfoById(param));
		// 友情链接
		param.clear();
		request.setAttribute("links", linkInfoService.selectLinkInfoBySearch(param));
		return "/index_EN.jsp";
	}
	
	/**
	 * 获取所有模块文章信息
	 * @param param
	 */
	public void getCntList(Map<String, String> param) {
		List<Map<String, Object>> pList = mtInfoService.selectAllParentMt();

		pList.stream().forEach(pMt ->{
			param.put("model", pMt.get("MT_ID").toString());
			param.put("max", "9");
			param.put("title_EN", "title_EN");
			param.put("status", "1");		// 只取正常的
			param.put("sort", "INSERT_TIME");		// 设置排序
			param.put("order", "DESC");
			request.setAttribute("list"+pMt.get("MT_ID"), cntInfoService.selectCntByList(param));
			/*//根据父节点获取所有子节点
			List<Map<String, Object>> list = mtInfoService.selectMtInfoByParentId(param);
			//遍历子节点，获取文章信息
			list.stream().forEach(mt -> {
				Map<String, String> mParam = Maps.newHashMap();
				mParam.put("model", param.get("model"));
				mParam.put("type", mt.get("MT_ID").toString());
				mParam.put("max", "9");
				request.setAttribute("list"+mParam.get("type"), cntInfoService.selectCntByList(mParam));
			});*/
		});
	}
	
	@RequestMapping("english")
	public String english() {
		return "/english.jsp";
	}
}
