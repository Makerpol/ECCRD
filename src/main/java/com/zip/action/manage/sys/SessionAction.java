package com.zip.action.manage.sys;

import static com.zip.util.SysUtil.isNull;
import static com.zip.util.SysUtil.isNum;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.zip.action.BaseAction;
import com.zip.service.RoleInfoService;
import com.zip.util.JsonUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("session")
public class SessionAction extends BaseAction {
	
	@Autowired private RoleInfoService roleInfoService;
	
	@RequestMapping("index")
	public String index() {
		request.setAttribute("roles", roleInfoService.selectRole());
		return "/manage/sys/session/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception {
		Map<String, String> param = getParameterMapWithPageInfo();
		if (!SysUtil.isNull(param.get("userName"))) {
			param.put("userName", URLDecoder.decode(param.get("userName"), SysUtil.ENCODE));
		}
		return () -> {
			
			int page = Ints.tryParse(param.get("page"));
			if (page < 0) {
				page = 1;
			}
			int rows = Ints.tryParse(param.get("rows"));
			int start = (page - 1) * rows;
			
			// 整理符合要求的在线用户列表列表
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> userList = getAllLoginSession().stream()
				.filter(s -> !SysUtil.isNull(s))
				.map(s -> (Map<String, Object>) s.getAttribute(SysUtil.LOGIN_SESSION))
				.filter(u -> {
					if (!SysUtil.isNull(param.get("userName"))) {
						if (u.get("USER_NAME").toString().toUpperCase().contains(param.get("userName").toUpperCase())) {
							return true;
						} else {
							return false;
						}
					}
					return true;
				})
				.filter(u -> {
					if (!SysUtil.isNull(param.get("roleId")) && SysUtil.isNum(param.get("roleId"))) {
						if (u.get("ROLE_ID").toString().equals(param.get("roleId"))) {
							return true;
						} else {
							return false;
						}
					}
					return true;
				})
				.sorted((u1, u2) -> {
					if (SysUtil.isNull(param.get("order")) || param.get("order").equalsIgnoreCase("desc")) {
						return u2.get(param.get("sort")).toString().compareTo(u1.get(param.get("sort")).toString());
					} else {
						return u1.get(param.get("sort")).toString().compareTo(u2.get(param.get("sort")).toString());
					}
				}).collect(Collectors.toList());
			
			JSONObject json = new JSONObject();
			if (SysUtil.isNull(userList)) {
				return JsonUtil.getFailJson("没有匹配的数据");
			}
			
			json.put("data", userList.parallelStream()
								.skip(start).limit(rows)
								.collect(Collectors.toList()));
			json.put("count", getLoginSessionSize());
			json.putAll(JsonUtil.getSucJson());
			return json;
		};
	}
	
	/**
	 * 强制下线
	 * @return
	 */
	@RequestMapping("logout")
	public @ResponseBody JSONObject logout() {
		Map<String, String> param = getParameterMap();
		if (isNull(param.get("userId")) || !isNum(param.get("userId"))) {
			return JsonUtil.getFailJson("用户为空或没有登录");
		}
		Optional.ofNullable(getLoginSessionByUserId(Ints.tryParse(param.get("userId")))).orElse(Lists.newArrayList()).stream().forEach(s -> {
			Subject subject = getSubject(s);
			subject.logout();
			enterpriseCacheSessionDAO.delete(s);
		});
		return JsonUtil.getSucJson();
	}
}
