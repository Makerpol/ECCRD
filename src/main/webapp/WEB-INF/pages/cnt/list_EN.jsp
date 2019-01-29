<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="ssk"%>
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
			<div class="m_title">${model.MT_NAME_EN}</div>
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
			<div class="m_title"><c:if test="${type.MT_NAME == null || type.MT_NAME == ''}">All</c:if><c:if test="${type.MT_NAME != null && type.MT_NAME != ''}">${type.MT_NAME}</c:if></div>
			<div class="m_body">
				<ul class="m_body_ul">
				<c:forEach items="${list }" var="l">
				<c:if test="${not empty  l.CNT_TITLE_EN}">
				<li><div class="left"><a href="${path }/cnt/detailEN/${l.CNT_MODEL}/${l.CNT_ID}.shtml" title="${l.CNT_TITLE_EN }">${l.CNT_TITLE_EN }</a></div><div class="right">${l.INSERT_TIME_SIMPLE }</div></li>
				</c:if>
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
<c:import url="/bottom_EN.do"></c:import>
<script type="text/javascript">
function toPage(page) {
	window.location.href="${path }/cnt/listEN/"+page+".shtml?model=${model.MT_ID	}";
}
</script>
</body>
</html>
