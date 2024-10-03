package com.ezpay.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezpay.model.BankPinDetails;
import com.ezpay.model.Customer;

import java.util.Optional;

/**
 * Repository interface for handling {@link BankPinDetail} entity persistence.
 * 
 * This interface provides CRUD operations and additional query methods
 * for {@link BankPinDetail} using Spring Data JPA.
 * 
 * The repository is responsible for interacting with the database
 * and retrieving {@link BankPinDetail} objects based on provided parameters.
 * 
 * @author Prerak Semwal
 * @date    15-09-2024
 * @version 1.0
 */
@Repository
public interface BankPinDetailRepository extends JpaRepository<BankPinDetails,String> {

	/**
	 * Retrieves a {@link BankPinDetail} entity by its associated bank account number.
	 *
	 * @param bankAccountNumber The bank account number to search for.
	 * @return An {@link Optional} containing the {@link BankPinDetail} if found, 
	 *         or {@link Optional#empty()} if not found.
	 */
	Optional<BankPinDetails> findByBankAccountNumber(String bankAccountNumber);
	
	
	Optional<BankPinDetails> findByCustomer(Customer customer);

}
