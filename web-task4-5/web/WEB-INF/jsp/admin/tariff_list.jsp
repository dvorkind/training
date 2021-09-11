<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.tariffList"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <h2 class="text-center no-margin-bottom">${titlePage}</h2>
        <a href="tariff_manage.html" class="btn center-btn no-margin-bottom">
            <fmt:message key="admin.tariffListAdd"/> ➕
        </a>
        <div class="sort-wrapper">
            <span><fmt:message key="admin.tariffListSortTitle"/></span>
            <form action="tariff_list.html" method="POST" class="select-form no-margin">
                <select name="sort" onchange="submit()" class="btn btn-transparent">
                    <option value="nameUp" ${sort == 'nameUp' ? 'selected' : ''}>
                        <fmt:message key="admin.tariffListSortNameUp"/>
                    </option>
                    <option value="nameDown" ${sort == 'nameDown' ? 'selected' : ''}>
                        <fmt:message key="admin.tariffListSortNameDown"/>
                    </option>
                    <option value="subscriptionFeeUp" ${sort == 'subscriptionFeeUp' ? 'selected' : ''}>
                        <fmt:message key="admin.tariffListSortSubscriptionFeeUp"/>
                    </option>
                    <option value="subscriptionFeeDown" ${sort == 'subscriptionFeeDown' ? 'selected' : ''}>
                        <fmt:message key="admin.tariffListSortSubscriptionFeeDown"/>
                    </option>
                    <option value="callCostUp" ${sort == 'callCostUp' ? 'selected' : ''}>
                        <fmt:message key="admin.tariffListSortCallCostUp"/>
                    </option>
                    <option value="callCostDown" ${sort == 'callCostDown' ? 'selected' : ''}>
                        <fmt:message key="admin.tariffListSortCallCostDown"/>
                    </option>
                    <option value="smsCostUp" ${sort == 'smsCostUp' ? 'selected' : ''}>
                        <fmt:message key="admin.tariffListSortSmsCostUp"/>
                    </option>
                    <option value="smsCostDown" ${sort == 'smsCostDown' ? 'selected' : ''}>
                        <fmt:message key="admin.tariffListSortSmsCostDown"/>
                    </option>
                </select>
            </form>
        </div>
        <table class="data-table">
            <thead>
            <tr>
                <th width="150"><fmt:message key="admin.tariffListName"/></th>
                <th width="300"><fmt:message key="admin.tariffListDescription"/></th>
                <th width="150"><fmt:message key="admin.tariffListSubscriptionFee"/></th>
                <th width="150"><fmt:message key="admin.tariffListCallCost"/></th>
                <th width="150"><fmt:message key="admin.tariffListSmsCost"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="tariff" items="${tariffs}">
                <tr>
                    <td>${tariff.name}</td>
                    <td class="pre-wrap">${tariff.description}</td>
                    <td>
                        <ctg:money-format balance="${tariff.subscriptionFee}" locale="${locale}"/>
                        &nbsp<fmt:message key="admin.tariffListMoney"/>
                    </td>
                    <td>
                        <ctg:money-format balance="${tariff.callCost}" locale="${locale}"/>
                        &nbsp<fmt:message key="admin.tariffListMoney"/>
                    </td>
                    <td>
                        <ctg:money-format balance="${tariff.smsCost}" locale="${locale}"/>
                        &nbsp<fmt:message key="admin.tariffListMoney"/>
                    </td>
                    <td class="button-cell">
                        <form action="tariff_manage.html" method="POST">
                            <input type="hidden" name="id" value="${tariff.id}">
                            <input type="submit" value="<fmt:message key="admin.tariffListEdit"/> 📝"
                                   class="btn btn-small btn-in-cell">
                        </form>
                        <form action="tariff_delete.html" method="POST">
                            <input type="hidden" name="id" value="${tariff.id}">
                            <c:if test="${fn:length(tariffs) != 1}">
                                <input type="submit" value="<fmt:message key="admin.tariffListDelete"/> ❌"
                                       class="btn btn-small btn-in-cell">
                            </c:if>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</u:html>