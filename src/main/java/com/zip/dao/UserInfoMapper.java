package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {

	/**
	 * 通过用户名查询用户信息
	 * @return
	 */
	public Map<String, Object> selectUserByName(Map<String, String> param);
	
	/**
	 * 通过ID查询用户信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectUserById(Map<String, String> param);
	
	/**
	 * 检测sn是否已存在
	 * @param param
	 * @return
	 */
	public int selectUserByNameCount(Map<String, String> param);
	
	/**
	 * 搜索用户信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectUserBySearch(Map<String, String> param);
	
	/**
	 * 更新用户登录时间
	 * @param param
	 */
	public void updateUserLastLoginTime(Map<String, String> param);
	
	/**
	 * 添加用户
	 * @param param
	 * @return
	 */
	public void insertUser(Map<String, String> param);
	
	/**
	 * 获取最后一次插入的ID
	 * @return
	 */
	public int selectLastInsertID();
	
	/**
	 * 更新用户状态
	 * @param param
	 */
	public void updateUserStatus(Map<String, String> param);
	
	/**
	 * 更新用户信息
	 * @param param
	 */
	public void updateUser(Map<String, String> param);
	
	/**
	 * 通过用户ID更新密码
	 * @param param
	 */
	public void updatePassById(Map<String, String> param);
}
