
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
<table class="list_table">
<tr>
	<td style="width: 260px;" valign="top">
		<div class="mod_div">
			<div class="line"></div>
			<div class="m_title">About ECCRD</div>
			<div class="m_body">
				<ul>
					<c:forEach items="${types }" var="t">
					<c:if test="${not empty t.value.DICT_NAME_EN }">
						<li><i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i> 
						<a href="${path }/inst/indexEN.shtml?type=${t.value.DICT_VALUE}">${t.value.DICT_NAME_EN }</a></li>
					</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</td>
	<td style="padding-left: 10px;">
		<div class="mod_list">
			<div class="line"></div>
			<div class="m_body" style="padding: 10px 20px;min-height:500px;">
				<c:forEach items="${list }" var="l">
				<div class="cnt">${l.INST_CONTENT_EN }</div>
				</c:forEach>
			</div>
		</div>
	</td>
</tr>
	
</table>
<c:import url="/bottom_EN.do"></c:import>
</body>
</html>
