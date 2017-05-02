<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@page import="servlets.LoginBean"%>
<%@page import="servlets.productBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>
<head>
  <title>Home Page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!--   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
 -->  
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
  <link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
  <script>
$(document).ready(function() {
    $('#dynamic_table').dataTable();
} );
</script>
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
	productBean[] p=(productBean[])request.getAttribute("beans"); 
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
 <%-- <% String p=(String)request.getAttribute("bean"); %> --%>
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
        <li class="active"><a href="welcome-page.jsp">Home</a></li>
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

<br>
<br>

<div class="container">
		<div class="col-lg-12">
			<table class="table  table-striped table-hover" id="dynamic_table">
			<thead>
				<tr >
				    <th > Product Id </th>
					<th > Product Name </th>
					<th width="40%"> Description </th>
					<th > Starting Price </th>
					<th > End Date </th>
					<th style='border-color: #ffffff; background-color:#ffffff' class = "text-center"></th>
					
				</tr>
			</thead>
			<tbody id="table_body">
				<tr id="hide" >
				    <td id ="" ></td>
					<td id ="" ></td>
					<td id ="" ></td>
					<td id ="" ></td>
					<td id ="" ></td>
					<td style='border-color: #ffffff; background-color:#ffffff'></t>
				</tr>

			 </tbody>
			</table>
		</div>
	</div>


	<!--  <script>
		$(document).ready(function() {
			

			$("#dynamic_table").on('click','#button',function(){
        		$(this).parent().parent().remove();
   			 });
				

		});
	</script>   --> 
	<script>
	document.getElementById("hide").style.display="none";
	var myTable = document.getElementById("dynamic_table");
	<%
	 int l=p.length;;
	 int i=0;
	 for(i=0;i<l;i++)
	 {
		 if(p[i].result==0){
	 %> 
		 var row = myTable.insertRow(1);
		row.insertCell(0).innerHTML = " <%=p[i].id %>";
		row.insertCell(1).innerHTML = " <%=p[i].name %>";
		row.insertCell(2).innerHTML = " <%=p[i].des %>";
		row.insertCell(3).innerHTML = " <%=p[i].startBid %>";
		var s=toDateTime(<%=p[i].endDate %>);
		row.insertCell(4).innerHTML = s;
		var td=row.insertCell(5);
				
		        td.innerHTML="<a href='ProductControllerServlet?pid=<%=p[i].id %>' class = 'btn btn-default' >Select Item</a>"
				td.style.borderColor="#ffffff";
		        td.style.backgroundColor="#ffffff";
		
<%}}%> 
function toDateTime(secs) {
	var d = new Date(secs);
    var n = d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
    return n;
}
	</script>



</body>
</html>

