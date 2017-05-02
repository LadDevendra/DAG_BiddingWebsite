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

@Path("/bid")
public class BidController {

	@POST
	@Path("/bidForItem")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String bidForItem(@FormParam("customerId") String customerId, @FormParam("productId") String productId,
			@FormParam("amount") String amount, @FormParam("authKey") String authKey)
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
				WebResource webResource1 = client.resource(urlString + "bid/bidForItem/");
				MultivaluedMap formData1 = new MultivaluedMapImpl();
				formData1.add("customerId", customerId);
				formData1.add("productId", productId);
				formData1.add("amount", amount);
				
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .post(ClientResponse.class, formData1);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				
				response1 = restResponse1.getEntity(String.class);
				//case handling: 4 cases
				if (response1.toString().equals("User not found"))
					return "User not found";
				else if (response1.toString().equals("Product not found"))
					return "Product not found";
				else if (response1.toString().equals("Sell Date for product is already passed, you can't bid now"))
					return "Sell Date for product is already passed, you can't bid now"; 
				else if (response1.toString().equals("Bid should be greater than or equal to start bid set by the seller"))
					return "Bid should be greater than or equal to start bid set by the seller";
				}
			
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
		return Boolean.TRUE.toString();
	}
	
	@GET
	@Path("/getProductBids/{customerId}/{authKey}/{productId}")
	public Response getProductBids(@PathParam("productId") int productId, @PathParam("customerId") int customerId,
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
				WebResource webResource1 = client.resource(urlString + "bid/getProductBids/" + productId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .get(ClientResponse.class);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				String jsonResponse = restResponse1.getEntity(String.class);
				System.out.println("ProductBids: " + jsonResponse);
				return Response.ok(jsonResponse, MediaType.TEXT_PLAIN).build();
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
	}
	
	
	@GET
	@Path("/getMyBids/{customerId}/{authKey}")
	public Response getMyBids(@PathParam("customerId") int customerId,
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
				WebResource webResource1 = client.resource(urlString + "bid/getMyBids/" + customerId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .get(ClientResponse.class);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				String jsonResponse = restResponse1.getEntity(String.class);
				System.out.println("MyBids: " + jsonResponse);
				return Response.ok(jsonResponse, MediaType.TEXT_PLAIN).build();
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
	}
	
	@GET
	@Path("/deleteBid/{customerId}/{authKey}/{bidId}")
	public String deleteBid(@PathParam("bidId") int bidId, @PathParam("customerId") int customerId,
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
				WebResource webResource1 = client.resource(urlString + "bid/deleteBid/" + bidId);
				ClientResponse restResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .get(ClientResponse.class);
				if (restResponse1.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + restResponse1.getStatus());
				}
				response1 = restResponse1.getEntity(String.class);
				//case handling: product does not exist
				if (!response1.toString().equals(Boolean.TRUE.toString())){
					return "Bid does not exist";
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed : HTTP error code : " + e.getMessage());
		}
		return Boolean.TRUE.toString();
	}
}
