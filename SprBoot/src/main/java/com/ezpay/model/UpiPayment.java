package com.ezpay.model;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezpay.repository.CustomerRepository;

import jakarta.persistence.*;

/**
* Represents a bank account entity.
* Author: Meghna Bhat
* Date: 2nd September 2024
*/
@Entity
@Table(name = "upi_payment")
public class UpiPayment {
    /**
     * Member fields:
     * - paymentId: Unique identifier for the payment.
     * - senderUpiId: UPI ID of the payment sender.
     * - receiverUpiId: UPI ID of the payment receiver.
     * - amount: Amount of the payment.
     * - status: Status of the payment ("SUCCESS" or "FAILURE").
     * - label: A descriptive label for the payment.
     * - timestamp: Date and time when the payment was made.
     * - paymentType: Type of payment ("CREDIT" or "WITHDRAWAL").
     */
    
    @Id
    @Column(name = "payment_id")
    private String paymentId;
    
    @Column(name = "sender_upi_id")
    private String senderUpiId;
    
    @Column(name = "receiver_upi_id")
    private String receiverUpiId;
    
    @Column(name = "amount")
    private double amount;
    
    @Column(name = "status")
    private Integer status; // "SUCCESS" or "FAILURE"
    
    @Column(name = "label")
    private String label;
    
    @Column(name = "time_stamp")
    private LocalDateTime timestamp;
    
    @Column(name = "payment_type")
    private String paymentType; // "CREDIT" or "WITHDRAWAL"
    
    @Column(name = "remark")
    private String remark;
   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SENDER_CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID", nullable = false)
    private Customer sender;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECEIVER_CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID", nullable = false)
    private Customer receiver;
    

    

    public UpiPayment() {}
    
    /**
     * Parameterized constructor to initialize all fields.
     *
     * @param paymentId Unique identifier for the payment.
     * @param senderUpiId UPI ID of the payment sender.
     * @param receiverUpiId UPI ID of the payment receiver.
     * @param amount Amount of the payment.
     * @param status Status of the payment ("SUCCESS" or "FAILURE").
     * @param label A descriptive label for the payment.
     * @param timestamp Date and time when the payment was made.
     * @param paymentType Type of payment ("CREDIT" or "WITHDRAWAL").
     */
//    public UpiPayment(String paymentId, String senderUpiId, String receiverUpiId, double amount, 
//                      Integer status, String label, LocalDateTime timestamp, String paymentType, String remark, Customer customer) {
//        this.paymentId = paymentId;
//        this.senderUpiId = senderUpiId;
//        this.receiverUpiId = receiverUpiId;
//        this.amount = amount;
//        this.status = status;
//        this.label = label;
//        this.timestamp = timestamp;
//        this.paymentType = paymentType;
//        this.remark = remark;
//        this.customer=customer;
//    }

    
    // Method for logging payment initiation
    public void initiatePayment() {
        System.out.println("Your payment has been initiated...");
    }

    public UpiPayment(String paymentId, String senderUpiId, String receiverUpiId, double amount, Integer status,
			String label, LocalDateTime timestamp, String paymentType, String remark, Customer sender,
			Customer receiver) {
		super();
		this.paymentId = paymentId;
		this.senderUpiId = senderUpiId;
		this.receiverUpiId = receiverUpiId;
		this.amount = amount;
		this.status = status;
		this.label = label;
		this.timestamp = timestamp;
		this.paymentType = paymentType;
		this.remark = remark;
		this.sender = sender;
		this.receiver = receiver;
	}

	// Method for logging payment completion
    public void confirmPayment() {
        System.out.println("Payment completed with status: " + status);
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSenderUpiId() {
        return senderUpiId;
    }

    public void setSenderUpiId(String senderUpiId) {
        this.senderUpiId = senderUpiId;
    }

    public String getReceiverUpiId() {
        return receiverUpiId;
    }

    public void setReceiverUpiId(String receiverUpiId) {
        this.receiverUpiId = receiverUpiId;
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

	@Override
	public String toString() {
		return "UpiPayment [paymentId=" + paymentId + ", senderUpiId=" + senderUpiId + ", receiverUpiId="
				+ receiverUpiId + ", amount=" + amount + ", status=" + status + ", label=" + label + ", timestamp="
				+ timestamp + ", paymentType=" + paymentType + ", remark=" + remark + ", sender=" + sender
				+ ", receiver=" + receiver + "]";
	}

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