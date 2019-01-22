package com.zip.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.VideoInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class VideoInfoService {
	@Autowired VideoInfoMapper videoInfoMapper;
	
	public List<Map<String, Object>> selectVideoInfoBySearch(Map<String, String> param){
		return videoInfoMapper.selectVideoInfoBySearch(param);
	}
	
	public Map<String, Object> selectVideoInfoById(Map<String, String> param){
		return videoInfoMapper.selectVideoInfoById(param);
	}
	
	public int insertVideoInfo(Map<String, String> param) {
		videoInfoMapper.insertVideoInfo(param);
		return videoInfoMapper.selectLastInsertID();
	}
	
	public int selectLastInsertID() {
		return videoInfoMapper.selectLastInsertID();
	}
	
	public void updateVideoInfoById(Map<String, String> param) {
		videoInfoMapper.updateVideoInfoById(param);
	}
	
	public void deleteVideoInfoById(Map<String, String> param) {
		videoInfoMapper.deleteVideoInfoById(param);
	}
	
	public List<Map<String, Object>> selectVideoInfoByType(Map<String, String> param){
		return videoInfoMapper.selectVideoInfoByType(param);
	}
}
