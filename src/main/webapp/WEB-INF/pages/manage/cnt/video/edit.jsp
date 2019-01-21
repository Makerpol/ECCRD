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
	<span class="layui-breadcrumb" style="visibility: visible;">
	  <a ><i class="fa fa-home" aria-hidden="true"></i>平台</i></a>
	  <a >视频管理</a>
	  <a><cite>编辑文本</cite></a>
	</span>
	<br><br>
	
	<fieldset class="layui-elem-field">
	<legend><i class="layui-icon">&#xe63c;</i> 编辑视频信息</legend>
	<div class="layui-field-box">
	<form action="${path }/videoMgr/edit.shtml" class="layui-form layui-form-pane" method="post" id="mainForm">
	<input type="hidden" value="${videoId }" name="videoId">
	
	<div class="layui-form-item">
	<!-- 单元格 -->
	<div class="layui-inline">
    <label class="layui-form-label" style="width:170px;">标题：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="title" placeholder="标题" autocomplete="off" maxlength="9" class="layui-input" type="text" value="${data.VIDEO_TITLE }">
    </div>
    </div>
    
    <!-- 单元格 -->
    <div class="layui-form-item">
    <div class="layui-inline">
    <label class="layui-form-label" style="width:120px;">
    <span class="layui-badge-dot layui-bg-green" title="必填"></span> <i class="layui-icon">&#xe60d;</i>：</label>
    <div class="layui-input-inline" style="width:320px;">
    <div class="layui-input-block" style="width:200px;margin-left:0px;">
		<a class="layui-btn layui-btn-primary" href="#" id="selectImg"><i class="layui-icon">&#xe62f;</i> 选择图片</a>
		<input type="hidden" name="image" id="imgText">
	</div>
	<div class="layui-form-mid layui-word-aux">共选择了<span id="imageCount">0</span>张图片</div>
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
					window.parent.updateTab('hc.cnt.videoMgr', true);
					window.parent.closeTab('hc.cnt.videoMgr.edit_${videoId}');
				} else {
					layer.msg(data.msg, {icon: 2, time:2000});
				}
			}
		})
	}
	
	function showPhoto() {
		var json = {
			title:'',
			id:${data.VIDEO_ID},
			data:[{alt:'',pid:${data.VIDEO_ID},src:'${path}${data.VIDEO_IMAGE_PATH}',thumb:""}]
		}
		layer.photos({photos:json,anim:5})
	}
	
	$(function(){
		ImageEditor.initText('imgText', 'selectImg', undefined, 'imageCount');
	});
	</script>
	</body>

</html>