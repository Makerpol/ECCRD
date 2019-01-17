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
import com.zip.dao.FileInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class FileInfoService {

	@Autowired private FileInfoMapper fileInfoMapper;
	@Autowired private SqlSessionFactory sqlSessionFactory;
	@Autowired private MybatisUtil mybatisUtil;
	
	/**
	 * 查询文件信息
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectFileInfoByType(Map<String, String> param) {
		return fileInfoMapper.selectFileInfoByType(param);
	}
	
	/**
	 * 搜索文件信息
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectFileInfoBySearch(Map<String, String> param) {
		return fileInfoMapper.selectFileInfoBySearch(param);
	}
	
	/**
	 * 通过hash值查询文件
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectFileInfoByHash(Map<String, String> param) {
		return fileInfoMapper.selectFileInfoByHash(param);
	}
	
	/**
	 * 通过ID更新文件状态
	 * @param param
	 */
	public void updateFileStatusById(Map<String, String> param) {
		fileInfoMapper.updateFileStatusById(param);
	}
	
	/**
	 * 通过UUID查询文件信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectFileInfoByUuid(Map<String, String> param) {
		return fileInfoMapper.selectFileInfoByUuid(param);
	}
	
	/**
	 * 通过ID查询文件信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectFileInfoById(Map<String, String> param) {
		return fileInfoMapper.selectFileInfoById(param);
	}
	
	/**
	 * 通过ID删除文件信息
	 * @param param
	 */
	public void deleteFileInfoById(Map<String, String> param) {
		fileInfoMapper.deleteFileInfoById(param);
	}
	
	
	
	/**
	 * 批量删除 文件信息
	 */
	public void deleteFileBathByIdList(Map<String, String> param){
		List<Map<String, String>> data = Lists.newArrayList();
		String [] idList = param.get("idList").toString().split(",");
		for (String idString : idList) {
			Map<String, String> temp = Maps.newHashMap();
			temp.put("fileId", idString);
			data.add(temp);
		}	
		// 批量删除信息
		mybatisUtil.executeBatchSql(sqlSessionFactory, "com.zip.dao.FileInfoMapper.deleteFileInfoById", data);
	}
	
	
	/**
	 * 通过UUID删除文件信息
	 * @param param
	 */
	public void deleteFileInfoByUuid(Map<String, String> param) {
		fileInfoMapper.deleteFileInfoByUuid(param);
	}
	
	/**
	 * 插入文件信息
	 * @param param
	 */
	public int insertFileInfo(Map<String, String> param) {
		fileInfoMapper.insertFileInfo(param);
		return fileInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 插入crl文件信息并更改其他crl文件状态
	 * @param param
	 */
	public void insertFileInfoAndDelOldFile(Map<String, String> param) {
		int insertFileInfo = insertFileInfo(param);
		param.put("fileId", String.valueOf(insertFileInfo));
		fileInfoMapper.updateFileStatusByIdAndFileName(param);
		
	}
}
