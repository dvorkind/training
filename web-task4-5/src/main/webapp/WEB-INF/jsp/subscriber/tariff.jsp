<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.changeTariffTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${fn:length(tariffs) == 1}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="subscriber.changeTariffEmpty"/>
                </h2>
            </c:when>
            <c:otherwise>
                <h2 class="text-center">${titlePage}</h2>
                <div class="sort-wrapper">
                    <span><fmt:message key="subscriber.changeTariffSortTitle"/></span>
                    <form action="tariff.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="nameUp" ${sort == 'nameUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.changeTariffSortNameUp"/>
                            </option>
                            <option value="nameDown" ${sort == 'nameDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.changeTariffSortNameDown"/>
                            </option>
                            <option value="subscriptionFeeUp" ${sort == 'subscriptionFeeUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.changeTariffSortSubscriptionFeeUp"/>
                            </option>
                            <option value="subscriptionFeeDown" ${sort == 'subscriptionFeeDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.changeTariffSortSubscriptionFeeDown"/>
                            </option>
                            <option value="callCostUp" ${sort == 'callCostUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.changeTariffSortCallCostUp"/>
                            </option>
                            <option value="callCostDown" ${sort == 'callCostDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.changeTariffSortCallCostDown"/>
                            </option>
                            <option value="smsCostUp" ${sort == 'smsCostUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.changeTariffSortSmsCostUp"/>
                            </option>
                            <option value="smsCostDown" ${sort == 'smsCostDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.changeTariffSortSmsCostDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="subscriber.changeTariffName"/></th>
                        <th width="300"><fmt:message key="subscriber.changeTariffDescription"/></th>
                        <th width="150"><fmt:message key="subscriber.changeTariffSubscriptionFee"/></th>
                        <th width="150"><fmt:message key="subscriber.changeTariffCallCost"/></th>
                        <th width="150"><fmt:message key="subscriber.changeTariffSmsCost"/></th>
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
                                &nbsp;<fmt:message key="subscriber.changeTariffMoney"/>
                            </td>
                            <td>
                                <ctg:money-format balance="${tariff.callCost}" locale="${locale}"/>
                                &nbsp;<fmt:message key="subscriber.changeTariffMoney"/>
                            </td>
                            <td>
                                <ctg:money-format balance="${tariff.smsCost}" locale="${locale}"/>
                                &nbsp;<fmt:message key="subscriber.changeTariffMoney"/>
                            </td>
                            <td class="button-cell">
                                <form action="tariff.html" method="POST">
                                    <input type="hidden" name="newTariff" value="${tariff.id}">
                                    <input type="hidden" name="confirmation">
                                    <c:if test="${tariff.id != id}">
                                        <input type="submit" value="<fmt:message key="subscriber.changeTariffChoose"/>"
                                               class="btn btn-small btn-in-cell">
                                    </c:if>
                                    <c:if test="${tariff.id == id}">
                                        <input type="submit" value="<fmt:message key="subscriber.changeTariffChoose"/>"
                                               class="btn btn-small btn-in-cell" disabled>
                                    </c:if>

                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</u:html>