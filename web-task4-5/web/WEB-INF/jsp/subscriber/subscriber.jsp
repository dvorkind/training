<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.mainTitle"/>

<u:html title="${title} : ${titlePage}">
    <div class="main">
        <h2 class="text-center">
            <fmt:message key="subscriber.mainHello"/>, ${sessionSubscriber.firstname}!
        </h2>
        <table class="data-table">
            <thead>
            <tr>
                <th width="150"><fmt:message key="subscriber.mainFirstname"/></th>
                <th width="300"><fmt:message key="subscriber.mainLastname"/></th>
                <th width="150"><fmt:message key="subscriber.mainPhoneNumber"/></th>
                <th width="150"><fmt:message key="subscriber.mainBalance"/></th>
                <th width="150"><fmt:message key="subscriber.mainStatus"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${sessionSubscriber.firstname}</td>
                <td>${sessionSubscriber.lastname}</td>
                <td>${sessionSubscriber.phoneNumber}</td>
                <td>
                    <ctg:money-format balance="${sessionSubscriber.balance}" locale="${locale}"/>
                    &nbsp<fmt:message key="subscriber.mainMoney"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${sessionSubscriber.blocked}">
                            <fmt:message key="subscriber.mainBlocked"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="subscriber.mainActive"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            </tbody>
        </table>

        <h2 class="text-center">
            <fmt:message key="subscriber.mainTariff"/>
        </h2>
        <table class="data-table">
            <thead>
            <tr>
                <th width="150"><fmt:message key="subscriber.mainTariffName"/></th>
                <th width="300"><fmt:message key="subscriber.mainTariffDescription"/></th>
                <th width="150"><fmt:message key="subscriber.mainTariffSubscriptionFee"/></th>
                <th width="150"><fmt:message key="subscriber.mainTariffCallCost"/></th>
                <th width="150"><fmt:message key="subscriber.mainTariffSmsCost"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${tariff.name}</td>
                <td class="pre-wrap">${tariff.description}</td>
                <td>
                    <ctg:money-format balance="${tariff.subscriptionFee}" locale="${locale}"/>
                    &nbsp<fmt:message key="subscriber.mainTariffMoney"/>
                </td>
                <td>
                    <ctg:money-format balance="${tariff.callCost}" locale="${locale}"/>
                    &nbsp<fmt:message key="subscriber.mainTariffMoney"/>
                </td>
                <td>
                    <ctg:money-format balance="${tariff.smsCost}" locale="${locale}"/>
                    &nbsp<fmt:message key="subscriber.mainTariffMoney"/>
                </td>
            </tr>
            </tbody>
        </table>
        <c:if test="${not empty subscribersServices}">
            <h2 class="text-center">
                <fmt:message key="subscriber.mainActiveServices"/>
            </h2>
            <table class="data-table">
                <thead>
                <tr>
                    <th width="150"><fmt:message key="subscriber.mainServiceName"/></th>
                    <th width="620"><fmt:message key="subscriber.mainServiceDescription"/></th>
                    <th width="150"><fmt:message key="subscriber.mainServicePrice"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="service" items="${subscribersServices}">
                    <tr>
                        <td>${service.name}</td>
                        <td class="pre-wrap">${service.description}</td>
                        <td>
                            <ctg:money-format balance="${service.price}" locale="${locale}"/>
                            &nbsp<fmt:message key="subscriber.mainServiceMoney"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <a href="call.html" class="btn center-btn">
            <fmt:message key="subscriber.mainCallButton"/>
        </a>
        <a href="sms.html" class="btn center-btn">
            <fmt:message key="subscriber.mainSmsButton"/>
        </a>
    </div>
</u:html>