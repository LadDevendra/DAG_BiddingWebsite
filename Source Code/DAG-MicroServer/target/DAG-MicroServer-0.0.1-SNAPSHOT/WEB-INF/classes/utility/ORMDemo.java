package utility;

 
/**
 * @author Crunchify.com
 */
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import entity.Customer;
import entity.Product;
 
@Path("/test")
public class ORMDemo {
	@GET
	@Produces("application/xml")
	public String convertCtoF() {
 
		Double fahrenheit;
		Double celsius = 36.8;
		fahrenheit = ((celsius * 9) / 5) + 32;
		
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		
		Customer customer = new Customer();
		customer.setName("devya");
		customer.setPassword("adad");
		customer.setEmailId("dev@gmail.com");
		customer.setContactNo("123345");
		
		Product product = new Product();
		product.setName("prod1");
		product.setDescription("asesfsfadadada");
		
		String startDate="20-04-2017 00:00:00";
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		Date date = new Date();
		try {
			date = sdf1.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		product.setSellDate(date);					
		product.setCustomer(customer);
		
		Transaction tx = session.beginTransaction();
		session.save(customer);
		session.save(product);
		tx.commit();
		sessionBuilder.close(session);
		
		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fahrenheit;
		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
	}
 
	@Path("{c}")
	@GET
	@Produces("application/xml")
	public String convertCtoFfromInput(@PathParam("c") Double c) {
		Double fahrenheit;
		Double celsius = c;
		fahrenheit = ((celsius * 9) / 5) + 32;
 
		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fahrenheit;
		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
	}
}