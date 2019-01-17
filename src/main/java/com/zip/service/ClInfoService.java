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
import com.zip.dao.ClInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class ClInfoService {

	@Autowired private ClInfoMapper clInfoMapper;
	@Autowired private SqlSessionFactory sqlSessionFactory;
	@Autowired private MybatisUtil mybatisUtil;
	
	/**
	 * 查询轮播
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectClInfoBySearch(Map<String, String> param) {
		return clInfoMapper.selectClInfoBySearch(param);
	}
	
	/**
	 * 通过类型获取轮播列表
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectClInfoByType(Map<String, String> param) {
		return clInfoMapper.selectClInfoByType(param);
	}
	
	/**
	 * 通过ID查询轮播信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectClInfoById(Map<String, String> param) {
		return clInfoMapper.selectClInfoById(param);
	}
	
	/**
	 * 插入轮播信息
	 * @param param
	 */
	public int insertClInfo(Map<String, String> param) {
		clInfoMapper.insertClInfo(param);
		return clInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 通过ID更新轮播信息
	 * @param param
	 */
	public void updateClInfoById(Map<String, String> param) {
		clInfoMapper.updateClInfoById(param);
	}
	
	/**
	 * 通过ID删除轮播信息
	 * @param param
	 */
	public void deleteClInfoById(Map<String, String> param) {
		clInfoMapper.deleteClInfoById(param);
	}
	
	/**
	 * 插入角色和权限对应关系
	 * @param param
	 */
	public void insertClList(String type, List<Integer> fileList) {
		List<Map<String, String>> paramList = Lists.newArrayList();
		for (Integer fileId : fileList) {
			Map<String, String> map = Maps.newHashMap();
			map.put("type", type);
			map.put("fileId", fileId.toString());
			paramList.add(map);
		}
		mybatisUtil.executeBatchSql(sqlSessionFactory, "com.zip.dao.ClInfoMapper.insertClInfo", paramList);
	}
}
