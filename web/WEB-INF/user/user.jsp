<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="../../style.css">
<html>
<head>
    <title>Title</title>
</head>
<body>

<img src="/getImage?picName=${sessionScope.get('user').picUrl}" width="200px"/>
<h2>${sessionScope.get('user').name}
</h2>
<h2>${sessionScope.get('user').surname}
</h2>
<a href="/logout">LOGOUT</a>
<a href="/user/userProfile" id="changeProfile">Change Profile</a>



<hr>
<div class="info">
    <h3>ALL USERS</h3>
    <c:choose>
        <c:when test="${requestScope.get('allUser') != null && fn:length(requestScope.get('allUser')) != 0}">
            <div class="allUser">

                <c:if test="${sessionScope.get('errorMessage') !=null}">
                    <h3 style="color: red">${sessionScope.get('errorMessage')}</h3>

                </c:if>
                    ${sessionScope.remove('errorMessage')}

                <table border="1">
                    <tr>
                        <td>Name</td>
                        <td>Surname</td>
                        <td>Picture</td>
                        <td>Action</td>
                    </tr>
                    <c:forEach var="user" items="${requestScope.get('allUser')}">

                        <tr>
                            <td>${user.name}
                            </td>
                            <td>${user.surname}
                            </td>
                            <td><img src="/getImage?picName=${user.picUrl}" width="100"/></td>
                            <td><a href="/user/friendRequest?toId=${user.id}"> Send </a></td>

                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:when>
        <c:when test="${requestScope.get('allUser') == null || fn:length(requestScope.get('allUser')) == 0}">
            <p>No users yet</p></c:when>
    </c:choose>

    <hr>

    <h3>ALL REQUESTS</h3>

    <c:choose>
        <c:when test="${requestScope.get('allRequests') != null && fn:length(requestScope.get('allRequests')) != 0}">
            <div class="allRequests">

                <table border="1">
                    <tr>
                        <td>Name</td>
                        <td>Surname</td>
                        <td>Picture</td>
                        <td>Accept or Reject</td>
                    </tr>
                    <c:forEach var="allRequests" items="${requestScope.get('allRequests')}">

                        <tr>
                            <td>${allRequests.name}
                            </td>
                            <td>${allRequests.surname}
                            </td>

                            <td><img src="/getImage?picName=${allRequests.picUrl}" width="100"/></td>
                            <td><a href="/user/acceptOrReject?fromId=${allRequests.id}&action=accept">Accept</a>
                                <a href="/user/acceptOrReject?fromId=${allRequests.id}&action=reject">Reject</a></td>
                        </tr>

                    </c:forEach>
                </table>
            </div>
        </c:when>
        <c:when test="${requestScope.get('allRequests') == null || fn:length(requestScope.get('allRequests')) == 0}">
            <p>No requests yet</p></c:when>
    </c:choose>

    <hr>

    <h3>ALL FRIENDS</h3>

    <c:choose>
        <c:when test="${requestScope.get('allFriends') != null && fn:length(requestScope.get('allFriends')) != 0}">
            <div class="allFriends">
                <table border="1">
                    <tr>
                        <td>Name</td>
                        <td>Surname</td>
                        <td>Picture</td>
                        <td>Delete Friend</td>
                        <td>Message</td>
                    </tr>
                    <c:forEach var="allFriends" items="${requestScope.get('allFriends')}">

                        <tr>
                            <td>${allFriends.name}
                            </td>
                            <td>${allFriends.surname}
                            </td>
                            <td><img src="/getImage?picName=${allFriends.picUrl}" width="100"></td>
                            <td><a href="/user/remove?id=${allFriends.id}">Delete</a></td>
                            <td><a href="user/messagePage?toId=${allFriends.id}">Message</a></td>
                        </tr>


                    </c:forEach>
                </table>
            </div>
        </c:when>
        <c:when test="${requestScope.get('allFriends') ==null || fn:length(requestScope.get('allFriends')) == 0}">
            <p>No friends yet</p></c:when>
    </c:choose>
    <hr>


</div>
</body>
</html>
