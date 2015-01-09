<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ page import="org.myfirst.dto.UserDto" %>
<%@ page import="java.util.*" %>
<html>
<head>
	<meta charset="UTF-8">
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.1.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>

	<title>Search Results</title>
	
</head>

<body>
<%UserDto user = (UserDto)session.getAttribute("loggedUser"); %>
<jsp:include page="header.jsp">
    <jsp:param name="username" value="<%=user.getUsername()%>"/>
</jsp:include>


<div id="container">
    <div class="box form controls">
	    <p class="list_title"> 
	       		<img class="small_icon" src="<c:url value='/resources/images/search.png'/>" alt="Search"/>
					Who to follow
	    </p>
    </div>
 		<c:choose>
		  <c:when test="${not empty users}">
		      <c:forEach items="${users}" var="user">
		        <div id="wrapper">
					<div class="box form" id="general">
					<table>
				        <tr>
				        <td rowspan="2">
					        <c:choose> 
							  <c:when test="${!empty user.profilePhotoLink}">
							   <img class="profile_picture" src="${user.profilePhotoLink}" alt="Profile picture"/>
							  </c:when>
							  <c:otherwise>
							    <img class="profile_picture" src="<c:url value='/resources/images/profile.png'/>" alt="Profile picture"/>
							  </c:otherwise>
							</c:choose>
				        </td>
				        <td>
				          @${user.username}<br/>
						  ${user.firstName} ${user.lastName}
				        </td>
				        </tr>
				        <tr>
				        <td>
						<form action="/myfirst/profile/${user.username}" method="get" class="inline">	
				          <input type="submit" value="" class="imgClass account" title="View Profile"/>
						</form>
						<c:choose>
		  				<c:when test="${!user.following}">
						<form action="/myfirst/addFriend" method="get" class="inline">	
				          <input type="hidden" value='${user.username}' name="username"/>
				          <input type="submit" value="" class="imgClass follow" title="Follow"/>
						</form>
    					</c:when>
						<c:otherwise>
    					<form action="/myfirst/removeFriend" method="get" class="inline">	
				          <input type="hidden" value='${user.username}' name="username"/>
				          <input type="submit" value="" class="imgClass unfollow" title="Unfollow"/>
						</form>
						</c:otherwise>
						</c:choose>
				        </td>
				        </tr>
				        </table>
			        </div> 
			    </div>
		      </c:forEach>
		  </c:when>
		  <c:otherwise>
		  <div id="wrapper">
			<div class="box form" id="general">
		    No one follows you yet.
		    </div>
		    </div>
		  </c:otherwise>
		</c:choose>
</div>


</body>
</html>