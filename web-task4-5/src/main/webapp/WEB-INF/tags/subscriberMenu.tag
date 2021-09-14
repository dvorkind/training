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
            <a href="${context}/subscriber/subscriber.html" ${fn:contains(pageContext.request.servletPath, 'subscriber/subscriber.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.profile"/>
            </a>
        </li>
        <li>
            <a href="${context}/subscriber/refill_balance.html" ${fn:contains(pageContext.request.servletPath, 'subscriber/refill_balance.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.balance"/>
            </a>
        </li>
        <li>
            <a href="${context}/subscriber/services.html" ${fn:contains(pageContext.request.servletPath, 'subscriber/services.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.services"/>
            </a>
        </li>
        <li>
            <a href="${context}/subscriber/tariff.html" ${fn:contains(pageContext.request.servletPath, 'subscriber/tariff.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.changeTariff"/>
            </a>
        </li>
        <li>
            <a href="${context}/subscriber/bills.html" ${fn:contains(pageContext.request.servletPath, 'subscriber/bills.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.bills"/>
            </a>
        </li>
        <li>
            <a href="${context}/subscriber/statement.html" ${fn:contains(pageContext.request.servletPath, 'subscriber/statement.jsp') ? 'class="active-item"' : ''}>
                <fmt:message key="menu.statement"/>
            </a>
        </li>
        <li>
            <a href="${context}/change_password.html" ${fn:contains(pageContext.request.servletPath, 'change_password.jsp') ? 'class="active-item"'  : ''}>
                <fmt:message key="menu.changePassword"/>
            </a>
        </li>
    </ul>
</nav>
