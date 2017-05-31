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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<div id="nav-bar" class="gutter">
    <span id="menu-check">Check</span><span id="menu-in">in</span>
    <div id="menu-list">
        <span class="menu-item"><b>Family Id:</b> <i>${family.familyid}</i></span>
        <a href="${pageContext.request.contextPath}/"><span class="menu-item"><b>Home</b></span></a>
        <a href="${pageContext.request.contextPath}/dashboard"><span class="menu-item"><b>Dashboard</b></span></a>
        <a href="${pageContext.request.contextPath}/action=login"><span class="menu-item"><b>Login</b></span></a>
        <a href="${pageContext.request.contextPath}/action=register/user"><span class="menu-item"><b>Register a User Account</b></span></a>
        <a href="${pageContext.request.contextPath}/action=register/family"><span class="menu-item"><b>Register an Admin Account</b></span></a>
        <a href="${pageContext.request.contextPath}/action=logout"><span class="menu-item"><b>Logout</b></span></a>
    </div>
</div>
<h2>Dashboard</h2>
<b>Family Id: </b>${family.familyid}<br />
<form action="${pageContext.request.contextPath}/action=submitlocation" method="post">
    <input type="submit" value="Check In" />
    <input type="hidden" name="lat" id="lat" />
    <input type="hidden" name="long" id="long" />
    <input type="hidden" name="userId" id="userId" value="${user.userid}"/>
</form>
<div align="right" id="tlkio" data-channel="checkinroom${family.familyid}" style="height:100%;text-align:right;" >
    <script async src="http://tlk.io/embed.js" type="text/javascript">
    </script>
</div>
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

<div id="tlkio" data-channel="checkinfamily" data-theme="theme--day" style="width:400px;height:600px;float:right;" >
    <script async src="http://tlk.io/embed.js" type="text/javascript">
    </script>
</div>

</body>
</html>
