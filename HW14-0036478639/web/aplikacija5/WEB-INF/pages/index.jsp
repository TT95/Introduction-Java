<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
	<ol>
		<c:forEach var="poll" items="${polls }" varStatus="status">
			<li><a href="glasanje?id=${poll.id}">${poll.title}</a>${poll.message}</li>
		</c:forEach>
	</ol>
</body>
</html>