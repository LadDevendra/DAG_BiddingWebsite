<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login Page</title>

	<!-- Google Fonts -->
	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900' rel='stylesheet' type='text/css'>
	
	<link rel="stylesheet" href="css/animate.css">
	
	<!-- Custom Stylesheet -->
	<link rel="stylesheet" href="css/style.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>

<body>
<%
String email= (String)request.getAttribute("email");
%>

<div class="container">
		<div class="top">
			<h1 id="title" class="hidden"><span id="logo">Dag Company</span></h1>
		</div>
		
		<div class="login-box animated fadeInUp">
			<div class="box-header">
				<h2>Log In</h2>
			</div>
			<form action="SessionControllerServlet" method="post">
			<label for="username">EmailId</label>
			<br/>
			<input type="text" id="username" name="name" value=<%=((email==null)?"":email)%>>
			<br/>
			<label for="password">Password</label>
			<br/>
			<input type="password" id="password" name="password">
			<br/>
			<button type="submit" value="login">Sign In</button>
			<br/>
			</form>
			<a href="SignUp.jsp"><p class="small">New user?Click here to register</p></a>
		</div>
</div>

<script>
	$(document).ready(function () {
    	$('#logo').addClass('animated fadeInDown');
    	$("input:text:visible:first").focus();
	});
	$('#username').focus(function() {
		$('label[for="username"]').addClass('selected');
	});
	$('#username').blur(function() {
		$('label[for="username"]').removeClass('selected');
	});
	$('#password').focus(function() {
		$('label[for="password"]').addClass('selected');
	});
	$('#password').blur(function() {
		$('label[for="password"]').removeClass('selected');
	});
</script>


</body>
</html>