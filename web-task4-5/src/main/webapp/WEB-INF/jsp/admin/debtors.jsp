<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.debtorsTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${empty subscribers}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="admin.debtorsEmpty"/>
                </h2>
            </c:when>
            <c:otherwise>
                <h2 class="text-center">${titlePage}</h2>
                <div class="sort-wrapper">
                    <span><fmt:message key="admin.debtorsSortTitle"/></span>
                    <form action="debtors.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="firstNameUp" ${sort == 'firstNameUp' ? 'selected' : ''}>
                                <fmt:message key="admin.debtorsSortFirstNameUp"/>
                            </option>
                            <option value="firstNameDown" ${sort == 'firstNameDown' ? 'selected' : ''}>
                                <fmt:message key="admin.debtorsSortFirstNameDown"/>
                            </option>
                            <option value="lastNameUp" ${sort == 'lastNameUp' ? 'selected' : ''}>
                                <fmt:message key="admin.debtorsSortLastNameUp"/>
                            </option>
                            <option value="lastNameDown" ${sort == 'lastNameDown' ? 'selected' : ''}>
                                <fmt:message key="admin.debtorsSortLastNameDown"/>
                            </option>
                            <option value="balanceUp" ${sort == 'balanceUp' ? 'selected' : ''}>
                                <fmt:message key="admin.debtorsSortBalanceUp"/>
                            </option>
                            <option value="balanceDown" ${sort == 'balanceDown' ? 'selected' : ''}>
                                <fmt:message key="admin.debtorsSortBalanceDown"/>
                            </option>
                            <option value="stateUp" ${sort == 'stateUp' ? 'selected' : ''}>
                                <fmt:message key="admin.debtorsSortStateUp"/>
                            </option>
                            <option value="stateDown" ${sort == 'stateDown' ? 'selected' : ''}>
                                <fmt:message key="admin.debtorsSortStateDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.debtorsFirstname"/></th>
                        <th width="150"><fmt:message key="admin.debtorsLastname"/></th>
                        <th width="150"><fmt:message key="admin.debtorsPhoneNumber"/></th>
                        <th width="150"><fmt:message key="admin.debtorsBalance"/></th>
                        <th width="150"><fmt:message key="admin.debtorsState"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="subscriber" items="${subscribers}">
                        <tr>
                            <td>${subscriber.firstname}</td>
                            <td>${subscriber.lastname}</td>
                            <td>${subscriber.phoneNumber}</td>
                            <td>
                                <ctg:money-format balance="${subscriber.balance}" locale="${locale}"/>
                                &nbsp;<fmt:message key="admin.debtorsMoney"/>
                            </td>
                            <c:choose>
                                <c:when test="${subscriber.blocked}">
                                    <td><fmt:message key="admin.debtorsStateBlocked"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:message key="admin.debtorsStateActive"/></td>
                                </c:otherwise>
                            </c:choose>
                            <td class="button-cell">
                                <c:if test="${not subscriber.blocked}">
                                    <form action="debtors.html" method="POST">
                                        <input type="hidden" name="id" value="${subscriber.id}">
                                        <input type="submit" value="<fmt:message key="admin.debtorsBlock"/> ⛔"
                                               class="btn btn-small btn-in-cell">
                                    </form>
                                    <form action="subscriber_bills.html" method="POST">
                                        <input type="hidden" name="subscriberId" value="${subscriber.id}">
                                        <input type="submit" value="<fmt:message key="admin.subscriberAllBills"/> 💸"
                                               class="btn btn-small btn-in-cell">
                                    </form>
                                </c:if>
                                <c:if test="${subscriber.blocked}">
                                    <form action="subscriber_edit.html" method="POST">
                                        <input type="hidden" name="id" value="${subscriber.id}">
                                        <input type="submit" value="<fmt:message key="admin.debtorsManage"/> 📝"
                                               class="btn btn-small btn-in-cell">
                                    </form>
                                    <form action="subscriber_bills.html" method="POST">
                                        <input type="hidden" name="subscriberId" value="${subscriber.id}">
                                        <input type="submit" value="<fmt:message key="admin.debtorsBills"/> 💸"
                                               class="btn btn-small btn-in-cell">
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</u:html>