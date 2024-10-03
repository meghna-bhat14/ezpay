package com.ezpay.service;

import com.ezpay.model.Customer;

import com.ezpay.model.FraudEntry;
import com.ezpay.model.BankTransfer;
import com.ezpay.model.LoginInfo;
import com.ezpay.model.SuspiciousActivity;
import com.ezpay.model.UpiPayment; // Import UpiPayment
import com.ezpay.repository.BankTransferRepository;
import com.ezpay.repository.CustomerRepository;
import com.ezpay.repository.FraudEntryRepository;
import com.ezpay.repository.LoginInfoRepository;
import com.ezpay.repository.SuspiciousActivityRepository;
import com.ezpay.repository.UpiPaymentRepository; // Import UpiPaymentRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class FraudDetectionService {

    @Autowired
    private FraudEntryRepository fraudEntryRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Autowired
    private SuspiciousActivityRepository suspiciousActivityRepository;
    
    @Autowired
    private BankTransferRepository bankTransferRepository;

    @Autowired
    private UpiPaymentRepository upiPaymentRepository; // Change from TransactionRepository to UpiPaymentRepository

    // Method to flag a login attempt based on customerId
    public boolean flagLoginAttempt(Integer customerId) {
        try {
            // Insert fraud entry for login attempt
            insertFraudEntry(customerId, 1, 0);

            // Retrieve the risk counts
            int actual1 = suspiciousActivityRepository.getActualRiskCount(1);
            int countRisk1 = fraudEntryRepository.getCountRisk(customerId, 1);
            System.out.println("Login Risk Count: " + countRisk1 + "/" + actual1);

            // Check if the login attempts exceed the threshold
            if (actual1 <= countRisk1) {
                updateLoginDetails(customerId, 1);
                return true; // Indicate that the user should be blocked
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions
        }
        return false; // Indicate that the user should not be blocked
    }

    // Method to flag a transaction based on customerId
    public boolean flagUpiTransactionForToken(Integer customerId) {
        try {
            // Get the number of successful UPI payments for the customer
            int paymentCount = upiPaymentRepository.getUpiTransactionCount(customerId);
            System.out.println("Payment Count = " + paymentCount);
            
            // Only proceed if the customer has more than 3 payments (to get the pattern)
            if (paymentCount >= 3) {
                // Calculate average successful payment amount and the last initiated payment amount
                double avgAmount = upiPaymentRepository.getAverageUpiTransactionAmount(customerId);
                double paymentAmount = upiPaymentRepository.getLastUpiTransactionAmount(customerId);

                System.out.println("Average Amount = " + avgAmount + ", Payment Amount = " + paymentAmount);
                if (paymentAmount > avgAmount) {
                    return true; // Indicate potential fraud detected
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions
        } 
        return false; // Return the result to the controller
    }
    
    public boolean flagUpiTransaction(Integer customerId) {
        try {
            // Insert a fraud entry for high transaction
            insertFraudEntry(customerId, 2, 0);
            
            // Get actual risk count and count of fraud entries
            int actual2 = suspiciousActivityRepository.getActualRiskCount(2);
            int countRisk2 = fraudEntryRepository.getCountRisk(customerId, 2);
            System.out.println("Transaction Risk Count: " + countRisk2 + "/" + actual2);

            // Check if the payment amounts exceed the threshold
            if (actual2 <= countRisk2) {
                updateLoginDetails(customerId, 2);

                // Get the UPI payment associated with the customerId and processing transaction
                UpiPayment payment = upiPaymentRepository.findUpiPaymentByCustomerId(customerId);
                if (payment != null) {
                    // Change payment status to "failed"
                    payment.setStatus(0); // Assuming 0 indicates failed
                    upiPaymentRepository.save(payment); // Persist the changes
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions
        }
        return false;
    }
    
    
    public boolean flagBankTransactionForToken(Integer customerId) {
        try {
            // Get the number of successful UPI payments for the customer
            int paymentCount = bankTransferRepository.getBankTransactionCount(customerId);
            System.out.println("Payment Count = " + paymentCount);
            


            
            // Only proceed if the customer has more than 3 payments (to get the pattern)
            if (paymentCount >= 3) {
            	
                System.out.println("Querying for customerId: " + customerId);
                Double paymentAmount = bankTransferRepository.getLastBankTransactionAmount(customerId);
                System.out.println("Payment Amount = " + paymentAmount);
                // Calculate average successful payment amount and the last initiated payment amount
                double avgAmount = bankTransferRepository.getAverageBankTransactionAmount(customerId);
                //double paymentAmount = bankTransferRepository.getLastBankTransactionAmount(customerId);

                System.out.println("Average Amount = " + avgAmount + ", Payment Amount = " + paymentAmount);
                if (paymentAmount > avgAmount) {
                    return true; // Indicate potential fraud detected
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions
        } 
        return false; // Return the result to the controller
    }
    
    
    public boolean flagBankTransaction(Integer customerId) {
        try {
            // Insert a fraud entry for high transaction
            insertFraudEntry(customerId, 3, 0);
            
            // Get actual risk count and count of fraud entries
            int actual2 = suspiciousActivityRepository.getActualRiskCount(3);
            int countRisk2 = fraudEntryRepository.getCountRisk(customerId, 3);
            System.out.println("Transaction Risk Count: " + countRisk2 + "/" + actual2);

            // Check if the payment amounts exceed the threshold
            if (actual2 <= countRisk2) {
                updateLoginDetails(customerId, 3);

                // Get the UPI payment associated with the customerId and processing transaction
                BankTransfer payment = bankTransferRepository.findBankPaymentByCustomerId(customerId);
                if (payment != null) {
                    // Change payment status to "failed"
                    payment.setStatus(0); // Assuming 0 indicates failed
                    bankTransferRepository.save(payment); // Persist the changes
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions
        }
        return false;
    }
    
    

    // Insert a new fraud entry in the database
    private void insertFraudEntry(Integer customerId, int blockId, int resolved) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        System.out.println("Check here");
        SuspiciousActivity suspiciousActivity = suspiciousActivityRepository.findById(blockId).orElse(null);

        if (customer != null && suspiciousActivity != null) {
            FraudEntry fraudEntry = new FraudEntry();
            fraudEntry.setCustomer(customer);
            fraudEntry.setSuspiciousActivity(suspiciousActivity);
            fraudEntry.setDateOfEntry(new Timestamp(System.currentTimeMillis()));
            fraudEntry.setResolved(resolved);

            fraudEntryRepository.save(fraudEntry);
        }
    }

    // Update login details to block a user
    private void updateLoginDetails(Integer customerId, int blockId) {
        LoginInfo loginDetail = loginInfoRepository.findByCustomerCustomerId(customerId);
        SuspiciousActivity suspiciousActivity = suspiciousActivityRepository.findById(blockId).orElse(null);

        if (loginDetail != null && suspiciousActivity != null) {
            System.out.println("Updating login details...");
            loginDetail.setSuspiciousActivity(suspiciousActivity);
            loginInfoRepository.save(loginDetail); // Update the login detail to block the user
        }
    }
}
