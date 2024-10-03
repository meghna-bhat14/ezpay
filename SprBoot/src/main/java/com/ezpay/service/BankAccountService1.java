//package com.ezpay.service;
//
//import com.ezpay.model.BankAccount;
//import com.ezpay.model.User;
//import com.ezpay.repo.BankAccountRepository;
//
//import java.io.Console;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.time.LocalDateTime;
//import java.util.Base64;
//import java.util.Scanner;
//import java.util.regex.Pattern;
//import javax.swing.*;
//
//
///**
// * Service class for managing bank accounts.
// * Provides methods for creating bank accounts, verifying UPI PINs, updating balances, and validating inputs.
// * Author: Deepshika
// * Date: 2024-09-02
// */
//
//public class BankAccountService1 {
//    private static final Pattern PIN_PATTERN = Pattern.compile("\\d{4}");
//    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("\\d{8}");
//    private static final Pattern IFSC_CODE_PATTERN = Pattern.compile("[A-Z]{4}0[A-Z0-9]{6}");
//    private static final Pattern UPI_ID_PATTERN = Pattern.compile("\\d{10}@ezpay");
//
//    private BankAccountRepository bankAccountRepository = new BankAccountRepository();
//
//    /**
//     * Creates a new bank account with the given details.
//     * Validates the account number, IFSC code, and PIN, hashes the PIN, and initializes the account with a default balance.
//     * @param accountNumber the bank account number
//     * @param userId the user ID
//     * @param ifscCode the IFSC code
//     * @param accountType the type of the account (e.g., SAVINGS)
//     * @param pin the PIN for bank transactions
//     */
//    public void createBankAccount(String accountNumber, User user, String ifscCode, String accountType, String pin,String upiId) {
//		
//
//        if (!isValidPin(pin)) {
//            System.out.println("PIN must be a 4-digit number.");
//            return;
//        }
//
//        if (!isValidIfscCode(ifscCode)) {
//            System.out.println("Invalid IFSC code.");
//            return;
//        }
//
//        double initialBalance = 10000.0; // Initial balance set to 10,000
//        LocalDateTime registrationDate = LocalDateTime.now();
//
//        // Hash the PIN before storing it
//        String hashedPin = hashPin(pin);
//
//        BankAccount newAccount = new BankAccount(accountNumber, user,upiId, initialBalance, registrationDate, accountType, hashedPin,ifscCode);
//        bankAccountRepository.addBankAccount(newAccount);
//        System.out.println("Bank account registered.\nCurrent balance: 10,000.");
//    }
//
//    /**
//     * Retrieves a bank account based on the provided account number and IFSC code.
//     * @param accountNumber the bank account number
//     * @param ifscCode the IFSC code of the account
//     * @return the BankAccount object if found, null otherwise
//     */
//    public BankAccount getBankAccountByAccountNumber(String accountNumber) {
//        return bankAccountRepository.getBankAccountByAccountNumber(accountNumber);
//    }
//
//    /**
//     * Verifies if the provided PIN matches the stored hash for the given account number and IFSC code.
//     * @param accountNumber the bank account number
//     * @param ifscCode the IFSC code of the account
//     * @param pin the PIN to verify
//     * @return true if the PIN is valid, false otherwise
//     */
//    public boolean verifyPin(String accountNumber, String ifscCode, String pin) {
//        if (!isValidPin(pin)) {
//            System.out.println("PIN must be a 4-digit number.");
//            return false;
//        }
//
//        BankAccount account = getBankAccountByAccountNumber(accountNumber);
//        if (account != null) {
//            String hashedPin = hashPin(pin);
//            return hashedPin.equals(account.getUpiPin());
//        }
//        return false;
//    }
//
//    /**
//     * Updates the balance of a given bank account.
//     * @param account the BankAccount object to update
//     * @param newBalance the new balance to set
//     */
//    public void updateAccountBalance(BankAccount account, double newBalance) {
//        account.setAccountBalance(newBalance);
//        System.out.println("Remaining Balance: " + newBalance);
//    }
//
//    /**
//     * Checks if an account number is already assigned to a bank account.
//     * @param accountNumber the bank account number to check
//     * @return true if an account number is already assigned, false otherwise
//     */
//    public boolean isAccountNumberAlreadyAssigned(String accountNumber) {
//        BankAccount account = bankAccountRepository.getBankAccountByAccountNumber(accountNumber);
//        return account != null;
//    }
//
//    /**
//     * Validates the format of a bank account number.
//     * @param accountNumber the bank account number to validate
//     * @return true if the account number is valid, false otherwise
//     */
//    public boolean isValidAccountNumber(String accountNumber) {
//        return accountNumber != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
//    }
//
//    /**
//     * Validates the format of a PIN.
//     * @param pin the PIN to validate
//     * @return true if the PIN is valid, false otherwise
//     */
//    public boolean isValidPin(String pin) {
//        return pin != null && PIN_PATTERN.matcher(pin).matches();
//    }
//
//    /**
//     * Validates the format of an IFSC code.
//     * @param ifscCode the IFSC code to validate
//     * @return true if the IFSC code is valid, false otherwise
//     */
//    public boolean isValidIfscCode(String ifscCode) {
//        return ifscCode != null && IFSC_CODE_PATTERN.matcher(ifscCode).matches();
//    }
//    
//    
//    public boolean isValidUpiId(String upiId) {
//        return upiId != null && UPI_ID_PATTERN.matcher(upiId).matches();
//    }
//
//    /**
//     * Hashes a PIN using SHA-256 algorithm.
//     * @param pin the PIN to hash
//     * @return the Base64 encoded hashed PIN
//     */
//    public String hashPin(String pin) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hashedBytes = md.digest(pin.getBytes());
//            return Base64.getEncoder().encodeToString(hashedBytes);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error hashing the PIN", e);
//        }
//    }
//    public void setBankAccountRepository(BankAccountRepository bankAccountRepository) {
//		this.bankAccountRepository = bankAccountRepository;
//	}
//
//    public boolean isAccountAndIfscExist(String accountNumber, String ifscCode) {
//        BankAccount account = bankAccountRepository.getBankAccountByAccountNumberAndIfsc(accountNumber, ifscCode);
//        return account != null;
//    }
//
//	public boolean isUpiIdAlreadyAssigned(String upiId) {
//		 BankAccount account = bankAccountRepository.getBankAccountByUpiId(upiId);
//	        return account != null && account.getBankAccountNumber() != null;
//	
//	}
//
//
//}

/**
 * Service class for managing bank accounts.
 * Provides methods for creating bank accounts, verifying UPI PINs, updating balances, and validating inputs.
 * Author: Deepshika
 * Date: 2024-09-02
 */

package com.ezpay.service;

import com.ezpay.model.BankAccount;
import com.ezpay.model.BankPinDetails;
import com.ezpay.model.Customer;
import com.ezpay.repository.BankAccountRepository;
import com.ezpay.repository.BankPinDetailRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BankAccountService1 {
    private static final Pattern PIN_PATTERN = Pattern.compile("\\d{4}");
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("\\d{10,18}");
    private static final Pattern IFSC_CODE_PATTERN = Pattern.compile("[A-Z]{4}0[A-Z0-9]{6}");
    private static final Pattern UPI_ID_PATTERN = Pattern.compile("\\d{10}@ezpay");
    @Autowired
    private BankAccountRepository bankAccountRepository;
    
    @Autowired
    private BankPinDetailRepository bankPindeatilRepository;

    /**
     * Creates a new bank account with the given details.
     * Validates the account number, IFSC code, and PIN, hashes the PIN, and initializes the account with a default balance.
     * @param accountNumber the bank account number
     * @param customer the User object associated with the bank account
     * @param ifscCode the IFSC code of the bank
     * @param accountType the type of the account (e.g., SAVINGS)
     * @param pin the PIN for bank transactions
     * @param upiId the UPI ID to be assigned to the bank account
     */
    public BankAccount createBankAccount(String accountNumber, Customer user, String pin) {
        

        //double initialBalance = 10000.0; // Initial balance set to 10,000
    	double initialBalance = 1.0 * (int)getRandomBalance(5000, 50000);
        // Hash the PIN before storing it
        String hashedPin = hashPin(pin);
        
        BankPinDetails newPin =new  BankPinDetails(accountNumber,hashedPin,1,user);
        bankPindeatilRepository.save(newPin);

        BankAccount newAccount = new BankAccount(accountNumber, initialBalance, 0.0 ,user);
        bankAccountRepository.save(newAccount);
        System.out.println("Bank account activated with an initial balance of " + initialBalance);
        return newAccount;
    }
    private double getRandomBalance(int min, int max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
    /**
     * Retrieves a bank account based on the provided account number.
     * @param accountNumber the bank account number
     * @return the BankAccount object if found, null otherwise
     */
    public BankAccount getBankAccountByAccountNumber(String accountNumber) {
        return bankAccountRepository.findByBankAccountNumber(accountNumber);
    }

    /**
     * Verifies if the provided PIN matches the stored hash for the given account number.
     * @param accountNumber the bank account number
     * @param pin the PIN to verify
     * @return true if the PIN is valid, false otherwise
     */
    public boolean verifyPin(String accountNumber, String ifscCode, String pin) {
        if (!isValidPin(pin)) {
            System.out.println("PIN must be a 4-digit number.");
            return false;
        }
        
        Optional<BankPinDetails> bankPinDetailsOpt = bankPindeatilRepository.findByBankAccountNumber(accountNumber);

        // Check if the bank account details are present and match the IFSC code
        if (bankPinDetailsOpt.isPresent()) {
            BankPinDetails bankPinDetail = bankPinDetailsOpt.get();
            String hashedPin = hashPin(pin);
            return hashedPin.equals(bankPinDetail.getHashedTransactionPin());
            }
        
        

			/*
			 * BankAccount account = getBankAccountByAccountNumber(accountNumber); if
			 * (account != null) { String hashedPin = hashPin(pin); return
			 * hashedPin.equals(account.getUpiPin()); }
			 */
        return false;
    }

    /**
     * Updates the balance of a given bank account.
     * @param account the BankAccount object to update
     * @param newBalance the new balance to set
     */
    public void updateAccountBalance(BankAccount account, double newBalance) {
        account.setBankAccountBalance(newBalance);
        bankAccountRepository.save(account);

        System.out.println("Remaining Balance: " + newBalance);
    }

  
    /**
     * Validates the format of a bank account number.
     * @param accountNumber the bank account number to validate
     * @return true if the account number is valid, false otherwise
     */
    public boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }

    /**
     * Validates the format of a PIN.
     * @param pin the PIN to validate
     * @return true if the PIN is valid, false otherwise
     */
    public boolean isValidPin(String pin) {
        return pin != null && PIN_PATTERN.matcher(pin).matches();
    }

    /**
     * Validates the format of an IFSC code.
     * @param ifscCode the IFSC code to validate
     * @return true if the IFSC code is valid, false otherwise
     */
    public boolean isValidIfscCode(String ifscCode) {
        return ifscCode != null && IFSC_CODE_PATTERN.matcher(ifscCode).matches();
    }
    
    /**
     * Validates the format of a UPI ID.
     * @param upiId the UPI ID to validate
     * @return true if the UPI ID is valid, false otherwise
     */
    public boolean isValidUpiId(String upiId) {
        return upiId != null && UPI_ID_PATTERN.matcher(upiId).matches();
    }

    /**
     * Hashes a PIN using the SHA-256 algorithm.
     * @param pin the PIN to hash
     * @return the Base64 encoded hashed PIN
     */
    public String hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(pin.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing the PIN", e);
        }
    }

    /**
     * Sets the BankAccountRepository instance.
     * @param bankAccountRepository the BankAccountRepository instance to set
     */
    public void setBankAccountRepository(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }


    public boolean isAccountExist(String accountNumber, String ifscCode) {
        BankAccount account = bankAccountRepository.findByBankAccountNumber(accountNumber);
        return account != null;
    }
    

  
}

