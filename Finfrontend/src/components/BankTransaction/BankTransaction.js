/**
 * @file BankTransaction.js
 * @description This component handles the bank transaction process. It collects sender and receiver account details,
 * validates the input, and processes the transaction by communicating with a backend API. It displays loading
 * indicators during the payment process and shows error messages for invalid inputs.
 * @author [Prerak Semwal]
 * @date [22-09-2024]
 */

import { useEffect, useState } from "react"; // Importing React's useState hook for managing state.
import { useLocation, useNavigate } from "react-router-dom"; // Importing useNavigate for navigation after transaction processing.
import "./BankTransaction.css"; // Importing styles for this component.
import Header from "../Header/Header";
import Footer from "../Footer/Footer";

const BankTransaction = () => {
  // State variables for capturing input values and errors.
  const [senderAccountNumber, setSenderAccountNumber] = useState("");
  const [senderIfscCode, setSenderIfscCode] = useState("");
  const [receiverAccountNumber, setReceiverAccountNumber] = useState("");
  const [receiverIfscCode, setReceiverIfscCode] = useState("");
  const [pin, setPin] = useState("");
  const [amount, setAmount] = useState("");
  const [label, setLabel] = useState("");
  const [paymentInProgress, setPaymentInProgress] = useState(false);
  const navigate = useNavigate();

  // State variables for capturing validation errors.
  const [senderAccountError, setSenderAccountError] = useState("");
  const [senderIfscError, setSenderIfscError] = useState("");
  const [receiverAccountError, setReceiverAccountError] = useState("");
  const [receiverIfscError, setReceiverIfscError] = useState("");
  const [pinError, setPinError] = useState("");
  const [amountError, setAmountError] = useState("");

  // Regex patterns for validating input fields.
  const ACCOUNT_NUMBER_PATTERN = /^\d{10,18}$/;
  const IFSC_CODE_PATTERN = /^[A-Z]{4}0[A-Z0-9]{6}$/;
  const PIN_PATTERN = /^\d{4}$/;

  //==================================================================
  const [error, setError] = useState("");
  const location = useLocation();

  const fetchSenderAccountNumberByCustomerId = async () => {
    const customer_id = localStorage.getItem("customer_id");
    if (!customer_id) {
      setError("Customer ID not found in localStorage");
      return;
    }

    try {
      setPaymentInProgress(true);
      const response = await fetch(
        `http://localhost:9020/API/customer/${encodeURIComponent(
          customer_id
        )}/bank-account-number`
      );
      if (!response.ok) {
        throw new Error("Failed to fetch bank account number");
      }

      const acc_no = await response.text();
      setSenderAccountNumber(acc_no);
      setPaymentInProgress(false);
    } catch (error) {
      setError(error.message);
      setPaymentInProgress(false);
    }
  };

  const fetchSenderIfscCoderByCustomerId = async () => {
    const customer_id = localStorage.getItem("customer_id");
    if (!customer_id) {
      setError("Customer ID not found in localStorage");
      return;
    }

    try {
      setPaymentInProgress(true);
      const response = await fetch(
        `http://localhost:9020/API/customer/${encodeURIComponent(
          customer_id
        )}/ifsc-code`
      );
      if (!response.ok) {
        throw new Error("Failed to fetch bank account number");
      }

      const ifsc_code = await response.text();
      setSenderIfscCode(ifsc_code);
      setPaymentInProgress(false);
    } catch (error) {
      setError(error.message);
      setPaymentInProgress(false);
    }
  };

  useEffect(() => {
    const customerId = localStorage.getItem("customer_id");
    if (!customerId) {
      setError("Customer ID not found in localStorage");
      return;
    }

    fetchSenderAccountNumberByCustomerId();
    fetchSenderIfscCoderByCustomerId();
  }, []);

  //==================================================================

  // Validation functions for input fields.
  const validateSenderAccountNumber = () => {
    if (!ACCOUNT_NUMBER_PATTERN.test(senderAccountNumber)) {
      setSenderAccountError("Account number should between 10-18 digit number.");
      return;
    }
    setSenderAccountError("");
  };

  const validateReceiverAccountNumber = () => {
    if (!ACCOUNT_NUMBER_PATTERN.test(receiverAccountNumber)) {
      setReceiverAccountError("Account number should between 10-18 digit number.");
      return;
    }
    setReceiverAccountError("");
  };

  const validateSenderIfscCode = () => {
    if (!IFSC_CODE_PATTERN.test(senderIfscCode)) {
      setSenderIfscError(
        "Invalid IFSC code. It should follow the pattern XXXX0XXXXXX."
      );
      return;
    }
    setSenderIfscError("");
  };

  const validateReceiverIfscCode = () => {
    if (!IFSC_CODE_PATTERN.test(receiverIfscCode)) {
      setReceiverIfscError(
        "Invalid IFSC code. It should follow the pattern XXXX0XXXXXX."
      );
      return;
    }
    setReceiverIfscError("");
  };

  const validatePin = () => {
    if (!PIN_PATTERN.test(pin)) {
      setPinError("Invalid PIN. It should be a 4-digit number.");
      return;
    }
    setPinError("");
  };

  const validateAmount = () => {
    const parsedAmount = parseInt(amount);
    if (
      isNaN(parsedAmount) ||
      parsedAmount <= 0 ||
      !Number.isInteger(parsedAmount)
    ) {
      setAmountError("Please enter a valid non-zero number");
      return;
    }
    setAmountError("");
  };

  // Function to handle the payment process.
  const handlePayment = async () => {
    setPaymentInProgress(true);
    try {
      const response = await fetch(
        "http://localhost:9020/API/process-transfer",
        {
          method: "POST",
          headers: {
            "content-type": "application/json",
          },
          body: JSON.stringify({
            senderAccountNumber,
            senderIfscCode,
            receiverAccountNumber,
            receiverIfscCode,
            amount,
            label,
            pin,
          }),
        }
      );

      let paymentStatus = "";
      let reasonError = "";
      if (response.ok) {
        paymentStatus = "successful";
        // if (location.pathname === "bank-transaction") {
        navigate("/bank-transaction-result", {
          state: {
            isSuccess: response.ok,
            transactionDetails: {
              senderAccountNumber,
              receiverAccountNumber,
              amount,
              label,
            },
            reasonError,
          },
        });
        // }
      } 
      else if (response.status === 401) {
        // Token validation flow
        const customerId = localStorage.getItem("customer_id");

        // Step 1: Generate token
        const tokenResponse = await fetch("http://localhost:9020/generate-token", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ customerId }),
        });

        const token = await tokenResponse.text();

        // Step 2: Open a new popup window for token validation
        const tokenWindow = window.open("", "Token Validation", "width=400,height=300");

        tokenWindow.document.write(`
          <html>
          <body>
            <h2>Token Validation</h2>
            <p>${token}</p>
            <input type="text" id="token" placeholder="Enter Token" />
            <button id="validateButton">Validate Token</button>
            <script>
              document.getElementById("validateButton").onclick = async function() {
                const tokenValue = document.getElementById("token").value;
                const response = await fetch("http://localhost:9020/validate-tokenB", {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                  },
                  body: JSON.stringify({ customerId: '${customerId}', token: tokenValue, upi: 0 }),
                });

                const isValid = await response.json();
                if (isValid) {
                  localStorage.setItem("tokenValidationMessage", "Token validated successfully! Transaction success");
                } else {
                  const flagResponse = await fetch("http://localhost:9020/flagBankTransaction?customerId=${customerId}", {
                    method: "POST",
                    headers: {
                      "Content-Type": "application/json",
                    }
                  });

                  const isBlocked = await flagResponse.json();
                  const validationMessage = isBlocked 
                    ? "Token validation failed. Transaction failed and user blocked."
                    : "Token validation failed. Transaction failed.";
                  
                  localStorage.setItem("tokenValidationMessage", validationMessage);
                }
                window.close(); // Close the token validation window
              };
            </script>
          </body>
          </html>
        `);

        // Step 3: Check for popup window closure and navigate based on validation
        const tokenWindowCheckInterval = setInterval(() => {
          if (tokenWindow.closed) {
            clearInterval(tokenWindowCheckInterval);
            const validationMessage = localStorage.getItem("tokenValidationMessage");

            let transferStatus = "failed";
            let reason = validationMessage;

            if (validationMessage === "Token validated successfully! Transaction success") {
              transferStatus = "success";
              reason = "";
            }else{
              localStorage.removeItem('customer_id');
            }

            // Navigate to the transfer result page
            navigate("/bank-transaction-result", {
              state: {
                isSuccess: transferStatus === "success",
                transactionDetails: {
                  senderAccountNumber,
                  receiverAccountNumber,
                  amount,
                  label
                },
                reason: reason,
              },
            });

            localStorage.removeItem("tokenValidationMessage");
          }
        }, 1000); // Poll every 1 second to check if the window has closed

        return; // Return early to avoid continuing the process while token validation is pending
      }else {
        paymentStatus = "failed";
        reasonError = await response.text();
        navigate("/bank-transaction-result", {
          state: {
            isSuccess: false,
            reason: reasonError,
          },
        });
      }
    } catch (error) {
      alert(error); // Alerting the user in case of an error during the fetch.
    }
    setPaymentInProgress(false); // Resetting the payment progress state.
  };

  return (
    <>
    <Header title="Bank Payment" />
    
    <div id="bt-contact_section"  className="layout_padding">
      <div id="bt-container-fluid">
        <h1 id="bt-what_taital">Bank Transaction</h1>
        {paymentInProgress ? (
          <div className="bt-loading-indicator">
            <p>Processing Payment</p>
            <div className="bt-spinner"></div>
          </div>
        ) : (
          <form id="bt-form-card" onSubmit={handlePayment}>
            <div>
              <input
                type="number"
                className="bt-mail_text_1"
                placeholder="Enter your account number"
                value={senderAccountNumber}
                onChange={(e) => setSenderAccountNumber(e.target.value)}
                onBlur={validateSenderAccountNumber}
                required
              />
              <p style={{ color: "red" }}>{senderAccountError}</p>
            </div>

            <div>
              <input
                type="text"
                className="bt-mail_text_1"
                placeholder="Enter your IFSC code"
                value={senderIfscCode}
                onChange={(e) => setSenderIfscCode(e.target.value)}
                onBlur={validateSenderIfscCode}
                required
              />
              <p style={{ color: "red" }}>{senderIfscError}</p>
            </div>

            <div>
              <input
                type="number"
                className="bt-mail_text_1"
                placeholder="Enter receiver's account number"
                value={receiverAccountNumber}
                onChange={(e) => setReceiverAccountNumber(e.target.value)}
                onBlur={validateReceiverAccountNumber}
                required
              />
              <p style={{ color: "red" }}>{receiverAccountError}</p>
            </div>

            <div>
              <input
                type="text"
                className="bt-mail_text_1"
                placeholder="Enter receiver's IFSC code"
                value={receiverIfscCode}
                onChange={(e) => setReceiverIfscCode(e.target.value)}
                onBlur={validateReceiverIfscCode}
                required
              />
              <p style={{ color: "red" }}>{receiverIfscError}</p>
            </div>

            <div>
              <input
                type="password"
                className="bt-mail_text_1"
                placeholder="Enter UPI Pin"
                value={pin}
                onChange={(e) => setPin(e.target.value)}
                onBlur={validatePin}
                required
              />
              <p style={{ color: "red" }}>{pinError}</p>
            </div>

            <div>
              <input
                type="number"
                className="bt-mail_text_1"
                placeholder="Amount"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                onBlur={validateAmount}
                required
              />
              <p style={{ color: "red" }}>{amountError}</p>
            </div>

            <div>
              <input
                type="text"
                className="bt-mail_text_1"
                placeholder="Add a note (optional)"
                value={label}
                onChange={(e) => setLabel(e.target.value)}
              />
            </div>

            <button type="submit" id="bt-send_bt">
              Pay
            </button>
          </form>
        )}
        ;
      </div>
    </div>
    <Footer/>
    </>
  );
};

export default BankTransaction;