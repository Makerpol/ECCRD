package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface PermissionInfoMapper {

	/**
	 * 通过角色ID列表查询权限
	 * @return
	 */
	public List<Map<String, Object>> selectPermissionByRoleList(List<Integer> list);
	
	/**
	 * 查询所有的权限和菜单信息
	 * @return
	 */
	public List<Map<String, Object>> selectPermission();

	/**
	 * 通过用户ID查询权限列表
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectPermissionByUserId(Map<String, String> param);
	
	/**
	 * 通过角色id查询用户权限信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectPermissionByRoleId(Map<String, String> param);
}
