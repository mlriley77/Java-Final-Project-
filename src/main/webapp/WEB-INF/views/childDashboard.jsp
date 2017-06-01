<%--
  Created by IntelliJ IDEA.
  User: MichaelRiley
  Date: 5/24/17
  Time: 7:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>CheckIn - Child Dashboard</title>
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
<div class="container">
    <div class="row justify-content-center">
        <div class="col-12 col-lg-3 gutter">
            <div align="right" id="tlkio" class="side-bar" data-channel="checkinroom${family.familyid}" style="height:100%;text-align:right;" >
                <script async src="http://tlk.io/embed.js" type="text/javascript">
                </script>
            </div>
        </div>
        <div id="dashboard-content" class="col-12 col-lg-9">
            <div class="col-12">
                <div class="row gutter" id="page-title-row">
                    <div class="col-9">
                        <h2 id="page-title">Your Dashboard</h2>
                    </div>
                    <div class="col-3" style="padding: 0;">
                        <form action="${pageContext.request.contextPath}/action=submitlocation" method="post">
                            <button class="btn btn-primary btn-block"
                                    type="submit" id="submitButton">
                                Check In
                            </button>
                            <input type="hidden" name="lat" id="lat" />
                            <input type="hidden" name="long" id="long" />
                            <input type="hidden" name="userId" id="userId" value="${user.userid}"/>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-12" id="map-holder">
                <div class="row gutter">
                    <div class="col-12 gutter">
                        <div class="maps">
                            <div class="map-name"><b>${parent.fname}</b></div>
                            <div class="map-view">
                                <iframe id="google_map" frameborder="0" scrolling="no"
                                        width="100%"
                                        height="300px"
                                        marginheight="0" marginwidth="0"
                                        src="https://maps.google.com/?q=${parent.lastlat}, ${parent.lastlong}&z=15&output=embed">
                                </iframe>
                            </div>
                            <div class="map-time"><em>Last Check In: ${parent.lasttime}</em></div>
                            <div class="map-location">Grandma's House</div>
                        </div>
                    </div>
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
</body>
</html>