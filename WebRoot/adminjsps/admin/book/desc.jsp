<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>book_desc.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/book/desc.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datepick.css'/>">
<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>

<script type="text/javascript" src="<c:url value='/adminjsps/admin/js/book/desc.js'/>"></script>

<script type="text/javascript">

$(function() {
	$("#box").attr("checked", false);
	$("#formDiv").css("display", "none");
	$("#show").css("display", "");	
	
	// 操作和显示切换
	$("#box").click(function() {
		if($(this).attr("checked")) {
			$("#show").css("display", "none");
			$("#formDiv").css("display", "");
		} else {
			$("#formDiv").css("display", "none");
			$("#show").css("display", "");		
		}
	});
});

</script>
  </head>
  
  <body>
    <input type="checkbox" id="box"><label for="box">编辑或删除</label>
    <br/>
    <br/>
  <div id="show">
    <div class="sm">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</div>
    <img align="top" src="<c:url value='/book_img/23254532-1_w.jpg'/>" class="tp"/>
    <div id="book" style="float:left;">
	    <ul>
	    	<li>商品编号：4A9574F03A6B40C1B2A437237C17DEEC</li>
	    	<li>当前价：<span class="price_n">&yen;40.7</span></li>
	    	<li>定价：<span style="text-decoration:line-through;">&yen;59.0</span>　折扣：<span style="color: #c30;">6.9</span>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
			<tr>
				<td colspan="3">
					作者：Craig Walls著
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：人民邮电出版社</a>
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：2013-6-1</td>
			</tr>
			<tr>
				<td>版次：1</td>
				<td>页数：374</td>
				<td>字数：487000</td>
			</tr>
			<tr>
				<td width="180">印刷时间：2013-6-1</td>
				<td>开本：16开</td>
				<td>纸张：胶版纸</td>
			</tr>
		</table>
	</div>
  </div>
  
  
  <div id='formDiv'>
   <div class="sm">&nbsp;</div>
   <form action="javascript:alert('编辑或删除图书成功！')" method="post" id="form">
   	<input type="hidden" name="bid" value=""/>
   	<input type="hidden" name="image_w" value=""/>
   	<input type="hidden" name="image_b" value=""/>
    <img align="top" src="<c:url value='/book_img/23254532-1_w.jpg'/>" class="tp"/>
    <div style="float:left;">
	    <ul>
	    	<li>商品编号：4A9574F03A6B40C1B2A437237C17DEEC</li>
	    	<li>书名：　<input id="bname" type="text" name="bname" value="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" style="width:500px;"/></li>
	    	<li>当前价：<input id="currPrice" type="text" name="currPrice" value="40.7" style="width:50px;"/></li>
	    	<li>定价：　<input id="price" type="text" name="price" value="59.0" style="width:50px;"/>
	    	折扣：<input id="discount" type="text" name="discount" value="6.9" style="width:30px;"/>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
			<tr>
				<td colspan="3">
					作者：　　<input id="author" type="text" name="author" value="Craig Walls" style="width:150px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：　<input id="press" type="text" name="press" value="人民邮电出版社" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：<input id="publishtime" type="text" name="publishtime" value="2013-6-1" style="width:100px;"/></td>
			</tr>
			<tr>
				<td>版次：　　<input id="edition" type="text" name="edition" value="1" style="width:40px;"/></td>
				<td>页数：　　<input id="pageNum" type="text" name="pageNum" value="374" style="width:50px;"/></td>
				<td>字数：　　<input id="wordNum" type="text" name="wordNum" value="48700" style="width:80px;"/></td>
			</tr>
			<tr>
				<td width="250px">印刷时间：<input id="printtime" type="text" name="printtime" value="2013-6-1" style="width:100px;"/></td>
				<td width="250px">开本：　　<input id="booksize" type="text" name="booksize" value="16" style="width:30px;"/></td>
				<td>纸张：　　<input id="paper" type="text" name="paper" value="胶版纸" style="width:80px;"/></td>
			</tr>
			<tr>
				<td>
					一级分类：<select name="pid" id="pid" onchange="loadChildren()">
						<option value="">==请选择1级分类==</option>
			    		<option value="1" selected='selected'>程序设计</option>
			    		<option value="2">办公室用书</option>
			    		<option value="3">图形 图像 多媒体</option>
			    		<option value="4">操作系统/系统开发</option>
					</select>
				</td>
				<td>
					二级分类：<select name="cid" id="cid">
						<option value="">==请选择2级分类==</option>
			    		<option value="1" selected='selected'>Java Javascript</option>
			    		<option value="2">JSP</option>
			    		<option value="3">C C++ VC VC++</option>
					</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2">
					<input onclick="editForm()" type="button" name="method" id="editBtn" class="btn" value="编　　辑">
					<input onclick="delForm()" type="button" name="method" id="delBtn" class="btn" value="删　　除">
				</td>
				<td></td>
			</tr>
		</table>
	</div>
   </form>
  </div>

  </body>
</html>
