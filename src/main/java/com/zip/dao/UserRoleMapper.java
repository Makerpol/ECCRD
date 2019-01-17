package com.zip.dao;

import java.util.Map;

public interface UserRoleMapper {

	/**
	 * 插入用户角色关系
	 * @return
	 */
	public void insertUserRole(Map<String, String> param);
	
	/**
	 * 搜索用户信息
	 * @param param
	 * @return
	 */
	public void deleteUserRoleByUser(Map<String, String> param);

}
