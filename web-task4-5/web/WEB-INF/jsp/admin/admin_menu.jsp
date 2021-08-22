<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<ul>
    <li>
        <a href="admin.html">
            <fmt:message key="menu.profile"/>
        </a>
    </li>
    <li>
        <a href="users.html">
            <fmt:message key="menu.users"/>
        </a>
    </li>
    <li>
        <a href="new_users.html">
            <fmt:message key="menu.new.users"/>
        </a>
    </li>
    <li>
        <a href="debtors.html">
            <fmt:message key="menu.debtors"/>
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
</ul>
