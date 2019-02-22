<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 17.02.2019
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="login">
    <form action="/login" method="post">
        Email: <input type="text" name="email"/><br>
        Password: <input type="password" name="password"/><br>
        <input type="submit" value="Login">


    </form>

    <form action="registre.jsp"><input type="submit" value="Registre"></form>

</div>
</body>
</html>
