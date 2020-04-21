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
	  <a >PDF管理</a>
	  <a><cite>编辑文本</cite></a>
	</span>
	<br><br>
	
	<fieldset class="layui-elem-field">
	<legend><i class="layui-icon">&#xe63c;</i> 编辑PDF信息</legend>
	<div class="layui-field-box">
	<form action="${path }/pdfMgr/edit.shtml" class="layui-form layui-form-pane" method="post" id="mainForm">
	<input type="hidden" value="${pdfId }" name="pdfId">
	<!-- 行 -->
<%-- 	<div class="layui-form-item">
	
	<div class="layui-inline layui-form-text">
    <label class="layui-form-label" style="width:320px;">图片：</label>
    <div class="layui-input-inline" style="width:320px;">
    <img src="${path }${data.FILE_URL }" style="width:100%;" onclick="showPhoto()">
    </div>
    </div>
    </div> --%>
	
	<div class="layui-form-item">
	<!-- 单元格 -->
	<div class="layui-inline">
    <label class="layui-form-label" style="width:170px;">标题：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="title" placeholder="标题" autocomplete="off" maxlength="9" class="layui-input" type="text" value="${data.PDF_TITLE }">
    </div>
    </div>
    
    <!-- 单元格 -->
    <%-- <div class="layui-inline">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 类型：</label>
    <div class="layui-input-inline" style="width:220px;">
    <div class="layui-input-block" style="width:200px;margin-left:0px;" >
		<select name="type" lay-filter="type" id="type" initVal="${data.PDF_TYPE }">
		<c:forEach items="${types }" var="d">
		<option value="${d.value.DICT_VALUE }" <c:choose><c:when test="${t.value.DICT_VALUE eq data.PDF_TYPE}">selected="selected"</c:when></c:choose>>${d.value.DICT_NAME }</option>
		</c:forEach>
		</select>
	</div>
    </div>
    </div> --%>
    
    <div class="layui-inline">
    <label class="layui-form-label" style="width:170px;">引用连接：</label>
    <div class="layui-input-inline" style="width:520px;">
    <input name="url" placeholder="" autocomplete="off" class="layui-input" type="text" value="${data.PDF_URL }">
    </div>
    </div>
    
    <div class="layui-form-item">
	<a class="layui-btn" href="#" onclick="saveData()"><i class="layui-icon">&#xe616;</i> 保存</a>
	</div>
	
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
					window.parent.updateTab('hc.cnt.pdfMgr', true);
					window.parent.closeTab('hc.cnt.pdfMgr.edit_${pdfId}');
				} else {
					layer.msg(data.msg, {icon: 2, time:2000});
				}
			}
		})
	}
	
	function showPhoto() {
		var json = {
			title:'',
			id:${data.PDF_ID},
			data:[{alt:'',pid:${data.PDF_ID},src:'${path}${data.FILE_URL}',thumb:""}]
		}
		layer.photos({photos:json,anim:5})
	}
	
	$(function(){
	});
	</script>
	</body>

</html>