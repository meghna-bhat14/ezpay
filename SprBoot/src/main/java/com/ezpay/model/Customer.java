package com.ezpay.model;

import jakarta.persistence.*;

//import org.springframework.data.annotation.Id;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "master_data")
public class Customer {


	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)  // or GenerationType.SEQUENCE depending on your DB setup
	    @Column(name = "customer_id")
	    private int customerId;

	    @Column(name = "name", nullable = false, length = 30)
	    private String name;

	    @Column(name = "email", nullable = false, unique = true, length = 255)
	    private String email;

	    @Column(name = "mobile_number", nullable = false, unique = true, length = 20)
	    private String mobileNumber;

	    @Column(name = "address", nullable = false, length = 255)
	    private String address;


	    @Column(name = "dob", nullable = false)
	    private String dob;

	    @Column(name = "gender", nullable = false, length = 10)
	    private String gender;

	    @Column(name = "profile_creation_date", nullable = false)
	    private String profileCreationDate;

	    @Column(name = "profile_last_updated_date", nullable = false)
	    private String profileLastUpdatedDate;

	    @Column(name = "profile_picture_url")
	    private String profilePictureUrl;

	    @Column(name = "is_profile_info_set", nullable = false)
	    private boolean isProfileInfoSet;

	    @Column(name = "upi_id", nullable = false, unique = true, length = 16)
	    private String upiId;

	    @Column(name = "acc_no", nullable = false, unique = true, length = 16)
	    private String bankAccountNumber;

	    @Column(name = "ifsc_code", nullable = false, length = 11)
	    private String ifscCode;

	    @Column(name = "account_type", nullable = false)
	    private int accountType;

	    // Constructors, getters, and setters
	

    // Constructors, getters, and setters
    public Customer()
    {
    	
    }
    public Customer(int customerId, String name, String email, String mobileNumber, String address, String dob, String gender, String profileCreationDate, String profileLastUpdatedDate, String profilePictureUrl, boolean isProfileInfoSet,String upiId, String accNo, String ifscCode, int accountType) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.profileCreationDate = profileCreationDate;
        this.profileLastUpdatedDate = profileLastUpdatedDate;
        this.profilePictureUrl = profilePictureUrl;
        this.isProfileInfoSet = isProfileInfoSet;
        this.upiId = upiId;
        this.bankAccountNumber = accNo;
        this.ifscCode = ifscCode;
        this.accountType = accountType;
    }

    // Getters and setters for all fields
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getProfileCreationDate() { return profileCreationDate; }
    public void setProfileCreationDate(String profileCreationDate) { this.profileCreationDate = profileCreationDate; }
    public String getProfileLastUpdatedDate() { return profileLastUpdatedDate; }
    public void setProfileLastUpdatedDate(String profileLastUpdatedDate) { this.profileLastUpdatedDate = profileLastUpdatedDate; }
    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
    public boolean getIsProfileInfoSet() { return isProfileInfoSet; }
    public void setIsProfileInfoSet(boolean profileInfoSet) { isProfileInfoSet = profileInfoSet; }
    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    // Getter and Setter for bankAccountNumber
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String accNo) {
        this.bankAccountNumber = accNo;
    }

    // Getter and Setter for ifscCode
    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    // Getter and Setter for accountType
    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}