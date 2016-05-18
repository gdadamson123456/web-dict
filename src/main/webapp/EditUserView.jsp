<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pure/0.6.0/pure-min.css">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <script src='https://www.google.com/recaptcha/api.js'></script>
    <title>Registration</title>
</head>
<body>
<div align="center">
    <h1>Registration</h1>
    <form:form method="POST" action="${mode}" commandName="edit" modelAttribute="editForm"
               cssClass="pure-form block shadow op">
        <table>
            <form:hidden path="id"/>
            <tr>
                <td>Login</td>
                <td><form:input path="login"/></td>
                <td style="color: red"><form:errors path="login"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><form:password path="password" showPassword="true"/></td>
                <td style="color: red"><form:errors path="password"/></td>
            </tr>
            <tr>
                <td>Password check</td>
                <td><form:password path="passwordRepeat" showPassword="true"/></td>
                <td style="color: red"><form:errors path="passwordRepeat"/></td>
                <td style="color: red"><form:errors path="isSamePasswords"/></td>
            </tr>
            <tr>
                <td>E-mail</td>
                <td><form:input path="email"/></td>
                <td style="color: red"><form:errors path="email"/></td>
            </tr>
            <tr>
                <td><br> <form:hidden path="role"/></td>
            </tr>
            <tr>
                <td colspan="3">
                    <div class="g-recaptcha"
                         data-sitekey="6LeX6R8TAAAAAKH33xReg6EPgu1bWVS7dP9rpqem"></div>
                </td>
            </tr>
            <tr>
                <td colspan="3" align="left">
                    <div>
                        <input type="submit" value="Ok" name="button"
                               class="pure-button pure-button-primary"/> <a href="/"> <input
                            type="button" value="Cancel" class="pure-button"/>
                    </a>
                    </div>
                </td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>