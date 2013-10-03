<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>OpenSDI Manager ${PageTitle}</title>
<link href="<c:url value="/css/bootstrap.min.css"/>" media="all" type="text/css"
	rel="stylesheet">
<link href="<c:url value="/css/nrl.css"/>" media="all" type="text/css"
	rel="stylesheet">
<link id="theme" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" rel="stylesheet">
<script src="<c:url value="/js/jquery-1.10.2.min.js"/>"></script>
<script src="<c:url value="/js/jquery-validate.min.js"/>"></script>
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/form.js"/>"></script>
<script src="<c:url value="/js/jquery.form.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap-filestyle.min.js"/>"></script>

</head>
<body>
	<div id="#holder">
		<%@ include file="common/banner.jsp"%>
		<div class="container-flow">
		
		<%@ include file="common/navbar.jsp"%>
		<jsp:include page="${context}.jsp" />
	
		</div>
		<%@ include file="common/footer.jsp"%>
	</div>
</body>
</html>