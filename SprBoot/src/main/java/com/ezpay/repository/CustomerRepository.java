package com.ezpay.repository;

import com.ezpay.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    Optional<Customer> findByUpiId(String upiId);
 
    Optional<Customer> findByBankAccountNumberAndIfscCode(String accountNumber, String ifscCode);
    
    Optional<Customer> findByBankAccountNumber(String accountNumber);
    
    Optional<Customer> findByCustomerId(int customerId);
//    public User findByUpiId(String upiId);
}
