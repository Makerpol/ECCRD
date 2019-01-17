<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/hc.tld" prefix="hc"%>
<!DOCTYPE html>

<html>

	<head>
		<meta charset="utf-8">
		<title>河南省《创新科技》杂志社</title>
		<jsp:include page="/common/public.jsp"/>

		<link rel="stylesheet" href="${path }/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="${path }/common/css/global.css" media="all">
		<link rel="stylesheet" href="${path }/font-awesome/css/font-awesome.min.css">

	</head>

	<body>
		<div class="layui-layout layui-layout-admin" style="border-bottom: solid 5px #1aa094;">
			<div class="layui-header header header-demo">
				<div class="layui-main">
					<div class="admin-login-box">
						<a class="logo" style="left: 0;" >
							<span style="font-size: 18px;">河南省《创新科技》杂志社</span>
						</a>
						<div class="admin-side-toggle" title="隐藏或展示菜单栏">
							<i class="fa fa-bars" aria-hidden="true"></i>
						</div>
						<!-- <div class="admin-side-full" title="全屏">
							<i class="fa fa-life-bouy" aria-hidden="true"></i>
						</div> -->
					</div>
					<ul class="layui-nav admin-header-item">
						<li class="layui-nav-item" >
							<a href="${path }/" target="_blank"><span>首页</span></a>
						</li>
						<li class="layui-nav-item" >
							<a href="javascript:showNotice();"><span>在线人数 <span class="layui-badge" id="onlineSize">0</span></span></a>
						</li>
						<li class="layui-nav-item">
							<a href="javascript:;" class="admin-header-user">
								<img src="${path }/common/images/login_logo.png" />
								<span title="角色：${loginUser.ROLE_NAME }">${loginUser.USER_NAME }</span>
							</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="javascript:addTab('修改密码','${path }/user/rePwsPage.shtml?userId=${loginUser.USER_ID}','','hc.sys.user.rePws_${loginUser.USER_ID }');"><i class="fa fa-gear" aria-hidden="true"></i> 修改密码</a>
								</dd>
								<dd>
									<a href="javascript:logout()"><i class="fa fa-sign-out" aria-hidden="true"></i> 退出</a>
								</dd>
							</dl>
						</li>
					</ul>
				</div>
			</div>
			<div class="layui-side layui-bg-black" id="admin-side">
				<div class="layui-side-scroll" id="admin-navbar-side" lay-filter="side">
				<ul class="layui-nav layui-nav-tree layui-inline" style="margin-right: 10px;">
				  <c:forEach items="${menus }" var="m" varStatus="stu">
				  <c:if test="${m.PER_PARENT == null || m.PER_PARENT == 0 }">
				  <hc:url per="${m.PER_NAME }" roles="admin">
				  <li class="layui-nav-item <c:if test="${stu.index == 0 }">layui-nav-itemed</c:if> ">
				    <a href="javascript:;">
				    <c:if test="${fn:contains(m.PER_ICON, 'fa-') }">
				    <i class="fa ${m.PER_ICON }" aria-hidden="true" data-icon="fa-cubes"></i> 
				    </c:if>
				    ${m.PER_TITLE }</a>
				    <dl class="layui-nav-child" >
				    <c:forEach items="${menus }" var="c" varStatus="stu">
				    <c:if test="${c.PER_PARENT == m.PER_ID}">
				    <hc:url per="${c.PER_NAME }" roles="admin">
				    <dd style="padding-left:20px;">
				    <a href="javascript:addTab('${c.PER_TITLE }', '${path }${c.PER_URL}?${c.PER_PARAMS }', '${c.PER_ICON }', '${c.PER_NAME }');" >
				    <c:if test="${fn:contains(c.PER_ICON, 'fa-') }">
				    <i class="fa ${c.PER_ICON }" aria-hidden="true"></i> 
				    </c:if>
				    ${c.PER_TITLE }</a></dd>
				    </hc:url>
				    </c:if>
				    </c:forEach>
				    </dl>
				  </li>
				  </hc:url>
				  </c:if>
				  </c:forEach>
				</ul>
				</div>
			</div>
			<div class="layui-body" style="bottom: 0;border-left: solid 2px #1AA094;" id="admin-body">
				<div class="layui-tab admin-nav-card layui-tab-brief" lay-filter="admin-tab" id="admin-tab" >
					<ul class="layui-tab-title">
						<li title="关闭所有的Tab" lay-id="close"><i class="layui-icon">&#xe62a;</i> 关闭</li>
						<li class="layui-this" lay-id="welcome">
							<i class="fa fa-font-awesome" aria-hidden="true"></i>
							<cite>欢迎页</cite>
						</li>
					</ul>
					<div class="layui-tab-content" style="min-height: 150px; padding: 0 0 0 0;">
					<div class="layui-tab-item" ></div>
						<div class="layui-tab-item layui-show" >
							<iframe src="${path }/sys/welcome.shtml" scrolling="auto" frameborder="0"></iframe>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-footer footer footer-demo" id="admin-footer">
				<div class="layui-main">
					<p>&copy;2018</p>
				</div>
			</div>
			<div class="site-tree-mobile layui-hide">
				<i class="layui-icon">&#xe602;</i>
			</div>
			<div class="site-mobile-shade"></div>
			
			<script type="text/javascript" src="${path }/layui/layui.all.js"></script>
			
			<script>
			var layer = layui.layer, element = layui.element;
			
			/*
			 * 添加tab窗口
			 * 并选中新创建的tab
			 */
			function addTab(title, url, icon, id) {
				if (!document.getElementById(id)) {
					element.tabAdd('admin-tab', {
						title:'<i class="fa '+icon+'" aria-hidden="true"></i>&nbsp;'+title+'&nbsp;&nbsp;<i onclick=closeTab("'+id+'") class="fa fa-times" aria-hidden="true">',
						content:'<iframe src="'+url+'" id="'+id+'" scrolling="auto" frameborder="0"></iframe>',
						id:id
					});
					var $content = $('.admin-nav-card .layui-tab-content');
					$content.height($(this).height() - 147);
					$content.find('iframe').each(function() {
						$(this).height($content.height());
					});
				}
				element.tabChange('admin-tab', id);
			}
			
			/*
			 * 更新tab窗口
			 * 是否选中新创建的tab
			 */
			function updateTab(id, select) {
				if (!document.getElementById(id)) {
					return;
				}
				if (select) {
					element.tabChange('admin-tab', id);
				}
				if (document.getElementById(id).contentWindow) {
					document.getElementById(id).contentWindow.reload();
				}
			}
			
			/**
			 * 关闭tab
			 */
			function closeTab(id) {
				element.tabDelete('admin-tab', id);
			}

			//定时刷新系统点数
			function flushOnlineSize() {
				$.ajax({
					url : '${path}/sys/onlineSize.shtml',
					dataType : 'json',
					type : 'GET',
					cache : false,
					success : function(data) {
						if (data.code == 0) {
							$('#onlineSize').html(data.size);
						}
					}
				})
			}
			
			function logout() {
				layer.confirm('你确定要退出系统嘛？', {
					btn : ['确定', '取消' ],
					icon:0
				}, function() {
					window.location.href = "${path}/sys/logout.shtml";
				});
			}
			
			function showNotice() {
				layer.open({
					type: 0,
					title: '<i class="fa fa-volume-up" aria-hidden="true"></i> 通知公告',
					shade: 0,
					btn:'我知道了',
					resize:false,
					area: ['340px', '215px'],
					offset: 'rb', //右下角弹出
					time: 0,
					anim: 2,
					content: "测试内容" //iframe的url，no代表不显示滚动条
				});
			}
			
			$(function(){
				element.on('tab(admin-tab)', function(data){
					if (data.index == '0') {
						$.each($('#admin-tab').find('li'), function(index, li) {
							if (index < 2) {
								return true;
							}
							element.tabDelete('admin-tab', $(li).attr('lay-id'));
						})
						// 选中欢迎页
						element.tabChange('admin-tab', "welcome");
					}
					
				});
				
				// 先加载一次
				flushOnlineSize();
				
				// 定时刷新在线人数
				$('body').everyTime('15s', 'flushTask', function() {
					flushOnlineSize();
				});
				
				//iframe自适应
				$(window).on('resize', function() {
					var $content = $('.admin-nav-card .layui-tab-content');
					$content.height($(this).height() - 147);
					$content.find('iframe').each(function() {
						$(this).height($content.height());
					});
				}).resize();

				$('.admin-side-toggle').on('click', function() {
					var sideWidth = $('#admin-side').width();
					if (sideWidth === 200) {
						$('#admin-body').animate({
							left : '0'
						}); //admin-footer
						$('#admin-footer').animate({
							left : '0'
						});
						$('#admin-side').animate({
							width : '0'
						});
					} else {
						$('#admin-body').animate({
							left : '200px'
						});
						$('#admin-footer').animate({
							left : '200px'
						});
						$('#admin-side').animate({
							width : '200px'
						});
					}
				});

				$('.admin-side-full').on('click', function() {
					var docElm = document.documentElement;
					//W3C  
					if (docElm.requestFullscreen) {
						docElm.requestFullscreen();
					}
					//FireFox  
					else if (docElm.mozRequestFullScreen) {
						docElm.mozRequestFullScreen();
					}
					//Chrome等  
					else if (docElm.webkitRequestFullScreen) {
						docElm.webkitRequestFullScreen();
					}
					//IE11
					else if (docElm.msRequestFullscreen) {
						docElm.msRequestFullscreen();
					}
					layer.msg('按Esc即可退出全屏');
				});
			})
			</script>
		</div>
	</body>

</html>