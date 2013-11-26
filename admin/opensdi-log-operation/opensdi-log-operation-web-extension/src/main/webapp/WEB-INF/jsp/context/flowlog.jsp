<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="osdim" uri="../../tld/osdim.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>

<c:set var="logUrl"><c:url value='/operationManager/flowlog' /></c:set>

<c:set var="thisUrl">
<c:choose>
<c:when test="${empty id}">${logUrl}</c:when>
<c:otherwise>${logUrl}?id=${id}</c:otherwise>
</c:choose>
</c:set>

<c:choose>
<c:when test="${empty fileName}">
	<c:set var="pageTitle" value="Run history " />
	<c:set var="refreshUrl">${logUrl}?update=true</c:set>
	<c:set var="returnButton"></c:set>
</c:when>
<c:otherwise>
	<c:set var="pageTitle" value="${fileName} history " />
	<c:choose>
	<c:when test="${empty returnUrl}">
		<c:set var="refreshUrl">${logUrl}?id=${id}&update=true&returnUrl=${thisUrl}</c:set>
		<c:set var="returnButton"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="refreshUrl">${logUrl}?id=${id}&update=true&returnUrl=${returnUrl}</c:set>
		<c:set var="returnButton"><span class="return-span"><a class="btn btn-return" href="${returnUrl}"><i class="icon-arrow-left" title="Return to previous page"></i></a></span></c:set>
	</c:otherwise>
	</c:choose>
</c:otherwise>
</c:choose>

<c:set var="refreshButton"><span class="refresh-span"><a class="btn btn-success" href="${refreshUrl}"><i class="icon-refresh icon-white" title="Refresh status of each run"></i></a></span></c:set>


<div class="container" id="log_${containerId}">
	<h2>${pageTitle}<div class="pull-right">${refreshButton}${returnButton}</div></h2>
	<table class="table table-hover">
		<thead>
			<tr>
				<c:choose>
				<c:when test="${empty fileName }">
				<th>Name</th>
				<c:if test="${showPath}">
				<th>Path</th>
				</c:if>
				<th>Operation</th>
				<th>Last execution</th>
				<th>Last check</th>
				<th>Status</th>
				<th>History</th>
				</c:when>
				<c:otherwise>
				<th>Last execution</th>
				<th>Last check</th>
				<th>Status</th>
				</c:otherwise>
				</c:choose>
			</tr>
		</thead>
		<tbody>
			<c:choose>
			<c:when test="${not empty runInformation }">
			<c:forEach items="${runInformation}" var="logFile">
			<tr>
				<c:choose>
				<c:when test="${empty fileName }">
				<td>${logFile.compositeId[1]}</td>
				<c:if test="${showPath}">
				<td>${logFile.compositeId[0]}</td>
				</c:if>
				<td>${logFile.compositeId[2]}</td>
				<td><span class="fileRunDate">${logFile.lastExecution}</span></td>
				<td><span class="fileCheckDate">${logFile.lastCheck}</span></td>
				<td><span class="fileStatus status_${logFile.flowStatus}"><a class="btn btn-${(logFile.flowStatus == 'FAIL') ? 'danger' 
											: ((logFile.flowStatus == 'RUNNING') ? 'warning' : 'success')}" 
						  href="../flowstatus/?id=${logFile.flowUid}" title="Show run log">${logFile.flowStatus}</a></span></td>
				<td><a class="btn btn-history" href="?id=${logFile.internalUid}&returnUrl=${thisUrl}" title="Show file history"><i class="icon-time"></i></a></td>
				</c:when>
				<c:otherwise>
				<td><span class="fileRunDate">${logFile.lastExecution}</span></td>
				<td><span class="fileCheckDate">${logFile.lastCheck}</span></td>
				<td><span class="fileStatus status_${logFile.flowStatus}"><a class="btn btn-${(logFile.flowStatus == 'FAIL') ? 'danger' 
											: ((logFile.flowStatus == 'RUNNING') ? 'warning' : 'success')}" 
						  href="../flowstatus/?id=${logFile.flowUid}" title="Show run log">${logFile.flowStatus}</a></span></td>
				</c:otherwise>
				</c:choose>
			</tr>
			</c:forEach>
			</c:when>
			<c:otherwise>
			<tr>
				<td>No log to show</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</c:otherwise>
			</c:choose>
		</tbody>
	</table>