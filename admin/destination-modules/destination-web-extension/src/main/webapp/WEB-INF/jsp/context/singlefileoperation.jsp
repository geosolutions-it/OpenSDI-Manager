<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="uniqueIDforZip">
<c:choose>
<c:when test="${not empty runInfo}">
	<div class="modal-body">
		<form:form method="post" class="form-horizontal">
		<div class="control-group">
			 <label class="control-label" for="fileName">File Name</label>
			 <div class="controls">
				<input id="fileName" name="fileName" type="text" value="${fileName}" data-required>
			</div>
			<label class="control-label" for="lastExecution">Execution date</label>
			<div class="controls">
			 	<input id="lastExecution" name="lastExecution" type="text" value="${runInfo.lastExecution}" readonly />
			</div>
			<label class="control-label" for="status">Status</label>
			<div class="controls">
			 	<a id="status" class="btn btn-${(runInfo.flowStatus == 'FAIL') ? 'danger' 
											: ((runInfo.flowStatus == 'RUNNING') ? 'warning' : 'success')}" 
											href="../../operationManager/flowstatus/?id=${runInfo.flowUid}">${runInfo.flowStatus}</a>
			</div>
			<div class="help-block">Do you really want to execute it again?</div>
		</div>
		</form:form>
		<div class="modal-footer">
			<button id="createsubmit" class="btn btn-primary" onClick="ajaxRequest('uniqueIDforZip', '#command');">Replace</button>
		</div>
	</div>
</c:when>
<c:otherwise>
	<div class="modal-body">
		<form:form method="post" class="form-horizontal">
		<div class="control-group">
			 <label class="control-label" for="fileName">File Name</label>
			 <div class="controls">
				<input id="fileName" name="fileName" type="text" value="${fileName}" data-required>
			</div>
		</div>
		</form:form>
		<div class="modal-footer">
				<button id="createsubmit" class="btn btn-primary" onClick="ajaxRequest('uniqueIDforZip', '#command');">Run</button>
		</div>
	</div>
</c:otherwise>
</c:choose>
</div>

