package com.zip.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.primitives.Ints;
import com.zip.component.InitParams;
import com.zip.component.RandomValidateCode;
import com.zip.service.FileInfoService;
import com.zip.util.JsonUtil;
import com.zip.util.PDFUtil;
import com.zip.util.SysUtil;

/**
 * 百度文本编辑器的插件的操作类
 * @author ssk
 *
 */
@Controller
public class UeditorAction extends BaseAction {
	
	private static Logger log = LoggerFactory.getLogger(UeditorAction.class);
	
	@Autowired private FileInfoService fileInfoService;
	
	@Autowired private InitParams initParams;
	
	/**
	 * 分派上传action
	 * @param multipartFile
	 * @param request
	 * @param response
	 */
	@RequestMapping("upload")
	public @ResponseBody JSONObject upload(HttpServletRequest request) {
		Map<String, String> param = getParameterMapWithPageInfo();
		log.debug("ueditor调用的action名称：" + param.get("action"));
		// 图片上传
		if (param.get("action").equalsIgnoreCase("uploadimage")) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			return uploadImg(multipartHttpServletRequest.getFile("upfile"), param);
		}
		// 文件上传
		if (param.get("action").equalsIgnoreCase("uploadfile")) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			return uploadFile(multipartHttpServletRequest.getFile("upfile"), param);
		}
		if(param.get("action").equalsIgnoreCase("uploadvideo")) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			return uploadVideo(multipartHttpServletRequest.getFile("upfile"), param);
		}
		// 图片列表
		if (param.get("action").equalsIgnoreCase("listimage")) {
			return showImgList(param);
		}
		// 文件列表
		if (param.get("action").equalsIgnoreCase("listfile")) {
			return showFileList(param);
		}
		return new JSONObject();
	}
	
	/**
	 * 图片列表
	 * @param request
	 * @param response
	 */
	public JSONObject showImgList(Map<String, String> param) {
		JSONObject json = new JSONObject();
		param.put("type", "1");
		List<Map<String, Object>> fileList = fileInfoService.selectFileInfoByType(param);
		json.put("total", Ints.tryParse(param.get("total")));
		// 返回开始行数
		json.put("start", param.get("start"));
		JSONArray temp = new JSONArray();
		for (Map<String, Object> map : fileList) {
			JSONObject img = new JSONObject();
			img.put("id", map.get("FILE_ID").toString());
			img.put("uuid", map.get("FILE_UUID").toString());
			img.put("hash", map.get("FILE_HASH").toString());
			img.put("url", "/showFile/" + map.get("FILE_UUID").toString() + ".shtml");
			img.put("size", map.get("FILE_LENGTH").toString());
			img.put("type", "." + map.get("FILE_EXTE").toString());
			img.put("original", map.get("FILE_NAME").toString());
			img.put("title", map.get("FILE_NAME").toString() + "." + map.get("FILE_EXTE").toString());
			temp.add(img);
		}
		json.put("list", temp);
		json.put("state", "SUCCESS");
		return json;
	}
	
	/**
	 * 文件列表
	 * @param request
	 * @param response
	 */
	public JSONObject showFileList(Map<String, String> param) {
		JSONObject json = new JSONObject();
		param.put("type", "2");
		List<Map<String, Object>> fileList = fileInfoService.selectFileInfoByType(param);
		json.put("total", Ints.tryParse(param.get("total")));
		// 返回开始行数
		json.put("start", param.get("start"));
		JSONArray temp = new JSONArray();
		for (Map<String, Object> map : fileList) {
			JSONObject img = new JSONObject();
			img.put("id", map.get("FILE_ID").toString());
			img.put("uuid", map.get("FILE_UUID").toString());
			img.put("hash", map.get("FILE_HASH").toString());
			img.put("url", "/downFile/" + map.get("FILE_UUID").toString() + ".shtml");
			img.put("size", map.get("FILE_LENGTH").toString());
			img.put("type", "." + map.get("FILE_EXTE").toString());
			img.put("original", map.get("FILE_NAME").toString());
			img.put("title", map.get("FILE_NAME").toString() + "." + map.get("FILE_EXTE").toString());
			temp.add(img);
		}
		json.put("list", temp);
		json.put("state", "SUCCESS");
		return json;
	}
	
	/**
	 * 图片上传文件
	 * @param file
	 * @param request
	 */
	public JSONObject uploadImg(MultipartFile multipartFile, Map<String, String> param) {
		// 文件原名
		String tempName = multipartFile.getOriginalFilename();
		param.put("name", tempName.substring(0, tempName.lastIndexOf(".")));
		param.put("type", "1");		// 1表示图片文件
		// 扩展名
		String extension = tempName.substring(tempName.lastIndexOf("."), tempName.length());
		param.put("exte", extension.substring(1, extension.length()));
		// 文件夹
		String folder = getRealPath() + initParams.uploadPathImages;
		// 如果不存在则创建
		if (!new File(folder).exists()) {
			new File(folder).mkdirs();
		}
		// uuid
		String uuid = SysUtil.getUUID();
		String finalName = uuid + extension;
		param.put("uuid", uuid);
		// 要生成的新文件的路径
		String val = getRealPath() + initParams.uploadPathImages + File.separator + finalName;
		JSONObject json = new JSONObject();
		File targetFile = new File(val);
		if (targetFile.exists()) {
			json.put("state", "文件已存在");
		} else {
			try {
				multipartFile.transferTo(targetFile);
				
				param.put("length", targetFile.length() + "");
				param.put("lengthStr", SysUtil.getLengthStr(targetFile.length()));
				param.put("path", initParams.uploadPathImages + "/" + finalName);
				// 哈希值
				param.put("hash", Files.asByteSource(targetFile).hash(Hashing.sha256()).toString());
				
				json.put("state", "SUCCESS");
				json.put("size", targetFile.length());
				json.put("type", extension);
				json.put("original", tempName.substring(0, tempName.lastIndexOf(".")));
				json.put("title", tempName + extension);
				json.put("id", fileInfoService.insertFileInfo(param));
				json.put("url", "/showFile/" + uuid + ".shtml");
				json.put("uuid", param.get("uuid"));
				json.put("hash", param.get("hash"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				json.put("state", "上传失败");
			}
		}
		return json;
	}
	
	/**
	 * 文件上传文件
	 * @param file
	 * @param request
	 */
	/**
	 * @param multipartFile
	 * @param param
	 * @return
	 */
	public JSONObject uploadFile(MultipartFile multipartFile, Map<String, String> param) {
		// 文件原名
		String tempName = multipartFile.getOriginalFilename();
		param.put("name", tempName.substring(0, tempName.lastIndexOf(".")));
		param.put("type", "2");		// 文件
		// 扩展名
		String extension = tempName.substring(tempName.lastIndexOf("."), tempName.length());
		// 文件夹
		String folder = getRealPath() + initParams.uploadPathFiles;
		// 如果不存在则创建
		if (!new File(folder).exists()) {
			new File(folder).mkdirs();
		}
		// uuid
		String uuid = SysUtil.getUUID();
		// 要生成的新文件的路径
		String finalName = uuid + extension;
		param.put("uuid", uuid);
		String val = getRealPath() + initParams.uploadPathFiles + File.separator + finalName;
		// 转换扩展名，是为了让Ueditor能识别格式，并在编辑器中展示图标
		if (extension.toLowerCase().equals(".xlsx")) {
			extension = ".xls";
		} else if (extension.toLowerCase().equals(".docx")) {
			extension = ".doc";
		} else if (extension.toLowerCase().equals(".pptx")) {
			extension = ".ppt";
		}
		param.put("exte", extension.substring(1, extension.length()));
		
		File targetFile = new File(val);
		JSONObject json = new JSONObject();
		if (targetFile.exists()) {
			json.put("state", "文件已存在");
		} else {
			try {
				multipartFile.transferTo(targetFile);
				
				param.put("length", targetFile.length() + "");
				param.put("lengthStr", SysUtil.getLengthStr(targetFile.length()));
				param.put("path", initParams.uploadPathFiles + "/" + finalName);
				// 哈希值
				param.put("hash", Files.asByteSource(targetFile).hash(Hashing.sha256()).toString());
				
				json.put("state", "SUCCESS");
				json.put("type", extension);
				json.put("size", targetFile.length());
				json.put("original", tempName.substring(0, tempName.lastIndexOf(".")));
				json.put("title", tempName + extension);
				int id = fileInfoService.insertFileInfo(param);
				json.put("url", "/downFile/" + param.get("uuid") + ".shtml");
				json.put("id", id);
				json.put("uuid", param.get("uuid"));
				json.put("hash", param.get("hash"));
				//生成PDF略缩图
				if(extension.toLowerCase().equals(".pdf")){
					PDFUtil pdfUtil = new PDFUtil(val);
					String imgPath = pdfUtil.writeImage(0, getRealPath(), initParams.uploadPathImages, finalName.toLowerCase().replace(".pdf", ".png"));
					pdfUtil.close();
					json.put("url", imgPath);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				json.put("state", "上传失败");
			}
		}
		return json;
	}
	
	public JSONObject uploadVideo(MultipartFile multipartFile, Map<String, String> param) {
		// 文件原名
		String tempName = multipartFile.getOriginalFilename();
		param.put("name", tempName.substring(0, tempName.lastIndexOf(".")));
		param.put("type", "3");		// 视频
		// 扩展名
		String extension = tempName.substring(tempName.lastIndexOf("."), tempName.length());
		// 文件夹
		String folder = getRealPath() + initParams.uploadPathVideo;
		// 如果不存在则创建
		if (!new File(folder).exists()) {
			new File(folder).mkdirs();
		}
		// uuid
		String uuid = SysUtil.getUUID();
		// 要生成的新文件的路径
		String finalName = uuid + extension;
		param.put("uuid", uuid);
		String val = getRealPath() + initParams.uploadPathVideo + File.separator + finalName;
		JSONObject json = new JSONObject();
		File targetFile = new File(val);
		if(targetFile.exists()) {
			json.put("state", "文件已存在");
		}else {
			try {
				multipartFile.transferTo(targetFile);
				param.put("length", targetFile.length() + "");
				param.put("lengthStr", SysUtil.getLengthStr(targetFile.length()));
				param.put("path", initParams.uploadPathVideo + "/" + finalName);
				// 哈希值
				param.put("hash", Files.asByteSource(targetFile).hash(Hashing.sha256()).toString());
				
				json.put("state", "SUCCESS");
				json.put("size", targetFile.length());
				json.put("type", extension);
				json.put("original", tempName.substring(0, tempName.lastIndexOf(".")));
				json.put("title", tempName + extension);
				json.put("id", fileInfoService.insertFileInfo(param));
				json.put("url", "/showFile/" + uuid + ".shtml");
				json.put("uuid", param.get("uuid"));
				json.put("hash", param.get("hash"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json.put("state", "上传失败");
			}
		}
		return json;
	}
	
	/**
	 * 验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping("verCode")
	public @ResponseBody void verCode() {
		RandomValidateCode randomValidateCode = new RandomValidateCode();
		String randomString = randomValidateCode.getRandcode(response);
		getSession().setAttribute(RandomValidateCode.RANDOMCODEKEY, randomString);
	}
	
	/**
	 * 图片文件展示
	 */
	@RequestMapping("showFile")
	public @ResponseBody void showFile() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("uuid"))) {
			JsonUtil.writeStr(response, "文件不存在");
			return;
		}
		Map<String, Object> fileMap = fileInfoService.selectFileInfoByUuid(param);
		if (fileMap == null || fileMap.size() == 0) {
			JsonUtil.writeStr(response, "文件不存在");
			return;
		}
		File img = new File(getRealPath() + fileMap.get("FILE_PATH").toString());
		if (!img.exists()) {
			// 图片文件不存在
			JsonUtil.writeStr(response, "文件不存在");
			return;
		}
		try (FileInputStream fileInputStream = new FileInputStream(img); ) {
			ServletOutputStream servletOutputStream = response.getOutputStream();
			byte imgByte[] = new byte[fileInputStream.available()];
			fileInputStream.read(imgByte);
			servletOutputStream.write(imgByte);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("读取图片文件失败", e);
		}
	}
	
	/**
	 * 文件下载
	 */
	@RequestMapping("downFile")
	public @ResponseBody void downFile() {
		Map<String, String> param = getParameterMap();
		if (SysUtil.isNull(param.get("uuid"))) {
			JsonUtil.writeStr(response, "文件不存在");
			return;
		}
		Map<String, Object> fileMap = fileInfoService.selectFileInfoByUuid(param);
		if (fileMap == null || fileMap.size() == 0) {
			JsonUtil.writeStr(response, "文件不存在");
			return;
		}
		log.debug("文件下载：" + param.toString());
		long size = Long.valueOf(fileMap.get("FILE_LENGTH").toString());
		File downFile = new File(getRealPath() + fileMap.get("FILE_PATH").toString());
		if (downFile.exists()) {
			long cur = 0;		// 断点位置
			ServletOutputStream outputStream = null;
			try (InputStream inputStream = new BufferedInputStream(new FileInputStream(downFile))) {
				response.reset();
				// 高度客户端传输类型
				response.setHeader("Accept-Ranges", "bytes");
				response.setContentType("text/html;charset=" + SysUtil.ENCODE);
				response.setCharacterEncoding(SysUtil.ENCODE);
				if (request.getHeader("Range") != null) {
					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
					System.out.println("Range:" + request.getHeader("Range"));
					cur = Long.parseLong(request.getHeader("Range").replace("bytes=", "").split("-")[0]);
				}
				String ext = fileMap.get("FILE_PATH").toString();
				String file = fileMap.get("FILE_NAME").toString() + ext.substring(ext.lastIndexOf("."), ext.length());
				if (file.substring(file.lastIndexOf("."), file.length()).toLowerCase().contains("doc") || 
						file.substring(file.lastIndexOf("."), file.length()).toLowerCase().contains("docx")) {
					response.setContentType("application/msword");
				} else if (file.substring(file.lastIndexOf("."), file.length()).toLowerCase().contains("pdf")) {
					response.setContentType("application/pdf");
				} else if (file.substring(file.lastIndexOf("."), file.length()).toLowerCase().contains("xls") || 
						file.substring(file.lastIndexOf("."), file.length()).toLowerCase().contains("xlsx")) {
					response.setContentType("application/vnd.ms-excel");
				} else {
					response.setContentType("application/octet-stream");
				}
				// 设置文件长度
				response.setHeader("Content-Length", new Long(size - cur).toString());
				// 设置文件名
				response.addHeader("Content-Disposition", "attachment;filename=" + 
							new String(file.replaceAll(" ", "").getBytes(SysUtil.ENCODE), "ISO-8859-1"));
				if (cur > 0) {
					String contentRange = new StringBuffer("bytes ")
							.append(new Long(cur).toString()).append("-")
							.append(new Long(size - 1).toString()).append("/")
							.append(new Long(size).toString()).toString();
					// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
					response.setHeader("Content-Range", contentRange);
					// 跳过已经下载过的长度
					inputStream.skip(cur);
				}
				byte buf[] = new byte[1024 * 4];
				int temp = 0;
				outputStream = response.getOutputStream();
				while ((temp = inputStream.read(buf)) != -1) {
					outputStream.write(buf, 0, temp);
					outputStream.flush();
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.debug("下载过程可能出错，多半是断开连接后，断点续传引起的", e);
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (Exception outExcep) {
						// TODO: handle exception
					}
				}
			}
		} else {
			// 说明有数据冗余，跑出异常，让异常模块处理
			JsonUtil.writeStr(response, "文件不存在");
		}
	}
}
