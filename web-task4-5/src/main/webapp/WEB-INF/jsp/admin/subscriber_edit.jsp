<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.subscriberEditTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <h2 class="text-center">${titlePage}</h2>
        <div class="index edit-container">
            <h3 class="text-center full-width"><fmt:message key="admin.subscriberEditProfile"/></h3>
            <form action="subscriber_edit.html" method="post" class="submit-form reg-form">
                <div class="form-inputs">
                    <div class="input-group">
                        <label for="login"><fmt:message key="admin.subscriberEditLogin"/></label>
                        <c:if test="${login != null}">
                            <input type="text" id="login" name="login" value=${login}>
                        </c:if>
                        <c:if test="${login == null}">
                            <input type="text" id="login" name="login" value=${account.login}>
                        </c:if>
                        <c:if test="${loginIsValid}">
                            <span class="validation-indicator"> &#10004 </span>
                        </c:if>
                    </div>
                    <p class="form-message">
                        <c:if test="${not empty loginError}">
                            <span><fmt:message key="${loginError}"/></span>
                        </c:if>
                    </p>
                    <div class="input-group">
                        <label for="firstname"><fmt:message key="admin.subscriberEditFirstname"/></label>
                        <c:if test="${firstname != null}">
                            <input type="text" id="firstname" name="firstname" value=${firstname}>
                        </c:if>
                        <c:if test="${firstname == null}">
                            <input type="text" id="firstname" name="firstname" value=${subscriber.firstname}>
                        </c:if>
                        <c:if test="${firstnameIsValid}">
                            <span class="validation-indicator"> &#10004 </span>
                        </c:if>
                    </div>
                    <p class="form-message">
                        <c:if test="${not empty firstnameError}">
                            <fmt:message key="${firstnameError}"/>
                        </c:if>
                    </p>
                    <div class="input-group">
                        <label for="lastname"><fmt:message key="admin.subscriberEditLastname"/></label>
                        <c:if test="${lastname != null}">
                            <input type="text" id="lastname" name="lastname" value=${lastname}>
                        </c:if>
                        <c:if test="${lastname == null}">
                            <input type="text" id="lastname" name="lastname" value=${subscriber.lastname}>
                        </c:if>
                        <c:if test="${lastnameIsValid}">
                            <span class="validation-indicator"> &#10004 </span>
                        </c:if>
                    </div>
                    <p class="form-message">
                        <c:if test="${not empty lastnameError}">
                            <fmt:message key="${lastnameError}"/>
                        </c:if>
                    </p>
                    <div class="input-group">
                        <label for="phoneNumber"><fmt:message key="admin.subscriberEditPhoneNumber"/></label>
                        <c:if test="${phoneNumber != null}">
                            <input type="text" id="phoneNumber" name="phoneNumber" value=${phoneNumber}>
                        </c:if>
                        <c:if test="${phoneNumber == null}">
                            <input type="text" id="phoneNumber" name="phoneNumber" value=${subscriber.phoneNumber}>
                        </c:if>
                        <c:if test="${phoneNumberIsValid}">
                            <span class="validation-indicator"> &#10004 </span>
                        </c:if>
                    </div>
                    <p class="form-message">
                        <c:if test="${not empty phoneNumberError}">
                            <fmt:message key="${phoneNumberError}"/>
                        </c:if>
                    </p>
                </div>
                <div>
                    <input type="hidden" name="subscriberId" value="${subscriber.id}">
                    <input type="hidden" name="confirmation">
                    <input type="submit" value="<fmt:message key="admin.subscriberEditSave" /> 💾" class="btn">
                    <a href="subscribers_all.html" class="form-link"><fmt:message key="admin.subscriberEditCancel"/></a>
                </div>
            </form>
            <div class="edit-buttons-container">
                <form action="subscriber_edit.html" method="POST">
                    <input type="hidden" name="subscriberId" value="${subscriber.id}">
                    <input type="hidden" name="block">
                    <c:choose>
                        <c:when test="${subscriber.blocked}">
                            <input type="submit" value="<fmt:message key="admin.subscriberEditUnblock"/>  &#10004"
                                   class="btn">
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="<fmt:message key="admin.subscriberEditBlock"/> ⛔" class="btn">
                        </c:otherwise>
                    </c:choose>
                </form>

                <form action="subscriber_edit.html" method="POST">
                    <input type="hidden" name="subscriberId" value="${subscriber.id}">
                    <input type="hidden" name="resetPassword">
                    <input type="submit" value="<fmt:message key="admin.subscriberEditResetPassword"/>  🔑" class="btn">
                </form>

                <form action="subscriber_bills.html" method="POST">
                    <input type="hidden" name="subscriberId" value="${subscriber.id}">
                    <input type="submit" value="<fmt:message key="admin.subscriberEditBills"/> 💸" class="btn">
                </form>
            </div>
        </div>
        <div class="index">
            <h3 class="text-center"><fmt:message key="admin.subscriberEditServicesTitle"/></h3>
            <table class="data-table">
                <thead>
                <tr>
                    <th width="150"><fmt:message key="admin.subscriberEditServicesName"/></th>
                    <th width="450"><fmt:message key="admin.subscriberEditServicesDescription"/></th>
                    <th width="150"><fmt:message key="admin.subscriberEditServicesPrice"/></th>
                    <th width="150"><fmt:message key="admin.subscriberEditServicesStatus"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="service" items="${allServices}">
                    <tr>
                        <td>${service.name}</td>
                        <td class="pre-wrap">${service.description}</td>
                        <td>
                            <ctg:money-format balance="${service.price}" locale="${locale}"/>
                            &nbsp;<fmt:message key="admin.subscriberEditServicesMoney"/>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${subscribersServices.contains(service)}">
                                    <fmt:message key="admin.subscriberEditServicesStatusOn"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="admin.subscriberEditServicesStatusOff"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="button-cell">
                            <form action="subscriber_edit.html" method="POST">
                                <input type="hidden" name="serviceId" value="${service.id}">
                                <input type="hidden" name="subscriberId" value="${subscriber.id}">
                                <c:choose>
                                    <c:when test="${subscribersServices.contains(service)}">
                                        <input type="hidden" name="off">
                                        <input type="submit"
                                               value="<fmt:message key="admin.subscriberEditServicesSwitchOff"/>"
                                               class="btn btn-small btn-in-cell">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="on">
                                        <input type="submit"
                                               value="<fmt:message key="admin.subscriberEditServicesSwitchOn"/>"
                                               class="btn btn-small btn-in-cell">
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="index">
            <h3 class="text-center"><fmt:message key="admin.subscriberTariffTitle"/></h3>
            <table class="data-table">
                <thead>
                <tr>
                    <th width="150"><fmt:message key="admin.subscriberTariffName"/></th>
                    <th width="300"><fmt:message key="admin.subscriberTariffDescription"/></th>
                    <th width="150"><fmt:message key="admin.subscriberTariffSubscriptionFee"/></th>
                    <th width="150"><fmt:message key="admin.subscriberTariffCallCost"/></th>
                    <th width="150"><fmt:message key="admin.subscriberTariffSmsCost"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="tariff" items="${tariffs}">
                    <tr>
                        <td>${tariff.name}</td>
                        <td class="pre-wrap">${tariff.description}</td>
                        <td>
                            <ctg:money-format balance="${tariff.subscriptionFee}" locale="${locale}"/>
                            &nbsp;<fmt:message key="admin.subscriberTariffMoney"/>
                        </td>
                        <td>
                            <ctg:money-format balance="${tariff.callCost}" locale="${locale}"/>
                            &nbsp;<fmt:message key="admin.subscriberTariffMoney"/>
                        </td>
                        <td>
                            <ctg:money-format balance="${tariff.smsCost}" locale="${locale}"/>
                            &nbsp;<fmt:message key="admin.subscriberTariffMoney"/>
                        </td>
                        <td class="button-cell">
                            <form action="subscriber_edit.html" method="POST">
                                <input type="hidden" name="newTariff" value="${tariff.id}">
                                <input type="hidden" name="subscriberId" value="${subscriber.id}">
                                <c:if test="${tariff.id != subscriber.tariff}">
                                    <input type="submit" value="<fmt:message key="admin.subscriberTariffChoose"/>"
                                           class="btn btn-small btn-in-cell">
                                </c:if>
                                <c:if test="${tariff.id == subscriber.tariff}">
                                    <input type="submit" value="<fmt:message key="admin.subscriberTariffChoose"/>"
                                           class="btn btn-small btn-in-cell" disabled>
                                </c:if>

                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</u:html>