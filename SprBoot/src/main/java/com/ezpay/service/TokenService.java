package com.ezpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@Service
public class TokenService {
    private final HashMap<String, String> tokens = new HashMap<>();
    private static final String HARD_CODED_EMAIL = "dikshu.v002@gmail.com"; // Hard-coded email address


    @Autowired
    private JavaMailSender emailSender;

    // Generate a token for a UPI transaction
    public String generateToken(String customerId) {
    	
        final Random random = new Random();
        String token = String.format("%04d", random.nextInt(10000)); // Generate a 4-digit OTP
        tokens.put(customerId, token);
        System.out.println("Token (OTP) sent to " + customerId + ": " + token);
        sendTokenEmail(HARD_CODED_EMAIL, token); // Send the token to the hard-coded email
        // You can implement email sending logic here if needed
        return "OTP generated and sent to your email!";

        //String token = UUID.randomUUID().toString(); // Generate a unique token
        //tokens.put(customerId, token);
        //System.out.println("Token sent to " + customerId + ": " + token);
        // You can implement email sending logic here if needed
        //return "Token generated and sent to your email!";
    }
    
    
    private void sendTokenEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Token");
        message.setText("Your token is: " + token);
        emailSender.send(message);
        System.out.println("Token sent to " + to + ": " + token);
    }


    // Validate token and flag transaction if invalid
    public boolean validateToken(String customerId, String token) {
        if (tokens.containsKey(customerId) && tokens.get(customerId).equals(token)) {
            tokens.remove(customerId); // Token is one-time use
            return true;
        }
        // Flag transaction if token validation fails
        System.out.println("Invalid token for customerId: " + customerId);
        return false;
    }
}
