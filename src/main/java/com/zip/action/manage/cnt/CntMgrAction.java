package com.zip.action.manage.cnt;

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zip.action.BaseAction;
import com.zip.service.AnnounInfoService;
import com.zip.service.CntInfoService;
import com.zip.util.DictUtil;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("cntMgr")
public class CntMgrAction extends BaseAction {
	
	@Autowired private CntInfoService cntInfoService;
	@Autowired private AnnounInfoService announInfoService;

	@RequestMapping("index")
	public String index() {
		request.setAttribute("status", DictUtil.get("CNT_STATUS"));
		return "/manage/cnt/cnt/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception {
		Map<String, String> param = getParameterMapWithPageInfo();
		if (!SysUtil.isNull(param.get("title"))) {
			param.put("title", URLDecoder.decode(param.get("title"), SysUtil.ENCODE));
		}
		if (!SysUtil.isNull(param.get("url"))) {
			param.put("url", URLDecoder.decode(param.get("url"), SysUtil.ENCODE));
		}
		if (!SysUtil.isNull(param.get("from"))) {
			param.put("from", URLDecoder.decode(param.get("from"), SysUtil.ENCODE));
		}
		if (!SysUtil.isNull(param.get("time"))) {
			String time[] = param.get("time").split(" - ");
			param.put("start", time[0].trim());
			if (time.length > 1) {
				param.put("end", time[1].trim());
			}
		}
		return () -> {
			JSONObject json = new JSONObject();
			List<Map<String, Object>> list = cntInfoService.selectCntBySearch(param);
			if (SysUtil.isNull(list)) {
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
		return "/manage/cnt/cnt/add.jsp";
	}
	
	@RequestMapping("add")
	public @ResponseBody JSONObject add() {
		Map<String, String> param = getParameterMap();
		if (!SysUtil.betweenLength(param.get("title"), 1, 120)) {
			return JsonUtil.getFailJson("标题不能为空，且在1~120个字之间");
		}
		if (SysUtil.isNull(param.get("model")) || !SysUtil.isNum(param.get("model"))) {
			return JsonUtil.getFailJson("模块不能为空");
		}
		if (SysUtil.isNull(param.get("type")) || !SysUtil.isNum(param.get("type"))) {
			return JsonUtil.getFailJson("类型不能为空");
		}
		if (SysUtil.isNull(param.get("content"))) {
			return JsonUtil.getFailJson("内容不能为空");
		}
		if (!SysUtil.betweenLength(param.get("from"), 0, 80)) {
			return JsonUtil.getFailJson("出处在1~80个字之间");
		}
		if (!SysUtil.betweenLength(param.get("url"), 0, 80)) {
			return JsonUtil.getFailJson("引用连接在1~80个字之间");
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) getSession().getAttribute(SysUtil.LOGIN_SESSION);
		param.put("userId", user.get("USER_ID").toString());
		cntInfoService.insertCntInfo(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("editPage")
	public String editPage() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("cntId")) || !SysUtil.isNum(param.get("cntId"))) {
			JsonUtil.writeStr(response, "ID不能为空");
			return null;
		}
		request.setAttribute("data", cntInfoService.selectCntById(param));
		return "/manage/cnt/cnt/edit.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("cntId")) || !SysUtil.isNum(param.get("cntId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		if (!SysUtil.betweenLength(param.get("title"), 1, 120)) {
			return JsonUtil.getFailJson("标题不能为空，且在1~120个字之间");
		}
		if (SysUtil.isNull(param.get("model")) || !SysUtil.isNum(param.get("model"))) {
			return JsonUtil.getFailJson("模块不能为空");
		}
		if (SysUtil.isNull(param.get("type")) || !SysUtil.isNum(param.get("type"))) {
			return JsonUtil.getFailJson("类型不能为空");
		}
		if (SysUtil.isNull(param.get("content"))) {
			return JsonUtil.getFailJson("内容不能为空");
		}
		if (!SysUtil.betweenLength(param.get("from"), 0, 80)) {
			return JsonUtil.getFailJson("出处在1~80个字之间");
		}
		if (!SysUtil.betweenLength(param.get("url"), 0, 80)) {
			return JsonUtil.getFailJson("引用连接在1~80个字之间");
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) getSession().getAttribute(SysUtil.LOGIN_SESSION);
		param.put("userId", user.get("USER_ID").toString());
		cntInfoService.updateCntInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("del")
	public @ResponseBody JSONObject del() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("cntId")) || !SysUtil.isNum(param.get("cntId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		cntInfoService.deleteCntInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("status")
	public @ResponseBody JSONObject status() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("cntId")) || !SysUtil.isNum(param.get("cntId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		if (SysUtil.isNull(param.get("status")) || !DictUtil.contain("CNT_STATUS", param.get("status"))) {
			return JsonUtil.getFailJson("状态不能为空");
		}
		cntInfoService.deleteCntInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("pie")
	public @ResponseBody Callable<JSONObject> cntCount() {
		Map<String, String> param = getParameterMap();
		param.put("month", new DateTime().getMonthOfYear()+"");
		return () -> {
			JSONObject json = new JSONObject();
			List<Map<String, Object>> list = cntInfoService.selectCntCountByUserId(param);
			Map<String, Object> pieMap = announInfoService.selectAnnounCountByUserId(param);
			if(Integer.parseInt(pieMap.get("value").toString())!=0) {
				pieMap.put("name", "通知公告");
				list.add(pieMap);
			}
			if(SysUtil.isNull(list)) {
				return JsonUtil.getFailJson("没有匹配的数据");
			}
			json.put("data", list);
			return json;
		};
	}
	
	@RequestMapping("line")
	public @ResponseBody Callable<JSONObject> allMonth(){
		Map<String, String> param = getParameterMap();
		param.put("year", new DateTime().getYear()+"");
		return () -> {
			JSONObject json = new JSONObject();
			Map<String, Object> conMap = cntInfoService.selectCntCountByAllMonth(param);
			Map<String, Object> annMap = announInfoService.selectAnnounCountByAllMonth(param);
			if(SysUtil.isNull(conMap)) {
				return JsonUtil.getFailJson("没有匹配数据");
			}
			//根据Key排序
			Map<String, Object> con = sortByKey(conMap);
			Map<String, Object> ann = sortByKey(annMap);
			//整理数据结构，适应线图数据结构标准
			//将Map中KEY值标识的月份转换成list，用作线图X轴刻度值
			List<String> keyList = con.entrySet().stream().map(m->getMonth(m.getKey())).collect(Collectors.toList());
			//将Map中Value值标识的数量转换成list，用作线图各点坐标的Y轴值
			List<Integer> conValList = con.entrySet().stream().map(m->Integer.parseInt(m.getValue().toString())).collect(Collectors.toList());
			List<Integer> annValList = ann.entrySet().stream().map(m->Integer.parseInt(m.getValue().toString())).collect(Collectors.toList());
			//文章、通知公告查询出的没有数量相加
			List<Integer> valList = IntStream.range(0, conValList.size())
                    .map(i -> conValList.get(i) + annValList.get(i))
                    .boxed()
                    .collect(Collectors.toList());
			Map<String, List<?>> temp = Maps.newHashMap();
			temp.put("key", keyList);
			temp.put("value", valList);
			json.put("data", temp);
			return json;
		};
	}
	
	/**
	 * 根据Key值排序
	 * @param map
	 * @return
	 */
	private Map<String, Object> sortByKey(Map<String, Object> map){
		return map.entrySet().stream()
				  .sorted(Map.Entry.comparingByKey())
				  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						   (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}
	
	private String getMonth(String s) {
		String month = "";
		switch(s) {
		case "a":
			month="一月";
			break;
		case "b":
			month="二月";
			break;
		case "c":
			month="三月";
			break;
		case "d":
			month="四月";
			break;
		case "e":
			month="五月";
			break;
		case "f":
			month="六月";
			break;
		case "g":
			month="七月";
			break;
		case "h":
			month="八月";
			break;
		case "i":
			month="九月";
			break;
		case "j":
			month="十月";
			break;
		case "k":
			month="十一月";
			break;
		case "l":
			month="十二月";
			break;
		}
		return month;
	}
}
