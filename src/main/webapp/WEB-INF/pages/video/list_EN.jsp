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
	<td style="padding-left: 10px;">
		<div class="mod_list">
			<div class="line"></div>
			<div class="m_title" style="width:85px;text-align:center;">Videos</div>
			<div style="width: 1000px;height: auto;padding-top: 10px;">

				<c:forEach items="${list }" var="l">

				<div style="text-align:center;width:210px;height:150px;float:left;margin-left:20px;margin-top: 35px;">
				<video controls="controls" controls="controls" style="width:200px;height:150px;">
					<source src="${path}${l.FILE_URL }" type="video/mp4" />
				</video>
				<p>${l.VIDEO_TITLE_EN }</p>
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
		
<c:import url="/bottom.do"></c:import>
<script type="text/javascript">
function toPage(page) {
	window.location.href='${path }/pdf/list_EN.shtml?page='+page;
}
</script>
</body>
</html>
