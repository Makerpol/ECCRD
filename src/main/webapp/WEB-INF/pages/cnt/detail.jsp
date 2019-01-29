<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<!-- 空白首页，跳转到真实首页 -->
<head>
<title>河南省软科学研究公共服务平台</title>
<jsp:include page="/common/index.jsp" />
</head>
<body>
<c:import url="/title.do"></c:import>
<table class="cnt_table" style="border: 0px;height: 30px;margin-left: unset;">
			<tr>
				<td><ol class="breadcrumb">
					<li><a href="${path }/">首页</a></li>
					<li><a href="${path }/cnt/list/1.shtml?model=${data.CNT_MODEL}">${data.CNT_MODEL_NAME }</a></li>
					<li><a href="${path }/cnt/list/1.shtml?model=${data.CNT_MODEL}&type=${data.CNT_TYPE}">${data.CNT_TYPE_NAME }</a></li>
					<li class="active">${data.CNT_TITLE }</li>
				</ol></td>
			</tr>
		</table>
		<table class="cnt_table" >
			<tr>
				<td class="cnt_title" style="width: 100%;" valign="top" align="center">
					${data.CNT_TITLE }
				</td>
			</tr>
			<tr>
				<td align="center">
				<!-- 来源可换成角色名，在sql中加入获取角色的函数 -->
					<div class="cnt_note">来源: <font style="color: red;">
					<c:choose>
					<c:when test="${data.CNT_FROM eq null}">${data.USER_NAME }</c:when>
					<c:otherwise>${data.CNT_FROM }</c:otherwise>
					</c:choose>
					</font>&nbsp;&nbsp;&nbsp;发布时间: <font style="color: red;">${data.INSERT_TIME_SIMPLE }</font></div>
				</td>
			</tr>
			<tr>
				<td >
					<div class="cnt">${data.CNT_CONTENT }</div>
				</td>
			</tr>
		</table>
<%-- <c:import url="/link.do"></c:import> --%>
<c:import url="/bottom.do"></c:import>
</body>
</html>
