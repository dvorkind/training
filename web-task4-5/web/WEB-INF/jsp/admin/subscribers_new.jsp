<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.newSubscribers"/>
<u:html title="${title} : ${titlePage}">
    <c:choose>
        <c:when test="${empty subscribers}">
            <div class="main">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="admin.newSubscribersEmpty"/>
                </h2>
            </div>
        </c:when>
        <c:otherwise>
            <div class="main">
                <h2 class="text-center no-margin-bottom">${titlePage}</h2>
                <div class="sort-wrapper">
                    <span><fmt:message key="admin.subscriberSortTitle"/></span>
                    <form action="subscribers_new.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="firstNameUp" ${sort == 'firstNameUp' ? 'selected' : ''}><fmt:message
                                    key="admin.subscriberSortFirstNameUp"/></option>
                            <option value="firstNameDown" ${sort == 'firstNameDown' ? 'selected' : ''}><fmt:message
                                    key="admin.subscriberSortFirstNameDown"/></option>
                            <option value="lastNameUp" ${sort == 'lastNameUp' ? 'selected' : ''}><fmt:message
                                    key="admin.subscriberSortLastNameUp"/></option>
                            <option value="lastNameDown" ${sort == 'lastNameDown' ? 'selected' : ''}><fmt:message
                                    key="admin.subscriberSortLastNameDown"/></option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.subscriberFirstname"/></th>
                        <th width="150"><fmt:message key="admin.subscriberLastname"/></th>
                        <th width="150"><fmt:message key="admin.subscriberPhoneNumber"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="subscriber" items="${subscribers}">
                        <tr>
                            <td>${subscriber.firstname}</td>
                            <td>${subscriber.lastname}</td>
                            <td>${subscriber.phoneNumber}</td>
                            <td class="button-cell">
                                <form action="subscribers_new.html" method="POST">
                                    <input type="hidden" name="id" value="${subscriber.id}">
                                    <input type="submit" value="<fmt:message key="admin.subscriberActivate"/> ✅"
                                           class="btn btn-small">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</u:html>