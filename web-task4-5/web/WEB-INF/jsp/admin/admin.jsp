<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>


<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="admin.title"/>
<fmt:message var="titlePage" key="admin.mainTitle"/>
<u:html title="${title} : ${titlePage}">
    <div class="main">
        <h2 class="text-center">
            <fmt:message key="admin.mainSentence"/>
        </h2>
        <div class="summary large">
            <table>
                <tr>
                    <td><fmt:message key="admin.mainSubscribersAll"/></td>
                    <td>${countSubscribers}</td>
                </tr>
                <tr>
                    <td><fmt:message key="admin.mainSubscribersNew"/></td>
                    <td>${countNewSubscribers}</td>
                </tr>
                <tr>
                    <td><fmt:message key="admin.mainDebtors"/></td>
                    <td>${countDebtors}</td>
                </tr>
                <tr>
                    <td><fmt:message key="admin.mainDebtorsSum"/></td>
                    <td><ctg:money-format balance="${debtorsSum}" locale="${locale}"/>&nbsp<fmt:message key="admin.mainMoney"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="admin.mainServices"/></td>
                    <td>${countServices}</td>
                </tr>
                <tr>
                    <td><fmt:message key="admin.mainTariffs"/></td>
                    <td>${countTariffs}</td>
                </tr>
                <tr>
                    <td><fmt:message key="admin.mainBills"/></td>
                    <td>${countUnpaidBills}</td>
                </tr>
                <tr>
                    <td><fmt:message key="admin.mainBillsSum"/></td>
                    <td><ctg:money-format balance="${unpaidBillsSum}" locale="${locale}"/>&nbsp<fmt:message key="admin.mainMoney"/></td>
                </tr>
            </table>
            <div class="user-actions">
                <form action="admin.html" method="POST">
                    <input type="hidden" name="new">
                    <input type="submit" value="<fmt:message key="admin.mainBillsToAllSubscribers"/> ➕"
                           class="btn center-btn width-auto no-margin-bottom">
                </form>
            </div>
        </div>
    </div>
</u:html>