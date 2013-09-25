<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="container">
	<h2>Operations List</h2>

	<table class="table table-hover">
		<thead>
			<tr>
				<th>Name</th>
				<th>REST Path</th>
				<th>Type</th>
				<th>File Action</th>
			</tr>
		</thead>
		<tbody>
			
			<c:if test="${not empty operations }">
				<c:forEach items="${operations}" var="op">
					<tr>
						<td>${op[0]}</td>
						<td>${op[1]}</td>
						<td>${op[2]}</td>
						<td>
							<c:if test="${op[3]}">
								<a data-toggle="modal" class="btn ${fn:toLowerCase(op[0])}" data-target="#${fn:toLowerCase(op[0])}"
									href="../operation/${op[1]}/">${op[0]}</a>
							</c:if>
							
							<c:if test="${not op[3]}">
								no
							</c:if>

						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty operations }">
				<tr>
					<td>No operations to show</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>
