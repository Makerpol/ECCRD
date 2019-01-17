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
	  <a >栏目管理</a>
	  <a><cite>编辑栏目</cite></a>
	</span>
	<br><br>
	
	<fieldset class="layui-elem-field">
	<legend><i class="layui-icon">&#xe63c;</i> 编辑栏目信息 - ${data.MT_NAME }</legend>
	<div class="layui-field-box">
	<form action="${path }/mtUtil/edit.shtml" class="layui-form layui-form-pane" method="post" id="mainForm">
	<input type="hidden" value="${data.MT_ID }" name="mtId">
	
	<div class="layui-form-item">
	
	<div class="layui-inline" customType="0">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 栏目名：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="mtName" placeholder="栏目名" autocomplete="off" class="layui-input" type="text" value="${data.MT_NAME }">
    </div>
    
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 父栏目：</label>
    <div class="layui-input-inline" style="width:220px;">
   	<select name="parent" lay-filter="aihao" initVal="${data.MT_PARENT }">
		<c:forEach items="${list}" var="p">
			<option value="${p.MT_ID }" >${p.MT_NAME }</option>
		</c:forEach>
	</select>
    </div>
    </div>
    
	</div>
	
	<div class="layui-form-item">
	
	<div class="layui-inline layui-form-text">
    <label class="layui-form-label" style="width:800px;">备注：</label>
    <div class="layui-input-inline" style="width:800px;">
    <textarea placeholder="备注信息" class="layui-textarea" name="mtNtote">${data.MT_NTOTE }</textarea>
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
					window.parent.updateTab('hc.sys.mtUtil', true);
					window.parent.closeTab('hc.sys.mtUtil.edit_${data.MT_ID}');
				} else {
					layer.msg(data.msg, {icon: 2, time:2000});
				}
			}
		})
	}
	</script>
	</body>

</html>