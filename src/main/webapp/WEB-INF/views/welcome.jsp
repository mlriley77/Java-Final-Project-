<%--
  Created by IntelliJ IDEA.
  User: MichaelRiley
  Date: 5/8/17
  Time: 1:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Check-In - Home</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<div id="nav-bar" class="gutter">
    <span id="menu-check">Check</span><span id="menu-in">in</span>
    <div id="menu-list">
        <span class="menu-item"><b>Family Id:</b> <i>${family.familyid}</i></span>
        <a href="${pageContext.request.contextPath}/"><span class="menu-item"><b>Home</b></span></a>
        <a href="${pageContext.request.contextPath}/dashboard"><span class="menu-item"><b>Dashboard</b></span></a>
        <a href="${pageContext.request.contextPath}/action=login"><span class="menu-item"><b>Login</b></span></a>
        <a href="${pageContext.request.contextPath}/action=register/user"><span class="menu-item"><b>Register a User Account</b></span></a>
        <a href="${pageContext.request.contextPath}/action=register/family"><span class="menu-item"><b>Register an Admin Account</b></span></a>
        <a href="${pageContext.request.contextPath}/action=logout"><span class="menu-item"><b>Logout</b></span></a>
    </div>
</div>
<h2>Hello, and welcome to check-In!</h2>
Check-In is a....<br />

${jsonString}

</body>
</html>
