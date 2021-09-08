<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<u:html title="${title}">
    <div class="main">
        <form class="submit-form reg-form">
            <c:choose>
                <c:when test="${not empty success}">
                    <h2><fmt:message key="${success}"/></h2>
                </c:when>
                <c:otherwise>
                    <c:redirect url="subscriber.html"/>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</u:html>