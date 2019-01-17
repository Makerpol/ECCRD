<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="top row"></div>
<div class="title row">
	<table>
		<tr>
			<th><a href="${path }/">首&nbsp;&nbsp;&nbsp;&nbsp;页</a></th>
			<c:forEach items="${models }" var="m">
			<td>|</td>
			<th><a href="${path }/cnt/list/1.shtml?model=${m.MT_ID}">${m.MT_NAME }</a></th>
			</c:forEach>
		</tr>
	</table>
</div>
