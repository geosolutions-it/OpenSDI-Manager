<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<c:url value="/js/bootstrap-datepicker.js"/>"></script>
<link href="<c:url value="/css/datepicker.css"/>" media="all"
	type="text/css" rel="stylesheet">
<script>
	$(function() {

		$(':radio').on('change', function() {
			var category = $(this).val();
			var fileselector = $('#' + $(this).attr('name') + "_file");

			if (category == 'file') {
				fileselector.removeAttr('disabled');
			} else {
				// OR you can set attr to "" 
				fileselector.attr('disabled', '');
			}
		});

		$('#granule_mounth').datepicker({
			format : "yyyy-mm",
			viewMode : "months",
			minViewMode : "months"
		});
	})
</script>
<div class="container width700">
	<form class="form-horizontal span6 " method="post" action="../operation/NDVIStatistics">
		<div class="control-group">
			<label class="control-label">Regions</label>
			<div class="controls">
				<c:forEach items="${regions.elements}" var="elem">
					<label class="radio"> <input type="radio" name="region"
						value="${elem.value}">${elem.label}
					</label>
				</c:forEach>
				<c:if test="${not empty regions.fileBrowser }">
					<label class="radio"> <input class="custom-selector"
						type="radio" name="region" value="file">Custom
					</label>
					<select name="region_file" id="region_file"
						class="input-block-level" disabled>
						<c:forEach items="${regions.fileBrowser.fileNames}" var="file">
							<option value="${file}">${file}</option>
						</c:forEach>
					</select>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Mask</label>
			<div class="controls">
				<c:forEach items="${masks.elements}" var="elem">
					<label class="radio"> <input type="radio" name="mask"
						value="${elem.value}">${elem.label}
					</label>
				</c:forEach>
				<c:if test="${not empty masks.fileBrowser }">
					<label class="radio"> <input class="custom-selector"
						type="radio" name="mask" value="file">Custom
					</label>
					<select name="mask_file" id="mask_file" class="input-block-level"
						disabled>
						<c:forEach items="${masks.fileBrowser.fileNames}" var="file">
							<option value="${file}">${file}</option>
						</c:forEach>
					</select>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Dekad</label>
			<div class="controls">

				<span class="input-append " data-date-format="yyyy mm">
					<input id="granule_mounth" class="span2" size="16" type="text" name="month">
					<span class="add-on"><i class="icon-calendar"></i></span>
				</span>
				<select class="input-small" name="dekad" id="dekad">
					<option>1</option>
					<option>2</option>
					<option>3</option>
				</select>
			</div>
		</div>
		<button class="btn pull-right" type="submit">Generate Statistics</button>
	</form>
</div>

