package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface PdfInfoMapper extends BaseInfoMapper {
	
	/**
	 * 查询PDF
	 * @return
	 */
	public List<Map<String, Object>> selectPdfInfoBySearch(Map<String, String> param);
	
	/**
	 * 根据类型查询PDF
	 * @return
	 */
	public List<Map<String, Object>> selectPdfInfoByType(Map<String, String> param);
	
	/**
	 * 前端页面获取PDF列表
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectPdfInfoByList(Map<String, String> param);
	
	/**
	 * 根据ID查询
	 * @return
	 */
	public Map<String, Object> selectPdfInfoById(Map<String, String> param);
	
	/**
	 * 插入PDF信息
	 */
	public void insertPdfInfo(Map<String, String> param);
	
	/**
	 * 根据ID更新PDF信息
	 */
	public void updatePdfInfoById(Map<String, String> param);
	
	/**
	 * 根据ID删除PDF信息
	 */
	public void deletePdfInfoById(Map<String, String> param);
	
	/**
	 * 获取最后插入的信息ID
	 */
	public int selectLastInsertID();
}
