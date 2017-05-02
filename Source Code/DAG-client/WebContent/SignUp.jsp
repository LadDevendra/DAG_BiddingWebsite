<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sign Up Form</title>
        <link href='http://fonts.googleapis.com/css?family=Nunito:400,300' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="css/SignUp.css">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
    <%
String msg= (String)request.getAttribute("msg");
%>
<p style="color:red; font-size: 20px;"><b><%=((msg==null)?"":msg)%></b></p>

      <form action="RegistrationControllerServlet" method="post">
      
        <h1>Sign Up</h1>
        
        <fieldset>
          <legend>Your Profile</legend>
          <label for="name">Name:</label>
          <input type="text" id="name" name="user_name">
          
          <label for="mail">EmailId:</label>
          <input type="email" id="mail" name="user_email">
          
          <label for="password">Password:</label>
          <input type="password" id="password" name="user_password">
          
          <label for="phone">Contact No.:</label>
		  <input type="tel" id="phone" name="user_phone">
   
          <label for="address">Address:</label>
          <textarea id="address" name="user_address"></textarea>
        </fieldset>

        <button type="submit">Sign Up</button>
      </form>

    </body>
</html>