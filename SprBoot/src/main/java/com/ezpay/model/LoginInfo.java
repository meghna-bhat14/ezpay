package com.ezpay.model;

import jakarta.persistence.*;

@Entity
@Table(name = "login_data_uc2")
public class LoginInfo {
	
	@Id
	@Column(name = "user_id", length = 30)
	private String userId; 
    
    @Column(name = "password_hash", nullable = false)
	private String passwordHash; 
    
    @ManyToOne
    @JoinColumn(name = "blocked_code", referencedColumnName="block_id",nullable = false)
    private SuspiciousActivity suspiciousActivity;
	
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
    

    // Default Constructor
    public LoginInfo()
    {
    	this.userId="user";
    	this.passwordHash ="";
    	this.suspiciousActivity=new SuspiciousActivity();
    	this.customer = new Customer();
    }


	public LoginInfo(String userId, String passwordHash, SuspiciousActivity suspiciousActivity, Customer customer) {
		super();
		this.userId = userId;
		this.passwordHash = passwordHash;
		this.suspiciousActivity = suspiciousActivity;
		this.customer = customer;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPasswordHash() {
		return passwordHash;
	}


	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}


	public SuspiciousActivity getSuspiciousActivity() {
		return suspiciousActivity;
	}


	public void setSuspiciousActivity(SuspiciousActivity suspiciousActivity) {
		this.suspiciousActivity = suspiciousActivity;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
    


}
