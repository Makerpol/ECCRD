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
	  <a ><cite>机构简介管理</cite></a>
	</span>
	<br><br>
	
	<blockquote class="layui-elem-quote">
	<form class="layui-form" method="get" id="queryForm">
	<table style="width:100%;border-collapse:separate;border-spacing:10px;">
	
	<hc:url per="hc.cnt.instMgr.add" roles="admin">
	<tr>
	<td></td><td colspan="7"><a href="javascript:window.parent.addTab('添加简介', '${path }/instMgr/addPage.shtml', '', 'hc.cnt.instMgr.add')" class="layui-btn"><i class="layui-icon">&#xe654;</i> 添加简介</a></td>
	</tr>
	</hc:url>
	
	<tr>
	<td align="right" width=100>标题：</td><td align="left" width=230>
	<input name="title" placeholder="" autocomplete="off" class="layui-input" type="text"></td>

	<td align="right">类型：</td><td>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
		<select name="type" lay-filter="type" id="type">
			<option value="">全部</option>
			<c:forEach items="${types }" var="t">
			<option value="${t.value.DICT_VALUE }">${t.value.DICT_NAME }</option>
			</c:forEach>
		</select>
	</div>
	</td>
	<td align="left" >
	<a class="layui-btn layui-btn-primary" href="#" onclick="clearForm('queryForm')"><i class="layui-icon">&#x1002;</i> 重置</a>
	<a class="layui-btn" href="#" onclick="reload(true)"><i class="layui-icon">&#xe615;</i> 搜索</a>
	</td>
	</tr>
	</table>
	</form>
	</blockquote>
	
	<table id="baseTable" lay-filter="baseTable">
	<thead>
	<th lay-data="{field:'INST_ID',width:80,sort:true,fixed:'left'}">ID</th>
	<th lay-data="{field:'INST_TITLE',width:700,sort:true,fixed:'left'}">标题</th>
	<th lay-data="{field:'USER_ID',width:110,templet:'#userId'}">操作人</th>
	<th lay-data="{field:'INST_TYPE',width:110,sort:true,templet:'#typeDiv'}">类别</th>
	<th lay-data="{field:'INSERT_TIME',width:110,sort:true,templet:'#insertTime'}">添加时间</th>
	<th lay-data="{field:'UPDATE_TIME',width:110,sort:true,templet:'#updateTime'}">更新时间</th>
	<th lay-data="{field:'INST_COUNT',width:110,sort:true,templet:'#cntCount'}">访问量</th>
	<th lay-data="{field:'opt',width:120,fixed:'right',templet:'#opt'}">操作</th>
	</thead>
	</table>

	<script type="text/javascript" id="typeDiv">
	{{d.INST_TYPE_NAME}}
	</script>
	
	<script type="text/javascript" id="userId">
	{{d.USER_NAME}}
	</script>
	
	<script type="text/javascript" id="opt">
	<hc:url per="hc.cnt.instMgr.edit" roles="admin">
	<a href="#" onclick="javascript:window.parent.addTab('编辑简介信息 - {{d.INST_TITLE}}', '${path }/instMgr/editPage.shtml?instId={{d.INST_ID}}', '', 'hc.cnt.instMgr.edit_{{d.CNT_ID}}')" class="layui-btn layui-btn-xs" title="编辑"><i class="layui-icon">&#xe642;</i></a>
	</hc:url>
	</script>
	
	
	<script type="text/javascript" id="insertTime">
	{{d.INSERT_TIME_SIMPLE}}
	</script>
	
	<script type="text/javascript" id="updateTime">
	{{d.INSERT_TIME_SIMPLE}}
	</script>
	
	<script type="text/javascript" id="cntCount">
	{{d.INST_COUNT}}
	</script>

	<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
	<!-- 页面加载完成后 -->
	<script type="text/javascript">
	var layer = layui.layer,
		table = layui.table,
		form = layui.form,
		laydate = layui.laydate,
		options;
	
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
				field:'INST_ID',
				type:'DESC'
			}
		};
		// 渲染表格
		table.init('baseTable', options);
		// 添加URL
		options.where=getWhere('queryForm', options.initSort);
		options.url='${path}/instMgr/list.shtml';
		// 加载数据
		table.reload('baseTable',options);
		/*****************************************************/

	});
	</script>
	</body>

</html>