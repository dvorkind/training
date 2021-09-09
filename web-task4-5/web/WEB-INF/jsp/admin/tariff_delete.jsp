<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.tariffDeleteTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="tariff_delete.html" method="post" class="submit-form reg-form">
            <h2 class="text-center">${titlePage} "${tariffName}"</h2>
            <c:if test="${not empty using}">
                <p class="msg"><fmt:message key="admin.tariffDeleteMessageOne"/><span class="bold"> ${using}</span></p>
                <p class="msg"><fmt:message key="admin.tariffDeleteMessageTwo"/></p>
                <select name="newTariff" class="btn center-btn">
                    <c:forEach var="tariff" items="${tariffs}">
                        <c:if test="${tariff.id != id}">
                            <option value="${tariff.id}">${tariff.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </c:if>
            <p class="msg"><fmt:message key="admin.tariffDeleteMessageThree"/></p>
            <p class="msg"><fmt:message key="admin.tariffDeleteMessageFour"/></p>
            <p class="form-message">
                <c:if test="${not empty tariffDeleteError}">
                    <span><fmt:message key="${tariffDeleteError}"/></span>
                </c:if>
            </p>
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="confirmation">
            <input type="submit" value="<fmt:message key="admin.tariffDelete" />" class="btn">
            <a href="tariff_list.html" class="form-link"><fmt:message key="admin.tariffCancel"/></a>
        </form>
    </div>
</u:html>