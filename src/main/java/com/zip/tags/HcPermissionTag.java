package com.zip.tags;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.zip.util.SysUtil;

public class HcPermissionTag extends TagSupport {

	private static final long serialVersionUID = 94278455876378994L;
	
	/**
	 * 权限名称
	 */
	private String per;
	
	/**
	 * 角色名称
	 */
	private String roles;
	
	/**
	 * 条件
	 */
	private String condition;
	
	public String getPer() {
		return per;
	}
	public void setPer(String per) {
		this.per = per;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	/**
	 * 相应函数，通过条件，确定临时条件是否为true
	 */
	private Function<String, Boolean> funCond = (cond) -> {
		return Optional.ofNullable(cond)
				.filter(s -> !SysUtil.isNull(s))
				.map(s -> s.toLowerCase())
				.map(s -> s.equals("and"))
				.orElse(false);
	};
	
	/**
	 * 权限验证
	 */
	public int doStartTag() throws JspException {
		Subject currentUser = SecurityUtils.getSubject();
		roles = Optional.ofNullable(roles).map(s -> s.trim()).orElse("");
		List<String> roleList = Arrays.asList(roles.split(","))
				.stream().filter(s -> !SysUtil.isNull(s))
				.map(s -> s.trim())
				.collect(Collectors.toList());
		if (!funCond.apply(condition)) {
			// 如果条件是or，则包含任何一个权限或角色都可返回正常
			if (currentUser.isPermitted(per)) {
				return EVAL_BODY_INCLUDE;
			}
			for (String tempRole : roleList) {
				if (currentUser.hasRole(tempRole)) {
					return EVAL_BODY_INCLUDE;
				}
			}
		} else {
			if (currentUser.isPermitted(per)) {
				for (String tempRole : roleList) {
					if (currentUser.hasRole(tempRole)) {
						return EVAL_BODY_INCLUDE;
					}
				}
			}
		}
		return SKIP_BODY;		// 否则不展示
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
