<%--
  Created by IntelliJ IDEA.
  User: MichaelRiley
  Date: 5/24/17
  Time: 7:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Child Console</title>
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
</head>
<body>

<ul>
    <li><a href="adminDashboard.jsp">Parent Console</a></li>
    <li><a class="active" href="childDashboard.jsp">Child Console</a></li>
    <li><a href="welcome.jsp">Logout</a></li>
</ul>

<script>
    var c = function(pos) {
        var lat = pos.coords.latitude,
            long = pos.coords.longitude,
            coords = lat + ', ' + long;
        document.getElementById('google_map').setAttribute('src', 'https://maps.google.com/?q=' + coords + '&z=60&output=embed');
    };
    var getLocationLink = document.getElementById('get_location');
    getLocationLink.onclick = function(){
        navigator.geolocation.getCurrentPosition(c);
        return false;
    };
</script>

</body>
</html>
