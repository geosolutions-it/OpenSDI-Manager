<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<title>Login Page</title>
<link href="css/bootstrap.min.css" media="all" type="text/css"
	rel="stylesheet">
<link href="css/nrl.css" media="all" type="text/css"
	rel="stylesheet">
</head>
<body onload='document.f.j_username.focus();'>
	<%@ include file="common/banner.jsp"%>

	<div class="container" style="width:700px;">
		<div class="" id="loginModal">
		
			<div class="modal-header"><h1>Administration page</h1></div>
			<div class="modal-body">
				<div class="well">
				
					<div class="" id="login">
						<form name='f' class="form-horizontal"
							action="<c:url value='j_spring_security_check' />" method="POST">
							<fieldset>
								<div id="legend">
									<legend class="">Login</legend>
								</div>
								<div class="login-form-controls">
								<div class="control-group">
									<!-- Username -->
									<label class="control-label" for="j_username">Username</label>
									<div class="controls">
										<input type="text" name='j_username' placeholder=""
											class="input-block-level">
									</div>
								</div>

								<div class="control-group">
									<!-- Password-->
									<label class="control-label" for="j_password">Password</label>
									<div class="controls">
										<input type="password" name='j_password' placeholder=""
											class="input-block-level">
									</div>
								</div>


								<div class="control-group">
									<!-- Button -->
									<div class="controls pull-right">
										<button class="btn btn-large btn-success" type="submit">Login</button>
									</div>
								</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
				<c:if test="${not empty error}">
					<div class="alert  alert-error ">
						Your login attempt was not successful, try again.<br /> Caused :
						${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
					</div>
				</c:if>
			</div>

		</div>

	</div>
<%@ include file="common/footer.jsp"%>
</body>
</html>