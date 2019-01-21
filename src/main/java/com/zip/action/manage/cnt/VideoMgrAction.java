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
import com.zip.service.VideoInfoService;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("videoMgr")
public class VideoMgrAction extends BaseAction{
	@Autowired VideoInfoService videoInfoService;
	
	@RequestMapping("index")
	public String index() {
		return "/manage/cnt/video/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception{
		Map<String, String> param = getParameterMapWithPageInfo();
		if (!SysUtil.isNull(param.get("title"))) {
			param.put("title", URLDecoder.decode(param.get("title"), SysUtil.ENCODE));
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
			List<Map<String, Object>> videoList = videoInfoService.selectVideoInfoBySearch(param);
			if (SysUtil.isNull(videoList)) {
				return JsonUtil.getFailJson("没有匹配的数据");
			}
			json.put("data", videoList);
			json.put("count", param.get("total"));
			json.putAll(JsonUtil.getSucJson());
			return json;
		};
	}
	
	@RequestMapping("addPage")
	public String addPage() {
		return "/manage/cnt/video/add.jsp";
	}
	
	@RequestMapping("add")
	public @ResponseBody JSONObject add() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("fileId"))) {
			return JsonUtil.getFailJson("视频文件不能为空！");
		}
		if(SysUtil.isNull(param.get("title"))) {
			return JsonUtil.getFailJson("视频标题不能为空！");
		}
		videoInfoService.insertVideoInfo(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("editPage")
	public String editPage() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("videoId"))||!SysUtil.isNum(param.get("videoId"))) {
			JsonUtil.writeStr(response, "视频ID不能为空！");
			return null;
		}
		request.setAttribute("data", videoInfoService.selectVideoInfoById(param));
		return "/manage/cnt/video/edit.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit(){
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("videoId"))||!SysUtil.isNum(param.get("videoId"))) {
			return JsonUtil.getFailJson("视频ID不能为空！");
		}
		if(SysUtil.isNull(param.get("title"))) {
			return JsonUtil.getFailJson("标题不能为空！");
		}
		
		videoInfoService.updateVideoInfoById(param);
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("del")
	public @ResponseBody JSONObject del() {
		Map<String, String> param  = getParameterMap();
		if(SysUtil.isNull(param.get("videoId"))||!SysUtil.isNum(param.get("videoId"))) {
			return JsonUtil.getFailJson("视频ID不能为空！");
		}
		videoInfoService.deleteVideoInfoById(param);
		return JsonUtil.getSucJson();
	}
}
