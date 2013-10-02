<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true" %>
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
			<c:if test="${not empty directory }">
				<tr>
					<td><a href="?d=${directoryBack}">.. /</a></td>
					<td>Folder</td>
					<td></td>
					<td>
						<a class="btn" href="?d=${directoryBack}">Up one folder</a>
					</td>
				</tr>				
			</c:if>
			<c:if test="${not empty fileBrowser.files }">
				<c:forEach items="${fileBrowser.files}" var="file">
					<tr>
						<c:if test="${not file.isDirectory}">
						<td>${file.name}</td>
						</c:if>
						<c:if test="${file.isDirectory}">
						<td><a href="?d=${directory}/${file.name}">${file.name}/</a></td>
						</c:if>
						<c:if test="${not file.isDirectory }"><td>${file.size} Bytes</td></c:if>
						<c:if test="${file.isDirectory }"><td>Folder</td></c:if>
						<td>${file.lastModified}</td>
						<td>
							<c:if test="${file.isDirectory}">
								<a class="btn" href="?d=${directory}/${file.name}">Open ${file.name} folder</a>
							</c:if>
							<c:forEach items="${operations}" var="entry">
								<c:set var="fileext" value=".${entry.key}" /> 
								<c:if test="${fn:endsWith(file.name, fileext)}">
									<a data-toggle="modal" class="btn ${fn:toLowerCase(entry.value.name)}" data-target="#${fn:toLowerCase(entry.value.name)}"
										data-fileid="${file.name}" href="../${entry.value.RESTPath}/${file.name}">${entry.value.name}</a>
								</c:if>
							</c:forEach>
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
	<form:form method="post" modelAttribute="uploadFile" enctype="multipart/form-data">
	 
	    <p>Select files to upload. Press Add button to add more file inputs.</p>
	    <table id="fileTable">
	    	<tr>
	            <td>
					<input  name="files[0]" type="file" />
				</td>
	        </tr>
	    </table>
	    <br/>
	    <input id="addFile" type="button" value="Add File" class="btn" />
		<input type="submit" value="Upload" class="btn btn-primary "/>
	</form:form>
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

<c:forEach items="${operations}" var="entry">
<!-- TODO: create only if they are used in the file list! -->
<div id="${fn:toLowerCase(entry.value.name)}" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">${entry.value.name}</h3>
	</div>
	<form:form method="post" class="form-horizontal">
		<div class="modal-body">
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button id="createsubmit" class="btn btn-primary">${entry.value.name}</button>
		</div>
	</form:form>
</div></c:forEach>

<script>
	$(function() {

		formUtils.initModalForm('#create');
		formUtils.initModalForm('#action-1');
		formUtils.initModalForm('#delete');
	<c:forEach items="${operations}" var="entry">
		formUtils.initModalForm('#${fn:toLowerCase(entry.value.name)}');
		$('.${fn:toLowerCase(entry.value.name)}').on('click', function() {
			var fileId = $(this).data('fileid');
			formUtils.changeAction('#${fn:toLowerCase(entry.value.name)}', '../${entry.value.RESTPath}/' + fileId);
		})
	</c:forEach>
		$('.action-1').on('click', function() {
			var fileId = $(this).data('fileid');
			//alert('Clicked action-1 on '+fileId);
			formUtils.changeAction('#action-1', 'action-1/' + fileId);
		})
		$('.action-2').on('click', function() {
			var fileId = $(this).data('fileid');
			alert('Clicked action-2 on '+fileId);
			formUtils.changeAction('#delete', 'delete/' + fileId);
		})

	});

	function remrow(id){
		$('#row_'+ id).remove();
		if(id>1){
			$('#rembtn_'+ (id-1)).show();
		}
	}
	
	$(document).ready(function() {
	    $(":file").filestyle();
	    //add more file components if Add is clicked
	    $('#addFile').click(function() {
	        var fileIndex = $('#fileTable tr').children().length;
	        $(".remrow").hide();
	        $('#fileTable').append(
	                '<tr id="row_'+ fileIndex +'"><td>'+
	                '   <input type="file" name="files['+ fileIndex +']" /> <input id="rembtn_'+ fileIndex +'" type="button" value="Remove" class="remrow btn btn-danger" name="clear'+ fileIndex +'" onClick="remrow('+ fileIndex +')"/>'+
	                '</td></tr>');
		    $(":file").filestyle();
	    });
	     
	});
</script>