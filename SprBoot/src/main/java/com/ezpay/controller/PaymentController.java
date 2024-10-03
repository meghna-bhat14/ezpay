package com.ezpay.controller;

import java.util.Scanner;

import com.ezpay.auth.Authentication;
import com.ezpay.model.BankAccount;
import com.ezpay.model.Customer;
import com.ezpay.service.BankAccountService;
import com.ezpay.service.BankAccountService1;
import com.ezpay.service.BankTransferService;
import com.ezpay.service.UpiPaymentService;
import com.ezpay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * * Controller class for managing bank transfers.
 * 
 * @author Ravva Amrutha
 * @date 11-09-29024 
 */
@Controller
public class PaymentController {
	
	@Autowired
	private Authentication authentication1;

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;
    
    @Autowired
    private BankAccountService1 bankAccountService1;

    @Autowired
    private UpiPaymentService upiPaymentService;

    @Autowired
    private Authentication authentication;
    
    @Autowired
    private BankTransferService bankTransferService;
    

    /**
     * Displays the main menu for UPI Payment options and handles user selection.
     */
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Displaying menu options
        	try {
            System.out.println("1. UPI Payment");
            System.out.println("2. Bank Transfer");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (option) {
                case 1:
                    initiateUpiPayment(scanner);
                    break;
                case 2:
                    initiateBankTransfer(scanner);

                    break;
                case 3:
                    System.out.println("Exiting application.");
                  return;
                  
                default:
                    System.out.println("Invalid option. Please try again.");
            }}catch(Exception e) {
            	System.out.println("Invalid option");
            	return;
            }
        }
        
    }

    /**
     * Initiates a UPI payment process by handling user and bank account verification.
     *
     * @param scanner Scanner to read user inputs
     */
    private void initiateUpiPayment(Scanner scanner) {
        System.out.print("Enter your UPI ID: ");
        String senderUpiId = scanner.nextLine();

        // Validate UPI ID format
        if (!bankAccountService.isValidUpiId(senderUpiId)) {
            System.out.println("Invalid UPI ID. It must be a 10-digit number followed by '@ezpay'.");
            return;
        }

        // If user does not exist, register new user
        //if (!userService.isUpiIdExist(senderUpiId)) {
        if(!userService.isUpiIdExist(senderUpiId)) {
            System.out.println("Please register user");
            return;
            }
//           
           
        if (userService.isUpiIdExist(senderUpiId) && !bankAccountService.isUpiIdExist(senderUpiId)) {
            // User exists, but bank account needs to be activated
            System.out.println("User exists but bank account doesn't, so activating the bank account.");
            
            String accountNumber = userService.getUserByAccountNumber(senderUpiId);
         
            System.out.print("Set your UPI Pin: ");
            String upiPin = scanner.nextLine();
            System.out.print("Confirm your UPI Pin: ");
            String confirmUpiPin = scanner.nextLine();

            // Check if UPI Pins match
            if (!upiPin.equals(confirmUpiPin)) {
                System.out.println("UPI Pins do not match. Please try again.");
                return;
            }

            // Validate UPI Pin format
            if (!bankAccountService.isValidUpiPin(upiPin)) {
                System.out.println("UPI Pin must be a 4-digit number.");
                return;
            }

            // Activate bank account for the existing user
            Customer customer = userService.getUserByUpiId(senderUpiId);
            bankAccountService.createBankAccount(accountNumber, customer, upiPin);
            System.out.println("Bank account activated successfully.");
            return;
        }

        // Proceed with UPI payment
        authentication1.startSession();
        System.out.print("Enter UPI Pin: ");
        String upiPin = scanner.nextLine();

        System.out.print("Enter receiver UPI ID: ");
        String receiverUpiId = scanner.nextLine();
        
        
        
        // If user does not exist, register new user
        //if (!userService.isUpiIdExist(senderUpiId)) {
        if(!userService.isUpiIdExist(receiverUpiId)) {
            System.out.println("User doesnt exist");
            return;
            }
        
        if (userService.isUpiIdExist(senderUpiId) && !bankAccountService.isUpiIdExist(receiverUpiId)) {
            // User exists, but bank account needs to be activated
            System.out.println("Receiver Bank account not activated");
            return;}
        
        
        
        
        

        // Validate receiver UPI ID
        BankAccount receiverAccount = bankAccountService.getBankAccountByUpiId(receiverUpiId);
        if (receiverAccount == null) {
            System.out.println("UPI ID not associated with any bank account. Please try again.");
            return;
        }

        // Prompt for payment details
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Add a note (optional): ");
        String label = scanner.nextLine();

        // Process the UPI payment
       // upiPaymentService.processPayment(senderUpiId, receiverUpiId, amount, label, upiPin, authentication1);

        // End authentication session after payment
        authentication1.endSession();
    }
    
    
    
    
    private void initiateBankTransfer(Scanner scanner) {
        System.out.print("Enter your account number: ");
        String senderAccountNumber = scanner.nextLine();

        // Validate account number
        if (!bankAccountService1.isValidAccountNumber(senderAccountNumber)) {
            System.out.println("Invalid Account Number. Account Number must be an 8-digit number.");
            return;
        }

        System.out.print("Enter your IFSC code: ");
        String senderIfscCode = scanner.nextLine();

        // Validate IFSC code
        if (!bankAccountService1.isValidIfscCode(senderIfscCode)) {
            System.out.println("Invalid IFSC Code.");
            return;
        }

        // Check if the user exists by bank account and IFSC code
        // Register new user if not found
        if (!userService.isUserExistByAccountNumberAndIfscCode(senderAccountNumber, senderIfscCode)) {
            System.out.println("Please register yourself");
//            registerNewUser(scanner, senderAccountNumber, senderIfscCode);
            return;
        } else if (userService.isUserExistByAccountNumberAndIfscCode(senderAccountNumber, senderIfscCode) &&
                !bankAccountService1.isAccountExist(senderAccountNumber, senderIfscCode)) {
            // User exists but bank account does not exist
            System.out.println("User exists but bank account doesn't, so activating the bank account.");

            System.out.print("Set your PIN: ");
            String transactionPin = scanner.nextLine();
            System.out.print("Confirm your PIN: ");
            String confirmTransactionPin = scanner.nextLine();

            // Validate PIN match
            if (!transactionPin.equals(confirmTransactionPin)) {
                System.out.println("Transaction PINs do not match. Please try again.");
                return;
            }

            // Validate PIN format
            if (!bankAccountService1.isValidPin(transactionPin)) {
                System.out.println("Transaction PIN must be a 4-digit number.");
                return;
            }

            Customer customer = userService.getUserByAccountNumberAndIfscCode(senderAccountNumber, senderIfscCode);

            // Create a new bank account with initial balance
            bankAccountService1.createBankAccount(senderAccountNumber, customer, transactionPin);
            System.out.println("Your transaction PIN has been successfully set.");
            return;
        }

        // User exists, proceed with transfer
        BankAccount senderAccount = bankAccountService1.getBankAccountByAccountNumber(senderAccountNumber);

        if (senderAccount == null) {
            System.out.println("Account number and IFSC code combination not found. Please try again.");
            return;
        }

        // Start session for transfer
        authentication1.startSession();

        // Use masked PIN input method
        System.out.print("Enter your transaction PIN: ");
        String transactionPin = scanner.nextLine();

        System.out.print("Enter receiver's account number: ");
        String receiverAccountNumber = scanner.nextLine();

        // Validate receiver account number
        if (!bankAccountService1.isValidAccountNumber(receiverAccountNumber)) {
            System.out.println("Invalid Account Number. Account Number must be an 8-digit number.");
            return;
        }

        System.out.print("Enter receiver's IFSC code: ");
        String receiverIfscCode = scanner.nextLine();

        // Validate receiver IFSC code
        if (!bankAccountService1.isValidIfscCode(receiverIfscCode)) {
            System.out.println("Invalid IFSC Code.");
            return;
        }
        
        
        
        if (!userService.isUserExistByAccountNumberAndIfscCode(receiverAccountNumber, receiverIfscCode)) {
            System.out.println("User doesn'y exist");
//            registerNewUser(scanner, senderAccountNumber, senderIfscCode);
            return;
        } else if (userService.isUserExistByAccountNumberAndIfscCode(receiverAccountNumber, receiverIfscCode) &&
                !bankAccountService1.isAccountExist(receiverAccountNumber, receiverIfscCode)) {
            // User exists but bank account does not exist
            System.out.println("Receiver bank account not activated");
            return;}

        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Add a note (optional): ");
        String label = scanner.nextLine();

        // Indicate transfer initiation
        System.out.println("Initiating transfer...");

        // Process transfer and display status
     //  bankTransferService.processBankTransfer(
      //          senderAccountNumber, senderIfscCode, receiverAccountNumber, receiverIfscCode, amount, label, transactionPin, authentication1);

        // End session after transfer
        authentication1.endSession();
    }
    
    
    
}
