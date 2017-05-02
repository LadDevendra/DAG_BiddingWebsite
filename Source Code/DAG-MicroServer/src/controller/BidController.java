package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import utility.HibSessionBuilder;
import entity.Bid;
import entity.BidDisplay;
import entity.Customer;
import entity.Product;

@Path("/bid")
public class BidController {
	
	@POST
	@Path("/bidForItem")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String bidForItem(@FormParam("customerId") String customerId, @FormParam("productId") String productId,
							@FormParam("amount") String amount)
	{
		Customer customer = new Customer();
		Product product = new Product();
		int customerIdint = Integer.parseInt(customerId);
		int productIdint = Integer.parseInt(productId);
		int amountint = Integer.parseInt(amount);
		
		Bid bid = new Bid();
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();

		String hql = "FROM Customer C WHERE C.customerId = ?";
		List results = session.createQuery(hql).setParameter(0, customerIdint).list();
		if(results.size() == 1)
		{
			customer = (Customer)results.get(0);
		}
		else {
			return "User not found";
		}
		String hql1 = "FROM Product P WHERE P.productId = ?";
		List results1 = session.createQuery(hql1).setParameter(0, productIdint).list();
		if(results1.size() == 1)
		{
			product = (Product)results1.get(0);
		}
		else {
			return "Product not found";
		}
		//get current date in our format
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
		Date date = new Date();
		String curDateString = dateFormat.format(date);
		try {
			date = dateFormat.parse(curDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//check currenttime against sellBid and check amt > startBid
		if(product.getSellDate().before(date))
		{
			return "Sell Date for product is already passed, you can't bid now";
		}
		else if(product.getStartBid() > amountint)
		{
			return "Bid should be greater than or equal to start bid set by the seller";
		}
		
		bid.setAmount(amountint);
		bid.setCustomer(customer);
		bid.setProduct(product);
		
		Transaction tx = session.beginTransaction();
		session.save(bid);
		tx.commit();
		sessionBuilder.close(session);
		
		return Boolean.TRUE.toString();
	}
	
	@GET
	@Path("/getMyBids/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BidDisplay> getMyBids(@PathParam("customerId") int customerId)
	{
		Customer customer = new Customer();
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		List<BidDisplay> bidDisplayList = new ArrayList<BidDisplay>(); 
		
		String hql = "FROM Customer C WHERE C.customerId = ?";
		List results = session.createQuery(hql).setParameter(0, customerId).list();
		
		if(results.size() == 1)
		{
			customer = (Customer)results.get(0);
		}
		Set<Bid> bids = customer.getBid();	
		for(Bid b : bids)
		{
			BidDisplay bidDisplay = new BidDisplay();
			bidDisplay.setBidId(b.getBidId());
			bidDisplay.setAmount(b.getAmount());
			bidDisplay.setStatus(b.getStatus());
			bidDisplay.setProductName(b.getProduct().getName());
			bidDisplay.setProductSellDate(b.getProduct().getSellDate());
			bidDisplay.setProductStartBid(b.getProduct().getStartBid());
		
			bidDisplayList.add(bidDisplay);
		}
		sessionBuilder.close(session);	
		return bidDisplayList;
	}
	
	@GET
	@Path("/getProductBids/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BidDisplay> getProductBids(@PathParam("productId") int productId)
	{
		Product product = new Product();
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		List<BidDisplay> bidDisplayList = new ArrayList<BidDisplay>(); 
		
		String hql = "FROM Product P WHERE P.productId = ?";
		List results = session.createQuery(hql).setParameter(0, productId).list();
		
		if(results.size() == 1)
		{
			product = (Product)results.get(0);
		}
		Set<Bid> bids = product.getBid();	
		for(Bid b : bids)
		{
			BidDisplay bidDisplay = new BidDisplay();
			bidDisplay.setBidId(b.getBidId());
			bidDisplay.setAmount(b.getAmount());
			bidDisplay.setStatus(b.getStatus());
			bidDisplay.setCustomerName(b.getCustomer().getName());
			
			bidDisplayList.add(bidDisplay);
		}
		sessionBuilder.close(session);	
		return bidDisplayList;
	}
	
	@GET
	@Path("/deleteBid/{bidId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBid(@PathParam("bidId") int bidId)
	{
		Bid bid = new Bid();
		bid.setBidId(bidId);
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();

		try {
			Transaction tx = session.beginTransaction();
			session.delete(bid);
			tx.commit();
			sessionBuilder.close(session);
			
		} catch (Exception e) {
			return "Bid does not exist";
		}
		return Boolean.TRUE.toString();
	}	

	
		
}
