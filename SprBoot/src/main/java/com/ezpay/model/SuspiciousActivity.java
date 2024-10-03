package com.ezpay.model;

/**
 * SuspiciousActivity Class
 * 
 * Mapping class for the suspicious_activities table in the database
 * 
 *     Author: Dikshitha Vani V
 *     Date: 03/09/2024
 * 
 */
import jakarta.persistence.*;

@Entity
@Table(name = "suspicious_activities")
public class SuspiciousActivity {

    @Id
    @Column(name = "block_id")
    private int blockId;

    @Column(name = "description")
    private String description;

    @Column(name = "risk_count")
    private int riskCount;

    // Getters and Setters
    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRiskCount() {
        return riskCount;
    }

    public void setRiskCount(int riskCount) {
        this.riskCount = riskCount;
    }
}
