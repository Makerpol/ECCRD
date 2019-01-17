package com.zip.action.manage.sys;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.primitives.Ints;
import com.zip.action.BaseAction;
import com.zip.service.RoleInfoService;
import com.zip.service.UserInfoService;
import com.zip.service.UserRoleService;
import com.zip.util.DictUtil;
import com.zip.util.JsonUtil;
import com.zip.util.MD5Util;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("user")
public class UserAction extends BaseAction {
	
	@Autowired private RoleInfoService roleInfoService;
	@Autowired private UserInfoService userInfoService;
	@Autowired private UserRoleService userRoleService;

	@RequestMapping("index")
	public String index() {
		request.setAttribute("status", DictUtil.get("USER_STATUS"));
		request.setAttribute("roles", roleInfoService.selectRole());
		return "/manage/sys/user/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception {
		Map<String, String> param = getParameterMapWithPageInfo();
		if (!SysUtil.isNull(param.get("userName"))) {
			param.put("userName", URLDecoder.decode(param.get("userName"), SysUtil.ENCODE));
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
			List<Map<String, Object>> list = userInfoService.selectUserBySearch(param);
			if (SysUtil.isNull(list)) {
				return JsonUtil.getFailJson("没有匹配的数据");
			}
			json.put("data", list);
			json.put("count", param.get("total"));
			json.putAll(JsonUtil.getSucJson());
			return json;
		};
	}
	
	@RequestMapping("status")
	public @ResponseBody JSONObject status() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("userId")) || !SysUtil.isNum(param.get("userId"))) {
			return JsonUtil.getFailJson("用户ID不能为空");
		}
		if (SysUtil.isNull(param.get("status")) || !DictUtil.contain("USER_STATUS", param.get("status"))) {
			return JsonUtil.getFailJson("状态不能为空");
		}
		userInfoService.updateUserStatus(param);

		// 不是正常状态
		if (!param.get("status").equals("0")) {
			Map<String, Object> theUser = getLoginUserByUserId(Ints.tryParse(param.get("userId")));
			if(theUser!=null) {
				getSubject(theUser.get("SESSIONID").toString()).logout();
			}
		}
		
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("addPage")
	public String addPage() {
		request.setAttribute("roles", roleInfoService.selectRole());
		return "/manage/sys/user/add.jsp";
	}
	
	/**
	 * 添加用户信息
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody JSONObject add() {
		Map<String, String> param = getParameterMap();
		// 用户名必须在0~20个字符之间
		if (!SysUtil.betweenLength(param.get("userName"), 1, 20)) {
			return JsonUtil.getFailJson("账号不能为空，并且长度在1~20之间");
		}
		// 角色不能为空
		if (SysUtil.isNull(param.get("roleId")) || !SysUtil.isNum(param.get("roleId"))) {
			return JsonUtil.getFailJson("角色不能为空");
		}
		if (!SysUtil.betweenLength(param.get("note"), 0, 200)) {
			return JsonUtil.getFailJson("备注信息长度在0~200之间");
		}
		// 验证用户是否存在
		if (!SysUtil.isNull(userInfoService.selectUserByName(param))) {
			return JsonUtil.getFailJson("该用户信息已存在");
		}
		// 默认密码111111
		param.put("userPass", Optional.ofNullable(param.get("userPass")).filter(s -> !SysUtil.isNull(s))
				.map(s -> MD5Util.md5(s)).orElse(MD5Util.md5("111111")));
		// 创建用户
		int userId = userInfoService.insertUser(param);
		param.put("userId", userId + "");
		
		// 建立角色关系
		userRoleService.insertUserRole(param);
		return JsonUtil.getSucJson();
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	@RequestMapping("editPage")
	public String editPage() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("userId")) || !SysUtil.isNum(param.get("userId"))) {
			JsonUtil.writeJson(response, JsonUtil.getFailJson("ID不能为空"));
			return null;
		}
		request.setAttribute("roles", roleInfoService.selectRole());
		request.setAttribute("data", userInfoService.selectUserById(param));
		return "/manage/sys/user/edit.jsp";
	}
	
	/**
	 * 编辑用户信息
	 * @return
	 */
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("userId")) || !SysUtil.isNum(param.get("userId"))) {
			return JsonUtil.getFailJson("用户ID不能为空");
		}
		// 角色不能为空
		if (SysUtil.isNull(param.get("roleId")) || !SysUtil.isNum(param.get("roleId"))) {
			return JsonUtil.getFailJson("角色不能为空");
		}
		if (!SysUtil.betweenLength(param.get("note"), 0, 200)) {
			return JsonUtil.getFailJson("备注信息长度在0~200字之间");
		}
		Map<String, Object> theUser = userInfoService.selectUserById(param);
		Map<String, Object> loginUser = getLoginUserByUserId(Ints.tryParse(theUser.get("USER_ID").toString()));
		if (!SysUtil.isNull(loginUser) && !loginUser.get("ROLE_ID").toString().equals(param.get("roleId"))) {
			getSubject(loginUser.get("SESSIONID").toString()).logout();
		}
		
		// 更新用户信息
		userInfoService.updateUser(param);
		// 先删除旧关系
		userRoleService.deleteUserRoleByUser(param);
		// 建立角色关系
		userRoleService.insertUserRole(param);
		return JsonUtil.getSucJson();
	}
	
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping("rePwsPage")
	public String rePwsPage() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("userId")) || !SysUtil.isNum(param.get("userId"))) {
			JsonUtil.writeJson(response, JsonUtil.getFailJson("ID不能为空"));
			return null;
		}
		request.setAttribute("data", userInfoService.selectUserById(param));
		return "/manage/sys/user/rePws.jsp";
	} 
	
	@RequestMapping("rePws")
	public @ResponseBody JSONObject rePws() {
		Map<String, String> param = getParameterMap();
		if(SysUtil.isNull(param.get("userId")) || !SysUtil.isNum(param.get("userId"))) {
			return JsonUtil.getFailJson("ID不能为空");
		}
		if(SysUtil.isNull(param.get("userPass")) || !SysUtil.betweenLength(param.get("userPass"), 1, 20)) {
			return JsonUtil.getFailJson("密码不能为空，并且长度在1-20之间");
		}
		param.put("userPass", MD5Util.md5(param.get("userPass")));
		userInfoService.updatePassById(param);
		return JsonUtil.getSucJson();
	}
}
