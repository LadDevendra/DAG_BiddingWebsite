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
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import utility.URL;

/**
 * Servlet implementation class EditorControllerServlet
 */
@WebServlet("/EditorControllerServlet")
public class EditorControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditorControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
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
				
			
		String usr_name=request.getParameter("user_name");
		String email=request.getParameter("user_email");
		String password=request.getParameter("user_password");
		String phone=request.getParameter("user_phone");
		String addr=request.getParameter("user_address");
		
		String id= Long.toString((long) session.getAttribute("id")) ;
		String auth= Long.toString((long) session.getAttribute("auth")) ;
		
		Client client = Client.create();
		URL url = new URL();
		String urlString = url.getUrlPrefix();
		WebResource webResource = client.resource(urlString + "customer/updateProfile");
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("customerId", id);
		formData.add("authKey", auth);
		formData.add("name",usr_name);
		formData.add("password", password);
		formData.add("emailId", email);
		formData.add("contactNo",phone);
		formData.add("address",addr);
		ClientResponse restResponse = webResource
		    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		    .post(ClientResponse.class, formData);
		
		
		if (restResponse.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
		}
		session.setAttribute("USER", usr_name);
		request.setAttribute("edited",true);
		RequestDispatcher rd=request.getRequestDispatcher("AccountControllerServlet");
		rd.forward(request, response);	
			}
		}
	}

}
