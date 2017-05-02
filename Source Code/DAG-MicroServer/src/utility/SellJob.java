package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Bid;
import entity.Customer;
import entity.Product;

public class SellJob extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("***CronJob Starts-> every 3 minutes");
		
	try {
		//sell products if any...
		HibSessionBuilder sessionBuilder = new HibSessionBuilder();
		Session session = sessionBuilder.getSession();
		List<Product> all = new ArrayList<Product>();
		
		//Current dateTime
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
		Date CurrDate = new Date();
		String curDateString = dateFormat.format(CurrDate);
		CurrDate = dateFormat.parse(curDateString);
	
		String hql = "FROM Product P";
		List results = session.createQuery(hql).list();
		
		for(int i=0; i< results.size();i++)
		{
			Product p = (Product)results.get(i);
			//collect is selldate is passed && product isn't sold yet
			// -1 that means its processed but not sold
			if(p.getWinnerBidId() != -1 && p.getSellDate().before(CurrDate) && p.getWinnerBidId() == 0)
			all.add(p);
		}
		Transaction tx = session.beginTransaction();
		
		for(int i=0; i< all.size(); i++)
		{
			System.out.println(all.get(i).getProductId() + " ->  ");
			Product currProd = all.get(i) ;
			//For each qualified product ->> get all bids and check for max
			Set<Bid> bids = currProd.getBid();
			List<Bid> bidsList = new ArrayList<Bid>(bids);
			int maxBid = 0;
			Bid maxBidEntity = new Bid(); 
			for(Bid b : bidsList)
			{
				if(b.getAmount() > maxBid)
				{
					maxBid = b.getAmount();
					maxBidEntity = b;
				}
				//status lost by default
				b.setStatus(2);
				session.save(b);
				//System.out.print(" " + b.getBidId());
			}
			if(maxBid != 0)
			{
				//won, bid status update
				maxBidEntity.setStatus(1);
				session.save(maxBidEntity);
				//update winning bid id in product
				currProd.setWinnerBidId(maxBidEntity.getBidId());
				
				//"Purchase order receipt" send email sent to seller and Highest Bidder
				Customer seller = currProd.getCustomer();
				Customer buyer = maxBidEntity.getCustomer();
				
				String prodDetails = "\n\nProduct Details: \nName - " + currProd.getName() + "\nDescription - " + currProd.getDescription() + "\nStart Bid - " + currProd.getStartBid();
				
				String sellerMsg = "\nHello "+ seller.getName() + ",\n\nYour following product is being sold for " 
									+ maxBidEntity.getAmount() + " $ and the amount is being credited to your account." + prodDetails
									+ "\n\nPurchased By: \nName - " + buyer.getName() + "\nEmailId - " + buyer.getEmailId()
									+ "\n\nRegards,\nTeam DAG";
				String buyerMsg = "\nHello "+ buyer.getName() + ",\n\nYou have purchased the following product for " 
									+ maxBidEntity.getAmount() + " $ and the amount is being debited from your account."
									+ prodDetails + "\n\nSeller Details: \nName - " + seller.getName() + "\nEmailId - " + seller.getEmailId()
									+ "\n\nRegards,\nTeam DAG";
				EmailManager emailManager = new EmailManager();
				emailManager.sendPurchaseEmails(seller.getEmailId(), buyer.getEmailId(), sellerMsg, buyerMsg);
			}
			else {
				currProd.setWinnerBidId(-1);
			}
			session.save(currProd);
		}
		
		tx.commit();
		sessionBuilder.close(session);
		//System.out.println(CurrDate.toString());
		System.out.println("***CronJob Ends");
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
}
