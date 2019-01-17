package com.zip.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @title InitParams.java
 * @author ssk
 * @date 2017年12月5日
 * @description 系统初始化参数，@Value("#{'${prop}'.split(',')}")
 *
 */
@Component
public class InitParams {
	
	/**
	 * 上传文件临时目录
	 */
	@Value("${file.upload.temp.dir}")
	public String fileUploadTempDir;
	
	/**
	 * 分页的最大行
	 */
	@Value("${max.row}")
	public int maxRow;
	
	@Value("${upload.path.images}")
	public String uploadPathImages;
	
	@Value("${upload.path.files}")
	public String uploadPathFiles;
	
	@Value("${upload.path.video}")
	public String uploadPathVideo;
	/**
	 * 项目根路径
	 */
	public static String CONTEXTPATH;
	
	/**
	 * 页面最大行
	 */
	public static String MAXROW;
	
	public String getContextPath() {
		return CONTEXTPATH;
	}

	@Value("${context.path}")
	public void setContextPath(String contextPath) {
		CONTEXTPATH = contextPath;
	}

	public static String getMAXROW() {
		return MAXROW;
	}

	@Value("${max.row}")
	public static void setMAXROW(String mAXROW) {
		MAXROW = mAXROW;
	}
	
}
