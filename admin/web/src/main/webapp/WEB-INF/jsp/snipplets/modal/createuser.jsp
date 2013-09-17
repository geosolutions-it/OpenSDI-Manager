<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<div class="control-group">
		 <label class="control-label" for="inputEmail">User</label>
		 <div class="controls">
			<input id="name" name="name" type="text" value="${user.name}" data-required  ${context=='create' ? '':'disabled'}	>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label">Role</label>
		 <div class="controls">
			<select  id="role" name="role" data-required>
				<option value="ADMIN">Admin</option>
				<option value="USER" selected>User</option>
			</select>
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

 