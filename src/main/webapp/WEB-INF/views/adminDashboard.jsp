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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<div id="nav-bar" class="gutter">
    <span id="menu-check">Check</span><span id="menu-in">in</span>
    <div id="menu-list">
        <span class="menu-item"><b>Family Id:</b> <i>${family.familyid}</i></span>
        <a href="${pageContext.request.contextPath}/action=logout"><span class="menu-item"><b>Logout</b></span></a>
    </div>
</div>
<div class="container">
    <div class="row justify-content-center">
        <div id="side-bar" class="col-3">
            <div align="right" id="tlkio" data-channel="checkinfamily" style="height:100%;text-align:right;" >
                <script async src="http://tlk.io/embed.js" type="text/javascript">
                </script>
            </div>
        </div>
        <div id="dashboard-content" class="col-9">
            <div class="col-12">
                <div class="row gutter" id="page-title-row">
                    <h2 id="page-title">Your Dashboard</h2>
                    <form action="${pageContext.request.contextPath}/action=submitlocation" method="post">
                        <input type="submit" value="Check In" />
                        <input type="hidden" name="lat" id="lat" />
                        <input type="hidden" name="long" id="long" />
                        <input type="hidden" name="userId" id="userId" value="${user.userid}"/>
                    </form>
                </div>
            </div>
            <div class="col-12" id="map-holder">
                <div class="row gutter">
                    <c:forEach items="${children}" var="child">
                        <div class="col-6 gutter">
                            <div class="maps">
                                <div class="map-name"><b>${child.fname}</b></div>
                                <div class="map-view">
                                    <iframe id="google_map" frameborder="0" scrolling="no"
                                            width="100%"
                                            marginheight="0" marginwidth="0"
                                            src="https://maps.google.com/?q=${child.lastlat}, ${child.lastlong}&z=15&output=embed">
                                    </iframe>
                                </div>
                                <div class="map-time"><em>Last Check In: ${child.lasttime}</em></div>
                                <div class="map-location">Grandma's House</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    window.onload = function() {
        var c = function(pos) {
            document.getElementById('lat').value = pos.coords.latitude;
            document.getElementById('long').value = pos.coords.longitude;
        };
        navigator.geolocation.getCurrentPosition(c);
        return false;
    }
</script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHLLLdbLtD8-GFY-wHJTzy08_q-4M_oGM"></script>
<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>

<div id="tlkio" data-channel="checkinfamily" data-theme="theme--day" style="width:400px;height:600px;float:right;" >
    <script async src="http://tlk.io/embed.js" type="text/javascript">
    </script>
</div>
</body>
</html>