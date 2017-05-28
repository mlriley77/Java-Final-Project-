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
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<h1>Check-In</h1>
${navbar}
<h2>Dashboard</h2>
<form action="${pageContext.request.contextPath}/action=submitlocation" method="post">
    <input type="submit" value="Check In" />
    <input type="hidden" name="lat" id="lat" />
    <input type="hidden" name="long" id="long" />
    <input type="hidden" name="userId" id="userId" value="${user.userid}"/>
</form>
<b>${parent.fname} last checked in at ${parent.lasttime}</b>
<div id="map">
    <iframe id="google_map"
            width="425" height="350"
            frameborder="0" scrolling="no"
            marginheight="0" marginwidth="0" src="https://maps.google.com/?q=${parent.lastlat}, ${parent.lastlong}&z=15&output=embed"></iframe>
</div>
<script>
    window.onload = function() {
        var c = function(pos) {
            var lat = pos.coords.latitude,
                long = pos.coords.longitude;
            document.getElementById('lat').value = lat;
            document.getElementById('long').value = long;
        };
        navigator.geolocation.getCurrentPosition(c);
        return false;
    }
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHLLLdbLtD8-GFY-wHJTzy08_q-4M_oGM&callback=initMap">
</script>
</body>
</html>
