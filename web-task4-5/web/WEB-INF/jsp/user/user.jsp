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
                    <fmt:message key="user.hello"/>, ${user.firstname} (${account.login}) (${account.password})!
                </h2>
                <br>
                <table>
                    <tr>
                        <td><fmt:message key="user.profile.firstname"/>: &nbsp</td>
                        <td>${user.firstname}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.profile.lastname"/>: &nbsp</td>
                        <td>${user.lastname}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.profile.phoneNumber"/>: &nbsp</td>
                        <td>${user.phoneNumber}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.profile.balance"/>: &nbsp</td>
                        <td><ctg:money-format balance="${user.balance}"/>&nbsp<fmt:message key="user.money"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.profile.status"/>: &nbsp</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.isBlocked()}">
                                    <fmt:message key="user.profile.blocked"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="user.profile.active"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <form action="user.html" method="post" class="reg-form submit-form">
                <h2 class="text-center">
                    <fmt:message key="registration.congratulation.sentence1"/><br>
                    <fmt:message key="registration.congratulation.sentence2"/><br>
                    <fmt:message key="registration.congratulation.sentence3"/>
                </h2>
                <div>
                    <input type="submit" value="<fmt:message key="user.refresh" />" class="btn">
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</u:html>