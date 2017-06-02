<%--
  Created by IntelliJ IDEA.
  User: Sarah Guarino
  Date: 5/22/2017
  Time: 3:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CheckIn - Register A New Child Account</title>
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
                    <a href="${pageContext.request.contextPath}/action=login"><span class="menu-item"><b>Login</b></span></a>
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
            <h1 id="page-title" class="display-4">Register A New Family Member</h1>
        </div>
    </div>

    <div class="row gutter" id="content-holder">
        <form id="newFamily" action= "${pageContext.request.contextPath}/action=register/user/submit" method="post" style="margin:0;padding:0;width:100%;">
            <h4>Family Number  </h4>
            <div class="form-group row justify-content-center">
                <label for="famId" class="col-2 col-form-label">Family Id #</label>
                <div class="col-6">
                    <input class="form-control" title="Found in Parent Account"
                           type="text" id="famId" name="famId"/>
                    <small class="form-text text-muted"><em>(Found within the parent's account)</em></small>
                </div>
            </div>

            <h4>Account Details</h4>
            <div class="form-group row justify-content-center">
                <label for="famId" class="col-2 col-form-label">First Name</label>
                <div class="col-6">
                    <input class="form-control" title="First Name"
                           type="text" id="fName" name="fName" />
                </div>
            </div>
            <div class="form-group row justify-content-center">
                <label for="famId" class="col-2 col-form-label">Last Name</label>
                <div class="col-6">
                    <input class="form-control" title="Last Name" type="text"
                           id="lName" name="lName" />
                </div>
            </div>
            <div class="form-group row justify-content-center">
                <label for="famId" class="col-2 col-form-label">Email</label>
                <div class="col-6">
                    <input class="form-control" title="Email" type="text"
                           id="email" name="email"
                           onchange="emailValidation(this)"/>
                </div>
            </div>
            <div class="form-group row justify-content-center">
                <label for="famId" class="col-2 col-form-label">Password</label>
                <div class="col-6">
                    <input class="form-control" title="Password" type="password"
                           id="password" name="password"
                           onchange="passwordVerification()" />
                </div>
            </div>
            <div class="form-group row justify-content-center">
                <label for="famId" class="col-2 col-form-label">Confirm Password</label>
                <div class="col-6">
                    <input class="form-control" title="Confirm Password" type="password"
                           id="confPassword" name="confPassword"
                           onchange="passwordVerification()" />
                </div>
            </div>
            <div class="col-12" id="error-box">
                ${err}
            </div>
            <button class="btn btn-primary btn-block orange-btn"
                    type="submit" id="submitButton" disabled>
                Register
            </button>
        </form>
    </div>
</div>
<%@ include file="footer.jsp"%>
<script>
    var isPasswordGood = false,
        isEmailGood = false;
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
    function passwordVerification() {
        var password = document.getElementById("password");
        var confPassword = document.getElementById("confPassword");

        if (password.value !== confPassword.value) {
            password.style.borderColor = "#FFA62F";
            confPassword.style.borderColor = "#FFA62F";
            confPassword.value = '';

            var errorMessage = document.createTextNode("Passwords do not match. ");
            errorBox.appendChild(errorMessage);
            errorBox.setAttribute("style",
                "border:1px solid #FFA62F;" +
                "padding: 5px;" +
                "border-radius: 5px;" +
                "margin-bottom: 15px;");

        } else {
            isPasswordGood = true;
            confPassword.style.borderColor = "rgba(0,0,0,.15)";
            password.style.borderColor = "rgba(0,0,0,.15)";
            errorBox.innerHTML = '';
            errorBox.removeAttribute("style");
        }
        clearButton();
    }
    function emailValidation(element) {
        var emailBox = document.getElementById("email");
        var email = element.value;
        var url = "/getemail2";
        var errorBox = document.getElementById("error-box");
        $.get(url, {email:email}, function(data){
            if (data === "Ok") {
                errorBox.innerHTML = '';
                emailBox.style.borderColor = "rgba(0,0,0,.15)";
                isEmailGood = true;
                errorBox.removeAttribute("style");

            } else {
                var errorMessage = document.createTextNode("This is not a valid email. ");
                errorBox.appendChild(errorMessage);
                emailBox.style.borderColor = "#FFA62F";
                emailBox.innerHTML = '';
                errorBox.setAttribute("style",
                    "border:1px solid #FFA62F;" +
                    "padding: 5px;" +
                    "border-radius: 5px;" +
                    "margin-bottom: 15px;");
            }
        });
        clearButton();
    }
    function clearButton() {
        var submitButton = document.getElementById("submitButton");
        submitButton.disabled = true;

        if (isPasswordGood && isEmailGood) {
            submitButton.disabled = false;
        }
    }
</script>
<script
        src="https://code.jquery.com/jquery-2.2.4.js"
        integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
        crossorigin="anonymous"></script>
</body>
</html>