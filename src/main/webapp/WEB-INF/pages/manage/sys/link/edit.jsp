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
	  <a >链接管理</a>
	  <a><cite>编辑链接</cite></a>
	</span>
	<br><br>
	
	<fieldset class="layui-elem-field">
	<legend><i class="layui-icon">&#xe63c;</i> 编辑链接信息 - ${data.LINK_TITLE }</legend>
	<div class="layui-field-box">
	<form action="${path }/link/edit.shtml" class="layui-form layui-form-pane" method="post" id="mainForm">
	<input type="hidden" value="${data.LINK_ID }" name="linkId">
	
	<div class="layui-form-item">
	
	<div class="layui-inline" customType="0">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 链接标题：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="title" placeholder="链接标题" autocomplete="off" class="layui-input" type="text" value="${data.LINK_TITLE }">
    </div>
    
    <div class="layui-inline" customType="0">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 英文链接标题：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="title_EN" placeholder="英文链接标题" autocomplete="off" class="layui-input" type="text" value="${data.LINK_TITLE_EN }">
    </div>
    
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 地址：</label>
    <div class="layui-input-inline" style="width:220px;">
   	 <input name="url" placeholder="链接地址" autocomplete="off" class="layui-input" type="text" value="${data.LINK_URL }">
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
		$.ajax({
			url: $('#mainForm').attr('action'),
			dataType:'json',
			data:$('#mainForm').serialize(),
			type:$('#mainForm').attr('method'),
			cache:false,
			success:function(data){
				if (data.code == 0) {
					window.parent.updateTab('hc.sys.link', true);
					window.parent.closeTab('hc.sys.link.edit_${data.LINK_ID}');
				} else {
					layer.msg(data.msg, {icon: 2, time:2000});
				}
			}
		})
	}
	</script>
	</body>

</html>