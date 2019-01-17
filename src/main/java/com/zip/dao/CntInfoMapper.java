package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface CntInfoMapper extends BaseInfoMapper {

	/**
	 * 查询文本信息
	 * @return
	 */
	public List<Map<String, Object>> selectCntBySearch(Map<String, String> param);
	
	/**
	 * 通过ID获取文本信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCntById(Map<String, String> param);
	
	/**
	 * 插入文本信息
	 * @param param
	 */
	public void insertCntInfo(Map<String, String> param);
	
	/**
	 * 通过ID修改状态
	 * @param param
	 */
	public void updateStatusById(Map<String, String> param);
	
	/**
	 * 通过ID修改文本信息
	 * @param param
	 */
	public void updateCntInfoById(Map<String, String> param);
	
	/**
	 * 通过ID更新浏览次数
	 * @param param
	 */
	public void updateCountById(Map<String, String> param);
	
	/**
	 * 通过ID删除文本信息
	 * @param param
	 */
	public void deleteCntInfoById(Map<String, String> param);
	
	/**
	 * 前端获取文本信息列表
	 * @return
	 */
	public List<Map<String, Object>> selectCntByList(Map<String, String> param);
	
	/**
	 * 根据操作人Id查询本月各个栏目上传文章数量
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCntCountByUserId(Map<String, String> param);
	
	/**
	 * 根据操作人ID以及当前年份，获取本年度每月上传文章数量
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCntCountByAllMonth(Map<String, String> param);
}
