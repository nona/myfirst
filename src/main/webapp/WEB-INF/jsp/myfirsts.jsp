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
    <%if (user.getDateOfBirth() != null) {%>
		<b>Date Of Birth:</b>
		<br/><%=user.getDateOfBirth() %>
		<br/>
	<%} %>
	<%if (user.getCountry() != null) { %>
		<b>Country:</b>
		<br/><%=user.getCountry() %>
		<br/>
	<%} %>
		<b>Email:</b>
		<br/><%=user.getEmail() %>
		<br/>
		<b>Interests:</b>
	<%if (user.getInterests() != null && !user.getInterests().isEmpty()) { %>
		<br/>
		       <%
		       	for (ThingDto t: user.getInterests()) {
		       %>
                	<%="#" + t.getTag()%>
                <%
                	}
                %>
	<%
		} else {
	%>
		You haven't added any interests yet.
	<%
		}
	%>
	</td>
	</tr>
    </table>
    </div>
<%
	for (FirstThingDto first: user.getFirstThings()) {
%>
<div id="wrapper">
	<div class="box form">
	<div class="title">
		<span class="label"><%=first.getTitle()%></span>
		<span class="date"><%=first.getDate()%>
		<form action="/myfirst/deleteFirstThing"  method="get" class="inline">
			<input type="hidden" value="<%=first.getId()%>" id="firstThingId" name="firstThingId"/>
			<input type="submit" value="" class="imgClassSmall eraser" alt="Delete" title="Delete the first thing"/>
		</form>
		</span>
	</div>
	<hr class="line"/>
	<%
		if (first.getImage() != null && !first.getImage().isEmpty()) {
	%>
	<img alt="image" src="<%=first.getImage()%>"  class="postImage">
	<%
		}
	%>
	<p>
	<%=first.getDescription()%>
	</p>
	<%
		for(ThingDto t: first.getTags()) {
	%>
		#<%=t.getTag()%>
	<%} %>
    </div> 
    <div class="comment">
    <%if (first.getComments() != null && !first.getComments().isEmpty()) { 
    for (CommentDto c: first.getComments()) {%>
    	<img src="<%=c.getCommentorProfilePic()%>"  class="small_icon"/>
          <b><%=c.getUsername() %></b> (<%=c.getDate() %>)  <%=c.getContent() %> 
          <br/> 
    <% }
    } %>
    <form action="/myfirst/addComment" method="get" >
    <input type="hidden" value='<%=user.getUsername() %>' name="username"/>
    <input type="hidden" value='<%=first.getId() %>' name="thingId"/>
	<%if(user.getProfilePhotoLink() != null && !user.getProfilePhotoLink().isEmpty()){ %>
    <img class="small_icon" src="<%=user.getProfilePhotoLink()%>" alt="Profile picture" style="position:absolute;"/>
    <%} else { %>
    <img class="small_icon" src="<c:url value='/resources/images/profile.png'/>" alt="Profile picture" style="position:absolute;"/>
    <%} %>
    <input type="text" id="comment" name="comment" style="width: 320px;margin-left: 38px;"/>
    <input type="submit" value="Comment"  class="submitBtn" style="width: 74px;height: 10px;padding: 0 0 15 0;"/>
    </form>
    </div>
</div>
<% } %>
</div>

</body>
</html>