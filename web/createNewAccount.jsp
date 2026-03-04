<%-- 
    Document   : createNewAccount
    Created on : 02-Mar-2026, 07:49:49
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create</title>
    </head>
    <body>
        <div>Create new account</div>
        <form action="MainController" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERRORS}"/>
            Username* <input type="text" name="txtUsername" 
                             value="${param.txtUsername}" />6 - 20 characters<br/>
            <c:if test="${not empty errors.usernameLengthErr}">
                ${errors.usernameLengthErr}<br/>
            </c:if>
            Password* <input type="password" name="txtPassword" value="" />6 - 30 characters<br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                ${errors.passwordLengthErr}<br/>
            </c:if>
            Confirm* <input type="password" name="txtConfirm" value="" /><br/>
            <c:if test="${not empty errors.confirmNotMatched}">
                ${errors.confirmNotMatched}<br/>
            </c:if>
            Full name* <input type="text" name="txtFullName"
                              value="${param.txtFullName}" />2 - 50 characters<br/>
            <c:if test="${not empty errors.fullNameLengthErr}">
                ${errors.fullNameLengthErr}<br/>
            </c:if>
            <input type="submit" value="Create New Account" name="action" /><br/>
            <input type="reset" value="Reset" name="action" /><br/>
    </body>
</html>
