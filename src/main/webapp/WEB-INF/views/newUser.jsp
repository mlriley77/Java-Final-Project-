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
    <title>CheckIn - Register New User</title>
    <link rel="stylesheet" href="/resources/styles.css" />
</head>
<body>
<h1>Check-In</h1>
${navbar}
<h2>Create A New Account</h2>
<form action="${pageContext.request.contextPath}/action=register/user/submit" method="post">
    ${err}
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
                      value="" onchange="emailValidation(this)"/> <div id="emailerror"> </div><br>
        Password: <input title="Password" type="password"
                         id="password" name="password" required
                         onclick=""
                         value="" /><br />
        Confirm Password: <input title="Confirm Password" type="password"
                                 id="confPassword" name="confPassword"
                                 onclick=""
                                 value="" onchange="email2()"/><br />
        <input id="formsubmit" type="submit"  onsubmit="formValidation()" value="Register" disabled/>

</form>


<script>
    function formValidation() {

        var famName = document.getElementById("famName").value;
        var email = document.getElementById("email").value;


        if (emailValidation(email) === "Bad"){
            alert ("Enter a valid email address")
        }

    }
    function email2() {
        var password = document.getElementById("password").value;
        var confPassword = document.getElementById("confPassword").value;
        var submitButton = document.getElementById("formsubmit");
        if (password !== confPassword) {
            document.getElementById("password").style.borderColor = "#e34234";
            document.getElementById("confPassword").style.borderColor = "#E34234";
            alert("Passwords Do not match");
        }else {
            submitButton.disabled = false;
        }


    }
    function emailValidation(element) {
        var email = element.value;
        //console.log(element.value);
        //var url = "http://localhost:8080/getemail2";
        var url = "/getemail2";
        $.get(url, {email:email} ,function(data){
            console.log(data);
            //process response
            $("#emailerror").empty().append(data);
        });
    }
</script>
<script src="//code.jquery.com/jquery-2.2.1.js"></script>
</body>
</html>
