package com.zip.component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MybatisUtil {
	
	private static Logger log = LoggerFactory.getLogger(MybatisUtil.class);
	
	/**
	 * 批处理最大行数
	 */
	@Value("${sql.batch.max-row}")
	private int MAX_ROW;

	/**
	 * 批量处理sql
	 * @param sqlSessionFactory
	 * @param mapper Mapper的String路径
	 * @param list 要处理的数据
	 */
	public void executeBatchSql(SqlSessionFactory sqlSessionFactory, String mapper, List<Map<String, String>> list) {
		if (list == null || list.size() == 0) {
			log.debug("要批量处理的数据不能为空");
			return;
		}
		if (list.size() > MAX_ROW) {
			throw new RuntimeException("要批量处理的数据不能超过" + MAX_ROW + "行");
		}
		MappedStatement mappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement(mapper);
		if (mappedStatement == null) {
			throw new RuntimeException("没有找到相应的Mapper");
		}
		// 获取原始的sql 语句
		String sql = mappedStatement.getBoundSql(list.get(0)).getSql();
		if (sql == null || sql.equals("")) {
			throw new RuntimeException("Mapper无效，没有响应的参数");
		}
		// 获取参数顺序
		List<ParameterMapping> paramList = mappedStatement.getBoundSql(list.get(0)).getParameterMappings();
		// 获取数据库连接，这个连接与调用的service的其他sql为同一个连接，交由spring管理，不需要关闭，提交等操作
		SqlSession session= sqlSessionFactory.openSession();
		Connection connection = session.getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			for (Map<String, ?> map : list) {
				for (int i = 0; i < paramList.size(); i++) {
					preparedStatement.setObject(i + 1, map.get(paramList.get(i).getProperty()));
				}
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			preparedStatement.clearBatch();
			log.debug("批量处理(" + mapper + ":" + list.toString() + ")");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("批量处理出错(" + mapper + ":" + list.toString() + ")", e);
			throw new RuntimeException(e);
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

}
