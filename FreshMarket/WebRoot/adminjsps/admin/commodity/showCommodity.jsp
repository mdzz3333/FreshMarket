<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>商品列表</title>
    
    
    <script type="text/javascript">
		function deleteitem(id){
		
			var b = window.confirm("你确认删除吗??");
			if(b){
				window.location.href="${pageContext.request.contextPath}/AdminCommodityServlet?method=deleteCommodity&commodityID="+id;
			}
		}
	</script>
  </head>
  
  <body>
    	<table border="1" align="center" >
    		<tr>
	    		<td>商品名称</td>
	    		<td>商品种类</td>
	    		<td>商品图片</td>
	    		<td>商品价格</td>
	    		<td>操作</td>		
    		</tr>
    			<tr>
	    			<td width="30%">${commodity.commodityName}</td>
	    			<td width="20%">${commodity.commodityType.commodityTypeName}</td>
	    			<td><img src="${commodity.url}" width="90" height="90"/></td>
	    			<td width="20%">${commodity.commodityPrice}</td>
	    			<td width="20%">
		    			<a href="javascript:void(0)" onclick="deleteitem(${commodity.commodityID})">删除</a>  |
		    			     
		    			<a href="${pageContext.request.contextPath}/AdminCommodityServlet?method=update&commodityID=${commodity.commodityID}">修改</a>
	    			</td>
    			</tr>
    		
    	</table>
  </body>
</html>
