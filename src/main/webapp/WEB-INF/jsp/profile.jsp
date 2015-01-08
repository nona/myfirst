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
 <c:choose> 
  <c:when test="${!empty viewed.profilePhotoLink}">
   <img class="profile_picture" src="${viewed.profilePhotoLink}" alt="Profile picture"/>
  </c:when>
  <c:otherwise>
    <img class="profile_picture" src="<c:url value='/resources/images/profile.png'/>" alt="Profile picture"/>
  </c:otherwise>
</c:choose>
	</td>
	<td>
		<b>@${viewed.username}</b>
		<br/>
		${viewed.firstName} ${viewed.lastName}
    	<br/>
    	<b>First things:</b> ${viewed.firstThingsCount}
    </td>
    </tr>
    <tr>
    <td colspan="2">
		<b>Date Of Birth:</b>
		<br/>${viewed.dateOfBirth}
		<br/>
		<b>Country:</b>
		<br/>${viewed.country}
		<br/>
		<br/><b>Interests:</b>
		<br/>
		  	<c:choose>
			  <c:when test="${not empty viewed.interests}">
			      <c:forEach items="${viewed.interests}" var="thing">
			          #${thing.tag} 
			      </c:forEach>
			  </c:when>
			  <c:otherwise>
			  <div id="wrapper">
				<div class="box form" id="general">
			    No interests have been added yet.
			    </div>
			    </div>
			  </c:otherwise>
			</c:choose>
	</td>
	</tr>
    </table>
    </div>
  			<c:choose>
			  <c:when test="${not empty viewed.firstThings}">
			      <c:forEach items="${viewed.firstThings}" var="thing">
			        <div id="wrapper">
	<div class="box form">
	<div class="title">
		<span class="label">${thing.title}</span>
		<span class="date">${thing.date}
		</span>
	</div>
	<hr class="line"/>
	<c:choose> 
	  <c:when test="${!empty thing.image}">
	   <img class="profile_picture" src="${thing.image}" alt="image"/>
	  </c:when>
	</c:choose>
	<p>
	${thing.description}
	</p>
	<c:forEach items="${thing.tags}" var="tag">
          #${tag.tag} 
      </c:forEach>
    </div> 
</div>
			      </c:forEach>
			  </c:when>
			  <c:otherwise>
			  <div id="wrapper">
				<div class="box form" id="general">
			    ${viewed.username} hasn't done any first things yet.
			    </div>
			    </div>
			  </c:otherwise>
			</c:choose>
</div>

</body>
</html>