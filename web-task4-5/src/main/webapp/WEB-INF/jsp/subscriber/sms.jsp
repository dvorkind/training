<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.smsTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="sms.html" method="post" class="submit-form reg-form">
            <h2 class="text-center">${titlePage}</h2>
            <p class="msg"><fmt:message key="subscriber.smsSentenceOne"/></p>
            <p class="msg"><fmt:message key="subscriber.smsTariffCost"/>
                &nbsp;<ctg:money-format balance="${tariff.smsCost}" locale="${locale}"/>
                &nbsp;<fmt:message key="subscriber.smsTariffMoney"/></p>
            <input type="hidden" name="confirmation">
            <input type="submit" value="<fmt:message key="subscriber.smsButton" />" class="btn center-btn">
            <a href="subscriber.html" class="form-link"><fmt:message key="subscriber.smsCancel"/></a>
        </form>
    </div>
</u:html>