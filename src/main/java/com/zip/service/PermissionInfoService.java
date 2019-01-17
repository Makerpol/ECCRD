package com.zip.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.PermissionInfoMapper;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PermissionInfoService {

	@Autowired private PermissionInfoMapper permissionInfoMapper;
	
	/**
	 * 通过角色ID列表查询权限
	 * @return
	 */
	
	public List<Map<String, Object>> selectPermissionByRoleList(List<Integer> list) {
		return permissionInfoMapper.selectPermissionByRoleList(list);
	}
	
	/**
	 * 查询所有的权限和菜单信息
	 * @return
	 */
	public List<Map<String, Object>> selectPermission() {
		return permissionInfoMapper.selectPermission();
	}
	
	/**
	 * 通过用户ID查询权限列表
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectPermissionByUserId(Map<String, String> param) {
		return permissionInfoMapper.selectPermissionByUserId(param);
	}
	
	/**
	 * 通过角色id查询用户权限信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectPermissionByRoleId(Map<String, String> param) {
		return permissionInfoMapper.selectPermissionByRoleId(param);
	}
}
