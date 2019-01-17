package com.zip.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.LinkInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class LinkInfoService {
	
	@Autowired private LinkInfoMapper linkInfoMapper;
	
	/**
	 * 查询友情链接信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectLinkInfoBySearch(Map<String, String> param){
		return linkInfoMapper.selectLinkInfoBySearch(param);
	}
	
	/**
	 * 根据ID查询友情链接信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectLinkInfoById(Map<String, String> param){
		return linkInfoMapper.selectLinkInfoById(param);
	}
	
	/**
	 * 插入友情链接信息
	 * @param param
	 * @return
	 */
	public int insertLinkInfo(Map<String, String> param) {
		linkInfoMapper.insertLinkInfo(param);
		return linkInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 根据ID更新友情链接信息
	 * @param param
	 */
	public void updateLinkInfoById(Map<String, String> param) {
		linkInfoMapper.updateLinkInfoById(param);
	}
	
	/**
	 * 根据ID删除友情链接信息
	 * @param param
	 */
	public void deleteLinkInfoById(Map<String, String> param) {
		linkInfoMapper.deleteLinkInfoById(param);
	}
}
