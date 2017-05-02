<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@page import="servlets.LoginBean"%>
<%@page import="servlets.productBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>
<head>
  <title>Bid here</title>
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
	
	productBean prod=(productBean)request.getAttribute("pbean");
	String txt="";
	long stat=prod.result;
	if(stat==0)
		txt="On going";
	else if(stat==-1)
		txt="Expired, not sold ";
	else
		txt="Sold ";
	
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
      <a class="navbar-brand" href="#">Hi, <%//=name %>!</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><a href="welcome-page.jsp">Home</a></li>
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
<%
String msg= (String)request.getAttribute("msg");
%>
<p style="color:red; font-size: 20px;"><b><%=((msg==null)?"":msg)%></b></p>

<form action="BitemsControllerServlet" method="post">

  <div class="form-group">
     <h3 style="display:inline"><span class="label label-default">Product Id:</span> <h2 style="display:inline"><small><%=prod.id %></small></h2></h3>  
  </div>
  
 <div class="form-group">
     <h3 style="display:inline"><span class="label label-default">Product Name:</span> <h2 style="display:inline"><small><%=prod.name %></small></h2></h3>
  </div>
  
  <div class="form-group">
     <h3><span class="label label-default">Description:</span><h2><small><p class="text-justify"><%=prod.des %></p></small></h2></h3>
  </div>
 
  <div class="form-group">
     <h3 style="display:inline"><span class="label label-default">Starting Price:</span> <h2 style="display:inline"><small><%=prod.startBid %></small></h2></h3>
  </div>
  
  <div class="form-group">
     <h3 style="display:inline"><span class="label label-default">End Date:</span> <h2 style="display:inline"><small id="dt"></small></h2></h3>
  </div>
  
   <div id="pid" style="display: none;" class="form-group">
    <input type="text" class="form-control" id="" name="pid" value=<%=prod.id %>>
  </div>
  
  <div id="prc" style="display: none;" class="form-group">
    <label for="price">Your Price:</label>
    <input type="text" class="form-control" id="" name="price">
  </div>
  
  <button id="submit" type="submit" style="display: none;">Submit</button> 
  <button id="bid" type="button" onclick="myFun()">Bid</button>
  
</form>

<script >
function myFun()
{
	document.getElementById('bid').style.display = 'none';
	document.getElementById('submit').style.display = 'block';
	document.getElementById('prc').style.display = 'block';
	
	
	}

</script>
<script >
function toDateTime(secs) {
	var d = new Date(secs);
    var n = d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
    return n;
}
var s=toDateTime(<%=prod.endDate %>);
document.getElementById("dt").innerHTML=s;
</script>

</body>
</html>