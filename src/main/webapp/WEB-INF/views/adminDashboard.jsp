<%--
  Created by IntelliJ IDEA.
  User: Sarah Guarino
  Date: 5/22/2017

  Time: 4:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>CheckIn - Admin Dashboard</title>
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<h1>Check-In</h1>
${navbar}
<h2>Dashboard</h2>
<b>Family Id: </b>${family.familyid}<br />
<form action="${pageContext.request.contextPath}/action=submitlocation" method="post">
    <input type="submit" value="Check In" />
    <input type="hidden" name="lat" id="lat" />
    <input type="hidden" name="long" id="long" />
    <input type="hidden" name="userId" id="userId" value="${user.userid}"/>
</form>
<c:forEach items="${children}" var="child">
    <div style="width:28%;display: inline-block;">
        <b>${child.fname} last checked in at ${child.lasttime}</b>
        <div id="map">
            <iframe id="google_map"
                    width="425" height="350"
                    frameborder="0" scrolling="no"
                    marginheight="0" marginwidth="0" src="https://maps.google.com/?q=${child.lastlat}, ${child.lastlong}&z=15&output=embed"></iframe>
        </div>
    </div>
</c:forEach>
<a href="https://maps.googleapis.com/maps/api/geocode/xml?latlng=40.714224,-73.961452&key=AIzaSyAlb721ruUDaYfkOEwqHnNS-cNyNXx-0Pw">thisorthat</a>
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
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHLLLdbLtD8-GFY-wHJTzy08_q-4M_oGM">
</script>
</body>
</html>