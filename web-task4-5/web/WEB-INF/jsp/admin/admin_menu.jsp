<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<c:set var="context" value="${pageContext.request.contextPath}" />

<ul>
    <li>
        <a href="${context}/admin/admin.html">
            <fmt:message key="menu.profile"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/users.html">
            <fmt:message key="menu.users"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/new_users.html">
            <fmt:message key="menu.new.users"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/debtors.html">
            <fmt:message key="menu.debtors"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/services.html">
            <fmt:message key="menu.services"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/tariffs.html">
            <fmt:message key="menu.tariffs"/>
        </a>
    </li>
    <li>
        <a href="${context}/change_password.html">
            <fmt:message key="menu.changePassword"/>
        </a>
    </li>
</ul>
