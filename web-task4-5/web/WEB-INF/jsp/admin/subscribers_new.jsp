<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.subscribersNewTitle"/>
<u:html title="${title} : ${titlePage}">
    <c:choose>
        <c:when test="${empty subscribers}">
            <div class="main">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="admin.subscribersNewEmpty"/>
                </h2>
            </div>
        </c:when>
        <c:otherwise>
            <div class="main">
                <h2 class="text-center no-margin-bottom">${titlePage}</h2>
                <div class="sort-wrapper">
                    <span><fmt:message key="admin.subscribersNewSortTitle"/></span>
                    <form action="subscribers_new.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="firstNameUp" ${sort == 'firstNameUp' ? 'selected' : ''}><fmt:message
                                    key="admin.subscribersNewSortFirstNameUp"/></option>
                            <option value="firstNameDown" ${sort == 'firstNameDown' ? 'selected' : ''}><fmt:message
                                    key="admin.subscribersNewSortFirstNameDown"/></option>
                            <option value="lastNameUp" ${sort == 'lastNameUp' ? 'selected' : ''}><fmt:message
                                    key="admin.subscribersNewSortLastNameUp"/></option>
                            <option value="lastNameDown" ${sort == 'lastNameDown' ? 'selected' : ''}><fmt:message
                                    key="admin.subscribersNewSortLastNameDown"/></option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.subscribersNewFirstname"/></th>
                        <th width="150"><fmt:message key="admin.subscribersNewLastname"/></th>
                        <th width="150"><fmt:message key="admin.subscribersNewPhoneNumber"/></th>
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
                                    <input type="submit" value="<fmt:message key="admin.subscribersNewActivate"/> ✅"
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