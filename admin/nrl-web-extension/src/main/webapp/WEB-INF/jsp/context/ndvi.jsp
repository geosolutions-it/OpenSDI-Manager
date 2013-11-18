<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="uniqueIDforZip">
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
</div>