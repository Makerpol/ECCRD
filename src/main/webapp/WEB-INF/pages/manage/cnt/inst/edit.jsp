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
	</head>

	<body style="padding: 10px;">
	<span class="layui-breadcrumb" style="visibility: visible;">
	  <a ><i class="fa fa-home" aria-hidden="true"></i>平台</i></a>
	  <a >机构简介管理</a>
	  <a><cite>编辑简介</cite></a>
	</span>
	<br><br>
	
	<fieldset class="layui-elem-field">
	<legend><i class="layui-icon">&#xe63c;</i> 编辑简介信息</legend>
	<div class="layui-field-box">
	<form action="${path }/instMgr/edit.shtml" class="layui-form layui-form-pane" method="post" id="mainForm">
	<input type="hidden" value="${instId }" name="instId">
	<!-- 行 -->
	<div class="layui-form-item">
	
	<!-- 单元格 -->
	<div class="layui-inline">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 中文标题：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="title" placeholder="中文标题" autocomplete="off" class="layui-input" type="text" value="${data.INST_TITLE }">
    </div>
    </div>  
    
    <div class="layui-inline">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green"></span> 英文标题：</label>
    <div class="layui-input-inline" style="width:220px;">
    <input name="title_EN" placeholder="英文标题" autocomplete="off" class="layui-input" type="text" value="${data.INST_TITLE_EN }">
    </div>
    </div> 
    
    <!-- 单元格 -->
    <div class="layui-inline">
    <label class="layui-form-label" style="width:170px;"><span class="layui-badge-dot layui-bg-green" title="必填"></span> 类别：</label>
    <div class="layui-input-inline" style="width:220px;">
    <div class="layui-input-block" style="width:200px;margin-left:0px;" >
		<select name="type" id="type" initVal="${data.INST_TYPE }">
			<c:forEach items="${types }" var="t">
				<option value="${t.value.DICT_VALUE }" <c:choose><c:when test="${t.value.DICT_VALUE eq data.INST_TYPE}">selected="selected"</c:when></c:choose>>${t.value.DICT_NAME }</option>
			</c:forEach>
		</select>
	</div>
    </div>
    </div>
    
	</div>
	
	<div class="layui-form-item layui-form-text">
	
    <label class="layui-form-label" style="width:1300px;">文本内容：</label>
    <div class="layui-input-inline" style="width:1300px;">
    <input type="hidden" id="content"  name="content" value=""/></li>
    <textarea id="ueditorCnt" cols="" rows=""  style="border:0px;padding:0px;width:1300px; height:610px; overflow:hidden;">${data.INST_CONTENT }</textarea>
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
		var content = UE.getEditor('ueditorCnt').getContent();
		if(!content){
			$.messager.alert('消息','内容不能为空','info');
			return;
		}
		$('#content').val(content);
		$.ajax({
			url: $('#mainForm').attr('action'),
			dataType:'json',
			data:$('#mainForm').serialize(),
			type:$('#mainForm').attr('method'),
			cache:false,
			success:function(data){
				if (data.code == 0) {
					window.parent.updateTab('hc.cnt.instMgr', true);
					window.parent.closeTab('hc.cnt.instMgr.edit_${instId}');
				} else {
					layer.msg(data.msg, {icon: 2, time:2000});
				}
			}
		})
	}
	
	$(function(){
		var ue = UE.getEditor('ueditorCnt', {
			initialFrameWidth: 1300,
			initialFrameHeight: 500
		});
	});
	</script>
	</body>

</html>