<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.zip.action.BaseAction"%>
<style>
	nav ul li a{
		font-size: 15px;
	}
</style>
<div class="topEN"></div>
<div class="title">
<nav>
     <ul>
         <li><a href="${path }/">Home Page</a></li>
         <li><a href="${path }/inst/index.shtml?type=1">About ECCRD</a>
             <ul>
             <li><a href="${path }/inst/index.shtml?type=1">Leader Speech</a></li>
			 <li><a href="${path }/inst/index.shtml?type=4">The Leads</a></li>
			 <li><a href="${path }/inst/index.shtml?type=5">Organization</a></li>
             </ul>
         </li>
         <li><a href="${path }/cnt/list.shtml?model=1&page=1">News</a></li>
         <li><a href="${path }/cnt/list.shtml?model=2&page=1">Diplomatic Research</a></li>
         <li><a href="${path }/cnt/list.shtml?model=3&page=1">Cultural＆Economic</a></li>
         <%-- <li><a href="${path }/cnt/list.shtml?model=4&page=1">经济合作</a></li> --%>
         <li><a href="${path }/cnt/list.shtml?model=5&page=1">OUTLOOK</a></li>
         <%-- <li><a href="${path }/cnt/list.shtml?model=6&page=1">过往成就</a></li> --%>
         <li><a class="btn" style="background-color: dodgerblue;" href="${path }/">Chinese</a></li>
     </ul>
    </nav>
</div>
