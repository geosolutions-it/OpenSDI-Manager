<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<div class="control-group">
		 <label class="control-label" for="inputEmail">id</label>
		 <div class="controls">
			<input id="id" name="factor" type="text" value="${factor.factor}" data-required  ${context=='create' ? '':'disabled'}	>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label" >Label</label>
		 <div class="controls">
			<input id="label" name="label" type="text" value="${factor.label}" data-required >
		</div>
	</div>
	
	<div class="control-group">
		 <label class="control-label" >Unit</label>
		 <div class="controls">
			<input id="unit" name="unit" type="text" value="${factor.unit}"  >
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label" >Aggregation</label>
		 <div class="controls">
			<input id="unit" name="aggregation" type="text" value="${context=='create' ? 'avg': factor.aggregation }">
				
		</div>
	</div>

 