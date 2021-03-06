<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.serviceDeleteTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="service_delete.html" method="post" class="submit-form reg-form">
            <h2 class="text-center">${titlePage} "${serviceName}"</h2>
            <p class="msg"><fmt:message key="admin.serviceDeleteMessageOne"/></p>
            <p class="msg"><fmt:message key="admin.serviceDeleteMessageTwo"/></p>
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="confirmation">
            <input type="submit" value="<fmt:message key="admin.serviceDeleteButton" />" class="btn center-btn">
            <a href="service_list.html" class="form-link"><fmt:message key="admin.serviceDeleteCancel"/></a>
        </form>
    </div>
</u:html>