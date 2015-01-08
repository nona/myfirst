<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page import="org.myfirst.dto.UserDto" %>
<c:url value="/results/records" var="resultsUrl"/>
<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.1.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>
<script type='text/javascript'>
	$(function() {
		urlHolder.search = '${resultsUrl}';
	});
</script>
<%UserDto user = (UserDto)session.getAttribute("loggedUser"); %>	
<table style="margin-bottom: 10px;">
	<tr>
		<td>
			<img id="logo" src="<c:url value='/resources/images/logo.png'/>" alt="Logo"/>
		</td>
		<td colspan="4">
			<div>
				<form action="/myfirst/search" method="get">
				    <input id="searchFor" type="text" name="q" placeholder="Search for things or people"/>
				    <input type="submit" value="" class="imgClass search"/>
				</form>
			</div>
		</td>
		<td>
			<%if(user.getProfilePhotoLink() != null && !user.getProfilePhotoLink().isEmpty()){ %>
	        <img class="profile_picture" src="<%=user.getProfilePhotoLink()%>" alt="Profile picture"/>
	        <%} else { %>
	        <img class="profile_picture" src="<c:url value='/resources/images/profile.png'/>" alt="Profile picture"/>
	        <%} %>
		</td>
	</tr>
	<tr class="tr_special">
		<td class="td_first">
			<a href="<c:url value='/home'/>" >
				<img class="small_icon" src="<c:url value='/resources/images/home.png'/>" alt="Home" title="Home"/>
				Home
			</a>
		</td>
		<td>
			<a href="<c:url value='/following'/>" >
				<img class="small_icon" src="<c:url value='/resources/images/runner.png'/>" alt="Following" title="Following"/>
				Following(<%=user.getFollowingCount() %>)
			</a>
		</td>
		<td>
			<a href="<c:url value='/followers'/>" >
					<img class="small_icon" src="<c:url value='/resources/images/fishing.png'/>" alt="Followers" title="Followers"/>
					Followers(<%=user.getFollowersCount() %>)
			</a>
		</td>
		<td>
			<a href="<c:url value='/myfirsts'/>" >
				<img class="small_icon" src="<c:url value='/resources/images/myfirst.png'/>" alt="My Firsts" title="The things you have done for first time"/>
				My Firsts(<%=user.getFirstThingsCount() %>)
			</a>
		</td>
		<td>
			<a href="<c:url value='/myprofile'/>" >
				<img class="small_icon" src="<c:url value='/resources/images/account.png'/>" alt="My Profile" title="View or edit profile, add interests"/>
				My Profile
			</a>
		</td>
		<td>
			<a href="<c:url value='/logout'/>" >
				<img class="small_icon" src="<c:url value='/resources/images/logout.png'/>" alt="Logout" title="Logout"/>
				${param["username"]}(Logout)
			</a>
		</td>
	</tr>
</table>