package com.ezpay.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezpay.auth.Authentication;
import com.ezpay.model.BankAccount;
import com.ezpay.model.BankTransfer;
import com.ezpay.model.Customer;
import com.ezpay.repository.BankTransferRepository;
import com.ezpay.repository.CustomerRepository;

import jakarta.mail.MessagingException;


/**
 * Service class for handling bank transfers.
 * Provides methods for processing transfers, recording failed transfers, and printing transaction history.
 * Author: Deepshika(Sprint 1)
 * Author: Atishay(Sprint 2)
 * Date: 2024-09-01
 */
@Service
public class BankTransferService {
	@Autowired
    private BankTransferRepository bankTransferRepository;
	@Autowired
    private BankAccountService1 bankAccountService;

	@Autowired
	private CustomerRepository customerRepository;	
	
	
	@Autowired
	private FraudDetectionService fraudDetectionService;
    /**
     * Processes a bank transfer from the sender to the receiver.
     * Validates account numbers, IFSC codes, and PINs, checks for sufficient balance, and performs the transaction.
     * @param senderAccountNumber the account number of the sender
     * @param senderIfscCode the IFSC code of the sender's bank
     * @param receiverAccountNumber the account number of the receiver
     * @param receiverIfscCode the IFSC code of the receiver's bank
     * @param amount the amount to be transferred
     * @param label a label for the transfer
     * @param transactionPin the PIN for transaction authorization
     * @param auth the authentication object for session validation
     * @return 
     */
    public BankTransfer processBankTransfer(String senderAccountNumber, String senderIfscCode, 
                                    String receiverAccountNumber, String receiverIfscCode, 
                                    double amount, String label, String transactionPin,String remark, Authentication auth) {
       
    	Customer sender = customerRepository.findByBankAccountNumber(senderAccountNumber).orElse(null);
    	Customer receiver = customerRepository.findByBankAccountNumber(receiverAccountNumber).orElse(null);
    	
    	
    	
    	// Generate a custom transfer ID with the format: BNKXXXX-XXXX-XXXX-XXXX
        String transferId = "BNK" + generateRandomDigits(4) + "-" + generateRandomDigits(4) + "-" + generateRandomDigits(4) + "-" + generateRandomDigits(4);
    	
        //create a new payment record
    	BankTransfer transfer = new BankTransfer(
                transferId,
                senderAccountNumber,
                senderIfscCode,
                receiverAccountNumber,
                receiverIfscCode,
                amount,
                4,
                label,
                LocalDateTime.now(),
                "BANK_TRANSFER",
                remark,
                sender,
                receiver
            );
    	bankTransferRepository.save(transfer);
    	
    	// Retrieve bank accounts for sender and receiver
        BankAccount senderAccount = getBankAccountService().getBankAccountByAccountNumber(senderAccountNumber);
        BankAccount receiverAccount = getBankAccountService().getBankAccountByAccountNumber(receiverAccountNumber);
        
     // Display message indicating payment initiation
        System.out.println("Payment initiated from " + senderAccountNumber + " to " + receiverAccountNumber + "...");

     // Simulate processing delay
        try {
            Thread.sleep(15000); // Simulate processing time with a 3-second delay
        } catch (InterruptedException e) {
            System.out.println("Payment processing interrupted.");
            return transfer;
        }
        
     // Display message indicating payment initiation
        System.out.println("Payment Processing. Processing payment from " + senderAccountNumber + " to " + receiverAccountNumber + "...");
        
     
          // Update the existing payment record status
          transfer.setStatus(3);
          bankTransferRepository.save(transfer);
    	
       // Simulate further processing
          try {
              Thread.sleep(15000); // Simulate processing time with a 15-second delay
          } catch (InterruptedException e) {
              System.out.println("Payment processing interrupted.");
          }
          
          //check1
          // Check if the session is active
    	if (!auth.isSessionActive()) {
            System.out.println("Session has expired. Transaction aborted.");
            transfer.setStatus(0);
            transfer.setRemark("Session expired");
            bankTransferRepository.save(transfer);
//            recordFailedTransfer(senderAccountNumber, senderIfscCode, receiverAccountNumber, receiverIfscCode, amount, "FAIL: Session expired", label);
            return transfer;
        }

        // Validate IFSC codes for sender and receiver
        if (!senderAccount.getUser().getIfscCode().equals(senderIfscCode)) {
            System.out.println("Invalid IFSC Code for the respective sender Account Number");
            System.out.println("Transaction failed");
            transfer.setStatus(0);
            transfer.setRemark("Invalid IFSC code for sender");
            bankTransferRepository.save(transfer);
//            recordFailedTransfer(senderAccountNumber, senderIfscCode, receiverAccountNumber, receiverIfscCode, amount, "FAIL: Account Number, IFSC Code mismatch", label);
            return transfer;
        }
        
        if (!receiverAccount.getUser().getIfscCode().equals(receiverIfscCode)) {
            System.out.println("Invalid IFSC Code for the respective receiver Account Number");
            System.out.println("Transaction failed");
            transfer.setStatus(0);
            transfer.setRemark("Invalid IFSC code for receiver");
            bankTransferRepository.save(transfer);
//            recordFailedTransfer(senderAccountNumber, senderIfscCode, receiverAccountNumber, receiverIfscCode, amount, "FAIL: Account Number, IFSC Code mismatch", label);
            return transfer;
        }

        // Ensure sender and receiver are different accounts
        if (senderAccountNumber.equals(receiverAccountNumber)) {
            System.out.println("Sender and receiver Account Number should not be the same");
            System.out.println("Transaction failed");
            transfer.setStatus(0);
            transfer.setRemark("Sender and receiver Account Number should not be same");
            bankTransferRepository.save(transfer);
//            recordFailedTransfer(senderAccountNumber, senderIfscCode, receiverAccountNumber, receiverIfscCode, amount, "FAIL: Sender and receiver Account Number Same", label);
            return transfer; 
        }

        // Verify the provided PIN
        if (!bankAccountService.verifyPin(senderAccountNumber, senderIfscCode, transactionPin)) {
            System.out.println("Incorrect PIN. Transaction failed.");
            transfer.setStatus(0);
            transfer.setRemark("Incorrect pin");
            bankTransferRepository.save(transfer);
//            recordFailedTransfer(senderAccountNumber, senderIfscCode, receiverAccountNumber, receiverIfscCode, amount, "FAIL: Incorrect PIN", label);
            return transfer;
        }

        // Check if sender has sufficient balance
        if (senderAccount.getBankAccountBalance() < amount) {
            System.out.println("Insufficient balance. Transfer failed.");
            transfer.setStatus(0);
            transfer.setRemark("Insufficient balance");
            bankTransferRepository.save(transfer);
//            recordFailedTransfer(senderAccountNumber, senderIfscCode, receiverAccountNumber, receiverIfscCode, amount, "FAIL: Insufficient balance", label);
            return transfer;
        }
        
        
        
        if(fraudDetectionService.flagBankTransactionForToken(sender.getCustomerId()))
        {
        		System.out.println("Bank Transaction flagged for token verification");
                 transfer.setStatus(3);
        		//can it be with a status 3 itself? like its still processing?
                 transfer.setRemark("Bank Transaction flagged for token verification");
                 bankTransferRepository.save(transfer);
                 return transfer;

        }


        // Deduct amount from sender and add to receiver
        double newSenderBalance = senderAccount.getBankAccountBalance() - amount;
        double newReceiverBalance = receiverAccount.getBankAccountBalance() + amount;

        getBankAccountService().updateAccountBalance(senderAccount, newSenderBalance);
        getBankAccountService().updateAccountBalance(receiverAccount, newReceiverBalance);


        transfer.setStatus(1);
        
        BankTransfer savedbanktransaction = bankTransferRepository.save(transfer);

        System.out.println("Transfer successful. Transfer ID: " + transfer.getTransferId());
        this.processBankTransaction(savedbanktransaction);
        System.out.println("Email has been sent successfully please check that ");
        
		return transfer;
      
    }

    /**
     * Records a failed transfer transaction.
     * @param senderAccountNumber the account number of the sender
     * @param senderIfscCode the IFSC code of the sender's bank
     * @param receiverAccountNumber the account number of the receiver
     * @param receiverIfscCode the IFSC code of the receiver's bank
     * @param amount the amount attempted for transfer
     * @param failureReason the reason for the failure
     * @param label a label for the transfer
     */
    public void recordFailedTransfer(String senderAccountNumber, String senderIfscCode, 
                                     String receiverAccountNumber, String receiverIfscCode,
                                     double amount, Integer failureReason, String label, String remark) {
    	Customer sender = customerRepository.findByBankAccountNumber(senderAccountNumber).orElse(null);
    	Customer receiver = customerRepository.findByBankAccountNumber(receiverAccountNumber).orElse(null);
    	
    	
    	// Generate a custom transfer ID with the format: BNKXXXX-XXXX-XXXX-XXXX
    	 	
    	
        BankTransfer failedTransfer = new BankTransfer(
        	UUID.randomUUID().toString(),
            senderAccountNumber,
            senderIfscCode,
            receiverAccountNumber,
            receiverIfscCode,
            amount,
            failureReason, // Mark as failed
            label,
            LocalDateTime.now(),
            "BANK_TRANSFER",
            remark,
            sender,
            receiver
        );
        bankTransferRepository.save(failedTransfer);
    }

    /**
     * Gets the BankAccountService instance.
     * @return the BankAccountService instance
     */
    public BankAccountService1 getBankAccountService() {
        return bankAccountService;
    }

    /**
     * Sets the BankAccountService instance.
     * @param bankAccountService the BankAccountService instance to set
     */
    public void setBankAccountService(BankAccountService1 bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * Gets the BankTransferRepository instance.
     * @return the BankTransferRepository instance
     */
    public BankTransferRepository getBankTransferRepository() {
        return bankTransferRepository;
    }

    /**
     * Sets the BankTransferRepository instance.
     * @param bankTransferRepository the BankTransferRepository instance to set
     */
    public void setBankTransferRepository(BankTransferRepository bankTransferRepository) {
        this.bankTransferRepository = bankTransferRepository;
    }
    
    
    
    public Integer getTransferStatus(String transferId) {
        BankTransfer transfer = bankTransferRepository.findByTransferId(transferId);
        
        if (transfer!=null) {

            return transfer.getStatus();
        } else {
            return 5;
        }
        
        
}
    
    
    private String generateRandomDigits(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((int) (Math.random() * 10));  // Random digit from 0-9
        }
        return sb.toString();
    }

    
   //USECASE 6 CODE 
    
    @Autowired
    private BankEmailService emailService;
    public void processBankTransaction(BankTransfer transaction) {
        // Save the transaction as successful
        
        

        // Retrieve sender and receiver email from User repository
        Customer sender = customerRepository.findByBankAccountNumber(transaction.getSenderAccountNumber())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Customer receiver = customerRepository.findByBankAccountNumber(transaction.getReceiverAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Send email to both sender and receiver
        try {
            emailService.sendTransactionSuccessEmail(sender.getEmail(), receiver.getEmail(), transaction);
        } catch (MessagingException e) {
            // Handle the exception (e.g., log it)
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
    
    
    
    public Double getTotalUpiAmount(Long customerId) {
    	return bankTransferRepository.getTotalAmountByCustomerId(customerId).orElse(0.0);
    }
    
    public int countBankTransactionsForPreviousMonth(Long userId) {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfLastMonth = now.minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfLastMonth = now.withDayOfMonth(1).minusDays(1).atTime(23, 59, 59);
        
        return bankTransferRepository.countBankTransfersByUserIdAndPreviousMonth(userId, startOfLastMonth, endOfLastMonth);
    }
    
    //Uc3 code:-
    
    public List<BankTransfer> getTransactionsByDateRangeAndCustomerId(LocalDateTime start, LocalDateTime end, Long customerId) {
        return bankTransferRepository.findByTimestampBetweenAndCustomerId(start, end, customerId);
    } 

    public List<BankTransfer> getTransactionsByExactAmountAndCustomerId(double amount, Long customerId) {
        return bankTransferRepository.findByAmountAndCustomerId(amount, customerId);
    }

    public List<BankTransfer> getTransactionsByCustomerId(Long customerId) {
    	return bankTransferRepository.findByCustomerId(customerId); 
    }

    public List<BankTransfer> getTransactionsByMinAmountAndCustomerId(double minAmount, Long customerId) {
        return bankTransferRepository.findByAmountGreaterThanEqualAndCustomerId(minAmount, customerId);
    }

    public List<BankTransfer> getTransactionsByMaxAmountAndCustomerId(double maxAmount, Long customerId) {
        return bankTransferRepository.findByAmountLessThanEqualAndCustomerId(maxAmount, customerId);
    }

    public List<BankTransfer> getTransactionsByStatusAndCustomerId(Long status, Long customerId) {
        return bankTransferRepository.findByStatusAndCustomerId(status, customerId);
    }

    public List<BankTransfer> getTransactionsByDescriptionAndCustomerId(String descriptionKeyword, Long customerId) {
        return bankTransferRepository.findByDescriptionContainingAndCustomerId(descriptionKeyword, customerId);
    }

    public BankTransfer getTransactionByTransferIdAndCustomerId(String id, Long customerId) {
        return bankTransferRepository.findByTransferIdAndCustomerId(id, customerId);
    }
    
    
    public boolean updateBankPaymentStatus(int customerId) {
        BankTransfer transaction = bankTransferRepository.findBankPaymentByCustomerId(customerId);
        
        if (transaction != null) {
            // Update the status to "success"
            transaction.setStatus(1);
            System.out.println("Transaction status set to success");
            bankTransferRepository.save(transaction);
            
            // Get sender and receiver UPI IDs from the transaction
            String senderAccountNumber = transaction.getSenderAccountNumber();
            String receiverAccountNumber = transaction.getReceiverAccountNumber();
            double amount = transaction.getAmount(); // Assuming there's an amount field in UpiPayment
            
            // Update bank account balances
            BankAccount senderAccount = getBankAccountService().getBankAccountByAccountNumber(senderAccountNumber);
            BankAccount receiverAccount = getBankAccountService().getBankAccountByAccountNumber(receiverAccountNumber);
            
            // Directly update the balances without checking
            double newSenderBalance = senderAccount.getBankAccountBalance() - amount;
            double newReceiverBalance = receiverAccount.getBankAccountBalance() + amount;
            
            // Update the balances
            bankAccountService.updateAccountBalance(senderAccount, newSenderBalance);
            bankAccountService.updateAccountBalance(receiverAccount, newReceiverBalance);
            
            System.out.println("Bank account balances updated successfully");
            return true; // Indicate that the status was updated successfully
        }
        
        System.out.println("Transaction has been set as failed");
        return false; // Indicate that no processing transaction was found
    }

}