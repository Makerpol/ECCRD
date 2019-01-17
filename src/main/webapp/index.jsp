<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<!-- 空白首页，跳转到真实首页 -->
<head>
<title>欧中发展研究中心</title>
<jsp:include page="/common/index.jsp" />
</head>
<body>
<c:import url="/title.do"></c:import>
<c:import url="/index.do"></c:import>
<%-- <c:import url="/link.do"></c:import> --%>
<c:import url="/bottom.do"></c:import>
</body>
</html>
