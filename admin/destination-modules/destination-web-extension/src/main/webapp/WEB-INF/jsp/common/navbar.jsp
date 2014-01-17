<!-- <div class="navbar">
  <div class="navbar-inner">

 <div 
    <ul class="nav pull-left">
      <li class="active"><a href="#">Home</a></li>
      <li><a href="#">NDVI</a></li>
      <li><a href="#">Link</a></li>
    </ul>
      <a class="brand"><${username}</a>
    

  </div>
</div>
-->
<div class="navbar">
   <div class="navbar-inner">
     <div class="container">
       <div class="nav-collapse collapse navbar-responsive-collapse">
        <c:if test="${not empty PageTitle}">
          <a class="brand" href="#">${PageTitle}</a>
        </c:if>
         <ul class="nav">
           <li class="${context=='context/users'?'active':'' }"><a href="<c:url value="/users/"/>">Users</a></li>
           <li class="${context=='context/files'?(operationName=='fileBrowserTarget'?'active':''):'' }"><a href="<c:url value="/operationManager/fileBrowserTarget/ZIP"/>">Target Ingestion</a></li>
           <li class="${context=='context/files'?(operationName=='fileBrowserPter'?'active':''):'' }"><a href="<c:url value="/operationManager/fileBrowserPter/ZIP"/>">PTER Ingestion</a></li>
           <li class="${context=='context/files'?(operationName=='fileBrowserRoadSegmentation'?'active':''):'' }"><a href="<c:url value="/operationManager/fileBrowserRoadSegmentation/ZIP"/>">Road Segmentation</a></li>
           <li class="${context=='context/files'?(operationName=='fileBrowserRoad'?'active':''):'' }"><a href="<c:url value="/operationManager/fileBrowserRoad/ZIP"/>">Road Ingestion</a></li>
           <li class="${context=='context/files'?(operationName=='fileBrowserGate'?'active':''):'' }"><a href="<c:url value="/operationManager/fileBrowserGate/XML"/>">Gate Ingestion</a></li>
           <!--li class="${context=='operations'?'active':'' }"><a href="<c:url value="/operationManager/activeOpListOp/"/>">Operations List</a></li>
           <li class="${context=='flowstatus'?'active':'' }"><a href="<c:url value="/operationManager/flowstatus/"/>">Check Flow Status</a></li>
           <li class="${context=='operations'?'active':'' }"><a href="<c:url value="/operationManager/flowlog/"/>">Run history</a></li-->
         </ul>
   
         <ul class="nav pull-right">
           <li><button id="helpOpener" class="icon-help" title="Help">Help</button></li>
           <li><a href="<c:url value="/j_spring_security_logout" />"><i class="icon-user"></i> ${username} <i class=" icon-off"></i> Logout</a></li>
         </ul>
       </div><!-- /.nav-collapse -->
     </div>
   </div><!-- /navbar-inner -->
 </div>

<!-- help file-->
<%@ include file="help.jsp"%>