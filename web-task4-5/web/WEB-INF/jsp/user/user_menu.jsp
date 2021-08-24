<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<ul>
    <li>
        <a href="${context}/user/user.html" ${fn:contains(pageContext.request.servletPath, 'user/user.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.profile"/>
        </a>
    </li>
    <li>
        <a href="${context}/user/services.html" ${fn:contains(pageContext.request.servletPath, 'user/services.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.services"/>
        </a>
    </li>
    <li>
        <a href="${context}/user/tariffs.html" ${fn:contains(pageContext.request.servletPath, 'user/tariffs.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.tariffs"/>
        </a>
    </li>
    <li>
        <a href="${context}/user/bills.html" ${fn:contains(pageContext.request.servletPath, 'user/bills.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.bills"/>
        </a>
    </li>
    <li>
        <a href="${context}/change_password.html" ${fn:contains(pageContext.request.servletPath, 'change_password.jsp') ? 'class="active-item"'  : ''}>
            <fmt:message key="menu.changePassword"/>
        </a>
    </li>
</ul>
