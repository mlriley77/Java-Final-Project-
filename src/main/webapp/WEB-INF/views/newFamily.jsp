<%--
  Created by IntelliJ IDEA.
  User: Sarah Guarino
  Date: 5/22/2017
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>CheckIn - Register A New Parent Account</title>
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
<h1>Check-In</h1>
<div id="error-box">
    ${err}
</div>
<h2>Register A New Parent Account</h2>
<form id="newFamily" action= "${pageContext.request.contextPath}/action=register/family/submit" method="post">
    <h1>Family Unit</h1>
    Family Name: <input title="Family Name" type="text"
                        id="famName" name="famName" required
                        onclick=""
                        value="" /><br />
    <h1>Leader Account</h1>
    First Name: <input title="First Name" type="text"
                       id="fName" name="fName" required
                       onclick=""
                       value="" /><br />
    Last Name: <input title="Last Name" type="text"
                      id="lName" name="lName" required
                      onclick=""
                      value="" /><br />
    Email: <input title="Email" type="text"
                  id="email" name="email" required
                  onclick=""
                  value="" onchange="emailValidation(this)"/><br />
    Password: <input title="Password" type="password"
                     id="password" name="password"
                     onchange="passwordVerification()" required/><br />
    Confirm Password: <input title="Confirm Password" type="password"
                             id="confPassword" name="confPassword"
                             onchange="passwordVerification()" required/><br />
    <input type="submit" value="Register" id="submitButton" disabled/>
</form>
<script>
    var isPasswordGood = false,
        isEmailGood = false;
    var errorBox = document.getElementById("error-box");

    function passwordVerification() {
        var password = document.getElementById("password");
        var confPassword = document.getElementById("confPassword");

        if (password.value !== confPassword.value) {
            password.style.borderColor = "#e34234";
            confPassword.style.borderColor = "#E34234";
            confPassword.value = '';

            var errorMessage = document.createTextNode("Passwords do not match");
            errorBox.appendChild(errorMessage);

        } else {
            isPasswordGood = true;
            confPassword.style.borderColor = "inherit";
            password.style.borderColor = "inherit";
            errorBox.innerHTML = '';
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
                emailBox.style.borderColor = "inherit";
                isEmailGood = true;

            } else {
                var errorMessage = document.createTextNode("This is not a valid email");
                errorBox.appendChild(errorMessage);
                emailBox.style.borderColor = "#E34234";
                emailBox.innerHTML = '';
            }
        });
        clearButton();
    }

    function clearButton() {
        var submitButton = document.getElementById("submitButton");
        submitButton.disabled = true;

        if (isPasswordGood === true && isEmailGood === true) {
            submitButton.disabled = false;
        }

        console.log("Password: " + isPasswordGood);
        console.log("Email: " + isEmailGood);
    }
</script>
<script src="//code.jquery.com/jquery-2.2.1.js"></script>
</body>
</html>
