<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="ssk"%>
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
	<td style="padding-left: 10px;width: 1024px;">
		<div class="mod_list">
			<div class="line"></div>
			<div class="m_title">通知公告</div>
			<div class="m_body">
				<ul>
				<c:forEach items="${list }" var="l">
				<li><div class="left" style="width: auto;"><a href="${path }/anno/detail.shtml?id=${l.ANNOUN_ID}" title="${l.ANNOUN_TITLE }">${l.ANNOUN_TITLE }</a></div><div class="right">${l.UPDATE_TIME_SIMPLE }</div></li>
				</c:forEach>
				</ul>
			</div>
		</div>
	</td>
</tr>
<tr >
	<!-- <td></td> -->
	<td align="right" style="" style="height: 60px;">
			<nav aria-label="Page navigation">
				<ul class="pagination">
				<ssk:page javascript="toPage" total="${total }" page="${currentPage }"/>
				</ul>
			</nav>
		</td>
	</tr>
</table>
<c:import url="/link.do"></c:import>
<c:import url="/bottom.do"></c:import>
<script type="text/javascript">
function toPage(page) {
	window.location.href='${path }/anno/list.shtml?page='+page;
}
</script>
</body>
</html>
