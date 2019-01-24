<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zip.action.BaseAction"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row" style="margin-top: 10px;border: 1px solid #F0F0F0;">
	<div class="div-link">相关连接</div>
	<c:forEach items="${links }" var="l">
	<div class="col-md-2 link"><a target="_blank" href="${l.LINK_URL }">${l.LINK_TITLE_EN }</a></div>
	</c:forEach>
</div>