<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style type="text/css">
	body {
		background: #15B69A;
		margin: 0px;
		color: #ffffff;
	}
	a {
		font-family:YouYuan;
		text-transform:none;
		text-decoration:none;
		color: #ffffff;
		font-weight: 900;
	} 
	a:hover {
		text-decoration:underline;
	}
	#user{
		font-size:15px;
	}
</style>
  </head>
  
  <body>
<a href="http://localhost:8080/goods/index.jsp" target="_top">
	<h1 style="font-family:YouYuan;text-align: center;">激昂网上书城系统</h1>
</a>
<div style="font-size: 10pt; line-height: 10px;">
	<c:choose>
	<c:when test="${empty sessionScope.username }">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">会员登录</a> |&nbsp; 
		<a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">注册会员</a>
	</c:when>
	<c:otherwise>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="user">欢迎,${sessionScope.username }</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<c:url value="/CartItemServlet" var="cartItemUrl">
			<c:param name="method" value="findByUsername"/>
			<c:param name="uid" value="${sessionScope.uid }"/>
		</c:url>
		<a href="${cartItemUrl }" target="body">我的购物车</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/OrderServlet?method=findOrderByUid'/>" target="body">我的订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/UserServlet?method=invalidate'/>" target="_parent">退出</a>	
	</c:otherwise>
	</c:choose>
		</div>
	</div>
  </body>
</html>
