<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="osdim" uri="../../tld/osdim.tld"%>
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
			<c:if test="${not empty directory && directory!='/'}">
				<tr>
					<td><a href="?d=${directoryBack}">.. /</a></td>
					<td>Folder</td>
					<td></td>
					<td>
						<a class="btn" href="?d=${directoryBack}">Up one folder</a>
					</td><td></td>
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
									<button data-toggle="modal" class="btn ${fn:toLowerCase(op.name)}" data-target="#${fn:toLowerCase(op.name)}"
										data-fileid="${file.name}" href="../../operation/${op.RESTPath}/${file.name}?d=${directory == '/'?'': osdim:encodeURIComponent(directory)}">${op.name}</button>
									</c:forEach>
								</c:if>
							</c:forEach>
						</td>
						<c:if test="${not file.isDirectory }"><td>${file.size} Bytes</td></c:if>
						<c:if test="${file.isDirectory }"><td>Folder</td></c:if>
						<td>${file.lastModified}</td>
						<td>
							<c:if test="${(not file.isDirectory) and canDelete }">
								<button class="btn btn-mini btn-danger" data-confirm="are you sure to delete the file '${file.name}'" data-filename="${file.name}" onClick="confirmDelete('${file.name}')">Delete</button> 
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
	            <td><div class="input-append">
					<input  name="files[0]" type="file" accept="${not empty accept ?accept :''}" />
					</div>
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

	
	$(document).ready(function() {
	    $(":file").filestyle();
	    var fileIndex =$('#fileTable tr').children().length;
	    //add more file components if Add is clicked
	    $('#addFile').click(function() {
	        fileIndex++;
	        //$(".remrow").hide();
	        $('#fileTable').append(
	                '<tr id="row_'+ fileIndex +'"><td>'+
	                '<div class="input-append" >' + 
	                '    <input type="file" name="files['+ fileIndex +']" id="files['+ fileIndex +']" accept="${not empty accept ?accept :''}" /> '+
	                '<input id="rembtn_'+ fileIndex +'" type="button" value="Remove" class="remrow btn btn-danger" name="clear'+ fileIndex +'" onclick="delete('+ fileIndex +')"/>'+
	            	'</div>'    + 
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

	function confirmDelete(filename) {
		if (!$('#deleteFileConfirmModal').length) {
			$('body').append('<div id="deleteFileConfirmModal" class="modal" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h3 id="dataConfirmLabel">Please Confirm</h3></div><div class="modal-body"></div><div class="modal-footer"><button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button><a class="btn btn-primary" data-dismiss="modal" id="dataConfirmOK">OK</a></div></div>');
		} 
		$('#deleteFileConfirmModal').find('.modal-body').text("Are you sure that you want to delete '"+filename+"'?");
		
		$('#dataConfirmOK').on('click', function(){
			delFile(filename);
		} );
		$('#deleteFileConfirmModal').modal({show:true});
		return false;
	}

</script>
</div>