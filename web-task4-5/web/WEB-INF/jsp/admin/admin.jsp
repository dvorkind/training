<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>


<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.main"/>
<u:html title="${title} : ${titlePage}">
    <nav>
        <jsp:include page="/WEB-INF/jsp/admin/admin_menu.jsp"/>
    </nav>
    <div class="main">
        <h2 class="text-center">
            <fmt:message key="admin.users"/>
        </h2>
    </div>
</u:html>