<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="locale" value="${not empty locale ? locale : 'ru'}" scope="session"/>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${title}</title>
    <c:url var="urlCss" value="/style.css"/>
    <link href="${urlCss}" rel="stylesheet" type="text/css">
</head>
<body>
<div class="wrapper">
    <header>
        <a class="header-link" href="${context}/index.html">
            <fmt:message key="header.phoneStation"/>
        </a>
        <div class="flex">
            <form action="${context}/language.html" method="POST" class="select-form">
                <input type="hidden" name="pagePath" value="${pageContext.request.requestURI}">
                <input type="hidden" name="queryString" value="${pageContext.request.queryString}">
                <select name="locale" onchange="submit()" class="btn">
                    <option value="en" ${locale == 'en' ? 'selected' : ''}><fmt:message
                            key="header.english"/></option>
                    <option value="ru" ${locale == 'ru' ? 'selected' : ''}><fmt:message
                            key="header.russian"/></option>
                </select>
            </form>
            <c:choose>
                <c:when test="${not empty sessionAccount}">
                    <a href="${context}/logout.html" class="btn">
                        <fmt:message key="header.logout"/> (${sessionAccount.login})
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${context}/login.html" class="btn">
                        <fmt:message key="header.login"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
    <main>
        <c:choose>
            <c:when test="${sessionAccount.role == 'SUBSCRIBER'}">
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
            </c:when>
        </c:choose>
        <jsp:doBody/>
    </main>
</div>
<footer>
    <span><fmt:message key="footer.copyright"/></span>
    <c:if test="${sessionAccount.role == 'SUBSCRIBER'}">
        <span>
            <fmt:message key="footer.balance"/>&nbsp
            <ctg:money-format balance="${sessionSubscriber.balance}" locale="${locale}"/>
            &nbsp
            <fmt:message key="subscriber.money"/>
        </span>
    </c:if>
</footer>
</body>
</html>