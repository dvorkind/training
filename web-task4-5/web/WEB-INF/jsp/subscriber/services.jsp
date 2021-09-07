<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/money.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.services"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${empty allServices}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="subscriber.servicesEmpty"/>
                </h2>
            </c:when>
            <c:otherwise>
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="subscriber.services"/>
                </h2>
                <p class="form-message">
                    <c:if test="${not empty serviceError}">
                        <span><fmt:message key="${serviceError}"/></span>
                    </c:if>
                </p>
                <p class="msg"><fmt:message key="subscriber.serviceMessageOne"/></p>
                <p class="msg"><fmt:message key="subscriber.serviceMessageTwo"/></p>
                <div class="sort-wrapper">
                    <span><fmt:message key="subscriber.serviceSortTitle"/></span>
                    <form action="services.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="nameUp" ${sort == 'nameUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.serviceSortNameUp"/>
                            </option>
                            <option value="nameDown" ${sort == 'nameDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.serviceSortNameDown"/>
                            </option>
                            <option value="priceUp" ${sort == 'priceUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.serviceSortPriceUp"/>
                            </option>
                            <option value="priceDown" ${sort == 'priceDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.serviceSortPriceDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="subscriber.serviceName"/></th>
                        <th width="450"><fmt:message key="subscriber.serviceDescription"/></th>
                        <th width="150"><fmt:message key="subscriber.servicePrice"/></th>
                        <th width="150"><fmt:message key="subscriber.serviceStatus"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="service" items="${allServices}">
                        <tr>
                            <td>${service.name}</td>
                            <td class="pre-wrap">${service.description}</td>
                            <td>
                                <ctg:money-format balance="${service.price}"/>&nbsp<fmt:message
                                    key="subscriber.serviceMoney"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${subscribersServices.contains(service.id)}">
                                        <fmt:message key="subscriber.serviceStatusOn"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="subscriber.serviceStatusOff"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="button-cell">
                                <form action="services.html" method="POST">
                                    <input type="hidden" name="id" value="${service.id}">
                                    <c:choose>
                                        <c:when test="${subscribersServices.contains(service.id)}">
                                            <input type="hidden" name="off">
                                            <input type="submit"
                                                   value="<fmt:message key="subscriber.serviceSwitchOff"/>"
                                                   class="btn btn-small btn-in-cell">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="hidden" name="on">
                                            <input type="submit"
                                                   value="<fmt:message key="subscriber.serviceSwitchOn"/>"
                                                   class="btn btn-small btn-in-cell">
                                        </c:otherwise>
                                    </c:choose>
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