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
import com.zip.dao.PdfInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class PdfInfoService {
	
	@Autowired private PdfInfoMapper pdfInfoMapper;
	@Autowired private SqlSessionFactory sqlSessionFactory;
	@Autowired private MybatisUtil mybatisUtil;
	
	/**
	 * 查询PDF
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectPdfInfoBySearch(Map<String, String> param){
		return pdfInfoMapper.selectPdfInfoBySearch(param);
	}
	
	/**
	 * 根据类型获取PDF列表
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectPdfInfoByType(Map<String, String> param){
		return pdfInfoMapper.selectPdfInfoByType(param);
	}
	
	/**
	 * 前端页面获取PDF列表
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectPdfInfoByList(Map<String, String> param){
		return pdfInfoMapper.selectPdfInfoByList(param);
	}
	
	/**
	 * 根据ID获取PDF信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectPdfInfoById(Map<String, String> param){
		return pdfInfoMapper.selectPdfInfoById(param);
	}
	
	/**
	 * 添加PDF信息
	 * @param param
	 * @return
	 */
	public int insertPdfInfo(Map<String, String> param) {
		pdfInfoMapper.insertPdfInfo(param);
		return pdfInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 根据ID更新PDF信息
	 * @param param
	 */
	public void updatePdfInfoById(Map<String, String> param) {
		pdfInfoMapper.updatePdfInfoById(param);
	} 
	
	/**
	 * 根据ID删除PDF信息
	 * @param param
	 */
	public void deletePdfInfoById(Map<String, String> param) {
		pdfInfoMapper.deletePdfInfoById(param);
	}
	
	/**
	 * 批量插入
	 * @param type
	 * @param fileList
	 */
	public void insertPdfList(String type, String userId, List<Integer> fileList) {
		List<Map<String, String>> paramList = Lists.newArrayList();
		for(Integer fileId : fileList) {
			Map<String, String> map = Maps.newHashMap();
			map.put("userId", userId);
			map.put("type", type);
			map.put("fileId", fileId.toString());
			paramList.add(map);
		}
		mybatisUtil.executeBatchSql(sqlSessionFactory, "com.zip.dao.PdfInfoMapper.insertPdfInfo", paramList);
	}
}
