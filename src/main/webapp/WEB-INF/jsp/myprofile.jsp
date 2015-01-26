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
<script type='text/javascript'>
	$(function() {
		// init
		urlHolder.edit = '${editUrl}';
		urlHolder.password = '${passwordUrl}';
		urlHolder.interests = '${interestsUrl}';
		urlHolder.removeInterest = '${removeInterestUrl}';
		$('#general').submit(function(event) {
			event.preventDefault();
			editProfile();
		});
		$('#password').submit(function(event) {
			event.preventDefault();
			changePassword();
		});
		$('#interests').submit(function(event) {
			event.preventDefault();
			addInterest();
		});
	});

    webshims.setOptions('forms-ext', {types: 'date'});
	webshims.polyfill('forms forms-ext');
</script>	
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
           <li class="active" id="generalLink"> 
           <a href="#" onclick="selectFromList('generalLink');">
           		<img class="small_icon" src="<c:url value='/resources/images/list.png'/>" alt="General"/>
				General
           </a>
           </li>
           <li id="photoLink"> 
           <a href="#" onclick="selectFromList('photoLink');">
           		<img class="small_icon" src="<c:url value='/resources/images/frame.png'/>" alt="Profile picture"/>
				Profile Picture
           </a>
           </li>
           <li id="passwordLink"  onclick="selectFromList('passwordLink');"> 
           <a href="#">
           		<img class="small_icon" src="<c:url value='/resources/images/swords.png'/>" alt="Password"/>
				Password
           </a>
           </li>
           <li id="interestsLink"  onclick="selectFromList('interestsLink');"> 
           <a href="#">
           		<img class="small_icon" src="<c:url value='/resources/images/pokemon.png'/>" alt="Interests"/>
           		Interests
           </a>
           </li>
    </ul>
    </div> 
    <div id="wrapper">
	<div class="box form" id="general">
            <form> 
            <fieldset>
                <p> 
                    <label for="newFirstName" class="uname" data-icon="u">Your first name</label>
                    <input id="newFirstName" name="newFirstName" required="required" type="text" value="<%=user.getFirstName()%>"/>
                </p>
                <p>
                	<label for="newMiddleName" class="uname" data-icon="u" > Your middle name</label>
                    <input id="newMiddleName" name="newMiddleName" required="required" type="text" value="<%=user.getMiddleName() != null ? user.getMiddleName() : ""%>"/>
                </p>
                <p> 
                    <label for="newLastName" class="uname" data-icon="u">Your last name</label>
                    <input id="newLastName" name="newLastName" required="required" type="text" value="<%=user.getLastName()%>"/>
                </p>
                <p> 
                	<label for="newCountry" >Your country</label>
                    <select id="newCountry">
                    <option value="">-</option>
                    <%
                    	ArrayList<String> countries = Constants.getCountries();
                                                            for (String country: countries) {
                    %>
                      <option value="<%=country%>" <%if(country.equals(user.getCountry())) {%>selected="selected"<%}%>>
                      	<%=country%>
                      </option>
                    <%
                    	}
                    %>
                    </select>
                </p>
                <p>
                	<label for="newDateOfBirth"> Your date of birth</label>
                    <input id="newDateOfBirth" name="newDateOfBirth" required="required" type="date" value="<%=user.getDateOfBirth()%>"/>
                </p>
                <p> 
                    <label for="newEmail" class="youmail" data-icon="e" > Your email</label>
                    <input id="newEmail" name="newEmail" required="required" type="email" value="<%=user.getEmail()%>"/> 
                </p>
                </fieldset>
                <p class="save button"> 
                    <input type="submit" value="Save changes" class="submitBtn"/> 
                </p>
            </form>
        </div> 
        <div class="box form" id="photo"  style="display:none;">
	        <%
	        	if(user.getProfilePhotoLink() != null && !user.getProfilePhotoLink().isEmpty()){
	        %>
	        <img class="profile_picture" src="<%=user.getProfilePhotoLink()%>" alt="Profile picture"/>
	        <%
	        	} else {
	        %>
	        <img class="profile_picture" src="<c:url value='/resources/images/profile.png'/>" alt="Profile picture"/>
	        <%
	        	}
	        %>
		    <form action="/myfirst/myprofile/photo" method="post" enctype="multipart/form-data">
			        <input  type="file" name="file"/>
			        <p class="save button"> 
			        <input type="submit" value="Upload"  class="submitBtn"/>
			        </p>
			</form>
        </div> 
        <div class="box form" id="password" style="display:none;">
            <form> 
            <fieldset>
				<p> 
                    <label for="newPassword" class="youpasswd" data-icon="p">Your password </label>
                    <input id="newPassword" name="newPassword" required="required" type="password" placeholder="eg. X8df!90EO"/>
                </p>
                <p> 
                    <label for="repeatPassword" class="youpasswd" data-icon="p">Please confirm your password </label>
                    <input id="repeatPassword" name="repeatPassword" required="required" type="password" placeholder="eg. X8df!90EO"/>
                </p>
                </fieldset>
                <p class="save button"> 
                    <input type="submit" value="Save changes"  class="submitBtn"/> 
                </p>
            </form>
        </div> 
        <div class="box form" id="interests" style="display:none;">
            <form> 
            <fieldset>
				<p> 
                    <label for="newInterest">Add new interest tag:</label>
                    <input id="newInterest" name="newInterest" type="text"/>
                </p>

                </fieldset>
                <p class="save button"> 
                    <input type="submit" value="Add"  class="submitBtn"/> 
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