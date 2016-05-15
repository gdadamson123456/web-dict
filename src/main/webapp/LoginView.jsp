<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pure/0.6.0/pure-min.css">
    <link href="<%= response.encodeURL(request.getContextPath() + "../css/main.css") %>" rel="stylesheet"
          type="text/css">
    <title>Login Page</title>
</head>
<body onload='document.loginForm.username.focus();'>
<div id="login-box">
    <div align="center" class="login shadow op">
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>
        <br> <br>
        <form name='loginForm' action="j_spring_security_check" method='POST' class="pure-form">
            <table>
                <tr>
                    <td>User:</td>
                    <td><input type='text' name='username' class="form-control" value=''></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type='password' name='password' class="form-control"/></td>
                </tr>
                <tr>
                    <td><br></td>
                </tr>
                <tr>
                    <td colspan="3" align="right"><input name="submit"
                                                         class="pure-button pure-button-primary" type="submit"
                                                         value="Sign in"/></td>
                </tr>
                <tr>
                    <td><br></td>
                </tr>
                <tr>
                    <td colspan="3" align="right"><a href="reg">or register</a></td>
                </tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>
</body>
</html>