<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String pickedColor = (String)request.getSession().getAttribute("pickedBgCol"); 
if(pickedColor == null) {
	pickedColor = "white";
}
%>

<html>
<body bgcolor="<%=pickedColor%>">
	<h1OS usage></h1>
	<p>Here are the results of OS usage in survey that we completed:</p>
	<a href='report'>Chart report</a> <br>
	<br> <br> <br>
<a href='index.html'> &lt&lt Go back </a>
</body>
</html>