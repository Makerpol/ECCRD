package com.zip.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zip.component.InitParams;
import com.zip.util.SysUtil;

public class PageTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 94278455876378994L;

	private static Logger log = LoggerFactory.getLogger(PageTag.class);
	
	private InitParams initParams = SysUtil.getBean(InitParams.class);

	private int page = 0;		// 页码
	private int max = initParams.maxRow == 0 ? 20 : initParams.maxRow;		// 单页最大值
	private int total = 0;		// 总量
	private String javascript;		// 样式
	private String name;		// 页码提交的name

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String className) {
		this.javascript = className;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int doStartTag() throws JspException {
		int maxPage = 0;
		if (total % max == 0) {
			maxPage = total / max;
		} else {
			maxPage = total / max + 1;
		}
		StringBuilder sb = new StringBuilder();
		if (page == 0) {
			page = 1;
		}
		
		
		// 首页判断
		if (page == 1) {
			sb.append("<li class=\"disabled\">");
			sb.append("<a >First</a>");
		} else {
			sb.append("<li>");
			sb.append("<a href=\"javascript:");
			sb.append(javascript);
			sb.append("(1)\">First</a>");
		}
		sb.append("</li>");
		
		int start = 0;
		int end = 0;
		if (maxPage <= 7) {
			start = 1;
			end = maxPage;
		} else {
			if (page < 4) {
				start = 1;
				end = 7;
			} else if (page > maxPage - 4) {
				start = maxPage - 6;
				end = maxPage;
			} else {
				start = page - 3;
				end = page + 3;
			}
		}
		
		for (int i = start; i <= end; i++) {
			
			if (page == i) {
				sb.append("<li class=\"active\">");
				sb.append("<a >");
				sb.append(i);
				sb.append("</a >");
			} else {
				sb.append("<li>");
				sb.append("<a href=\"javascript:");
				sb.append(javascript);
				sb.append("(");
				sb.append(i);
				sb.append(")\">");
				sb.append(i);
				sb.append("</a>");
			}
			sb.append("</li>");
		}
		
		// 尾页
		if (page >= maxPage) {
			sb.append("<li class=\"disabled\">");
			page = maxPage;
			sb.append("<a >End</li>");
		} else {
			sb.append("<li>");
			sb.append("<a href='javascript:" + javascript + "(");
			sb.append(maxPage);
			sb.append(")' >End</a>");
		}
		sb.append("</li>");
//		sb.append("</td><td align=\"right\" style=\"font-size:12px;\">共<font color=red>");
//		sb.append(maxPage);
//		sb.append("</font>页，当前为第<font color=red>");
//		sb.append(page);
//		sb.append("</font>页，共<font color=red>");
//		sb.append(total);
//		sb.append("</font>条数据。</td>");
		log.debug("分页语句：" + sb.toString());
		try {
			pageContext.getOut().println(sb.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("分页错误", e);
		}
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
