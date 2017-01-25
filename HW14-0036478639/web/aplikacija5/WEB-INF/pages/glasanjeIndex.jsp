<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<body>
	<ol>
		<c:forEach var="pollOption" items="${pollOptions }" varStatus="status">
			<li><a href="glasanje-glasaj?id=${pollOption.id}"> ${pollOption.optionTitle}</a></li>
		</c:forEach>
	</ol>
	<br> <br> <br>
	<a href='index.html'> &lt&lt Go back </a>
</body>
</html>