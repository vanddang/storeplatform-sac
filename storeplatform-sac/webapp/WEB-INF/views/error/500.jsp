<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isErrorPage="true"%>
<html>
<head>
<title>Error Page</title>
</head>
<body>
	<h1>Store Platform Error Information</h1>
<%
	Exception ex = (Exception) request.getAttribute("exception");
	if (ex instanceof com.skplanet.storeplatform.framework.core.exception.StorePlatformException) {
		com.skplanet.storeplatform.framework.core.exception.StorePlatformException spex = (com.skplanet.storeplatform.framework.core.exception.StorePlatformException) ex;
%>
	<ul>
		<li><b>Code :</b><%=spex.getErrorInfo().getCode()%></li>
		<li><b>Message :</b><%=spex.getErrorInfo().getMessage()%></li>
		<li><b>Host Name :</b><%=spex.getErrorInfo().getHostName()%></li>
		<li><b>Instance Name :</b><%=spex.getErrorInfo().getInstanceName()%></li>
	</ul>
<%
	} else {
%>
	<ul><li>This error is not a StorePlatformException.</li></ul>
<%
	}
%>	
	<h1>General Error Information</h1>
	<table width="100%" border="1">
		<tr valign="top">
			<td><b>Error:</b></td>
			<td>${pageContext.exception}</td>
		</tr>
		<tr valign="top">
			<td><b>URI:</b></td>
			<td>${pageContext.errorData.requestURI}</td>
		</tr>
		<tr valign="top">
			<td><b>Status code:</b></td>
			<td>${pageContext.errorData.statusCode}</td>
		</tr>
		<tr valign="top">
			<td><b>Stack trace:</b></td>
			<td><c:forEach var="trace"
					items="${pageContext.exception.stackTrace}">
					<p>${trace}</p>
				</c:forEach></td>
		</tr>
	</table>
</body>
</html>