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

	<h1>Registration</h1>
	<body>

		<form action="save" method="post">
		
		firstName <input type="text" name="firstName" value='<c:out value="${user.firstName}"/>' size="5"><br>
		<c:if test="${user.hasError('firstName')}">
		<div class="error"><c:out value="${user.getError('firstName')}"/></div>
		</c:if>
		
		lastName <input type="text" name="lastName" value='<c:out value="${user.lastName}"/>' size="30"><br>
		<c:if test="${user.hasError('lastName')}">
		<div class="error"><c:out value="${user.getError('lastName')}"/></div>
		</c:if>

		email <input type="text" name="email" value='<c:out value="${user.email}"/>' size="50"><br>
		<c:if test="${user.hasError('email')}">
		<div class="error"><c:out value="${user.getError('email')}"/></div>
		</c:if>

		nick <input type="text" name="nick" value='<c:out value="${user.nick}"/>' size="100"><br>
		<c:if test="${user.hasError('nick')}">
		<div class="error"><c:out value="${user.getError('nick')}"/></div>
		</c:if>
		
		password <input type="text" name="password" value='<c:out value="${user.password}"/>' size="100"><br>
		<c:if test="${user.hasError('password')}">
		<div class="error"><c:out value="${user.getError('password')}"/></div>
		</c:if>

		<input type="submit" name="method" value="Register">
		<input type="submit" name="method" value="Cancel">
		
		</form>


	</body>
</html>