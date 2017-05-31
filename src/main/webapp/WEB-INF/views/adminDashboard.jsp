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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
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
<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
</body>
</html>