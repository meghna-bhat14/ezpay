package com.ezpay.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Component
@Entity
@Table(name = "BANK_PIN_DETAILS")

public class BankPinDetails {

	/**
	 * Unique identifier for each BankPinDetail record.
	 * This ID is automatically generated.
	 */
	@Id
	@Column(name = "bank_account_number", nullable = false, unique = true, length = 255)
	private String bankAccountNumber;
	/**
	 * The hashed value of the customer's transaction PIN.
	 * It is stored as a String with a maximum length of 255 characters.
	 */
	@Column(name = "hashed_transaction_pin", nullable = false, length = 255)
	private String hashedTransactionPin;

	/**
	 * Status indicating if the transaction PIN has been set.
	 * This value is mandatory and represented as an Integer.
	 * Possible values:
	 * - 0: PIN not set
	 * - 1: PIN set
	 */
	@Column(name = "transaction_pin_set_status", nullable = false)
	private Integer transactionPinSetStatus;


	@OneToOne
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID", nullable = false)
    private Customer customer;




	public BankPinDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public BankPinDetails(String bankAccountNumber, String hashedTransactionPin, Integer transactionPinSetStatus,
			Customer customer) {
		super();
		this.bankAccountNumber = bankAccountNumber;
		this.hashedTransactionPin = hashedTransactionPin;
		this.transactionPinSetStatus = transactionPinSetStatus;
		this.customer = customer;
	}


	public String getBankAccountNumber() {
		return bankAccountNumber;
	}


	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}


	public String getHashedTransactionPin() {
		return hashedTransactionPin;
	}


	public void setHashedTransactionPin(String hashedTransactionPin) {
		this.hashedTransactionPin = hashedTransactionPin;
	}


	public Integer getTransactionPinSetStatus() {
		return transactionPinSetStatus;
	}


	public void setTransactionPinSetStatus(Integer transactionPinSetStatus) {
		this.transactionPinSetStatus = transactionPinSetStatus;
	}


	public Customer getUser() {
		return customer;
	}


	public void setUser(Customer customer) {
		this.customer = customer;
	}

	
	
}


	