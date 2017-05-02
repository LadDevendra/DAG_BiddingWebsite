package entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	private String name;
	private String password;
	@Column(length = 10)
	private String contactNo;
	private String address;
	@Column(unique = true)
	private String emailId;
	//self generated Auth key (4 digit)
	@Column(columnDefinition= "int default 0")
	private int authKey;
	
	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date currentLogin;
	
	
	public Date getCurrentLogin() {
		return currentLogin;
	}
	public void setCurrentLogin(Date currentLogin) {
		this.currentLogin = currentLogin;
	}
	private String lastLocation;
	
	//location
	
	public String getLastLocation() {
		return lastLocation;
	}
	public void setLastLocation(String lastLocation) {
		this.lastLocation = lastLocation;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="customer", fetch = FetchType.EAGER)
	private Set<Product> product = new HashSet<Product>();

	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="customer", fetch = FetchType.EAGER)
	private Set<Bid> bid = new HashSet<Bid>();
	
	public Set<Product> getProduct() {
		return product;
	}
	public void setProduct(Set<Product> product) {
		this.product = product;
	}
	public Set<Bid> getBid() {
		return bid;
	}
	public void setBid(Set<Bid> bid) {
		this.bid = bid;
	}
	public int getAuthKey() {
		return authKey;
	}
	public void setAuthKey(int authKey) {
		this.authKey = authKey;
	}
	
	
	public int getCustomerId() {
		return customerId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}	
	
}
