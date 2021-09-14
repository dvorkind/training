<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.callTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="call.html" method="post" class="submit-form reg-form">
            <h2 class="text-center">${titlePage}</h2>
            <p class="msg"><fmt:message key="subscriber.callSentenceOne"/></p>
            <p class="msg"><fmt:message key="subscriber.callTariffCost"/>
                &nbsp;<ctg:money-format balance="${tariff.callCost}" locale="${locale}"/>
                &nbsp;<fmt:message key="subscriber.callTariffMoney"/></p>
            <div class="input-group center-input">
                <input type="number" min="1" max="60" id="callLength" name="callLength"
                       class="small-input" value="5">
                <label for="callLength" class="small-label">
                    <fmt:message key="subscriber.callMinutes"/>
                </label>
            </div>
            <input type="hidden" name="confirmation">
            <input type="submit" value="<fmt:message key="subscriber.callButton" />" class="btn center-btn">
            <a href="subscriber.html" class="form-link"><fmt:message key="subscriber.callCancel"/></a>
        </form>
    </div>
</u:html>