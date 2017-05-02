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
 * Servlet implementation class BitemsControllerServlet
 */
@WebServlet("/BitemsControllerServlet")
public class BitemsControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BitemsControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
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
			else
			{

		
		String pid=request.getParameter("pid");
		String price=request.getParameter("price");
		
		String id= Long.toString((long) session.getAttribute("id")) ;
		String auth= Long.toString((long) session.getAttribute("auth")) ;
		
		Client client = Client.create();
		URL url = new URL();
		String urlString = url.getUrlPrefix();
		WebResource webResource = client.resource(urlString + "bid/bidForItem");
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("customerId", id);
		formData.add("authKey", auth);
		formData.add("amount",price);
		formData.add("productId", pid);
		ClientResponse restResponse = webResource
		    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		    .post(ClientResponse.class, formData);
		
		
		if (restResponse.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
		}
		String status = restResponse.getEntity(String.class);
		if(status.equals("true"))
		{
		request.setAttribute("bidded","true");
		RequestDispatcher rd=request.getRequestDispatcher("welcome-page.jsp");
		rd.forward(request, response);
		}
		else
		{
			request.setAttribute("msg",status);
			RequestDispatcher rd=request.getRequestDispatcher("ProductControllerServlet?pid="+pid);
			rd.forward(request, response);
		}
	        }
		}
	}
}
