<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="width: 100%;height: 1000px;background-color: azure;">
<div style="width: 80%;float: left;">
<div style="margin-top: 5px;position: relative;z-index: 1;">
	<div class="col-md-4 cl" style="width:100%;background-color: gainsboro;float:left;">
		<div id="carousel-home" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators" style="bottom:0px;">
				<c:forEach items="${clList }" var="c" varStatus="st">
				<li data-target="#carousel-home" data-slide-to="${st.index }" <c:if test="${st.index == 0 }">class="active"</c:if> ></li>
				</c:forEach>
			</ol>
			<div class="carousel-inner" role="listbox">
				<c:forEach items="${clList }" var="c" varStatus="st">
				<div  class="item <c:if test="${st.index == 0 }">active</c:if>">
					<div><img style="width:500px;height:285px;float: left;" src="${path }${c.FILE_URL}" alt="${c.CL_TITLE }" class="cl-img"></div>
					<%-- <div class="carousel-caption" style="height:50px;left:0%;right:0%;background:rgba(0,0,0,0.6);bottom:0px;">
						<p class="cl-txt"><c:if test="${c.CL_URL == null }">${c.CL_TITLE }</c:if><c:if test="${c.CL_URL != null }"><a style="color:white;" target="_blank" href="${c.CL_URL}">${c.CL_TITLE }</a></c:if></p>
					</div> --%>
					<div class="txt">
						<h3 style="text-align: center;width: 250px;height:50px;margin-top: 55px;font-weight: 700;">
							<a style="text-decoration:none;font-size:18px;width: 250px;height: 50px;color:white;overflow: hidden;display: block;text-overflow: ellipsis;">${c.CL_TITLE }</a>
						</h3>
						<p style="text-indent: 2em;line-height: 22px;word-wrap: break-word;overflow: hidden;width: 214px;"><a style="color:white;text-decoration:none;">${c.MARK}</a></p>
					</div>
				</div>
				</c:forEach>
			</div>
			
			<!-- <a class="left carousel-control" href="#carousel-home" role="button" data-slide="prev">
			    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
			<span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="#carousel-home" role="button" data-slide="next">
				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a> -->
		</div>
		<script type="text/javascript">
			$(function(){
				// 永久停止轮播
		        $('#carousel-home').carousel({
		    		interval: false
				})
			})
		</script>
	</div>
	<%-- <div class="col-md-4 tab" style="width:500px; margin-left: 7px;">
		<div class="tab-model">
			<ol>
				<li class="active" value="10"><a href='${path }/cnt/list.shtml?model=8&type=10&page=1'>国家要闻</a></li>
				<li value="26"><a href='${path }/cnt/list.shtml?model=8&type=26&page=1'>河南要闻</a></li>
				<li value="12"><a href='${path }/cnt/list.shtml?model=8&type=12&page=1'>媒体聚焦</a></li>
			</ol>
			<a class="more" href="#" onclick="javascript:showTabModelList1(8)">more</a>
			<div class="tab-body" style="width:500px;">
				<ul>
					<c:forEach items="${list10 }" var="l">
					<li><div class="left">● <a href="${path }/cnt/detail/${l.CNT_MODEL}/${l.CNT_TYPE}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE }">${l.CNT_TITLE }</a></div><div class="right" style="margin-right: 15px;">${l.UPDATE_TIME_SIMPLE }</div></li>
					</c:forEach>
				</ul>
			</div>
			<div class="tab-body" style="width:500px;">	
				<ul>
					<c:forEach items="${list26 }" var="l">
					<li><div class="left">● <a href="${path }/cnt/detail/${l.CNT_MODEL}/${l.CNT_TYPE}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE }">${l.CNT_TITLE }</a></div><div class="right" style="margin-right: 15px;">${l.UPDATE_TIME_SIMPLE }</div></li>
					</c:forEach>
				</ul>
			</div>
			<div class="tab-body" style="width:500px;">
				<ul>
					<c:forEach items="${list12 }" var="l">
					<li><div class="left">● <a href="${path }/cnt/detail/${l.CNT_MODEL}/${l.CNT_TYPE}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE }">${l.CNT_TITLE }</a></div><div class="right" style="margin-right: 15px;">${l.UPDATE_TIME_SIMPLE }</div></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div> --%>
	
	<!-- <div style="width: 255px;height: 285px;float: right; background-color: blue;"></div> -->
</div>

<div>
<div class="col-md-4 tab" style="margin-top: 30px;margin-left:0px;border: 1px solid #F0F0F0;width:410px;float:left;">
	<div class="tab-model">
		<ol id="list2">
			<li class="active" value="-1"><a href='${path }/anno/list.shtml?page=1'>新闻动态</a></li>
		</ol>
		<a class="more" href="#" onclick="javascript:showTabModelList2()">more</a>
		<div class="tab-body">
			<ul>
				<c:forEach items="${list11 }" var="l">
				<li><div class="left">● <a href="${path }/cnt/detail/${l.CNT_MODEL}/${l.CNT_TYPE}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE }">${l.CNT_TITLE }</a></div><div class="right">${l.UPDATE_TIME_SIMPLE }</div></li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
<div class="col-md-4 tab" style="margin-top: 30px;margin-left: 5px;border: 1px solid #F0F0F0;width:409px;">
<div class="tab-model">
	<ol id="list3">
		<li class="active" value="9"><a href='${path }/cnt/list.shtml?model=8&type=9&page=1'>欧洲外交</a></li>
	</ol>
	<a class="more" href="#" onclick="javascript:showTabModelList3()">more</a>
	<div class="tab-body">
		<ul>
			<c:forEach items="${list9 }" var="l">
			<li><div class="left">● <a href="${path }/cnt/detail/${l.CNT_MODEL}/${l.CNT_TYPE}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE }">${l.CNT_TITLE }</a></div><div class="right" style="margin-right: -10px;">${l.UPDATE_TIME_SIMPLE }</div></li>
			</c:forEach>
		</ul>
	</div>
</div>
</div>
</div>
<div class="col-md-4 tab" style="margin-top: 10px;margin-left: 0px;border: 1px solid #F0F0F0;width:410px;">
<div class="tab-model">
	<ol id="list3">
		<li class="active" value="9"><a href='${path }/cnt/list.shtml?model=8&type=9&page=1'>人文交流</a></li>
	</ol>
	<a class="more" href="#" onclick="javascript:showTabModelList3()">more</a>
	<div class="tab-body">
		<ul>
			<c:forEach items="${list9 }" var="l">
			<li><div class="left">● <a href="${path }/cnt/detail/${l.CNT_MODEL}/${l.CNT_TYPE}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE }">${l.CNT_TITLE }</a></div><div class="right" style="margin-right: -10px;">${l.UPDATE_TIME_SIMPLE }</div></li>
			</c:forEach>
		</ul>
	</div>
</div>
</div>

<div class="col-md-4 tab" style="margin-top: 10px;margin-left: 5px;border: 1px solid #F0F0F0;width:409px;">
<div class="tab-model">
	<ol id="list3">
		<li class="active" value="9"><a href='${path }/cnt/list.shtml?model=8&type=9&page=1'>经济合作</a></li>
	</ol>
	<a class="more" href="#" onclick="javascript:showTabModelList3()">more</a>
	<div class="tab-body">
		<ul>
			<c:forEach items="${list9 }" var="l">
			<li><div class="left">● <a href="${path }/cnt/detail/${l.CNT_MODEL}/${l.CNT_TYPE}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE }">${l.CNT_TITLE }</a></div><div class="right" style="margin-right: -10px;">${l.UPDATE_TIME_SIMPLE }</div></li>
			</c:forEach>
		</ul>
	</div>
</div>
</div>
</div>
	<div style="float: right;background-color: bisque;width: 200px;height: 100%;">
		<div class="main" style="border: 1px solid #bed4e2;padding: 20px 0 20px 12px;width: 182px;height:200px;"></div>
	</div>
</div>
