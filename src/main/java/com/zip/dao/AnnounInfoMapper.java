package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface AnnounInfoMapper extends BaseInfoMapper {
	
	/**
	 * 查询通知公告
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectAnnounInfoBySearch(Map<String, String> param);
	
	/**
	 * 根据ID获取通知公告信息
	 * @param pram
	 * @return
	 */
	public Map<String, Object> selectAnnounInfoById(Map<String, String> pram);
	
	/**
	 * 插入通知公告信息
	 * @param param
	 */
	public void insertAnnounInfo(Map<String, String> param);
	
	/**
	 * 根据ID更新通知公告信息
	 * @param param
	 */
	public void updateAnnounInfoById(Map<String, String> param);
	
	/**
	 * 根据ID更新浏览次数
	 * @param param
	 */
	public void updateCountById(Map<String, String> param);
	
	/**
	 * 根据ID更新通知公告状态
	 * @param param
	 */
	public void updateStatusById(Map<String, String> param);
	
	/**
	 * 根据ID删除通知公告信息
	 * @param param
	 */
	public void deleteAnnounInfoById(Map<String, String> param);
	
	/**
	 * 前端页面获取通知公告列表
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectAnnounByList(Map<String, String> param);
	
	/**
	 * 根据用户ID查询本月上传通知公告数量
	 */
	public Map<String, Object> selectAnnounCountByUserId(Map<String, String> param);
	
	/**
	 * 根据操作人ID以及当前年份，获取本年度每月上传通知公告数量
	 */
	public Map<String, Object> selectAnnounCountByAllMonth(Map<String, String> param);
}
