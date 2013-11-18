<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true" %>

<div id="message" class="alert alert-${messageType}" aria-labelledby="myModalLabel">
	<div class="message-body" style="text-align: center;">
		<c:if test="${(not empty messageCode)}">
			<strong><spring:message code="${messageCode}"/></strong>
		</c:if>
		${not empty messageTrailCode ?':':''}
		
		<c:if test="${(not empty messageTrailCode)}">
			<spring:message code="${messageTrailCode}"/>
		</c:if>
		<c:if test="${(not empty notLocalizedMessage)}">
		${notLocalizedMessage}
		</c:if>
		<c:if test="${(not empty operationMessage)}">
			<jsp:include page="../operations/${messageJsp}.jsp" />
		</c:if>
	</div>

</div>