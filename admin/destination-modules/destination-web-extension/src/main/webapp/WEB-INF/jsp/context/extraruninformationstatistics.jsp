<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Extra information for destination -->
<div class="container resume">
	<h3>Errors details</h3>
	<table class="flexme">
		<tr>
			<th>Id</th>
			<th>Detail</th>
		</tr>
		<c:forEach items="${runInfo.extraInformation.trace.lines}" var="line">
		<tr>
			<td>${line.progressivo}</td>
			<td>${line.descr_errore}</td>
		</tr>
		</c:forEach>
	</table>
</div>

<script type="text/javascript">
/**
 * Flexgrid on tables to be flexed
 */
$(document).ready(function() {
	
	$('.flexme').flexigrid({
		height: 300
	});

});
</script>