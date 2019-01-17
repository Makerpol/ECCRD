package com.zip.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.CntInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class CntInfoService  {

	@Autowired private CntInfoMapper cntInfoMapper;
	
	/**
	 * 查询文本信息
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectCntBySearch(Map<String, String> param) {
		return cntInfoMapper.selectCntBySearch(param);
	}
	
	/**
	 * 通过ID获取文本信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectCntById(Map<String, String> param) {
		return cntInfoMapper.selectCntById(param);
	}
	
	/**
	 * 插入文本信息
	 * @param param
	 */
	public int insertCntInfo(Map<String, String> param) {
		cntInfoMapper.insertCntInfo(param);
		return cntInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 通过ID修改状态
	 * @param param
	 */
	public void updateStatusById(Map<String, String> param) {
		cntInfoMapper.updateStatusById(param);
	}
	
	/**
	 * 根据ID更新浏览次数
	 * @param param
	 */
	public void updateCountById(Map<String, String> param) {
		cntInfoMapper.updateCountById(param);
	}
	
	/**
	 * 通过ID修改文本信息
	 * @param param
	 */
	public void updateCntInfoById(Map<String, String> param) {
		cntInfoMapper.updateCntInfoById(param);
	}
	
	/**
	 * 通过ID删除文本信息
	 * @param param
	 */
	public void deleteCntInfoById(Map<String, String> param) {
		cntInfoMapper.deleteCntInfoById(param);
	}
	
	/**
	 * 前端获取文本信息列表
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectCntByList(Map<String, String> param) {
		return cntInfoMapper.selectCntByList(param);
	}
	
	/**
	 * 根据操作人Id查询本月各个栏目上传文章数量
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCntCountByUserId(Map<String, String> param){
		return cntInfoMapper.selectCntCountByUserId(param);
	}
	
	/**
	 * 根据操作人ID以及当前年份，获取本年度每月上传文章数量
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCntCountByAllMonth(Map<String, String> param){
		return cntInfoMapper.selectCntCountByAllMonth(param);
	}
}
