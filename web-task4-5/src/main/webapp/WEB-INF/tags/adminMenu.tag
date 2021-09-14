<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<nav>
    <ul>
        <li>
            <a href="${context}/admin/admin.html" ${fn:contains(pageContext.request.servletPath, 'admin/admin.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.profile"/>
            </a>
        </li>
        <li>
            <a href="${context}/admin/subscribers_all.html" ${fn:contains(pageContext.request.servletPath, 'admin/subscribers_all.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.subscribers"/>
            </a>
        </li>
        <li>
            <a href="${context}/admin/subscribers_new.html" ${fn:contains(pageContext.request.servletPath, 'admin/subscribers_new.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.newSubscribers"/>
            </a>
        </li>
        <li>
            <a href="${context}/admin/debtors.html" ${fn:contains(pageContext.request.servletPath, 'admin/debtors.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.debtors"/>
            </a>
        </li>
        <li>
            <a href="${context}/admin/service_list.html" ${fn:contains(pageContext.request.servletPath, 'admin/service_list.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.services"/>
            </a>
        </li>
        <li>
            <a href="${context}/admin/tariff_list.html" ${fn:contains(pageContext.request.servletPath, 'admin/tariff_list.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.tariffs"/>
            </a>
        </li>
        <li>
            <a href="${context}/change_password.html" ${fn:contains(pageContext.request.servletPath, 'change_password.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.changePassword"/>
            </a>
        </li>
    </ul>
</nav>
