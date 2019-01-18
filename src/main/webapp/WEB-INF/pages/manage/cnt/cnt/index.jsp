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
	  <a ><cite>文本管理</cite></a>
	</span>
	<br><br>
	
	<blockquote class="layui-elem-quote">
	<form class="layui-form" method="get" id="queryForm">
	<table style="width:100%;border-collapse:separate;border-spacing:10px;">
	
	<hc:url per="hc.cnt.cntMgr.add" roles="admin">
	<tr>
	<td></td><td colspan="7"><a href="javascript:window.parent.addTab('添加文本', '${path }/cntMgr/addPage.shtml', '', 'hc.cnt.cntMgr.add')" class="layui-btn"><i class="layui-icon">&#xe654;</i> 添加文本</a></td>
	</tr>
	</hc:url>
	
	<tr>
	<td align="right" width=100>标题：</td><td align="left" width=230>
	<input name="title" placeholder="" autocomplete="off" class="layui-input" type="text"></td>
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
	<td align="right">栏目：</td><td>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
		<select name="model" lay-filter="model" id="model">
			<option value="">全部</option>
		</select>
	</div>
	</td>
	<!-- <td align="right">类型：</td><td>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
		<select name="type" lay-filter="type" id="type">
			<option value="">全部</option>
		</select>
	</div>
	</td> -->
	<td align="right">操作人：</td><td>
	<div class="layui-input-block" style="width:200px;margin-left:0px;">
	<input name="user" placeholder="用户名" autocomplete="off" class="layui-input" type="text">
	</div>
	</td>
	</tr>
	<tr>
	<td align="right" width=100>文本ID：</td><td align="left">
	<input name="cntId" placeholder="" autocomplete="off" class="layui-input" type="text"></td>
	<td></td>
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
	<th lay-data="{field:'CNT_ID',width:80,sort:true,fixed:'left'}">ID</th>
	<th lay-data="{field:'CNT_TITLE',width:700,sort:true,fixed:'left'}">标题</th>
	<th lay-data="{field:'USER_ID',width:110,templet:'#userId'}">操作人</th>
	<th lay-data="{field:'CNT_MODEL',width:110,sort:true,templet:'#modelDiv'}">栏目</th>
	<th lay-data="{field:'CNT_TYPE',width:110,sort:true,templet:'#typeDiv'}">类别</th>
	<th lay-data="{field:'INSERT_TIME',width:110,sort:true,templet:'#insertTime'}">添加时间</th>
	<th lay-data="{field:'UPDATE_TIME',width:110,sort:true,templet:'#updateTime'}">更新时间</th>
	<th lay-data="{field:'CNT_COUNT',width:110,sort:true,templet:'#cntCount'}">访问量</th>
	<th lay-data="{field:'CNT_STATUS',width:90,sort:true,templet:'#status'}">状态</th>
	<th lay-data="{field:'CNT_FROM',width:220}">出处</th>
	<th lay-data="{field:'CNT_URL',width:220,templet:'#url'}">引用连接</th>
	<th lay-data="{field:'opt',width:120,fixed:'right',templet:'#opt'}">操作</th>
	</thead>
	</table>
	
	<script type="text/javascript" id="url">
	{{# if (d.CNT_URL) { }}
		<a href="{{d.CNT_URL}}" target="_blank">{{d.CNT_URL}}</a>
	{{# } }}
	
	</script>
	
	<script type="text/javascript" id="modelDiv">
	{{d.CNT_MODEL_NAME}}
	</script>
	
	<script type="text/javascript" id="typeDiv">
	{{d.CNT_TYPE_NAME}}
	</script>
	
	<script type="text/javascript" id="userId">
	{{d.USER_NAME}}
	</script>
	
	<script type="text/javascript" id="opt">
	<hc:url per="hc.cnt.cntMgr.edit" roles="admin">
	<a href="#" onclick="javascript:window.parent.addTab('编辑文本信息 - {{d.CNT_TITLE}}', '${path }/cntMgr/editPage.shtml?cntId={{d.CNT_ID}}', '', 'hc.cnt.cntMgr.edit_{{d.CNT_ID}}')" class="layui-btn layui-btn-xs" title="编辑"><i class="layui-icon">&#xe642;</i></a>
	</hc:url>
	<hc:url per="hc.cnt.cntMgr.del" roles="admin">
	<a href="#" onclick="javascript:delData({{d.CNT_ID}}, '{{d.CNT_TITLE}}')" class="layui-btn layui-btn-xs layui-btn-danger" title="删除"><i class="layui-icon">&#xe640;</i></a>
	</hc:url>
	</script>
	
	<script type="text/javascript" id="status">
	<input type="checkbox" id="CNT_{{d.CNT_ID}}" value="{{d.CNT_STATUS}}" curId="{{d.CNT_ID}}" name="{{d.CNT_TITLE}}" lay-skin="switch" lay-text="启用|禁用" title="停/启用" lay-filter="statusBtn" {{ d.CNT_STATUS == 1 ? 'checked' : '' }}>
	</script>
	
	<script type="text/javascript" id="insertTime">
	{{d.INSERT_TIME_SIMPLE}}
	</script>
	
	<script type="text/javascript" id="updateTime">
	{{d.INSERT_TIME_SIMPLE}}
	</script>
	
	<script type="text/javascript" id="cntCount">
	{{d.CNT_COUNT}}
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
	
	form.on('switch(statusBtn)', function(obj){
		if (obj.elem.checked) {
			status = 1;
		} else {
			status = 2;
		}
		var msg = "您确定要 ";
		if (status == 1) {
			msg += "<span class=\"layui-badge layui-bg-green\">启动</span> ";
		} else {
			msg += "<span class=\"layui-badge\">禁用</span> ";
		}
		msg += "当前文本 '<font color=rgb(36,164,152)>"+$(obj.elem).attr('name')+"</font>' 吗？";
		layer.confirm(msg, {
			btn : ['确定', '取消'],
			icon:0, 
			closeBtn: 0
		}, function(index) {
			// ajax请求
			$.ajax({	// 确定
				url : '${path}/cntMgr/status.shtml',
				dataType : 'json',
				data:{cntId:$(obj.elem).attr('curId'),status:status},
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
	
	form.on('select(model)', function(data){
		if (data.value == '') {
			loadMt("type", -1)
		} else {
			loadMt("type", data.value)
		}
	});
	
	function loadMt(id, parent) {
		$.ajax({
			url: '${path}/mtUtil/list.shtml',
			dataType:'json',
			data:{parent:parent},
			type:'GET',
			cache:false,
			success:function(data){
				var options = '<option value="">全部</option>';
				if (data.code == 0) {
					$(data.data).each(function(index,row){
						options += '<option value="'+row.MT_ID+'">'+row.MT_NAME+'</option>';
					});
				}
				$('#'+id).html(options);
				form.render("select");
				// 触发select选中事件
				$('#'+id).next().find('dd').each(function(index,elm){
					if ($('#'+id).attr('initVal')) {
						if ($('#'+id).attr('initVal') == $(elm).attr('lay-value')) {
							$(elm).click();
							return false;
						}
					} else {
						if (index == 0) {
							$(elm).click();
							return false;
						}
					}
				});
			}
		})
	}
	
	function delData(id, name) {
		layer.confirm("你确定要删除文本 '" + name + "' 吗？", {
			btn : ['确定', '取消' ],
			icon:0
		}, function(index) {
			$.ajax({	// 确定
				url : '${path}/cntMgr/del.shtml',
				dataType : 'json',
				data:{cntId:id},
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
		loadMt('model', 0);
		
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
				field:'CNT_ID',
				type:'DESC'
			}
		};
		// 渲染表格
		table.init('baseTable', options);
		// 添加URL
		options.where=getWhere('queryForm', options.initSort);
		options.url='${path}/cntMgr/list.shtml';
		// 加载数据
		table.reload('baseTable',options);
		/*****************************************************/

	});
	</script>
	</body>

</html>