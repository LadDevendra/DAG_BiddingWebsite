package entity;

import java.util.Date;

public class BidDisplay {

	private int bidId;
	private int amount;
	private int status;
	private String customerName;
	private String productName;
	private Date productSellDate;
	private int productStartBid; 
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getProductSellDate() {
		return productSellDate;
	}
	public void setProductSellDate(Date productSellDate) {
		this.productSellDate = productSellDate;
	}
	public int getProductStartBid() {
		return productStartBid;
	}
	public void setProductStartBid(int startBid) {
		this.productStartBid = startBid;
	}
	public int getBidId() {
		return bidId;
	}
	public void setBidId(int bidId) {
		this.bidId = bidId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
