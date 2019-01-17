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
	  <a ><cite>Session管理</cite></a>
	</span>
	<br><br>
	
	<blockquote class="layui-elem-quote">
	<form class="layui-form" method="get" id="queryForm">
	<table style="width:100%;border-collapse:separate;border-spacing:10px;">
	
	<tr>
	<td align="right" width=100>用户名：</td><td align="left" width=200>
	<input name="userName" placeholder="用户名" autocomplete="off" class="layui-input" type="text"></td>
	<td align="right" width=100>角色：</td><td width=200>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
		<select name="roleId" lay-filter="aihao">
			<option value="">全部角色</option>
			<c:forEach items="${roles }" var="r">
			<option value="${r.ROLE_ID }">${r.ROLE_NAME }</option>
			</c:forEach>
		</select>
	</div>
	</td>
	<td >
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
	<th lay-data="{field:'USER_ID',width:80,sort:true,fixed:'left'}">ID</th>
	<th lay-data="{field:'USER_NAME',width:120,sort:true,fixed:'left'}">用户名</th>
	<th lay-data="{field:'ROLE_NAME',width:110,templet:'#roleList'}">角色</th>
	<th lay-data="{field:'CURRENT_TIME',width:175,sort:true,templet:'#loginTime'}">登录时间</th>
	<th lay-data="{field:'CERT_SN',width:200}">证书序列号</th>
	<th lay-data="{field:'NOTE',width:280}">备注</th>
	<th lay-data="{field:'opt',width:130,fixed:'right',templet:'#opt'}">操作</th>
	</thead>
	</table>
	
	<script type="text/javascript" id="roleList">
	<a href="#" onclick="javascript:showRoleList({{d.ROLE_ID}},'{{d.USER_NAME}}')" class="layui-btn layui-btn-xs" title="点击查看权限"><i class="layui-icon">&#xe64c;</i> {{d.ROLE_NAME}}</a>
	</script>
	
	<script type="text/javascript" id="opt">
	<hc:url per="hc.sys.session.logout" roles="admin">
	<a href="#" onclick="javascript:logout({{d.USER_ID}},'{{d.USER_NAME}}')" class="layui-btn layui-btn-xs layui-btn-danger" title="强制下线"><i class="layui-icon">&#xe640; 强制下线</i></a>
	</hc:url>
	</script>
	
	<script type="text/javascript" id="loginTime">
	{{d.CURRENT_TIME}}
	</script>

	<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
	<!-- 页面加载完成后 -->
	<script type="text/javascript">
	var layer = layui.layer,
		table = layui.table,
		form = layui.form,
		options;
	
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
	
	function showRoleList(id, name) {
		roleTree = layer.open({
			type: 2,
			title: '权限列表 - ' + name,
			shade: 0.6,
			area: ['320px', '580px'],
			content: '${path}/role/per.shtml?type=1&roleId=' + id
		}); 
	}
	
	function logout(id, name) {
		layer.confirm("你确定要强制用户 '" + name + "' 下线吗？", {
			btn : ['确定', '取消' ],
			icon:0
		}, function(index) {
			$.ajax({	// 确定
				url : '${path}/session/logout.shtml',
				dataType : 'json',
				data:{userId:id},
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
				field:'USER_ID',
				type:'DESC'
			}
		};
		// 渲染表格
		table.init('baseTable', options);
		// 添加URL
		options.where=getWhere('queryForm', options.initSort);
		options.url='${path}/session/list.shtml';
		// 加载数据
		table.reload('baseTable',options);
		/*****************************************************/
	})
	</script>
	</body>

</html>