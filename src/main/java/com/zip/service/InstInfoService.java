package com.zip.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.InstInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class InstInfoService  {

	@Autowired private InstInfoMapper instInfoMapper;
	
	/**
	 * 查询文本信息
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectInstBySearch(Map<String, String> param) {
		return instInfoMapper.selectInstBySearch(param);
	}
	
	/**
	 * 通过ID获取文本信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectInstById(Map<String, String> param) {
		return instInfoMapper.selectInstById(param);
	}
	
	/**
	 * 插入文本信息
	 * @param param
	 */
	public int insertInstInfo(Map<String, String> param) {
		instInfoMapper.insertInstInfo(param);
		return instInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 通过ID修改文本信息
	 * @param param
	 */
	public void updateInstInfoById(Map<String, String> param) {
		instInfoMapper.updateInstInfoById(param);
	}
	
	/**
	 * 通过ID删除文本信息
	 * @param param
	 */
	public void deleteInstInfoById(Map<String, String> param) {
		instInfoMapper.deleteInstInfoById(param);
	}
	
	/**
	 * 前端获取文本信息列表
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectInstByList(Map<String, String> param) {
		return instInfoMapper.selectInstByList(param);
	}
}
