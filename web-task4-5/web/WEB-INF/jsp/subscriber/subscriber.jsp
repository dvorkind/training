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
        <div class="summary">
            <table>
                <tr>
                    <td><fmt:message key="subscriber.mainFirstname"/></td>
                    <td>${sessionSubscriber.firstname}</td>
                </tr>
                <tr>
                    <td><fmt:message key="subscriber.mainLastname"/></td>
                    <td>${sessionSubscriber.lastname}</td>
                </tr>
                <tr>
                    <td><fmt:message key="subscriber.mainPhoneNumber"/></td>
                    <td>${sessionSubscriber.phoneNumber}</td>
                </tr>
                <tr>
                    <td><fmt:message key="subscriber.mainTariff"/></td>
                    <td>${tariff.name}</td>
                </tr>
                <tr>
                    <td><fmt:message key="subscriber.mainBalance"/></td>
                    <td><ctg:money-format balance="${sessionSubscriber.balance}" locale="${locale}"/>&nbsp<fmt:message key="subscriber.mainMoney"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="subscriber.mainStatus"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${subscriber.blocked}">
                                <fmt:message key="subscriber.mainBlocked"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="subscriber.mainActive"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
            <div class="user-actions">
                <a href="call.html" class="btn">
                    <fmt:message key="subscriber.mainCallButton"/>
                </a>
                <a href="sms.html" class="btn">
                    <fmt:message key="subscriber.mainSmsButton"/>
                </a>
            </div>
        </div>

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
    </div>
</u:html>