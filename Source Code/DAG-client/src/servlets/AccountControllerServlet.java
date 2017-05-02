package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import utility.URL;


/**
 * Servlet implementation class AccountControllerServlet
 */
@WebServlet("/AccountControllerServlet")
public class AccountControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    //response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		if (session == null)
	  	{
			String address = "/NotLoggedIn.jsp";
	    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
	    	dispatcher.forward(request,response);
	  	}
		else
		{
			String name=(String)session.getAttribute("USER");
			
			if(name == null) 
			{
				String address = "/NotLoggedIn.jsp";
		    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		    	dispatcher.forward(request,response);
			}
			else{
				//get user info data from the webServices
				
				String id= Long.toString((long) session.getAttribute("id")) ;
			    //int Id=Integer.parseInt(id);
				String auth= Long.toString((long) session.getAttribute("auth")) ;
			    Client client = Client.create();
				URL url = new URL();
				String urlString = url.getUrlPrefix();
				WebResource webResource = client.resource(urlString + "customer/getUserProfile/"+id+"/"+auth);
				ClientResponse restResponse = webResource
					    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					    .get(ClientResponse.class);
					
					if (restResponse.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()+webResource);
					}
					
					String statusString = restResponse.getEntity(String.class);
					JSONParser parser = new JSONParser(); 
					JSONObject json;
					String Uname =null;
					String emailId=null;
					String password=null;
					String contactNo=null;
					String address=null;
					String Lloc=null;
					long Ltime=0;
					try {
						json = (JSONObject) parser.parse(statusString);
						Uname=(String)json.get("name");
						emailId=(String)json.get("emailId");
						password=(String)json.get("password");
						contactNo=(String)json.get("contactNo");
						address=(String)json.get("address");
						Lloc=(String)json.get("lastLocation");
						Ltime=(long)json.get("lastLogin");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				request.setAttribute("name",Uname);
				request.setAttribute("email",emailId);
				request.setAttribute("password",password);
				request.setAttribute("phone",contactNo);
				request.setAttribute("addr",address);
				request.setAttribute("Lloc",Lloc);
				request.setAttribute("Ltime",Ltime);
				
				
				RequestDispatcher rd=request.getRequestDispatcher("YourAcct.jsp");
				rd.forward(request, response);	
				
			}
		}
		
		
		 
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
//		response.setContentType("text/html");
//		HttpSession session = request.getSession();
//		if (session == null)
//	  	{
//			String address = "/NotLoggedIn.jsp";
//	    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
//	    	dispatcher.forward(request,response);
//	  	}
//		else
//		{
//			String name=(String)session.getAttribute("USER");
//			
//			if(name == null) 
//			{
//				String address = "/NotLoggedIn.jsp";
//		    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
//		    	dispatcher.forward(request,response);
//			}
//			else{
//				//get user info data from the webServices
//				
//				request.setAttribute("name","gayatri");
//				request.setAttribute("email","gayatriprabhu04@gmail.com");
//				request.setAttribute("password","admin");
//				request.setAttribute("phone","222-222-2222");
//				request.setAttribute("addr","TX 75252, USA");
//				
//				
//				RequestDispatcher rd=request.getRequestDispatcher("YourAcct.jsp");
//				rd.forward(request, response);	
//				
//			}
//		}
		
	}
	

}
