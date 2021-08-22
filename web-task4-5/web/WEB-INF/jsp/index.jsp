<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>
<fmt:message var="title" key="header.phone.station"/>

<u:html title="${title}">
    <form class="submit-form">
        <h2><fmt:message key="login.authentication"/></h2>
        <div class="form-inputs">
            <div class="input-group">
                <a href="login.html" class="btn"><fmt:message key="login.sign.in"/></a>
            </div>
        </div>
        <div class="form-inputs">
            <div class="input-group">
                <a href="registration.html" class="btn"><fmt:message key="login.sign.up"/></a>
            </div>
        </div>
    </form>
</u:html>
