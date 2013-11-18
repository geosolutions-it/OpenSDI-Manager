<script type="text/javascript" src="<c:url value="/js/jquery-mapstore-footer.js"/>"></script>
<div id="hiddenDiv"><!-- Hidden div fro modal-backdrop --></div>
<!-- Credits dialog -->
<div id="creditsDialog" title="About this application" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">About this application</h3>
	</div>
	<div class="modal-body">
		<iframe style='border: none; height: 360px; width: 360px' src='<c:url value="/about"/>' frameborder='0' border='0'>
			<a target='_blank' href='<c:url value="/about.jsp"/>'>About</a>
		</iframe>
	</div>
</div>
<div id="footer" class="footer">
	<button id="creditsOpener" class="icon-geoexplorer">Credits</button>
</div>