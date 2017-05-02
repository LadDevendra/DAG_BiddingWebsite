package entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
//@Cacheable
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="productRegion")
public class Product
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String name;
	private String description;
	@Column(columnDefinition= "int default 0")
	private int startBid;
	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sellDate;
	@Column(columnDefinition= "int default 0")
	private int winnerBidId;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="product", fetch = FetchType.EAGER)
	private Set<Bid> bid = new HashSet<Bid>();		

	public int getWinnerBidId() {
		return winnerBidId;
	}

	public void setWinnerBidId(int winnerBidId) {
		this.winnerBidId = winnerBidId;
	}
	
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name
				+ ", description=" + description + ", startBid=" + startBid
				+ ", sellDate=" + sellDate + ", customer=" + customer + "]";
	}

	public Set<Bid> getBid() {
		return bid;
	}

	public void setBid(Set<Bid> bid) {
		this.bid = bid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStartBid() {
		return startBid;
	}

	public void setStartBid(int startBid) {
		this.startBid = startBid;
	}

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}	
}