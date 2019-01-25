<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="width: 100%;height: 1500px;background-color: azure;margin-top: 5px;">
<div style="width: 80%;float: left;">
<div style="margin-top: 10px;position: relative;z-index: 1;">
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
						<h3 style="text-align: center;width: 250px;height:60px;margin-top: 40px;font-weight: 700;">
							<a style="text-decoration:none;font-size:18px;width: 250px;height: 60px;color:white;overflow: hidden;display: block;text-overflow: ellipsis;">${c.CL_TITLE_EN }</a>
						</h3>
						<p style="text-indent: 2em;line-height: 22px;word-wrap: break-word;overflow: hidden;width: 254px;"><a style="color:white;text-decoration:none;">${c.MARK_EN}</a></p>
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
		<!-- <script type="text/javascript">
			$(function(){
				// 永久停止轮播
		        $('#carousel-home').carousel({
		    		interval: false
				})
			})
		</script> -->
	</div>
</div>

<div>
<div class="col-md-4 tab" style="margin-top: 30px;margin-left:0px;border: 1px solid #F0F0F0;width:410px;float:left;">
	<div class="tab-model">
		<ol id="list1">
			<li class="active"><a href='${path }/cnt/listEN.shtml?page=1'>News</a></li>
		</ol>
		<a class="more" href="#" onclick="${path }/cnt/listEN.shtml?page=1">more</a>
		<div class="tab-body">
			<ul>
				<c:forEach items="${list1 }" var="l">
				<c:if test="${not empty  l.CNT_TITLE_EN}">
					<li><div class="left">● <a href="${path }/cnt/detailEN/${l.CNT_MODEL}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE_EN }">${l.CNT_TITLE_EN }</a></div><div class="right">${l.UPDATE_TIME_SIMPLE }</div></li>
				</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
<div class="col-md-4 tab" style="margin-top: 30px;margin-left: 5px;border: 1px solid #F0F0F0;width:409px;">
<div class="tab-model">
	<ol id="list2">
		<li class="active"><a href='${path }/cnt/listEN.shtml?model=2&page=1'>Diplomatic Research</a></li>
	</ol>
	<a class="more" href="#" onclick="${path }/cnt/listEN.shtml?model=2&page=1">more</a>
	<div class="tab-body">
		<ul>
			<c:forEach items="${list2 }" var="l">
			<c:if test="${not empty  l.CNT_TITLE_EN}">
			<li><div class="left">● <a href="${path }/cnt/detailEN/${l.CNT_MODEL}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE_EN }">${l.CNT_TITLE_EN }</a></div><div class="right" style="margin-right: -10px;">${l.UPDATE_TIME_SIMPLE }</div></li>
			</c:if>
			</c:forEach>
		</ul>
	</div>
</div>
</div>
</div>
<div class="col-md-4 tab" style="margin-top: 10px;margin-left: 0px;border: 1px solid #F0F0F0;width:410px;">
<div class="tab-model">
	<ol id="list3">
		<li class="active"><a href='${path }/cnt/listEN.shtml?model=3&page=1'>Cultural＆Economic</a></li>
	</ol>
	<a class="more" href="#" onclick="${path }/cnt/listEN.shtml?model=3&page=1">more</a>
	<div class="tab-body">
		<ul>
			<c:forEach items="${list3 }" var="l" varStatus="idxStatus">
			<c:if test="${idxStatus.index <= 7 }">
			<c:if test="${not empty  l.CNT_TITLE_EN}">
			<li><div class="left">● <a href="${path }/cnt/detailEN/${l.CNT_MODEL}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE_EN }">${l.CNT_TITLE_EN }</a></div><div class="right" style="margin-right: -10px;">${l.UPDATE_TIME_SIMPLE }</div></li>
			</c:if>
			</c:if>
			</c:forEach>
		</ul>
	</div>
</div>
</div>

<div class="col-md-4 tab" style="margin-top: 10px;margin-left: 5px;border: 1px solid #F0F0F0;width:409px;">
<%-- <div class="tab-model">
	<ol id="list4">
		<li class="active"><a href='${path }/cnt/list.shtml?model=4&page=1'>经济合作</a></li>
	</ol>
	<a class="more" href="#" onclick="${path }/cnt/list.shtml?model=4&page=1">more</a>
	<div class="tab-body">
		<ul>
			<c:forEach items="${list4 }" var="l">
			<li><div class="left">● <a href="${path }/cnt/detail/${l.CNT_MODEL}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE }">${l.CNT_TITLE }</a></div><div class="right" style="margin-right: -10px;">${l.UPDATE_TIME_SIMPLE }</div></li>
			</c:forEach>
		</ul>
	</div>
</div> --%>
<div class="tab-model">
	<ol id="list5">
		<li class="active"><a href='${path }/cnt/listEN.shtml?model=5&page=1'>OUTLOOK</a></li>
	</ol>
	<a class="more" href="#" onclick="${path }/cnt/listEN.shtml?model=5&page=1">more</a>
	<div class="tab-body">
		<ul>
			<c:forEach items="${list5 }" var="l" varStatus="idxStatus">
			<c:if test="${idxStatus.index <= 7 }">
			<c:if test="${not empty  l.CNT_TITLE_EN}">
			<li><div class="left">● <a href="${path }/cnt/detailEN/${l.CNT_MODEL}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE_EN }">${l.CNT_TITLE_EN }</a></div><div class="right" style="margin-right: -10px;">${l.UPDATE_TIME_SIMPLE }</div></li>
			</c:if>
			</c:if>
			</c:forEach>
		</ul>
	</div>
</div>
</div>
<div class="col-md-4 tab" style="margin-top: 10px;margin-left: 5px;border: 1px solid #F0F0F0;width:405px;">
<div class="tab-model">
	<ol id="video">
		<li class="active"><a href='${path }/video/listEN.shtml?page=1'>Videos</a></li>
	</ol>
	<a class="more" href="#" onclick="${path }/video/listEN.shtml?page=1">more</a>
	<div class="tab-body">
		<video controls="controls" controls="controls" style="width:350px;height:250px;margin-left: 5%;">
			<source src="${path}${video.FILE_URL }" type="video/mp4" />
		</video>
		<p style="TEXT-ALIGN: center;">${video.VIDEO_TITLE_EN }</p>
	</div>
</div>
</div>
<div class="col-md-4 tab" style="margin-top: 10px;margin-left: 5px;border: 1px solid #F0F0F0;width:405px;">
<div class="tab-model">
	<ol id="list6">
		<li class="active"><a href='${path }/cnt/listEN.shtml?model=6&page=1'>Past Achievements</a></li>
	</ol>
	<a class="more" href="#" onclick="${path }/cnt/listEN.shtml?model=6&page=1">more</a>
	<div class="tab-body">
		<ul>
			<c:forEach items="${list6 }" var="l" varStatus="idxStatus">
			<c:if test="${idxStatus.index <= 7 }">
			<c:if test="${not empty  l.CNT_TITLE_EN}">
			<li><div class="left">● <a href="${path }/cnt/detailEN/${l.CNT_MODEL}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE_EN }">${l.CNT_TITLE_EN }</a></div><div class="right" style="margin-right: -10px;">${l.UPDATE_TIME_SIMPLE }</div></li>
			</c:if>
			</c:if>
			</c:forEach>
		</ul>
	</div>
</div>
</div>

<div style="width: 1000px;margin-top: 15px;float: left;">
	<div class="div-title" style="background-color: #044e77;width:200px;height: 30px;">
		<div style="float: left;margin-left: 15px; "><a style="color: white;text-align:center;line-height: 30px;" href="#">Projects</a></div>
	</div>
	<div class="one" style="margin-left: 1.5%;">
		<ul>
		<c:forEach items="${clList2 }" var="l">
			<li><a href="${path}${l.CL_URL }"><img style="width:220px;height:150px;" src="${path}${l.FILE_URL }" alt=""><span style="width: 240px;">${l.CL_TITLE_EN }</span></a></li>
		</c:forEach>
       </ul>
	</div>
</div>
<script type="text/javascript">
function Rin($Box, v) { //$Box移动的对象，v对象移动的速率
    var $Box_ul = $Box.find("ul"),
        $Box_li = $Box_ul.find("li"),
        $Box_li_span = $Box_li.find("span"),
        left = 0,
        s = 0,
        timer; //定时器

    $Box_li.each(function(index) {
        $($Box_li_span[index]).width($(this).width()); //hover
        s += $(this).outerWidth(true); //即要滚动的长度
    })

    window.requestAnimationFrame = window.requestAnimationFrame || function(Tmove) { return setTimeout(Tmove, 1000 / 60) };
    window.cancelAnimationFrame = window.cancelAnimationFrame || clearTimeout;

    if (s >= $Box.width()) { //如果滚动长度超出Box长度即开始滚动，没有的话就不执行滚动
        $Box_li.clone(true).appendTo($Box_ul);
        Tmove();

        function Tmove() {
            //运动是移动left  从0到-s;（个人习惯往左滚）
            left -= v;
            if (left <= -s) { left = 0;
                $Box_ul.css("left", left) } else { $Box_ul.css("left", left) }
            timer = requestAnimationFrame(Tmove);
        }
        $Box_ul.hover(function() { cancelAnimationFrame(timer) }, function() { Tmove() })
    }
}

$(function(){
	var box0 = $(".one"),
	v0 = 1.5; //这里添加滚动的对象和其速率
	v1 = 1;
 	Rin(box0, v0);
})
</script>
</div>
	<div style="float: right;width: 200px;height: 100%;">
		<div class="main" style="text-align: center;border: 3px solid #bfe2f9;border-radius: 5px; width: 182px;height:280px;margin-left: 5px;margin-top: 10px;">
			<img style="width: 182px;height:280px;" calt="" src="${path}/common/images/zz_EN.JPG">
		</div>
		
		<div class="main" style="border: 3px solid #bfe2f9;border-radius: 5px; width: 182px;height:250px;margin-left: 5px;margin-top: 10px;">
			<p style="background-color: #1872a7;text-align: center;font-size: 16px;"><a style="color:white;" href="${path }/cnt/listEN.shtml?model=7&page=1">Amalia Column</a></p>
			<div >
				<ul style="list-style: none;margin-left: -30px;">
					<c:forEach items="${list7 }" var="l">
					<c:if test="${idxStatus.index <= 6 }">
					<c:if test="${not empty  l.CNT_TITLE_EN}">
					<li>
					<div style="width: 160px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
						<a href="${path }/cnt/detailEN/${l.CNT_MODEL}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE_EN }">${l.CNT_TITLE_EN }</a>
					</div>
					</li>
					</c:if>
					</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="main" style="border: 3px solid #bfe2f9;border-radius: 5px; width: 182px;height:250px;margin-left: 5px;margin-top: 10px;">
			<p style="background-color: #1872a7;text-align: center;font-size: 16px;color:white;"><a style="color:white;" href="${path }/cnt/listEN.shtml?model=8&page=1">Special Column</a></p>
			<div >
				<ul style="list-style: none;margin-left: -30px;">
					<c:forEach items="${list8 }" var="l">
					<c:if test="${idxStatus.index <= 6 }">
					<c:if test="${not empty  l.CNT_TITLE_EN}">
					<li>
					<div style="width: 160px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
						<a href="${path }/cnt/detailEN/${l.CNT_MODEL}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE_EN }">${l.CNT_TITLE_EN }</a>
					</div>
					</li>
					</c:if>
					</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="main" style="border: 3px solid #bfe2f9;border-radius: 5px; width: 182px;height:250px;margin-left: 5px;margin-top: 10px;">
			<p style="background-color: #1872a7;text-align: center;font-size: 16px;color:white;"><a style="color:white;" href="${path }/cnt/listEN.shtml?model=9&page=1">Grasp of civilization</a></p>
			<div >
				<ul style="list-style: none;margin-left: -30px;">
					<c:forEach items="${list9 }" var="l">
					<c:if test="${idxStatus.index <= 6 }">
					<c:if test="${not empty  l.CNT_TITLE_EN}">
					<li>
					<div style="width: 160px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
						<a href="${path }/cnt/detailEN/${l.CNT_MODEL}/${l.CNT_ID}.shtml" target="_blank" title="${l.CNT_TITLE_EN }">${l.CNT_TITLE_EN }</a>
					</div>
					</li>
					</c:if>
					</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="main" style="border: 3px solid #bfe2f9;border-radius: 5px; width: 182px;height:200px;margin-left: 5px;margin-top: 10px;">
			<p style="background-color: #1872a7;text-align: center;font-size: 16px;color:white;">Links</p>
			<div >
				<ul style="list-style: none;margin-left: -30px;">
					<c:forEach items="${links }" var="l" varStatus="idxStatus">
					<c:if test="${idxStatus.index <= 5 }">
					<c:if test="${not empty  l.LINK_TITLE_EN}">
						<div class="link">
							<a style="display: block;text-align: initial;width: 160px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" target="_blank" href="${l.LINK_URL }">${l.LINK_TITLE_EN }</a>
						</div>
					</c:if>
					</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
