<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.newUsers"/>
<u:html title="${title} : ${titlePage}">
    <div class="content-block">
    <div class="menu">
        <jsp:include page="/WEB-INF/jsp/admin/admin_menu.jsp"/>
    </div>
    <c:choose>
        <c:when test="${empty users}">
            <div class="main">
                <h3>
                    <fmt:message key="admin.newUsersEmpty"/>
                </h3>
            </div>
        </c:when>
        <c:otherwise>
            <div class="main">
                <h3>
                    <fmt:message key="admin.newUsers"/>
                </h3>
                <br>
                <table border="1">
                    <tr>
                        <th width="150"><fmt:message key="admin.userLogin"/></th>
                        <th width="150"><fmt:message key="admin.userFirstname"/></th>
                        <th width="150"><fmt:message key="admin.userLastname"/></th>
                        <th width="150"><fmt:message key="admin.userPhoneNumber"/></th>
                        <th width="120"></th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.key}</td>
                            <td>${user.value.firstname}</td>
                            <td>${user.value.lastname}</td>
                            <td>${user.value.phoneNumber}</td>
                            <td>
                                <form action="new_users.html" method="POST">
                                    <input type="hidden" name="id" value="${user.value.id}">
                                    <input type="submit" value="<fmt:message key="admin.userActivate"/>" class="btn">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            </div>
        </c:otherwise>
    </c:choose>
</u:html>