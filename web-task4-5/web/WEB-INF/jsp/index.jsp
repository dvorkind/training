<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>
<fmt:message var="title" key="header.phone.station"/>

<u:html title="${title}">
    <form method="post" class="submit-form">
        <h2>
            <fmt:message key="index.sentence1"/><br>
            <fmt:message key="index.sentence2"/><br>
        </h2>
    </form>
</u:html>