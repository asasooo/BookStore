<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/jsps/css/order/list.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/jsps/pager/pager.css'/>" />
<script type="text/javascript"
	src="<c:url value='/jsps/pager/pager.js'/>"></script>
</head>

<body>
	<c:choose>
		<c:when test="${empty requestScope.pb }">
			<!-- 显示空 -->
			<table width="95%" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right"><img align="top"
						src="<c:url value='/images/icon_empty.png'/>" /></td>
					<td><br /> <span style="font-family:YouYuan;font-size:22px;"
						class="spanEmpty">&nbsp;您的订单中没有信息！</span></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<div class="divTitle">
				<span style="margin-left: 150px;margin-right: 280px;">商品信息</span> <span
					style="margin-left: 40px;margin-right: 38px;">金额</span> <span
					style="margin-left: 50px;margin-right: 40px;">订单状态</span> <span
					style="margin-left: 50px;margin-right: 50px;">操作</span>
			</div>
			<c:forEach items="${requestScope.pb.beanList }" var="order">
				<div class="divMain">
					<br />
					<table align="center" border="0" width="100%" cellpadding="0"
						cellspacing="0">
						<tr class="tt">
							<td width="320px">订单号：<a
								href="<c:url value='/OrderServlet?method=findByOid&oid=${order.oid }'/>">${order.oid }</a></td>
							<td width="200px">下单时间：${order.ordertime }</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr style="padding-top: 10px; padding-bottom: 10px;">
							<td colspan="2"><c:forEach items="${order.orderItemList }"
									var="orderitem">
									<a class="link2"
										href="<c:url value='/BookServlet?method=findByBid&bid=${orderitem.bid }'/>">
										<img border="0" width="70"
										src="<c:url value='/${orderitem.image_b }'/>" />
									</a>
								</c:forEach></td>
							<td width="115px"><span style="font-size:20px;"
								class="price_t">&yen;${order.total }</span></td>
							<td width="142px"><c:choose>
									<c:when test="${order.status eq 1 }">
										<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(等待付款)</a>
									</c:when>
									<c:when test="${order.status eq 2 }">
										<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(准备发货)</a>
									</c:when>
									<c:when test="${order.status eq 3 }">
										<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(等待确认)</a>
									</c:when>
									<c:when test="${order.status eq 4 }">
										<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(交易成功)</a>
									</c:when>
									<c:when test="${order.status eq 5 }">
										<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(已取消)</a>
									</c:when>
								</c:choose>
								</td>
								<td>
								<c:choose>
									<c:when test="${order.status eq 1 }">
										<a style="font-size:16px;" href="<c:url value='/OrderServlet?method=findByOid&oid=${order.oid }'/>">查看</a>
										<br />
										<a style="font-size:16px;" href="<c:url value='/OrderServlet?method=pay&oid=${order.oid }'/>">支付</a>
										<br />
										<a style="font-size:16px;" href="<c:url value='/OrderServlet?method=updateStatus&status=5&oid=${order.oid }'/>">取消</a>
										<br />
									</c:when>
									<c:when test="${order.status eq 2 }">
										<a style="font-size:16px;" href="<c:url value='/OrderServlet?method=findByOid&oid=${order.oid }'/>">查看</a>
										<br />
									</c:when>
									<c:when test="${order.status eq 3 }">
										<a style="font-size:16px;" href="<c:url value='/OrderServlet?method=findByOid&oid=${order.oid }'/>">查看</a>
										<br />
										<a style="font-size:16px;" href="<c:url value='/OrderServlet?method=updateStatus&status=4&oid=${order.oid }'/>">确认收货</a>
										<br />
									</c:when>
									<c:when test="${order.status eq 4 }">
										<a style="font-size:16px;" href="<c:url value='/OrderServlet?method=findByOid&oid=${order.oid }'/>">查看</a>
										<br />
									</c:when>
									<c:when test="${order.status eq 5 }">
										<a style="font-size:16px;" href="<c:url value='/OrderServlet?method=findByOid&oid=${order.oid }'/>">查看</a>
										<br />
									</c:when>
							</c:choose>
							</td>
						</tr>
					</table>
				</div>
			</c:forEach>
			<%@include file="/jsps/pager/pager.jsp"%>
		</c:otherwise>
	</c:choose>


</body>
</html>
