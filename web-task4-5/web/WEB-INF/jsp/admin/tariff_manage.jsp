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
        <fmt:message var="titlePage" key="admin.tariffManageEditTitle"/>
    </c:when>
    <c:otherwise>
        <fmt:message var="titlePage" key="admin.tariffManageAddTitle"/>
    </c:otherwise>
</c:choose>

<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="tariff_manage.html" method="post" class="reg-form submit-form">
            <h2>${titlePage}</h2>
            <div class="form-inputs">
                <div class="input-group">
                    <label for="tariffName"><fmt:message key="admin.tariffManageName"/></label>
                    <input type="text" id="tariffName" name="tariffName" value="${tariffName}">
                    <c:if test="${tariffNameIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty tariffNameError}">
                        <span><fmt:message key="${tariffNameError}"/></span>
                    </c:if>
                </p>
                <div class="input-group">
                    <label for="tariffDescription"><fmt:message key="admin.tariffManageDescription"/></label>
                    <textarea id="tariffDescription" name="tariffDescription"
                              maxlength="200">${tariffDescription}</textarea>
                    <c:if test="${tariffDescriptionIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty tariffDescriptionError}">
                        <span><fmt:message key="${tariffDescriptionError}"/></span>
                    </c:if>
                </p>
                <div class="input-group">
                    <label><fmt:message key="admin.tariffManageSubscriptionFee"/></label>
                    <input type="number" min="0" id="tariffSubscriptionFeeRoubles" name="tariffSubscriptionFeeRoubles"
                           class="small-input"
                           value="${tariffSubscriptionFeeRoubles == null ? 5 : tariffSubscriptionFeeRoubles}">
                    <label for="tariffSubscriptionFeeRoubles" class="small-label">
                        <fmt:message key="admin.tariffManageMoney"/>
                    </label>
                    <input type="number" min="0" max="99" id="tariffSubscriptionFeeKopecks"
                           name="tariffSubscriptionFeeKopecks" class="small-input"
                           value="${tariffSubscriptionFeeKopecks == null ? 0 : tariffSubscriptionFeeKopecks}">
                    <label for="tariffSubscriptionFeeKopecks" class="small-label">
                        <fmt:message key="admin.tariffManageMoneyKopecks"/>
                    </label>
                    <c:if test="${tariffSubscriptionFeeIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty tariffSubscriptionFeeError}">
                        <span><fmt:message key="${tariffSubscriptionFeeError}"/></span>
                    </c:if>
                </p>
                <div class="input-group">
                    <label><fmt:message key="admin.tariffManageCallCost"/></label>
                    <input type="number" min="0" id="tariffCallCostRoubles" name="tariffCallCostRoubles"
                           class="small-input" value="${tariffCallCostRoubles == null ? 0 : tariffCallCostRoubles}">
                    <label for="tariffSubscriptionFeeRoubles" class="small-label">
                        <fmt:message key="admin.tariffManageMoney"/>
                    </label>
                    <input type="number" min="0" max="99" id="tariffCallCostKopecks" name="tariffCallCostKopecks"
                           class="small-input" value="${tariffCallCostKopecks == null ? 10 : tariffCallCostKopecks}">
                    <label for="tariffCallCostKopecks" class="small-label">
                        <fmt:message key="admin.tariffManageMoneyKopecks"/>
                    </label>
                    <c:if test="${tariffCallCostIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty tariffCallCostError}">
                        <span><fmt:message key="${tariffCallCostError}"/></span>
                    </c:if>
                </p>
                <div class="input-group">
                    <label><fmt:message key="admin.tariffManageSmsCost"/></label>
                    <input type="number" min="0" id="tariffSmsCostRoubles" name="tariffSmsCostRoubles"
                           class="small-input" value="${tariffSmsCostRoubles == null ? 0 : tariffSmsCostRoubles}">
                    <label for="tariffSmsCostRoubles" class="small-label">
                        <fmt:message key="admin.tariffManageMoney"/>
                    </label>
                    <input type="number" min="0" max="99" id="tariffSmsCostKopecks" name="tariffSmsCostKopecks"
                           class="small-input" value="${tariffSmsCostKopecks == null ? 10 : tariffSmsCostKopecks}">
                    <label for="tariffSmsCostKopecks" class="small-label">
                        <fmt:message key="admin.tariffManageMoneyKopecks"/>
                    </label>
                    <c:if test="${tariffSmsCostIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty tariffSmsCostError}">
                        <span><fmt:message key="${tariffSmsCostError}"/></span>
                    </c:if>
                </p>
            </div>
            <div>
                <c:choose>
                    <c:when test="${not empty id}">
                        <input type="hidden" name="id" value="${id}">
                        <input type="submit" value="<fmt:message key="admin.tariffManageEditButton" />" class="btn">
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="<fmt:message key="admin.tariffManageAddButton" />" class="btn">
                    </c:otherwise>
                </c:choose>
                <a href="tariff_list.html" class="form-link"><fmt:message key="admin.tariffManageCancel"/></a>
            </div>
        </form>
    </div>
</u:html>