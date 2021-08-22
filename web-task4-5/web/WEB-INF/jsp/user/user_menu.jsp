<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<c:set var="context" value="${pageContext.request.contextPath}" />

<ul>
    <li>
        <a href="${context}/user/user.html">
            <fmt:message key="menu.profile"/>
        </a>
    </li>
    <li>
        <a href="${context}/user/services.html">
            <fmt:message key="menu.services"/>
        </a>
    </li>
    <li>
        <a href="${context}/user/tariffs.html">
            <fmt:message key="menu.tariffs"/>
        </a>
    </li>
    <li>
        <a href="${context}/user/bills.html">
            <fmt:message key="menu.bills"/>
        </a>
    </li>
    <li>
        <a href="${context}/change_password.html">
            <fmt:message key="menu.changePassword"/>
        </a>
    </li>
</ul>
