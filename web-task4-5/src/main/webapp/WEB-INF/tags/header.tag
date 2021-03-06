<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<header>
    <c:choose>
        <c:when test="${sessionAccount.role == 'SUBSCRIBER'}">
            <a class="header-link" href="${context}/subscriber/subscriber.html">
                <fmt:message key="header.phoneStation"/>
            </a>
        </c:when>
        <c:when test="${sessionAccount.role == 'ADMINISTRATOR'}">
            <a class="header-link" href="${context}/admin/admin.html">
                <fmt:message key="header.phoneStation"/>
            </a>
        </c:when>
        <c:otherwise>
            <a class="header-link" href="${context}/index.html">
                <fmt:message key="header.phoneStation"/>
            </a>
        </c:otherwise>
    </c:choose>
    <div class="flex">
        <form action="${context}/language.html" method="POST" class="select-form">
            <input type="hidden" name="pagePath" value="${pageContext.request.requestURI}">
            <input type="hidden" name="queryString" value="${pageContext.request.queryString}">
            <select name="locale" onchange="submit()" class="btn">
                <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>
                    <fmt:message key="header.english"/>
                </option>
                <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>
                    <fmt:message key="header.russian"/>
                </option>
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