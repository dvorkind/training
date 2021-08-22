<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>
<fmt:message var="title" key="login.title"/>

<u:html title="${title}">
    <form action="login.html" method="post" class="submit-form">
        <h2><fmt:message key="login.authentication"/></h2>
        <div class="form-inputs">
            <div class="input-group">
                <label for="login"><fmt:message key="login.login"/></label>
                <input type="text" id="login" name="login" value="${login}">
            </div>
            <div class="input-group">
                <label for="password"><fmt:message key="login.password"/></label>
                <input type="password" id="password" name="password">
            </div>
        </div>
        <p class="form-message">
            <c:if test="${not empty error}">
                <span><fmt:message key="${error}"/></span>
            </c:if>
        </p>
        <div>
            <input type="submit" value="<fmt:message key="login.sign.in" />" class="btn">
            <a href="registration.html" class="form-link"><fmt:message key="login.sign.up"/></a>
        </div>
    </form>
</u:html>