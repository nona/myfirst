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
    <div class="box form controls">
    <table> 
    <tr>
    <td>
			<%if(user.getProfilePhotoLink() != null && !user.getProfilePhotoLink().isEmpty()){ %>
	        <img class="profile_picture" src="<%=user.getProfilePhotoLink()%>" alt="Profile picture"/>
	        <%} else { %>
	        <img class="profile_picture" src="<c:url value='/resources/images/profile.png'/>" alt="Profile picture"/>
	        <%} %>
	</td>
	<td>
		<b>@<%=user.getUsername() %></b>
		<br/>
		<%=user.getFirstName() + " " + (user.getMiddleName() != null && user.getMiddleName().length() > 0 ? user.getMiddleName() + " " : "") + user.getLastName()%>
    	<br/>
    	<b>First things:</b> <%=user.getFirstThingsCount() %>
    </td>
    </tr>
    <tr>
    <td colspan="2">
		<b>Date Of Birth:</b>
		<br/><%=user.getDateOfBirth() %>
		<br/>
		<b>Country:</b>
		<br/><%=user.getCountry() %>
		<br/>
		<b>Email:</b>
		<br/><%=user.getEmail() %>
		<br/><b>Interests:</b>
		<br/>
		       <%for (ThingDto t: user.getInterests()) { %>
                	<%="#" + t.getTag() %><a href="#" onclick="removeInterest('<%=t.getTag()%>');">
                <%} %>
	</td>
	</tr>
    </table>
    </div>
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