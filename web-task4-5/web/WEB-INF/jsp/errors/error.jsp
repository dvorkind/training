<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="error.unexpected"/>
<u:html title="${title}">
    <u:menu/>
    <div class="main">
        <h2 class="text-center">
            <fmt:message key="error.unexpectedMessageOne"/>
        </h2>
        <p class="msg"><fmt:message key="error.unexpectedMessageTwo"/></p>
        <p class="msg"><fmt:message key="error.unexpectedMessageThree"/></p>
    </div>
</u:html>
