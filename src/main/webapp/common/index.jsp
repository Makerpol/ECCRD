<%@page import="com.zip.action.BaseAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("path", BaseAction.getBaseUrl());%>
<META http-equiv="pragma" content="no-cache"> 
<META http-equiv="Cache-Control" content="no-cache, must-revalidate">
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="ie-comp|ie-stand">
<meta name="full-screen" content="yes">
<META http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<style>
* {
	font-size:14px/1.5;
	font-family:Microsoft Yahei;
}

</style>
<link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="${path}/bootstrap/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="${path}/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${path}/common/css/index.css"/>
<link rel="stylesheet" type="text/css" href="${path}/common/css/title.css"/>
<script src="${path}/common/js/jquery.min.js"></script>
<!-- jquery -->
<script type="text/javascript" src="${path}/common/js/jquery.min.js"></script>
<script type="text/javascript" src="${path}/common/js/jquery.base64.js"></script>
<script type="text/javascript" src="${path}/common/js/jquery.timer.js"></script>
<script type="text/javascript" src="${path}/common/js/json2.js"></script>
<script type="text/javascript" src="${path}/common/js/index.js"></script>
<script type="text/javascript" src="${path}/bootstrap/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${path}/bootstrap/js/bootstrap-paginator.js" ></script>

<script  type="text/javascript">
function showTabModelList1(model){
	var type = $(".tab-model .active").attr("value");
	location.href = '${path }/cnt/list.shtml?model='+model+"&type="+type+"&page=1";
}

function showTabModelList2(){
	var val = $("#list2 .active").attr("value");
	if(val == 11){
		location.href = "${path }/cnt/list.shtml?model=8&type=11&page=1";
	}
	if(val == -1){
		location.href = "${path }/anno/list.shtml?page=1";
	}
}

function showTabModelList3(){
	var val = $("#list3 .active").attr("value");
	if(val == 9){
		location.href = "${path }/cnt/list.shtml?model=8&type=9&page=1";
	}
	if(val == 14){
		location.href = "${path }/cnt/list.shtml?model=13&type=14&page=1";
	}
}
</script>
