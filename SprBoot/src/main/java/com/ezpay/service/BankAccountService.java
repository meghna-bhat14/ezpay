package com.ezpay.service;

import com.ezpay.model.BankAccount;
import com.ezpay.model.BankPinDetails;
import com.ezpay.model.Customer;
import com.ezpay.repository.BankAccountRepository;
import com.ezpay.repository.BankPinDetailRepository;
import com.ezpay.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class BankAccountService {

    private static final Pattern PIN_PATTERN = Pattern.compile("\\d{4}");
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("\\d{10,18}");
    private static final Pattern UPI_ID_PATTERN = Pattern.compile("\\d{10}@ezpay");

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired 
    private CustomerRepository customerRepository;
    
    @Autowired
    private BankPinDetailRepository bankPinDetailRepository;
    
    

    /**
     * Creates a new bank account and saves it using the repository.
     */
    /**
     * Creates a new bank account and saves it using the repository.
     */
    public BankAccount createBankAccount(String accountNumber, Customer user, String upiPin) {
       

        //double initialBalance = 10000.0;
    	double initialBalance = 1.0 * (int)getRandomBalance(5000, 50000);
//        LocalDateTime registrationDate = LocalDateTime.now();
        String hashedPin = hashPin(upiPin);
        
        BankPinDetails newPin =new  BankPinDetails(accountNumber,hashedPin,1,user);
        bankPinDetailRepository.save(newPin);
        
        
        BankAccount newAccount = new BankAccount(accountNumber, initialBalance, 0.0,user);
        bankAccountRepository.save(newAccount);
        System.out.println("Bank account activated with an initial balance of " + initialBalance);
        return newAccount;
    }
    private double getRandomBalance(int min, int max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    /**
     * Retrieves a bank account using the UPI ID.
     */
    public BankAccount getBankAccountByUpiId(String upiId) {
    	Customer customer = customerRepository.findByUpiId(upiId).orElse(null);
    	if(customer==null) {
    		System.out.println("User is null");
    		return null;
    	}
        return bankAccountRepository.findByCustomer(customer);
    }

    /**
     * Verifies the UPI PIN for a bank account.
     */
    public boolean verifyUpiPin(String upiId, String upiPin) {
        if (!isValidUpiPin(upiPin)) {
            System.out.println("UPI Pin must be a 4-digit number.");
            return false;
        }
        
        
        
    	
        Customer user1 = customerRepository.findByUpiId(upiId).orElse(null);
        Optional<BankPinDetails> bankPinDetailsOpt = bankPinDetailRepository.findByCustomer(user1);
        
        if (bankPinDetailsOpt.isPresent()) {
            BankPinDetails bankPinDetail = bankPinDetailsOpt.get();
            String hashedPin = hashPin(upiPin);
            return hashedPin.equals(bankPinDetail.getHashedTransactionPin());
            }
        return false;

        
			/*
			 * BankAccount account = bankAccountRepository.findByUser(user1); if (account !=
			 * null) { String hashedPin = hashPin(upiPin); return
			 * hashedPin.equals(account.getUpiPin()); } return false;
			 */
    }

    /**
     * Updates the balance of a bank account.
     */
    public void updateAccountBalance(BankAccount account, double newBalance) {
        account.setBankAccountBalance(newBalance);
        bankAccountRepository.save(account);  // Persist the updated balance
        System.out.println("Remaining Balance: " + newBalance);
    }

    /**
     * Checks if a UPI ID is already assigned to an account number.
     */
    public boolean isUpiIdAlreadyAssigned(String accountNumber) {
        BankAccount account = bankAccountRepository.findByBankAccountNumber(accountNumber);
        return account != null && account.getUser().getUpiId() != null;
    }

    /**
     * Validates the account number against the expected pattern.
     * 
     * @param accountNumber The account number to be validated.
     * @return true if the account number is valid, false otherwise.
     */
    public boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }
    /**
     * Validates the UPI PIN.
     * 
     */
    public boolean isValidUpiPin(String upiPin) {
        return upiPin != null && PIN_PATTERN.matcher(upiPin).matches();
    }

    /**
     * Validates the UPI ID.
     */
    public boolean isValidUpiId(String upiId) {
        return upiId != null && UPI_ID_PATTERN.matcher(upiId).matches();
    }
    /**
     * Checks if a UPI ID exists.
     */
    public boolean isUpiIdExist(String upiId) {
    	Customer customer = customerRepository.findByUpiId(upiId).orElse(null);
        return bankAccountRepository.findByCustomer(customer)!= null;
    }
    public String hashUpiPin(String pin) {
    	return hashPin(pin);
    }
    /**
     * Hashes the UPI PIN using SHA-256.
     */
    private String hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(pin.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing the PIN", e);
        }
    }
}
