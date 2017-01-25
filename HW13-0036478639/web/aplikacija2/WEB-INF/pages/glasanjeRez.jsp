<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String pickedColor = (String)request.getSession().getAttribute("pickedBgCol"); 
if(pickedColor == null) {
	pickedColor = "white";
}
%>

<html>
<head>
<style type="text/css">
table.rez td {text-align: center;}
</style>
</head>
<body bgcolor="<%=pickedColor%>">
	<h1>These are the voting results</h1>
	<table border="1" cellspacing="0" class="rez">
	<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
	<tbody>
	<c:forEach var="result" items="${results }" varStatus="status">
	<tr><td>${result.name }</td><td>${result.num }</td></tr>
	</c:forEach>
	</tbody>
	</table>
	
	<h2>Graphical result review</h2>
	<img alt="Pie-chart" src="${pageContext.request.contextPath}/glasanje-grafika"
	 width="500" height="270" />

	<h2>Results in XLS format</h2>	
	<p>Results in XLS format are avaliable 
	<a href="${pageContext.request.contextPath}/glasanje-xls">here</a></p>

	<h2>Other</h2>
	<p>Winning band(s) song example:</p>
	<ul>
	<c:forEach var="winner" items="${winners }" varStatus="status">
	<li>${winner.name }: <a href="${winner.song }"
	target="_blank">link</a></li>
	</c:forEach>
	</ul>

	<br> <br> <br>
	<a href='index.html'> &lt&lt Go back </a>
</body>
</html>