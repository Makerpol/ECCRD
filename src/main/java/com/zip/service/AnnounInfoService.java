package com.zip.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.AnnounInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class AnnounInfoService {
	
	@Autowired private AnnounInfoMapper announInfoMapper;
	
	/**
	 * 查询通知公告
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectAnnounInfoBySearch(Map<String, String> param){
		return announInfoMapper.selectAnnounInfoBySearch(param);
	}
	
	/**
	 * 根据ID获取通知公告信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectAnnounInfoById(Map<String, String> param){
		return announInfoMapper.selectAnnounInfoById(param);
	}
	
	/**
	 * 插入通知公告信息
	 * @param param
	 * @return
	 */
	public int insertAnnounInfo(Map<String, String> param) {
		announInfoMapper.insertAnnounInfo(param);
		return announInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 根据ID更新通知公告信息
	 * @param param
	 */
	public void updateAnnounInfoById(Map<String, String> param) {
		announInfoMapper.updateAnnounInfoById(param);
	}
	
	/**
	 * 根据ID更新通知公告状态
	 * @param param
	 */
	public void updateStatusById(Map<String, String> param) {
		announInfoMapper.updateStatusById(param);
	}
	
	/**
	 * 根据ID更新浏览次数
	 * @param param
	 */
	public void updateCountById(Map<String, String> param) {
		announInfoMapper.updateCountById(param);
	}
	
	/**
	 * 根据ID删除通知公告信息
	 * @param param
	 */
	public void deleteAnnounInfoById(Map<String, String> param) {
		announInfoMapper.deleteAnnounInfoById(param);
	}
	
	/**
	 * 前端页面获取通知公告列表
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectAnnounByList(Map<String, String> param){
		return announInfoMapper.selectAnnounByList(param);
	}
	
	/**
	 * 根据用户ID查询本月上传通知公告数量
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectAnnounCountByUserId(Map<String, String> param){
		return announInfoMapper.selectAnnounCountByUserId(param);
	}
	
	/**
	 * 根据操作人ID以及当前年份，获取本年度每月上传通知公告数量
	 */
	public Map<String, Object> selectAnnounCountByAllMonth(Map<String, String> param){
		return announInfoMapper.selectAnnounCountByAllMonth(param);
	}
}
