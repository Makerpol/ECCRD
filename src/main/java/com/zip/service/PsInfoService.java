package com.zip.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.PsInfoMapper;


@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class PsInfoService {
	@Autowired private PsInfoMapper psInfoMapper;
	
	/**
	 * 根据类型获取平台服务信息
	 * @return
	 */
	public Map<String, Object> selectPsInfoByType(Map<String, String> param){
		return psInfoMapper.selectPsInfoByType(param);
	}
	
	/**
	 * 根据ID获取平台服务信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectPsInfoById(Map<String, String> param){
		return psInfoMapper.selectPsInfoById(param);
	}
	
	/**
	 * 添加平台服务信息
	 * @param param
	 * @return
	 */
	public int insertPsInfo(Map<String, String> param) {
		psInfoMapper.insertPsInfo(param);
		return psInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 更新平台服务信息
	 * @param param
	 */
	public void updatePsInfo(Map<String, String> param) {
		psInfoMapper.updatePsInfo(param);
	}
}
