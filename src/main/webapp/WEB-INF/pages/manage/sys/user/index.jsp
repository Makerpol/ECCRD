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
	  <a ><cite>用户管理</cite></a>
	</span>
	<br><br>
	
	<blockquote class="layui-elem-quote">
	<form class="layui-form" method="get" id="queryForm">
	<table style="width:100%;border-collapse:separate;border-spacing:10px;">
	
	<hc:url per="hc.sys.user.add" roles="admin">
	<tr>
	<td></td><td colspan="7"><a href="javascript:window.parent.addTab('添加用户', '${path }/user/addPage.shtml', '', 'hc.sys.user.add')" class="layui-btn"><i class="layui-icon">&#xe654;</i> 添加用户</a></td>
	</tr>
	</hc:url>
	
	<tr>
	<td align="right" width=100>用户名：</td><td align="left" width=200>
	<input name="userName" placeholder="用户名" autocomplete="off" class="layui-input" type="text"></td>
	<td align="right">状态：</td><td>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
		<select name="status" lay-filter="aihao">
			<option value="">全部状态</option>
			<c:forEach items="${status }" var="d">
			<option value="${d.value.DICT_VALUE }">${d.value.DICT_NAME }</option>
			</c:forEach>
		</select>
	</div>
	</td>
	<td align="right" width=100>创建时间：</td>
	<td align="left">
	<div class="layui-inline">
	<div class="layui-input-inline" style="width: 230px;">
		<input class="layui-input" id="time" name="time" placeholder=" - " type="text">
	</div>
	</div>
	</td>
	<td></td>
	</tr>
	<tr>
	<td align="right">角色：</td><td>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
		<select name="roleId" lay-filter="aihao">
			<option value="">全部角色</option>
			<c:forEach items="${roles }" var="r">
			<option value="${r.ROLE_ID }">${r.ROLE_NAME }</option>
			</c:forEach>
		</select>
	</div>
	</td>
	<td align="right" width=100>用户ID：</td><td align="left" style="width:200px;margin-left:0px;">
	<input name="userId" placeholder="用户ID" autocomplete="off" class="layui-input" type="text"></td>
	<td align="center" colspan="2" width=230>
	<a class="layui-btn layui-btn-primary" href="#" onclick="clearForm('queryForm')"><i class="layui-icon">&#x1002;</i> 重置</a>
	<a class="layui-btn" href="#" onclick="reload(true)"><i class="layui-icon">&#xe615;</i> 搜索</a>
	</td>
	<td></td><td></td>
	</tr>
	</table>
	</form>
	</blockquote>
	
	<table id="baseTable" lay-filter="baseTable">
	<thead>
	<th lay-data="{field:'USER_ID',width:80,sort:true,fixed:'left'}">ID</th>
	<th lay-data="{field:'USER_NAME',width:115,sort:true,fixed:'left'}">用户名</th>
	<th lay-data="{field:'ROLE_NAME',width:110,templet:'#roleList'}">角色</th>
	<th lay-data="{field:'INSERT_TIME',width:110,sort:true,templet:'#insertTime'}">添加时间</th>
	<th lay-data="{field:'LAST_LOGIN_TIME',width:175,sort:true,templet:'#loginTime'}">最后一次登录</th>
	<th lay-data="{field:'USER_STATUS',width:90,sort:true,templet:'#status'}">状态</th>
	<th lay-data="{field:'NOTE',width:280}">备注</th>
	<th lay-data="{field:'opt',width:100,fixed:'right',templet:'#opt'}">操作</th>
	</thead>
	</table>
	
	<script type="text/javascript" id="roleList">
	<a href="#" onclick="javascript:showRoleList({{d.ROLE_ID}},'{{d.USER_NAME}}')" class="layui-btn layui-btn-xs" title="点击查看权限"><i class="layui-icon">&#xe64c;</i> {{d.ROLE_NAME}}</a>
	</script>
	
	<script type="text/javascript" id="opt">
	<hc:url per="hc.sys.user.edit" roles="admin">
	<a href="#" onclick="javascript:window.parent.addTab('编辑用户信息 - {{d.USER_NAME}}', '${path }/user/editPage.shtml?userId={{d.USER_ID}}', '', 'hc.sys.user.edit_{{d.USER_ID}}')" class="layui-btn layui-btn-xs" title="编辑"><i class="layui-icon">&#xe642;</i></a>
	</hc:url>
	</script>
	
	<script type="text/javascript" id="status">
	<input type="checkbox" id="USER_{{d.USER_ID}}" value="{{d.USER_STATUS}}" authId="{{d.USER_ID}}" name="{{d.USER_NAME}}" lay-skin="switch" lay-text="启用|禁用" title="停/启用" lay-filter="statusBtn" {{ d.USER_STATUS == 0 ? 'checked' : '' }}>
	</script>
	
	<script type="text/javascript" id="insertTime">
	{{d.INSERT_TIME_SIMPLE}}
	</script>
	
	<script type="text/javascript" id="loginTime">
	{{# if (d.LAST_LOGIN_TIME_FULL != undefined ) { }}
		{{d.LAST_LOGIN_TIME_FULL}}
	{{# } else { }}
		未使用
	{{# } }}
	</script>

	<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
	<!-- 页面加载完成后 -->
	<script type="text/javascript">
	var laydate = layui.laydate,
		layer = layui.layer,
		table = layui.table,
		form = layui.form,
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
	
	function showRoleList(id, name) {
		roleTree = layer.open({
			type: 2,
			title: '权限列表 - ' + name,
			shade: 0.6,
			area: ['320px', '580px'],
			content: '${path}/role/per.shtml?type=1&roleId=' + id
		}); 
	}
	
	form.on('switch(statusBtn)', function(obj){
		if (obj.elem.checked) {
			status = 0;
		} else {
			status = 1;
		}
		var msg = "您确定要 ";
		if (status == 0) {
			msg += "<span class=\"layui-badge layui-bg-green\">启动</span> ";
		} else {
			msg += "<span class=\"layui-badge\">禁用</span> ";
		}
		msg += "当前用户 '<font color=rgb(36,164,152)>"+$(obj.elem).attr('name')+"</font>' 吗？";
		layer.confirm(msg, {
			btn : ['确定', '取消'],
			icon:0, 
			closeBtn: 0
		}, function(index) {
			// ajax请求
			$.ajax({	// 确定
				url : '${path}/user/status.shtml',
				dataType : 'json',
				data:{userId:$(obj.elem).attr('authId'),status:status},
				type : 'POST',
				cache : false,
				success : function(data) {
					if (data.code == 0) {
						layer.close(index);
					} else {
						layer.msg(data.msg, {icon: 2, time:2000});
					}
				}
			})
		}, function() {	// 取消，开关恢复原状
			div = $(obj.elem).next();
			if (status == 0 ? false : true) {
				obj.elem.checked=true;
				div.attr('class', 'layui-unselect layui-form-switch layui-form-onswitch');
				div.find('em').text('启用')
			} else {
				obj.elem.checked=false;
				div.attr('class', 'layui-unselect layui-form-switch');
				div.find('em').text('禁用')
			}
		})
		
	})
	
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
		options.url='${path}/user/list.shtml';
		// 加载数据
		table.reload('baseTable',options);
		/*****************************************************/
	})
	</script>
	</body>

</html>