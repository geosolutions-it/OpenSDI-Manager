<!-- Credits dialog -->
<div id="helpDialog" title="Help" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">Help</h3>
	</div>
	<div class="modal-body">
		The help file will be here
	</div>
</div>
<script type="text/javascript">
	$(function() {
		// reuse from jquery-mapstore-footer
		$().jqueryDialogAndBackDrop("#helpDialog", "#helpOpener", "#helpDialog button", "#hiddenDiv");
	});
</script>