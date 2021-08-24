<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.users"/>
<u:html title="${title} : ${titlePage}">
    <nav>
        <jsp:include page="/WEB-INF/jsp/admin/admin_menu.jsp"/>
    </nav>
    <div class="main">
        <c:choose>
            <c:when test="${empty users}">
                <h2 class="text-center">
                    <fmt:message key="admin.usersEmpty"/>
                </h2>
            </c:when>
            <c:otherwise>
                <h2 class="text-center">
                    <fmt:message key="admin.users"/>
                </h2>
                <br>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.userLogin"/></th>
                        <th width="150"><fmt:message key="admin.userFirstname"/></th>
                        <th width="150"><fmt:message key="admin.userLastname"/></th>
                        <th width="150"><fmt:message key="admin.userPhoneNumber"/></th>
                        <th width="150"><fmt:message key="admin.userBalance"/></th>
                        <th width="150"><fmt:message key="admin.userState"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.key}</td>
                            <td>${user.value.firstname}</td>
                            <td>${user.value.lastname}</td>
                            <td>${user.value.phoneNumber}</td>
                            <td><ctg:money-format balance="${user.value.balance}"/>&nbsp<fmt:message
                                    key="user.money"/></td>
                            <c:choose>
                                <c:when test="${user.value.blocked}">
                                    <td><fmt:message key="admin.userStateBlocked"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:message key="admin.userStateActive"/></td>
                                </c:otherwise>
                            </c:choose>
                            <td class="button-cell">
                                <form action="edit.html" method="POST">
                                    <input type="hidden" name="id" value="${user.value.id}">
                                    <input type="submit" value="<fmt:message key="admin.userManage"/> 📝"
                                           class="btn btn-small">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</u:html>