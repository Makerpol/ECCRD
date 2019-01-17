package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface FileInfoMapper {

	/**
	 * 查询文件信息
	 * @return
	 */
	public List<Map<String, Object>> selectFileInfoByType(Map<String, String> param);
	
	/**
	 * 搜索文件信息
	 * @return
	 */
	public List<Map<String, Object>> selectFileInfoBySearch(Map<String, String> param);
	
	/**
	 * 通过hash值查询文件
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFileInfoByHash(Map<String, String> param);
	
	/**
	 * 通过UUID查询文件信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectFileInfoByUuid(Map<String, String> param);
	
	/**
	 * 通过ID查询文件信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectFileInfoById(Map<String, String> param);
	
	/**
	 * 通过ID更新文件状态
	 * @param param
	 */
	public void updateFileStatusById(Map<String, String> param);
	
	/**
	 * 通过ID删除文件信息
	 * @param param
	 */
	public void deleteFileInfoById(Map<String, String> param);
	
	/**
	 * 通过UUID删除文件信息
	 * @param param
	 */
	public void deleteFileInfoByUuid(Map<String, String> param);
	
	/**
	 * 插入文件信息
	 * @param param
	 */
	public void insertFileInfo(Map<String, String> param);
	
	/**
	 * 查询最后一次插入的ID
	 * @return
	 */
	public int selectLastInsertID();
	
	/**
	 * 插入crl文件信息并更改其他crl文件状态
	 * @param param
	 */
	public void updateFileStatusByIdAndFileName(Map<String, String> param);
}
