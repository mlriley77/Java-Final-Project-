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

<br><br><br>
<form method="get" action="" onsubmit="return getLocation();">
    <input type="text" id="lat" name="lat" /><br>
    <input type="text" id="long" name="long" /><br>
    <input type="submit" value ="Send location to your parent" name = "Submit" id="get_location" />
</form>

<script>
    var c = function(pos) {
        var lat = pos.coords.latitude,
            long = pos.coords.longitude,
            coords = lat + ', ' + long;

        document.getElementById('google_map').setAttribute('src', 'https://maps.google.com/?q=' + coords + '&z=60&output=embed');
        document.getElementById('lat').setAttribute('value', lat);
        document.getElementById('long').setAttribute('value', long);
    };

    var getLocationLink = document.getElementById('get_location');
    getLocationLink.onclick = function(){
        navigator.geolocation.getCurrentPosition(c);
        return false;
    };

    function getLocation () {
        console.log("help");
        navigator.geolocation.getCurrentPosition(c);
        return true;
    }

</script>

<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHLLLdbLtD8-GFY-wHJTzy08_q-4M_oGM&callback=initMap">

</script>

</body>
</html>
