<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error page</title>
</head>
<body>
<c:url value="/" var="toHome"/>
<div align="center">
    <h1 style="color: red;">Error</h1>
</div>
<br>
<div align="center" style="height: 10%">${errorMessage}</div>
<br>
<br>
<div align="center">
    <a href="${toHome}/">go to main page</a>
</div>
</body>
</html>