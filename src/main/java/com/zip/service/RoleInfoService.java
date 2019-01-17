package com.zip.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zip.component.MybatisUtil;
import com.zip.dao.RoleInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class RoleInfoService {

	@Autowired private RoleInfoMapper roleInfoMapper;
	@Autowired private SqlSessionFactory sqlSessionFactory;
	@Autowired private MybatisUtil mybatisUtil;
	
	/**
	 * 通过用户名查询角色信息
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectRoleByName(Map<String, String> param) {
		return roleInfoMapper.selectRoleByName(param);
	}
	
	/**
	 * 获取所有角色
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectRole() {
		return roleInfoMapper.selectRole();
	}
	
	/**
	 * 通过名称查询
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectRoleBySearch(Map<String, String> param) {
		return roleInfoMapper.selectRoleBySearch(param);
	}
	
	/**
	 * 通过角色名称查询角色数量
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int selectRoleCountByNameAndNotId(Map<String, String> param) {
		return roleInfoMapper.selectRoleCountByNameAndNotId(param);
	}

	/**
	 * 通过ID查询角色信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectRoleById(Map<String, String> param) {
		return roleInfoMapper.selectRoleById(param);
	}
	
	/**
	 * 插入角色信息
	 */
	public int insertRole(Map<String, String> param) {
		roleInfoMapper.insertRole(param);
		return roleInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 通过角色查询用户数量
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int selectUserCountByRole(Map<String, String> param) {
		return roleInfoMapper.selectUserCountByRole(param);
	}
	
	/**
	 * 通过ID更新角色信息
	 * @param param
	 */
	public void updateRoleById(Map<String, String> param) {
		roleInfoMapper.updateRoleById(param);
	}
	
	/**
	 * 通过ID删除角色信息
	 * @param param
	 */
	public void deleteRoleById(Map<String, String> param) {
		roleInfoMapper.deleteRoleById(param);
	}
	
	/**
	 * 删除对应关系
	 * @param param
	 */
	public void deleteRolePerById(Map<String, String> param) {
		roleInfoMapper.deleteRolePerById(param);
	}
	
	/**
	 * 插入角色和权限对应关系
	 * @param param
	 */
	public void insertRolePermission(String roleId, List<Integer> perList) {
		List<Map<String, String>> paramList = Lists.newArrayList();
		for (Integer perId : perList) {
			Map<String, String> map = Maps.newHashMap();
			map.put("roleId", roleId);
			map.put("perId", perId.toString());
			paramList.add(map);
		}
		mybatisUtil.executeBatchSql(sqlSessionFactory, "com.zip.dao.RoleInfoMapper.insertRolePermission", paramList);
	}
}
