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
	  <a ><cite>视频管理</cite></a>
	</span>
	<br><br>
	
	<blockquote class="layui-elem-quote">
	<form class="layui-form" method="get" id="queryForm">
	<table style="width:100%;border-collapse:separate;border-spacing:10px;">
	
	<hc:url per="hc.cnt.pdfMgr.add" roles="admin">
	<tr>
	<td></td><td colspan="7"><a href="javascript:showAdd()" class="layui-btn"><i class="layui-icon">&#xe654;</i> 添加视频文件</a></td>
	</tr>
	</hc:url>
	
	<tr>
	<td align="right" width=100>中文标题：</td><td align="left" width=230>
	<input name="title" placeholder="" autocomplete="off" class="layui-input" type="text"></td>
	<%-- <td align="right" width=100>类型：</td><td width=230>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
		<select name="type" lay-filter="type" id="type">
			<option value="">全部</option>
			<c:forEach items="${types }" var="d">
			<option value="${d.value.DICT_VALUE }">${d.value.DICT_NAME }</option>
			</c:forEach>
		</select>
	</div>
	</td> --%>
	<td align="right" width=100>更新时间：</td>
	<td align="left" width=230>
	<div class="layui-inline">
	<div class="layui-input-inline" style="width: 230px;">
		<input class="layui-input" id="time" name="time" placeholder=" - " type="text">
	</div>
	</div>
	</td>
	<td></td>
	</tr>
	<tr>
	<td align="right" width=100>文件ID：</td><td align="left" width=230>
	<input name="pdfId" placeholder="" autocomplete="off" class="layui-input" type="text"></td>
	<td align="right" width=100>状态：</td><td width=230>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
		<select name="status" lay-filter="status">
			<option value="">全部状态</option>
			<c:forEach items="${status }" var="d">
			<option value="${d.value.DICT_VALUE }">${d.value.DICT_NAME }</option>
			</c:forEach>
		</select>
	</div>
	</td>
	
	<!-- <td align="right">操作人：</td><td>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
	<input name="user" placeholder="用户名" autocomplete="off" class="layui-input" type="text">
	</div>
	</td> -->
	
	</tr>
	
	<tr>
	<td align="right" colspan="6">
	<a class="layui-btn layui-btn-primary" href="#" onclick="clearForm('queryForm')"><i class="layui-icon">&#x1002;</i> 重置</a>
	<a class="layui-btn" href="#" onclick="reload(true)"><i class="layui-icon">&#xe615;</i> 搜索</a>
	</td>
	</tr>
	</table>
	</form>
	</blockquote>
	
	<table id="baseTable" lay-filter="baseTable">
	<thead>
	<th lay-data="{field:'VIDEO_ID',width:80,sort:true,fixed:'left'}">ID</th>
	<th lay-data="{field:'VIDEO_TITLE',width:500,sort:true,fixed:'left'}">中文标题</th>
	<th lay-data="{field:'VIDEO_TITLE_EN',width:500,sort:true}">英文标题</th>
	<th lay-data="{field:'VIDEO_IMAGE',width:320,templet:'#image'}">封面图片</th> 
	<th lay-data="{field:'INSERT_TIME',width:110,sort:true,templet:'#insertTime'}">添加时间</th>
	<th lay-data="{field:'UPDATE_TIME',width:110,sort:true,templet:'#updateTime'}">更新时间</th>
	<th lay-data="{field:'VIDEO_STATUS',width:90,sort:true,templet:'#status'}">状态</th>
	<th lay-data="{field:'opt',width:150,fixed:'right',templet:'#opt'}">操作</th>
	</thead>
	</table>
	
	<script type="text/javascript" id="image">
	<img src="${path}{{d.VIDEO_IMAGE_PATH}}" style="width:100px;" onclick="showPhoto({{d.VIDEO_ID}},'${path}{{d.VIDEO_IMAGE_PATH}}')" />
	</script>
	
	<script type="text/javascript" id="typeDiv">
	{{d.VIDEO_TYPE_NAME}}
	</script>
	
	<script type="text/javascript" id="userId">
	{{d.USER_NAME}}
	</script>
	
	<script type="text/javascript" id="opt">
	<hc:url per="hc.cnt.videoMgr.edit" roles="admin">
	<a href="#" onclick="javascript:window.parent.addTab('编辑文本信息 - {{d.VIDEO_TITLE}}', '${path }/videoMgr/editPage.shtml?videoId={{d.VIDEO_ID}}', '', 'hc.cnt.videoMgr.edit_{{d.VIDEO_ID}}')" class="layui-btn layui-btn-xs" title="编辑"><i class="layui-icon">&#xe642;</i></a>
	</hc:url>
	<hc:url per="hc.cnt.videoMgr.list" roles="admin">
	<a href="#" onclick="javascript:showVideo('${path}{{d.FILE_PATH}}')" class="layui-btn layui-btn-xs" title="预览" ><i class="layui-icon">&#xe705;</i></a>
	</hc:url>
	<hc:url per="hc.cnt.videoMgr.del" roles="admin">
	<a href="#" onclick="javascript:delData({{d.VIDEO_ID}}, '{{d.VIDEO_TITLE}}')" class="layui-btn layui-btn-xs layui-btn-danger" title="删除"><i class="layui-icon">&#xe640;</i></a>
	</hc:url>
	</script>
	
	<script type="text/javascript" id="status">
	<input type="checkbox" id="CL_{{d.VIDEO_ID}}" value="{{d.VIDEO_STATUS}}" curId="{{d.VIDEO_ID}}" name="{{d.VIDEO_TITLE}}" lay-skin="switch" lay-text="启用|禁用" title="停/启用" lay-filter="statusBtn" {{ d.VIDEO_STATUS == 1 ? 'checked' : '' }}>
	</script> 
	
	<script type="text/javascript" id="insertTime">
	{{d.INSERT_TIME_SIMPLE}}
	</script>
	
	<script type="text/javascript" id="updateTime">
	{{d.INSERT_TIME_SIMPLE}}
	</script>

	<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
	<!-- 页面加载完成后 -->
	<script type="text/javascript">
	var laydate = layui.laydate,
		layer = layui.layer,
		table = layui.table,
		form = layui.form,
		options, addpage;
	
	laydate.render({
		elem: '#time'
		,type: 'date'
		,range: true
	});
	
	// 监听排序
	table.on('sort(baseTable)', function(obj){
		options.initSort=obj;
		table.reload('baseTable', {
			initSort:obj,
			where:getWhere('queryForm', obj)
		});
	});
	
	/** 重新加载表格，是否从第一页开始 **/
	function reload(toFirst) {
		if (toFirst) {
			table.reload('baseTable', {
				where:getWhere('queryForm', options.initSort),
				page:{
					curr:1
				}
			});
		} else {
			table.reload('baseTable', {
				where:getWhere('queryForm', options.initSort)
			});
		}
		
	}
	
	function closeAdd() {
		layer.close(addpage);
		reload(true);
	}
	
	function showAdd() {
		addpage = layer.open({
			type: 2,
			title: '添加视频文件',
			shade: 0.6,
			area: ['655px', '512px'],
			content: '${path}/videoMgr/addPage.shtml'
		}); 
	}
	
	function showVideo(path){
		console.log(path);
		var loadstr='<video  controls="controls" controls="controls" style="width:600px;height:400px;"><source src="'+path+'" type="video/mp4"></source></video>';
		layer.open({
			type: 1,
			skin: 'layui-layer-rim', //加上边框
			maxmin: true,
			area: ['700px', '500px'], //宽高
			content: loadstr
		});
	}
	
	function delData(id, name) {
		if (name == undefined) {
			name = "无标题"
		}
		layer.confirm("你确定要删除视频文件 '" + name + "' 吗？", {
			btn : ['确定', '取消' ],
			icon:0
		}, function(index) {
			$.ajax({	// 确定
				url : '${path}/videoMgr/del.shtml',
				dataType : 'json',
				data:{videoId:id},
				type : 'POST',
				cache : false,
				success : function(data) {
					if (data.code == 0) {
						layer.close(index);
						reload();
					} else {
						layer.msg(data.msg, {icon: 2, time:2000});
					}
				}
			})
		});
	}
	
 	function showPhoto(id,src) {
		var json = {
			title:'',
			id:id,
			data:[{alt:'',pid:id,src:src,thumb:""}]
		}
		layer.photos({photos:json,anim:5})
	} 
	
	/* function showPdf(fileUrl){
		layer.open({
			title:"预览",
			type:2,
			area:['1200px','600px'],
			content:"${path}/pdf/web/viewer.html?file="+fileUrl
		})
	} */
	
	$(function(){
		
		/*****************************************************/
		// 1.将数据加载放到表格初始化之后
		// 2.指定url和参数，从远程获取数据
		// 这么做主要是为了避免默认加载数据，没有远程排序的字段导致表格数据逻辑错误的问题，同时为了方便默认值的加载，将表格初始化放到最后
		
		// 定义表格参数
		options = {
			page:{
				limit:20,
				limits:[10,20,30,50,100],
				groups:7
			},
			method:'get',
			request:{	// 分页的参数名称，页码和最大行
				pageName:'page',
				limitName:'rows'
			},
			loading:true,
			skin:'row',
			even:true,
			initSort:{
				field:'VIDEO_ID',
				type:'DESC'
			}
		};
		// 渲染表格
		table.init('baseTable', options);
		// 添加URL
		options.where=getWhere('queryForm', options.initSort);
		options.url='${path}/videoMgr/list.shtml';
		// 加载数据
		table.reload('baseTable',options);
		/*****************************************************/

	});
	</script>
	</body>

</html>