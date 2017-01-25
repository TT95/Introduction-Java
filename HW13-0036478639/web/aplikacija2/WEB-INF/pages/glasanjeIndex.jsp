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
	<h1>Vote for your favorite band:</h1>
	<p>What is your favorite band? Click on link for voting!</p>
	<ol>
		<c:forEach var="band" items="${bands }" varStatus="status">
			<li><a href="glasanje-glasaj?id=${band.id}">${band.name}</a></li>
		</c:forEach>
	</ol>
	<br> <br> <br>
	<a href='index.html'> &lt&lt Go back </a
</body>
</html>