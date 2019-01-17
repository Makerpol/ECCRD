package com.zip.component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.primitives.Ints;
import com.zip.util.ReflectUtil;

/** 
 * 
 * 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。 
 * 利用拦截器实现Mybatis分页的原理： 
 * 要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象，Mybatis在执行Sql语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句 
 * 是在Statement之前产生的，所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手。在Mybatis中Statement语句是通过RoutingStatementHandler对象的 
 * prepare方法生成的。所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法，然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句，之后再调用 
 * StatementHandler对象的prepare方法，即调用invocation.proceed()。 
 * 对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少，这是通过获取到了原始的Sql语句后，把它改为对应的统计语句再利用Mybatis封装好的参数和设 
 * 置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。 
 * 
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisPlugin implements Interceptor {
	
	private static Logger log = LoggerFactory.getLogger(MybatisPlugin.class);
	
	private static final String SQL_FROM_STR = " FROM ";
	
	private static String DATA_TYPE = null;
	
	@Value("${max.row}")
	private int maxRow;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			//对于StatementHandler其实只有两个实现类，一个是RoutingStatementHandler，另一个是抽象类BaseStatementHandler，  
			//BaseStatementHandler有三个子类，分别是SimpleStatementHandler，PreparedStatementHandler和CallableStatementHandler，  
			//SimpleStatementHandler是用于处理Statement的，PreparedStatementHandler是处理PreparedStatement的，而CallableStatementHandler是  
			//处理CallableStatement的。Mybatis在进行Sql语句处理的时候都是建立的RoutingStatementHandler，而在RoutingStatementHandler里面拥有一个  
			//StatementHandler类型的delegate属性，RoutingStatementHandler会依据Statement的不同建立对应的BaseStatementHandler，即SimpleStatementHandler、  
			//PreparedStatementHandler或CallableStatementHandler，在RoutingStatementHandler里面所有StatementHandler接口方法的实现都是调用的delegate对应的方法。  
			//我们在PageInterceptor类上已经用@Signature标记了该Interceptor只拦截StatementHandler接口的prepare方法，又因为Mybatis只有在建立RoutingStatementHandler的时候  
			//是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。  
			RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
			//通过反射获取到当前RoutingStatementHandler对象的delegate属性  
			StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
			//通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
			MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
			//获取到当前StatementHandler的 boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了  
			//RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。
			BoundSql boundSql = delegate.getBoundSql();
			//拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
			Object obj = boundSql.getParameterObject();
			SQL_CHECK : if (obj != null && obj instanceof HashMap<?, ?>) {
				@SuppressWarnings("unchecked")
				Map<String, String> param = (Map<String, String>) obj;
				// 如果参数中包含分页信息
				if (param.keySet().contains("page")) {
					Connection connection = (Connection) invocation.getArgs()[0];
					String sql = boundSql.getSql();
					if (!sql.trim().toUpperCase().startsWith("SELECT")) {
						break SQL_CHECK;
					}
					setParameter(sql, connection, mappedStatement, boundSql, param);
					if (DATA_TYPE.equals("oracle")) {
						sql = buildOraclePageSql(sql, param);
					} else if (DATA_TYPE.equals("mysql")) {
						sql = buildMysqlPageSql(sql, param);
					}
					ReflectUtil.setFieldValue(boundSql, "sql", sql);
				}
			}
		}
		return invocation.proceed();
	}
	
	
	/**
	 * 整理oracle分页语句
	 * @param sql
	 * @param param
	 * @return
	 */
	private String buildOraclePageSql(String sql, Map<String, String> param) {
		StringBuilder pageSql = new StringBuilder();
		
		return pageSql.toString();
	}
	
	/**
	 * 整理mysql分页语句
	 * @param sql
	 * @param param
	 * @return
	 */
	private String buildMysqlPageSql(String sql, Map<String, String> param) {
		StringBuilder pageSql = new StringBuilder();
		int MAX = Optional.ofNullable(param).filter(map -> map.containsKey("rows")).map(rows -> Ints.tryParse(rows.get("rows"))).orElse(maxRow);
		param.put("rows", MAX + "");
		int PAGE = Ints.tryParse(param.get("page"));
		int START = (PAGE - 1) * MAX;
		pageSql.append(sql);
		pageSql.append(" LIMIT ");
		pageSql.append(START);
		pageSql.append(" , ");
		pageSql.append(MAX);
//		param.put("startRow", START + "");
		return pageSql.toString();
	}
	
	/**
	 * 拼装查询总量的SQL，并查询结果
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param param
	 */
	private void setParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Map<String, String> param) {
		String tempSql = Optional.of(sql).map(str -> str.toUpperCase().replaceAll("\r\n", " ").replaceAll("\n", " ").replaceAll("\t", " ")).
				map(str -> str.substring(str.indexOf(SQL_FROM_STR), str.length())).map(str -> "SELECT COUNT(0) " + str).get();
		tempSql = Optional.of(tempSql).filter(str -> str.contains(" ORDER ")).map(str -> str.substring(0, str.lastIndexOf(" ORDER "))).orElse(tempSql);

		ResultSet reSet = null;
		try (PreparedStatement ps = connection.prepareStatement(tempSql)) {
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), tempSql, 
					boundSql.getParameterMappings(), boundSql.getParameterObject());
			ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, 
					boundSql.getParameterObject(), countBS);
			parameterHandler.setParameters(ps);
			reSet = ps.executeQuery();
			if (reSet.next()) {
				param.put("total", reSet.getInt(1) + "");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("分页语句异常", e);
		} finally {
			if (reSet != null) {
				try {
					reSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("分页语句ResultSet异常", e);
				}
			}
		}
	}

	/**
	 * 只过滤特定方法
	 */
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		DATA_TYPE = properties.getProperty("dataType");
	}

}
