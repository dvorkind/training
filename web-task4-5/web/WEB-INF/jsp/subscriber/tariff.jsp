<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.tariff"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="tariff.html" method="post" class="submit-form reg-form">
            <h2 class="text-center">
                <fmt:message key="subscriber.tariff"/>
            </h2>
            <p class="msg"><fmt:message key="subscriber.changeTariffMessageOne"/></p>
            <select name="newTariff" class="btn center-btn">
                <c:forEach var="tariff" items="${tariffs}">
                    <c:if test="${tariff.id != id}">
                        <option value="${tariff.id}">${tariff.name}</option>
                    </c:if>
                </c:forEach>
            </select>
            <p class="msg"><fmt:message key="subscriber.changeTariffMessageTwo"/></p>
            <p class="msg"><fmt:message key="subscriber.changeTariffMessageThree"/></p>
            <p class="form-message">
                <c:if test="${not empty tariffChangeError}">
                    <c:if test="${not empty tariffChangeDate}">
                        <span><fmt:message key="${tariffChangeError}"/>&nbsp;${tariffChangeDate}</span>
                    </c:if>
                    <c:if test="${empty tariffChangeDate}">
                        <span><fmt:message key="${tariffChangeError}"/></span>
                    </c:if>

                </c:if>
            </p>
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="confirmation">
            <input type="submit" value="<fmt:message key="subscriber.changeTariff" />" class="btn">
            <a href="subscriber.html" class="form-link"><fmt:message key="subscriber.changeCancel"/></a>
        </form>
    </div>
</u:html>