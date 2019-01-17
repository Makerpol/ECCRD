<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.zip.action.BaseAction"%>
<div class="top"></div>
<div class="title">
<nav>
     <ul>
         <li><a href="${path }/">首&nbsp;&nbsp;&nbsp;&nbsp;页</a></li>
         <li><a href="${path }/inst/index.shtml?type=1">关于ECCRD</a>
             <ul>
             <c:forEach items="${jggk }" var="j">
             	<li><a href="${path }/inst/index.shtml?type=${j.value.DICT_VALUE}">${j.value.DICT_NAME }</a></li>
             </c:forEach>
             </ul>
         </li>
         <li><a href="${path }/cnt/list.shtml?model=8&type=10&page=1">新闻动态</a><%-- 
             <ul>
             <c:forEach items="${zxdt }" var="z">
             	<li><a href="${path }/cnt/list/1.shtml?model=8&type=${z.MT_ID}">${z.MT_NAME }</a></li>
             </c:forEach>
             </ul>
          --%></li>
         <li><a href="${path }/cnt/list.shtml?model=13&type=14&page=1">欧中外交</a></li>
         <li><a href="#">人文交流</a></li>
         <li><a href="${path }/ps/index.shtml">经济合作</a></li>
         <li><a href="${path }/pdf/list.shtml?page=1">OUTLOOK</a></li>
         <li><a href="${path }/con/index.shtml">一带一路</a></li>
         <li><a class="btn" style="background-color: dodgerblue;" href="${path }/con/index.shtml">English</a></li>
     </ul>
    </nav>
    
</div>
