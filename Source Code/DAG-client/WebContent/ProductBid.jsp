<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@page import="servlets.LoginBean"%>
<%@page import="servlets.productBean"%>
<%@page import="servlets.pidBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>
<head>
  <title>Bids</title>
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
  <link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
  <script>
$(document).ready(function() {
    $('#dynamic_table').dataTable();
} );
</script>
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
	pidBean[] p=(pidBean[])request.getAttribute("beans");
	productBean prod=(productBean)request.getAttribute("pbean");
	String txt="";
	long stat=prod.result;
	if(stat==0)
		txt="Sale on going";
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

<div style="display:inline; text-align: center;" >
<h3 style="display:inline"><span class="label label-default">Product Id:</span> <h2 style="display:inline"><small><%=prod.id %> &nbsp; &nbsp; &nbsp; </small></h2></h3>  
<h3 style="display:inline"><span class="label label-default">Product Name:</span> <h2 style="display:inline"><small><%=prod.name %> &nbsp; &nbsp; &nbsp; </small></h2></h3>
<h3 style="display:inline"><span class="label label-default">Starting Price:</span> <h2 style="display:inline"><small><%=prod.startBid %> &nbsp; &nbsp; &nbsp; </small></h2></h3>
<h3 style="display:inline"><span class="label label-default">End Date:</span> <h2 style="display:inline"><small id="dt"></small></h2></h3>
<h3 style="display:inline"><span class="label label-default">Status:</span> <h2 style="display:inline"><small><%=txt %> &nbsp; &nbsp; &nbsp; </small></h2></h3>

</div>

<br>
<br>

<div class="container">
		<div class="col-lg-12">
			<table class="table table-striped table-hover" id="dynamic_table">
			<thead>
				<tr >
				    
					<th > Bidder's Name </th>
					<th > Bidded Price </th>
				
				</tr>
			</thead>
			<tbody id="table_body">
                    <tr id="hide" >
				    <td id ="" ></td>
					<td id ="" ></td>
				</tr>
			 </tbody>
			</table>
		</div>
		
<a href="PostControllerServlet"><button id="add_item" class = "btn btn-default" style="margin:auto; display:block; width:20%">OK</button></a>
		
		
	</div>
<script>
	document.getElementById("hide").style.display="none";
	var myTable = document.getElementById("dynamic_table");
	<%
	 int l=p.length;;
	 int i=0;
	 for(i=0;i<l;i++)
	 {
	 %> 
		 var row = myTable.insertRow(1);
		row.insertCell(0).innerHTML = " <%=p[i].Bidder%>";
		row.insertCell(1).innerHTML = " <%=p[i].amt%>";
		
		
<%}%> 

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