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

	<body style="padding: 0px;">
	
	<fieldset class="layui-elem-field">
	<form action="${path }/role/perEdit.shtml" class="layui-form" method="post" id="mainForm">
	<table style="width:100%;border-collapse:separate;border-spacing:10px;">
	<tr>
	<td>
	<div id="roleTree" style="width:100%;max-height:457px;overflow:auto;padding: 0px;"></div>
	</td>
	</tr>
	<c:if test="${type != 1 }">
	<tr>
	<td align="right">
	<hc:url per="hc.sys.role.perEdit" roles="admin">
	<a class="layui-btn" href="#" onclick="saveData()"><i class="layui-icon">&#xe616;</i> 保存</a>
	</hc:url>
	</td>
	</tr>
	</c:if>
	</table>
	</form>
	</fieldset>

	<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
	<script type="text/javascript" src="${path }/layui/layui-xtree.js"></script>
	<!-- 页面加载完成后 -->
	<script type="text/javascript">
	var layer = layui.layer, 
		form = layui.form,
		roleTree;
	
	function getParentVal(val) {
		return $(roleTree.GetParent(val)).val();
	}
	
	function saveData() {
		var ids = '';
		$.each(roleTree.GetChecked(), function(index, dom) {
			ids += $(dom).val() + ',';
			var menu = getParentVal($(dom).val());
			if (menu) {
				ids += menu + ',';
			}
			var root = getParentVal(menu);
			if (root) {
				ids += root + ',';
			}
		})
		$.ajax({
			url: $('#mainForm').attr('action'),
			dataType:'json',
			data:{roleId:${roleId},ids:ids},
			type:$('#mainForm').attr('method'),
			cache:false,
			success:function(data){
				if (data.code == 0) {
					layer.msg(data.msg, {icon: 1, time:2000});
				} else {
					layer.msg(data.msg, {icon: 2, time:2000});
				}
			}
		})
	}
	
	$(function(){
		roleTree = new layuiXtree({  
		      elem: 'roleTree'  //(必填) 放置xtree的容器id，不要带#号  
		      , form: form    //(必填) layui 的 from
		      <c:if test="${type != 1}">
		      , ckall: true
		      </c:if>
		      , icon: {
		    	  close: '&#xe622;'
		    	  , open: '&#xe7a0;'
		    	  , end: '&#xe857;'
		      }
		      , data: '${path}/role/perList.shtml?type=${type}&roleId=${roleId}&r='+Math.random()  //(必填) 数据接口，需要返回以上结构的json字符串（数据格式在下面）  
		});
	})
	</script>
	</body>

</html>