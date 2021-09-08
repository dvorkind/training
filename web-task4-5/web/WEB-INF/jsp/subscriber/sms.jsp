<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/money.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.smsTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="sms.html" method="post" class="submit-form reg-form">
            <h2 class="text-center">${titlePage}</h2>
            <p class="msg"><fmt:message key="subscriber.smsSentenceOne"/></p>
            <p class="msg"><fmt:message key="subscriber.tariffSmsCost"/>&nbsp;<ctg:money-format
                    balance="${tariff.smsCost}" locale="${sessionScope.locale}"/>&nbsp<fmt:message
                    key="subscriber.tariffSmsMoney"/></p>
            <p class="form-message">
                <c:if test="${not empty smsError}">
                    <span><fmt:message key="${smsError}"/></span>
                </c:if>
            </p>
            <input type="hidden" name="confirmation">
            <input type="submit" value="<fmt:message key="subscriber.sms" />" class="btn">
            <a href="subscriber.html" class="form-link"><fmt:message key="subscriber.smsCancel"/></a>
        </form>
    </div>
</u:html>