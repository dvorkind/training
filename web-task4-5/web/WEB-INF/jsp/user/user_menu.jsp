<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<ul>
    <li>
        <a href="user.html">
            <fmt:message key="menu.profile"/>
        </a>
    </li>
    <li>
        <a href="services.html">
            <fmt:message key="menu.services"/>
        </a>
    </li>
    <li>
        <a href="tariffs.html">
            <fmt:message key="menu.tariffs"/>
        </a>
    </li>
    <li>
        <a href="bills.html">
            <fmt:message key="menu.bills"/>
        </a>
    </li>
</ul>
