package com.zip.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

/**
 * 
 * @author ssk
 *
 */
public class HttpUtil implements AutoCloseable {

	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

	private CloseableHttpClient httpClient;
	private CloseableHttpResponse response;
	private String url;
	private String encode;

	private String fileName;
	private boolean downTag = true;

	/**
	 * 
	 * @param url
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public HttpUtil(String url) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		this(url, ENCODE.UTF8);
	}

	/**
	 * 
	 * @param url
	 * @param ec
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public HttpUtil(String url, ENCODE ec) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		this(url, ec.getEncode());
	}
	
	public HttpUtil(String url, String ec) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		this.url = url;
		this.encode = ec;
		if (url.startsWith("https")) {
			httpClient = getSSLClient();
		} else {
			httpClient = HttpClients.createDefault();
		}
	}

	/**
	 * 
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	CloseableHttpClient getSSLClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustStrategy() {

			/**
			 * 不验证证书，直接通过
			 */
			@Override
			public boolean isTrusted(X509Certificate[] x509Certs, String sigAlgName) throws CertificateException {
				// TODO Auto-generated method stub
				// for (X509Certificate cert : x509Certs) {
				//
				// }
				return true;
			}
		});

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(builder.build(),
				new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" }, null, NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory()).register("https", socketFactory).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
		connectionManager.setMaxTotal(200);
		return HttpClients.custom().setSSLSocketFactory(socketFactory).setConnectionManager(connectionManager)
				.setConnectionManagerShared(true).build();
	}

	/**
	 * 请求方法
	 */
	public enum METHOD {
		POST, GET;
	}

	/**
	 * 请求编码
	 */
	public enum ENCODE {
		UTF8("UTF-8"), GBK("GBK"), GB2312("GB2312");

		private ENCODE(String encode) {
			this.encode = encode;
		}

		private String encode;

		public String getEncode() {
			return encode;
		}

		public void setEncode(String encode) {
			this.encode = encode;
		}

	}

	/**
	 * 生成get方法
	 * 
	 * @param url
	 * @param param
	 * @param ec
	 * @return
	 */
	private HttpGet get(Map<String, String> param) {
		if (SysUtil.isNull(url)) {
			return null;
		}
		StringBuilder paramStr = new StringBuilder();
		// 拼接参数
		if (!SysUtil.isNull(param)) {
			param.entrySet().stream().map(s -> s.getKey() + "=" + getEncode(s.getValue()))
					.collect(Collectors.joining("&"));
		}
		String params = "";
		if (paramStr.length() > 0 && paramStr.toString().endsWith("&")) {
			params = paramStr.substring(0, paramStr.length() - 1);
		} else {
			params = paramStr.toString();
		}
		if (!url.endsWith("?")) {
			return new HttpGet(url + "?" + params);
		} else {
			return new HttpGet(url + params);
		}
	}

	/**
	 * url编码字符串
	 * 
	 * @param str
	 * @return
	 */
	private String getEncode(String str) {
		if (SysUtil.isNull(str)) {
			return "";
		}
		try {
			return URLEncoder.encode(str, encode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	/**
	 * 获取post方法
	 * 
	 * @param param
	 * @param ec
	 * @return
	 */
	private HttpPost post(Map<String, String> param) {
		if (SysUtil.isNull(url)) {
			return null;
		}
		HttpPost post = new HttpPost(url);
		if (!SysUtil.isNull(param)) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (String key : param.keySet()) {
				if (!SysUtil.isNull(param.get(key))) {
					list.add(new BasicNameValuePair(key, param.get(key)));
				}
			}
			try {
				post.setEntity(new UrlEncodedFormEntity(list, encode));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return post;
			}
		}
		return post;
	}
	
	/**
	 * 字符串消息体的post方法
	 * @param val
	 * @return
	 */
	private HttpPost post(String val) {
		if (SysUtil.isNull(url)) {
			return null;
		}
		HttpPost post = new HttpPost(url);
		if (!SysUtil.isNull(val)) {
			StringEntity entity = new StringEntity(val, encode);
			entity.setContentEncoding(encode);
			entity.setContentType("application/json");
			post.setEntity(entity);
		}
		return post;
	}

	/**
	 * 默认编码UTF8编码
	 * 
	 * @param param
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void execute(Map<String, String> param) throws ClientProtocolException, IOException {
		execute(param, METHOD.GET);
	}
	
	/**
	 * 提交字符串消息体
	 * @param val
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void execute(String val) throws ClientProtocolException, IOException {
		HttpUriRequest request = post(val);
		response = httpClient.execute(request);
	}
	
	/**
	 * 提交json消息体
	 * @param json
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void execute(JSONObject json) throws ClientProtocolException, IOException {
		execute(json.toJSONString());
	}

	/**
	 * 
	 * @param param
	 * @param mt
	 * @param ec
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void execute(Map<String, String> param, METHOD mt) throws ClientProtocolException, IOException {
		HttpUriRequest request = null;
		switch (mt) {
		case POST:
			request = post(param);
			break;
		case GET:
		default:
			request = get(param);
			break;
		}
		setHeader(request);
		response = httpClient.execute(request);
	}

	/**
	 * 设定请求的头信息 设定支持的编码及格式信息
	 * 
	 * @param request
	 */
	private void setHeader(HttpRequest request) {
		request.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		request.setHeader("Accept-Encoding", "gzip, deflate");
		request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		request.setHeader("Connection", "keep-alive");
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
		request.setHeader("Accept", "");
	}

	/**
	 * 返回http请求状态
	 * 
	 * @return
	 */
	public int status() {
		return response.getStatusLine().getStatusCode();
	}

	/**
	 * 获取相应的header
	 * 
	 * @return
	 */
	public Map<String, String> getHeader() {
		Header header[] = response.getAllHeaders();
		Map<String, String> headerMap = new HashMap<>();
		Stream.of(header).forEach(h -> headerMap.put(h.getName(), h.getValue()));
		return headerMap;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public String getReturnStr() throws ParseException, IOException {
		if (SysUtil.isNull(response)) {
			return null;
		}
		return EntityUtils.toString(response.getEntity(), encode);
	}

	/**
	 * 下载文件
	 * 
	 * @param path
	 *            文件夹名称
	 */
	public void down(String path,String filename) {
		if(SysUtil.isNull(filename)) {
			// 获取文件名
			Header header = response.getFirstHeader("Content-Disposition");
			fileName = "";
			if (!SysUtil.isNull(header) && header.getElements().length == 1) {
				// 从header中获取文件名
				NameValuePair pair = header.getElements()[0].getParameterByName("filename");
				if (!SysUtil.isNull(pair)) {
					fileName = pair.getValue();
				} else {
					System.out.println("bbbb");
				}
			} else {
				// 从url中获取文件名
				fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
			}
			if (SysUtil.isNull(fileName)) {
				fileName = SysUtil.getUUID();
			}
		}else {
			fileName=filename;
		}

		/**
		 * 创建文件夹
		 */
		File file = new File(path.endsWith(File.separator) ? (path + fileName) : (path + File.separator + fileName));
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		log.info("HttpUtil下载路径："+file.toString());
		try {
			HttpEntity entity = response.getEntity();
			BufferedInputStream inputStream = null;
			BufferedOutputStream outputStream = null;
			try {
				inputStream = new BufferedInputStream(entity.getContent());
				outputStream = new BufferedOutputStream(new FileOutputStream(file));
				byte temp[] = new byte[1024 * 2];
				int index = 0;
				while ((index = inputStream.read(temp, 0, temp.length)) != -1) {
					if (!downTag) {
						throw new Exception("下载被中断");
					}
					outputStream.write(temp, 0, index);
				}
			} finally {
				response.close();
				outputStream.close();
				inputStream.close();
			}
		} catch (Exception e) {
			log.error("下载失败", e);
			file.deleteOnExit();;
			
			}
		}

	public void stopDown() {
		this.downTag = false;
	}

	public String getFileName() {
		return this.fileName;
	}

	/**
	 * 重写自动关闭方法
	 */
	@Override
	public void close() throws Exception {
		if (!SysUtil.isNull(httpClient)) {
			httpClient.close();
		}
		if (!SysUtil.isNull(response)) {
			response.close();
		}
	}

	public static void main(String[] args) {
		/*try (HttpUtil util = new HttpUtil("https://v.qq.com/x/search/")) {
			Map<String, String> param = new HashMap<>();
			param.put("q", "欢乐颂2");
			param.put("stag", "7");
			param.put("smartbox_ab", "");
			util.execute(param);
			if (util.status() == 200) {
				System.out.println(util.getReturnStr());
			} else {
				System.out.println("请求失败：" + util.status());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
		 try(HttpUtil util = new HttpUtil("http://www.9611111.com/SearchCert/downloadFile/hnca2048")) {
			 util.execute(Maps.newHashMap());
			 if (util.status() == 200) {
				
				 util.down("F:\\develop\\tomcat\\apache-tomcat-8.0.47\\webapps\\yyszpt\\upload" + File.separator + "crl","");
				}
		} catch (Exception e) {
		}
		 log.info("更新CRL列表");

	}
}
