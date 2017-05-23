<%--
  Created by IntelliJ IDEA.
  User: Sarah Guarino
  Date: 5/22/2017
  Time: 4:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CheckIn - Admin Dashboard</title>usergroup
</head>
<body>
The ${family.name} ID#: ${family.familyid}<br />
<a href="${pageContext.request.contextPath}/dashboard/admin/newChild?id=${family.familyid}">Create a sub-account</a>
</body>
</html>
