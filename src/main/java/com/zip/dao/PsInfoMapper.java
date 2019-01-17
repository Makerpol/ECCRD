package com.zip.dao;

import java.util.Map;

public interface PsInfoMapper extends BaseInfoMapper{
	
	/**
	 * 根据类型获取平台服务信息
	 * @return
	 */
	public Map<String, Object> selectPsInfoByType(Map<String, String> param);
	
	/**
	 * 根据ID获取创新助手信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectPsInfoById(Map<String, String> param);
	
	/**
	 * 添加创新助手信息
	 * @param param
	 */
	public void insertPsInfo(Map<String, String> param);
	
	/**
	 * 更新创新助手信息
	 * @param param
	 */
	public void updatePsInfo(Map<String, String> param);
}
