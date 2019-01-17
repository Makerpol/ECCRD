package com.zip.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.MtInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class MtInfoService {

	@Autowired private MtInfoMapper mtInfoMapper;
	
	/**
	 * 通过检索框参数查询
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectMtInfoBySearch(Map<String, String> param) {
		return mtInfoMapper.selectMtInfoBySearch(param);
	}
	
	/**
	 * 通过模块ID查询
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectMtInfoById(Map<String, String> param){
		return mtInfoMapper.selectMtInfoById(param);
	}
	
	/**
	 * 获取所有的一级栏目
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectAllParentMt(){
		return mtInfoMapper.selectAllParentMt();
	}
	
	/**
	 * 添加模块
	 * @return
	 */
	public int insertMtInfo(Map<String, String> param) {
		mtInfoMapper.insertMtInfo(param);
		return mtInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 通过模块ID更新模块信息
	 * @param param
	 */
	public void updateMtInfoById(Map<String, String> param) {
		mtInfoMapper.updateMtInfoById(param);
	}
	
	/**
	 * 通过模块ID刪除模块信息
	 * @param param
	 */
	public void deleteMtInfoById(Map<String, String> param) {
		mtInfoMapper.deleteMtInfoById(param);
	}
	
	/**
	 * 通过父级ID获取所有的子栏目
	 * @param param
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectMtInfoByParentId(Map<String, String> param) {
		return mtInfoMapper.selectMtInfoByParentId(param);
	}
}
