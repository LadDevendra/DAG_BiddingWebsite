package utility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entity.Bid;
import entity.Customer;
import entity.Product;


public class HibSessionBuilder {
	
	
	public Session getSession()
	{
		// TODO Auto-generated method stub
		Configuration con = new Configuration().configure().addAnnotatedClass(Customer.class).addAnnotatedClass(Product.class).addAnnotatedClass(Bid.class);
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		return session;
	}

	public void close(Session session) {
		// TODO Auto-generated method stub
		session.close();
	}
}
