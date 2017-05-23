<%--
  Created by IntelliJ IDEA.
  User: Sarah Guarino
  Date: 5/22/2017
  Time: 3:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CheckIn - Register New User</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/dashboard/admin/newChild/done" method="post">
    <h1>Family Unit</h1>
    Family Id: <input title="Family Id Number" type="text"
                      id="famId" name="famId"
                      onclick=""
                      value="${famId}" /><br />
    <h1>Leader Account</h1>
    First Name: <input title="First Name" type="text"
                       id="fName" name="fName"
                       onclick="" /><br />
    Last Name: <input title="Last Name" type="text"
                      id="lName" name="lName"
                      onclick="" /><br />
    Email: <input title="Email" type="text"
                  id="email" name="email"
                  onclick="" /><br />
    Password: <input title="Password" type="password"
                     id="password" name="password"
                     onclick="" /><br />
    Confirm Password: <input title="Confirm Password" type="password"
                             id="confPassword" name="confPassword"
                             onclick="" /><br />
    <input type="submit" value="Register" />
</form>
</body>
</html>
