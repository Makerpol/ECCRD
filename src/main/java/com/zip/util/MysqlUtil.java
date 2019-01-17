package com.zip.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class MysqlUtil {
	
	private static Logger log = LoggerFactory.getLogger(MysqlUtil.class);
	
	/**
	 * 通过表名获取表的明细信息
	 * @param tableName
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getTableDetail(String tableName, Connection con) throws Exception {
		if (SysUtil.isNull(tableName)) {
			throw new Exception("表明不能为空");
		}
		if (con.isClosed()) {
			throw new Exception("连接已关闭");
		}
		Map<String, String> filedMap = Maps.newHashMap();
		DatabaseMetaData databaseMetaData = con.getMetaData();
		
		try (ResultSet reSet = databaseMetaData.getColumns(con.getCatalog(), null, tableName, "%");) {
			while (reSet.next()) {
				filedMap.put(reSet.getString("COLUMN_NAME"), reSet.getString("REMARKS"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("获取表明细异常", e);
		}
		return filedMap;
	}
}
