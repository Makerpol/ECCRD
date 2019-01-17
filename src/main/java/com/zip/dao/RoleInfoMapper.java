package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface RoleInfoMapper {

	/**
	 * 通过用户名查询角色信息
	 * @return
	 */
	public List<Map<String, Object>> selectRoleByName(Map<String, String> param);
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Map<String, Object>> selectRole();
	
	/**
	 * 通过角色名称查询角色数量
	 * @return
	 */
	public int selectRoleCountByNameAndNotId(Map<String, String> param);
	
	/**
	 * 通过名称查询
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectRoleBySearch(Map<String, String> param);
	
	/**
	 * 通过ID查询角色信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectRoleById(Map<String, String> param);
	
	/**
	 * 插入角色信息
	 */
	public void insertRole(Map<String, String> param);
	
	public int selectLastInsertID();
	
	/**
	 * 通过ID更新角色信息
	 * @param param
	 */
	public void updateRoleById(Map<String, String> param);
	
	/**
	 * 通过ID删除角色信息
	 * @param param
	 */
	public void deleteRoleById(Map<String, String> param);
	
	/**
	 * 通过ID删除角色和权限对应关系
	 * @param param
	 */
	public void deleteRolePerById(Map<String, String> param);
	
	/**
	 * 通过角色查询用户数量
	 * @param param
	 * @return
	 */
	public int selectUserCountByRole(Map<String, String> param);
	
	/**
	 * 插入角色和权限对应关系
	 * @param param
	 */
	public void insertRolePermission(Map<String, String> param);
	
}
