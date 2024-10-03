
package com.ezpay.service;

import com.ezpay.model.Customer;
import com.ezpay.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean isUserExist(String userId) {
        return customerRepository.findById(Integer.valueOf(userId)).isPresent();
    }

//    public void registerUserForBankTransfer(String username, String accountNumber, String upiId, String ifscCode) {
//        User user = new User(username, upiId, ifscCode, accountNumber);
//        userRepository.save(user);
//    }

//    public User registerUser(String username, String upiId, String accountNumber) {
//        User user = new User(username, upiId, accountNumber);
//        return userRepository.save(user);
//    }

    public Customer getUserByUpiId(String upiId) {
        Optional<Customer> customer = customerRepository.findByUpiId(upiId);
        return customer.orElse(null);
    }

    public boolean isUpiIdExist(String upiId) {
        return customerRepository.findByUpiId(upiId).isPresent();
    }

	
	  public boolean isUserExistByAccountNumberAndIfscCode(String accountNumber,
	  String ifscCode) { return
	  customerRepository.findByBankAccountNumberAndIfscCode(accountNumber, ifscCode).isPresent(); }
	 
    
	/*
	 * public boolean isUserExistByAccountNumberAndIfscCode(String accountNumber,
	 * String ifscCode) { System.out.println("Account Number: " + accountNumber);
	 * System.out.println("IFSC Code: " + ifscCode); return
	 * userRepository.findByAccNoAndIfscCode(accountNumber, ifscCode).isPresent(); }
	 */


    public String getUserByAccountNumber(String senderUpiId) {
        return customerRepository.findByUpiId(senderUpiId).map(Customer::getBankAccountNumber).orElse(null);
    }

    public Customer getUserByAccountNumberAndIfscCode(String accountNumber, String ifscCode) {
        return customerRepository.findByBankAccountNumberAndIfscCode(accountNumber, ifscCode).orElse(null);
    }

    public String getUpiIdByAccountNumber(String accountNumber) {
        return customerRepository.findByBankAccountNumber(accountNumber).map(Customer::getUpiId).orElse(null);
    }
}
//
//
