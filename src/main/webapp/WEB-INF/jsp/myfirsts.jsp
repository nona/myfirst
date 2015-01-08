<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ page import="org.myfirst.dto.*" %>
<html>
<head>
	<meta charset="UTF-8">
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.1.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>

	<title>My firsts</title>
	
</head>

<body>
<%UserDto user = (UserDto)session.getAttribute("loggedUser"); 
%>
<jsp:include page="header.jsp">
    <jsp:param name="username" value="<%=user.getUsername()%>"/>
</jsp:include>

<div id="container">
<%
for (MyFirstDto first: user.getFirstThings()) { %>
<div id="wrapper">
	<div class="box form">
	<div class="title">
		<span class="label"><%=first.getTitle()%></span>
		<span class="date"><%=first.getDate() %>
		<form action="/myfirst/deleteFirstThing"  method="get" class="inline">
			<input type="hidden" value="<%=first.getId()%>" id="firstThingId" name="firstThingId"/>
			<input type="submit" value="" class="imgClassSmall eraser" alt="Delete" title="Delete the first thing"/>
		</form>
		</span>
	</div>
	<hr class="line"/>
	<img alt="image" src="<%=first.getImage()%>">
	<p>
	<%=first.getDescription()%>
	</p>
	<%=first.getVisibility()%>
	<%for(ThingDto t: first.getTags()) {%>
		#<%=t.getTag()%>
	<%} %>
    </div> 
</div>
<% } %>
</div>

</body>
</html>