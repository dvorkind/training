<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ tag trimDirectiveWhitespaces="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru'}" scope="session"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages" />

<c:set var="context" value="${pageContext.request.contextPath}" />

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
        <c:choose>
            <c:when test="${sessionAccount.role == 'ADMINISTRATOR'}">
                <a class="header-link" href="${context}/admin/admin.html">
                    <fmt:message key="header.phone.station" />
                </a>
            </c:when>
            <c:when test="${sessionAccount.role == 'SUBSCRIBER'}">
                <a class="header-link" href="${context}/user/user.html">
                    <fmt:message key="header.phone.station" />
                </a>
            </c:when>
            <c:otherwise>
                <a class="header-link" href="${context}/index.html">
                    <fmt:message key="header.phone.station" />
                </a>
            </c:otherwise>
        </c:choose>
        <div class="flex">
            <form action="${context}/language.html" method="POST" class="select-form">
                <input type="hidden" name="pagePath" value="${pageContext.request.requestURI}">
                <input type="hidden" name="queryString" value="${pageContext.request.queryString}">
                <select name="locale" onchange="submit()" class="btn">
                    <option value="en" ${sessionScope.locale == 'en' ? 'selected' : ''}>English</option>
                    <option value="ru" ${sessionScope.locale == 'ru' ? 'selected' : ''}>Русский</option>
                </select>
            </form>

            <c:choose>
                <c:when test="${not empty sessionAccount}">
                    <a href="${context}/logout.html" class="btn">
                        <fmt:message key="header.logout"/>
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
        <jsp:doBody/>
    </main>
</div>
<footer>
    <span><fmt:message key="footer.copyright"/></span>
</footer>
</body>
</html>