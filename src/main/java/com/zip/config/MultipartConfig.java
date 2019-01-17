package com.zip.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zip.component.InitParams;
import com.zip.util.SysUtil;

@Order(2)
@Configuration
public class MultipartConfig {

	@Autowired private InitParams initParams;
	
	/**
	 * 上传组件
	 * 
	 * @return
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() throws IOException {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding(SysUtil.ENCODE);
		commonsMultipartResolver.setMaxInMemorySize(1);
		commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 1024);
		//commonsMultipartResolver.setMaxUploadSizePerFile(10 * 1024 * 1024);
		commonsMultipartResolver.setUploadTempDir(new FileSystemResource(initParams.fileUploadTempDir));
		return commonsMultipartResolver;
	}
}
