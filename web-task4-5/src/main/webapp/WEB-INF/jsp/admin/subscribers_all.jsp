<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.subscribersAllTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${empty subscribers}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="admin.subscribersAllEmpty"/>
                </h2>
            </c:when>
            <c:otherwise>
                <h2 class="text-center">${titlePage}</h2>
                <div class="sort-wrapper">
                    <span><fmt:message key="admin.subscribersAllSortTitle"/></span>
                    <form action="subscribers_all.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="firstNameUp" ${sort == 'firstNameUp' ? 'selected' : ''}>
                                <fmt:message key="admin.subscribersAllSortFirstNameUp"/>
                            </option>
                            <option value="firstNameDown" ${sort == 'firstNameDown' ? 'selected' : ''}>
                                <fmt:message key="admin.subscribersAllSortFirstNameDown"/>
                            </option>
                            <option value="lastNameUp" ${sort == 'lastNameUp' ? 'selected' : ''}>
                                <fmt:message key="admin.subscribersAllSortLastNameUp"/>
                            </option>
                            <option value="lastNameDown" ${sort == 'lastNameDown' ? 'selected' : ''}>
                                <fmt:message key="admin.subscribersAllSortLastNameDown"/>
                            </option>
                            <option value="balanceUp" ${sort == 'balanceUp' ? 'selected' : ''}>
                                <fmt:message key="admin.subscribersAllSortBalanceUp"/>
                            </option>
                            <option value="balanceDown" ${sort == 'balanceDown' ? 'selected' : ''}>
                                <fmt:message key="admin.subscribersAllSortBalanceDown"/>
                            </option>
                            <option value="stateUp" ${sort == 'stateUp' ? 'selected' : ''}>
                                <fmt:message key="admin.subscribersAllSortStateUp"/>
                            </option>
                            <option value="stateDown" ${sort == 'stateDown' ? 'selected' : ''}>
                                <fmt:message key="admin.subscribersAllSortStateDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.subscribersAllFirstname"/></th>
                        <th width="150"><fmt:message key="admin.subscribersAllLastname"/></th>
                        <th width="150"><fmt:message key="admin.subscribersAllPhoneNumber"/></th>
                        <th width="150"><fmt:message key="admin.subscribersAllBalance"/></th>
                        <th width="150"><fmt:message key="admin.subscribersAllState"/></th>
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
                                &nbsp;<fmt:message key="admin.subscribersAllMoney"/>
                            </td>
                            <c:choose>
                                <c:when test="${subscriber.blocked}">
                                    <td><fmt:message key="admin.subscribersAllStateBlocked"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:message key="admin.subscribersAllStateActive"/></td>
                                </c:otherwise>
                            </c:choose>
                            <td class="button-cell">
                                <form action="subscriber_edit.html" method="POST">
                                    <input type="hidden" name="id" value="${subscriber.id}">
                                    <input type="submit" value="<fmt:message key="admin.subscribersAllManage"/> 📝"
                                           class="btn btn-small btn-in-cell">
                                </form>
                                <form action="subscriber_bills.html" method="POST">
                                    <input type="hidden" name="subscriberId" value="${subscriber.id}">
                                    <input type="submit" value="<fmt:message key="admin.subscriberAllBills"/> 💸"
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