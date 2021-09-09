<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/money.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.tariff"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="tariff.html" method="post" class="submit-form reg-form">
            <h2 class="text-center">${titlePage}</h2>
            <p class="msg"><fmt:message key="subscriber.changeTariffMessageOne"/></p>
            <select name="newTariff" class="btn center-btn">
                <c:forEach var="tariff" items="${tariffs}">
                    <c:if test="${tariff.id != id}">
                        <option value="${tariff.id}">${tariff.name}</option>
                    </c:if>
                </c:forEach>
            </select>
            <p class="msg"><fmt:message key="subscriber.changeTariffMessageTwo"/></p>
            <p class="msg"><fmt:message key="subscriber.changeTariffMessageThree"/></p>
            <p class="form-message">
                <c:if test="${not empty tariffChangeError}">
                    <c:if test="${not empty tariffChangeDate}">
                        <span><fmt:message key="${tariffChangeError}"/>&nbsp;${tariffChangeDate}</span>
                    </c:if>
                    <c:if test="${empty tariffChangeDate}">
                        <span><fmt:message key="${tariffChangeError}"/></span>
                    </c:if>

                </c:if>
            </p>
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="confirmation">
            <input type="submit" value="<fmt:message key="subscriber.changeTariff" />" class="btn">
            <a href="subscriber.html" class="form-link"><fmt:message key="subscriber.changeCancel"/></a>
        </form>
        <div class="sort-wrapper">
            <span><fmt:message key="admin.tariffSortTitle"/></span>
            <form action="tariff.html" method="POST" class="select-form no-margin">
                <select name="sort" onchange="submit()" class="btn btn-transparent">
                    <option value="nameUp" ${sort == 'nameUp' ? 'selected' : ''}><fmt:message key="admin.tariffSortNameUp"/></option>
                    <option value="nameDown" ${sort == 'nameDown' ? 'selected' : ''}><fmt:message key="admin.tariffSortNameDown"/></option>
                    <option value="subscriptionFeeUp" ${sort == 'subscriptionFeeUp' ? 'selected' : ''}><fmt:message key="admin.tariffSortSubscriptionFeeUp"/></option>
                    <option value="subscriptionFeeDown" ${sort == 'subscriptionFeeDown' ? 'selected' : ''}><fmt:message key="admin.tariffSortSubscriptionFeeDown"/></option>
                    <option value="callCostUp" ${sort == 'callCostUp' ? 'selected' : ''}><fmt:message key="admin.tariffSortCallCostUp"/></option>
                    <option value="callCostDown" ${sort == 'callCostDown' ? 'selected' : ''}><fmt:message key="admin.tariffSortCallCostDown"/></option>
                    <option value="smsCostUp" ${sort == 'smsCostUp' ? 'selected' : ''}><fmt:message key="admin.tariffSortSmsCostUp"/></option>
                    <option value="smsCostDown" ${sort == 'smsCostDown' ? 'selected' : ''}><fmt:message key="admin.tariffSortSmsCostDown"/></option>
                </select>
            </form>
        </div>
        <table class="data-table">
            <thead>
            <tr>
                <th width="150"><fmt:message key="admin.tariffName"/></th>
                <th width="300"><fmt:message key="admin.tariffDescription"/></th>
                <th width="150"><fmt:message key="admin.tariffSubscriptionFee"/></th>
                <th width="150"><fmt:message key="admin.tariffCallCost"/></th>
                <th width="150"><fmt:message key="admin.tariffSmsCost"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="tariff" items="${tariffs}">
                <c:if test="${tariff.id != id}">
                    <tr>
                        <td>${tariff.name}</td>
                        <td class="pre-wrap">${tariff.description}</td>
                        <td>
                            <ctg:money-format balance="${tariff.subscriptionFee}" locale="${locale}"/>&nbsp<fmt:message
                                key="admin.tariffMoney"/>
                        </td>
                        <td>
                            <ctg:money-format balance="${tariff.callCost}"
                                              locale="${locale}"/>&nbsp<fmt:message
                                key="admin.tariffMoney"/>
                        </td>
                        <td>
                            <ctg:money-format balance="${tariff.smsCost}"
                                              locale="${locale}"/>&nbsp<fmt:message
                                key="admin.tariffMoney"/>
                        </td>
                        <td class="button-cell">
                            <form action="tariff.html" method="POST">
                                <input type="hidden" name="newTariff" value="${tariff.id}">
                                <input type="hidden" name="confirmation">
                                    <input type="submit" value="<fmt:message key="subscriber.changeTariffChoose"/>"
                                           class="btn btn-small btn-in-cell">
                            </form>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</u:html>