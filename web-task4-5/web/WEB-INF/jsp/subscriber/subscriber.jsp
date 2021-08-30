<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/money.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="subscriber.title"/>
<fmt:message var="titlePage" key="subscriber.main"/>
<u:html title="${title} : ${titlePage}">
    <c:choose>
        <c:when test="${subscriber.registered}">
            <div class="main">
                <h2 class="text-center">
                    <fmt:message key="subscriber.hello"/>, ${subscriber.firstname}!
                </h2>
                <table>
                    <tr>
                        <td><fmt:message key="subscriber.profileFirstname"/>: &nbsp</td>
                        <td>${subscriber.firstname}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="subscriber.profileLastname"/>: &nbsp</td>
                        <td>${subscriber.lastname}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="subscriber.profilePhoneNumber"/>: &nbsp</td>
                        <td>${subscriber.phoneNumber}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="subscriber.profileTariff"/>: &nbsp</td>
                        <td>${tariffName}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="subscriber.profileBalance"/>: &nbsp</td>
                        <td><ctg:money-format balance="${subscriber.balance}"/>&nbsp<fmt:message key="subscriber.money"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="subscriber.profileStatus"/>: &nbsp</td>
                        <td>
                            <c:choose>
                                <c:when test="${subscriber.blocked}">
                                    <fmt:message key="subscriber.profileBlocked"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="subscriber.profileActive"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="main">
                <h2 class="text-center">
                    <fmt:message key="registration.congratulationSentence1"/>
                </h2>
                <p class="msg"><fmt:message key="registration.congratulationSentence2"/></p>
                <p class="msg"><fmt:message key="registration.congratulationSentence3"/></p>
                <a href="subscriber.html" class="btn center-btn">
                    <fmt:message key="subscriber.refresh"/>
                </a>
            </div>
        </c:otherwise>
    </c:choose>
</u:html>