<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true" %>
<div class="container" id="${containerId}">
	<h2>File Browser</h2>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Name</th>
				<th>Actions</th>
				<th>Size</th>
				<th>LastModified</th>
				<th>Delete File</th>
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
						<td><a href="?d=${directory}${file.name}">${file.name}/</a></td>
						</c:if>
						<td>
							<c:if test="${file.isDirectory}">
								<a class="btn" href="?d=${directory}${file.name}">Open folder</a>
							</c:if>
							<c:forEach items="${operations}" var="entry">
								<c:set var="fileext" value=".${entry.key}" /> 
								<c:if test="${fn:endsWith(file.name, fileext)}">
								<c:forEach items="${entry.value}" var="op">
									<a data-toggle="modal" class="btn ${fn:toLowerCase(op.name)}" data-target="#${fn:toLowerCase(op.name)}"
										data-fileid="${file.name}" href="../../operation/${op.RESTPath}/${file.name}">${op.name}</a>
									</c:forEach>
								</c:if>
							</c:forEach>
						</td>
						<c:if test="${not file.isDirectory }"><td>${file.size} Bytes</td></c:if>
						<c:if test="${file.isDirectory }"><td>Folder</td></c:if>
						<td>${file.lastModified}</td>
						<td>
							<c:if test="${(not file.isDirectory) and canDelete }">
								<button class="btn btn-mini btn-danger" onClick="delFile('${file.name}')">Delete</button> 
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
<c:if test="${ canUpload }">
	<form:form method="post" modelAttribute="uploadFile" id="${formId}" enctype="multipart/form-data" action="../../operation/${operationRESTPath}/">
	    <p>Select files to upload. Press Add button to add more file inputs.</p>
	    <table id="fileTable">
	    	<tr>
	            <td>
					<input  name="files[0]" type="file" />
				</td>
	        </tr>
	    </table>
	    <br/>
	    <input id="basedir" type="hidden" name="d" value="${directory}" />
	    <input id="addFile" type="button" value="Add File" class="btn" />
		<input type="submit" value="Upload" class="btn btn-primary "/>
	</form:form>
</c:if>

<c:forEach items="${operations}" var="entry">
<!-- TODO: create only if they are used in the file list! -->
<c:forEach items="${entry.value}" var="op">
<div id="${fn:toLowerCase(op.name)}" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">${op.name}</h3>
	</div>
	<div class="modal-body">
	</div>
</div>
</c:forEach>
</c:forEach>

<script type="text/javascript">
	$(function() {

	<c:forEach items="${operations}" var="entry">
	<c:forEach items="${entry.value}" var="op">
		formUtils.initModalForm('#${fn:toLowerCase(op.name)}');
		$('.${fn:toLowerCase(op.name)}').on('click', function() {
			var fileId = $(this).data('fileid');
			formUtils.changeAction('#${fn:toLowerCase(op.name)}', '../../operation/${op.RESTPath}/' + fileId);
		})
	</c:forEach>
	</c:forEach>

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
	    
    	$("#${formId}").submit(function() {
    		var options = {
    			/* target:"#divResult", */
    			success: function(html) {
    				//  the next server response could not have the same id
    				$("#${containerId}").replaceWith($('#${containerId}', $(html)));
    				//$("#${containerId}").html(html);
    			}
    		};

    		$(this).ajaxSubmit(options);
    		return false;
    	});
	});

<c:if test="${canDelete}">
	function delFile(fileName){
		var data = {
				"action": "delete", 
				"toDel" : fileName
			};
<c:if test="${not empty directory}">
		data["d"] = "${directory}";
</c:if>
		$.ajax({
			type : 'POST',
			url : "../../operation/${operationRESTPath}/",
			data : data,
			success : function(response) {
				$("#${containerId}").replaceWith($('#${containerId}', $(response)));
			},
			error : function(response) {
				$("#${containerId}").replaceWith($('#${containerId}', $(response)));
			}
		});
	}
</c:if>
</script>
</div>