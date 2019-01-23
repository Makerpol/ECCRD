<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.zip.action.BaseAction"%>
<div class="top"></div>
<div class="title">
<nav>
     <ul>
         <li><a href="${path }/">首&nbsp;&nbsp;&nbsp;&nbsp;页</a></li>
         <li><a style="height: 34px;" href="${path }/inst/index.shtml?type=1">关于ECCRD</a>
             <ul>
             <c:forEach items="${eccrd }" var="j">
             	<li><a style="text-align: center;width: 112px;" href="${path }/inst/index.shtml?type=${j.value.DICT_VALUE}">${j.value.DICT_NAME }</a></li>
             </c:forEach>
             </ul>
         </li>
         <li><a href="${path }/cnt/list.shtml?model=1&page=1">新闻动态</a></li>
         <li><a href="${path }/cnt/list.shtml?model=2&page=1">欧中外交</a></li>
         <li><a href="${path }/cnt/list.shtml?model=3&page=1">人文交流</a></li>
         <li><a href="${path }/cnt/list.shtml?model=4&page=1">经济合作</a></li>
         <li><a href="${path }/cnt/list.shtml?model=5&page=1">OUTLOOK</a></li>
         <li><a href="${path }/cnt/list.shtml?model=6&page=1">过往成就</a></li>
         <li><a class="btn" style="background-color: dodgerblue;" href="${path }/english.shtml">English</a></li>
     </ul>
    </nav>
</div>
