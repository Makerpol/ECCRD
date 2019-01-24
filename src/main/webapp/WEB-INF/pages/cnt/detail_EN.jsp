<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<!-- 空白首页，跳转到真实首页 -->
<head>
<title>EURO CHINESE CENTRE FOR RESEARCH AND DEVELOPMENT</title>
<jsp:include page="/common/index.jsp" />
</head>
<body>
<c:import url="/title_EN.do"></c:import>
<table class="cnt_table" style="border: 0px;height: 30px;margin-left: unset;">
			<tr>
				<td><ol class="breadcrumb">
					<li><a href="${path }/english.shtml">Home Page</a></li>
					<li><a href="${path }/cnt/listEN.shtml?model=${data.CNT_MODEL}">${data.CNT_MODEL_NAME_EN }</a></li>
					<li class="active">${data.CNT_TITLE_EN }</li>
				</ol></td>
			</tr>
		</table>
		<table class="cnt_table" >
			<tr>
				<td class="cnt_title" style="width: 100%;" valign="top" align="center">
					${data.CNT_TITLE_EN }
				</td>
			</tr>
			<tr>
				<td align="center">
				<!-- 来源可换成角色名，在sql中加入获取角色的函数 -->
					<div class="cnt_note">Editor: <font style="color: red;">${data.USER_NAME_EN }</font>&nbsp;&nbsp;&nbsp;Time: <font style="color: red;">${data.UPDATE_TIME_SIMPLE }</font></div>
				</td>
			</tr>
			<tr>
				<td >
					<div class="cnt">${data.CNT_CONTENT_EN }</div>
				</td>
			</tr>
		</table>
<%-- <c:import url="/link.do"></c:import> --%>
<c:import url="/bottom_EN.do"></c:import>
</body>
</html>
