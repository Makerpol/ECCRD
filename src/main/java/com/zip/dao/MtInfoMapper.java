package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface MtInfoMapper extends BaseInfoMapper{

	/**
	 * 通过检索框参数查询
	 * @return
	 */
	public List<Map<String, Object>> selectMtInfoBySearch(Map<String, String> param);
	
	/**
	 * 通过模块ID查询
	 * @return
	 */
	public Map<String,Object> selectMtInfoById(Map<String, String> param);
	
	/**
	 * 获取所有的一级栏目
	 * @return
	 */
	public List<Map<String, Object>> selectAllParentMt();
	
	/**
	 * 添加模块
	 * @param param
	 */
	public void insertMtInfo(Map<String, String> param);
	
	/**
	 * 删除模块
	 * @param param
	 */
	public void deleteMtInfoById(Map<String, String> param);
	
	/**
	 * 更新模块信息
	 * @param param
	 */
	public void updateMtInfoById(Map<String, String> param);
	
	/**
	 * 通过父级ID获取所有的子栏目
	 * @param param
	 */
	public List<Map<String, Object>> selectMtInfoByParentId(Map<String, String> param);
}
