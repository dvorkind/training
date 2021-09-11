<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

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
    <u:header/>
    <main>
        <c:choose>
            <c:when test="${sessionAccount.role == 'SUBSCRIBER'}">
                <u:subscriberMenu/>
            </c:when>
            <c:when test="${sessionAccount.role == 'ADMINISTRATOR'}">
                <u:adminMenu/>
            </c:when>
        </c:choose>
        <jsp:doBody/>
    </main>
</div>
<u:footer/>
</body>
</html>