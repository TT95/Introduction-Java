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
	<a href='setcolor?color=white'>WHITE</a>
	<a href='setcolor?color=green'>GREEN</a>
	<a href='setcolor?color=red'>RED</a>
	<a href='setcolor?color=cyan'>CYAN</a>
	<br> <br> <br>
<a href='index.html'> &lt&lt Go back </a>
</body>
</html>