package com.ezpay.controller;

import com.ezpay.service.BankTransferService;
import com.ezpay.service.FraudDetectionService;
import com.ezpay.service.TokenService;
import com.ezpay.service.UpiPaymentService; // Change TransactionService to UpiPaymentService

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Only for REST endpoints
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React app
public class FraudDetectionController {

    @Autowired
    private FraudDetectionService fraudDetectionService;
    
    @Autowired
    private UpiPaymentService upiPaymentService; // Use UpiPaymentService instead of TransactionService
    
    @Autowired
    private BankTransferService bankTransferService; // Use UpiPaymentService instead of TransactionService

    
    @Autowired
    private TokenService tokenService;

    // Internal method for flagging login attempts (No change needed)
    public boolean flagLoginAttempt(Integer customerId) {
        boolean isBlocked = fraudDetectionService.flagLoginAttempt(customerId);
        if (isBlocked) {
            System.out.println("User with customerId " + customerId + " is blocked due to suspicious login attempts.");
        } else {
            System.out.println("Login attempt for customerId " + customerId + " is not suspicious.");
        }
        return isBlocked;
    }

    // Internal method for flagging UPI transaction (modified to use UpiPayment)
    public boolean flagUpiTransactionForToken(Integer customerId) {
        return fraudDetectionService.flagUpiTransactionForToken(customerId); // Update to Upi-specific method
    }

    // REST endpoint to flag UPI transactions
    @PostMapping("/flagTransaction")
    public boolean flagUpiTransaction(@RequestParam Integer customerId) {
        boolean isBlocked = fraudDetectionService.flagUpiTransaction(customerId); // Update to Upi-specific method
        if (isBlocked) {
            System.out.println("UPI Transaction for customerId " + customerId + " is blocked due to high amount.");
        } else {
            System.out.println("UPI Transaction for customerId " + customerId + " is not flagged.");
        }
        return isBlocked;
    }
    
    
    @PostMapping("/flagBankTransaction")
    public boolean flagBankTransaction(@RequestParam Integer customerId) {
        boolean isBlocked = fraudDetectionService.flagBankTransaction(customerId); // Update to Upi-specific method
        if (isBlocked) {
            System.out.println("Bank Transaction for customerId " + customerId + " is blocked due to high amount.");
        } else {
            System.out.println("Bank Transaction for customerId " + customerId + " is not flagged.");
        }
        return isBlocked;
    }
    
    // REST endpoint to generate token
    @PostMapping("/generate-token")
    public String generateToken(@RequestBody HashMap<String, String> request) {
        String customerId = request.get("customerId");
        return tokenService.generateToken(customerId);
    }

    // REST endpoint to validate token and update UPI payment status
    @PostMapping("/validate-token")
    public boolean validateToken(@RequestBody HashMap<String, String> request) {
        String customerId = request.get("customerId");
        String token = request.get("token");
        boolean isValid = tokenService.validateToken(customerId, token);
        
        if (isValid) {
            // Update UPI payment status to success if the token is valid
            upiPaymentService.updateUPIPaymentStatus(Integer.parseInt(customerId)); // Update UPI-specific method
        }
        
        return isValid;
    }
    
    @PostMapping("/validate-tokenB")
    public boolean validateTokenTransfer(@RequestBody HashMap<String, String> request) {
        String customerId = request.get("customerId");
        String token = request.get("token");
        boolean isValid = tokenService.validateToken(customerId, token);
        
        if (isValid) {
            // Update UPI payment status to success if the token is valid
            bankTransferService.updateBankPaymentStatus(Integer.parseInt(customerId)); // Update UPI-specific method
        }
        
        return isValid;
    }
}