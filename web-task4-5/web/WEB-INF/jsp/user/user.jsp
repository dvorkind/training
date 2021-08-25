<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="user.title"/>
<fmt:message var="titlePage" key="user.main"/>
<u:html title="${title} : ${titlePage}">
    <c:choose>
        <c:when test="${user.isRegistered()}">
            <nav>
                <jsp:include page="/WEB-INF/jsp/user/user_menu.jsp"/>
            </nav>
            <div class="main">
                <h2 class="text-center">
                    <fmt:message key="user.hello"/>, ${user.firstname} (${sessionAccount.login}) (${sessionAccount.password})!
                </h2>
                <table>
                    <tr>
                        <td><fmt:message key="user.profileFirstname"/>: &nbsp</td>
                        <td>${user.firstname}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.profileLastname"/>: &nbsp</td>
                        <td>${user.lastname}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.profilePhoneNumber"/>: &nbsp</td>
                        <td>${user.phoneNumber}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.profileTariff"/>: &nbsp</td>
                        <td>${tariffName}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.profileBalance"/>: &nbsp</td>
                        <td><ctg:money-format balance="${user.balance}"/>&nbsp<fmt:message key="user.money"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.profileStatus"/>: &nbsp</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.isBlocked()}">
                                    <fmt:message key="user.profileBlocked"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="user.profileActive"/>
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
                <a href="user.html" class="btn center-btn">
                    <fmt:message key="user.refresh"/>
                </a>
            </div>
        </c:otherwise>
    </c:choose>
</u:html>