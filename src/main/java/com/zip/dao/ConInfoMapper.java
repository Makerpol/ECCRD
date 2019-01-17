package com.zip.dao;

import java.util.Map;

public interface ConInfoMapper extends BaseInfoMapper{
	
	/**
	 * 查询联系我们文本信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectConInfo();
	
	/**
	 * 根据ID查询联系我们文本信息
	 * @return
	 */
	public Map<String, Object> selectConInfoById(Map<String, String> param);
	
	/**
	 * 添加联系我们信息
	 */
	public void insertConInfo(Map<String, String> param);
	
	/**
	 * 编辑联系我们文本信息
	 */
	public void updateConInfo(Map<String, String> param);
}
