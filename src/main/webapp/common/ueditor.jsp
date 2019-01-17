<%@page import="com.zip.component.InitParams"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="${path }/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<!-- 变量成名需写在Ueditor引用文件的上面 -->
<script type="text/javascript">
window.UEDITOR_HOME_URL = "${path}/ueditor/";
window.BASE_PATH = "${path}";
baseURL = '${path}/ueditor/_src/';
window.ROWS = <%=InitParams.MAXROW%>

</script>
<script type="text/javascript" charset="utf-8" src="${path }/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${path }/ueditor/ueditor.parse.js"> </script>
<script type="text/javascript" charset="utf-8" src="${path }/ueditor/lang/zh-cn/zh-cn.js"></script>

