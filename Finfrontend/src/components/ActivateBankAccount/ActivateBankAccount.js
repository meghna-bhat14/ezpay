/* Author: Meghna
 Date: 22nd September 2024
 Description: This component provides a form for users to activate their bank account by setting up a UPI Pin.
         It validates the UPI Pin and confirms it before sending the activation request to the backend API. */

         import React, { useEffect, useState } from 'react';
         // import './UpiPayment.css'; // Importing CSS for styling
         import './ActivateBankAccount.css'
         import { FaRegEye,FaRegEyeSlash } from "react-icons/fa";
         import { BsJustify } from 'react-icons/bs';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
         
         
         
         const ActivateBankAccount = () => {
             // State variables to store user input and messages
             const [upiId, setUpiId] = useState('');
             const [upiPin, setUpiPin] = useState('');
             const [confirmUpiPin, setConfirmUpiPin] = useState('');
             const [message, setMessage] = useState(''); // State to store messages
             const [showSenderSuggestion, setShowSenderSuggestion] = useState(false);
             const [senderUpiIdError, setSenderUpiIdError] = useState('');
             const [upiPinError, setUpiPinError] = useState('');
             const [showPin, setShowPin] = useState(false);
             const [isSuccess, setIsSuccess] = useState(false); // Track success status
         
         
             //fetching upi id
             const [error, setError] = useState('');
             const [loading, setLoading] = useState(false);
         
             // Function to fetch UPI ID using customer_id from localStorage
             const fetchUpiIdByCustomerId = async () => {
                 const customer_id = localStorage.getItem('customer_id');
                 if (!customer_id) {
                     setError('Customer ID not found in localStorage');
                     return;
                 }
         
                 try {
                     setLoading(true);
                     const response = await fetch(`http://localhost:9020/api/customer/${encodeURIComponent(customer_id)}/upi-id`);
                     if (!response.ok) {
                         throw new Error('Failed to fetch UPI ID');
                     }
         
                     const upiId = await response.text();
                     setUpiId(upiId);
                     setLoading(false);
                 } catch (error) {
                     setError(error.message);
                     setLoading(false);
                 }
             };
         
         
         
             // Effect to perform cleanup actions on component unmount
             useEffect(() => {
                 // Logging necessary actions here
                
                 const customerId = localStorage.getItem('customer_id');
                 if (!customerId) {
                     setError('Customer ID not found in localStorage');
                     return;
                     
                 }
         
                 fetchUpiIdByCustomerId();
                 console.log("component mounted." +customerId+ "upi id" +upiId);
         
                 // Cleanup function to reset or clear any values when the component is unmounted
                 return () => {
                     console.log("BankActivation component unmounted. Cleaning up...");
         
         
                 };
             }, []);
         
             // Validation functions remain unchanged
             const validateSenderUpiId = (upiId) => {
                 const upiPattern = /^[0-9]{10}@ezpay$/;
                 if (!upiPattern.test(upiId)) {
                     setSenderUpiIdError('Please enter a valid UPI ID (10-digit number followed by @ezpay)');
                     return false;
                 }
                 return true;
             };
         
         
                //show pin
                const toggleShowPin = () => {
                 setShowPin(!showPin);
             };
         
             //validating upi pin
             const validateUpiPin = (upiPin) => {
                 const pinPattern = /^[0-9]{4}$/;
                 if (!pinPattern.test(upiPin)) {
                     setUpiPinError('Please enter a valid 4-digit UPI Pin');
                     return false;
                 }
                 return true;
             };
         
         
               // Handle change for sender UPI ID and show suggestion
               const handleSenderUpiIdChange = (e) => {
                 const value = e.target.value;
                 setUpiId(value);
                 setShowSenderSuggestion(value.length === 10);
             };    
         
             // Autofill sender UPI ID on Tab key press
             const handleSenderUpiIdKeyDown = (e) => {
                 if (e.key === 'Tab' && upiId.length === 10) {
                     e.preventDefault();
                     setUpiId(upiId + '@ezpay');
                     setShowSenderSuggestion(false);
                 }
             };
             /**
              * handleActivation
              * @description Handles the bank account activation process by sending user inputs to the backend API.
              * It checks if the UPI Pins match, sends a POST request to the API, and updates the message state based on the response.
              * @param {Event} e - The event triggered by form submission.
              */
             const handleActivation = async (e) => {
                 e.preventDefault();
         
                 setUpiPinError('');
                 setSenderUpiIdError('');
         
         
                 // Validate all fields before proceeding
                 if (!validateSenderUpiId(upiId)) return;
                 if (!validateUpiPin(upiPin)) return;
         
                 
                 // Check if UPI Pins match
                 if (upiPin !== confirmUpiPin) {
                     setMessage("UPI Pins do not match.");
                     setIsSuccess(false); // Set success status to false for red message
                     return;
                 }
         
            
         
                 try {
                     // Sending activation request to the backend API
                     const response = await fetch('http://localhost:9020/api/activate-bank-account', {
                         method: 'POST',
                         headers: {
                             'Content-Type': 'application/json',
                         },
                         body: JSON.stringify({ upiId, upiPin }), // Send UPI ID and Pin in request body
                     });
         
         
                     // Handling response based on API result
                     if (response.ok) {
                         // setMessage("Transaction pin set successfully!"); // Success message
                         const data = await response.text();
                         setIsSuccess(true); // Set success status to true for green message
                         setMessage(data);
                     } else {
                         const result = await response.text();
                         setMessage(`Failed to set the transaction pin: ${result}`); // Error message from API
                         setIsSuccess(false); // Set success status to false for red message
                     }
                 } catch (error) {
                     console.error('Error:', error); // Log error for debugging
                     setMessage("An error occurred while activating the bank account."); // Error message for user
                     setIsSuccess(false); // Set success status to false for red message
                 }
             };
         
             return (
             <>
             <Header title="Activate Your Account"/>
             <div className="activate_bank_contact_section">
             <h1 className="activate_bank_what_taital">Set your PIN</h1>
             
             <div className="activate_bank_container-fluid">
                 {/* Form for activating bank account */}
                 <form onSubmit={handleActivation}>
                     <div className="activate_bank_input-container">
                         <p className="activate_bank_upi_id-label">Your UPI ID: <span>{upiId}</span></p>
                     </div>
                     <div className="activate_bank_input-container activate_bank_pin-container">
                         <input
                             type={showPin ? 'text' : 'password'}
                             className="activate_bank_mail_text_1"
                             placeholder="Set your UPI Pin"
                             value={upiPin}
                             onChange={(e) => setUpiPin(e.target.value)}
                             required
                         />
                         <button type="button" onClick={toggleShowPin} className="activate_bank_toggle-pin-visibility">
                             {showPin ? <FaRegEyeSlash /> : <FaRegEye />}
                         </button>
                         {upiPinError && <p className="upi_error" style={{ color: 'red' }}>{upiPinError}</p>}
                     </div>
         
                     <div className="activate_bank_input-container">
                         <input
                             type="password"
                             className="activate_bank_mail_text_1"
                             placeholder="Confirm your UPI Pin"
                             value={confirmUpiPin}
                             onChange={(e) => setConfirmUpiPin(e.target.value)}
                             required
                         />
                     </div>
         
                     <button type="submit" className="activate_bank_send_bt">Set</button>
                 </form>
                 
            
             </div>
         
              {/* Display the message on the page, outside the card */}
              {message && (
                 <div className = "activate_bank_message_class">
                     <p className={`activate_bank_message ${isSuccess ? 'success' : 'error'}`}>{message}</p>
                 </div>
             )}
         
            
         </div>
         
             <Footer/>
             
             
             </>
             
             );
         };
         
         export default ActivateBankAccount;