<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 01.03.2019
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<img src="/getImage?picName=${sessionScope.get('user').picUrl}" width="200px"><br>
<h2>${sessionScope.get('user').name} ${sessionScope.get('user').surname} </h2>
<hr>
<br><br>
<div class="change">


    <c:if test="${requestScope.get('message') != null}">
        <h4 style="color: green">${requestScope.get('message')}</h4>
    </c:if>

    <form action="/user/changeUser" method="post" enctype="multipart/form-data"><br>

        Name: <input type="text" name="name"><br><br>
        Surname: <input type="text" name="surname" id="surname"><br><br>
        Picture: <input type="file" name="file"><br><br>
        <input type="submit" value="Change"><br>
    </form>
    <form action="/user/home" method="post">
        <input type="submit" value="Home">
    </form>
    <form action="/user/changePassword" method="post"><br>
        <input type="submit" value="Change Password"><br>
    </form>
</div>
</body>
</html>
