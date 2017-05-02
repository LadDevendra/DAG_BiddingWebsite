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

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import utility.URL;

/**
 * Servlet implementation class PostControllerServlet
 */
@WebServlet("/PostControllerServlet")
public class PostControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostControllerServlet() {
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
			    Client client = Client.create();
				URL url = new URL();
				String urlString = url.getUrlPrefix();
				WebResource webResource = client.resource(urlString + "product/getMyProducts/"+id+"/"+auth);
				ClientResponse restResponse = webResource
					    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					    .get(ClientResponse.class);
					
					if (restResponse.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()+webResource);
					}
					
			  String json = restResponse.getEntity(String.class);
			  //json="{\"product\":"+json+"}";
//			  JSONParser parser = new JSONParser(); 
//			  JSONObject jsonO = null;
//			  try {
//				jsonO = (JSONObject) parser.parse(json);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			  JSONArray arr = new JSONArray(json); 
	
			  int n=arr.length();
			  
			  productBean[] p= new productBean[n];
			  for(int i=0;i<n;i++)
			  {
				  JSONParser parser = new JSONParser(); 
					JSONObject Jobj = null;
					try {
						Jobj = (JSONObject) parser.parse(arr.get(i).toString());
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				  p[i]=new productBean();
				  p[i].id=(long)Jobj.get("productId");
			      p[i].name=(String)Jobj.get("name");
			      p[i].des=(String)Jobj.get("description");
			      p[i].startBid=(long)Jobj.get("startBid");
			      p[i].endDate=(long)Jobj.get("sellDate");
			      p[i].result=(long)Jobj.get("winnerBidId");
			      
			  }
			
			  request.setAttribute("beans",p);
			  
				String address = "/Posts.jsp";
		    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		    	dispatcher.forward(request,response);
			}
		}
	}
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			//response.getWriter().append("Served at: ").append(request.getContextPath());
			doGet(request,response);
			/*HttpSession session = request.getSession();
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
					String address = "/Posts.jsp";
			    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
			    	dispatcher.forward(request,response);
				}
			}*/
		
	}

	

}
