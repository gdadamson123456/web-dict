<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="right">
    <c:url value="/logout" var="logoutUrl"/>
    <form action="${logoutUrl}" method="post" id="logoutForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <script>
        function formSubmit() {
            if (typeof gapi !== 'undefined') {
                googleSingOut();
            } else {
                document.getElementById("logoutForm").submit();
            }
        }
        function googleSingOut() {
            var auth2 = gapi.auth2;
            if (typeof auth2 !== 'undefined') {
                authSingOut();
            } else {
                gapi.load('auth2', function () {
                    gapi.auth2.init().then(function () {
                        authSingOut();
                    });

                });
            }
        }
        function authSingOut() {
            var auth2 = gapi.auth2;
            if (typeof auth2 !== 'undefined') {
                var auth2In = auth2.getAuthInstance();
                auth2In.signOut().then(function () {
                    console.log('User signed out.');
                    document.getElementById("logoutForm").submit();
                });
            }
        }
    </script>
    <c:if test="${pageContext.request.userPrincipal.name != null || sessionUser != null}">
        <h5 class="shadow op blockHeader">
                ${sessionUser.login} <a href="javascript:formSubmit()">(Logout)</a>
        </h5>
    </c:if>
</div>