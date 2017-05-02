package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Locale.LanguageRange;

//import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ser.std.StdArraySerializers.BooleanArraySerializer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Customer;
import entity.UserLoginData;
import utility.AuthKeyGenerator;
import utility.EmailManager;
import utility.HibSessionBuilder;


@Path("/customer")
public class CustomerController {
	
	@GET
	@Path("/isLoggedIn/{customerId}/{authKey}")
	@Produces(MediaType.TEXT_PLAIN)
	public String isLoggedIn(@PathParam("customerId") int customerId, 
							@PathParam("authKey") int authKey)
	{
		int trueAuthKey=0;
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		String hql = "SELECT C.authKey FROM Customer C WHERE C.customerId = ?";
		List results = session.createQuery(hql).setParameter(0, customerId).list();
		
		if(results.size() == 1)
		{
			trueAuthKey = (Integer)results.get(0);
		}
		if(trueAuthKey == 0)
		return Boolean.FALSE.toString();
		else if(authKey == trueAuthKey)
		{
			return Boolean.TRUE.toString();
		}
		else
			return Boolean.FALSE.toString();
	}
	
	@POST
	@Path("/getUserId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserId(@FormParam("emailId") String emailId)
	{
		int userId = 0;
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		String hql = "SELECT C.customerId FROM Customer C WHERE C.emailId = ?";
		List results = session.createQuery(hql).setString(0, emailId).list();
		
		if(results.size() == 1)
		{
			userId = (Integer)results.get(0);
		}
		sessionBuilder.close(session);
		
		return Integer.toString(userId);
	}
	
	@POST
	@Path("/isEmailIdAvailable")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String isEmailIdAvailable(@FormParam("emailId") String emailId)
	{
		int userId = 0;
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		String hql = "SELECT C.customerId FROM Customer C WHERE C.emailId = ?";
		List results = session.createQuery(hql).setString(0, emailId).list();
		
		if(results.size() == 1)
		{
			userId = (Integer)results.get(0);
		}
		sessionBuilder.close(session);
		
		if(userId == 0)
			return Boolean.TRUE.toString();
		else
			return Boolean.FALSE.toString();
	}
	
	@POST
	@Path("/insertUserData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void insertUserData(@FormParam("name") String name, @FormParam("password") String password, @FormParam("contactNo") String contactNo,
			@FormParam("address") String address, @FormParam("emailId") String emailId)
	{
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		Customer customer = new Customer();
		customer.setName(name);
		customer.setPassword(password);
		customer.setContactNo(contactNo);
		customer.setAddress(address);
		customer.setEmailId(emailId);
		
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
		
		customer.setLastLogin(date);
		customer.setCurrentLogin(date);
		
		Transaction tx = session.beginTransaction();
		session.save(customer);
		tx.commit();
		sessionBuilder.close(session);	
	}
	
	@POST
	@Path("/updateProfile")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateProfile(@FormParam("customerId") String customerId, @FormParam("name") String name, @FormParam("password") String password, @FormParam("contactNo") String contactNo,
			@FormParam("address") String address, @FormParam("emailId") String emailId)
	{
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		int customerIdint = Integer.parseInt(customerId);
		
		Customer customer = new Customer();
		String hql = "FROM Customer C WHERE C.customerId = ?";
		List results = session.createQuery(hql).setParameter(0, customerIdint).list();
		
		if(results.size() == 1)
		{
			customer = (Customer)results.get(0);
			customer.setName(name);
			customer.setPassword(password);
			customer.setContactNo(contactNo);
			customer.setAddress(address);
			customer.setEmailId(emailId);

			Transaction tx = session.beginTransaction();
			session.save(customer);
			tx.commit();
		}
	return Boolean.TRUE.toString();	
	}
	
	@POST
	@Path("/sendEmail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void sendEmail(@FormParam("type") String type, @FormParam("emailId") String emailId)
	{
		int typeInt = Integer.parseInt(type);
		//type 1 <= registration email, 2<= send item sold notification
		EmailManager em = new EmailManager();
		em.sendEmail(typeInt, emailId);
	}
	
	@POST
	@Path("/checkLoginCred")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public UserLoginData checkLoginCred(@FormParam("emailId") String emailId, @FormParam("password") String password)
	{
		int authKey = 0;
		Customer customer = new Customer();
		UserLoginData userLoginData = new UserLoginData();
		userLoginData.setAuthKey("0");
		userLoginData.setName("0");
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		String hql = "FROM Customer C WHERE C.emailId = ? and C.password = ?";
		List results = session.createQuery(hql).setString(0, emailId).setString(1, password).list();
		
		if(results.size() == 1)
		{
			customer = (Customer)results.get(0);
			authKey = customer.getAuthKey();
			userLoginData.setName(customer.getName());
		}
		else {
			//not a valid user credentials, returns authKey -> 0
			return userLoginData;
		}
			if(authKey == 0)
			{
				AuthKeyGenerator keyGenerator = new AuthKeyGenerator();
				authKey = keyGenerator.getAuthKey();
				customer.setAuthKey(authKey);
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
				//update login date entries
				customer.setLastLogin(customer.getCurrentLogin());
				customer.setCurrentLogin(date);
				customer.setLastLocation("Richardson, Tx");
				Transaction tx = session.beginTransaction();
				session.save(customer);
				tx.commit();
			}
			sessionBuilder.close(session);
			userLoginData.setAuthKey(Integer.toString(authKey));
			return userLoginData;
	}
	
	@GET
	@Path("/logOut/{customerId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String logOut(@PathParam("customerId") int customerId)
	{
		Customer customer = new Customer();
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		String hql = "FROM Customer C WHERE C.customerId = ?";
		List results = session.createQuery(hql).setParameter(0, customerId).list();
		
		if(results.size() == 1)
		{
			customer = (Customer)results.get(0);
			customer.setAuthKey(0);
			Transaction tx = session.beginTransaction();
			session.save(customer);
			tx.commit();
		}
	return Boolean.TRUE.toString();
	}
	
	@GET
	@Path("/getUserProfile/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getUserProfile(@PathParam("customerId") int customerId)
	{
		Customer customer = new Customer();
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		Query query = session.createQuery("FROM Customer C WHERE C.customerId = ?").setParameter(0, customerId);
		query.setCacheable(true);
		List results = query.list();
		
		if(results.size() == 1)
		{
			customer = (Customer)results.get(0);
		}
		customer.setProduct(null);
		customer.setBid(null);
		return customer;
	}	
}
