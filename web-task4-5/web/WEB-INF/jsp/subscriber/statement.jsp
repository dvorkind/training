<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.statementTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <form action="statement.html" method="post" class="reg-form submit-form">
            <h2>${titlePage}</h2>
            <p class="msg"><fmt:message key="subscriber.statementMessageOne"/></p>
            <p class="msg"><fmt:message key="subscriber.statementMessageTwo"/></p>
            <div class="form-inputs">
                <div class="input-group">
                    <label for="before"><fmt:message key="subscriber.statementBefore"/></label>
                    <input type="date" name="before" id="before" value="${before}">
                    <label for="after"><fmt:message key="subscriber.statementAfter"/></label>
                    <input type="date" name="after" id="after" value="${after}">
                </div>
            </div>
            <p class="form-message">
                <c:if test="${not empty error}">
                    <span><fmt:message key="${error}"/></span>
                </c:if>
            </p>
            <input type="hidden" name="show">
            <input type="submit" value="<fmt:message key="subscriber.statementShow"/>" class="btn">
        </form>

        <c:if test="${not empty actions}">
            <table class="data-table">
                <thead>
                <tr>
                    <th width="300"><fmt:message key="subscriber.statementAction"/></th>
                    <th width="150"><fmt:message key="subscriber.statementDate"/></th>
                    <th width="150"><fmt:message key="subscriber.statementSum"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="action" items="${actions}">
                    <tr>
                        <td><fmt:message key="${action.action.name}"/></td>
                        <td><ctg:date-format dateTime="${action.date}" locale="${locale}"/></td>
                        <td>
                            <ctg:money-format balance="${action.sum}" locale="${locale}"/>
                            &nbsp<fmt:message key="subscriber.statementMoney"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</u:html>