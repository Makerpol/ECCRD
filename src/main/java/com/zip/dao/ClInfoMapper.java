package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface ClInfoMapper extends BaseInfoMapper {

	/**
	 * 查询轮播
	 * @return
	 */
	public List<Map<String, Object>> selectClInfoBySearch(Map<String, String> param);
	
	/**
	 * 通过类型获取轮播列表
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectClInfoByType(Map<String, String> param);
	
	/**
	 * 通过ID查询轮播信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectClInfoById(Map<String, String> param);
	
	/**
	 * 插入轮播信息
	 * @param param
	 */
	public void insertClInfo(Map<String, String> param);
	
	/**
	 * 通过ID更新轮播信息
	 * @param param
	 */
	public void updateClInfoById(Map<String, String> param);
	
	/**
	 * 通过ID删除轮播信息
	 * @param param
	 */
	public void deleteClInfoById(Map<String, String> param);
}
