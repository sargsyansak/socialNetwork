
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
            <c:if test="${message.file ne ''}">
                <a href="/getFile?file=${message.file}">Download</a>
            </c:if>
        </c:forEach>
    </c:if>
    <form action="/user/sendMessage?userId=${requestScope.get('toUser').id}" method="post" enctype="multipart/form-data">
        Message: <input type="text" name="message">
        <input type="submit">
        File: <input type="file" name="file">


    </form>

</div>

</body>
</html>
