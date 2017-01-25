<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String pickedColor = (String)request.getSession().getAttribute("pickedBgCol"); 
if(pickedColor == null) {
	pickedColor = "white";
}
%>

<html>
<body bgcolor="<%=pickedColor%>">
	<h1>Java homework 13</h1>
	<a href='colors.jsp'>Set color</a> <br>
	<a href='trigonometric?a=0&b=90'>Trigonomentric functions</a> <br>
	<a href='funny.jsp'>Funny story</a> <br>
	<a href='report.jsp'>Chart report</a> <br>
	<a href='powers'>Powers table</a><br>
	<a href='appinfo.jsp'>app info</a><br>
	<a href='glasanje'>vote</a><br>
</body>
</html>