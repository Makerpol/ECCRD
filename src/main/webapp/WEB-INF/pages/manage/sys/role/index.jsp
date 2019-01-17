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
	  <a ><cite>角色管理</cite></a>
	</span>
	<br><br>
	
	<blockquote class="layui-elem-quote">
	<form class="layui-form" method="get" id="queryForm">
	<table style="width:100%;border-collapse:separate;border-spacing:10px;">
	
	<hc:url per="hc.sys.role.add" roles="admin">
	<tr>
	<td></td><td colspan="7"><a href="javascript:window.parent.addTab('添加角色', '${path }/role/addPage.shtml', '', 'hc.sys.role.add')" class="layui-btn"><i class="layui-icon">&#xe654;</i> 添加角色</a></td>
	</tr>
	</hc:url>
	
	<tr>
	<td align="right" width=100>用户名：</td><td align="left" width=200>
	<input name="roleName" placeholder="用户名" autocomplete="off" class="layui-input" type="text"></td>
	<td align="right" width=100>用户ID：</td><td align="left" width=200>
	<input name="roleId" placeholder="用户ID" autocomplete="off" class="layui-input" type="text"></td>
	<td align="center"  width="300">
	<a class="layui-btn layui-btn-primary" href="#" onclick="clearForm('queryForm')"><i class="layui-icon">&#x1002;</i> 重置</a>
	<a class="layui-btn" href="#" onclick="reload(true)"><i class="layui-icon">&#xe615;</i> 搜索</a>
	</td>
	<td></td>
	</tr>
	</table>
	</form>
	</blockquote>
	
	<table id="baseTable" lay-filter="baseTable">
	<thead>
	<th lay-data="{field:'ROLE_ID',width:80,sort:true,fixed:'left'}">ID</th>
	<th lay-data="{field:'ROLE_NAME',width:180,sort:true,fixed:'left'}">角色名</th>
	<th lay-data="{field:'ROLE_NOTE',width:380}">备注</th>
	<th lay-data="{field:'opt',width:180,fixed:'right',templet:'#opt'}">操作</th>
	</thead>
	</table>
	
	<script type="text/javascript" id="opt">
	<hc:url per="hc.sys.role.per" roles="admin">
	<a href="#" class="layui-btn layui-btn-xs" onclick="showRoleList({{d.ROLE_ID}},'{{d.ROLE_NAME}}')" title="查看权限"><i class="layui-icon">&#xe64c;</i></a>
	</hc:url>
	<hc:url per="hc.sys.role.edit" roles="admin">
	<a href="#" onclick="javascript:window.parent.addTab('编辑角色信息 - {{d.ROLE_NAME}}', '${path }/role/editPage.shtml?roleId={{d.ROLE_ID}}', '', 'hc.sys.role.edit_{{d.ROLE_ID}}')" class="layui-btn layui-btn-xs" title="编辑"><i class="layui-icon">&#xe642;</i></a>
	</hc:url>
	<hc:url per="hc.sys.role.del" roles="admin">
	<a href="#" class="layui-btn layui-btn-danger layui-btn-xs" onclick="delRole({{d.ROLE_ID}},'{{d.ROLE_NAME}}')" title="删除"><i class="layui-icon">&#xe640;</i></a>
	</hc:url>
	</script>

	<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
	
	<!-- 页面加载完成后 -->
	<script type="text/javascript">
	var laydate = layui.laydate,
		layer = layui.layer,
		table = layui.table,
		form = layui.form,
		options, roleTree;
	
	// 监听排序
	table.on('sort(baseTable)', function(obj){
		options.initSort=obj;
		table.reload('baseTable', {
			initSort:obj,
			where:getWhere('queryForm', obj)
		});
	});
	
	function delRole(id, name) {
		layer.confirm("你确定要删除角色 '" + name + "' 吗？", {
			btn : ['确定', '取消' ],
			icon:0
		}, function(index) {
			$.ajax({	// 确定
				url : '${path}/role/del.shtml',
				dataType : 'json',
				data:{roleId:id},
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
	
	function showRoleList(id, name) {
		roleTree = layer.open({
			type: 2,
			title: '权限列表 - ' + name,
			shade: 0.6,
			area: ['320px', '580px'],
			content: '${path}/role/per.shtml?roleId=' + id
		}); 
	}
	
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
			loading:true,
			request:{	// 分页的参数名称，页码和最大行
				pageName:'page',
				limitName:'rows'
			},
			skin:'row',
			even:true,
			initSort:{
				field:'ROLE_ID',
				type:'DESC'
			}
		};
		// 渲染表格
		table.init('baseTable', options);
		// 添加URL
		options.where=getWhere('queryForm', options.initSort);
		options.url='${path}/role/list.shtml';
		// 加载数据
		table.reload('baseTable',options);
		/*****************************************************/
	})
	</script>
	</body>

</html>