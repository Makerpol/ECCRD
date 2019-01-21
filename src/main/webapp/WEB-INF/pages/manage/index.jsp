<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/public.jsp"/>
<title>欧中发展研究中心 - 后台管理系统</title>
<link href="${path }/common/css/login.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${path }/layui/css/layui.css" media="all" />
<link rel="stylesheet" href="${path }/common/css/global.css" media="all">
<link rel="stylesheet" href="${path }/font-awesome/css/font-awesome.min.css">

</head>

<body>
<div class="login_box">
      <div class="login_l_img"><img src="${path }/common/images/login-img.png" /></div>
      <div class="login">
          <div class="login_logo"><a href="#"><img src="${path }/common/images/login_logo.png" /></a></div>
          <div class="login_name" style="margin-top:40px;">
               <p>欧中发展研究中心</p>
          </div>
          <form method="post" action="${path }/sys/login.shtml" class="layui-form" id="loginForm">
				<div class="layui-form-item">
					<label class="layui-form-label"><i class="layui-icon" >&#xe612;</i></label>
					<div class="layui-input-inline">
						<input type="text" name="userName" placeholder="请输入用户名" autocomplete="off" class="layui-input" value="${userName }">
					</div>
					<div class="layui-form-mid layui-word-aux"></div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><i class="layui-icon" >&#xe60f;</i></label>
					<div class="layui-input-inline">
						<input type="password" name="pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
					</div>
					<div class="layui-form-mid layui-word-aux"></div>
				</div>
              <input value="登录" style="width:187px;margin-left:80px;" type="button" onclick="loginForm()">
              <div style="color:red;width:187px;margin-left:80px;font-size:14px;">${errorInfo }</div>
          </form>
      </div>
      <div class="copyright"></div>
</div>

<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
<script type="text/javascript">
var layer = layui.layer;
function closeCertTable() {
	layer.close(certTable);
}
function loginForm() {
	layer.msg('系统登录中，请稍后……', {icon: 6, time:0});
	$('#loginForm').submit();
}
</script>
</body>
</html>
