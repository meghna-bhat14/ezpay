////package com.ezpay.model;
////
////import javax.persistence.*;
////import java.time.LocalDateTime;
////
////@Entity
////@Table(name = "bank_account")
////public class BankAccount {
////    
////    @Id
////    @Column(name = "bank_account_number")
////    private String bankAccountNumber;
////    
////    @ManyToOne
////    @JoinColumn(name = "user_id", nullable = false)  // Foreign key column
////    private User user;
////
////    @Column(name = "upi_id")
////    private String upiId;
////    
////    @Column(name = "account_balance")
////    private double accountBalance;
////    
////    @Column(name = "registration_date")
////    private LocalDateTime registrationDate;
////    
////    @Column(name = "account_type")
////    private String accountType;
////    
////    @Column(name = "upi_pin")
////    private String upiPin;
////    
////    @Column(name = "ifsc_code")
////    private String ifscCode;
////    
//// // Default IFSC Code
////    private static final String DEFAULT_IFSC_CODE = "EZPY0000123";
////
////
////    // Default constructor
////    public BankAccount() {
////        super();
////    }
////
////    
//////constructor without ifsc, uses default
////    public BankAccount(String bankAccountNumber, User user, String upiId, double accountBalance,
////			LocalDateTime registrationDate, String accountType, String upiPin) {
////		super();
////		this.bankAccountNumber = bankAccountNumber;
////		this.user = user;
////		this.upiId = upiId;
////		this.accountBalance = accountBalance;
////		this.registrationDate = registrationDate;
////		this.accountType = accountType;
////		this.upiPin = upiPin;
////		this.ifscCode = DEFAULT_IFSC_CODE;
////	}
////
////
//////constructor with ifsc code provided
////	public BankAccount(String bankAccountNumber, User user, String upiId, double accountBalance,
////			LocalDateTime registrationDate, String accountType, String upiPin, String ifscCode) {
////		super();
////		this.bankAccountNumber = bankAccountNumber;
////		this.user = user;
////		this.upiId = upiId;
////		this.accountBalance = accountBalance;
////		this.registrationDate = registrationDate;
////		this.accountType = accountType;
////		this.upiPin = upiPin;
////		this.ifscCode = ifscCode;
////	}
////
////
////
////	// Getters and Setters
////    public String getBankAccountNumber() {
////        return bankAccountNumber;
////    }
////
////    public void setBankAccountNumber(String bankAccountNumber) {
////        this.bankAccountNumber = bankAccountNumber;
////    }
////
////    public User getUser() {
////        return user;
////    }
////
////    public void setUser(User user) {
////        this.user = user;
////    }
////
////    public String getUpiId() {
////        return upiId;
////    }
////
////    public void setUpiId(String upiId) {
////        this.upiId = upiId;
////    }
////
////    public double getAccountBalance() {
////        return accountBalance;
////    }
////
////    public void setAccountBalance(double accountBalance) {
////        this.accountBalance = accountBalance;
////    }
////
////    public LocalDateTime getRegistrationDate() {
////        return registrationDate;
////    }
////
////    public void setRegistrationDate(LocalDateTime registrationDate) {
////        this.registrationDate = registrationDate;
////    }
////
////    public String getAccountType() {
////        return accountType;
////    }
////
////    public void setAccountType(String accountType) {
////        this.accountType = accountType;
////    }
////
////    public String getUpiPin() {
////        return upiPin;
////    }
////
////    public void setUpiPin(String upiPin) {
////        this.upiPin = upiPin;
////    }
////
////
////	public String getIfscCode() {
////		return ifscCode;
////	}
////
////
////	public void setIfscCode(String ifscCode) {
////		this.ifscCode = ifscCode;
////	}
////    
////    
////}
//
//package com.ezpay.model;
//
//
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
//import java.time.LocalDateTime;
//
///**
// * Represents a bank account entity.
// * Author: Meghna Bhat
// * Date: 2nd September 2024
// */
//@Entity
//@Table(name = "bank_account")
//public class BankAccount {
//
//    @Id
//    @Column(name = "bank_account_number")
//    private String bankAccountNumber;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id", nullable = false)  // Foreign key column referencing the User entity
//    private User user;
//
//    @Column(name = "upi_id")
//    private String upiId;
//
//    @Column(name = "account_balance")
//    private double accountBalance;
//
//    @Column(name = "registration_date")
//    private LocalDateTime registrationDate;
//
//    @Column(name = "account_type")
//    private String accountType;
//
//    @Column(name = "upi_pin")
//    private String upiPin;
//
//    @Column(name = "ifsc_code")
//    private String ifscCode;
//
//    // Default IFSC Code used if no IFSC code is provided
//    private static final String DEFAULT_IFSC_CODE = "EZPY0000123";
//
//    // Default constructor
//    public BankAccount() {
//        super();
//    }
//
//    /**
//     * Constructor for creating a BankAccount instance with a default IFSC code.
//     *
//     * @param bankAccountNumber The bank account number.
//     * @param user The associated user.
//     * @param upiId The UPI ID associated with the account.
//     * @param accountBalance The current balance of the account.
//     * @param registrationDate The date when the account was registered.
//     * @param accountType The type of the account (e.g., SAVINGS).
//     * @param upiPin The UPI PIN for the account.
//     */
//    public BankAccount(String bankAccountNumber, User user, String upiId, double accountBalance,
//                       LocalDateTime registrationDate, String accountType, String upiPin) {
//        super();
//        this.bankAccountNumber = bankAccountNumber;
//        this.user = user;
//        this.upiId = upiId;
//        this.accountBalance = accountBalance;
//        this.registrationDate = registrationDate;
//        this.accountType = accountType;
//        this.upiPin = upiPin;
//        this.ifscCode = DEFAULT_IFSC_CODE;
//    }
//
//    /**
//     * Constructor for creating a BankAccount instance with a provided IFSC code.
//     *
//     * @param bankAccountNumber The bank account number.
//     * @param user The associated user.
//     * @param upiId The UPI ID associated with the account.
//     * @param accountBalance The current balance of the account.
//     * @param registrationDate The date when the account was registered.
//     * @param accountType The type of the account (e.g., SAVINGS).
//     * @param upiPin The UPI PIN for the account.
//     * @param ifscCode The IFSC code of the bank.
//     */
//    public BankAccount(String bankAccountNumber, User user, String upiId, double accountBalance,
//                       LocalDateTime registrationDate, String accountType, String upiPin, String ifscCode) {
//        super();
//        this.bankAccountNumber = bankAccountNumber;
//        this.user = user;
//        this.upiId = upiId;
//        this.accountBalance = accountBalance;
//        this.registrationDate = registrationDate;
//        this.accountType = accountType;
//        this.upiPin = upiPin;
//        this.ifscCode = ifscCode;
//    }
//
//    // Getters and Setters
//
//    public String getBankAccountNumber() {
//        return bankAccountNumber;
//    }
//
//    public void setBankAccountNumber(String bankAccountNumber) {
//        this.bankAccountNumber = bankAccountNumber;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getUpiId() {
//        return upiId;
//    }
//
//    public void setUpiId(String upiId) {
//        this.upiId = upiId;
//    }
//
//    public double getAccountBalance() {
//        return accountBalance;
//    }
//
//    public void setAccountBalance(double accountBalance) {
//        this.accountBalance = accountBalance;
//    }
//
//    public LocalDateTime getRegistrationDate() {
//        return registrationDate;
//    }
//
//    public void setRegistrationDate(LocalDateTime registrationDate) {
//        this.registrationDate = registrationDate;
//    }
//
//    public String getAccountType() {
//        return accountType;
//    }
//
//    public void setAccountType(String accountType) {
//        this.accountType = accountType;
//    }
//
//    public String getUpiPin() {
//        return upiPin;
//    }
//
//    public void setUpiPin(String upiPin) {
//        this.upiPin = upiPin;
//    }
//
//    public String getIfscCode() {
//        return ifscCode;
//    }
//
//    public void setIfscCode(String ifscCode) {
//        this.ifscCode = ifscCode;
//    }
//}
//
package com.ezpay.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
@Component
@Entity
@Table(name = "BANK_BALANCE")
public class BankAccount {

    @Id
    @Column(name = "BANK_ACCOUNT_NUMBER", length = 20)
    private String bankAccountNumber;
    
//    @Column(name = "UPI_ID", length = 16, nullable = false)
//    private String upiId;

    @Column(name = "BANK_ACCOUNT_BALANCE")
    private Double bankAccountBalance;

    @Column(name = "BANK_OVERDRAFT")
    private Double bankOverdraft;
    

    @OneToOne
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    // Default constructor
    public BankAccount() {
        super();
    }

    public BankAccount(String bankAccountNumber, Double bankAccountBalance, Double bankOverdraft,
			Customer customer) {
		super();
		this.bankAccountNumber = bankAccountNumber;
		//this.upiId = upiId;
		this.bankAccountBalance = bankAccountBalance;
		this.bankOverdraft = bankOverdraft;
		this.customer = customer;
	}

	// Constructor
//    public BankBalance(String bankAccountNumber, Float bankAccountBalance, Float bankOverdraft, MasterData masterData) {
//        this.bankAccountNumber = bankAccountNumber;
//        this.bankAccountBalance = bankAccountBalance;
//        this.bankOverdraft = bankOverdraft;
//        this.masterData = masterData;
//    }

    // Getters and Setters
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Double getBankAccountBalance() {
        return bankAccountBalance;
    }

    public void setBankAccountBalance(Double bankAccountBalance) {
        this.bankAccountBalance = bankAccountBalance;
    }

    public Double getBankOverdraft() {
        return bankOverdraft;
    }

    public void setBankOverdraft(Double bankOverdraft) {
        this.bankOverdraft = bankOverdraft;
    }

    public Customer getUser() {
        return customer;
    }

    public void setUser(Customer customer) {
        this.customer = customer;
    }

    
}
