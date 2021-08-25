<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.tariffs"/>
<u:html title="${title} : ${titlePage}">
    <nav>
        <jsp:include page="/WEB-INF/jsp/admin/admin_menu.jsp"/>
    </nav>
    <div class="main">
        <h2 class="text-center">
            <fmt:message key="admin.tariffs"/>
        </h2>
        <a href="tariff_add.html" class="btn center-btn">
            <fmt:message key="admin.tariffAdd"/> ➕
        </a>
        <table class="data-table">
            <thead>
            <tr>
                <th width="150"><fmt:message key="admin.tariffName"/></th>
                <th width="150"><fmt:message key="admin.tariffDescription"/></th>
                <th width="150"><fmt:message key="admin.tariffSubscriptionFee"/></th>
                <th width="150"><fmt:message key="admin.tariffCallCost"/></th>
                <th width="150"><fmt:message key="admin.tariffSmsCost"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="tariff" items="${tariffs}">
                <tr>
                    <td>${tariff.name}</td>
                    <td class="pre-wrap">${tariff.description}</td>
                    <td>
                        <ctg:money-format balance="${tariff.subscriptionFee}"/>&nbsp<fmt:message key="admin.tariffMoney"/>
                    </td>
                    <td>
                        <ctg:money-format balance="${tariff.callCost}"/>&nbsp<fmt:message key="admin.tariffMoney"/>
                    </td>
                    <td>
                        <ctg:money-format balance="${tariff.smsCost}"/>&nbsp<fmt:message key="admin.tariffMoney"/>
                    </td>
                    <td class="button-cell">
                        <form action="tariff_edit.html" method="POST">
                            <input type="hidden" name="id" value="${tariff.id}">
                            <input type="submit" value="<fmt:message key="admin.tariffEdit"/> 📝"
                                   class="btn btn-small btn-in-cell">
                        </form>
                        <form action="tariff_delete.html" method="POST">
                            <input type="hidden" name="id" value="${tariff.id}">
                            <input type="submit" value="<fmt:message key="admin.tariffDelete"/> ❌"
                                   class="btn btn-small btn-in-cell">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</u:html>