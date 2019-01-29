
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
<table class="list_table">
<tr>
	<td style="width: 260px;" valign="top">
		<div class="mod_div">
			<div class="line"></div>
			<div class="m_title">机构概况</div>
			<div class="m_body">
				<ul>
					<c:forEach items="${types }" var="t">
					<li><i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i> 
					<a href="${path }/inst/index.shtml?type=${t.value.DICT_VALUE}">${t.value.DICT_NAME }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</td>
	<td style="padding-left: 10px;">
		<div class="mod_list" style="background-color: white;">
			<div class="line"></div>
			<%-- <div class="m_title">${type.MT_NAME}</div> --%>
			<div class="m_body" style="padding: 10px 20px;min-height:500px;">
				<c:forEach items="${list }" var="l">
				<div class="cnt">${l.INST_CONTENT }</div>
				</c:forEach>
			</div>
		</div>
	</td>
</tr>
	
</table>
<%-- <c:import url="/link.do"></c:import> --%>
<c:import url="/bottom.do"></c:import>
</body>
</html>
