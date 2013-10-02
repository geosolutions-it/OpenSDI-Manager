<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="message" class="modal hide fade alert alert-${messageType}"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<button type="button" class="close" data-dismiss="modal">×</button>
	<div class="modal-body" style="text-align: center;">
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
			<jsp:include page="../operations/${messageType}.jsp" />
		</c:if>
	</div>

</div>