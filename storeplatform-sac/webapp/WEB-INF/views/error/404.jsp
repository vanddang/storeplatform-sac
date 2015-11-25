<%@ page import="org.springframework.web.util.UrlPathHelper" %><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@page isErrorPage="true"%><%
	response.setHeader("x-sac-result-code", "FAIL");
	response.setHeader("Content-Type", "application/json;charset=UTF-8");

	String url = (new UrlPathHelper()).getOriginatingRequestUri(request);
%>{"code":"SAC_CMN_0063","message":"잘못된 URL(<%=url%>) 입니다.","hostName":"<%=java.net.InetAddress.getLocalHost().getHostName()%>"}