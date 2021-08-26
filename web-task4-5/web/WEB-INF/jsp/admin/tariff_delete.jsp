<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.tariffDeleteTitle"/>
<u:html title="${title} : ${titlePage}">
    <u:menu/>
    <div class="main">
        <form action="tariff_delete.html" method="post" class="submit-form">
            <h2 class="text-center">
                <fmt:message key="admin.tariffDeleteTitle"/> "${tariffName}"
            </h2>
            <c:if test="${not empty using}">
                <p class="msg">Выбранный тариф используется пользователями: ${using}</p>
                <p class="msg">Пожалуйста, выберите тариф, который им будет назначен взамен удаляемого:</p>
                <select name="newTariff" class="btn">
                    <c:forEach var="tariff" items="${tariffs}">
                        <c:if test="${tariff.id != id}">
                            <option value="${tariff.id}">${tariff.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </c:if>
            <p class="msg">Вы действительно хотите удалить этот тариф?</p>
            <p class="msg">Тариф будет удален немедленно.</p>
            <p class="msg">Вы не сможете отменить это действие.</p>
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