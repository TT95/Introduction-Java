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

	<h2>${entry.title }</h2>
	${entry.text } 

	
	<ul>
	<c:forEach var="c" items="${entry.comments }" varStatus="status">
	<li><div style="font-weight: bold">[User=<c:out value="${c.usersEMail}"/>] <c:out value="${c.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${c.message}"/></div></li>
	</c:forEach>
	</ul>
	
	<c:if test="${sessionScope.containsKey('current.user.id')}">
	<h4>New comment:</h4>
	<form action="${entry.id }" method="post">
		<input type="text" name="newComment" /><br>
		<input type="submit" name="method" value="Add comment">
	</form>
	
	<c:if test="${sessionScope.get('current.user.nick')==nick }">
	<h3>Edit entry:</h3>
	Click <a href="edit">here</a> for editing this entry
	</c:if>
	</c:if>
	
	<br> <br> <br>
	<a href="/aplikacija5/servleti/author/${nick }"> &lt&lt Go back </a>
</body>
</html>