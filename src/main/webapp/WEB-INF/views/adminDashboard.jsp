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
<%--<ul>--%>
    <%--<li><a class="active" href="adminDashboard.jsp">Parent Console</a></li>--%>
    <%--<li><a href="childDashboard.jsp">Child Console</a></li>--%>
    <%--<li><a href="welcome.jsp">Logout</a></li>--%>
<%--</ul>--%>


    <br>
    <br>
    <a href="#" id="get_location">Get Location</a>
    <div id="map">
        <iframe id="google_map"
                width="425" height="350"
                frameborder="0" scrolling="no"
                marginheight="0" marginwidth="0" src="https://maps.google.com?output=embed"></iframe>
    </div>
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



    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHLLLdbLtD8-GFY-wHJTzy08_q-4M_oGM&callback=initMap">
    </script>

</body>
</html>
