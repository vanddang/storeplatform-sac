<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" import="javax.naming.*" %>
<html>
<body>
<% InitialContext initialContext = new InitialContext();
   Object obj=initialContext.lookup("java:comp/env/jdbc/storeplatformDB");
   out.print("<b>"+obj+"</b>");
 %>
</body>
</html>
