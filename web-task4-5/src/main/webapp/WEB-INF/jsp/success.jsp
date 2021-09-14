<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="header.phoneStation"/>
<u:html title="${title}">
    <div class="main">
        <form class="submit-form reg-form">
            <c:choose>
                <c:when test="${not empty success}">
                    <h2><fmt:message key="${success}"/></h2>
                    <c:if test="${not empty successTwo}">
                        <h2>${successTwo}</h2>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:redirect url="login.html"/>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</u:html>