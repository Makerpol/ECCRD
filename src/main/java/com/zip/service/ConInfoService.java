package com.zip.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.ConInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class ConInfoService {
	@Autowired private ConInfoMapper conInfoMapper;
	
	/**
	 * 获取联系我们的文本信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectConInfo(){
		return conInfoMapper.selectConInfo();
	}
	
	/**
	 * 根据ID获取联系我们的文本信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectConInfoById(Map<String, String> param){
		return conInfoMapper.selectConInfoById(param);
	}
	
	/**
	 * 添加联系我们信息
	 * @param param
	 * @return
	 */
	public int insertConInfo(Map<String, String> param) {
		conInfoMapper.insertConInfo(param);
		return conInfoMapper.selectLastInsertID();
	} 
	
	/**
	 * 编辑联系我们信息
	 * @param param
	 */
	public void updateConInfo(Map<String, String> param) {
		conInfoMapper.updateConInfo(param);
	}
}
