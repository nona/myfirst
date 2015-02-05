<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ page import="org.myfirst.dto.UserDto" %>
<%@ page import="org.myfirst.dto.ThingDto" %>
<%@ page import="org.myfirst.dto.FirstThingDto" %>
<%@ page import="java.util.*" %>
<html>
<head>
	<meta charset="UTF-8">
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.1.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/polyfiller.js"/>'></script>

	<title>Search Results</title>
	<script type="text/javascript">
	    webshims.setOptions('forms-ext', {types: 'date'});
		webshims.polyfill('forms forms-ext');
	</script>
</head>

<body>
<%UserDto user = (UserDto)session.getAttribute("loggedUser"); %>


<jsp:include page="header.jsp">
    <jsp:param name="username" value="<%=user.getUsername()%>"/>
</jsp:include>


<div id="container">
    <div class="box form controls">
	    <p class="list_title"> 
	       		<img class="small_icon" src="<c:url value='/resources/images/tag.png'/>" alt="recommendations"/>
					Recommendations
	    </p>
	    	     <c:choose>
		  <c:when test="${not empty recommendations}">
		      <c:forEach items="${recommendations}" var="r">
		      <hr/>
					<form action="/myfirst/removeRecommendation" method="get" class="inline">	
			         <b> ${r.title}</b>
			         <input type="hidden" value='${r.id}' name="id"/>
			          <br/>
			          Done by @${r.username}
			          <select name="action" id="action" onchange="this.form.submit()" style="width:18px;">
			            <option value=""></option>
				        <option value="add">Add to my ToDo list</option>
				        <option value="notInterested">Not Interested</option>
				        <option value="done">Already done</option>
				       </select>
					</form>
					<br/>
		      </c:forEach>
		  </c:when>
		  <c:otherwise>
		    No recommendations yet.
		  </c:otherwise>
		</c:choose>
    </div>
    
    <div id="wrapper">
	<div class="box form">
	<h1>Add new ToDo item:</h1>
        <form id="toDoForm" method="post" action="/myfirst/addToDo"  enctype="multipart/form-data">	
        <p>
        <label for="title" >Title:</label>
        <input type="text" id="title" name="title" value="${title}" placeholder="I want to do..." />
        </p>
        <p>
        	<label for="dueDate">Due date:</label>
            <input id="dueDate" name="dueDate" required="required" type="date" />
        </p>
        <p>
	        <label for="tags" >Tags:</label>
	        <input type="text" id="tags" name="tags" placeholder="e.g. #dog, #kiss, #car" value="${tags}"/>
        </p>
          <input type="submit" value="ADD"  class="submitBtn" style="width: 50px;height: 10px;padding: 0 0 15 0;"/>
		</form>
    </div> 
</div>

 <% if (user.getToDos() != null && !user.getToDos().isEmpty()) {
	 for (FirstThingDto thing: user.getToDos()) {
 %>
<div id="wrapper">
	<div class="box form">
	<div class="title">
		<span class="label"><%=thing.getTitle()%></span>
		<span class="date">Due date:<%=thing.getDate() %>
		<form action="/myfirst/deleteToDo"  method="get" class="inline">
			<input type="hidden" value="<%=thing.getId() %>" id="firstThingId" name="firstThingId"/>
			<input type="submit" value="" class="imgClassSmall eraser" alt="Delete" title="Delete the first thing"/>
		</form>
		</span>
	</div>
	<hr class="line"/>
	<%for(ThingDto tag: thing.getTags()){ %>
          #<%=tag.getTag() %>
    <%} %>
    </div> 
</div>
<%} 
} else {%>

  <div id="wrapper">
	<div class="box form" id="general">
    Your ToDo list is still empty.
    </div>
    </div>
<%} %>
</div>


</body>
</html>