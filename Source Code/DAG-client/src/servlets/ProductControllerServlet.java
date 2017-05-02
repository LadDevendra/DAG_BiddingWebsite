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
 * Servlet implementation class ProductControllerServlet
 */
@WebServlet("/ProductControllerServlet")
public class ProductControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductControllerServlet() {
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
			else
			{
				//get posts list from webService
				String id= Long.toString((long) session.getAttribute("id")) ;
			    //int Id=Integer.parseInt(id);
				String auth= Long.toString((long) session.getAttribute("auth")) ;
				String pid=request.getParameter("pid");
				
				Client client = Client.create();
				URL url = new URL();
				String urlString = url.getUrlPrefix();
				WebResource webResource = client.resource(urlString + "product/getProduct/"+id+"/"+auth+"/"+pid);
				ClientResponse restResponse = webResource
					    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					    .get(ClientResponse.class);
					
					if (restResponse.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()+webResource);
					}
					String json = restResponse.getEntity(String.class);
					JSONParser parser = new JSONParser(); 
					JSONObject Jobj = null;
					try {
						Jobj = (JSONObject) parser.parse(json);
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					productBean prod=new productBean();
					  prod.id=(long)Jobj.get("productId");
				      prod.name=(String)Jobj.get("name");
				      prod.des=(String)Jobj.get("description");
				      prod.startBid=(long)Jobj.get("startBid");
				      prod.endDate=(long)Jobj.get("sellDate");
				      prod.result=(long)Jobj.get("winnerBidId");
			
			  request.setAttribute("pbean",prod);
			  RequestDispatcher rd=request.getRequestDispatcher("ProductBit.jsp");
			  rd.forward(request, response);	
	        }
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
