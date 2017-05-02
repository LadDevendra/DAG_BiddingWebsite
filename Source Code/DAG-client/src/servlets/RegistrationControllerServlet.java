package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import utility.URL;

/**
 * Servlet implementation class RegistrationControllerServlet
 */
@WebServlet("/RegistrationControllerServlet")
public class RegistrationControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		//PrintWriter out=response.getWriter();
		
		String name=request.getParameter("user_name");
		String email=request.getParameter("user_email");
		String password=request.getParameter("user_password");
		String phone=request.getParameter("user_phone");
		String addr=request.getParameter("user_address");
		
		//update database webService
		
		Client client = Client.create();
		URL url = new URL();
		String urlString = url.getUrlPrefix();
		WebResource webResource = client.resource(urlString + "customer/register");
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("name", name);
		formData.add("emailId", email);
		formData.add("password", password);
		formData.add("contactNo", phone);
		formData.add("address", addr);
		ClientResponse restResponse = webResource
		    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		    .post(ClientResponse.class, formData);
		
		
		if (restResponse.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
		}
		
		String status= restResponse.getEntity(String.class);
		
		if(status.equalsIgnoreCase("true"))
		{
		request.setAttribute("email",email);
		RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
		}
		else
		{
			request.setAttribute("msg",status);
			RequestDispatcher rd=request.getRequestDispatcher("SignUp.jsp");
			rd.forward(request, response);
		}
		
	}

}
