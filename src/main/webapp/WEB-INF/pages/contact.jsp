<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<!-- 空白首页，跳转到真实首页 -->
<head>
<title>河南省软科学研究公共服务平台</title>
<jsp:include page="/common/index.jsp" />
<style type="text/css">
table td{
	font-size:16px;
}

</style>
</head>
<body>
<c:import url="/title.do"></c:import>
<table class="list_table">
<tr>
	<td style="padding-left: 10px;">
		<div class="mod_list">
			<div class="line"></div>
			<div class="m_title">联系方式</div>
			<div class="m_body">
			<div style="margin-left:300px;">
			${contact.CON_CONTENT }
			</div>
			</div>
		</div>
	</td>
</tr>
	
</table>
<c:import url="/link.do"></c:import>
<c:import url="/bottom.do"></c:import>
</body>
</html>
