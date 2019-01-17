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
	  <a><cite>编辑用户</cite></a>
	</span>
	<br><br>
	
	<fieldset class="layui-elem-field">
	<legend><i class="layui-icon">&#xe63c;</i> 编辑用户信息 - ${data.USER_NAME }</legend>
	<div class="layui-field-box">
	<form action="${path }/user/edit.shtml" class="layui-form layui-form-pane" method="post" id="mainForm">
	<input type="hidden" name="userId" value="${data.USER_ID }">
	
	<!-- 行 -->
	<div class="layui-form-item">
	
	<!-- 单元格 -->
	<div class="layui-inline">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 用户名：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="userName" placeholder="用户名" autocomplete="off" class="layui-input" type="text" value="${data.USER_NAME }" readonly="readonly">
    </div>
    </div>
    
    <!-- 单元格 -->
    <div class="layui-inline">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 角色：</label>
    <div class="layui-input-inline" style="width:220px;">
    <select name="roleId" lay-filter="aihao" initVal="${data.ROLE_ID }">
		<c:forEach items="${roles }" var="r">
		<option value="${r.ROLE_ID }">${r.ROLE_NAME }</option>
		</c:forEach>
	</select>
    </div>
    </div>
    
	</div>
	
	<div class="layui-form-item">
	
	<div class="layui-inline layui-form-text">
    <label class="layui-form-label" style="width:800px;">备注：</label>
    <div class="layui-input-inline" style="width:800px;">
    <textarea placeholder="备注信息" class="layui-textarea" name="note"></textarea>
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
	
	function onLoadCert(sn, dn) {
		layer.close(certTable);
		$('#userSn').val(sn);
		$('#divShow').html(dn);
	}
	
	function openCertTable() {
		// 打开证书选择窗口
		certTable = layer.open({
			type: 2,
			title: '选择证书',
			shade: 0.6,
			area: ['680px', '400px'],
			content: '${path}/sys/certTable.shtml?certType=1&curSn=${loginUser.CERT_SN}'	// certType证书类型（签名，加密），curSn当前用户的SN（添加即屏蔽）
		}); 
	}
	
	function saveData() {
		$.ajax({
			url: $('#mainForm').attr('action'),
			dataType:'json',
			data:$('#mainForm').serialize(),
			type:$('#mainForm').attr('method'),
			cache:false,
			success:function(data){
				if (data.code == 0) {
					window.parent.updateTab('hc.sys.user', true);
					window.parent.closeTab('hc.sys.user.edit_${userId}');
				} else {
					layer.msg(data.msg, {icon: 2, time:2000});
				}
			}
		})
	}
	</script>
	</body>

</html>