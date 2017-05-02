package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.SystemException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ser.std.StdArraySerializers.BooleanArraySerializer;
import org.hibernate.Session;
import org.hibernate.Transaction;


import utility.HibSessionBuilder;
import entity.Customer;
import entity.Product;

@Path("/product")
public class ProductController {
	
	@POST
	@Path("/createProduct")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createProduct(@FormParam("customerId") String customerId, @FormParam("name") String name,
			@FormParam("description") String description, @FormParam("startBid") String startBid, @FormParam("sellDate") String sellDate)
	{
		Customer customer = new Customer();
		Product product = new Product();
		int customerIdInt = Integer.parseInt(customerId); 
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		String hql = "FROM Customer C WHERE C.customerId = ?";
		List results = session.createQuery(hql).setParameter(0, customerIdInt).list();
		if(results.size() == 1)
		{
			customer = (Customer)results.get(0);
		}
		else {
			return "User not found";
		}
		//create product
		product.setName(name);
		product.setDescription(description);
		product.setStartBid(Integer.parseInt(startBid));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
		Date date = new Date();
		try {
			date = sdf1.parse(sellDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		product.setSellDate(date);
		product.setCustomer(customer);
		
		Transaction tx = session.beginTransaction();
		session.save(product);
		tx.commit();
		sessionBuilder.close(session);	
		
		return Boolean.TRUE.toString();
	}
	
	@POST
	@Path("/searchForProduct")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> searchForProduct(@FormParam("keyWord") String keyWord)
	{
		//Customer customer = new Customer();
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		List<Product> all = new ArrayList<Product>();
		
		String hql = "FROM Product P";
		List results = session.createQuery(hql).list();
		
		for(int i=0; i< results.size();i++)
		{
			Product p = (Product)results.get(i);
			if(p.getName().toLowerCase().contains(keyWord.toLowerCase()) || p.getDescription().toLowerCase().contains(keyWord.toLowerCase()))
			all.add(p);
		}
		
		for(Product p : all)
		{
			p.setCustomer(null);
			p.setBid(null);
		}
		sessionBuilder.close(session);	
		return all;
	}
	
	//query caching 
	@GET
	@Path("/getMyProducts/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getMyProducts(@PathParam("customerId") int customerId)
	{
		Customer customer = new Customer();
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		String hql = "FROM Customer C WHERE C.customerId = ?";
	
		List results = session.createQuery(hql).setParameter(0, customerId).list();
		
		if(results.size() == 1)
		{
			customer = (Customer)results.get(0);
		}
		Set<Product> products = customer.getProduct();	
		for(Product p : products)
		{
			p.setCustomer(null);
			p.setBid(null);
		}
	
		sessionBuilder.close(session);	
		List<Product> prods = new ArrayList<Product>(products);
		return prods;
	}
	
	@GET
	@Path("/getProduct/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProduct(@PathParam("productId") int productId)
	{
		Product product = new Product();
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		String hql = "FROM Product P WHERE P.productId = ?";
		List results = session.createQuery(hql).setParameter(0, productId).list();
		
		if(results.size() == 1)
		{
			product = (Product)results.get(0);
			product.setCustomer(null);
			product.setBid(null);
		}
		sessionBuilder.close(session);	
		return product;
	}	


	@GET
	@Path("/deleteProduct/{productId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProduct(@PathParam("productId") int productId)
	{
		Product product = new Product();
		product.setProductId(productId);
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();

		try {
			Transaction tx = session.beginTransaction();
			session.delete(product);
			tx.commit();
			sessionBuilder.close(session);
			
		} catch (Exception e) {
			return "Product does not exist";
		}
			
		
		return Boolean.TRUE.toString();
	}	
	
}
