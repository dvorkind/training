<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.newUsers"/>
<u:html title="${title} : ${titlePage}">
    <nav>
        <jsp:include page="/WEB-INF/jsp/admin/admin_menu.jsp"/>
    </nav>
    <c:choose>
        <c:when test="${empty users}">
            <div class="main">
                <h2 class="text-center">
                    <fmt:message key="admin.newUsersEmpty"/>
                </h2>
            </div>
        </c:when>
        <c:otherwise>
            <div class="main">
                <h2 class="text-center">
                    <fmt:message key="admin.newUsers"/>
                </h2>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th width="150"><fmt:message key="admin.userLogin"/></th>
                        <th width="150"><fmt:message key="admin.userFirstname"/></th>
                        <th width="150"><fmt:message key="admin.userLastname"/></th>
                        <th width="150"><fmt:message key="admin.userPhoneNumber"/></th>
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
                            <td class="button-cell">
                                <form action="users_new.html" method="POST">
                                    <input type="hidden" name="id" value="${user.value.id}">
                                    <input type="submit" value="<fmt:message key="admin.userActivate"/> ✅" class="btn btn-small">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</u:html>