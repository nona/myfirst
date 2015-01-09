<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:url value="/login/signin" var="signinUrl"/>
<c:url value="/login/register" var="registerUrl"/>


<html>
<head>
	<meta charset="UTF-8">
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/animate-custom.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.1.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>

	<title>Log In or Register</title>
	<script type='text/javascript'>
	$(function() {
		// init
		urlHolder.signin = '${signinUrl}';
		urlHolder.register = '${registerUrl}';
		$('#login').submit(function(event) {
			event.preventDefault();
			signIn();
		});
		$('#register').submit(function(event) {
			event.preventDefault();
			register();
		});

	});
	</script>
</head>

<body>
	<img id="logo" src="<c:url value='/resources/images/logo.png'/>" alt="Logo"/>

	<div id="container">
	<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
	<a class="hiddenanchor" id="toregister"></a>
    <a class="hiddenanchor" id="tologin"></a>
    <div id="wrapper">
        <div id="login" class="animate form">
            <form autocomplete="on"> 
            <fieldset>
                <h1>Log in</h1> 
                <p> 
                    <label for="email" class="youmail" data-icon="e" > Your email</label>
                    <input id="email" name="email" required="required" type="text" placeholder="mymail@mail.com"/>
                </p>
                <p> 
                    <label for="password" class="youpasswd" data-icon="p"> Your password </label>
                    <input id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO" /> 
                </p>

			</fieldset>
                <p class="login button"> 
                    <input type="submit" value="Login" class="submitBtn"/> 
                </p>
                <p class="change_link">
                    Not a member yet ?
                    <a href="#toregister" class="to_register">Join us</a>
                </p>

			<input type='submit' value='Sign In'  class="submitBtn"/>
            </form>
        </div>
 
        <div id="register" class="animate form">
            <form autocomplete="on"> 
            <fieldset>
                <h1> Sign up </h1> 
                <p> 
                    <label for="newUsername" class="uname" data-icon="u">Your username</label>
                    <input id="newUsername" name="newUsername" required="required" type="text"/>
                </p>
                <p> 
                    <label for="newFirstName" class="uname" data-icon="u">Your first name</label>
                    <input id="newFirstName" name="newFirstName" required="required" type="text"/>
                </p>
                <p> 
                    <label for="newLastName" class="uname" data-icon="u">Your last name</label>
                    <input id="newLastName" name="newLastName" required="required" type="text"/>
                </p>
                <p> 
                    <label for="newEmail" class="youmail" data-icon="e" > Your email</label>
                    <input id="newEmail" name="newEmail" required="required" type="email" placeholder="mysupermail@mail.com"/> 
                </p>
                <p> 
                    <label for="newPassword" class="youpasswd" data-icon="p">Your password </label>
                    <input id="newPassword" name="newPassword" required="required" type="password" placeholder="eg. X8df!90EO"/>
                </p>
                <p> 
                    <label for="repeatPassword" class="youpasswd" data-icon="p">Please confirm your password </label>
                    <input id="repeatPassword" name="repeatPassword" required="required" type="password" placeholder="eg. X8df!90EO"/>
                </p>
                </fieldset>
                <p class="signin button"> 
                    <input type="submit" value="Sign up"  class="submitBtn"/> 
                </p>
                <p class="change_link">  
                    Already a member ?
                    <a href="#tologin" class="to_register"> Go and log in </a>
                </p>
            </form>
        </div>         
    </div>
</div>  
	
</body>
</html>