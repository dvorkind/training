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
<fmt:message var="titlePage" key="admin.tariffs"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <h2 class="text-center no-margin-bottom">${titlePage}</h2>
        <a href="tariff_manage.html" class="btn center-btn no-margin-bottom">
            <fmt:message key="admin.tariffAdd"/> ➕
        </a>
        <div class="sort-wrapper">
            <span><fmt:message key="admin.tariffSortTitle"/></span>
            <form action="tariff_list.html" method="POST" class="select-form no-margin">
                <select name="sort" onchange="submit()" class="btn btn-transparent">
                    <option value="nameUp" ${sort == 'nameUp' ? 'selected' : ''}><fmt:message key="admin.tariffSortNameUp"/></option>
                    <option value="nameDown" ${sort == 'nameDown' ? 'selected' : ''}><fmt:message key="admin.tariffSortNameDown"/></option>
                    <option value="subscriptionFeeUp" ${sort == 'subscriptionFeeUp' ? 'selected' : ''}><fmt:message key="admin.tariffSortSubscriptionFeeUp"/></option>
                    <option value="subscriptionFeeDown" ${sort == 'subscriptionFeeDown' ? 'selected' : ''}><fmt:message key="admin.tariffSortSubscriptionFeeDown"/></option>
                    <option value="callCostUp" ${sort == 'callCostUp' ? 'selected' : ''}><fmt:message key="admin.tariffSortCallCostUp"/></option>
                    <option value="callCostDown" ${sort == 'callCostDown' ? 'selected' : ''}><fmt:message key="admin.tariffSortCallCostDown"/></option>
                    <option value="smsCostUp" ${sort == 'smsCostUp' ? 'selected' : ''}><fmt:message key="admin.tariffSortSmsCostUp"/></option>
                    <option value="smsCostDown" ${sort == 'smsCostDown' ? 'selected' : ''}><fmt:message key="admin.tariffSortSmsCostDown"/></option>
                </select>
            </form>
        </div>
        <table class="data-table">
            <thead>
            <tr>
                <th width="150"><fmt:message key="admin.tariffName"/></th>
                <th width="300"><fmt:message key="admin.tariffDescription"/></th>
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
                        <ctg:money-format balance="${tariff.subscriptionFee}" locale="${locale}"/>&nbsp<fmt:message
                            key="admin.tariffMoney"/>
                    </td>
                    <td>
                        <ctg:money-format balance="${tariff.callCost}" locale="${locale}"/>&nbsp<fmt:message key="admin.tariffMoney"/>
                    </td>
                    <td>
                        <ctg:money-format balance="${tariff.smsCost}" locale="${locale}"/>&nbsp<fmt:message key="admin.tariffMoney"/>
                    </td>
                    <td class="button-cell">
                        <form action="tariff_manage.html" method="POST">
                            <input type="hidden" name="id" value="${tariff.id}">
                            <input type="submit" value="<fmt:message key="admin.tariffEdit"/> 📝"
                                   class="btn btn-small btn-in-cell">
                        </form>
                        <form action="tariff_delete.html" method="POST">
                            <input type="hidden" name="id" value="${tariff.id}">
                            <c:if test="${fn:length(tariffs) != 1}">
                                <input type="submit" value="<fmt:message key="admin.tariffDelete"/> ❌"
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