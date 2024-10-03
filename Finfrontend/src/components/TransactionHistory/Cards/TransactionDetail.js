/**
 *
 * Author: Medha
 * Date: 21-Sep-2024
 *
 *
 * TransactionDetail Component Description :
 *
 * This component is used to display detailed information about a specific transaction.
 * It uses the React Router's useLocation hook to retrieve transaction data passed via state.
 * The component handles different transaction statuses by applying conditional styling and text.
 * Functions are provided to handle navigation back, print the transaction details, and a placeholder for share functionality.
 *
 * Modifier: Chandan
 * Date : 23-Sep-2024
 * Chandan's Modifications: Added conditional styling based on transaction status and implemented the useNavigate hook
 * for navigation enhancements. Updated the component to use functional React patterns and hooks for state and side effects.
 * Also added validations for the fetched data before displaying it.
 *
 *
 * */

import React, { useState, useEffect } from "react";
import "../Styles/TransactionDetail.css";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import logosuccess from "../assests/Animation - 1727288186846.gif"
import logofailed from "../assests/cancelled-ezgif.com-gif-maker.gif";
import logopending from "../assests/pending.gif";
import logocancelled from "../assests/grey.gif";
import Header from "../../Header/Header.jsx";
import Footer from "../../Footer/Footer.jsx";

const TransactionDetail = () => {
  const location = useLocation(); // Retrieve the transaction data passed via state
  const navigate = useNavigate(); // useNavigate hook for programmatic navigation
  const { transaction, isDebit } = location.state || {}; // Extract transaction data, or default to an empty object.

  const [loginFailed, setLoginFailed] = useState(false);

  // Check if the customer is logged in before displaying the transaction details
  useEffect(() => {
    if (!localStorage.getItem("customer_id")) {
      //localStorage.setItem('customerId', '1');
      setLoginFailed(true);
      return;
    }
  }, []);

  // Redirect to login error page if the customer is not logged in and transaction details are available
  useEffect(() => {
    if (loginFailed) {
      navigate("/LoginError", {
        state: {
          message: "You are not logged in. Please log in to view transactions.",
        },
      });
    }
  }, [loginFailed, navigate]);

  // Check if the transaction data is valid before proceeding
  if (!transaction || typeof transaction !== "object") {
    return <div>No valid transaction details available.</div>; // Display an error message if data is invalid
  }

  // Function to validate if the transaction amount is a valid number greater than zero
  const isValidAmount = (amount) => typeof amount === "number" && amount > 0;

  // Function to validate if the transaction timestamp is a valid date
  const isValidDate = (timeStamp) => !isNaN(new Date(timeStamp).getTime());

  // Function to validate if a string is non-empty and valid
  const isValidString = (str) =>
    typeof str === "string" && str.trim().length > 0;

  // Get the CSS class based on the transaction status (pending, completed, failed, or cancelled)
  const getStatusClass = (status) => {
    switch (status) {
      case 0:
        return "pending"; // Pending transaction
      case 1:
        return "completed"; // Completed transaction
      case 2:
        return "failed"; // Failed transaction
      default:
        return "cancelled"; // Cancelled transaction
    }
  };

  // Get the display text based on the transaction status
  const getStatusText = (status) => {
    switch (status) {
      case 0:
        return "Pending";
      case 1:
        return "Completed";
      case 2:
        return "Failed";
      default:
        return "Cancelled";
    }
  };

  // Handle the back button click by navigating to the previous page
  const handleBack = () => {
    navigate(-1); // Go back one step in the navigation history
  };

  // Handle the print button click to print the current page
  const handlePrint = () => {
    window.print(); // Invoke the browser's print function
  };

  // Handle the share button click (currently a placeholder)
  const handleShare = () => {
    console.log("Share button clicked"); // Placeholder for future share functionality
  };

  return (
    <>
      <div
        className={`transaction-details-page ${getStatusClass(
          transaction.status
        )}`}
      >
        {/* Apply status-based styling to the transaction details container */}
        <Header />
        <div
          className={`transaction-details-container ${getStatusClass(
            transaction.status
          )}`}
        >
          <p className="transaction-detail-header">DETAILS</p>

          <div className="transaction-detail-grid">
            {/* Render Bank Transaction ID if valid */}
            {isValidString(transaction.transferId) && (
              <div
                className={`transaction-detail-tile transaction-id-bank ${getStatusClass(
                  transaction.status
                )}`}
              >
                <strong>Transaction ID (Bank)</strong>
                <p>{transaction.transferId}</p>
              </div>
            )}

            {/* Render UPI Transaction ID if valid */}
            {isValidString(transaction.paymentId) && (
              <div
                className={`transaction-detail-tile transaction-id-upi${getStatusClass(
                  transaction.status
                )}`}
              >
                <strong>Transaction ID (UPI)</strong>
                <p>{transaction.paymentId}</p>
              </div>
            )}

            {/* Validate and render the transaction amount */}
            {isValidAmount(transaction.amount) ? (
              <div
                className={`transaction-detail-tile amount-tile ${getStatusClass(
                  transaction.status
                )}`}
              >
                <strong>Amount</strong>
                <p className="payment-text">₹{transaction.amount.toFixed(2)}</p>
              </div>
            ) : (
              <div className="invalid-data">Invalid amount</div> // Show error if amount is invalid
            )}

            {/* Display Sender Information */}
            {isDebit && transaction.sender && (
              <div className="transaction-detail-tile">
                <div
                  style={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "space-around",
                  }}
                >
                  <p>
                    <strong>Sent to</strong>
                  </p>
                  <p>{transaction.receiver.name}</p>
                  <p>
                    <strong>Email: </strong>
                    {transaction.receiver.email}
                  </p>
                  <p>
                    <strong>UPI:</strong> {transaction.receiver.upiId}
                  </p>
                </div>
              </div>
            )}

            {/* Display Receiver Information */}
            {!isDebit && transaction.receiver && (
              <div className="transaction-detail-tile">
                <strong>Received from</strong>
                <p>{transaction.sender.name}</p>
                <p>
                  <strong>Email:</strong> {transaction.sender.email}
                </p>
                <p>
                  <strong>UPI:</strong> {transaction.sender.upiId}
                </p>
              </div>
            )}

            {/* Display the transaction status */}
            <div
              className={`transaction-detail-tile status-tile ${getStatusClass(
                transaction.status
              )}`}
            >
              <div
                className={`circular-status-container ${getStatusClass(
                  transaction.status
                )}`}
              >
                <div className="status-text"></div>
              </div>
              <div>
                {/* Conditionally render the logo if the transaction is successful */}
                {transaction.status === 1 && (
                  <img
                    src={logosuccess}
                    alt="Success Logo"
                    className="success-logo"
                  />
                )}
              </div>

              <div>
                {/* Conditionally render the logo if the transaction has failed */}
                {transaction.status === 2 && (
                  <img
                    src={logofailed}
                    alt="Failed Logo"
                    className="failed-logo"
                  />
                )}
              </div>

              <div>
                {/* Conditionally render the logo if the transaction is pending */}
                {transaction.status === 0 && (
                  <img
                    src={logopending}
                    alt="Pending Logo"
                    className="pending-logo"
                  />
                )}
              </div>

              <div>
                {/* Conditionally render the logo if the transaction is cancelled (default case) */}
                {transaction.status !== 0 &&
                  transaction.status !== 1 &&
                  transaction.status !== 2 && (
                    <img
                      src={logocancelled}
                      alt="Cancelled Logo"
                      className="cancelled-logo"
                    />
                  )}
              </div>
            </div>

            {/* Validate and render the transaction date and time */}
            {isValidDate(transaction.timestamp) ? (
              <div
                className={`transaction-detail-tile date-tile ${getStatusClass(
                  transaction.status
                )}`}
              >
                <strong>Date & Time</strong>
                <p>{new Date(transaction.timestamp).toLocaleString()}</p>
              </div>
            ) : (
              <div className="invalid-data">Invalid date</div> // Show error if timestamp is invalid
            )}

            {/* Render the remarks if valid */}
            {isValidString(transaction.remark) && (
              <div
                className={`transaction-detail-tile remarks-tile ${getStatusClass(
                  transaction.status
                )}`}
              >
                <strong>Remarks</strong>
                <p>{transaction.remark}</p>
              </div>
            )}

            {/* Render the label if valid */}
            {isValidString(transaction.label) && (
              <div
                className={`transaction-detail-tile label-tile ${getStatusClass(
                  transaction.status
                )}`}
              >
                <strong>Label</strong>
                <p>{transaction.label}</p>
              </div>
            )}
          </div>
        </div>
        {/* Action buttons for back and print functionality */}
        <div className="transaction-action-buttons">
          <button onClick={handleBack}>Back</button>
          <button onClick={handlePrint}>Print</button>
        </div>
        <Footer />
      </div>
    </>
  );
};

export default TransactionDetail;
