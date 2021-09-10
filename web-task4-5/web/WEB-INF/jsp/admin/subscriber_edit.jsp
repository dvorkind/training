<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.subscriberEditTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="subscriber_edit.html" method="post" class="reg-form submit-form">
            <h2>${titlePage}</h2>
            <div class="form-inputs">
                <div class="input-group">
                    <label for="login"><fmt:message key="admin.subscriberEditLogin"/></label>
                    <c:if test="${login != null}">
                        <input type="text" id="login" name="login" value=${login}>
                    </c:if>
                    <c:if test="${login == null}">
                        <input type="text" id="login" name="login" value=${account.login}>
                    </c:if>
                    <c:if test="${loginIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty loginError}">
                        <span><fmt:message key="${loginError}"/></span>
                    </c:if>
                </p>
                <div class="input-group">
                    <label for="firstname"><fmt:message key="admin.subscriberEditFirstname"/></label>
                    <c:if test="${firstname != null}">
                        <input type="text" id="firstname" name="firstname" value=${firstname}>
                    </c:if>
                    <c:if test="${firstname == null}">
                        <input type="text" id="firstname" name="firstname" value=${subscriber.firstname}>
                    </c:if>
                    <c:if test="${firstnameIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty firstnameError}">
                        <fmt:message key="${firstnameError}"/>
                    </c:if>
                </p>
                <div class="input-group">
                    <label for="lastname"><fmt:message key="admin.subscriberEditLastname"/></label>
                    <c:if test="${lastname != null}">
                        <input type="text" id="lastname" name="lastname" value=${lastname}>
                    </c:if>
                    <c:if test="${lastname == null}">
                        <input type="text" id="lastname" name="lastname" value=${subscriber.lastname}>
                    </c:if>
                    <c:if test="${lastnameIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty lastnameError}">
                        <fmt:message key="${lastnameError}"/>
                    </c:if>
                </p>
                <div class="input-group">
                    <label for="phoneNumber"><fmt:message key="admin.subscriberEditPhoneNumber"/></label>
                    <c:if test="${phoneNumber != null}">
                        <input type="text" id="phoneNumber" name="phoneNumber" value=${phoneNumber}>
                    </c:if>
                    <c:if test="${phoneNumber == null}">
                        <input type="text" id="phoneNumber" name="phoneNumber" value=${subscriber.phoneNumber}>
                    </c:if>
                    <c:if test="${phoneNumberIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty phoneNumberError}">
                        <fmt:message key="${phoneNumberError}"/>
                    </c:if>
                </p>
                <p class="msg"><fmt:message key="admin.subscriberEditTariff"/></p>
                <select name="newTariff" class="btn center-btn">
                    <c:forEach var="tariff" items="${tariffs}">
                        <c:if test="${tariff.id == subscriber.tariff}">
                            <option value="${tariff.id}" selected>${tariff.name}</option>
                        </c:if>
                        <c:if test="${tariff.id != subscriber.tariff}">
                            <option value="${tariff.id}">${tariff.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <fmt:message key="admin.subscriberEditState"/>:&nbsp;
                <c:choose>
                    <c:when test="${subscriber.blocked}">
                        <fmt:message key="admin.subscriberEditBlocked"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="admin.subscriberEditActive"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <div>
                <input type="hidden" name="id" value="${subscriber.id}">
                <input type="hidden" name="confirmation">
                <input type="submit" value="<fmt:message key="admin.subscriberEditSave" /> 💾" class="btn">
                <a href="subscribers_all.html" class="form-link"><fmt:message key="admin.subscriberEditCancel"/></a>
            </div>
        </form>
        <form action="subscriber_edit.html" method="POST">
            <input type="hidden" name="id" value="${subscriber.id}">
            <input type="hidden" name="block" value="${subscriber.id}">
            <c:choose>
                <c:when test="${subscriber.blocked}">
                    <input type="submit" value="<fmt:message key="admin.subscriberEditUnblock"/>  &#10004" class="btn">
                </c:when>
                <c:otherwise>
                    <input type="submit" value="<fmt:message key="admin.subscriberEditBlock"/> ⛔" class="btn">
                </c:otherwise>
            </c:choose>
        </form>
        <form action="subscriber_edit.html" method="POST">
            <input type="hidden" name="id" value="${subscriber.id}">
            <input type="hidden" name="resetPassword" value="${subscriber.id}">
            <input type="submit" value="<fmt:message key="admin.subscriberEditResetPassword"/>  🔑" class="btn">
        </form>
        <form action="subscriber_bills.html" method="POST">
            <input type="hidden" name="subscriberId" value="${subscriber.id}">
            <input type="submit" value="<fmt:message key="admin.subscriberEditBills"/>" class="btn">
        </form>
    </div>
</u:html>