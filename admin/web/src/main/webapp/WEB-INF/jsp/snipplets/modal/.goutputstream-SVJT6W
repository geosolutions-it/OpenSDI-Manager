<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<div class="control-group">
		 <label class="control-label" for="inputEmail">User</label>
		 <div class="controls">
			<input id="name" name="name" type="text" value="${user.name}" data-required  ${context=='create' ? '':'disabled'}	>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label">Role</label>
		 <div class="controls">
		 	<c:choose>
		 	<c:when test="${(user.name != null) && (username == user.name)}">
		 	<select id="role" name="role" disabled="true" data-required>
				<option value="ADMIN" selected>Admin</option>
			</select>
		 	</c:when><c:when test="${user.role == 'ADMIN'}">
			<select id="role" name="role" data-required>
				<option value="ADMIN" selected>Admin</option>
				<option value="USER">User</option>
			</select></c:when><c:otherwise>
			<select  id="role" name="role" data-required>
				<option value="ADMIN">Admin</option>
				<option value="USER" selected>User</option>
			</select></c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label">Password</label>
		 <div class="controls">
			<input type="password" name="newPassword" id="newPassword" ${context=='create' ? 'data-required':''}></input>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label">Confirm Password</label>
		 <div class="controls">
			<input type="password" ${context=='create' ? 'data-required':''} data-equalTo="#newPassword" data-validate="equalTo"></input>
		</div>
	</div>
	 <c:forEach var="attribute" items="${user.attribute}" varStatus="status">
        <div class="control-group">
		 <label class="control-label">${attribute.name}</label>
		 <div class="controls">
		 	<input type="hidden" name="attribute[${status.index}].name"   value="${attribute.name}"></input>
			<input type="text"   name="attribute[${status.index}].value"  value="${attribute.value}"></input>
		</div>
      </c:forEach>
	
 