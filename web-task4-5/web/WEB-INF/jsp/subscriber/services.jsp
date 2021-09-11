<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.servicesTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <c:choose>
            <c:when test="${empty allServices}">
                <h2 class="text-center no-margin-bottom">
                    <fmt:message key="subscriber.servicesEmpty"/>
                </h2>
            </c:when>
            <c:otherwise>
                <h2 class="text-center no-margin-bottom">${titlePage}</h2>
                <p class="msg"><fmt:message key="subscriber.servicesMessageOne"/></p>
                <p class="msg"><fmt:message key="subscriber.servicesMessageTwo"/></p>
                <div class="sort-wrapper">
                    <span><fmt:message key="subscriber.servicesSortTitle"/></span>
                    <form action="services.html" method="POST" class="select-form no-margin">
                        <select name="sort" onchange="submit()" class="btn btn-transparent">
                            <option value="nameUp" ${sort == 'nameUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.servicesSortNameUp"/>
                            </option>
                            <option value="nameDown" ${sort == 'nameDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.servicesSortNameDown"/>
                            </option>
                            <option value="priceUp" ${sort == 'priceUp' ? 'selected' : ''}>
                                <fmt:message key="subscriber.servicesSortPriceUp"/>
                            </option>
                            <option value="priceDown" ${sort == 'priceDown' ? 'selected' : ''}>
                                <fmt:message key="subscriber.servicesSortPriceDown"/>
                            </option>
                        </select>
                    </form>
                </div>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="subscriber.servicesName"/></th>
                        <th width="450"><fmt:message key="subscriber.servicesDescription"/></th>
                        <th width="150"><fmt:message key="subscriber.servicesPrice"/></th>
                        <th width="150"><fmt:message key="subscriber.servicesStatus"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="service" items="${allServices}">
                        <tr>
                            <td>${service.name}</td>
                            <td class="pre-wrap">${service.description}</td>
                            <td>
                                <ctg:money-format balance="${service.price}" locale="${locale}"/>&nbsp<fmt:message
                                    key="subscriber.servicesMoney"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${subscribersServices.contains(service)}">
                                        <fmt:message key="subscriber.servicesStatusOn"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="subscriber.servicesStatusOff"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="button-cell">
                                <form action="services.html" method="POST">
                                    <input type="hidden" name="id" value="${service.id}">
                                    <c:choose>
                                        <c:when test="${subscribersServices.contains(service)}">
                                            <input type="hidden" name="off">
                                            <input type="submit"
                                                   value="<fmt:message key="subscriber.servicesSwitchOff"/>"
                                                   class="btn btn-small btn-in-cell">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="hidden" name="on">
                                            <input type="submit"
                                                   value="<fmt:message key="subscriber.servicesSwitchOn"/>"
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