package com.zip.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFUtil {

	private static Logger log = LoggerFactory.getLogger(PDFUtil.class);
	
	private BufferedInputStream bufferedInputStream = null;
	private PDDocument document = null;
	private PDDocumentInformation documentInformation = null;
	private PDDocumentCatalog cata = null;
	private PDPageTree pageTree = null;
	private PDFRenderer pdfRenderer = null;
	
	/**
	 * 通过路径声明的构造函数
	 * @param path
	 */
	public PDFUtil(String path) {
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(path));
			document = PDDocument.load(bufferedInputStream);
			documentInformation = document.getDocumentInformation();
			cata = document.getDocumentCatalog();
			pageTree = cata.getPages();
			pdfRenderer = new PDFRenderer(document);
		} catch (Exception e) {
			log.error("读取PDF文件出错", e);
		}
	}
	
	/**
	 * 获取PDF标题
	 * @return
	 */
	public String getTitle() {
		return documentInformation.getTitle();
	}
	
	/**
	 * 获取作者
	 * @return
	 */
	public String getAuthor() {
		return documentInformation.getAuthor();
	}
	
	/**
	 * 获取完整的创建时间
	 * @return
	 */
	public String getCreationDate() {
		return SysUtil.toChar(documentInformation.getCreationDate().getTimeInMillis(), "yyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取简单的创建时间
	 * @return
	 */
	public String getCreationDateSimple() {
		return SysUtil.toChar(documentInformation.getCreationDate().getTimeInMillis(), "yyy-MM-dd");
	}
	
	/**
	 * 获取简单的修改时间
	 * @return
	 */
	public String getModificationDate() {
		return SysUtil.toChar(documentInformation.getModificationDate().getTimeInMillis(), "yyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取简单的修改时间
	 * @return
	 */
	public String getModificationDateSimple() {
		return SysUtil.toChar(documentInformation.getModificationDate().getTimeInMillis(), "yyy-MM-dd");
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getCount() {
		return pageTree.getCount();
	}
	
	/**
	 * 获取特定页的PDF图片流
	 * @param page
	 * @return
	 */
	private BufferedImage getImageByPage(int page) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = pdfRenderer.renderImageWithDPI(page, 96, ImageType.RGB);
			return bufferedImage;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("读取特定页的PDF出错", e);
			return null;
		}
	}
	
	/**
	 * 输出文件到路径
	 * @param page
	 * @param path
	 */
	public String writeImage(int page, String basePath, String path, String fileName) {
		try {
			BufferedImage bufferedImage = getImageByPage(page);
			new File(basePath + path).mkdirs();
			ImageIO.write(bufferedImage, "png", new File(basePath + path + "/" + fileName));
			return path + "/" + fileName;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("输出文件到路径出错", e);
			return null;
		}
	}
	
	/**
	 * 获取PDF图片文件的base64编码
	 * @param page
	 * @return
	 */
	public String getImageBase64(int page) {
		try {
			BufferedImage bufferedImage = getImageByPage(page);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			byte temp[] = baos.toByteArray();
			baos.close();
			Base64 base64 = new Base64();
			return new String(base64.encode(temp));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("获取图片并进行base64加密出错", e);
			return "";
		}
	}
	
	/**
	 * 关闭
	 */
	public void close() {
		try {
			document.close();
			bufferedInputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("关闭PDF流", e);
		}
	}
	
	public static void main(String[] args) {
		PDFUtil pdfUtil = new PDFUtil("C:/Users/Administrator/Desktop/a.pdf");
		System.out.println(pdfUtil.getAuthor());
		for (int i = 0; i < pdfUtil.getCount(); i++) {
			pdfUtil.writeImage(i, "C:/Users/Administrator/Desktop/", "test", "test" + i + ".png");
		}
		
//		System.out.println(pdfUtil.getImageBase64(1));
		pdfUtil.close();
	}
}
