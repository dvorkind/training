<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.billsTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${empty allBills}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="admin.billsEmpty"/>
                </h2>
                <h3 class="text-center">(${subscriberFirstName}&nbsp;${subscriberLastName})</h3>
                <p class="msg"><fmt:message key="admin.billsMessageOne"/></p>
                <p class="msg"><fmt:message key="admin.billsMessageTwo"/></p>
                <p class="form-message center-message">
                    <c:if test="${not empty error}">
                        <span><fmt:message key="${error}"/></span>
                    </c:if>
                </p>
                <form action="subscriber_bills.html" method="POST">
                    <input type="hidden" name="subscriberId" value="${subscriberId}">
                    <input type="hidden" name="new">
                    <input type="submit" value="<fmt:message key="admin.billsNew"/> ➕"
                           class="btn center-btn width-auto no-margin-bottom">
                </form>
            </c:when>
            <c:otherwise>
                <h2 class="text-center no-margin-bottom">${titlePage}</h2>
                <h3 class="text-center">(${subscriberFirstName}&nbsp;${subscriberLastName})</h3>
                <p class="msg"><fmt:message key="admin.billsMessageOne"/></p>
                <p class="msg"><fmt:message key="admin.billsMessageTwo"/></p>
                <p class="form-message center-message">
                    <c:if test="${not empty error}">
                        <span><fmt:message key="${error}"/></span>
                    </c:if>
                </p>
                <form action="subscriber_bills.html" method="POST">
                    <input type="hidden" name="subscriberId" value="${subscriberId}">
                    <input type="hidden" name="new">
                    <input type="submit" value="<fmt:message key="admin.billsNew"/> ➕"
                           class="btn center-btn width-auto no-margin-bottom">
                </form>
                <div class="sort-wrapper">
                    <span><fmt:message key="admin.billsSortTitle"/></span>
                    <form action="subscriber_bills.html" method="POST" class="select-form no-margin">
                        <input type="hidden" name="subscriberId" value="${subscriberId}">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="dateUp" ${sort == 'dateUp' ? 'selected' : ''}>
                                <fmt:message key="admin.billsSortDateUp"/>
                            </option>
                            <option value="dateDown" ${sort == 'dateDown' ? 'selected' : ''}>
                                <fmt:message key="admin.billsSortDateDown"/>
                            </option>
                            <option value="sumUp" ${sort == 'sumUp' ? 'selected' : ''}>
                                <fmt:message key="admin.billsSortSumUp"/>
                            </option>
                            <option value="sumDown" ${sort == 'sumDown' ? 'selected' : ''}>
                                <fmt:message key="admin.billsSortSumDown"/>
                            </option>
                            <option value="statusUp" ${sort == 'statusUp' ? 'selected' : ''}>
                                <fmt:message key="admin.billsSortStatusUp"/>
                            </option>
                            <option value="statusDown" ${sort == 'statusDown' ? 'selected' : ''}>
                                <fmt:message key="admin.billsSortStatusDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.billsDate"/></th>
                        <th width="150"><fmt:message key="admin.billsSum"/></th>
                        <th width="150"><fmt:message key="admin.billsStatus"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="bill" items="${allBills}">
                        <tr>
                            <td><ctg:date-format dateTime="${bill.invoiceDate}" locale="${locale}"/></td>
                            <td>
                                <ctg:money-format balance="${bill.sum}" locale="${locale}"/>
                                &nbsp;<fmt:message key="admin.billsMoney"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${bill.paid}">
                                        <fmt:message key="admin.billsStatusPaid"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="admin.billsStatusUnpaid"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="button-cell">
                                <form action="subscriber_bills.html" method="POST">
                                    <input type="hidden" name="billId" value="${bill.id}">
                                    <input type="hidden" name="subscriberId" value="${subscriberId}">
                                    <input type="submit" value="<fmt:message key="admin.billsDelete"/>"
                                           class="btn btn-small btn-in-cell">
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