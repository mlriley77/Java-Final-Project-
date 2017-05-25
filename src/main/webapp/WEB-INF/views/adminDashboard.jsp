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
    <title>CheckIn - Admin Dashboard</title>
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<h1>Check-In</h1>
${navbar}
<h2>Dashboard</h2>

    <form method="get" action="">
        <input type="submit" value ="Where is my kid?" name = "Submit" />
    </form>
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
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHLLLdbLtD8-GFY-wHJTzy08_q-4M_oGM">
    </script>
</body>
</html>