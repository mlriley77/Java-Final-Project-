<%--
  Created by IntelliJ IDEA.
  User: Sarah Guarino
  Date: 5/24/2017
  Time: 12:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Check-In - Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<div id="nav-bar">
    <div class="container">
        <div class="row">
            <div class="col-3">
                <a href="${pageContext.request.contextPath}/">
                    <img src="/resources/images/checkin-color-light.png" height="50px"/>
                </a>
            </div>
            <div class="col-9">
                <div id="menu-list">
                    <a href="${pageContext.request.contextPath}/action=register/user"><span class="menu-item"><b>Register a User Account</b></span></a>
                    <a href="${pageContext.request.contextPath}/action=register/family"><span class="menu-item"><b>Register an Admin Account</b></span></a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="row gutter" id="page-title-row">
        <div class="col-12">
            <h1 id="page-title" class="display-4">Please Login.</h1>
        </div>
    </div>
    <div class="row gutter" id="content-holder">
        <form action="${pageContext.request.contextPath}/action=login/submit" method="post" style="margin:0;padding:0;width:100%;">
            <div class="form-group row justify-content-center">
                <label for="email" class="col-2 col-form-label">Email: </label>
                <div class="col-6">
                    <input class="form-control" title="Email" type="text"
                           id="email" name="email" />
                </div>
            </div>
            <div class="form-group row justify-content-center">
                <label for="password" class="col-2 col-form-label">Password: </label>
                <div class="col-6">
                    <input class="form-control" title="Password" type="password"
                           id="password" name="password" />
                </div>
            </div>
            <div class="col-12" id="error-box">
                ${err}
            </div>
            <input class="btn btn-primary btn-block orange-btn" type="submit" value="Log In" />
        </form>
    </div>
</div>
<%@ include file="footer.jsp"%>
<script>
var errorBox = document.getElementById("error-box");
var javaError = "${err}";

window.onload = function() {
if (javaError !== "") {
errorBox.setAttribute("style",
"border:1px solid #FFA62F;" +
"padding: 5px;" +
"border-radius: 5px;" +
"margin-bottom: 15px;");
console.log("this is still happening!");
}
};
</script>
</body>
</html>