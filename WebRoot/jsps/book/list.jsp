<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/book/list.js'/>"></script>
  </head>
  
  <body>

<ul>
<c:choose >
	<c:when test="${empty pb.beanList}">
		<hr style="height:1px;border:none;border-top:1px dashed #20B2AA;"/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
		<a style="color:#C0C0C0;text-align:center;font-family:YouYuan;font-size:56">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;没有查找到内容！ </a>  
	<br/>
	<br/>
	<br/>
	</c:when>
	<c:otherwise>
		<c:forEach items="${pb.beanList }" var="book">
  <li>
  <div class="inner">
  	<c:url var="bidUrl" value="/BookServlet">
		<c:param name="method" value="findByBid"/>
		<c:param name="bid" value="${book.bid }"/>
	</c:url>
    <a class="pic" href="${bidUrl }"><img src="<c:url value='/${book.image_b }'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;${book.currPrice }</span>
		<span class="price_r">&yen;${book.price }</span>
		<span class="price_s">${book.discount }折</span>
	</p>
	<p><a id="bookname" title="${book.bname }" href="${bidUrl }">${book.bname }</a></p>
		<c:url value="/BookServlet" var="authorUrl">
			<c:param name="method" value="findByAuthor"/>
			<c:param name="author" value="${book.author }"/>
		</c:url>
	<p><a href="${authorUrl }" name='P_zz' title='${book.author }'>${book.author }</a></p>
	<p class="publishing">
		<c:url value="/BookServlet" var="pressUrl">
			<c:param name="method" value="findByPress"/>
			<c:param name="press" value="${book.press }"/>
		</c:url>
		<span>出 版 社：</span><a href="${pressUrl }">${book.press }</a>  <!-- 这里的标签末尾可把我坑惨了 -->
	</p>
	<p class="publishing_time"><span>出版时间：</span>${book.publishtime }</p>
  </div>
  </li>
</c:forEach>
   <div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
   </div>
	</c:otherwise>
</c:choose>
</ul>
  </body>
</html>

