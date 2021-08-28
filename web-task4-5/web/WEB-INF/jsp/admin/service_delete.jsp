<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.serviceDeleteTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="service_delete.html" method="post" class="submit-form reg-form">
            <h2 class="text-center">
                <fmt:message key="admin.serviceDeleteTitle"/> "${serviceName}"
            </h2>
            <p class="msg"><fmt:message key="admin.serviceDeleteMessageOne"/></p>
            <p class="msg"><fmt:message key="admin.serviceDeleteMessageTwo"/></p>
            <p class="form-message">
                <c:if test="${not empty serviceDeleteError}">
                    <span><fmt:message key="${serviceDeleteError}"/></span>
                </c:if>
            </p>
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="confirmation">
            <input type="submit" value="<fmt:message key="admin.serviceDelete" />" class="btn">
            <a href="service_list.html" class="form-link"><fmt:message key="admin.serviceCancel"/></a>
        </form>
    </div>
</u:html>