package com.ezpay.controller;

import com.ezpay.model.BankTransfer;
import com.ezpay.model.UpiPayment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ezpay.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private final BankTransferService BankTransferService;
	private final UpiPaymentService UpiPaymentService;

	@Autowired
	public TransactionController(BankTransferService BankTransferService, UpiPaymentService UpiPaymentService) {
		this.BankTransferService = BankTransferService;
		this.UpiPaymentService = UpiPaymentService;
	}

	@GetMapping("/test")
	public ResponseEntity<String> testingController() {
		return new ResponseEntity<>("Connected", HttpStatus.OK);
	}

	private List<Object> mergeTransactions(List<BankTransfer> bankTransactions, List<UpiPayment> UpiPayments) {
		List<Object> mergedTransactions = new ArrayList<>();
		mergedTransactions.addAll(bankTransactions);
		mergedTransactions.addAll(UpiPayments);

		mergedTransactions.sort((o1, o2) -> {
			LocalDateTime timestamp1 = getTimestamp(o1);
			LocalDateTime timestamp2 = getTimestamp(o2);
			return timestamp2.compareTo(timestamp1); // Descending order
		});

		return mergedTransactions;
	}

	private LocalDateTime getTimestamp(Object transaction) {
		if (transaction instanceof BankTransfer) {
			return ((BankTransfer) transaction).getTimestamp();
		} else if (transaction instanceof UpiPayment) {
			return ((UpiPayment) transaction).getTimestamp();
		} else {
			throw new IllegalArgumentException("Unknown transaction type");
		}
	}

	@GetMapping("/dateRange")
	public ResponseEntity<?> getTransactionsByDateRange(@RequestParam("start") String startDate,
			@RequestParam("end") String endDate, @RequestParam("customerId") Long customerId) {
		try {
			LocalDateTime start = LocalDateTime.parse(startDate);
			LocalDateTime end = LocalDateTime.parse(endDate);

			List<BankTransfer> bankTransactions = BankTransferService.getTransactionsByDateRangeAndCustomerId(start,
					end, customerId);
			List<UpiPayment> UpiPayments = UpiPaymentService.getPaymentsByDateRangeAndCustomerId(start, end,
					customerId);

			List<Object> mergedTransactions = mergeTransactions(bankTransactions, UpiPayments);
			return new ResponseEntity<>(mergedTransactions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/amount/exact")
	public ResponseEntity<?> getTransactionsByExactAmount(@RequestParam("amount") double amount,
			@RequestParam("customerId") Long customerId) { 
		try {
			List<BankTransfer> bankTransactions = BankTransferService.getTransactionsByExactAmountAndCustomerId(amount,
					customerId);
			List<UpiPayment> UpiPayments = UpiPaymentService.getPaymentsByExactAmountAndCustomerId(amount, customerId);

			List<Object> mergedTransactions = mergeTransactions(bankTransactions, UpiPayments);
			return new ResponseEntity<>(mergedTransactions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/amount/min")
	public ResponseEntity<?> getTransactionsByMinAmount(@RequestParam("minAmount") double minAmount,
			@RequestParam("customerId") Long customerId) {
		try {
			List<BankTransfer> bankTransactions = BankTransferService.getTransactionsByMinAmountAndCustomerId(minAmount,
					customerId);
			List<UpiPayment> UpiPayments = UpiPaymentService.getPaymentsByMinAmountAndCustomerId(minAmount, customerId);

			List<Object> mergedTransactions = mergeTransactions(bankTransactions, UpiPayments);
			return new ResponseEntity<>(mergedTransactions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/amount/max")
	public ResponseEntity<?> getTransactionsByMaxAmount(@RequestParam("maxAmount") double maxAmount,
			@RequestParam("customerId") Long customerId) {
		try {
			List<BankTransfer> bankTransactions = BankTransferService.getTransactionsByMaxAmountAndCustomerId(maxAmount,
					customerId);
			List<UpiPayment> UpiPayments = UpiPaymentService.getPaymentsByMaxAmountAndCustomerId(maxAmount, customerId);

			List<Object> mergedTransactions = mergeTransactions(bankTransactions, UpiPayments);
			return new ResponseEntity<>(mergedTransactions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/status")
	public ResponseEntity<?> getTransactionsByStatus(@RequestParam("status") Long status,
			@RequestParam("customerId") Long customerId) {
		try {
			List<BankTransfer> bankTransactions = BankTransferService.getTransactionsByStatusAndCustomerId(status,
					customerId);
			List<UpiPayment> UpiPayments = UpiPaymentService.getPaymentsByStatusAndCustomerId(status, customerId);

			List<Object> mergedTransactions = mergeTransactions(bankTransactions, UpiPayments);
			return new ResponseEntity<>(mergedTransactions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllTransactions(@RequestParam("customerId") Long customerId) {
		try {
			List<BankTransfer> bankTransactions = BankTransferService.getTransactionsByCustomerId(customerId);
			List<UpiPayment> UpiPayments = UpiPaymentService.getPaymentsByCustomerId(customerId);

			List<Object> mergedTransactions = mergeTransactions(bankTransactions, UpiPayments);
			return new ResponseEntity<>(mergedTransactions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/description")
	public ResponseEntity<?> getTransactionsByDescription(@RequestParam("keyword") String labelKeyword,
			@RequestParam("customerId") Long customerId) {
		try {
			List<BankTransfer> bankTransactions = BankTransferService
					.getTransactionsByDescriptionAndCustomerId(labelKeyword, customerId);
			List<UpiPayment> UpiPayments = UpiPaymentService.getTransactionsByDescriptionAndCustomerId(labelKeyword,
					customerId);

			List<Object> mergedTransactions = mergeTransactions(bankTransactions, UpiPayments);
			return new ResponseEntity<>(mergedTransactions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/transactionId/{id}")
	public ResponseEntity<?> getTransactionByTransactionId(@PathVariable("id") String id,
			@RequestParam("customerId") Long customerId) {
		try {
			if (id.startsWith("T")) {
				return new ResponseEntity<>(BankTransferService.getTransactionByTransferIdAndCustomerId(id, customerId),
						HttpStatus.OK);

			} else if (id.startsWith("P")) {
				return new ResponseEntity<>(UpiPaymentService.getPaymentByPaymentIdAndCustomerId(id, customerId),
						HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Invalid transaction ID", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/type/{transactionType}")
	public ResponseEntity<?> getTransactionByTransactionType(@PathVariable("transactionType") String transactionType,
			@RequestParam("customerId") Long customerId) {
		try {
			if (transactionType.equalsIgnoreCase("BANK")) {
				return new ResponseEntity<>(BankTransferService.getTransactionsByCustomerId(customerId), HttpStatus.OK);

			} else if (transactionType.equalsIgnoreCase("UPI")) {
				return new ResponseEntity<>(UpiPaymentService.getPaymentsByCustomerId(customerId), HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Invalid transaction type.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
