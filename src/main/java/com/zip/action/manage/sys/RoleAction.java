package com.zip.action.manage.sys;

import static com.zip.util.SysUtil.betweenLength;
import static com.zip.util.SysUtil.isNull;
import static com.zip.util.SysUtil.isNum;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.zip.action.BaseAction;
import com.zip.service.PermissionInfoService;
import com.zip.service.RoleInfoService;
import com.zip.util.JsonUtil;
import com.zip.util.PermissionUtil;
import com.zip.util.SysUtil;

@Controller
@RequestMapping("role")
public class RoleAction extends BaseAction {
	
	@Autowired private RoleInfoService roleInfoService;
	@Autowired private PermissionInfoService permissionInfoService;

	@RequestMapping("index")
	public String index() {
		return "/manage/sys/role/index.jsp";
	}
	
	@RequestMapping("list")
	public @ResponseBody Callable<JSONObject> list() throws Exception {
		Map<String, String> param = getParameterMapWithPageInfo();
		if (!SysUtil.isNull(param.get("roleName"))) {
			param.put("roleName", URLDecoder.decode(param.get("roleName"), SysUtil.ENCODE));
		}
		return () -> {
			JSONObject json = new JSONObject();
			List<Map<String, Object>> list = roleInfoService.selectRoleBySearch(param);
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
		return "/manage/sys/role/add.jsp";
	}
	
	@RequestMapping("add")
	public @ResponseBody JSONObject add() {
		Map<String, String> param = getParameterMap();
		if (!betweenLength(param.get("roleName"), 1, 20)) {
			return JsonUtil.getFailJson("角色名称不能为空，并且长度在1~20之间");
		}
		if (roleInfoService.selectRoleCountByNameAndNotId(param) > 0) {
			return JsonUtil.getFailJson("该角色名称已存在");
		}
		if (!betweenLength(param.get("note"), 0, 200)) {
			return JsonUtil.getFailJson("备注信息长度在0~200之间");
		}
		int roleId = roleInfoService.insertRole(param);
		
		Map<String, String> role = Maps.newHashMap();
		role.put("roleId", roleId + "");
		role.put("note", param.get("note"));
		role.put("roleName", param.get("roleName"));
		
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("editPage")
	public String editPage() {
		Map<String, String> param = getParameterMap();
		if (isNull(param.get("roleId")) || !isNum(param.get("roleId"))) {
			JsonUtil.writeJson(response, JsonUtil.getFailJson("ID不能为空"));
			return null;
		}
		request.setAttribute("data", roleInfoService.selectRoleById(param));
		return "/manage/sys/role/edit.jsp";
	}
	
	@RequestMapping("edit")
	public @ResponseBody JSONObject edit() {
		Map<String, String> param = getParameterMap();
		if (isNull(param.get("roleId")) || !isNum(param.get("roleId"))) {
			return JsonUtil.getFailJson("角色不能为空");
		}
		if (!betweenLength(param.get("roleName"), 1, 20)) {
			return JsonUtil.getFailJson("角色名称不能为空，并且长度在1~20之间");
		}
		if (roleInfoService.selectRoleCountByNameAndNotId(param) > 0) {
			return JsonUtil.getFailJson("该角色名称已存在");
		}
		if (!betweenLength(param.get("note"), 0, 200)) {
			return JsonUtil.getFailJson("备注信息长度在0~200之间");
		}
		roleInfoService.updateRoleById(param);
		
		Map<String, String> role = Maps.newHashMap();
		role.put("roleId", param.get("roleId"));
		role.put("note", param.get("note"));
		role.put("roleName", param.get("roleName"));
		
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("del")
	public @ResponseBody JSONObject del() {
		Map<String, String> param = getParameterMap();
		if (isNull(param.get("roleId")) || !isNum(param.get("roleId"))) {
			return JsonUtil.getFailJson("角色不能为空");
		}
		if (roleInfoService.selectUserCountByRole(param) > 0) {
			return JsonUtil.getFailJson("有用户绑定在此角色上，不能删除");
		}
		
		roleInfoService.deleteRoleById(param);
		roleInfoService.deleteRolePerById(param);
		
		return JsonUtil.getSucJson();
	}
	
	@RequestMapping("per")
	public String per() {
		Map<String, String> param = getParameterMap();
		if (isNull(param.get("roleId")) || !isNum(param.get("roleId"))) {
			JsonUtil.writeJson(response, JsonUtil.getFailJson("ID不能为空"));
			return null;
		}
		return "/manage/sys/role/per.jsp";
	}
	
	@RequestMapping("perList")
	public @ResponseBody Callable<JSONArray> perList() {
		Map<String, String> param = getParameterMap();
		if (isNull(param.get("roleId")) || !isNum(param.get("roleId"))) {
			return () -> new JSONArray();
		}
		List<Map<String, Object>> allMenuList = PermissionUtil.getAllPermission();
		
		return () -> {
			List<Map<String, Object>> rolePerList = permissionInfoService.selectPermissionByRoleId(param);
			List<String> perList = Lists.newArrayList();
			rolePerList.stream().forEach(role -> {
				perList.add(role.get("PER_NAME").toString());
			});
			JSONArray treeRoot = new JSONArray();
			
			// 整理根节点
			List<Map<String, Object>> rootList = allMenuList.stream().filter(root -> (SysUtil.isNull(root.get("PER_PARENT")) || Ints.tryParse(root.get("PER_PARENT").toString()) < 1))
					.sorted((r1, r2) -> Integer.valueOf(r1.get("PER_SORT").toString()).compareTo(Integer.valueOf(r2.get("PER_SORT").toString())))
					.collect(Collectors.toList());
			
			rootList.forEach(root -> {
				JSONObject jsonRoot = new JSONObject();
				jsonRoot.put("title", root.get("PER_TITLE"));
				if (perList.contains(root.get("PER_NAME").toString())) {
					jsonRoot.put("checked", true);
				} else {
					jsonRoot.put("checked", false);
				}
				if (!SysUtil.isNull(param.get("type")) && param.get("type").equals("1")) {
					jsonRoot.put("disabled", true);
				}
				jsonRoot.put("value", root.get("PER_ID"));
				
				JSONArray treeMenu =  new JSONArray();
				
				// 整理菜单节点
				List<Map<String, Object>> menuList = allMenuList.stream().filter(menu -> root.get("PER_ID").toString().equals(menu.get("PER_PARENT").toString()))
						.sorted((r1, r2) -> Integer.valueOf(r1.get("PER_SORT").toString()).compareTo(Integer.valueOf(r2.get("PER_SORT").toString())))
						.collect(Collectors.toList());
				
				menuList.forEach(menu -> {
					JSONObject jsonMenu = new JSONObject();
					jsonMenu.put("title", menu.get("PER_TITLE"));
					if (perList.contains(menu.get("PER_NAME").toString())) {
						jsonMenu.put("checked", true);
					} else {
						jsonMenu.put("checked", false);
					}
					if (!SysUtil.isNull(param.get("type")) && param.get("type").equals("1")) {
						jsonMenu.put("disabled", true);
					}
					jsonMenu.put("value", menu.get("PER_ID"));
					JSONArray treePer =  new JSONArray();
					
					// 整理权限节点
					List<Map<String, Object>> perMenuList = allMenuList.stream().filter(per -> menu.get("PER_ID").toString().equals(per.get("PER_PARENT").toString()))
							.sorted((r1, r2) -> Integer.valueOf(r1.get("PER_SORT").toString()).compareTo(Integer.valueOf(r2.get("PER_SORT").toString())))
							.collect(Collectors.toList());
					
					perMenuList.forEach(per -> {
						JSONObject jsonPer = new JSONObject();
						jsonPer.put("title", per.get("PER_TITLE"));
						if (perList.contains(per.get("PER_NAME").toString())) {
							jsonPer.put("checked", true);
						} else {
							jsonPer.put("checked", false);
						}
						if (!SysUtil.isNull(param.get("type")) && param.get("type").equals("1")) {
							jsonPer.put("disabled", true);
						}
						jsonPer.put("value", per.get("PER_ID"));
						jsonPer.put("data", new JSONArray());
						
						treePer.add(jsonPer);
					});
					
					jsonMenu.put("data", treePer);
					treeMenu.add(jsonMenu);
					
				});
				
				jsonRoot.put("data", treeMenu);
				treeRoot.add(jsonRoot);
			});
			
			return treeRoot;
		};
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("perEdit")
	public @ResponseBody Callable<JSONObject> perEdit() {
		Map<String, String> param = getParameterMap();
		if (isNull(param.get("roleId")) || !isNum(param.get("roleId"))) {
			return () -> JsonUtil.getFailJson("角色不能为空");
		}
		if (isNull(param.get("ids"))) {
			return () -> JsonUtil.getFailJson("选择的权限不能为空");
		}
		if (isNull(roleInfoService.selectRoleById(param))) {
			return () -> JsonUtil.getFailJson("角色不存在或已被删除");
		}
		return () -> {
			// 先删除旧关系，再添加新关系，两个事务不能放在一起
			roleInfoService.deleteRolePerById(param);
			roleInfoService.insertRolePermission(param.get("roleId"), 
					Stream.of(param.get("ids").split(",")).parallel()
							.filter(s -> !isNull(s)).distinct()
							.map(s -> Ints.tryParse(s))
							.collect(Collectors.toList()));
			// 清理当前角色已登录的用户信息，让用户重新登录
			getAllLoginSession().stream().map(s -> (Map<String, Object>) s.getAttribute(SysUtil.LOGIN_SESSION))
				.filter(user -> user.get("ROLE_ID").toString().equals(param.get("roleId")))
				.map(user -> user.get("SESSIONID").toString())
				.collect(Collectors.toList())
				.forEach(sid -> getSubject(sid).logout());
			
			return JsonUtil.getSucJson();
		};
	}
}
