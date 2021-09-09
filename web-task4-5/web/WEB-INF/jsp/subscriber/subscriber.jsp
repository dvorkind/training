<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/money.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.main"/>

<jsp:useBean id="sessionSubscriber" scope="session" class="by.dvorkin.web.model.entity.Subscriber"/>
<jsp:useBean id="tariff" scope="request" class="by.dvorkin.web.model.entity.Tariff"/>

<u:html title="${title} : ${titlePage}">
    <div class="main">
        <h2 class="text-center">
            <fmt:message key="subscriber.hello"/>, ${sessionSubscriber.firstname}!
        </h2>
        <table class="data-table">
            <thead>
            <tr>
                <th width="150"><fmt:message key="subscriber.profileFirstname"/></th>
                <th width="300"><fmt:message key="subscriber.profileLastname"/></th>
                <th width="150"><fmt:message key="subscriber.profilePhoneNumber"/></th>
                <th width="150"><fmt:message key="subscriber.profileBalance"/></th>
                <th width="150"><fmt:message key="subscriber.profileStatus"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${sessionSubscriber.firstname}</td>
                <td>${sessionSubscriber.lastname}</td>
                <td>${sessionSubscriber.phoneNumber}</td>
                <td>
                    <ctg:money-format balance="${sessionSubscriber.balance}" locale="${locale}"/>
                    &nbsp<fmt:message key="subscriber.money"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${sessionSubscriber.blocked}">
                            <fmt:message key="subscriber.profileBlocked"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="subscriber.profileActive"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            </tbody>
        </table>

        <h2 class="text-center">
            <fmt:message key="subscriber.profileTariff"/>
        </h2>
        <table class="data-table">
            <thead>
            <tr>
                <th width="150"><fmt:message key="admin.tariffName"/></th>
                <th width="300"><fmt:message key="admin.tariffDescription"/></th>
                <th width="150"><fmt:message key="admin.tariffSubscriptionFee"/></th>
                <th width="150"><fmt:message key="admin.tariffCallCost"/></th>
                <th width="150"><fmt:message key="admin.tariffSmsCost"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${tariff.name}</td>
                <td class="pre-wrap">${tariff.description}</td>
                <td>
                    <ctg:money-format balance="${tariff.subscriptionFee}" locale="${locale}"/>
                    &nbsp<fmt:message key="admin.tariffMoney"/>
                </td>
                <td>
                    <ctg:money-format balance="${tariff.callCost}" locale="${locale}"/>
                    &nbsp<fmt:message key="admin.tariffMoney"/>
                </td>
                <td>
                    <ctg:money-format balance="${tariff.smsCost}" locale="${locale}"/>
                    &nbsp<fmt:message key="admin.tariffMoney"/>
                </td>
            </tr>
            </tbody>
        </table>

        <h2 class="text-center">
            <fmt:message key="subscriber.activeServices"/>
        </h2>
        <table class="data-table">
            <thead>
            <tr>
                <th width="150"><fmt:message key="subscriber.serviceName"/></th>
                <th width="620"><fmt:message key="subscriber.serviceDescription"/></th>
                <th width="150"><fmt:message key="subscriber.servicePrice"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="service" items="${subscribersServices}">
                <tr>
                    <td>${service.name}</td>
                    <td class="pre-wrap">${service.description}</td>
                    <td>
                        <ctg:money-format balance="${service.price}" locale="${locale}"/>
                        &nbsp<fmt:message key="subscriber.serviceMoney"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <a href="call.html" class="btn center-btn">
            <fmt:message key="subscriber.call"/>
        </a>
        <a href="sms.html" class="btn center-btn">
            <fmt:message key="subscriber.sms"/>
        </a>
    </div>
</u:html>