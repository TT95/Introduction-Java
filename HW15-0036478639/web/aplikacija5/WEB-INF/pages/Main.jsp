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



<c:if test="${!sessionScope.containsKey('current.user.id')}">

<h2>Login:</h2>
<form action="login" method="post">

		nick <input type="text" name="nick" value='<c:out value="${user.nick}"/>' size="40"><br>
		<c:if test="${user.hasError('nick')}">
		<div class="error"><c:out value="${user.getError('nick')}"/></div>
		</c:if>
		
		password <input type="text" name="password" value='<c:out value="${user.password}"/>' size="40"><br>
		<c:if test="${user.hasError('password')}">
		<div class="error"><c:out value="${user.getError('password')}"/></div>
		</c:if>

		<input type="submit" name="method" value="Login">
		
		</form>

</c:if>


	<h2>Registration:</h2>
	For new registration click <a href="register">here</a>
	<h2>List of registered authors:</h2>
	<ul>
	<c:forEach var="user" items="${users }" varStatus="status">
	<li>${user.firstName} ${user.lastName} (${user.nick })
	<a href="author/${user.nick}">entries</a></li>
	</c:forEach>
	</ul>
	
</body>
</html>