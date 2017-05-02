package controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import utility.URL;

@Path("/product")
public class ProductController {

	@POST
	@Path("/postItemForSale")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String postItemForSale(@FormParam("customerId") String customerId, @FormParam("authKey") String authKey, @FormParam("name") String name,
			@FormParam("description") String description, @FormParam("startBid") String startBid, @FormParam("sellDate") String sellDate)
	{
		String response = "", response1 = "";
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
				return  "User is not logged in, please log in before you use further Services.";
			}
			else {
				WebResource webResource1 = client.resource(urlString + "product/createProduct");
				MultivaluedMap formData1 = new MultivaluedMapImpl();
				formData1.add("customerId", customerId);
				formData1.add("name", name);
				formData1.add("description", description);
				formData1.add("startBid", startBid);
				formData1.add("sellDate", sellDate);
				
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .post(ClientResponse.class, formData1);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				response1 = restResponse1.getEntity(String.class);
				//case handling: user not found
				if (!response1.toString().equals(Boolean.TRUE.toString())){
					return "User not found";
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
		return Boolean.TRUE.toString();
	}
	
	@POST
	@Path("/searchForProduct")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String searchForProduct(@FormParam("customerId") String customerId, @FormParam("authKey") String authKey, @FormParam("keyWord") String keyWord)
	{
		String response = "", response1 = "";
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
				return  "User is not logged in, please log in before you use further Services.";
			}
			else {
				WebResource webResource1 = client.resource(urlString + "product/searchForProduct");
				MultivaluedMap formData1 = new MultivaluedMapImpl();
				formData1.add("keyWord", keyWord);
				
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .post(ClientResponse.class, formData1);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				response1 = restResponse1.getEntity(String.class);
				//case handling: user not found
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
		return response1;
	}
	
	@GET
	@Path("/getMyProducts/{customerId}/{authKey}")
	public Response getMyProducts(@PathParam("customerId") int customerId,
									@PathParam("authKey") int authKey)
	{
		//check availability
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
				WebResource webResource1 = client.resource(urlString + "product/getMyProducts/" + customerId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .get(ClientResponse.class);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				String jsonResponse = restResponse1.getEntity(String.class);
				System.out.println("MyProducts: " + jsonResponse);
				return Response.ok(jsonResponse, MediaType.TEXT_PLAIN).build();
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
	}
	
	@GET
	@Path("/getProduct/{customerId}/{authKey}/{productId}")
	public Response getMyProducts(@PathParam("customerId") int customerId,
									@PathParam("authKey") int authKey, @PathParam("productId") int productId )
	{
		//check availability
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
				WebResource webResource1 = client.resource(urlString + "product/getProduct/" + productId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .get(ClientResponse.class);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				String jsonResponse = restResponse1.getEntity(String.class);
				System.out.println("MyProduct: " + jsonResponse);
				return Response.ok(jsonResponse, MediaType.TEXT_PLAIN).build();
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
	}

	
	@GET
	@Path("/deleteProduct/{customerId}/{authKey}/{productId}")
	public String deleteProduct(@PathParam("productId") int productId, @PathParam("customerId") int customerId,
									@PathParam("authKey") int authKey)
	{
		//check availability
		String response = "", response1 = "";
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
				return  "User is not logged In, Please log in before you use further Services";
			}
			else {
				WebResource webResource1 = client.resource(urlString + "product/deleteProduct/" + productId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .get(ClientResponse.class);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				response1 = restResponse1.getEntity(String.class);
				//case handling: product does not exist
				if (!response1.toString().equals(Boolean.TRUE.toString())){
					return "Product does not exist";
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
		return Boolean.TRUE.toString();
	}
}
