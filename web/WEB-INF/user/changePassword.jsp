<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 28.02.2019
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Password</title>
</head>
<body>

<img src="/getImage?picName=${sessionScope.get('user').picUrl}" width="200">
<h2>${sessionScope.get('user').name} ${sessionScope.get('user').surname}</h2>
<hr>

<div class="changePass">
    <h3 style="color: darkslateblue"> Hello ${sessionScope.get('user').name} you can change your password</h3><br>

    <c:if test="${requestScope.get('errorMessage') != null}">
        <h4>${requestScope.get('errorMessage')}</h4>
    </c:if>

    <c:if test="${requestScope.get('message') != null}">
        <h4 style="color: green">${requestScope.get('message')}</h4>
    </c:if>

    <form action="/user/changePassword" method="post">
        Old Password: <input type="password" name="oldPassword"><br><br>
        New Password: <input type="password" name="newPassword"><br><br>
        Again New Password: <input type="password" name="repeatNewPassword"><br><br>
        <input type="submit" value="Change" id="change">
    </form>
    <form action="/user/home" method="post">
        <input type="submit" value="Home">
    </form>
</div>
</body>
</html>