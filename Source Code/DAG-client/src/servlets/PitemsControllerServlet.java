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
 * Servlet implementation class PitemsControllerServlet
 */
@WebServlet("/PitemsControllerServlet")
public class PitemsControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PitemsControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("text/html");
		String name=request.getParameter("name");
		String des=request.getParameter("des");
		String price=request.getParameter("price");
		String date=request.getParameter("date");
		String time=request.getParameter("time");
		
		HttpSession session = request.getSession();
		String id= Long.toString((long) session.getAttribute("id")) ;
		String auth= Long.toString((long) session.getAttribute("auth")) ;
		
		Client client = Client.create();
		URL url = new URL();
		String urlString = url.getUrlPrefix();
		WebResource webResource = client.resource(urlString + "product/postItemForSale");
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("customerId", id);
		formData.add("authKey", auth);
		formData.add("name", name);
		formData.add("description", des);
		formData.add("startBid", price);
		formData.add("sellDate", date+" "+time);
		ClientResponse restResponse = webResource
		    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		    .post(ClientResponse.class, formData);
		
		
		if (restResponse.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
		}
		
		RequestDispatcher rd=request.getRequestDispatcher("PostControllerServlet");
		rd.forward(request, response);
	}

}
