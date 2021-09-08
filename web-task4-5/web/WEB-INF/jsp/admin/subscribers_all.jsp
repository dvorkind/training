<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/money.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.subscribers"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${empty subscribers}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="admin.subscribersEmpty"/>
                </h2>
            </c:when>
            <c:otherwise>
                <h2 class="text-center no-margin-bottom">${titlePage}</h2>
                <div class="sort-wrapper">
                    <span><fmt:message key="admin.subscriberSortTitle"/></span>
                    <form action="subscribers_all.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="firstNameUp" ${sort == 'firstNameUp' ? 'selected' : ''}>
                                <fmt:message key="admin.subscriberSortFirstNameUp"/>
                            </option>
                            <option value="firstNameDown" ${sort == 'firstNameDown' ? 'selected' : ''}>
                                <fmt:message key="admin.subscriberSortFirstNameDown"/>
                            </option>
                            <option value="lastNameUp" ${sort == 'lastNameUp' ? 'selected' : ''}>
                                <fmt:message key="admin.subscriberSortLastNameUp"/>
                            </option>
                            <option value="lastNameDown" ${sort == 'lastNameDown' ? 'selected' : ''}>
                                <fmt:message key="admin.subscriberSortLastNameDown"/>
                            </option>
                            <option value="balanceUp" ${sort == 'balanceUp' ? 'selected' : ''}>
                                <fmt:message key="admin.subscriberSortBalanceUp"/>
                            </option>
                            <option value="balanceDown" ${sort == 'balanceDown' ? 'selected' : ''}>
                                <fmt:message key="admin.subscriberSortBalanceDown"/>
                            </option>
                            <option value="stateUp" ${sort == 'stateUp' ? 'selected' : ''}>
                                <fmt:message key="admin.subscriberSortStateUp"/>
                            </option>
                            <option value="stateDown" ${sort == 'stateDown' ? 'selected' : ''}>
                                <fmt:message key="admin.subscriberSortStateDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.subscriberFirstname"/></th>
                        <th width="150"><fmt:message key="admin.subscriberLastname"/></th>
                        <th width="150"><fmt:message key="admin.subscriberPhoneNumber"/></th>
                        <th width="150"><fmt:message key="admin.subscriberBalance"/></th>
                        <th width="150"><fmt:message key="admin.subscriberState"/></th>
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
                                <ctg:money-format balance="${subscriber.balance}" locale="${sessionScope.locale}"/>&nbsp
                                <fmt:message key="subscriber.money"/>
                            </td>
                            <c:choose>
                                <c:when test="${subscriber.blocked}">
                                    <td><fmt:message key="admin.subscriberStateBlocked"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:message key="admin.subscriberStateActive"/></td>
                                </c:otherwise>
                            </c:choose>
                            <td class="button-cell">
                                <form action="subscribers_edit.html" method="POST">
                                    <input type="hidden" name="id" value="${subscriber.id}">
                                    <input type="submit" value="<fmt:message key="admin.subscriberManage"/> 📝"
                                           class="btn btn-small">
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