package com.zip.dao;

import java.util.List;
import java.util.Map;

public interface DictInfoMapper {

	/**
	 * 查询所有的字典信息
	 * @return
	 */
	public List<Map<String, Object>> selectDictInfo();
}
