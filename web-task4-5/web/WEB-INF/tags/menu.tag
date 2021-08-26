<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<c:choose>
    <c:when test="${sessionAccount.role == 'SUBSCRIBER'}">
        <nav>
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
        </nav>
    </c:when>
    <c:when test="${sessionAccount.role == 'ADMINISTRATOR'}">
        <nav>
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
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>
