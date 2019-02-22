<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 19.02.2019
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="style.css">

</head>
<body>
<div class="register">
<form action="/register" method="post" enctype="multipart/form-data">

    name:<input type="text" name="name"><br>
    surname:<input type="text" name="surname"><br>
    email:<input type="text" name="email"><br>
    password:<input type="password" name="password"><br>
    image: <input type="file" name="picture"/><br>

    <input type="submit" value="Register"><br>

<form action="login.jsp"><input type="submit" value="Login"></form>
</form>
</div>
</body>
</html>
