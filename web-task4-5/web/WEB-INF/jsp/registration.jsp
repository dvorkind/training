<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>
<%@ page trimDirectiveWhitespaces="true" %>

<fmt:message var="title" key="registration.title"/>
<u:html title="${title}">
    <div class="main">
        <form action="registration.html" method="post" class="reg-form submit-form">
            <h2><fmt:message key="registration.title"/></h2>
            <div class="form-inputs">
                <div class="input-group">
                    <label for="login"><fmt:message key="registration.login"/></label>
                    <input type="text" id="login" name="login" value=${login}>
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
                    <label for="password"><fmt:message key="registration.password"/></label>
                    <input type="password" id="password" name="password" value=${password}>
                    <c:if test="${passwordIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty passwordError}">
                        <fmt:message key="${passwordError}"/>
                    </c:if>
                </p>
                <div class="input-group">
                    <label for="confirmedPassword"><fmt:message key="registration.confirmPassword"/></label>
                    <input type="password" id="confirmedPassword" name="confirmedPassword" value=${confirmedPassword}>
                    <c:if test="${confirmedPasswordIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty confirmedPasswordError}">
                        <fmt:message key="${confirmedPasswordError}"/>
                    </c:if>
                </p>
                <div class="input-group">
                    <label for="firstname"><fmt:message key="registration.firstname"/></label>
                    <input type="text" id="firstname" name="firstname" value=${firstname}>
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
                    <label for="lastname"><fmt:message key="registration.lastname"/></label>
                    <input type="text" id="lastname" name="lastname" value=${lastname}>
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
                    <label for="phoneNumber"><fmt:message key="registration.phoneNumber"/></label>
                    <input type="text" id="phoneNumber" name="phoneNumber" value=${phoneNumber}>
                    <c:if test="${phoneNumberIsValid}">
                        <span class="validation-indicator"> &#10004 </span>
                    </c:if>
                </div>
                <p class="form-message">
                    <c:if test="${not empty phoneNumberError}">
                        <fmt:message key="${phoneNumberError}"/>
                    </c:if>
                </p>
            </div>
            <div>
                <input type="submit" value="<fmt:message key="registration.signUp" />" class="btn">
            </div>
        </form>
    </div>
</u:html>