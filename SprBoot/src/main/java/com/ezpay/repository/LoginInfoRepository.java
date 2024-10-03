package com.ezpay.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ezpay.model.LoginInfo;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, String> {

    LoginInfo findByCustomerCustomerId(Integer customerId);
    
    LoginInfo findBySuspiciousActivityBlockId(Integer blockId);
}

