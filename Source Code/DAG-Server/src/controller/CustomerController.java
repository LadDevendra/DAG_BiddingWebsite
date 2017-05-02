package controller;

import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONString;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import entity.UserLogInData;
import utility.URL;


@Path("/customer")
public class CustomerController {

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String register(@FormParam("name") String name, @FormParam("password") String password, @FormParam("contactNo") String contactNo,
			@FormParam("address") String address, @FormParam("emailId") String emailId)
	{
		//check availability
		Boolean status = false;
		try { 
			Client client = Client.create();
			URL url = new URL();
			String urlString = url.getUrlPrefix();
			WebResource webResource = client.resource(urlString + "customer/isEmailIdAvailable");
			MultivaluedMap formData = new MultivaluedMapImpl();
			formData.add("emailId", emailId);
			ClientResponse restResponse = webResource
			    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			    .post(ClientResponse.class, formData);
			
			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}
 
			String statusString = restResponse.getEntity(String.class);
			status = Boolean.parseBoolean(statusString);
			
			if(!status) {
				return "EmailId already registered. Please try with another emailId.";
			} else {				
				WebResource webResource1 = client.resource(urlString + "customer/insertUserData");
				
				MultivaluedMap formData1 = new MultivaluedMapImpl();
				formData1.add("name", name);
				formData1.add("password", password);
				formData1.add("contactNo", contactNo);
				formData1.add("address", address);
				formData1.add("emailId", emailId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .post(ClientResponse.class, formData1);
				//204 - no content & thats fine here
				if (restResponse1.getStatus() != 204) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				
				WebResource webResource2 = client.resource(urlString + "customer/sendEmail");
				MultivaluedMap formData2 = new MultivaluedMapImpl();
				formData2.add("type", "1");
				formData2.add("emailId", emailId);
				ClientResponse restResponse2 = webResource2
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .post(ClientResponse.class, formData2);
				
				if (restResponse2.getStatus() != 204) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse2.getStatus());
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
		return Boolean.TRUE.toString();
	}

	@POST
	@Path("/updateProfile")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateProfile(@FormParam("customerId") String customerId, @FormParam("authKey") String authKey, @FormParam("name") String name, @FormParam("password") String password, @FormParam("contactNo") String contactNo,
			@FormParam("address") String address, @FormParam("emailId") String emailId)
	{
		//check availability
		String response;
		try { 
			Client client = Client.create();
			URL url = new URL();
			String urlString = url.getUrlPrefix();
			WebResource webResource = client.resource(urlString + "customer/isLoggedIn/" + customerId + "/" + authKey);
			ClientResponse restResponse = webResource
			    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			    .get(ClientResponse.class);
			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}
			response = restResponse.getEntity(String.class);
			Boolean status = Boolean.parseBoolean(response);
			
			if(status == false)
			{
				return  Response.ok("User is not logged In, Please log in before you use further Services", MediaType.TEXT_PLAIN).build();
				
			} else {				
				WebResource webResource1 = client.resource(urlString + "customer/updateProfile");
				
				MultivaluedMap formData1 = new MultivaluedMapImpl();
				formData1.add("customerId", customerId);
				formData1.add("name", name);
				formData1.add("password", password);
				formData1.add("contactNo", contactNo);
				formData1.add("address", address);
				formData1.add("emailId", emailId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .post(ClientResponse.class, formData1);
				
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				response = restResponse1.getEntity(String.class);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
		return Response.ok(response , MediaType.TEXT_PLAIN).build();
	}
	
	@POST
	@Path("/logIn")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public UserLogInData logIn(@FormParam("password") String password,@FormParam("emailId") String emailId)
	{
		//check availability
		int authKey = 0;
		int userId = 0;
		UserLogInData userLogInData = new UserLogInData();
		try { 
			Client client = Client.create();
			URL url = new URL();
			String urlString = url.getUrlPrefix();
			WebResource webResource = client.resource(urlString + "customer/checkLoginCred");
			MultivaluedMap formData = new MultivaluedMapImpl();
			formData.add("emailId", emailId);
			formData.add("password", password);
			ClientResponse restResponse = webResource
			    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			    .post(ClientResponse.class, formData);
			
			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}
 
			String statusString = restResponse.getEntity(String.class);
			JSONObject jsonObject = new JSONObject(statusString);
			authKey = Integer.parseInt(jsonObject.getString("authKey"));
			userLogInData.setAuthKey(authKey);
			userLogInData.setName(jsonObject.getString("name"));
			System.out.println("AuthKey: " + authKey);
			
			if(authKey == 0)
			{
				return userLogInData;
			}
			else
			{
				WebResource webResource1 = client.resource(urlString + "customer/getUserId");
				
				MultivaluedMap formData1 = new MultivaluedMapImpl();
				formData1.add("emailId", emailId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .post(ClientResponse.class, formData1);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				String statusString1 = restResponse1.getEntity(String.class);
				userId =Integer.parseInt(statusString1);
				userLogInData.setUserId(userId);
				System.out.println("UserId: " + userId);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
		return userLogInData;
	}
	
	@GET
	@Path("/logOut/{customerId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String logOut(@PathParam("customerId") int customerId)
	{
		String response = "";
		try { 
			Client client = Client.create();
			URL url = new URL();
			String urlString = url.getUrlPrefix();
			WebResource webResource = client.resource(urlString + "customer/logOut/" + customerId);
			ClientResponse restResponse = webResource
			    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			    .get(ClientResponse.class);
			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}
			response = restResponse.getEntity(String.class);
			
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
	return response;
	}
	
	@GET
	@Path("/getUserProfile/{customerId}/{authKey}")
	public Response getUserProfile(@PathParam("customerId") int customerId,
									@PathParam("authKey") int authKey)
	{
		String response = "";
		try { 
			Client client = Client.create();
			URL url = new URL();
			String urlString = url.getUrlPrefix();
			WebResource webResource = client.resource(urlString + "customer/isLoggedIn/" + customerId + "/" + authKey);
			ClientResponse restResponse = webResource
			    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			    .get(ClientResponse.class);
			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}
			response = restResponse.getEntity(String.class);
			Boolean status = Boolean.parseBoolean(response);
			
			if(status == false)
			{
				return  Response.ok("User is not logged In, Please log in before you use further Services", MediaType.TEXT_PLAIN).build();
			}
			else {
				WebResource webResource1 = client.resource(urlString + "customer/getUserProfile/" + customerId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .get(ClientResponse.class);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				String jsonResponse = restResponse1.getEntity(String.class);
				System.out.println("UserProfile: " + jsonResponse);
				return Response.ok(jsonResponse, MediaType.TEXT_PLAIN).build();
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
	//return Response.ok(response, MediaType.TEXT_PLAIN).build();
	}


}
