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
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<h1>Check-In</h1>
<h2>Register A New Child Account</h2>
<form action="${pageContext.request.contextPath}/action=register/user/submit" method="post">
    <div id="error-box">
        ${err}
    </div>
    <h1>Family Unit</h1>
    Family Id: <input title="Family Id Number" type="text"
                      id="famId" name="famId" required/><br />
    <h1>Child Account</h1>
    First Name: <input title="First Name" type="text"
                       id="fName" name="fName" required/><br />
    Last Name: <input title="Last Name" type="text"
                      id="lName" name="lName" required/><br />
    Email: <input title="Email" type="text"
                  id="email" name="email" required
                  onchange="emailValidation(this)"/><br />
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
