<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/hc.tld" prefix="hc"%>
<!DOCTYPE html>

<html>

	<head>
		<meta charset="utf-8">
		<title></title>
		<jsp:include page="/common/public.jsp"/>
		<script src="${path }/common/js/echarts.min.js" type="text/javascript"></script>
		<link rel="stylesheet" href="${path }/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="${path }/common/css/global.css" media="all">
		<link rel="stylesheet" href="${path }/font-awesome/css/font-awesome.min.css">

	</head>

	<body>
		<div id="pie1" style="width: 600px;height:400px;float:left;margin-top:100px;"></div>
		<div id="line" style="width: 600px;height:400px;float:left;margin-top:100px;"></div><br>
		<script type="text/javascript">
		$(function(){
			var param = ${loginUser.ROLE_ID }<3?"":"?userId=${loginUser.USER_ID }";
			
			$.ajax({
				url:"${path }/cntMgr/pie.shtml"+param,
				dataType:"json",
				type:"GET",
				cache : false,
				success:function(data){
					if(data.data){renderPie(data);}
				}
			});
			
			$.ajax({
				url:"${path }/cntMgr/line.shtml"+param,
				dataType:"json",
				type:"GET",
				cache : false,
				success:function(data){
					if(data.data){renderLine(data.data);}
				}
			});
		})
		
		function renderPie(d){
			echarts.init(document.getElementById("pie1")).setOption({
				title : {
			        text: '本月上传文章数量统计',
			        x:'center'  
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
				series: {
					name: "栏目类型",
					type: 'pie',
					data: d.data
				 }
			})
		}
		
		function sound(str){
			var utterThis = new window.SpeechSynthesisUtterance(str);
			window.speechSynthesis.speak(utterThis); 
		}
		
		function renderLine(d){
			echarts.init(document.getElementById("line")).setOption({
			    title: {
			        text: '每月上传文章数量统计',
			        x:'center',
			        y:'20'
			    },
			    tooltip: {
			        trigger: 'axis'
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    toolbox: {
			        feature: {
			            saveAsImage: {}
			        }
			    },
			    xAxis: {
			        type: 'category',
			        boundaryGap: false,
			        data: d.key
			    },
			    yAxis: {
			        type: 'value'
			    },
			    series: [
			        {
			            name:'文章数量',
			            type:'line',
			            data:d.value
			        }
			    ]	
			})
		}
		</script>
	</body>

</html>