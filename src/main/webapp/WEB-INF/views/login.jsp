<%--
  Created by IntelliJ IDEA.
  User: Sarah Guarino
  Date: 5/24/2017
  Time: 12:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Check-In - Login</title>
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<h1>Check-In</h1>
${navbar}
<h2>Please login.</h2>
<form action="${pageContext.request.contextPath}/dashboardentry" method="post">
    Email: <input title="Email" type="text"
                     id="email" name="email"
                     onclick="" /><br />
    Password: <input title="Password" type="password"
                             id="password" name="password"
                             onclick="" /><br />
    ${err}
    <input type="submit" value="Log In" />
</form>
</body>
</html>
