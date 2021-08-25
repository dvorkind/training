<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<c:set var="context" value="${pageContext.request.contextPath}" />

<ul>
    <li>
        <a href="${context}/admin/admin.html" ${fn:contains(pageContext.request.servletPath, 'admin/admin.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.profile"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/users_all.html" ${fn:contains(pageContext.request.servletPath, 'admin/users_all.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.users"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/users_new.html" ${fn:contains(pageContext.request.servletPath, 'admin/users_new.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.newUsers"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/debtors.html" ${fn:contains(pageContext.request.servletPath, 'admin/debtors.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.debtors"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/services.html" ${fn:contains(pageContext.request.servletPath, 'admin/services.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.services"/>
        </a>
    </li>
    <li>
        <a href="${context}/admin/tariffs.html" ${fn:contains(pageContext.request.servletPath, 'admin/tariffs.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.tariffs"/>
        </a>
    </li>
    <li>
        <a href="${context}/change_password.html" ${fn:contains(pageContext.request.servletPath, 'change_password.jsp') ? 'class="active-item"' : ''}>
            <fmt:message key="menu.changePassword"/>
        </a>
    </li>
</ul>
