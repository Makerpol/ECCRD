package com.zip.util;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * 
 * @title SysUtil.java
 * @author ssk
 * @date 2017年12月6日
 * @description 系统工具类
 *
 */
public class SysUtil {
	
	private static Logger log = LoggerFactory.getLogger(SysUtil.class);
	
	/**
	 * 系统编码
	 */
	public final static String ENCODE = "UTF-8";
	
	/**
	 * 文件大小单位
	 */
	public final static String B = "B";		// byte
	
	public final static String K = "KB";		// kb
	
	public final static String M = "MB";		// mb
	
	public final static String G = "GB";		// gb
	
	public final static String T = "TB";		// gb
	
	public final static int UNIT = 1024;		// 换算单位
	
	public final static String LOGIN_SESSION = "loginUser";
	
	private static AnnotationConfigWebApplicationContext CONTEXT;
	
	/**
	 * 客户名称的正则，主要是各种特殊字符
	 */
	public static String REG_CINAME = "[`~!@#$%^&*+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\r\\n\\\"]";
	
	public static String LOCAL_IP = null;
	
	static {
		try {
			LOCAL_IP = getLocalHostLANAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置AnnotationConfigWebApplicationContext
	 * @param c
	 */
	public static void setContext(AnnotationConfigWebApplicationContext c) {
		CONTEXT = c;
		log.debug("初始化SysUtil的Context，方便spring没有管理的其他类获取Bean");
	}
	
	/**
	 * 通过类名获取Bean
	 * @param cls
	 * @return
	 */
	public static <T> T getBean(Class<T> cls) {
		return CONTEXT.getBean(cls);
	}

	/**
	 * 判断对象是否为null
	 * 如果是map或list类型，则验证size是否为0
	 * @param o
	 * @return
	 */
	public static boolean isNull(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof String) {
			return isNull(o.toString(), (str) -> str.equals(""));
		} else if (o instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) o;
			return isNull(map, (m) -> m.size() == 0);
		} else if (o instanceof List) {
			List<?> list = (List<?>) o;
			return isNull(list, (l) -> l.size() == 0);
		} else if (o instanceof Set) {
			Set<?> set = (Set<?>) o;
			return isNull(set, (s) -> s.size() == 0);
		} else {
			return false;
		}
	}
	
	private static boolean isNull(String str, Predicate<String> predicate) {
		return predicate.test(str);
	}
	
	private static boolean isNull(Map<?, ?> map, Predicate<Map<?, ?>> predicate) {
		return predicate.test(map);
	}
	
	private static boolean isNull(List<?> list, Predicate<List<?>> predicate) {
		return predicate.test(list);
	}
	
	private static boolean isNull(Set<?> set, Predicate<Set<?>> predicate) {
		return predicate.test(set);
	}
	
	/**
	 * 如果传入的O为null或空字符串，则返回v
	 * @param o
	 * @param v
	 * @return
	 */
	public static String nvl(Object o, String v) {
		return Optional.ofNullable(o).filter(s -> !isNull(o)).map(s -> s.toString()).orElse(v);
	}
	
	/**
	 * 通过递归获取目录下所有文件的大小
	 * @param path
	 * @return
	 */
	public static long getLength(File path) {
		long length = 0;
		if (path.isDirectory()) {
			File temp[] = path.listFiles();
			for (File file : temp) {
				length += getLength(file);
			}
		} else {
			length += path.length();
		}
		return length;
	}
	
	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}
	
	/**
	 * 生成业务标记
	 * @return
	 */
	public static String getApplyTag() {
		return MD5Util.md5(getUUID() + System.currentTimeMillis());
	}
	
	/**
	 * 获取文件长度所代表的单位
	 * @param length 文件长度
	 * @return
	 */
	public static String getLengthStr(Long length) {
		String value = "";
		if (length < 900 && length >= 0) {
			value = length + " " + B;
			return value;
		}
		double temp = Double.parseDouble(length + "") / UNIT;
		if (temp >= 0.9 && temp <= 999) {
			return String.format("%.2f", temp) + " " + K;
		}
		temp = Double.parseDouble(length + "") / UNIT / UNIT;
		if (temp >= 0.9 && temp <= 999) {
			return String.format("%.2f", temp) + " " + M;
		}
		temp = Double.parseDouble(length + "") / UNIT / UNIT / UNIT;
		if (temp >= 0.9 && temp <= 999) {
			return String.format("%.2f", temp) + " " + G;
		}
		temp = Double.parseDouble(length + "") / UNIT / UNIT / UNIT/ UNIT;
		if (temp >= 0.9 && temp <= 999) {
			return String.format("%.2f", temp) + " " + T;
		}
		return length + " " + B;
	}
	
	/**
	 * 判断字符长度是否在min和max之间
	 * @param val 要验证的字符串
	 * @param minLength 最小长度
	 * @param maxLength 最大长度
	 * @return
	 */
	public static boolean betweenLength(String val, int minLength, int maxLength) {
		if (SysUtil.isNull(val) && minLength <= 0) {
			return true;
		}
		return Optional.ofNullable(val).map(s -> s.length() >= minLength && s.length() <= maxLength).orElse(false);
	}
	
	/**
	 * 基础验证方法
	 * @param val 需要验证的结果
	 * @param p 正则
	 * @return
	 */
	public static boolean testReg(String val, String p) {
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(val);
		return matcher.matches();
	}
	
	/**
	 * 查找字符串中是否包含正则规定的内容
	 * @param val
	 * @param p
	 * @return
	 */
	public static boolean find(String val, String p) {
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(val);
		return matcher.find();
	}
	
	/**
	 * 查找字符串中是否包含正则规定的内容，并返回匹配的结果
	 * @param val
	 * @param p
	 * @return
	 */
	public static String findByReg(String val, String p) {
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(val);
		if (matcher.find()) {
			return matcher.group();
		}
		return "";
	}
	
	/**
	 * 验证邮箱
	 * @param val
	 * @return
	 */
	public static boolean testMail(String val) {
		return Optional.ofNullable(val).map(v -> testReg(v, "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$")).orElse(false);
	}
	
	/**
	 * 邮编
	 * @param val
	 * @return
	 */
	public static boolean testPostCode(String val) {
		return Optional.ofNullable(val).map(v -> testReg(v, "[1-9]\\d{5}")).orElse(false);
	}
	
	/**
	 * 验证座机号
	 * @param val
	 * @return
	 */
	public static boolean testPhone(String val) {
		return Optional.ofNullable(val).map(v -> testReg(v, "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|"
				+ "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)")).orElse(false);
	}
	
	/**
	 * 验证手机号
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
	 * 此方法中前三位格式有： 
	 * 13+任意数 
	 * 15+除4的任意数 
	 * 18+除1和4的任意数 
	 * 17+除9的任意数 
	 * 147
	 * @param val
	 * @return
	 */
	public static boolean testMobile(String val) {
		return Optional.ofNullable(val).map(v -> testReg(v, "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$")).orElse(false);
	}
	
	/**
	 * 是否为数字
	 * @param val
	 * @return
	 */
	public static boolean isNum(String val) {
		return Optional.ofNullable(val).map(v -> testReg(v, "^-?\\d+$")).orElse(false);
	}
	
	/**
	 * 把时间格式转换为字符
	 * @param date 要转换的时间
	 * @param p 格式
	 * @return
	 */
	public static String toChar(Date date, String p) {
		SimpleDateFormat sdf = new SimpleDateFormat(p);
		return sdf.format(date);
	}
	
	/**
	 * 把时间格式转换为字符
	 * @param date 要转换的时间
	 * @param p 格式
	 * @return
	 */
	public static String toChar(long date, String p) {
		SimpleDateFormat sdf = new SimpleDateFormat(p);
		return sdf.format(date);
	}
	
	/**
	 * 获取当前系统时间，并格式化 (1:yyyy-MM-dd HH:mm:ss; 2:yyyy年MM月dd日  HH:mm:ss; 3:yyyy-MM-dd; 其他任意Integer类型的数字/null:yyyy年MM月dd日)
	 * @param value
	 * @return
	 */
 	public static String getCurrentTime(Integer value) {
		switch (value) {
		case 1:
			return new DateTime().toString("yyyy-MM-dd HH:mm:ss");
		case 2:
			return new DateTime().toString("yyyy年MM月dd日 HH:mm:ss");
		case 3:
			return new LocalDate().toString();
		case 4:
			return new DateTime().toString("yyyyMMddHHmmssS");
		default:
			return new DateTime().toString("yyyy年MM月dd日");
		}
	}
	
	/**
	 * 根据给定日期，算出延期，新制后的时间
	 * @param months 需要延期几个月，若为负数，则是减少几个月
	 * @param date 需要延期的日期
	 * @return
	 */
	public static String getTimeDiff(Integer months,String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy年MM月dd日 HH:mm:ss"); 
		//Integer newMonths = Optional.of(months).filter(m ->(m>-1)).map(m ->m).orElse(0);
		return Optional.of(date)
				.map(d ->DateTime.parse(d, format))
				.map(d ->d.plusMonths(months))
				.map(d ->d.toString("yyyy年MM月dd日 HH:mm:ss"))
				.get();
	}
	
	/**
	 * 判断当前输入日期在系统日期之前还是之后
	 * @param date
	 * @return
	 */
	public static Boolean isAfterOrEqual(String date) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy年MM月dd日 HH:mm:ss");
		return Optional.of(date)
				.map(d ->DateTime.parse(d, format)).map(d ->d.isAfterNow()||d.isEqualNow()).get();
	}
		
	
	/**
	 * 让字符串的首字母大写
	 * @param str 首字母要大写的字符串
	 * @return
	 */
	public static String initcap(String str) {
		return Optional.ofNullable(str).map(s -> s.substring(1, s.length())).map(s -> str.substring(0, 1).toUpperCase() + s).orElse("");
	}
	
	// 正确的IP拿法，即优先拿site-local地址
	@SuppressWarnings("rawtypes")
	private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
		try {
			InetAddress candidateAddress = null;
			// 遍历所有的网络接口
			for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
				NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
				// 在所有的接口下再遍历IP
				for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
						if (inetAddr.isSiteLocalAddress()) {
							// 如果是site-local地址，就是它了
							return inetAddr;
						} else if (candidateAddress == null) {
							// site-local类型的地址未被发现，先记录候选地址
							candidateAddress = inetAddr;
						}
					}
				}
			}
			if (candidateAddress != null) {
				return candidateAddress;
			}
			// 如果没有发现 non-loopback地址.只能用最次选的方案
			InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
			if (jdkSuppliedAddress == null) {
				throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
			}
			return jdkSuppliedAddress;
		} catch (Exception e) {
			UnknownHostException unknownHostException = new UnknownHostException(
					"Failed to determine LAN address: " + e);
			unknownHostException.initCause(e);
			throw unknownHostException;
		}
	}
	
	public static void main(String[] args) {
//		System.out.println(getCurrentTime(4));
//		System.out.println(isAfterOrEqual("2018年12月31日 12:12:12"));
//		System.out.println(getTimeDiff(-1,"2018年12月31日 12:12:12"));
		System.out.println(testReg("测试?{}（）", "[`~!@#$%^&*+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\r\\n\\\"]"));
		System.out.println(findByReg("测试?{}（）", "[`~!@#$%^&*+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\r\\n\\\"]"));
		System.out.println(testPhone("0317-6549872"));
		System.out.println(testMobile("18236763152"));
	}
}
