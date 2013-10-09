<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="uniqueIDforZip">
	<form:form method="post" class="form-horizontal">
		<div class="modal-body">
			<div class="control-group">
			 <label class="control-label" for="fileName">File Name</label>
			 <div class="controls">
				<input id="fileName" name="fileName" type="text" value="${fileName}" data-required>
			</div>
		</div>
		<div class="modal-footer">
			<button id="createsubmit" class="btn btn-primary" onClick="postData('uniqueIDforZip');">Run</button>
		</div>
	</div>
	</form:form>
</div>
