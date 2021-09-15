<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<footer>
    <span><fmt:message key="footer.copyright"/>&nbsp;<span class="epam-blue">&lt;</span><fmt:message key="footer.epam"/><span class="epam-blue">&gt;</span></span>
    <c:if test="${sessionAccount.role == 'SUBSCRIBER'}">
        <span>
            <fmt:message key="footer.balance"/>
            &nbsp;<ctg:money-format balance="${sessionSubscriber.balance}" locale="${locale}"/>
            &nbsp;<fmt:message key="footer.money"/>
        </span>
    </c:if>
</footer>