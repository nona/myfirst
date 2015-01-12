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
		<c:if test="${viewed.followingMe}">
		<br/>
		(follows me)
		</c:if>
		<br/>
		${viewed.firstName} ${viewed.lastName}
    	<br/>
    	<b>First things:</b> ${viewed.firstThingsCount}
    </td>
    </tr>
    <tr>
    <td colspan="2">
    <c:if test="${not empty viewed.dateOfBirth}">
		<b>Date Of Birth:</b>
		<br/>${viewed.dateOfBirth}
		<br/>
    </c:if>
    <c:if test="${not empty viewed.country}">
		<b>Country:</b>
		<br/>${viewed.country}
		<br/>
    </c:if>
		  	<c:choose>
			  <c:when test="${not empty viewed.interests}">
			  <b>Interests:</b>
			      <c:forEach items="${viewed.interests}" var="thing">
			          #${thing.tag} 
			      </c:forEach>
			  </c:when>
			</c:choose>
	<c:choose>
	<c:when test="${!viewed.following}">
	<form action="/myfirst/addFriend" method="get" class="inline">	
         <input type="hidden" value='${viewed.username}' name="username"/>
         <input type="submit" value="" class="imgClass follow" title="Follow"/>
	</form>
	</c:when>
	<c:otherwise>
	<form action="/myfirst/removeFriend" method="get" class="inline">	
         <input type="hidden" value='${viewed.username}' name="username"/>
         <input type="submit" value="" class="imgClass unfollow" title="Unfollow"/>
	</form>
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
	   <img class="profile_picture" src="${thing.image}" alt="image" class="postImage"/>
	  </c:when>
	</c:choose>
	<p>
	${thing.description}
	</p>
	<c:forEach items="${thing.tags}" var="tag">
          #${tag.tag} 
      </c:forEach>
    </div> 
    <div class="comment">
    <c:if test="${not empty thing.comments}">
    	<c:forEach items="${thing.comments}" var="c">
    	<img src="${c.commentorProfilePic}"  class="small_icon"/>
          <b>${c.username}</b> (${c.date})  ${c.content} 
          <br/>
      </c:forEach>
    </c:if>
    <form action="/myfirst/addComment" method="get" >
    <input type="hidden" value='${viewed.username}' name="username"/>
    <input type="hidden" value='${thing.id}' name="thingId"/>
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