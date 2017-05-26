<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="right">
    <c:url value="/logout" var="logoutUrl"/>
    <form action="${logoutUrl}" method="post" id="logoutForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <c:if test="${pageContext.request.userPrincipal.name != null || sessionUser != null}">
        <h5 class="shadow op blockHeader">
                ${sessionUser.login} <a href="javascript:formSubmit()">(Logout)</a>
        </h5>
    </c:if>
</div>