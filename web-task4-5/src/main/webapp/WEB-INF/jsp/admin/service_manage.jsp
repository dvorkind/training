<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<c:choose>
    <c:when test="${not empty id}">
        <fmt:message var="titlePage" key="admin.serviceManageEditTitle"/>
    </c:when>
    <c:otherwise>
        <fmt:message var="titlePage" key="admin.serviceManageAddTitle"/>
    </c:otherwise>
</c:choose>

<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="service_manage.html" method="post" class="reg-form submit-form">
            <h2>${titlePage}</h2>
            <div class="form-inputs">
                <div class="input-group">
                    <label for="serviceName"><fmt:message key="admin.serviceManageName"/></label>
                    <input type="text" id="serviceName" name="serviceName" value="${serviceName}">
                    <c:if test="${serviceNameIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty serviceNameError}">
                        <span><fmt:message key="${serviceNameError}"/></span>
                    </c:if>
                </p>
                <div class="input-group">
                    <label for="serviceDescription"><fmt:message key="admin.serviceManageDescription"/></label>
                    <textarea id="serviceDescription" name="serviceDescription"
                              maxlength="200">${serviceDescription}</textarea>
                    <c:if test="${serviceDescriptionIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty serviceDescriptionError}">
                        <span><fmt:message key="${serviceDescriptionError}"/></span>
                    </c:if>
                </p>
                <div class="input-group">
                    <label><fmt:message key="admin.serviceManagePrice"/></label>
                    <input type="number" min="0" id="servicePriceRoubles" name="servicePriceRoubles"
                           class="small-input"
                           value="${servicePriceRoubles == null ? 5 : servicePriceRoubles}">
                    <label for="servicePriceRoubles" class="small-label"><fmt:message
                            key="admin.serviceManageMoney"/></label>
                    <input type="number" min="0" max="99" id="servicePriceKopecks"
                           name="servicePriceKopecks" class="small-input"
                           value="${servicePriceKopecks == null ? 0 : servicePriceKopecks}">
                    <label for="servicePriceKopecks" class="small-label"><fmt:message
                            key="admin.serviceManageMoneyKopecks"/></label>
                    <c:if test="${servicePriceIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty servicePriceError}">
                        <span><fmt:message key="${servicePriceError}"/></span>
                    </c:if>
                </p>
            </div>
            <div>
                <c:choose>
                    <c:when test="${not empty id}">
                        <input type="hidden" name="id" value="${id}">
                        <input type="submit" value="<fmt:message key="admin.serviceManageEditButton" />" class="btn">
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="<fmt:message key="admin.serviceManageAddButton" />" class="btn">
                    </c:otherwise>
                </c:choose>
                <a href="service_list.html" class="form-link"><fmt:message key="admin.serviceManageCancel"/></a>
            </div>
        </form>
    </div>
</u:html>