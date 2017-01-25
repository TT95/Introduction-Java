<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
	
		<style type="text/css">
		.error {
		   font-family: ariel;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		}
		</style>
	</head>

	<h1>New entry</h1>
	<body>

		<form action="create" method="post">
		
		title <input type="text" name="title" value='<c:out value="${entry.title}"/>' size="40"><br>
		<c:if test="${entry.hasError('title')}">
		<div class="error"><c:out value="${entry.getError('title')}"/></div>
		</c:if>
		
		text <input type="text" name="text" value='<c:out value="${entry.text}"/>' size="100"><br>
		<c:if test="${entry.hasError('text')}">
		<div class="error"><c:out value="${entry.getError('text')}"/></div>
		</c:if>

		<input type="submit" name="method" value="Create">
		
		</form>

	<br> <br> <br>
	<a href="/aplikacija5/servleti/author/${nick }"> &lt&lt Go back </a>
	
	</body>
</html>