package com.ezpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ezpay.model.BankAccount;
import com.ezpay.model.Customer;
import com.ezpay.model.UpiPayment;
import com.ezpay.repository.BankAccountRepository;
import com.ezpay.repository.CustomerRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UpiEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine; // Inject Thymeleaf template engine

    @Autowired
    private CustomerRepository customerRepo; // Inject UserRepo to fetch sender and receiver names
    
    @Autowired
    private BankAccountRepository bankAccRepo;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    // Send email using Thymeleaf HTML template
    public void sendEmail(String to, String subject, UpiPayment transactionDetails, String templateName, String senderName, String receiverName,Double balance) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Prepare the email context (Thymeleaf variables)
        Context context = new Context();
        context.setVariable("transactionId", transactionDetails.getPaymentId());
        context.setVariable("amount", transactionDetails.getAmount());
        context.setVariable("status", "Success");
        context.setVariable("label", transactionDetails.getLabel());
        context.setVariable("timestampFinal", transactionDetails.getTimestamp());
        context.setVariable("senderName", senderName);
        context.setVariable("receiverName", receiverName);
        context.setVariable("balance", balance);

        // Process Thymeleaf template to generate email content
        String htmlContent = templateEngine.process(templateName, context);

        // Set email details
        helper.setFrom(fromEmailId);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true indicates HTML content

        // Send the email
        System.out.println("balance"+balance);
        mailSender.send(mimeMessage);
    }

    // Send transaction success email to both sender and receiver using template for UPI TRANSACTION
    public void sendTransactionSuccessEmail(String senderEmail, String receiverEmail, UpiPayment transactionDetails) throws MessagingException {
        // Fetch sender and receiver details using UPI IDs
        Customer sender = customerRepo.findByUpiId(transactionDetails.getSenderUpiId()).orElse(null);
        Customer receiver = customerRepo.findByUpiId(transactionDetails.getReceiverUpiId()).orElse(null);
        
        
        BankAccount senderAcc = bankAccRepo.findByBankAccountNumber(sender.getBankAccountNumber());
        BankAccount receiverAcc = bankAccRepo.findByBankAccountNumber(receiver.getBankAccountNumber());
        // Email for Sender using sender template
        sendEmail(senderEmail, "Transaction Successful", transactionDetails, "senderTransactionSuccess", sender.getName(), receiver.getName(),senderAcc.getBankAccountBalance());

        // Email for Receiver using receiver template
        sendEmail(receiverEmail, "You've Received a Payment", transactionDetails, "receiverTransactionSuccess", sender.getName(), receiver.getName(),receiverAcc.getBankAccountBalance());
    }
}
