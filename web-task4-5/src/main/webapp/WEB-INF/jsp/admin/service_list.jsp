<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.serviceListTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${empty services}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="admin.serviceListEmpty"/>
                </h2>
                <a href="service_manage.html" class="btn center-btn no-margin-bottom">
                    <fmt:message key="admin.serviceListAdd"/> ➕
                </a>
            </c:when>
            <c:otherwise>
                <h2 class="text-center no-margin-bottom">${titlePage}</h2>
                <a href="service_manage.html" class="btn center-btn no-margin-bottom">
                    <fmt:message key="admin.serviceListAdd"/> ➕
                </a>
                <div class="sort-wrapper">
                    <span><fmt:message key="admin.serviceListSortTitle"/></span>
                    <form action="service_list.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="nameUp" ${sort == 'nameUp' ? 'selected' : ''}>
                                <fmt:message key="admin.serviceListSortNameUp"/>
                            </option>
                            <option value="nameDown" ${sort == 'nameDown' ? 'selected' : ''}>
                                <fmt:message key="admin.serviceListSortNameDown"/>
                            </option>
                            <option value="priceUp" ${sort == 'priceUp' ? 'selected' : ''}>
                                <fmt:message key="admin.serviceListSortPriceUp"/>
                            </option>
                            <option value="priceDown" ${sort == 'priceDown' ? 'selected' : ''}>
                                <fmt:message key="admin.serviceListSortPriceDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.serviceListName"/></th>
                        <th width="600"><fmt:message key="admin.serviceListDescription"/></th>
                        <th width="150"><fmt:message key="admin.serviceListPrice"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="service" items="${services}">
                        <tr>
                            <td>${service.name}</td>
                            <td class="pre-wrap">${service.description}</td>
                            <td>
                                <ctg:money-format balance="${service.price}" locale="${locale}"/>
                                &nbsp;<fmt:message key="admin.serviceListMoney"/>
                            </td>
                            <td class="button-cell">
                                <form action="service_manage.html" method="POST">
                                    <input type="hidden" name="id" value="${service.id}">
                                    <input type="submit" value="<fmt:message key="admin.serviceListEdit"/> 📝"
                                           class="btn btn-small btn-in-cell">
                                </form>
                                <form action="service_delete.html" method="POST">
                                    <input type="hidden" name="id" value="${service.id}">
                                    <input type="submit" value="<fmt:message key="admin.serviceListDelete"/> ❌"
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