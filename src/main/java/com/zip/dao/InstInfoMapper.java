package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface InstInfoMapper extends BaseInfoMapper {

	/**
	 * 查询文本信息
	 * @return
	 */
	public List<Map<String, Object>> selectInstBySearch(Map<String, String> param);
	
	/**
	 * 通过ID获取文本信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectInstById(Map<String, String> param);
	
	/**
	 * 插入文本信息
	 * @param param
	 */
	public void insertInstInfo(Map<String, String> param);
	
	/**
	 * 通过ID修改文本信息
	 * @param param
	 */
	public void updateInstInfoById(Map<String, String> param);
	
	/**
	 * 通过ID删除文本信息
	 * @param param
	 */
	public void deleteInstInfoById(Map<String, String> param);
	
	/**
	 * 前端获取文本信息列表
	 * @return
	 */
	public List<Map<String, Object>> selectInstByList(Map<String, String> param);
}
