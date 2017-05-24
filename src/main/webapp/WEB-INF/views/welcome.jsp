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
    <title>Spring Demo</title>
</head>
<body>

<a href="${pageContext.request.contextPath}/register/family">Register A New Family Account</a>
<a href="${pageContext.request.contextPath}/register/user">Register A New User Account</a>
${hello}

${jsonString}

</body>
</html>
