<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<title>Crop Informational Portal ${PageTitle}</title>
<link href="<c:url value="/css/bootstrap.min.css"/>" media="all" type="text/css"
	rel="stylesheet">
<link href="<c:url value="/css/nrl.css"/>" media="all" type="text/css"
	rel="stylesheet">
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-validate.min.js"/>"></script>
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/form.js"/>"></script>

</head>
<body>
	<div id="#holder">
		<%@ include file="common/banner.jsp"%>
		<div class="container-flow">
		
		<%@ include file="common/navbar.jsp"%>
		<jsp:include page="context/${context}.jsp" />
	
		</div>
		<%@ include file="common/footer.jsp"%>
	</div>
</body>
</html>