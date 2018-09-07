<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<body>
<h1>Serial Monitor Logger</h1>
<h2>Available ports:</h2>

<c:forEach var="column" items="${columnsOfPorts}">
    <ul>
        <c:forEach items="${column}" var="port">
            <li>${port}</li>
        </c:forEach>
    </ul>
</c:forEach>

</body>
</html>