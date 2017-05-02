package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import utility.URL;

/**
 * Servlet implementation class SessionControllerServlet
 */
@WebServlet("/SessionControllerServlet")
public class SessionControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		//PrintWriter out=response.getWriter();
		
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		
//		LoginBean bean=new LoginBean();
//		bean.setName(name);
//		bean.setPassword(password);
//		//request.setAttribute("bean",bean);
		
		//boolean status=bean.validate();//forward the bean to webService for status
		
		Client client = Client.create();
		URL url = new URL();
		String urlString = url.getUrlPrefix();
		WebResource webResource = client.resource(urlString + "customer/logIn");
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("emailId", name);
		formData.add("password", password);
		ClientResponse restResponse = webResource
		    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		    .post(ClientResponse.class, formData);
		
		
		if (restResponse.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
		}

		String statusString = restResponse.getEntity(String.class);
		JSONParser parser = new JSONParser(); 
		JSONObject json = null;
		long auth=0;
		long id=0;
		String Uname="";
		try {
			json = (JSONObject) parser.parse(statusString);
			auth=(long)json.get("authKey");
			id=(long)json.get("userId");
			Uname=(String)json.get("name");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(id!=0){
			HttpSession session = request.getSession();
			session.setAttribute("USER", Uname);
			session.setAttribute("auth", auth);
			session.setAttribute("id", id);
			request.setAttribute("bean",statusString);
			RequestDispatcher rd=request.getRequestDispatcher("welcome-page.jsp");
			rd.forward(request, response);
		}
		else{
			RequestDispatcher rd=request.getRequestDispatcher("login-error.jsp");
			rd.forward(request, response);
		}
	}

}