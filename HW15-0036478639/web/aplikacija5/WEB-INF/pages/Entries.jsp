<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<body>
<c:choose>
	<c:when test="${sessionScope.containsKey('current.user.id')}">
      ${sessionScope.get('current.user.fn') }
      ${sessionScope.get('current.user.ln') }
      <a href="/aplikacija5/servleti/logout">logout</a>
	</c:when>
	<c:otherwise>
	Not logged in
	</c:otherwise>
</c:choose>

	<h2>Blog entries:</h2>
	<ul>
	<c:forEach var="entry" items="${entries }" varStatus="status">
	<li>${entry.title} <a href="${nick }/${entry.id}">open</a></li>
	</c:forEach>
	</ul>
	
	<c:if test="${sessionScope.containsKey('current.user.id')}">
	<h3>Create new entry:</h3>
	Click <a href="${nick }/new">here</a> for new entry under your account
	</c:if>
	
	<br> <br> <br>
	<a href="/aplikacija5/servleti/main"> &lt&lt Go back </a>
</body>
</html>