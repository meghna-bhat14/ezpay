package com.ezpay.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "fraud_entries_uc2")
public class FraudEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "entry_id")
    private Integer entryID;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "block_id", referencedColumnName = "block_id")
    private SuspiciousActivity suspiciousActivity;

    @Column(name = "date_of_entry")
    private Timestamp dateOfEntry;


    @Column(name = "resolved")
    private int resolved;

    // Getters and Setters
    public Integer getEntryID() {
        return entryID;
    }

    public void setEntryID(Integer entryID) {
        this.entryID = entryID;
    }


    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SuspiciousActivity getSuspiciousActivity() {
        return suspiciousActivity;
    }

    public void setSuspiciousActivity(SuspiciousActivity suspiciousActivity) {
        this.suspiciousActivity = suspiciousActivity;
    }

    public Timestamp getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Timestamp dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public int getResolved() {
        return resolved;
    }

    public void setResolved(int resolved) {
        this.resolved = resolved;
    }
}
