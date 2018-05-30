<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单详细</title>

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
	href="<c:url value='/jsps/css/order/desc.css'/>">
</head>

<body>
	<c:choose>
		<c:when test="${empty requestScope.orderdesc }">
			<table width="95%" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right"><img align="top"
						src="<c:url value='/images/icon_empty.png'/>" /></td>
					<td><br /> <span style="font-family:YouYuan;font-size:22px;"
						class="spanEmpty">&nbsp;您的订单详情中没有信息！</span></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<div class="divOrder">
				<span>订单号：${orderdesc.oid } 
				<c:choose>
						<c:when test="${orderdesc.status eq 1 }">
							<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(等待付款)</a>
						</c:when>
						<c:when test="${orderdesc.status eq 2 }">
							<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(准备发货)</a>
						</c:when>
						<c:when test="${orderdesc.status eq 3 }">
							<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(等待确认)</a>
						</c:when>
						<c:when test="${orderdesc.status eq 4 }">
							<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(交易成功)</a>
						</c:when>
						<c:when test="${orderdesc.status eq 5 }">
							<a style="font-family:YouYuan;font-size:18px;color:#B22222;">(已取消)</a>
						</c:when>
					</c:choose>
					 下单时间：${orderdesc.ordertime }
				</span>
			</div>
			<div class="divContent">
				<div class="div2">
					<dl>
						<dt>收货人信息</dt>
						<dd style="color:#20B2AA;font-size:18px;font-family:YouYuan;font-weight:bold ;">${orderdesc.address }</dd>
					</dl>
				</div>
				<div class="div2">
					<dl>
						<dt>商品清单</dt>
						<dd>
							<table cellpadding="0" cellspacing="0">
								<tr>
									<th class="tt">商品名称</th>
									<th class="tt" align="left">单价</th>
									<th class="tt" align="left">数量</th>
									<th class="tt" align="left">小计</th>
								</tr>
							<c:forEach items="${orderdesc.orderItemList }" var="orderitem">
								<tr style="padding-top: 20px; padding-bottom: 20px;">
									<td class="td" width="400px">
										<div class="bookname">
										<a href="<c:url value='/BookServlet?method=findByBid&bid=${orderitem.bid }'/>">
											<img align="middle"  width="70" src="<c:url value='/${orderitem.image_b }'/>" /> 
										</a>	
											<a style="font-family:YouYuan;font-size:18px;" href="<c:url value='/BookServlet?method=findByBid&bid=${orderitem.bid }'/>">${orderitem.bname }</a>
										</div>
									</td>
									<td class="td"><span style="font-family:YouYuan;font-size:15px;color:#B22222;">&yen; ${orderitem.currPrice }</span></td>
									<td class="td"><span style="font-family:YouYuan;font-size:15px;color:#B22222;">&nbsp;${orderitem.quantity }</span></td>
									<td class="td"><span style="font-family:YouYuan;font-size:15px;color:#B22222;">&yen;${orderitem.subtotal }</span></td>
								</tr>
							</c:forEach>
							</table>
						</dd>
					</dl>
				</div>
				<div style="margin: 10px 10px 10px 550px;">
					<span style="font-weight: 900; font-size: 15px;">合计金额：</span> 
					<span class="price_t">&yen;${orderdesc.total }</span><br /> 
					<c:choose>
						<c:when test="${orderdesc.status eq 1}">
							<a href="<c:url value='/OrderServlet?method=pay&oid=${order.oid }'/>" class="pay"></a><br />
							<a id="cancel" href="<c:url value='/OrderServlet?method=updateStatus&status=5&oid=${order.oid }'/>">取消订单</a><br />
						</c:when>
						<c:when test="${orderdesc.status eq 3}">
							 <a id="confirm" href="<c:url value='/OrderServlet?method=updateStatus&status=4&oid=${order.oid }'/>">确认收货</a><br />
						</c:when>
					</c:choose>
				</div>
			</div>
		</c:otherwise>
	</c:choose>

</body>
</html>

