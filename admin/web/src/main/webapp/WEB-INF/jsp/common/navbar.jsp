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
         
  <!--           <li class="${context=='home'?'active':'' }"><a href="<c:url value="/home"/>">Home</a></li> -->
           
           <li><a href="<c:url value="/crops/"/>">Crops</a></li>
           <li><a href="<c:url value="/agromet/"/>">Agromet Factors</a></li>
           	
           <li class="dropdown">
             <a href="#" class="dropdown-toggle" data-toggle="dropdown">Statistics<b class="caret"></b></a>
             <ul class="dropdown-menu">
               <li><a href="<c:url value="/operationManager/NDVIStatistics"/>">NDVI</a></li>
<!--                <li><a href="#">Publish</a></li> -->
  
             </ul>
           </li>
            
           <li class="${context=='users'?'active':'' }"><a href="<c:url value="/users/"/>">Users</a></li>
           
           <li class="${context=='files'?(operationName=='NDVIBrowser'?'active':''):'' }"><a href="<c:url value="/operationManager/fileBrowserOp/NDVI"/>">File Browser NDVI</a></li>

           <li class="${context=='files'?(operationName=='CSVBrowser'?'active':''):'' }"><a href="<c:url value="/operationManager/fileBrowserOpCSV/CSV"/>">File Browser CSV</a></li>
           
           <li class="${context=='operations'?'active':'' }"><a href="<c:url value="/operationManager/activeOpListOp/"/>">Operations List</a></li>

           <li class="${context=='flowstatus'?'active':'' }"><a href="<c:url value="/operationManager/flowstatus/"/>">Check Flow Status</a></li>
         </ul>
   
         <ul class="nav pull-right">

           <li class="dropdown">
             <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-user"></i> ${username} <b class="caret"></b></a>
             <ul class="dropdown-menu">
               <li><a href="<c:url value="/j_spring_security_logout" />" ><i class=" icon-off"></i> Logout</a></li>
             </ul>
           </li>
         </ul>
       </div><!-- /.nav-collapse -->
     </div>
   </div><!-- /navbar-inner -->
 </div>

