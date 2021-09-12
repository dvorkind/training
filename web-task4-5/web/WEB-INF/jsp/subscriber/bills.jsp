<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.billsTitle"/>→
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${empty allBills}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="subscriber.billsEmpty"/>
                </h2>
            </c:when>
            <c:otherwise>
                <h2 class="text-center">${titlePage}</h2>
                <p class="msg"><fmt:message key="subscriber.billsMessage"/></p>
                <div class="sort-wrapper">
                    <span><fmt:message key="subscriber.billsSortTitle"/></span>
                    <form action="bills.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="dateUp" ${sort == 'dateUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.billsSortDateUp"/>
                            </option>
                            <option value="dateDown" ${sort == 'dateDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.billsSortDateDown"/>
                            </option>
                            <option value="sumUp" ${sort == 'sumUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.billsSortSumUp"/>
                            </option>
                            <option value="sumDown" ${sort == 'sumDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.billsSortSumDown"/>
                            </option>
                            <option value="statusUp" ${sort == 'statusUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.billsSortStatusUp"/>
                            </option>
                            <option value="statusDown" ${sort == 'statusDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.billsSortStatusDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="subscriber.billsDate"/></th>
                        <th width="150"><fmt:message key="subscriber.billsSum"/></th>
                        <th width="150"><fmt:message key="subscriber.billsStatus"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="bill" items="${allBills}">
                        <tr>
                            <td><ctg:date-format dateTime="${bill.invoiceDate}" locale="${locale}"/></td>
                            <td>
                                <ctg:money-format balance="${bill.sum}" locale="${locale}"/>&nbsp<fmt:message
                                    key="subscriber.billsMoney"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${bill.paid}">
                                        <fmt:message key="subscriber.billsStatusPaid"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="subscriber.billsStatusUnpaid"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="button-cell">
                                <form action="bills.html" method="POST">
                                    <input type="hidden" name="id" value="${bill.id}">
                                    <c:if test="${not bill.paid}">
                                        <input type="submit" value="<fmt:message key="subscriber.billsPay"/>"
                                               class="btn btn-small btn-in-cell">
                                    </c:if>
                                    <c:if test="${bill.paid}">
                                        <input type="submit" value="<fmt:message key="subscriber.billsPay"/>"
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