<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<fmt:message var="title" key="header.phoneStation"/>

<u:html title="${title}">
    <div class="main">
        <h2 class="text-center">
            <fmt:message key="index.title"/>
        </h2>
        <div class="index">
            <h3 class="text-center"><fmt:message key="index.sentenceSubscriber"/></h3>
        </div>
        <div class="index">
            <p><fmt:message key="index.sentenceSubscriberOne"/></p>
            <p><fmt:message key="index.sentenceSubscriberTwo"/></p>
            <p><fmt:message key="index.sentenceSubscriberThree"/></p>
            <p><fmt:message key="index.sentenceSubscriberFour"/></p>
            <table class="data-table">
                <thead>
                <tr>
                    <th width="100"><fmt:message key="index.tariffListName"/></th>
                    <th width="300"><fmt:message key="index.tariffListDescription"/></th>
                    <th width="100"><fmt:message key="index.tariffListSubscriptionFee"/></th>
                    <th width="150"><fmt:message key="index.tariffListCallCost"/></th>
                    <th width="100"><fmt:message key="index.tariffListSmsCost"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="tariff" items="${tariffs}">
                    <tr>
                        <td>${tariff.name}</td>
                        <td class="pre-wrap">${tariff.description}</td>
                        <td>
                            <ctg:money-format balance="${tariff.subscriptionFee}" locale="${locale}"/>
                            &nbsp;<fmt:message key="index.tariffListMoney"/>
                        </td>
                        <td>
                            <ctg:money-format balance="${tariff.callCost}" locale="${locale}"/>
                            &nbsp;<fmt:message key="index.tariffListMoney"/>
                        </td>
                        <td>
                            <ctg:money-format balance="${tariff.smsCost}" locale="${locale}"/>
                            &nbsp;<fmt:message key="index.tariffListMoney"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <p><fmt:message key="index.sentenceSubscriberFive"/></p>
            <p><fmt:message key="index.sentenceSubscriberSix"/></p>
            <p><fmt:message key="index.sentenceSubscriberSeven"/></p>
            <table class="data-table">
                <thead>
                <tr>
                    <th width="100"><fmt:message key="index.serviceListName"/></th>
                    <th width="540"><fmt:message key="index.serviceListDescription"/></th>
                    <th width="100"><fmt:message key="index.serviceListPrice"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="service" items="${services}">
                    <tr>
                        <td>${service.name}</td>
                        <td class="pre-wrap">${service.description}</td>
                        <td>
                            <ctg:money-format balance="${service.price}" locale="${locale}"/>
                            &nbsp;<fmt:message key="index.serviceListMoney"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <p><fmt:message key="index.sentenceSubscriberEight"/></p>
            <p><fmt:message key="index.sentenceSubscriberNine"/></p>
            <p><fmt:message key="index.sentenceSubscriberTen"/></p>
            <p><fmt:message key="index.sentenceSubscriberEleven"/></p>
        </div>
        <div class="index">
            <h3 class="text-center"><fmt:message key="index.sentenceAdmin"/></h3>
        </div>
        <div class="index">
            <p><fmt:message key="index.sentenceAdminOne"/></p>
            <p><fmt:message key="index.sentenceAdminTwo"/></p>
            <p><fmt:message key="index.sentenceAdminThree"/></p>
            <p><fmt:message key="index.sentenceAdminFour"/></p>
            <p><fmt:message key="index.sentenceAdminFive"/></p>
            <p><fmt:message key="index.sentenceAdminSix"/></p>
            <p><fmt:message key="index.sentenceAdminSeven"/></p>
        </div>
    </div>
</u:html>