package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface VideoInfoMapper extends BaseInfoMapper{
	public List<Map<String, Object>> selectVideoInfoBySearch(Map<String, String> param);
	
	public Map<String, Object> selectVideoInfoById(Map<String, String> param);
	
	public int insertVideoInfo(Map<String, String> param);
	
	public void updateVideoInfoById(Map<String, String> param);
	
	public void deleteVideoInfoById(Map<String, String> param);
	
	public List<Map<String, Object>> selectVideoInfoByType(Map<String, String> param);
	
	public int selectLastInsertID();
}
