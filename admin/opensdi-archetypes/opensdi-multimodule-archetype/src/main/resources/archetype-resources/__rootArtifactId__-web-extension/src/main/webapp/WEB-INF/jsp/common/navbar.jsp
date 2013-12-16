#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<!-- <div class="navbar">
  <div class="navbar-inner">

 <div 
    <ul class="nav pull-left">
      <li class="active"><a href="${symbol_pound}">Home</a></li>
      <li><a href="${symbol_pound}">NDVI</a></li>
      <li><a href="${symbol_pound}">Link</a></li>
    </ul>
      <a class="brand"><${symbol_dollar}{username}</a>
    

  </div>
</div>
-->
<div class="navbar">
   <div class="navbar-inner">
     <div class="container">
       <div class="nav-collapse collapse navbar-responsive-collapse">
        <c:if test="${symbol_dollar}{not empty PageTitle}">
          <a class="brand" href="${symbol_pound}">${symbol_dollar}{PageTitle}</a>
        </c:if>
         <ul class="nav">
           <li class="${symbol_dollar}{context=='users'?'active':'' }"><a href="<c:url value="/users/"/>">Users</a></li>
           <li class="${symbol_dollar}{context=='operations'?'active':'' }"><a href="<c:url value="/operationManager/activeOpListOp/"/>">Operations List</a></li>
           <li class="${symbol_dollar}{context=='flowstatus'?'active':'' }"><a href="<c:url value="/operationManager/flowstatus/"/>">Check Flow Status</a></li>
           <li class="${symbol_dollar}{context=='operations'?'active':'' }"><a href="<c:url value="/operationManager/custom/"/>">Custom</a></li>
         </ul>
   
         <ul class="nav pull-right">
           <li><button id="helpOpener" class="icon-help" title="Help">Help</button></li>
           <li><a href="<c:url value="/j_spring_security_logout" />"><i class="icon-user"></i> ${symbol_dollar}{username} <i class=" icon-off"></i> Logout</a></li>
         </ul>
       </div><!-- /.nav-collapse -->
     </div>
   </div><!-- /navbar-inner -->
 </div>

<!-- help file-->
<%@ include file="help.jsp"%>