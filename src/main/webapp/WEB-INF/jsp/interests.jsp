<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:url value="/myprofile/edit" var="editUrl"/>
<c:url value="/myprofile/password" var="passwordUrl"/>
<c:url value="/myprofile/interests" var="interestsUrl"/>
<c:url value="/myprofile/removeInterest" var="removeInterestUrl"/>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ page import="org.myfirst.dto.UserDto" %>
<%@ page import="org.myfirst.dto.ThingDto" %>
<%@ page import="org.myfirst.domain.Constants" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
	<meta charset="UTF-8">
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.1.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/resources/js/polyfiller.js"/>'></script>
	<title>My First - My Profile</title>
</head>

<body>
<%
	UserDto user = (UserDto)session.getAttribute("loggedUser");
%>
<jsp:include page="header.jsp">
    <jsp:param name="username" value="<%=user.getUsername()%>"/>
</jsp:include>

<div id="container">
    <div class="box form controls">
    <p class="list_title"> 
       		<img class="small_icon" src="<c:url value='/resources/images/account.png'/>" alt="My Profile"/>
			My Profile
    </p>
    <ul id="nav">
           <li id="generalLink"> 
           <a href="#" onclick="selectFromList('generalLink');">
           		<img class="small_icon" src="<c:url value='/resources/images/list.png'/>" alt="General"/>
				General
           </a>
           </li>
           <li id="passwordLink"  onclick="selectFromList('passwordLink');"> 
           <a href="#">
           		<img class="small_icon" src="<c:url value='/resources/images/swords.png'/>" alt="Password"/>
				Password
           </a>
           </li>
           <li class="active" id="interestsLink"> 
           <a href="/myfirst/interests">
           		<img class="small_icon" src="<c:url value='/resources/images/pokemon.png'/>" alt="Interests"/>
           		Interests
           </a>
           </li>
    </ul>
    </div> 
    <div id="wrapper">
        <div class="box form" id="interests">
            <form action="/myfirst/myprofile/interests" method="get"> 
            <fieldset>
				<p> 
                    <label for="newInterest">Add new interest tag:</label>
                    <input id="newInterest" name="newInterest" type="text"/>
                </p>

                </fieldset>
                <p class="save button"> 
                    <input type="submit" value="Add"/> 
                </p>
                <p>
                <%
                	for (ThingDto t: user.getInterests()) {
                %>
                	<%="#" + t.getTag() %><a href="#" onclick="removeInterest('<%=t.getTag()%>');">
                	<img class="removeIcon" id="remove<%=t.getTag()%>" src="<c:url value='/resources/images/delete.png'/>" alt="Delete"/></a>
                <%} %>
                </p>
            </form>
        </div> 
        </div>
    </div>        
</body>
</html>