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
	<h1>Trigonometric values for given input:</h1>
		<br> <br>
<a href='index.html'> &lt&lt Go back </a>
<br> <br>
	<table border='1'>
		<thead>
		<tr><th>degree</th><th>sin</th><th>cos</th></tr>
		</thead>
		<tbody>
		<c:forEach var="trig" items="${results }" varStatus="status">
			<tr><td>${status.index}</td><td>${trig.sin}</td><td>${trig.cos}</td></tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>