<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Crop Informational Portal ${PageTitle}</title>
<!--[if IE ]>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<![endif]-->
<link href="<c:url value="/css/bootstrap.min.css"/>" media="all" type="text/css"
	rel="stylesheet">
<link href="<c:url value="/css/opensdi.css"/>" media="all" type="text/css"
	rel="stylesheet">
<link href="<c:url value="/css/nrl.css"/>" media="all" type="text/css"
	rel="stylesheet">

<!-- Plupload -->
<link type="text/css" rel="stylesheet" href="<c:url value="/css/jquery-ui-1.10.3.custom.min.css"/>" media="screen" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/jquery.ui.plupload.css"/>" media="screen" />

<script src="<c:url value="/js/jquery-1.10.2.min.js"/>"></script>
<script src="<c:url value="/js/jquery-validate.min.js"/>"></script>
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/form.js"/>"></script>
<script src="<c:url value="/js/jquery.form.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap-filestyle.min.js"/>"></script>

<!-- Plupload -->
<script type="text/javascript" src="<c:url value="/js/shCore.js"/>" charset="UTF-8"></script>
<script type="text/javascript" src="<c:url value="/js/shBrushPhp.js"/>" charset="UTF-8"></script>
<script type="text/javascript" src="<c:url value="/js/shBrushjScript.js"/>" charset="UTF-8"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-ui.min.js"/>" charset="UTF-8"></script>
<script type="text/javascript" src="<c:url value="/js/plupload.full.min.js"/>" charset="UTF-8"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.ui.plupload/jquery.ui.plupload.min.js"/>" charset="UTF-8"></script>

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