<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
	<h2>Users</h2>

	<a data-toggle="modal" class="btn btn-success pull-right" href="create"
		data-target="#create">Create User</a>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>#</th>
				<th>name</th>
				<th>role</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.role}</td>

					<td>
							<a data-toggle="modal" class="btn edit-user" data-target="#edit"
								data-userid="${user.id}" href="edit/${user.id}">Edit</a>
						 	<c:if test="${(user.name != null) && (username != user.name)}">
							<a data-toggle="modal" data-target="#delete"
								class="btn delete-user" data-userid="${user.id}"
								href="delete/${user.id}">Delete</a>
							</c:if>
					</td>

				</tr>
			</c:forEach>
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