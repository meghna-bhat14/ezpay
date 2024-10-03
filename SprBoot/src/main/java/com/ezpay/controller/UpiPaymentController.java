//package com.ezpay.controller;
//
//import java.util.Scanner;
//
//import com.ezpay.auth.Authentication;
//import com.ezpay.model.BankAccount;
//import com.ezpay.model.User;
//import com.ezpay.repo.UserRepository;
//import com.ezpay.service.BankAccountService;
//import com.ezpay.service.UpiPaymentService;
//import com.ezpay.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//
///**
// * * Controller class for managing bank transfers.
// * 
// * @author Ravva Amrutha
// * @date 11-09-29024 
// */
//@Controller
//public class UpiPaymentController {
//	
//	@Autowired
//	private Authentication authentication1;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private BankAccountService bankAccountService;
//
//    @Autowired
//    private UpiPaymentService upiPaymentService;
//
//    @Autowired
//    private Authentication authentication;
//    
//    @Autowired
//    private UserRepository userRepository; 
//
//    /**
//     * Displays the main menu for UPI Payment options and handles user selection.
//     */
//    public void displayMenu() {
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            // Displaying menu options
//            System.out.println("1. UPI Payment");
//            System.out.println("2. Exit");
//            System.out.print("Select an option: ");
//            int option = scanner.nextInt();
//            scanner.nextLine(); // Consume newline
//
//            switch (option) {
//                case 1:
//                    initiateUpiPayment(scanner);
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
//     * Initiates a UPI payment process by handling user and bank account verification.
//     *
//     * @param scanner Scanner to read user inputs
//     */
//    private void initiateUpiPayment(Scanner scanner) {
//        System.out.print("Enter your UPI ID: ");
//        String senderUpiId = scanner.nextLine();
//
//        // Validate UPI ID format
//        if (!bankAccountService.isValidUpiId(senderUpiId)) {
//            System.out.println("Invalid UPI ID. It must be a 10-digit number followed by '@ezpay'.");
//            return;
//        }
//
//        // If user does not exist, register new user
//        //if (!userService.isUpiIdExist(senderUpiId)) {
//        if(!userService.isUpiIdExist(senderUpiId)) {
//            System.out.println("Please register user");
//            return;
//            }
////           
//           
//        if (userService.isUpiIdExist(senderUpiId) && !bankAccountService.isUpiIdExist(senderUpiId)) {
//            // User exists, but bank account needs to be activated
//            System.out.println("User exists but bank account doesn't, so activating the bank account.");
//            
//            String accountNumber = userService.getUserByAccountNumber(senderUpiId);
//            //System.out.print(accountNumber);
//            System.out.print("Set your UPI Pin: ");
//            String upiPin = scanner.nextLine();
//            System.out.print("Confirm your UPI Pin: ");
//            String confirmUpiPin = scanner.nextLine();
//
//            // Check if UPI Pins match
//            if (!upiPin.equals(confirmUpiPin)) {
//                System.out.println("UPI Pins do not match. Please try again.");
//                return;
//            }
//
//            // Validate UPI Pin format
//            if (!bankAccountService.isValidUpiPin(upiPin)) {
//                System.out.println("UPI Pin must be a 4-digit number.");
//                return;
//            }
//
//            // Activate bank account for the existing user
//            User user = userService.getUserByUpiId(senderUpiId);
//            bankAccountService.createBankAccount(accountNumber, user, upiPin);
//            System.out.println("Bank account activated successfully.");
//            return;
//        }
//
//        // Proceed with UPI payment
//        authentication1.startSession();
//        System.out.print("Enter UPI Pin: ");
//        String upiPin = scanner.nextLine();
//
//        System.out.print("Enter receiver UPI ID: ");
//        String receiverUpiId = scanner.nextLine();
//        
//        
//        
//        // If user does not exist, register new user
//        //if (!userService.isUpiIdExist(senderUpiId)) {
//        if(!userService.isUpiIdExist(receiverUpiId)) {
//            System.out.println("User doesnt exist");
//            return;
//            }
//        
//        if (userService.isUpiIdExist(senderUpiId) && !bankAccountService.isUpiIdExist(receiverUpiId)) {
//            // User exists, but bank account needs to be activated
//            System.out.println("Receiver Bank account not activated");
//            return;}
//        
//        
//        
//        
//        
//
//        // Validate receiver UPI ID
//        BankAccount receiverAccount = bankAccountService.getBankAccountByUpiId(receiverUpiId);
//        if (receiverAccount == null) {
//            System.out.println("UPI ID not associated with any bank account. Please try again.");
//            return;
//        }
//
//        // Prompt for payment details
//        System.out.print("Enter amount to transfer: ");
//        double amount = scanner.nextDouble();
//        scanner.nextLine(); // Consume newline
//
//        System.out.print("Add a note (optional): ");
//        String label = scanner.nextLine();
//
//        // Process the UPI payment
//        upiPaymentService.processPayment(senderUpiId, receiverUpiId, amount, label, upiPin, authentication1);
//
//        // End authentication session after payment
//        authentication1.endSession();
//    }
//}

package com.ezpay.controller;

import java.util.Scanner;

import com.ezpay.auth.Authentication;
import com.ezpay.model.BankAccount;
import com.ezpay.model.UpiPayment;
import com.ezpay.repository.CustomerRepository;
import com.ezpay.model.Customer;
import com.ezpay.service.BankAccountService;
import com.ezpay.service.UpiPaymentService;
import com.ezpay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UpiPaymentController {
    
    @Autowired
    private Authentication authentication;

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UpiPaymentService upiPaymentService;

    @Autowired
    private CustomerRepository customerRepository; 
    

    /**
     * Fetch UPI ID associated with the given customer_id.
     */
    @GetMapping("/customer/{customer_id}/upi-id")
    public ResponseEntity<String> getUpiIdByCustomerId(@PathVariable int customer_id) {
        Customer customer = customerRepository.findByCustomerId(customer_id).orElse(null);
        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer not found.");
        }

        String upiId = customer.getUpiId();
        return ResponseEntity.ok(upiId);
    }

    
    @PostMapping("/meghna")
    public ResponseEntity<String> Meghna(@RequestBody Meghna request) {
    	String name = request.getMeghna();
    	
    	System.out.println(name);
    	return ResponseEntity.ok("Name is " + name);
    	
    }

    /**
     * Endpoint to activate bank account for an existing user.
     */
    @PostMapping("/activate-bank-account")
    public ResponseEntity<String> activateBankAccount(@RequestBody ActivationRequest request) {
        String upiId = request.getUpiId();
        String upiPin = request.getUpiPin();

        if (!userService.isUpiIdExist(upiId)) {
            return ResponseEntity.badRequest().body("User does not exist.");
        }

        // Check if bank account already exists
        if (bankAccountService.isUpiIdExist(upiId)) {
            return ResponseEntity.badRequest().body("Bank account already activated.");
        }

        // Activate the bank account
        Customer customer = userService.getUserByUpiId(upiId);
        String accountNumber = userService.getUserByAccountNumber(upiId);
        BankAccount bankacc1 =bankAccountService.createBankAccount(accountNumber, customer, upiPin);
  	  
  	  String responseMessage = "Bank account linked successfully. Current balance: " + String.valueOf(bankacc1.getBankAccountBalance());
          

          return ResponseEntity.ok(responseMessage);
    }

    /**
     * Endpoint to process UPI payments.
     */
    /*
    @PostMapping("/process-payment")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest request) {
        String senderUpiId = request.getSenderUpiId();
        String receiverUpiId = request.getReceiverUpiId();
        double amount = request.getAmount();
        String label = request.getLabel();
        String upiPin = request.getUpiPin();
        authentication.startSession();
        
        System.out.println("Sender id "+senderUpiId);
        System.out.println("Receiver id "+receiverUpiId);
        System.out.println("amount "+amount);
        System.out.println("label "+label);
        System.out.println("upi Pin "+upiPin);

        if(!bankAccountService.isUpiIdExist(senderUpiId)) {
        	return ResponseEntity.badRequest().body("Bank account not activated. Please activate your account");
        }
        // Validate sender and receiver UPI IDs
        if (!userService.isUpiIdExist(senderUpiId) || !bankAccountService.isUpiIdExist(receiverUpiId)) {
            return ResponseEntity.badRequest().body("Invalid sender or receiver UPI ID.");
        }

        // Process the payment
        UpiPayment result = upiPaymentService.processPayment(senderUpiId, receiverUpiId, amount, label, upiPin, authentication);
        authentication.endSession();
        if (result.getStatus()==1) {
            return ResponseEntity.ok("Payment processed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Payment Failed "+ result.getRemark());
        }
        
    } */
    
    //also retrieving payment id 
    @PostMapping("/processs-payment")
    public ResponseEntity<PaymentResponse> processsPayment(@RequestBody PaymentRequest request) {
        String senderUpiId = request.getSenderUpiId();
        String receiverUpiId = request.getReceiverUpiId();
        double amount = request.getAmount();
        String label = request.getLabel();
        String upiPin = request.getUpiPin();
        String remark=request.getRemark();
        authentication.startSession();
        
        System.out.println("Sender id "+senderUpiId);
        System.out.println("Receiver id "+receiverUpiId);
        System.out.println("amount "+amount);
        System.out.println("label "+label);
        System.out.println("remark "+remark);
//        System.out.println("upi Pin "+upiPin);

        // Validate sender and receiver UPI IDs
        if (!userService.isUpiIdExist(senderUpiId) || !userService.isUpiIdExist(receiverUpiId)) {
            return ResponseEntity.badRequest().body(new PaymentResponse("Invalid sender or receiver UPI ID.","no payment id generated"));
        }
        
        if(!bankAccountService.isUpiIdExist(senderUpiId)|| !bankAccountService.isUpiIdExist(receiverUpiId)) {
     
        	return ResponseEntity.badRequest().body(new PaymentResponse("Bank account not activated. Please activate your account","no payment id generated"));
        }
        
     

        // Process the payment
        UpiPayment result = upiPaymentService.processPayment(senderUpiId, receiverUpiId, amount, label, upiPin,remark, authentication);
        authentication.endSession();
        if (result.getStatus()==1) {
            return ResponseEntity.ok(new PaymentResponse ("Payment processed successfully.",result.getPaymentId()));
        } 
        else if(result.getStatus()==3)
        {
        	System.out.println("Being called here in 2 ...");
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new PaymentResponse(result.getRemark(), result.getPaymentId()));
        }else {
            return ResponseEntity.badRequest().body(new PaymentResponse(result.getRemark(),result.getPaymentId()));
        }
        
    }
    
    
 // New GetMapping method to get the balance for a given senderUpiId  
    @GetMapping("/get-balance")
    public ResponseEntity<String> getBalance(@RequestParam String senderUpiId, @RequestParam String SenderUpiPin) {
        if (!userService.isUpiIdExist(senderUpiId)) {
            return ResponseEntity.badRequest().body("User does not exist.");
        }
        
        // Get the balance from the bank account service
        if(!bankAccountService.verifyUpiPin(senderUpiId, SenderUpiPin)) {
        	return ResponseEntity.badRequest().body("Incorrect transaction pin");
        }
        BankAccount bankAccount = bankAccountService.getBankAccountByUpiId(senderUpiId);
        
        
        if(bankAccount == null) {
        	return ResponseEntity.badRequest().body("Bank account not found for the provided UPI ID.");
        }
        double balance = bankAccount.getBankAccountBalance();
        

        return ResponseEntity.ok("The balance for UPI ID " + senderUpiId + " is: " + balance);
    }
    
    
    @GetMapping("/payment-status/{paymentId}")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String paymentId) {
        Integer status = upiPaymentService.getPaymentStatus(paymentId);
        if (status == null) {
            return ResponseEntity.badRequest().body("Payment not found.");
        }
        return ResponseEntity.ok("Payment status: " + status);
    }


    // Inner classes for request bodies
    public static class ActivationRequest {
        private String upiId;
        private String upiPin;

        // Getters and Setters
        public String getUpiId() { return upiId; }
        public void setUpiId(String upiId) { this.upiId = upiId; }
        public String getUpiPin() { return upiPin; }
        public void setUpiPin(String upiPin) { this.upiPin = upiPin; }
    }

    public static class PaymentRequest {
        private String senderUpiId;
        private String receiverUpiId;
        private double amount;
        private String label;
        private String upiPin;
        private String remark;

        // Getters and Setters
        public String getSenderUpiId() { return senderUpiId; }
       public String getRemark() {return remark;}
       public void setRemark(String remark) {this.remark=remark;}
		public void setSenderUpiId(String senderUpiId) { this.senderUpiId = senderUpiId; }
        public String getReceiverUpiId() { return receiverUpiId; }
        public void setReceiverUpiId(String receiverUpiId) { this.receiverUpiId = receiverUpiId; }
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        public String getUpiPin() { return upiPin; }
        public void setUpiPin(String upiPin) { this.upiPin = upiPin; }
    }
    public static class Meghna{
    	private String meghna;
    	public String getMeghna() { return meghna; }
        public void setMeghna(String meghna) { this.meghna = meghna; }
    	
    }
    
    public static class PaymentResponse {
        private String reason;
        private String paymentId;

        public PaymentResponse(String reason, String paymentId) {
            this.reason = reason;
            this.paymentId = paymentId;
        }

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }

        public String getPaymentId() { return paymentId; }
        public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    }
    
    
    //USECASE 6 CODE 
    @GetMapping("/totalupi/{id}")
    public Double getTotalUpiAmount(@PathVariable("id") Long customerId) {
    	return upiPaymentService.getTotalUpiAmount(customerId);
    }
    
    @GetMapping("/previousmonthupicount/{userId}")
    public Integer getUpiTransactionCount(@PathVariable Long userId) {
        int upiTransactionCount = upiPaymentService.countUpiTransactionsForPreviousMonth(userId);
        return upiTransactionCount;
    }

    
    
}

