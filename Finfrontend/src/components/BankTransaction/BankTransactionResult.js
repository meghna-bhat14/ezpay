/**
 * @file BankTransactionResult.js
 * @description This component displays the result of a bank transaction, showing a success or failure message
 * based on the transaction status. If the transaction is successful, it displays the transaction details.
 * @author [Prerak Semwal]
 * @date [22-09-2024]
 */

import React from "react";
import { FaCheckCircle, FaTimesCircle } from "react-icons/fa"; // Importing icons for success and failure states.
import { useLocation, useNavigate } from "react-router-dom"; // React Router hooks for navigation and location handling.
import "./BankTransactionResult.css"; // Importing styles for this component.
import Header from "../Header/Header";
import Footer from "../Footer/Footer";

const BankTransactionResult = () => {
  // Getting the state object from the location to access transaction details.
  const location = useLocation();
  const navigate = useNavigate(); // Hook for programmatic navigation.
  const { state } = location || {}; // Destructuring state from location, defaulting to an empty object.
  const { isSuccess, transactionDetails, reason } = state || {}; // Extracting necessary properties from the state object.

  return (
    <>
    <Header title="Payment Status"/>
    <div className="btr-transaction-result-container">
      <div className="btr-transaction-result">
        {isSuccess ? (
          <>
            {/* Displaying success message and transaction details if the transaction was successful. */}
            <FaCheckCircle className="btr-success-icon" />
            <h3>Transaction Successful</h3>
            <p className="btr-p">
              <strong>Sender Account Number:</strong>{" "}
              {transactionDetails.senderAccountNumber}
            </p>
            <p className="btr-p">
              <strong>Receiver Account Number:</strong>{" "}
              {transactionDetails.receiverAccountNumber}
            </p>
            <p className="btr-p">
              <strong>Amount:</strong> ₹{transactionDetails.amount}
            </p>
            <p className="btr-p">
              <strong>Label:</strong> {transactionDetails.label || "N/A"}
            </p>
          </>
        ) : (
          <>
            {/* Displaying failure message and reason for failure if the transaction failed. */}
            <FaTimesCircle className="btr-failure-icon" />
            <h3>Transaction Failed</h3>
            <p className="btr-p">{reason}</p>
          </>
        )}
        {/* Button for navigating back to the home page to either make another transaction or retry. */}
        <button className="btr-send_bt" onClick={() => navigate("/")}>
          {isSuccess ? "Make Another Transaction" : "Try Again"}
        </button>
      </div>
    </div>
    <Footer/>
    </>
  );
};

export default BankTransactionResult;