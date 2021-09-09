<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="changePassword.title"/>
<u:html title="${title}">
    <div class="main">
        <form action="change_password.html" method="post" class="reg-form submit-form">
            <h2>${title}</h2>
            <div class="form-inputs">
                <div class="input-group">
                    <label for="oldPassword"><fmt:message key="changePassword.oldPassword"/></label>
                    <input type="password" id="oldPassword" name="oldPassword" value=${oldPassword}>
                    <c:if test="${oldPasswordIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty oldPasswordError}">
                        <span><fmt:message key="${oldPasswordError}"/></span>
                    </c:if>
                </p>
                <div class="input-group">
                    <label for="newPassword"><fmt:message key="changePassword.newPassword"/></label>
                    <input type="password" id="newPassword" name="newPassword" value=${newPassword}>
                    <c:if test="${newPasswordIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty newPasswordError}">
                        <fmt:message key="${newPasswordError}"/>
                    </c:if>
                </p>
                <div class="input-group">
                    <label for="confirmedNewPassword"><fmt:message
                            key="changePassword.confirmNewPassword"/></label>
                    <input type="password" id="confirmedNewPassword" name="confirmedNewPassword"
                           value=${confirmedNewPassword}>
                    <c:if test="${confirmedNewPasswordIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty confirmedNewPasswordError}">
                        <fmt:message key="${confirmedNewPasswordError}"/>
                    </c:if>
                </p>
            </div>
            <div>
                <input type="submit" value="<fmt:message key="changePassword.button" />" class="btn">
                <a href="login.html" class="form-link"><fmt:message key="changePassword.cancel"/></a>
            </div>
        </form>
    </div>
</u:html>