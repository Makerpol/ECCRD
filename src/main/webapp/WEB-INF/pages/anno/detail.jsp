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
<table class="cnt_table" style="border: 0px;height: 30px;">
			<tr>
				<td><ol class="breadcrumb">
					<li><a href="${path }/">首页</a></li>
					<li><a href="${path }/anno/list/1.shtml">通知公告</a></li>
					<li class="active">${data.ANNOUN_TITLE }</li>
				</ol></td>
			</tr>
		</table>
		<table class="cnt_table">
			<tr>
				<td class="cnt_title" style="width: 100%;" valign="top" align="center">
					${data.ANNOUN_TITLE }
				</td>
			</tr>
			<tr>
				<td align="center">
				<!-- 来源可换成角色名，在sql中加入获取角色的函数 -->
					<div class="cnt_note">来源: <font style="color: red;">${data.USER_NAME }</font>&nbsp;&nbsp;&nbsp;发布时间: <font style="color: red;">${data.UPDATE_TIME_SIMPLE }</font>&nbsp;&nbsp;&nbsp;浏览: <font style="color: red;">${data.ANNOUN_COUNT }</font> 次</div>
				</td>
			</tr>
			<tr>
				<td >
					<div class="cnt">${data.ANNOUN_CONTENT }</div>
				</td>
			</tr>
		</table>
<c:import url="/link.do"></c:import>
<c:import url="/bottom.do"></c:import>
</body>
</html>
