package com.ezpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ezpay.model.FraudEntry;

@Repository
public interface FraudEntryRepository extends JpaRepository<FraudEntry, Integer> {

    @Query("SELECT COUNT(*) FROM FraudEntry fe WHERE fe.customer.customerId = :customerId AND fe.suspiciousActivity.blockId = :blockId AND fe.resolved = 0")
    int getCountRisk(@Param("customerId") Integer customerId, @Param("blockId") int blockId);
}
