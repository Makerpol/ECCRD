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
			<div class="m_title">期刊类型</div>
			<div class="m_body">
				<ul>
					<c:forEach items="${types }" var="t">
					<li><i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i> 
					<a href="${path }/pdf/list.shtml?page=1&type=${t.value.DICT_VALUE}">${t.value.DICT_NAME }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</td>
	<td style="padding-left: 10px;">
		<div class="mod_list">
			<div class="line"></div>
			<div class="m_title" style="width:90px;">${currentType.DICT_NAME }</div>
			<div style="width: 790px;height: auto;padding-top: 10px;">

				<c:forEach items="${list }" var="l">

				<div style="text-align:center;width:135px;float:left;margin-left:20px;">
				<a target="_blank" href="${path}/pdf/web/viewer.html?file=${path}${l.FILE_URL }"><img src="${path}${l.PDF_PATH }" style="width: 135px;height: 165px;"/></a>
				<p>${l.PDF_TITLE }</p>
				</div>
				

				</c:forEach>
				<br style="clear:both;">
			</div>
			
		</div>
		<div style="padding-right:35px;">
		<nav style="float:right;" aria-label="Page navigation">
			<ul class="pagination" style="border-bottom:none;">
			<ssk:page javascript="toPage" total="${total }" page="${currentPage }"/>
			</ul>
		</nav>
		</div>
		
	</td>
<tr >

</tr>
</table>
<div>
				
</div>
		
<%-- <c:import url="/link.do"></c:import> --%>
<c:import url="/bottom.do"></c:import>
<script type="text/javascript">
function toPage(page) {
	window.location.href='${path }/pdf/list.shtml?page='+page;
}
</script>
</body>
</html>
