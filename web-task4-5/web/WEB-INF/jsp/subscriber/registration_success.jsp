<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<u:html title="${title}">
    <div class="main">
        <h2 class="text-center">
            <fmt:message key="registration.congratulationSentenceOne"/>
        </h2>
        <p class="msg"><fmt:message key="registration.congratulationSentenceTwo"/></p>
        <p class="msg"><fmt:message key="registration.congratulationSentenceThree"/></p>
    </div>
</u:html>