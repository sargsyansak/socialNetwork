<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 21.02.2019
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Message</title>
</head>
<body>
<div class="message">
    <c:if test="${requestScope.get('allMessages') != null}">
        <c:forEach var="message" items="${requestScope.get('allMessages')}">
            <h2>From: ${message.fromId.name} To: ${message.toId.name}</h2>
            <p>${message.message}</p>
            <h5>${message.date}</h5>
        </c:forEach>
    </c:if>
    <form action="/user/sendMessage?userId=${requestScope.get('toUser').id}" method="post">
        <input type="text" name="message">
        <input type="submit">

    </form>

</div>

</body>
</html>
