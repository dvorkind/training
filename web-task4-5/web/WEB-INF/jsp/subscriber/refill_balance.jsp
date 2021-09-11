<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.refillBalanceTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="refill_balance.html" method="post" class="submit-form reg-form">
            <h2 class="text-center">${titlePage}</h2>
            <p class="msg"><fmt:message key="subscriber.refillBalanceMessage"/></p>
            <div class="input-group">
                <label>
                    <fmt:message key="subscriber.refillBalanceRefillSum"/>
                </label>
                <input type="number" min="0" id="balanceRefillRoubles" name="balanceRefillRoubles"
                       class="small-input"
                       value="5">
                <label for="balanceRefillRoubles" class="small-label">
                    <fmt:message key="subscriber.refillBalanceMoneyRoubles"/>
                </label>
                <input type="number" min="0" max="99" id="balanceRefillKopecks" name="balanceRefillKopecks"
                       class="small-input"
                       value="0">
                <label for="balanceRefillKopecks" class="small-label">
                    <fmt:message key="subscriber.refillBalanceMoneyKopecks"/>
                </label>
            </div>
            <input type="hidden" name="confirmation">
            <input type="submit" value="<fmt:message key="subscriber.refillBalanceButton" />" class="btn">
            <a href="subscriber.html" class="form-link"><fmt:message key="subscriber.refillBalanceCancel"/></a>
        </form>
    </div>
</u:html>