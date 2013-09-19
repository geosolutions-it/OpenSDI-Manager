<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
			
			<c:if test="${not empty fileBrowser.files }">
				<c:forEach items="${fileBrowser.files}" var="file">
					<tr>
						<td>${file.name}</td>
						<td>${file.size} Bytes</td>
						<td>${file.lastModified}</td>
						<td>
							<c:if test="${fn:endsWith(file.name, '.zip')}">
								<a data-toggle="modal" class="btn action-1" data-target="#action-1"
									data-fileid="${file.name}" href="action-1/${file.name}">Zip2pg</a>
							</c:if>
							<c:if test="${fn:endsWith(file.name, '.shp')}">
								<a data-toggle="modal" class="btn action-2" data-target="#action-2"
									data-fileid="${file.name}" href="javascript:void(0);">Shp2pg</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty fileBrowser.files }">
				<tr>
					<td>No files to show</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
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
<div id="action-1" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
		<h3 id="myModalLabel">Zip2pg</h3>
	</div>
	<form:form method="post" class="form-horizontal">
		<div class="modal-body">
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button id="createsubmit" class="btn btn-primary">Run</button>
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

<script>
	$(function() {

		formUtils.initModalForm('#create');
		formUtils.initModalForm('#action-1');
		formUtils.initModalForm('#delete');
		$('.action-1').on('click', function() {
			var fileId = $(this).data('fileid');
			alert('Clicked action-1 on '+fileId);
			formUtils.changeAction('#action-1', 'action-1/' + fileId);
		})
		$('.action-2').on('click', function() {
			var fileId = $(this).data('fileid');
			alert('Clicked action-2 on '+fileId);
			formUtils.changeAction('#delete', 'delete/' + fileId);
		})

	});
</script>