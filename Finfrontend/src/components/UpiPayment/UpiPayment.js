// //autofill + show pin eye
import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./UpiPayment.css";
import { FaRegEye,FaRegEyeSlash } from "react-icons/fa";
import Header from "../Header/Header";
import Footer from "../Footer/Footer";

/**
 * UpiPayment Component
 * This component allows users to enter UPI payment details, including sender and receiver UPI IDs,
 * UPI PIN, and the amount to be transferred. It validates the inputs and handles the payment process.
 * Upon successful payment, it navigates to the payment result page and stores transaction details in session storage.
 *
 * @author Meghna Bhat
 * @date 24th September 2024
 */

const UpiPayment = () => {
  const [senderUpiId, setSenderUpiId] = useState("");
  const [receiverUpiId, setReceiverUpiId] = useState("");
  const [upiPin, setUpiPin] = useState("");
  const [amount, setAmount] = useState("");
  const [label, setLabel] = useState("");
  const [loading, setLoading] = useState(false);
  const [showSenderSuggestion, setShowSenderSuggestion] = useState(false);
  const [showReceiverSuggestion, setShowReceiverSuggestion] = useState(false);
  const navigate = useNavigate();
  const [showPin, setShowPin] = useState(false);
  const [wait,setWait] = useState(true);

  // Separate error states for each field
  const [senderUpiIdError, setSenderUpiIdError] = useState("");
  const [receiverUpiIdError, setReceiverUpiIdError] = useState("");
  const [upiPinError, setUpiPinError] = useState("");
  const [amountError, setAmountError] = useState("");

  //fetching upi id
  const [error, setError] = useState("");

  const location = useLocation();

  // Function to fetch UPI ID using customer_id from localStorage
  const fetchUpiIdByCustomerId = async () => {
    const customer_id = localStorage.getItem("customer_id");
    if (!customer_id) {
      setError("Customer ID not found in localStorage");
      return;
    }

    try {
      setLoading(true);
      const response = await fetch(
        `http://localhost:9020/api/customer/${encodeURIComponent(
          customer_id
        )}/upi-id`
      );
      if (!response.ok) {
        throw new Error("Failed to fetch UPI ID");
      }

      const upiId = await response.text();
      setSenderUpiId(upiId);
      setLoading(false);
    } catch (error) {
      setError(error.message);
      setLoading(false);
    }
  };

  // Effect to perform cleanup actions on component unmount
  useEffect(() => {
    // Logging necessary actions here

    const customerId = localStorage.getItem("customer_id");
    if (!customerId) {
      setError("Customer ID not found in localStorage");
      return;
    }

    fetchUpiIdByCustomerId();
    console.log(
      "UpiPayment component mounted." +
        customerId +
        "sender upi ods" +
        senderUpiId
    );

    // Cleanup function to reset or clear any values when the component is unmounted
    return () => {
      console.log("UpiPayment component unmounted. Cleaning up...");
    };
  }, []);

  // Validation functions
  const validateSenderUpiId = (upiId) => {
    const upiPattern = /^[0-9]{10}@ezpay$/;
    if (!upiPattern.test(upiId)) {
      setSenderUpiIdError(
        "Please enter a valid UPI ID (10-digit number followed by @ezpay)"
      );
      return false;
    }
    return true;
  };

  const validateReceiverUpiId = (upiId) => {
    const upiPattern = /^[0-9]{10}@ezpay$/;
    if (!upiPattern.test(upiId)) {
      setReceiverUpiIdError(
        "Please enter a valid UPI ID (10-digit number followed by @ezpay)"
      );
      return false;
    }
    return true;
  };

  const validateUpiPin = (pin) => {
    const pinPattern = /^[0-9]{4}$/;
    if (!pinPattern.test(pin)) {
      setUpiPinError("Please enter a valid 4-digit UPI Pin");
      return false;
    }
    return true;
  };

  const validateAmount = (amount) => {
    const parsedAmount = parseFloat(amount);
    if (isNaN(parsedAmount) || parsedAmount <= 0) {
      setAmountError("Please enter a valid amount greater than zero");
      return false;
    }
    return true;
  };

  // Handle change for sender UPI ID and show suggestion
  const handleSenderUpiIdChange = (e) => {
    const value = e.target.value;
    setSenderUpiId(value);
    setShowSenderSuggestion(value.length === 10);
  };

  // Autofill sender UPI ID on Tab key press
  const handleSenderUpiIdKeyDown = (e) => {
    if (e.key === "Tab" && senderUpiId.length === 10) {
      e.preventDefault();
      setSenderUpiId(senderUpiId + "@ezpay");
      setShowSenderSuggestion(false);
    }
  };

  // Handle change for receiver UPI ID and show suggestion
  const handleReceiverUpiIdChange = (e) => {
    const value = e.target.value;
    setReceiverUpiId(value);
    setShowReceiverSuggestion(value.length === 10);
  };

  // Autofill receiver UPI ID on Tab key press
  const handleReceiverUpiIdKeyDown = (e) => {
    if (e.key === "Tab" && receiverUpiId.length === 10) {
      e.preventDefault();
      setReceiverUpiId(receiverUpiId + "@ezpay");
      setShowReceiverSuggestion(false);
    }
  };

  // Handle changes to other input fields
  const handleUpiPinChange = (e) => {
    setUpiPin(e.target.value);
  };

  const handleAmountChange = (e) => {
    setAmount(e.target.value);
  };

  //show pin
  const toggleShowPin = () => {
    setShowPin(!showPin);
  };

  // handlePayment function to handle the transaction
  const handlePayment = async (e) => {
    e.preventDefault();
  
    // Clear any existing error messages
    setSenderUpiIdError("");
    setReceiverUpiIdError("");
    setUpiPinError("");
    setAmountError("");
  
    // Validate all fields before proceeding
    if (!validateSenderUpiId(senderUpiId)) return;
    if (!validateReceiverUpiId(receiverUpiId)) return;
    if (!validateUpiPin(upiPin)) return;
    if (!validateAmount(amount)) return;
  
    // All validations passed, proceed with transaction
    setLoading(true);
    let paymentStatus = "";
    let reason = "";
    let paymentId = "";
  
    try {
      const response = await fetch(
        "http://localhost:9020/api/processs-payment",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            senderUpiId,
            receiverUpiId,
            amount,
            label,
            upiPin,
          }),
        }
      );
  
      if (response.ok) {
        // Payment success
        paymentStatus = "success";
        const data = await response.json();
        paymentId = data.paymentId;

        navigate("/payment-result", {
          state: {
            receiverUpiId,
            amount,
            paymentStatus,
            reason,
            senderUpiId,
            paymentId,
          },
        });
    
        setLoading(false); // Stop loading spinner after everything is done
  
      } else if (response.status === 401) {
        // Token validation needed
        const customerId = localStorage.getItem("customer_id");
        const data = await response.json();
        paymentId = data.paymentId;
  
        // Fetch token for validation
        const tokenResponse = await fetch("http://localhost:9020/generate-token", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ customerId }),
        });
  
        const token = await tokenResponse.text();
  
        // Open token validation window
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
                const response = await fetch("http://localhost:9020/validate-token", {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                  },
                  body: JSON.stringify({ customerId: '${customerId}', token: tokenValue }),
                });
  
                const isValid = await response.json();
                if (isValid) {
                  localStorage.setItem("tokenValidationMessage", "Token validated successfully! Transaction success");
                } else {
                  const flagResponse = await fetch("http://localhost:9020/flagTransaction?customerId=${customerId}", {
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
  
        const tokenWindowCheckInterval = setInterval(() => {
          if (tokenWindow.closed) {
            clearInterval(tokenWindowCheckInterval);
  
            const validationMessage = localStorage.getItem("tokenValidationMessage");
  
            if (validationMessage === "Token validation failed. Transaction failed and user blocked.") {
              paymentStatus = "failed";
              reason = "Token validation failed. Transaction failed and user blocked.";
              localStorage.removeItem('customer_id');
              console.log("User is logged out");
            } else if (validationMessage) {
              paymentStatus = "success";
            }
  
            localStorage.removeItem("tokenValidationMessage");
  
            // Now navigate to the payment result page after token validation
            navigate("/payment-result", {
              state: {
                receiverUpiId,
                amount,
                paymentStatus,
                reason,
                senderUpiId,
                paymentId,
              },
            });
          }
        }, 1000); // Polling every 1 second to check if the pop-up window has closed
        return; // Ensures the code below doesn't execute during the token validation process
        
      } else {
        // Handle other failure cases
        paymentStatus = "failed";
        const data = await response.json();
        reason = data.reason;
        paymentId = data.paymentId;

        navigate("/payment-result", {
          state: {
            receiverUpiId,
            amount,
            paymentStatus,
            reason,
            senderUpiId,
            paymentId,
          },
        });
    
        setLoading(false); // Stop loading spinner after everything is done
      }
  
    } catch (error) {
      console.error("Error:", error);
      paymentStatus = "failed";
      reason = "An unexpected error occurred";
    } finally {
      // Store transaction details in sessionStorage
      sessionStorage.setItem("transactionStatus", "completed");
      sessionStorage.setItem("paymentId", paymentId);
      sessionStorage.setItem("receiverUpiId", receiverUpiId);
      sessionStorage.setItem("amount", amount);
      sessionStorage.setItem("paymentStatus", paymentStatus);
      sessionStorage.setItem("senderUpiId", senderUpiId);
      sessionStorage.setItem("reason", reason);
  
      // Navigate to the payment result page in all cases
      //if(wait)

    }
  };
  

    return (
      <>
        <Header title="UPI Payment"/>
        
        <div className="upi_contact_section upi_layout_padding">
    <div className="upi_container-fluid">
        <h1 className="upi_what_taital">Proceed with UPI Payment</h1>
        {loading ? (
            <div className="upi_loading-indicator">
                <p id="payment_in_progress">Payment in progress...</p>
                {/* <div className="upi_loading-animation"></div> */}
                <img 
        src={process.env.PUBLIC_URL + "/images/payment_processing.gif"} 
        alt="Payment processing" 
        className="upi_loading-gif" 
    />
            </div>
        ) : (
            <form onSubmit={handlePayment} className="upi_form_card">
                <div className="upi_input-container">
                <div className="upi_id-label">
                <p>Your UPI ID: <span>{senderUpiId}</span></p>
                </div>
                </div>
                <div className="upi_input-container">
                    <input
                        type="text"
                        className="upi_mail_text_1"
                        placeholder="Enter receiver UPI ID"
                        value={receiverUpiId}
                        onChange={handleReceiverUpiIdChange}
                        onKeyDown={handleReceiverUpiIdKeyDown}
                        required
                    />
                    {showReceiverSuggestion && (
                        <span className="upi_suggestion">@ezpay</span>
                    )}
                    {receiverUpiIdError && <p className="upi_error" style={{ color: 'red' }}>{receiverUpiIdError}</p>}
                </div>
                <div className="upi_input-container">
                    <div className="upi_pin-container">
                        <input
                            type={showPin ? 'text' : 'password'}
                            className="upi_mail_text_1 upi_pin_input"
                            placeholder="Enter UPI Pin"
                            value={upiPin}
                            onChange={handleUpiPinChange}
                            required
                        />
                        <button type="button" onClick={toggleShowPin} className="upi_toggle-pin-visibility">
                        {showPin ? <FaRegEyeSlash /> : <FaRegEye />}
                        </button>
                    </div>
                    {upiPinError && <p className="upi_error" style={{ color: 'red' }}>{upiPinError}</p>}
                </div>

                <div>
                    <input
                        type="number"
                        className="upi_mail_text_1"
                        placeholder="Amount"
                        value={amount}
                        onChange={handleAmountChange}
                        required
                    />
                    {amountError && <p className="upi_error" style={{ color: 'red' }}>{amountError}</p>}
                </div>
                <div>
                    <input
                        type="text"
                        className="upi_mail_text_1"
                        placeholder="Add a note (optional)"
                        value={label}
                        onChange={(e) => setLabel(e.target.value)}
                    />
                </div>
                <button type="submit" className="upi_send_bt">Pay</button>
            </form>
        )}
    </div>
</div>

        <Footer/>
      </>
    );
};

export default UpiPayment;