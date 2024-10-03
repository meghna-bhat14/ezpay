//package com.ezpay.controller;
//import java.util.Scanner;
//
////import org.apache.catalina.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//
//import com.ezpay.model.User;
//import com.ezpay.auth.Authentication;
//import com.ezpay.model.BankAccount;
//import com.ezpay.service.BankAccountService1;
//import com.ezpay.service.BankTransferService;
//import com.ezpay.service.UserService;
//
///**
// * Manages Bank payment operations, including user registration, bank account creation,
// * and processing payments.
// * 
// * @author Atishay Jain,Deepshika
// * @date 11-09-29024 */
//@Controller
//public class BankTransferController {
//	// Services to manage user, bank account, bank transfers, and authentication
//	@Autowired
//	private Authentication authentication1;
//	@Autowired
//    private UserService userService;
//	@Autowired
//    private BankAccountService1 bankAccountService1;
//	@Autowired
//    private BankTransferService bankTransferService;
//	
//	
//
//	/**
//     * Displays the main menu and handles user input for bank transfer operations.
//     */
//    public void displayMenu() {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("1. Bank Transfer");
//            System.out.println("2. Exit");
//            System.out.print("Select an option: ");
//            int option = scanner.nextInt();
//            scanner.nextLine(); // Consume newline
//
//            switch (option) {
//                case 1:
//                    initiateBankTransfer(scanner);
//                    break;
//                case 2:
//                    System.out.println("Exiting application.");
//                    return;
//                default:
//                    System.out.println("Invalid option. Please try again.");
//            }
//        }
//    }
//
//    /**
//     * Initiates a bank transfer transaction.
//     *
//     * @param scanner Scanner object for reading user input.
//     */
//    private void initiateBankTransfer(Scanner scanner) {
//        System.out.print("Enter your account number: ");
//        String senderAccountNumber = scanner.nextLine();
//
//        // Validate account number
//        if (!bankAccountService1.isValidAccountNumber(senderAccountNumber)) {
//            System.out.println("Invalid Account Number. Account Number must be an 8-digit number.");
//            return;
//        }
//
//        System.out.print("Enter your IFSC code: ");
//        String senderIfscCode = scanner.nextLine();
//
//        // Validate IFSC code
//        if (!bankAccountService1.isValidIfscCode(senderIfscCode)) {
//            System.out.println("Invalid IFSC Code.");
//            return;
//        }
//
//        // Check if the user exists by bank account and IFSC code
//        // Register new user if not found
//        if (!userService.isUserExistByAccountNumberAndIfscCode(senderAccountNumber, senderIfscCode)) {
//            System.out.println("Please register yourself");
////            registerNewUser(scanner, senderAccountNumber, senderIfscCode);
//            return;
//        } else if (userService.isUserExistByAccountNumberAndIfscCode(senderAccountNumber, senderIfscCode) &&
//                !bankAccountService1.isAccountExist(senderAccountNumber, senderIfscCode)) {
//            // User exists but bank account does not exist
//            System.out.println("User exists but bank account doesn't, so activating the bank account.");
//
//            System.out.print("Set your PIN: ");
//            String transactionPin = scanner.nextLine();
//            System.out.print("Confirm your PIN: ");
//            String confirmTransactionPin = scanner.nextLine();
//
//            // Validate PIN match
//            if (!transactionPin.equals(confirmTransactionPin)) {
//                System.out.println("Transaction PINs do not match. Please try again.");
//                return;
//            }
//
//            // Validate PIN format
//            if (!bankAccountService1.isValidPin(transactionPin)) {
//                System.out.println("Transaction PIN must be a 4-digit number.");
//                return;
//            }
//
//            User user = userService.getUserByAccountNumberAndIfscCode(senderAccountNumber, senderIfscCode);
//
//            // Create a new bank account with initial balance
//            bankAccountService1.createBankAccount(senderAccountNumber, user, transactionPin);
//            System.out.println("Your transaction PIN has been successfully set.");
//            return;
//        }
//
//        // User exists, proceed with transfer
//        BankAccount senderAccount = bankAccountService1.getBankAccountByAccountNumber(senderAccountNumber);
//
//        if (senderAccount == null) {
//            System.out.println("Account number and IFSC code combination not found. Please try again.");
//            return;
//        }
//
//        // Start session for transfer
//        authentication1.startSession();
//
//        // Use masked PIN input method
//        System.out.print("Enter your transaction PIN: ");
//        String transactionPin = scanner.nextLine();
//
//        System.out.print("Enter receiver's account number: ");
//        String receiverAccountNumber = scanner.nextLine();
//
//        // Validate receiver account number
//        if (!bankAccountService1.isValidAccountNumber(receiverAccountNumber)) {
//            System.out.println("Invalid Account Number. Account Number must be an 8-digit number.");
//            return;
//        }
//
//        System.out.print("Enter receiver's IFSC code: ");
//        String receiverIfscCode = scanner.nextLine();
//
//        // Validate receiver IFSC code
//        if (!bankAccountService1.isValidIfscCode(receiverIfscCode)) {
//            System.out.println("Invalid IFSC Code.");
//            return;
//        }
//        
//        
//        
//        if (!userService.isUserExistByAccountNumberAndIfscCode(receiverAccountNumber, receiverIfscCode)) {
//            System.out.println("User doesn'y exist");
////            registerNewUser(scanner, senderAccountNumber, senderIfscCode);
//            return;
//        } else if (userService.isUserExistByAccountNumberAndIfscCode(receiverAccountNumber, receiverIfscCode) &&
//                !bankAccountService1.isAccountExist(receiverAccountNumber, receiverIfscCode)) {
//            // User exists but bank account does not exist
//            System.out.println("Receiver bank account not activated");
//            return;}
//
//        System.out.print("Enter amount to transfer: ");
//        double amount = scanner.nextDouble();
//        scanner.nextLine(); // Consume newline
//
//        System.out.print("Add a note (optional): ");
//        String label = scanner.nextLine();
//
//        // Indicate transfer initiation
//        System.out.println("Initiating transfer...");
//
//        // Process transfer and display status
//        bankTransferService.processBankTransfer(
//                senderAccountNumber, senderIfscCode, receiverAccountNumber, receiverIfscCode, amount, label, transactionPin, authentication1);
//
//        // End session after transfer
//        authentication1.endSession();
//    }
//
//   
//   
//}

package com.ezpay.controller;
//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezpay.auth.Authentication;
import com.ezpay.model.BankAccount;
import com.ezpay.model.BankTransfer;
import com.ezpay.model.Customer;
import com.ezpay.repository.CustomerRepository;
import com.ezpay.service.BankAccountService1;
import com.ezpay.service.BankTransferService;
import com.ezpay.service.UserService;

/**
 * Manages Bank payment operations, including user registration, bank account creation,
 * and processing payments.
 * 
 * @author Atishay Jain,Deepshika
 * @date 11-09-29024 */


@RestController
@RequestMapping("/API")
@CrossOrigin(origins = "http://localhost:3000")
public class BankTransferController {
	// Services to manage user, bank account, bank transfers, and authentication
	@Autowired
	private Authentication authentication1;
	@Autowired
    private UserService userService;
	@Autowired
    private BankAccountService1 bankAccountService1;
	@Autowired
    private BankTransferService bankTransferService;
	
	@Autowired
    private CustomerRepository customerRepository; 
	
	  
	@GetMapping("/customer/{customer_id}/bank-account-number")
    public ResponseEntity<String> getAccountNumberByCustomerId(@PathVariable int customer_id) {
        Customer customer = customerRepository.findByCustomerId(customer_id).orElse(null);
        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer not found.");
        }

        String accountNumber = customer.getBankAccountNumber();
        return ResponseEntity.ok(accountNumber);
    }
	
	
	
	@GetMapping("/customer/{customer_id}/ifsc-code")
    public ResponseEntity<String> getIfscCodeByCustomerId(@PathVariable int customer_id) {
        Customer customer = customerRepository.findByCustomerId(customer_id).orElse(null);
        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer not found.");
        }

        String accountNumber = customer.getIfscCode();
        return ResponseEntity.ok(accountNumber);
    }
	
	
	  @PostMapping("/activate-bank-account") 
	  public ResponseEntity<String>activateBankAccount(@RequestBody ActivationRequest request) { String
	  accountNumber = request.getAccountNumber(); String ifscCode =request.getIfscCode(); String pin=request.getPin();
	  
	  if
	  (!userService.isUserExistByAccountNumberAndIfscCode(accountNumber,ifscCode))
	  { return ResponseEntity.badRequest().body("User does not exist."); }
	  
	  // Check if bank account already exists if
	  if(bankAccountService1.isAccountExist(accountNumber, ifscCode)) { return
	  ResponseEntity.badRequest().body("Bank account already activated."); }
	  
	  // Activate the bank account User user =
      Customer customer = userService.getUserByAccountNumberAndIfscCode(accountNumber,ifscCode);

	  userService.getUserByAccountNumberAndIfscCode(accountNumber,ifscCode);
     BankAccount bankacc1 =bankAccountService1.createBankAccount(accountNumber, customer, pin);
	  
	  String responseMessage = "Bank account linked successfully. Current balance: " + String.valueOf(bankacc1.getBankAccountBalance());
        

        return ResponseEntity.ok(responseMessage);}
    
    
    
    
    
    @PostMapping("/process-transfer")
    public ResponseEntity<String> processTransfer(@RequestBody PaymentRequest request) {
        String senderAccountNumber = request.getSenderAccountNumber();
        String senderIfscCode=request.getSenderIfscCode();
        String receiverAccountNumber = request.getReceiverAccountNumber();
        String receiverIfscCode=request.getReceiverIfscCode();
        double amount = request.getAmount();
        String label = request.getLabel();
        String transactionPin = request.getPin();
        String remark=request.getRemark();
        authentication1.startSession();
        
        System.out.println("Sender AccountNumber "+senderAccountNumber);
        System.out.println("Sender IfscCode" +senderIfscCode);
        System.out.println("Receiver AccountNumber "+receiverAccountNumber);
        System.out.println("Receiver IfscCode " +receiverIfscCode);
        System.out.println("amount "+amount);
        System.out.println("label "+label);
        System.out.println("Pin "+transactionPin);
        System.out.println("Remark "+remark);
        
        
        if (!bankAccountService1.isAccountExist(senderAccountNumber, senderIfscCode)) {
            return ResponseEntity.badRequest().body("Bank account not activated. Please activate your account");
        }

        // Validate sender and receiver UPI IDs
        if (!userService.isUserExistByAccountNumberAndIfscCode(senderAccountNumber,senderIfscCode) || !bankAccountService1.isAccountExist(receiverAccountNumber, receiverIfscCode)) {
            return ResponseEntity.badRequest().body("Invalid sender or receiver UPI ID.");
        }

    
        
        
        
        
        BankTransfer result =bankTransferService.processBankTransfer(
                senderAccountNumber, senderIfscCode, receiverAccountNumber, receiverIfscCode, amount, label, transactionPin,remark, authentication1);
        authentication1.endSession();
        if (result.getStatus()==1) {
            return ResponseEntity.ok("Payment processed successfully.");
        } else if(result.getStatus()==3){
        	//System.out.println("Being called here in 2 ...");
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.getRemark());
        } else {
            return ResponseEntity.badRequest().body("Payment Failed "+ result.getRemark());
        }
        
    }
    
    
	
	  @GetMapping("/get-balance") 
	  public ResponseEntity<String> getBalance(@RequestParam String accountNumber,@RequestParam String ifscCode, @RequestParam String pin) {
	  
	  
	  if (!userService.isUserExistByAccountNumberAndIfscCode(accountNumber,ifscCode))
	  { return ResponseEntity.badRequest().body("User does not exist."); }
	  
	  
	  // Get the balance from the bank account service
	  if(!bankAccountService1.verifyPin(accountNumber, ifscCode,pin)) { 
		  return ResponseEntity.badRequest().body("Incorrect transaction pin"); } 
	  
	  BankAccount bankAccount = bankAccountService1.getBankAccountByAccountNumber(accountNumber);
	  
	  
	  if(bankAccount == null) { 
		  return ResponseEntity.badRequest().body("Bank account not found for the provided UPI ID."); } 
	 
	  double balance = bankAccount.getBankAccountBalance();
	  
	   return ResponseEntity.ok("The balance for Account Number " +accountNumber +
	  " is: " + balance); 
	   }
	 
    
    
    @GetMapping("/transfer-status/{transferId}")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String transferId) {
        Integer status = bankTransferService.getTransferStatus(transferId);
        if (status == null) {
            return ResponseEntity.badRequest().body("Transfer not found.");
        }
        return ResponseEntity.ok("Transfer status: " + status);
    }
    
    
    
    
    
    
    
    public static class ActivationRequest {
        private String accountNumber;
        private String ifscCode;
        private String pin;

        // Getters and Setters
        public String getAccountNumber() { return accountNumber; }
        public String getIfscCode() { return ifscCode; }
        public String getPin() { return pin; }


        public void setAccountNumber(String accountNumber) { this.accountNumber= accountNumber; }
        public void setIfscCode(String ifscCode) { this.ifscCode= ifscCode; }
        public void setUpiPin(String pin) { this.pin = pin; }
    }

    public static class PaymentRequest {
    	private String remark;
		private String senderAccountNumber;
        private String senderIfscCode;
        private String receiverAccountNumber;
        private String receiverIfscCode;
        private double amount;
        private String label;
        private String pin;

        // Getters and Setters
        public String getSenderAccountNumber() { return senderAccountNumber; }
        public void setSenderAccountNumber(String senderAccountNumber) { this.senderAccountNumber= senderAccountNumber; }
        public String getSenderIfscCode() { return senderIfscCode; }
        public void setSenderIfscCode(String senderIfscCode) { this.senderIfscCode= senderIfscCode; }
        public String getReceiverAccountNumber() { return receiverAccountNumber; }
        public void setReceiverAccountNumber(String receiverAccountNumber) { this.receiverAccountNumber= receiverAccountNumber; }
        public String getReceiverIfscCode() { return receiverIfscCode; }
        public void setReceiverIfscCode(String receiverIfscCode) { this.receiverIfscCode= receiverIfscCode; }
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        public String getPin() { return pin; }
        public void setPin(String pin) { this.pin = pin; }
        public String getRemark() { return remark; }
        public void setRemark(String remark) { this.remark = remark; }
    }
    
    
//USECASE 6 CODE 
    
    @GetMapping("/totalbank/{id}")
    public Double getTotalUpiAmount(@PathVariable("id") Long customerId) {
    	return bankTransferService.getTotalUpiAmount(customerId);
    }
    
    
    @GetMapping("/previousmonthbankcount/{userId}")
    public Integer getBankTransactionCount(@PathVariable Long userId) {
        int bankTransactionCount = bankTransferService.countBankTransactionsForPreviousMonth(userId);
        return bankTransactionCount;
    }
    
    
  
   
}