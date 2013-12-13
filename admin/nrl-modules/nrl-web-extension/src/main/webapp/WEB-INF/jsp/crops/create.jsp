<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<div class="control-group">
		 <label class="control-label" for="inputEmail">id</label>
		 <div class="controls">
			<input id="id" name="id" type="text" value="${crop.id}" data-required  ${context=='create' ? '':'disabled'}	>
		</div>
	</div>
	<div class="control-group">
		 <label class="control-label" >Label</label>
		 <div class="controls">
			<input id="label" name="label" type="text" value="${crop.label}" data-required >
		</div>
	</div>
	
	<div class="control-group">
		 <label class="control-label" >Season</label>
		 <div class="controls checkbox">
		 		<form:checkbox path="crop.seasons" value="RABI"></form:checkbox>Rabi<br/>
		 		<form:checkbox path="crop.seasons" value="KHARIF"></form:checkbox>Kharif
				
		</div>
	</div>

 