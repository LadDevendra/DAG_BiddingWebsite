package utility;

import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager {
	
	public boolean sendEmail(int type, String email) {
	
		final String username = "dag.wpl@gmail.com";
		final String password = "Devendra123";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("dag.wpl@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			switch (type) {
			case 1:
				message.setSubject("Registration Successful");
				message.setText("\nThank You for registering with DAG Enterprise \n\n Your email Id: " + email + "\n Password: **** \n\nRegards,\nTeam DAG");
				break;
			case 2:
				message.setSubject("Item Sold");
				message.setText("");
				break;
			default:
				break;
			}

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	public boolean sendPurchaseEmails(String sellerEmail, String bidderEmail, String sellerMsg, String bidderMsg) {
		
		final String username = "dag.wpl@gmail.com";
		final String password = "Devendra123";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			//to seller
			Message message1 = new MimeMessage(session);
			message1.setFrom(new InternetAddress("dag.wpl@gmail.com"));
			message1.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(sellerEmail));
			message1.setSubject("Congratulations, Your Product is being Sold");
			message1.setText(sellerMsg);
			Transport.send(message1);
			
			//to Highest bidder
			Message message2 = new MimeMessage(session);
			message2.setFrom(new InternetAddress("dag.wpl@gmail.com"));
			message2.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(bidderEmail));
			message2.setSubject("Congratulations, you purchased a Product");
			message2.setText(bidderMsg);
			Transport.send(message2);

			System.out.println("Emails Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	
}
