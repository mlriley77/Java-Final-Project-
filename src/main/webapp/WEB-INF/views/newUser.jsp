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
${navbar}
<h2>Register A New Child Account</h2>
<form action="${pageContext.request.contextPath}/action=register/user/submit" method="post">
    <div id="error-box">
        ${err}
    </div>
    <h1>Family Unit</h1>
    Family Id: <input title="Family Id Number" type="text"
                      id="famId" name="famId"
                      onclick="" /><br />
    <h1>Child Account</h1>
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
                     onclick="" /><br />
    Confirm Password: <input title="Confirm Password" type="password"
                             id="confPassword" name="confPassword"
                             onclick="" onchange="passwordVerification()"/><br />
    <input type="submit" value="Register" />
</form>


<script>
    function passwordVerification() {
        var password = document.getElementById("password").value;
        var confPassword = document.getElementById("confPassword").value;
        var submitButton = document.getElementById("formsubmit");
        if (password !== confPassword) {
            document.getElementById("password").style.borderColor = "#e34234";
            document.getElementById("confPassword").style.borderColor = "#E34234";
            alert("Passwords Do not match");
        } else {
            submitButton.disabled = false;
        }
    }
    function emailValidation(element) {
        var emailBox = document.getElementById("email");
        var email = element.value;
        var url = "/getemail2";
        var errorBox = document.getElementById("error-box");
        $.get(url, {email:email}, function(data){
            if (data === "Ok") {
                errorBox.value = "";
            } else {
                var errorMessage = document.createTextNode("This is not a valid email");
                errorBox.appendChild(errorMessage);
                emailBox.value = "";
            }
        });
    }
</script>
<script src="//code.jquery.com/jquery-2.2.1.js"></script>
</body>
</html>
