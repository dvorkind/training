<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="resetPassword.title"/>
<u:html title="${title}">
    <div class="main">
        <form action="reset_password.html" method="post" class="reg-form submit-form">
            <h2>${title}</h2>
            <p class="msg"><fmt:message key="resetPassword.sentenceOne"/></p>
            <p class="msg"><fmt:message key="resetPassword.sentenceTwo"/></p>
            <div class="form-inputs">
                <div class="input-group">
                    <label for="login"><fmt:message key="resetPassword.login"/></label>
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
                    <label for="phoneNumber"><fmt:message key="resetPassword.phoneNumber"/></label>
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
                <input type="submit" value="<fmt:message key="resetPassword.reset" />" class="btn">
                <a href="login.html" class="form-link"><fmt:message key="resetPassword.cancel"/></a>
            </div>
        </form>
    </div>
</u:html>