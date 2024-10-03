package com.ezpay.repository;

import com.ezpay.model.SuspiciousActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SuspiciousActivityRepository extends JpaRepository<SuspiciousActivity, Integer> {

    @Query("SELECT s.riskCount FROM SuspiciousActivity s WHERE s.blockId = :blockId")
    int getActualRiskCount(@Param("blockId") int blockId);
}
