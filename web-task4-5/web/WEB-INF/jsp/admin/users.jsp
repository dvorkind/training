<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.users"/>
<u:html title="${title} : ${titlePage}">
    <div class="content-block">
    <div class="menu">
        <jsp:include page="/WEB-INF/jsp/admin/admin_menu.jsp"/>
    </div>
    <c:choose>
        <c:when test="${empty users}">
            <div class="main">
                <h3>
                    <fmt:message key="admin.usersEmpty"/>
                </h3>
            </div>
        </c:when>
        <c:otherwise>

            <div class="main">
                <h3>
                    <fmt:message key="admin.users"/>
                </h3>
                <br>
                <table border="1">
                    <tr>
                        <th width="150"><fmt:message key="admin.userLogin"/></th>
                        <th width="150"><fmt:message key="admin.userFirstname"/></th>
                        <th width="150"><fmt:message key="admin.userLastname"/></th>
                        <th width="150"><fmt:message key="admin.userPhoneNumber"/></th>
                        <th width="150"><fmt:message key="admin.userBalance"/></th>
                        <th width="150"><fmt:message key="admin.userState"/></th>
                        <th width="150"></th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.key}</td>
                            <td>${user.value.firstname}</td>
                            <td>${user.value.lastname}</td>
                            <td>${user.value.phoneNumber}</td>
                            <td align="center"><ctg:money-format balance="${user.value.balance}"/>&nbsp<fmt:message
                                    key="user.money"/></td>
                            <c:choose>
                                <c:when test="${user.value.blocked}">
                                    <td><fmt:message key="admin.userStateBlocked"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:message key="admin.userStateActive"/></td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <form action="edit.html" method="POST">
                                    <input type="hidden" name="id" value="${user.value.id}">
                                    <input type="submit" value="<fmt:message key="admin.userManage"/>" class="btn">
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