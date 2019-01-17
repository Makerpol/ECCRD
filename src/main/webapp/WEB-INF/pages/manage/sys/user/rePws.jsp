<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/hc.tld" prefix="hc"%>

<!DOCTYPE html>
<html>

	<head>
		<jsp:include page="/common/public.jsp"/>
		<link rel="stylesheet" href="${path }/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="${path }/common/css/global.css" media="all">
		<link rel="stylesheet" href="${path }/font-awesome/css/font-awesome.min.css">
	</head>

	<body style="padding: 10px;">
	<span class="layui-breadcrumb" style="visibility: visible;">
	  <a ><i class="fa fa-home" aria-hidden="true"></i>平台</i></a>
	  <a >用户管理</a>
	  <a><cite>修改用户密码</cite></a>
	</span>
	<br><br>
	
	<fieldset class="layui-elem-field">
	<legend><i class="layui-icon">&#xe63c;</i> 修改用户密码 - ${data.USER_NAME }</legend>
	<div class="layui-field-box">
	<form action="${path }/user/rePws.shtml" class="layui-form layui-form-pane" method="post" id="mainForm">
	<input type="hidden" name="userId" value="${data.USER_ID }">
	
	<!-- 行 -->
	<div class="layui-form-item">
	
	<!-- 单元格 -->
	<div class="layui-inline">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 新密码：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="userPass" placeholder="新密码" autocomplete="off" class="layui-input" type="password">
    </div>
    </div>
    </div>
    
    <div class="layui-form-item">
    <!-- 单元格 -->
    <div class="layui-inline">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 重复密码：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="password" placeholder="重复密码" class="layui-input" type="password">
    </div>
    </div>
	</div>
	
	<div class="layui-form-item">
	<a class="layui-btn" href="#" onclick="saveData()"><i class="layui-icon">&#xe616;</i> 保存</a>
	</div>
	
	</form>
	</div>
	</fieldset>

	<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
	<!-- 页面加载完成后 -->
	<script type="text/javascript">
	var layer = layui.layer, 
		form = layui.form,
		certTable;
	
	function saveData() {
		if($(".userPass").val()!=$(".password").val()){
			layer.msg("两次输入密码不一致", {icon: 2, time:2000});
			return;
		}
		
		$.ajax({
			url: $('#mainForm').attr('action'),
			dataType:'json',
			data:$('#mainForm').serialize(),
			type:$('#mainForm').attr('method'),
			cache:false,
			success:function(data){
				if (data.code == 0) {
					layer.msg("密码修改成功", {icon: 1, time:2000});
					
				} else {
					layer.msg(data.msg, {icon: 3, time:2000});
				}
			}
		})
	}
	</script>
	</body>

</html>