package com.zip.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.alibaba.druid.pool.DruidDataSource;

@Order(2)
@Configuration
public class DataSourceConfig {
	
	@Value("${jdbc.url}")
	private String jdbcUrl;
	
	@Value("${jdbc.class}")
	private String jdbcClass;
	
	@Value("${jdbc.name}")
	private String jdbcName;
	
	@Value("${jdbc.pass}")
	private String jdbcPass;
	
	@Value("${jdbc.max}")
	private int max;
	
	@Value("${jdbc.min}")
	private int min;
	
	@Value("${jdbc.time}")
	private long time;

	@Bean(destroyMethod = "close", initMethod = "init")
	public DruidDataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(jdbcUrl);
		dataSource.setDriverClassName(jdbcClass);
		dataSource.setUsername(jdbcName);
		dataSource.setPassword(jdbcPass);
		dataSource.setMaxActive(max);
		dataSource.setMinIdle(min);
		dataSource.setMaxWait(60000);
		dataSource.setInitialSize(min);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMinEvictableIdleTimeMillis(time);
		dataSource.setDefaultAutoCommit(false);
		dataSource.setAsyncCloseConnectionEnable(true);
		dataSource.setRemoveAbandoned(true);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setValidationQueryTimeout(30000);
		dataSource.setTestWhileIdle(true);
		dataSource.setAsyncInit(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		return dataSource;
	}
}
