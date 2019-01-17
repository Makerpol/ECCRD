package com.zip.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zip.dao.UserInfoMapper;

/**
 * 
 * @title UserInfoService.java
 * @author ssk
 * @date 2017年12月16日
 * @description 
 * 
 * TransactionDefinition.PROPAGATION_REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。
 * TransactionDefinition.PROPAGATION_REQUIRES_NEW：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
 * TransactionDefinition.PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
 * TransactionDefinition.PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
 * TransactionDefinition.PROPAGATION_NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。
 * TransactionDefinition.PROPAGATION_MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
 * TransactionDefinition.PROPAGATION_NESTED：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。
 * 
 * @Transactional
 * value 	String 	可选的限定描述符，指定使用的事务管理器
 * propagation 	enum: Propagation 	可选的事务传播行为设置
 * isolation 	enum: Isolation 	可选的事务隔离级别设置
 * readOnly 	boolean 	读写或只读事务，默认读写
 * timeout 	int (in seconds granularity) 	事务超时时间设置
 * rollbackFor 	Class对象数组，必须继承自Throwable 	导致事务回滚的异常类数组
 * rollbackForClassName 	类名数组，必须继承自Throwable 	导致事务回滚的异常类名字数组
 * noRollbackFor 	Class对象数组，必须继承自Throwable 	不会导致事务回滚的异常类数组
 * noRollbackForClassName 	类名数组，必须继承自Throwable 	不会导致事务回滚的异常类名字数组
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UserInfoService {

	@Autowired private UserInfoMapper userInfoMapper;
	
	/**
	 * 通过用户名查询用户信息
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectUserByName(Map<String, String> param) {
		return userInfoMapper.selectUserByName(param);
	}
	
	/**
	 * 搜索用户
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> selectUserBySearch(Map<String, String> param) {
		return userInfoMapper.selectUserBySearch(param);
	}
	
	/**
	 * 更新用户登录时间
	 * 指定事务管理名称
	 * @param param
	 */
	public void updateUserLastLoginTime(Map<String, String> param) {
		userInfoMapper.updateUserLastLoginTime(param);
	}
	
	/**
	 * 检测sn是否已存在
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int selectUserByNameCount(Map<String, String> param) {
		return userInfoMapper.selectUserByNameCount(param);
	}
	
	/**
	 * 通过ID查询用户信息
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> selectUserById(Map<String, String> param) {
		return userInfoMapper.selectUserById(param);
	}
	
	/**
	 * 更新用户状态
	 * 不指定事务管理名称(默认)
	 * @param param
	 */
	public void updateUserStatus(Map<String, String> param) {
		userInfoMapper.updateUserStatus(param);
	}
	
	/**
	 * 添加用户
	 * @param param
	 * @return
	 */
	public int insertUser(Map<String, String> param) {
		userInfoMapper.insertUser(param);
		return userInfoMapper.selectLastInsertID();
	}
	
	/**
	 * 更新用户信息
	 * @param param
	 */
	public void updateUser(Map<String, String> param) {
		userInfoMapper.updateUser(param);
	}
	
	/**
	 * 通过用户ID更新密码
	 * @param param
	 */
	public void updatePassById(Map<String, String> param) {
		userInfoMapper.updatePassById(param);
	}
}
