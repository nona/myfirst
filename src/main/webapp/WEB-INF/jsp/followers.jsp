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
					<form action="/myfirst/viewAccount" method="get">			
			        <p>
			          ${user.username} - ${user.firstName} ${user.lastName}
			          <input type="hidden" value='${user.username}' name="username"/>
			          <img class="small_icon" src="<c:url value='/resources/images/account.png'/>" alt="View Profile" title="View Profile"/>
			          <img class="small_icon" src="<c:url value='/resources/images/runner.png'/>" alt="Follow" title="Follow"/>
			        </p>
					</form>
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