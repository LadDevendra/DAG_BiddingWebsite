<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Your Account</title>
        <link href='http://fonts.googleapis.com/css?family=Nunito:400,300' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
        <link rel="stylesheet" href="css/SignUp.css">
        <link rel="stylesheet" href="css/style.css">
        <style type="text/css">
        .blue{
        border-style: solid;
    border-color: #0000ff;
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
	String usr_name=(String)request.getAttribute("name");
	String usr_email=(String)request.getAttribute("email");
	String usr_password=(String)request.getAttribute("password");
	String usr_phone=(String)request.getAttribute("phone");
	String usr_addr=(String)request.getAttribute("addr");
	String Lloc=(String)request.getAttribute("Lloc");
	long Ltime=0;
	if(request.getAttribute("Ltime")!=null)
	Ltime=(long)request.getAttribute("Ltime");
	boolean edited;
	if(request.getAttribute("edited")!=null)
	    edited=(boolean)request.getAttribute("edited");
	else
		edited=false;
		
    %>
    
    
    <div class="top">
			<h1 id="title" ><span id="logo">Dag Company</span></h1>
	</div>
	
    <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Hi, <%=name%>!</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li ><a href="welcome-page.jsp">Home</a></li>
        <li><a href="PostControllerServlet">Your Posts</a></li>
        <li><a href="BidControllerServlet">Your Bids</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="active"><a href="#"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
        <li><a href="LogoutControllerServlet"><span class="glyphicon glyphicon-off"></span> Logout</a></li>
      </ul>
    </div>
  </div>
</nav>
<%
if (edited == true) 
{ 
%>
<p id="txt" align="center" style="color:red; font-size: 20px;"><b>Saved Successfully</b></p>
 <%}%>  
 <%
if (Lloc != null) 
{ 
%>
<p  float="left" style="color:red; font-size: 20px;" id="dt"></p>
<p  float="right" style="color:red; font-size: 20px;">Last Login location:<%=Lloc %></p>
 <%}%>      
      
    <form action="EditorControllerServlet" method="post">

      
        <h1 >Your Information</h1>
        
        <fieldset>
          
          <label for="name">Name:</label>
          <textarea id="name" class="ip" name="user_name"  readonly><%=name %></textarea>
          
          <label for="mail">EmailId:</label>
          <input type="email" class="ip" name="user_email" value=<%=usr_email %> readonly>
          
          <label for="password">Password:</label>
          <input type="password" class="ip" name="user_password" value=<%=usr_password %> readonly>
          
          <label for="phone">Contact No.:</label>
		  <input type="tel" class="ip" name="user_phone" value=<%=usr_phone %> readonly>
   
          <label for="address">Address:</label>
          <textarea class="ip" name="user_address"  readonly><%=usr_addr %></textarea>
          
          
        </fieldset>

       <button id="submit" type="submit" style="display: none;">Save</button> 
       <button id="edit" type="button" onclick="editable()" >Edit</button>
       
      </form>
     <!--  <div style="width:50%; margin-left:auto; margin-right:auto;"><button id="edit"  onclick="editable()" >Edit</button></div>
     --> 
      
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
      <script>
      function editable(){
    	  
    	  document.getElementById('edit').style.display = 'none';
    	  var txt=document.getElementById('txt');
    	  if(txt!=null)
    		  {
    		    txt.style.display = 'none';
    		  }
    	  document.getElementById('submit').style.display = 'block';
    	  
    	  document.getElementById("name").focus();
    	  var x=document.getElementsByClassName("ip");
    	  var i;
    	  for(i=0;i<5;i++)
    		  {
    		  x[i].readOnly = false;
    		  x[i].style.borderStyle = "solid";
    		  x[i].style.borderColor = "#1ac6ff";
    		  }
    	  
      }
      
      </script>
      <script >
function toDateTime(secs) {
	var d = new Date(secs);
    var n = d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
    return n;
}
<%if(request.getAttribute("Ltime")!=null) {%>
var s=toDateTime(<%=Ltime %>);
document.getElementById("dt").innerHTML="Last Login time:"+s;
<%}%>
</script>

    </body>
</html>