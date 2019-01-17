package com.zip.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.DictInfoMapper;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class DictInfoService {

	@Autowired private DictInfoMapper dictInfoMapper;
	
	/**
	 * 查询所有的字典信息
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectDictInfo() {
		return dictInfoMapper.selectDictInfo();
	}
}
