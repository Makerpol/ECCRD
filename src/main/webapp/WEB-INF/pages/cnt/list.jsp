<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="ssk"%>
<!DOCTYPE HTML>
<html>
<!-- 空白首页，跳转到真实首页 -->
<head>
<title>欧中发展研究中心</title>
<jsp:include page="/common/index.jsp" />
</head>
<body>
<c:import url="/title.do"></c:import>
<table class="list_table">
<tr>
	<td style="width: 260px;" valign="top">
		<div class="mod_div">
			<div class="line"></div>
			<div class="m_title">${model.MT_NAME}</div>
			<%-- <div class="m_body">
				<ul>
					<c:forEach items="${types }" var="t">
					<li><i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i> 
					<a href="${path }/cnt/list/1.shtml?model=${model.MT_ID	}&type=${t.MT_ID}">${t.MT_NAME }</a></li>
					</c:forEach>
				</ul>
			</div> --%>
		</div>
	</td>
	<td style="padding-left: 10px;" valign="top">
		<div class="mod_list" style="background-color: white;">
			<div class="line"></div>
			<div class="m_title"><c:if test="${type.MT_NAME == null || type.MT_NAME == ''}">全部</c:if><c:if test="${type.MT_NAME != null && type.MT_NAME != ''}">${type.MT_NAME}</c:if></div>
			<div class="m_body">
				<ul class="m_body_ul">
				<c:forEach items="${list }" var="l">
				<li><div class="left"><a href="${path }/cnt/detail/${l.CNT_MODEL}/${l.CNT_ID}.shtml" title="${l.CNT_TITLE }">${l.CNT_TITLE }</a></div><div class="right">${l.INSERT_TIME_SIMPLE }</div></li>
				</c:forEach>
				</ul>
			</div>
		</div>
	</td>
</tr>
<tr >
	<td></td>
	<td align="right" style="" style="height: 60px;">
			<nav aria-label="Page navigation">
				<ul class="pagination">
				<ssk:page javascript="toPage" total="${total }" page="${currentPage }"/>
				</ul>
			</nav>
		</td>
	</tr>
	
</table>
<%-- <c:import url="/link.do"></c:import> --%>
<c:import url="/bottom.do"></c:import>
<script type="text/javascript">
function toPage(page) {
	window.location.href="${path }/cnt/list/"+page+".shtml?model=${model.MT_ID	}";
}
</script>
</body>
</html>
