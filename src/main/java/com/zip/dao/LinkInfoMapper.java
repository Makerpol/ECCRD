package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface LinkInfoMapper extends BaseInfoMapper {
	
	/**
	 * 查询友情链接
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectLinkInfoBySearch(Map<String, String> param);
	
	/**
	 * 根据ID查询友情链接信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectLinkInfoById(Map<String, String> param);
	
	/**
	 * 插入友情链接信息
	 * @param param
	 */
	public void insertLinkInfo(Map<String, String> param);
	
	/**
	 * 根据ID更新友情链接信息
	 * @param param
	 */
	public void updateLinkInfoById(Map<String, String> param);
	
	/**
	 * 根据ID删除友情链接信息
	 * @param param
	 */
	public void deleteLinkInfoById(Map<String, String> param);
}
