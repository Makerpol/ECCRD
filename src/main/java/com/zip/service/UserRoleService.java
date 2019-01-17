package com.zip.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.UserRoleMapper;

/**
 * 
 * @title UserRoleService.java
 * @author ssk
 * @date 2017年12月18日
 * @description 
 *
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UserRoleService {

	@Autowired private UserRoleMapper userRoleMapper;
	
	/**
	 * 插入用户角色关系
	 * @return
	 */
	public void insertUserRole(Map<String, String> param) {
		userRoleMapper.insertUserRole(param);
	}
	
	/**
	 * 删除用户角色关系
	 * @param param
	 * @return
	 */
	public void deleteUserRoleByUser(Map<String, String> param) {
		userRoleMapper.deleteUserRoleByUser(param);
	}
}
