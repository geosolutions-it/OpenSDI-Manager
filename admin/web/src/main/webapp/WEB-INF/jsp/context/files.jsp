<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h2>File Browser</h2>

	<table class="table table-hover">
		<thead>
			<tr>
				<th>Name</th>
				<th>Size</th>
				<th>LastModified</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			
			<c:if test="${not empty fileBrowser }">
				<c:forEach items="${fileBrowser.files}" var="file">
					<tr>
						<td>${file.name}</td>
						<td>size</td>
						<td>lastModified</td>
						<td>
							<a data-toggle="modal" class="btn action-1" data-target="#action-1"
								data-fileid="${file.name}" href="#">Action-1</a>
							<a data-toggle="modal" class="btn action-2" data-target="#action-2"
								data-fileid="${file.name}" href="#">Action-2</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>
<div id="create" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">Create User</h3>
	</div>
	<form:form method="post" action="create" class="form-horizontal">
		<div class="modal-body">
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button id="createsubmit" class="btn btn-primary">Create</button>
		</div>
	</form:form>
</div>
<div id="edit" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">Edit User</h3>
	</div>
	<form:form method="post" class="form-horizontal">
		<div class="modal-body">
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button id="createsubmit" class="btn btn-primary">Save</button>
		</div>
	</form:form>
</div>
<div id="delete" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">Delete User</h3>
	</div>
	<form:form method="post" class="form-horizontal">
		<div class="modal-body">
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button id="createsubmit" class="btn btn-primary">Delete</button>
		</div>
	</form:form>
</div>
<ul class="pager">
	<c:if test="${page>0}">
	  <li><a href="${page-1}">Previous</a></li>
   	</c:if>
   	<c:if test="${nextPage}">
	  <li><a href="${nextPage}">Next</a></li>
	  </c:if>
</ul>
<script>
	$(function() {

		formUtils.initModalForm('#create');
		formUtils.initModalForm('#edit');
		formUtils.initModalForm('#delete');
		$('.delete-user').on('click', function() {
			var userId = $(this).data('userid');
			formUtils.changeAction('#delete', 'delete/' + userId);
		})
		$('.edit-user').on('click', function() {
			var userId = $(this).data('userid');
			formUtils.changeAction('#edit', 'edit/' + userId);
		})

	});
</script>