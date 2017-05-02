<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@page import="servlets.LoginBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>
<head>
  <title>Post Your Item</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <link rel="stylesheet" href="css/Post.css">

  <style>
    /* Remove the navbar's default rounded borders and increase the bottom margin */ 
    .navbar {
      margin-bottom: 50px;
      border-radius: 0;
    }
    
    /* Remove the jumbotron's default bottom margin */ 
     .jumbotron {
      margin-bottom: 0;
    }
   
    /* Add a gray background color and some padding to the footer */
    footer {
      background-color: #f2f2f2;
      padding: 25px;
    }
  </style>
</head>
<body>
<%
    String name=null;
	if (session == null)
  	{
		String address = "/NotLoggedIn.jsp";
    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
    	dispatcher.forward(request,response);
  	}
	else
	{
		name=(String)session.getAttribute("USER");
		
		if(name == null )
		{
			String address = "/NotLoggedIn.jsp";
	    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
	    	dispatcher.forward(request,response);
		}
		
	}
%>

<div class="jumbotron">
  <div class="container text-center">
    <h1>Dag Company</h1>      
    <p>Your Price, Your Way!</p>
  </div>
</div>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Hi, <%=name %>!</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><a href="welcome-page.jsp">Home</a></li>
        <li class="active"><a href="#">Your Posts</a></li>
        <li><a href="BidControllerServlet">Your Bids</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="AccountControllerServlet"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
        <li><a href="LogoutControllerServlet"><span class="glyphicon glyphicon-off"></span> Logout</a></li>
      </ul>
    </div>
  </div>
</nav>


<form action="PitemsControllerServlet" method="post">

  <div class="form-group">
    <label for="name">Product Name:</label>
    <input type="text" class="form-control" id="" name="name">
  </div>
  <div class="form-group">
    <label for="des">Description:</label>
    <textarea id="" name="des"></textarea>
  </div>
 <div class="form-group">
    <label for="price">Starting Price:</label>
    <input type="text" class="form-control" id="" name="price">
  </div>
  <div class="form-group">
    <label for="date">End Date:(dd-mm-yyyy)</p></label>
    <input type="text" class="form-control" id="" name="date">
    
  </div>
  <div class="form-group">
    <label for="time">End Time:(hh:mm:ss)</label>
    <input type="text" class="form-control" id="" name="time">
    
  </div>
 
  <button type="submit" class="btn btn-default">Submit</button>
  <hr>
  <button class="btn btn-default" onclick="window.location='PostControllerServlet';return false;">Cancel</button>

</form>


</body>
</html>