<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ page import="org.myfirst.dto.*" %>
<html>
<head>
	<meta charset="UTF-8">
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.1.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>

	<title>Home</title>
	
<script type="text/javascript">
   function chooseFile() {
      $("#file").click();
   }
</script>
</head>

<body>
<%
	UserDto user = (UserDto)session.getAttribute("loggedUser"); 
for (FirstThingDto first: user.getFirstThings()) {
	System.out.println(first.getDescription() + " " + first.getVisibility());
}
%>
<jsp:include page="header.jsp">
    <jsp:param name="username" value="<%=user.getUsername()%>"/>
</jsp:include>

<div id="container">
<div id="wrapper">
	<div class="box form">
	<h1>Add new first thing:</h1>
        <form id="firstThingForm" method="post" action="/myfirst/addFirstThing"  enctype="multipart/form-data">	
        <p>
        <label for="title" >Title:</label>
        <input type="text" id="title" name="title" placeholder="My first..." />
        </p>
        <p>
        	<label for="description">Description:</label>
        	<textarea name="description" style="width:450px;" rows="3" placeholder="What did you do for the first time today?"></textarea>
        </p>
        <p>
	        <label for="tags" >Tags:</label>
	        <input type="text" id="tags" name="tags" placeholder="e.g. #dog, #kiss, #car" />
        </p>
	  	  <img id="image_preview" src="" alt="your image" style="display:none;"/>
         <p class="save button">
        <div class='file_browse_wrapper'>
	  	  <input  type="file" name="file" id="file" class="file_browse" accept="image/gif, image/jpeg, image/png" onchange="readURL(this);"/>
	  	</div>
          <label for="visibility" style="padding-left: 130px;">Visibility:</label>
          <select id="visibility" name="visibility">
            <option value="public">Everyone</option>
            <option value="following">The people I follow</option>
			<option value="private">Only Me</option>
          </select>
          <input type="submit" value="Share"  class="submitBtn" style="width: 50px;height: 10px;padding: 0 0 15 0;"/>
         </p>
		</form>
    </div> 
</div>
</div>

</body>
</html>