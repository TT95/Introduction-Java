<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String pickedColor = (String)request.getSession().getAttribute("pickedBgCol"); 
if(pickedColor == null) {
	pickedColor = "white";
}
long milisec = (long)request.getServletContext().getAttribute("startTime");
long duration = System.currentTimeMillis()-milisec;
Date date = new Date(duration);
SimpleDateFormat format = new SimpleDateFormat(
		"'minutes:'mm 'seconds:'ss 'miliseconds:'SSS");
String dur = format.format(date);
%>

<html>
<body bgcolor="<%=pickedColor%>">
<h1>Application info</h1>
	Application is running as long as <br> <%=dur%>
	<br> <br> <br>
<a href='index.html'> &lt&lt Go back </a>
</body>
</html>