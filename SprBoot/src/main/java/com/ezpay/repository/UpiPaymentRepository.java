package com.ezpay.repository;

import com.ezpay.model.UpiPayment;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UpiPaymentRepository extends JpaRepository<UpiPayment, String> {
    // Define custom query methods if needed
	UpiPayment findByPaymentId(String paymentId);
	
	
	// UC 6 CODE
	@Query("SELECT SUM(t.amount) FROM UpiPayment t WHERE t.sender.customerId = :customerId")
	Optional<Double> getTotalAmountByCustomerId(@Param("customerId") Long customerId);
	
	
	@Query("SELECT COUNT(u) FROM UpiPayment u WHERE u.sender.customerId = :customerId AND u.timestamp BETWEEN :startDate AND :endDate")
    int countUpiPaymentsByUserIdAndPreviousMonth(@Param("customerId") Long customerId, 
                                                 @Param("startDate") LocalDateTime startDate, 
                                                 @Param("endDate") LocalDateTime endDate);
	
	//Uc3 codE:-
	
	 @Query("SELECT up FROM UpiPayment up WHERE up.timestamp BETWEEN :start AND :end AND (up.sender.customerId = :customerId OR up.receiver.customerId = :customerId)")
	    List<UpiPayment> findByTimestampBetweenAndCustomerId(
	            @Param("start") LocalDateTime start,
	            @Param("end") LocalDateTime end,
	            @Param("customerId") Long customerId
	    );

	    @Query("SELECT up FROM UpiPayment up WHERE FLOOR(up.amount) = FLOOR(:amount) AND (up.sender.customerId = :customerId OR up.receiver.customerId = :customerId)")
	    List<UpiPayment> findByAmountAndCustomerId(
	            @Param("amount") double amount,
	            @Param("customerId") Long customerId
	    );



	    @Query("SELECT up FROM UpiPayment up WHERE up.amount >= :minAmount AND (up.sender.customerId = :customerId OR up.receiver.customerId = :customerId)")
	    List<UpiPayment> findByAmountGreaterThanEqualAndCustomerId(
	            @Param("minAmount") double minAmount,
	            @Param("customerId") Long customerId
	    );

	    @Query("SELECT up FROM UpiPayment up WHERE up.sender.customerId = :customerId OR up.receiver.customerId = :customerId")
	    List<UpiPayment> findByCustomerId(@Param("customerId") Long customerId);

	    @Query("SELECT up FROM UpiPayment up WHERE up.amount <= :maxAmount AND (up.sender.customerId = :customerId OR up.receiver.customerId = :customerId)")
	    List<UpiPayment> findByAmountLessThanEqualAndCustomerId(
	            @Param("maxAmount") double maxAmount,
	            @Param("customerId") Long customerId
	    );

	    @Query("SELECT up FROM UpiPayment up WHERE up.status = :status AND (up.sender.customerId = :customerId OR up.receiver.customerId = :customerId)")
	    List<UpiPayment> findByStatusAndCustomerId(
	            @Param("status") Long status,
	            @Param("customerId") Long customerId
	    );

	    @Query("SELECT up FROM UpiPayment up WHERE up.paymentId = :paymentId AND (up.sender.customerId = :customerId OR up.receiver.customerId = :customerId)")
	    UpiPayment findByPaymentIdAndCustomerId(
	            @Param("paymentId") String paymentId,
	            @Param("customerId") Long customerId
	    );

	    @Query("SELECT up FROM UpiPayment up WHERE up.sender.customerId = :customerId")
	    List<UpiPayment> findBySenderCustomerId(@Param("customerId") Long customerId);

	    @Query("SELECT up FROM UpiPayment up WHERE up.receiver.customerId = :customerId")
	    List<UpiPayment> findByReceiverCustomerId(@Param("customerId") Long customerId);

	    @Query("SELECT up FROM UpiPayment up WHERE LOWER(up.label) LIKE LOWER(CONCAT('%', :descriptionKeyword, '%')) AND (up.sender.customerId = :customerId OR up.receiver.customerId = :customerId)")
	    List<UpiPayment> findByDescriptionContainingAndCustomerId(
	            @Param("descriptionKeyword") String descriptionKeyword,
	            @Param("customerId") Long customerId
	    );
	    
	    
	    
	    
	    @Transactional
	    @Query("SELECT AVG(up.amount) FROM UpiPayment up WHERE up.sender.customerId = :customerId AND up.status = 1") // 1 for success
	    Double getAverageUpiTransactionAmount(@Param("customerId") Integer customerId);

	    @Transactional
	    @Query("SELECT up.amount FROM UpiPayment up WHERE up.sender.customerId = :customerId AND up.status = 3") // 3 for processing
	    Double getLastUpiTransactionAmount(@Param("customerId") Integer customerId);
	    
	    @Transactional
	    @Query("SELECT COUNT(up) FROM UpiPayment up WHERE up.sender.customerId = :customerId AND up.status = 1") // 1 for success
	    int getUpiTransactionCount(@Param("customerId") Integer customerId);
	    
	    @Transactional
	    @Query("SELECT up FROM UpiPayment up WHERE up.sender.customerId = :customerId AND up.status = 3") // 0 for 'processing' status
	    UpiPayment findUpiPaymentByCustomerId(@Param("customerId") Integer customerId);
	
}
