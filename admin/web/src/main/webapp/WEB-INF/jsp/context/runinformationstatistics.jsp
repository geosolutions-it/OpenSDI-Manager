<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${not empty runInfo}">
	<div class="container geobatch-statistics">
		<div class="container resume">
			<h3>Resume</h3>
			<table class="table table-hover">
				<tr>
					<th>File</th>
					<th>Operation</th>
					<th>Flow UID</th>
					<th>Status</th>
					<th>Excution date</th>
					<th>Check Date</th>
				</tr>
				<tr>
					<td>${runInfo.compositeId[1]}</td>
					<td>${runInfo.compositeId[2]}</td>
					<td>${runInfo.flowUid}</td>
					<td><a class="btn btn-${(runInfo.flowStatus == 'FAIL') ? 'danger' 
											: ((runInfo.flowStatus == 'RUNNING') ? 'warning' : 'success')}" 
											href="../../operationManager/flowstatus/?id=${runInfo.flowUid}">${runInfo.flowStatus}</a></td>
					<td>${runInfo.lastExecution}</td>
					<td>${runInfo.lastCheck}</td>
				</tr>
			</table>
		</div>
		<c:if test="${not empty runInfo.extraInformation and not empty runInfo.extraInformation.trace and not empty runInfo.extraInformation.trace.lines}">
			<jsp:include page="extraruninformationstatistics.jsp" />
		</c:if>
	</div>

</c:if>