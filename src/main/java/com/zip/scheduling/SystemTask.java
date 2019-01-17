package com.zip.scheduling;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zip.service.DictInfoService;
import com.zip.util.DictUtil;

@Component
public class SystemTask {
	
	@Autowired private DictInfoService dictInfoService;
	private static Logger log = LoggerFactory.getLogger(SystemTask.class);
	
	@Scheduled(cron = "0 0 * * * ?")
	public void flush() {
		List<Map<String, Object>> dictList = dictInfoService.selectDictInfo();
		DictUtil.initDictMap(dictList);
		log.info("更新字典信息");
	}
}
