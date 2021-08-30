<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/money.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.services"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${empty services}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="admin.servicesEmpty"/>
                </h2>
                <a href="service_manage.html" class="btn center-btn no-margin-bottom">
                    <fmt:message key="admin.serviceAdd"/> ➕
                </a>
            </c:when>
            <c:otherwise>
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="admin.services"/>
                </h2>
                <a href="service_manage.html" class="btn center-btn no-margin-bottom">
                    <fmt:message key="admin.serviceAdd"/> ➕
                </a>
                <div class="sort-wrapper">
                    <span><fmt:message key="admin.serviceSortTitle"/></span>
                    <form action="service_list.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="nameUp" ${sort == 'nameUp' ? 'selected' : ''}>
                                <fmt:message key="admin.serviceSortNameUp"/>
                            </option>
                            <option value="nameDown" ${sort == 'nameDown' ? 'selected' : ''}>
                                <fmt:message key="admin.serviceSortNameDown"/>
                            </option>
                            <option value="priceUp" ${sort == 'priceUp' ? 'selected' : ''}>
                                <fmt:message key="admin.serviceSortPriceUp"/>
                            </option>
                            <option value="priceDown" ${sort == 'priceDown' ? 'selected' : ''}>
                                <fmt:message key="admin.serviceSortPriceDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.serviceName"/></th>
                        <th width="600"><fmt:message key="admin.serviceDescription"/></th>
                        <th width="150"><fmt:message key="admin.servicePrice"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="service" items="${services}">
                        <tr>
                            <td>${service.name}</td>
                            <td class="pre-wrap">${service.description}</td>
                            <td>
                                <ctg:money-format balance="${service.price}"/>&nbsp<fmt:message
                                    key="admin.serviceMoney"/>
                            </td>
                            <td class="button-cell">
                                <form action="service_manage.html" method="POST">
                                    <input type="hidden" name="id" value="${service.id}">
                                    <input type="submit" value="<fmt:message key="admin.serviceEdit"/> 📝"
                                           class="btn btn-small btn-in-cell">
                                </form>
                                <form action="service_delete.html" method="POST">
                                    <input type="hidden" name="id" value="${service.id}">
                                    <input type="submit" value="<fmt:message key="admin.serviceDelete"/> ❌"
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