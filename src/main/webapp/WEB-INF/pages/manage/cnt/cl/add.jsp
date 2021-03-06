<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/hc.tld" prefix="hc"%>

<!DOCTYPE html>
<html>

	<head>
		<jsp:include page="/common/public.jsp"/>
		<jsp:include page="/common/ueditor.jsp"></jsp:include>
		<link rel="stylesheet" href="${path }/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="${path }/common/css/global.css" media="all">
		<link rel="stylesheet" href="${path }/font-awesome/css/font-awesome.min.css">
		<script type="text/javascript" src="${path }/common/js/image-upload-common.js"></script>
	</head>

	<body style="padding: 10px;">

	<form action="${path }/clMgr/add.shtml" class="layui-form layui-form-pane" method="post" id="mainForm">
	
	<!-- 行 -->
	<div class="layui-form-item">
    <div class="layui-inline">
    <label class="layui-form-label" style="width:120px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 模块：</label>
    <div class="layui-input-inline" style="width:220px;">
    <div class="layui-input-block" style="width:200px;margin-left:0px;">
		<select name="type" lay-filter="type" id="type">
		<c:forEach items="${types }" var="d">
		<option value="${d.value.DICT_VALUE }">${d.value.DICT_NAME }</option>
		</c:forEach>
		</select>
	</div>
    </div>
    </div>
    
    <!-- 单元格 -->
    <div class="layui-inline">
    <label class="layui-form-label" style="width:120px;">
    <span class="layui-badge-dot layui-bg-green" title="必填"></span> <i class="layui-icon">&#xe60d;</i>：</label>
    <div class="layui-input-inline" style="width:320px;">
    <div class="layui-input-block" style="width:200px;margin-left:0px;">
		<a class="layui-btn layui-btn-primary" href="#" id="selectImg"><i class="layui-icon">&#xe62f;</i> 选择图片</a>
		<input type="hidden" name="fileIds" id="imgText">
	</div>
	<div class="layui-form-mid layui-word-aux">共选择了<span id="imageCount">0</span>张图片</div>
    </div>
    </div>
	
	</div>
	
	<div class="layui-form-item">
	<a class="layui-btn" href="#" onclick="saveData()"><i class="layui-icon">&#xe616;</i> 保存</a>
	</div>
	
	</form>

	<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
	<!-- 页面加载完成后 -->
	<script type="text/javascript">
	var layer = layui.layer, 
		form = layui.form;
	
	function saveData() {
		$.ajax({
			url: $('#mainForm').attr('action'),
			dataType:'json',
			data:$('#mainForm').serialize(),
			type:$('#mainForm').attr('method'),
			cache:false,
			success:function(data){
				if (data.code == 0) {
					window.parent.closeAdd();
				} else {
					layer.msg(data.msg, {icon: 2, time:2000});
				}
			}
		})
	}
	
	$(function(){
		ImageEditor.initText('imgText', 'selectImg', undefined, 'imageCount');
	});
	</script>
	</body>

</html>