package com.zip.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lock {

	private static Logger log = LoggerFactory.getLogger(Lock.class);
	
	private static ReentrantLock LOCK = null;
	
	/**
	 * 单利模式，定义唯一的锁
	 * @return
	 */
	public static ReentrantLock getInstance() {
		if (LOCK == null) {
			// 创建公平锁
			LOCK = new ReentrantLock(true);
		}
		return LOCK;
	}
	
	/**
	 * 初始化锁
	 */
	static {
		LOCK = getInstance();
	}
	
	/**
	 * 获取等待锁的线程数
	 * @return
	 */
	public static int getQueueLength() {
		return LOCK.getQueueLength();
	}
	
	/**
	 * 获取锁，如果没有获取到锁，则线程保持休眠状态
	 */
	public static void lock() {
		LOCK.lock();
		log.debug("线程（" + Thread.currentThread().getName() + "）已获取到lock");
	}
	
	/**
	 * 是否已锁定
	 * @return
	 */
	public static boolean isLocked() {
		return LOCK.isLocked();
	}
	
	/**
	 * 设置等待时间的锁，如果超过等待时长后，还没有获取到锁，则返回false（单位是秒）
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static boolean tryLock(int timeout) {
		try {
			log.debug("线程（" + Thread.currentThread().getName() + "）尝试获取锁，等待时间为" + timeout + "秒");
			boolean bool = LOCK.tryLock(timeout, TimeUnit.SECONDS);
			if (bool) {
				log.debug("线程（" + Thread.currentThread().getName() + "）" + timeout + "秒内获取到lock");
			} else {
				log.debug("线程（" + Thread.currentThread().getName() + "）" + timeout + "秒内没有获取到lock");
			}
			return bool;
		} catch (Exception e) {
			// TODO: handle exception
			log.debug("线程（" + Thread.currentThread().getName() + "）获取锁失败", e);
			return false;
		}
		
	}
	
	/**
	 * 释放锁
	 */
	public static void unlock() {
		LOCK.unlock();
		log.debug("线程（" + Thread.currentThread().getName() + "）释放lock");
	}
}
