<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="container">


	<form:form method="post" class="form-horizontal">
		<div class="modal-body">
			<div class="control-group">
				 <label class="control-label" for="flowinput">Consumer ID</label>
				 <div class="controls">
					<input id="flowinput" name="id" type="text" value="${consumer_id}" data-required>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="submitFlowId" class="btn btn-primary">Get Status</button>
		</div>
	</form:form>

</div>


 