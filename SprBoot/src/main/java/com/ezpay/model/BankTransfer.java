//package com.ezpay.model;
//
//import java.time.LocalDateTime;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
///**
// * Represents a bank transfer transaction.
// * This class includes details about the payment such as sender and receiver account numbers, 
// * IFSC codes, amount, status, timestamp, and payment type.
// * Author: Deepshika (Sprint 1)
// * Author: Atishay (Sprint 2)
// * Date: 2024-09-02
// */
//@Entity
//@Table(name = "BANK_TRANSFER")
//public class BankTransfer {
//
//    @Id
//    @Column(name = "TRANSFER_ID")
//    private String transferId;
//
//    @Column(name = "SENDER_ACCOUNT_NUMBER")
//    private String senderAccountNumber;
//
//    @Column(name = "SENDER_IFSC_CODE")
//    private String senderIfscCode;
//
//    @Column(name = "RECEIVER_ACCOUNT_NUMBER")
//    private String receiverAccountNumber;
//
//    @Column(name = "RECEIVER_IFSC_CODE")
//    private String receiverIfscCode;
//
//    @Column(name = "AMOUNT")
//    private double amount;
//
//    @Column(name = "STATUS")
//    private String status;
//
//    @Column(name = "LABEL")
//    private String label;
//
//    @Column(name = "TIMESTAMP")
//    private LocalDateTime timestamp;
//
//    @Column(name = "PAYMENT_TYPE")
//    private String paymentType;
//
//    // Default constructor
//    public BankTransfer() {}
//
//    /**
//     * Constructor to initialize a BankTransfer object with all fields.
//     *
//     * @param transferId The unique identifier for the transfer.
//     * @param senderAccountNumber The account number of the sender.
//     * @param senderIfscCode The IFSC code of the sender's bank.
//     * @param receiverAccountNumber The account number of the receiver.
//     * @param receiverIfscCode The IFSC code of the receiver's bank.
//     * @param amount The amount of money being transferred.
//     * @param status The status of the transfer (e.g., PENDING, COMPLETED).
//     * @param label An optional label or description for the transfer.
//     * @param timestamp The date and time when the transfer was initiated.
//     * @param paymentType The type of payment (e.g., CREDIT, DEBIT).
//     */
//    public BankTransfer(String transferId, String senderAccountNumber, String senderIfscCode,
//                        String receiverAccountNumber, String receiverIfscCode, double amount, 
//                        String status, String label, LocalDateTime timestamp, String paymentType) {
//        super();
//        this.transferId = transferId;
//        this.senderAccountNumber = senderAccountNumber;
//        this.senderIfscCode = senderIfscCode;
//        this.receiverAccountNumber = receiverAccountNumber;
//        this.receiverIfscCode = receiverIfscCode;
//        this.amount = amount;
//        this.status = status;
//        this.label = label;
//        this.timestamp = timestamp;
//        this.paymentType = paymentType;
//    }
//
//    /**
//     * Initiates the transfer process and prints a message to indicate that the payment has started.
//     */
//    public void initiateTransfer() {
//        System.out.println("Your payment has been initiated...");
//    }
//
//    /**
//     * Confirms the completion of the transfer and prints the status of the transfer.
//     */
//    public void confirmTransfer() {
//        System.out.println("Transfer completed with status: " + status);
//    }
//
//    // Getters and Setters
//
//    public String getTransferId() {
//        return transferId;
//    }
//
//    public void setTransferId(String transferId) {
//        this.transferId = transferId;
//    }
//
//    public String getSenderAccountNumber() {
//        return senderAccountNumber;
//    }
//
//    public void setSenderAccountNumber(String senderAccountNumber) {
//        this.senderAccountNumber = senderAccountNumber;
//    }
//
//    public String getSenderIfscCode() {
//        return senderIfscCode;
//    }
//
//    public void setSenderIfscCode(String senderIfscCode) {
//        this.senderIfscCode = senderIfscCode;
//    }
//
//    public String getReceiverAccountNumber() {
//        return receiverAccountNumber;
//    }
//
//    public void setReceiverAccountNumber(String receiverAccountNumber) {
//        this.receiverAccountNumber = receiverAccountNumber;
//    }
//
//    public String getReceiverIfscCode() {
//        return receiverIfscCode;
//    }
//
//    public void setReceiverIfscCode(String receiverIfscCode) {
//        this.receiverIfscCode = receiverIfscCode;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getLabel() {
//        return label;
//    }
//
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(LocalDateTime timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public String getPaymentType() {
//        return paymentType;
//    }
//
//    public void setPaymentType(String paymentType) {
//        this.paymentType = paymentType;
//    }
//}
//
package com.ezpay.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Represents a bank transfer transaction.
 * This class includes details about the payment such as sender and receiver account numbers, 
 * IFSC codes, amount, status, timestamp, and payment type.
 * Author: Deepshika (Sprint 1)
 * Author: Atishay (Sprint 2)
 * Date: 2024-09-02
 */
@Entity
@Table(name = "BANK_TRANSFER")
public class BankTransfer {

    @Id
    @Column(name = "TRANSFER_ID")
    private String transferId;

    @Column(name = "SENDER_ACCOUNT_NUMBER")
    private String senderAccountNumber;

    @Column(name = "SENDER_IFSC_CODE")
    private String senderIfscCode;

    @Column(name = "RECEIVER_ACCOUNT_NUMBER")
    private String receiverAccountNumber;

    @Column(name = "RECEIVER_IFSC_CODE")
    private String receiverIfscCode;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;
    
    @Column(name = "remark")
    private String remark;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SENDER_CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID", nullable = false)
    private Customer sender;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECEIVER_CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID", nullable = false)
    private Customer receiver;

    // Default constructor
    public BankTransfer() {}

    /**
     * Constructor to initialize a BankTransfer object with all fields.
     *
     * @param transferId The unique identifier for the transfer.
     * @param senderAccountNumber The account number of the sender.
     * @param senderIfscCode The IFSC code of the sender's bank.
     * @param receiverAccountNumber The account number of the receiver.
     * @param receiverIfscCode The IFSC code of the receiver's bank.
     * @param amount The amount of money being transferred.
     * @param status The status of the transfer (e.g., PENDING, COMPLETED).
     * @param label An optional label or description for the transfer.
     * @param timestamp The date and time when the transfer was initiated.
     * @param paymentType The type of payment (e.g., CREDIT, DEBIT).
     */
//    public BankTransfer(String transferId, String senderAccountNumber, String senderIfscCode,
//                        String receiverAccountNumber, String receiverIfscCode, double amount, 
//                        Integer status, String label, LocalDateTime timestamp, String paymentType,  String remark, Customer customer) {
//        super();
//        this.transferId = transferId;
//        this.senderAccountNumber = senderAccountNumber;
//        this.senderIfscCode = senderIfscCode;
//        this.receiverAccountNumber = receiverAccountNumber;
//        this.receiverIfscCode = receiverIfscCode;
//        this.amount = amount;
//        this.status = status;
//        this.label = label;
//        this.timestamp = timestamp;
//        this.paymentType = paymentType;
//        this.remark = remark;
//        this.customer=customer;
//    }

    
    
    /**
     * Initiates the transfer process and prints a message to indicate that the payment has started.
     */
    public void initiateTransfer() {
        System.out.println("Your payment has been initiated...");
    }

    public BankTransfer(String transferId, String senderAccountNumber, String senderIfscCode,
			String receiverAccountNumber, String receiverIfscCode, double amount, Integer status, String label,
			LocalDateTime timestamp, String paymentType, String remark, Customer sender, Customer receiver) {
		super();
		this.transferId = transferId;
		this.senderAccountNumber = senderAccountNumber;
		this.senderIfscCode = senderIfscCode;
		this.receiverAccountNumber = receiverAccountNumber;
		this.receiverIfscCode = receiverIfscCode;
		this.amount = amount;
		this.status = status;
		this.label = label;
		this.timestamp = timestamp;
		this.paymentType = paymentType;
		this.remark = remark;
		this.sender = sender;
		this.receiver = receiver;
	}

	/**
     * Confirms the completion of the transfer and prints the status of the transfer.
     */
    public void confirmTransfer() {
        System.out.println("Transfer completed with status: " + status);
    }

    // Getters and Setters

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getSenderIfscCode() {
        return senderIfscCode;
    }

    public void setSenderIfscCode(String senderIfscCode) {
        this.senderIfscCode = senderIfscCode;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public String getReceiverIfscCode() {
        return receiverIfscCode;
    }

    public void setReceiverIfscCode(String receiverIfscCode) {
        this.receiverIfscCode = receiverIfscCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
//
//	public Customer getUser() {
//		return customer;
//	}
//
//	public void setUser(Customer customer) {
//		this.customer = customer;
//	}

	public Customer getSender() {
		return sender;
	}

	public void setSender(Customer sender) {
		this.sender = sender;
	}

	public Customer getReceiver() {
		return receiver;
	}

	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}
	
	
    
	
    
}


