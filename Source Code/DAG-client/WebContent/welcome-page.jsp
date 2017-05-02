<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@page import="servlets.LoginBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>
<head>
  <title>Home Page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
<body onload="myFunction()">
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
		
		//LoginBean bean=(LoginBean)request.getAttribute("bean");
		if(name == null )//|| bean == null || name.compareTo(bean.getName()) != 0)
		{
			String address = "/NotLoggedIn.jsp";
	    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
	    	dispatcher.forward(request,response);
		}
		
	}
	boolean bidded;
	if(request.getAttribute("bidded")!=null)
	    bidded=true;
	else
		bidded=false;
%>

<%
if (bidded == true) 
{ 
%>
<script>
function myFunction() {
	alert("Bid Successful");
}
	


</script>
 <%}%> 
 <% String p=(String)request.getAttribute("bean"); %>
 <%-- <p><%=p %></p>  --%>

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
      <a class="navbar-brand" href="#">Hi, <%=name%> !</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="PostControllerServlet">Your Posts</a></li>
        <li><a href="BidControllerServlet">Your Bids</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="AccountControllerServlet"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
        <li><a href="LogoutControllerServlet"><span class="glyphicon glyphicon-off"></span> Logout</a></li>
      </ul>
    </div>
  </div>
</nav>


<footer class="container-fluid text-center">  
  <form action="SearchControllerServlet" method="post" class="form-inline">Get items to bid on:
    <input type="text" class="form-control" size="50" name="keyword">
    <button type="submit" class="btn btn-danger">Search</button>
  </form>
</footer>


</body>
</html>

