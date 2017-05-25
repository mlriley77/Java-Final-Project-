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
    <style>
        body {margin:0;}

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #3BB9FF;
            position: fixed;
            top: 0;
            width: 100%;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-family: Tahoma;
        }

        li a:hover:not(.active) {
            background-color: #FFA62F;
        }

        .active {
            background-color: #FFA62F;
        }
    </style>
    <title>CheckIn - Admin Dashboard</title>
</head>
<body>
    <title>CheckIn - Admin Dashboard</title>usergroup
<ul>
    <li><a class="active" href="adminDashboard.jsp">Parent Console</a></li>
    <li><a href="childConsole.jsp">Child Console</a></li>
    <li><a href="welcome.jsp">Logout</a></li>
</ul>

</body>
</html>
