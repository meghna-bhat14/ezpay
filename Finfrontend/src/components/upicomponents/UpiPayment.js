// // import React, { useState } from 'react';
// // import { useNavigate } from 'react-router-dom';

// // const UpiPayment = () => {
// //     const [senderUpiId, setSenderUpiId] = useState('');
// //     const [receiverUpiId, setReceiverUpiId] = useState('');
// //     const [upiPin, setUpiPin] = useState('');
// //     const [amount, setAmount] = useState('');
// //     const [label, setLabel] = useState('');
// //     const navigate = useNavigate();

// //     const handlePayment = async (e) => {
// //         e.preventDefault();

// //         try {
// //             const response = await fetch('http://localhost:8073/api/process-payment', {
// //                 method: 'POST',
// //                 headers: {
// //                     'Content-Type': 'application/json',
// //                 },
// //                 body: JSON.stringify({ senderUpiId, receiverUpiId, amount, label, upiPin }),
// //             });

// //             if (response.ok) {
// //                 alert("Payment processed successfully!");
// //             } else {
// //                 alert("Payment failed.");
// //                 const result = await response.text();
// //                 alert(result);

// //                  // Check if the response indicates that the account is not activated
// //                  if (result.includes("Bank account not activated")) {
// //                   alert("Please activate your account.");
// //                   // Button to navigate to ActivateBankAccount
// //                   const activate = window.confirm("Would you like to activate your account?");
// //                   if (activate) {
// //                       navigate('/activate'); // Navigate to ActivateBankAccount
// //                   }
// //               } else {
// //                   alert("Payment failed.");
// //               }

               
// //             }
// //         } catch (error) {
// //             console.error('Error:', error);
// //         }
// //     };

// //     return (
// //         <form onSubmit={handlePayment}>
// //             <h2>Proceed with UPI Payment</h2>
// //             <input
// //                 type="text"
// //                 placeholder="Enter your UPI ID"
// //                 value={senderUpiId}
// //                 onChange={(e) => setSenderUpiId(e.target.value)}
// //                 required
// //             />
// //             <input
// //                 type="text"
// //                 placeholder="Enter receiver UPI ID"
// //                 value={receiverUpiId}
// //                 onChange={(e) => setReceiverUpiId(e.target.value)}
// //                 required
// //             />
// //             <input
// //                 type="password"
// //                 placeholder="Enter UPI Pin"
// //                 value={upiPin}
// //                 onChange={(e) => setUpiPin(e.target.value)}
// //                 required
// //             />
// //             <input
// //                 type="number"
// //                 placeholder="Amount"
// //                 value={amount}
// //                 onChange={(e) => setAmount(e.target.value)}
// //                 required
// //             />
// //             <input
// //                 type="text"
// //                 placeholder="Add a note (optional)"
// //                 value={label}
// //                 onChange={(e) => setLabel(e.target.value)}
// //             />
// //             <button type="submit">Pay</button>
// //         </form>
// //     );
// // };

// // export default UpiPayment;


// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import './UpiPayment.css'; 

// const UpiPayment = () => {
//     const [senderUpiId, setSenderUpiId] = useState('');
//     const [receiverUpiId, setReceiverUpiId] = useState('');
//     const [upiPin, setUpiPin] = useState('');
//     const [amount, setAmount] = useState('');
//     const [label, setLabel] = useState('');
//     const navigate = useNavigate();
//     const [loading, setLoading] = useState(false); // State for loading

//     const handlePayment = async (e) => {
//         e.preventDefault();
//         setLoading(true); // Set loading to true
//         try {
//                       const response = await fetch('http://localhost:8073/api/process-payment', {
//                           method: 'POST',
//                           headers: {
//                               'Content-Type': 'application/json',
//                           },
//                           body: JSON.stringify({ senderUpiId, receiverUpiId, amount, label, upiPin }),
//                       });
          
//                       if (response.ok) {
//                           alert("Payment processed successfully!");
//                       } else {
//                           alert("Payment failed.");
//                           const result = await response.text();
//                           alert(result);
          
//                            // Check if the response indicates that the account is not activated
//                            if (result.includes("Bank account not activated")) {
//                             alert("Please activate your account.");
//                             // Button to navigate to ActivateBankAccount
//                             const activate = window.confirm("Would you like to activate your account?");
//                             if (activate) {
//                                 navigate('/activate'); // Navigate to ActivateBankAccount
//                             }
//                         } else {
//                             alert("Payment failed.");
//                         }
          
                         
//                       }
//                   } catch (error) {
//                       console.error('Error:', error);
//                   }finally {
//                     setLoading(false); // Set loading to false after processing
//                 }
          
//     };

//     // return (
//     //     <div className="contact_section layout_padding">
//     //         <div className="container-fluid">
//     //             <h1 className="what_taital">Proceed with UPI Payment</h1>
//     //             <form onSubmit={handlePayment}>
//     //                 <input
//     //                     type="text"
//     //                     className="mail_text_1"
//     //                     placeholder="Enter your UPI ID"
//     //                     value={senderUpiId}
//     //                     onChange={(e) => setSenderUpiId(e.target.value)}
//     //                     required
//     //                 />
//     //                 <input
//     //                     type="text"
//     //                     className="mail_text_1"
//     //                     placeholder="Enter receiver UPI ID"
//     //                     value={receiverUpiId}
//     //                     onChange={(e) => setReceiverUpiId(e.target.value)}
//     //                     required
//     //                 />
//     //                 <input
//     //                     type="password"
//     //                     className="mail_text_1"
//     //                     placeholder="Enter UPI Pin"
//     //                     value={upiPin}
//     //                     onChange={(e) => setUpiPin(e.target.value)}
//     //                     required
//     //                 />
//     //                 <input
//     //                     type="number"
//     //                     className="mail_text_1"
//     //                     placeholder="Amount"
//     //                     value={amount}
//     //                     onChange={(e) => setAmount(e.target.value)}
//     //                     required
//     //                 />
//     //                 <input
//     //                     type="text"
//     //                     className="mail_text_1"
//     //                     placeholder="Add a note (optional)"
//     //                     value={label}
//     //                     onChange={(e) => setLabel(e.target.value)}
//     //                 />
//     //                 <button type="submit" className="send_bt">Pay</button>
//     //             </form>
//     //         </div>
//     //     </div>
//     // );

//   //   //with loading button above screen
//     return (
//       <div className="contact_section layout_padding">
//           <div className="container-fluid">
//               <h1 className="what_taital">Proceed with UPI Payment</h1>
//               {loading ? (
//                   <div className="loading-indicator">
//                       <p>Payment in progress...</p>
//                       <div className="spinner"></div> {/* Spinner style defined in CSS */}
//                   </div>
//               ) : (
//                   <form onSubmit={handlePayment}>
//                       <input
//                           type="text"
//                           className="mail_text_1"
//                           placeholder="Enter your UPI ID"
//                           value={senderUpiId}
//                           onChange={(e) => setSenderUpiId(e.target.value)}
//                           required
//                       />
//                       <input
//                           type="text"
//                           className="mail_text_1"
//                           placeholder="Enter receiver UPI ID"
//                           value={receiverUpiId}
//                           onChange={(e) => setReceiverUpiId(e.target.value)}
//                           required
//                       />
//                       <input
//                           type="password"
//                           className="mail_text_1"
//                           placeholder="Enter UPI Pin"
//                           value={upiPin}
//                           onChange={(e) => setUpiPin(e.target.value)}
//                           required
//                       />
//                       <input
//                           type="number"
//                           className="mail_text_1"
//                           placeholder="Amount"
//                           value={amount}
//                           onChange={(e) => setAmount(e.target.value)}
//                           required
//                       />
//                       <input
//                           type="text"
//                           className="mail_text_1"
//                           placeholder="Add a note (optional)"
//                           value={label}
//                           onChange={(e) => setLabel(e.target.value)}
//                       />
//                       <button type="submit" className="send_bt">Pay</button>
//                   </form>
//               )}
//           </div>
//       </div>
//   );
  
// };

// export default UpiPayment;
// to view the above ConvolverNode, remove the below Code , select all and uncomment once using alt + /




// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import './UpiPayment.css'; 

// const UpiPayment = () => {
//     const [senderUpiId, setSenderUpiId] = useState('');
//     const [receiverUpiId, setReceiverUpiId] = useState('');
//     const [upiPin, setUpiPin] = useState('');
//     const [amount, setAmount] = useState('');
//     const [label, setLabel] = useState('');
//     const [loading, setLoading] = useState(false); // State for loading
//     const navigate = useNavigate();
//     const [errorMessage, setErrorMessage] = useState('');


//     const validateUpiId = (upiId) => {
//         const upiPattern = /^[0-9]{10}@ezpay$/;
//         if (!upiPattern.test(upiId)) {
//             setErrorMessage('Please enter a valid UPI ID (10-digit number followed by @ezpay)');
//         } else {
//             setErrorMessage('');
//         }
//     };

//     const handleUpiIdChange = (e) => {
//         const newUpiId = e.target.value;
//         setSenderUpiId(newUpiId);
//         validateUpiId(newUpiId);
//     };

//     const handlePayment = async (e) => {
//         e.preventDefault();
//         setLoading(true); // Set loading to true
//         try {
//             const response = await fetch('http://localhost:8073/api/process-payment', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify({ senderUpiId, receiverUpiId, amount, label, upiPin }),
//             });


//             let paymentStatus = '';
//             let reason = '';

//             if (response.ok) {
//                 paymentStatus = 'success';
//             } else {
//                 paymentStatus = 'failed';
//                 reason = await response.text();
//             }

//             navigate('/payment-result', { 
//                 state: {
//                     receiverUpiId,
//                     amount,
//                     paymentStatus,
//                     reason,
//                     senderUpiId
//                 }
//             });

//         } catch (error) {
//             console.error('Error:', error);
//         } finally {
//             setLoading(false); // Set loading to false after processing
//         }
//     };

//     return (
//         <div className="contact_section layout_padding">
//             <div className="container-fluid">
//                 <h1 className="what_taital">Proceed with UPI Payment</h1>
//                 {loading ? (
//                     <div className="loading-indicator">
//                         <p>Payment in progress...</p>
//                         <div className="spinner"></div> {/* Spinner style defined in CSS */}
//                     </div>
//                 ) : (
//                     <form onSubmit={handlePayment}>
//                         <div>
//                         <input
//                             type="text"
//                             className="mail_text_1"
//                             placeholder="Enter your UPI ID"
//                             value={senderUpiId}
//                             onChange={handleUpiIdChange}
//                             required
//                         />
//                         {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
//                         </div>
//                         <input
//                             type="text"
//                             className="mail_text_1"
//                             placeholder="Enter receiver UPI ID"
//                             value={receiverUpiId}
//                             onChange={(e) => setReceiverUpiId(e.target.value)}
//                             required
//                         />
//                         <input
//                             type="password"
//                             className="mail_text_1"
//                             placeholder="Enter UPI Pin"
//                             value={upiPin}
//                             onChange={(e) => setUpiPin(e.target.value)}
//                             required
//                         />
//                         <input
//                             type="number"
//                             className="mail_text_1"
//                             placeholder="Amount"
//                             value={amount}
//                             onChange={(e) => setAmount(e.target.value)}
//                             required
//                         />
//                         <input
//                             type="text"
//                             className="mail_text_1"
//                             placeholder="Add a note (optional)"
//                             value={label}
//                             onChange={(e) => setLabel(e.target.value)}
//                         />
//                         <button type="submit" className="send_bt">Pay</button>
//                     </form>
//                 )}
//             </div>
//         </div>
//     );
// };

// export default UpiPayment;



import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './UpiPayment.css';  // Same CSS file as before
// import Header from '../components/Header/Header';
import Header from '../Header/Header';

const UpiPayment = () => {
    const [senderUpiId, setSenderUpiId] = useState('');
    const [receiverUpiId, setReceiverUpiId] = useState('');
    const [upiPin, setUpiPin] = useState('');
    const [amount, setAmount] = useState('');
    const [label, setLabel] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    // Separate error states for each field
    const [upiIdError, setUpiIdError] = useState('');
    const [upiPinError, setUpiPinError] = useState('');
    const [amountError, setAmountError] = useState('');

    const validateUpiId = (upiId) => {
        const upiPattern = /^[0-9]{10}@ezpay$/;
        if (!upiPattern.test(upiId)) {
            setUpiIdError('Please enter a valid UPI ID (10-digit number followed by @ezpay)');
            return false;
        }
        return true;
    };

    const validateUpiPin = (pin) => {
        const pinPattern = /^[0-9]{4}$/;
        if (!pinPattern.test(pin)) {
            setUpiPinError('Please enter a valid 4-digit UPI Pin');
            return false;
        }
        return true;
    };

    const validateAmount = (amount) => {
        const parsedAmount = parseFloat(amount);
        if (isNaN(parsedAmount) || parsedAmount <= 0) {
            setAmountError('Please enter a valid amount greater than zero');
            return false;
        }
        return true;
    };

    const handleUpiIdChange = (e) => {
        setSenderUpiId(e.target.value);
    };

    const handleReceiverUpiIdChange = (e) => {
        setReceiverUpiId(e.target.value);
    };

    const handleUpiPinChange = (e) => {
        setUpiPin(e.target.value);
    };

    const handleAmountChange = (e) => {
        setAmount(e.target.value);
    };

    const handlePayment = async (e) => {
        e.preventDefault();

        // Clear any existing error messages
        setUpiIdError('');
        setUpiPinError('');
        setAmountError('');

        // Validate all fields before proceeding
        if (!validateUpiId(senderUpiId) || !validateUpiId(receiverUpiId)) {
            return;
        }
        if (!validateUpiPin(upiPin)) {
            return;
        }
        if (!validateAmount(amount)) {
            return;
        }

        // All validations passed, proceed with transaction
        setLoading(true);
        try {
            const response = await fetch('http://localhost:9020/api/process-payment', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ senderUpiId, receiverUpiId, amount, label, upiPin }),
            });

            let paymentStatus = '';
            let reason = '';

            if (response.ok) {
                paymentStatus = 'success';
            } else {
                paymentStatus = 'failed';
                reason = await response.text();
            }

            navigate('/payment-result', {
                state: {
                    receiverUpiId,
                    amount,
                    paymentStatus,
                    reason,
                    senderUpiId
                }
            });

        } catch (error) {
            console.error('Error:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
        <Header title="UPI Transfer"></Header>
        <div className="contact_section layout_padding">

            <div className="container-fluid">
                <h1 className="what_taital">Proceed with UPI Payment</h1>
                {loading ? (
                    <div className="loading-indicator">
                        <p>Payment in progress...</p>
                        <div className="spinner"></div>
                    </div>
                ) : (
                    <form onSubmit={handlePayment}>
                        <div>
                            <input
                                type="text"
                                className="mail_text_1"
                                placeholder="Enter your UPI ID"
                                value={senderUpiId}
                                onChange={handleUpiIdChange}
                                required />
                            {upiIdError && <p style={{ color: 'red' }}>{upiIdError}</p>}
                        </div>
                        <div>
                            <input
                                type="text"
                                className="mail_text_1"
                                placeholder="Enter receiver UPI ID"
                                value={receiverUpiId}
                                onChange={handleReceiverUpiIdChange}
                                required />
                            {upiIdError && <p style={{ color: 'red' }}>{upiIdError}</p>}
                        </div>
                        <div>
                            <input
                                type="password"
                                className="mail_text_1"
                                placeholder="Enter UPI Pin"
                                value={upiPin}
                                onChange={handleUpiPinChange}
                                required />
                            {upiPinError && <p style={{ color: 'red' }}>{upiPinError}</p>}
                        </div>
                        <div>
                            <input
                                type="number"
                                className="mail_text_1"
                                placeholder="Amount"
                                value={amount}
                                onChange={handleAmountChange}
                                required />
                            {amountError && <p style={{ color: 'red' }}>{amountError}</p>}
                        </div>
                        <div>
                            <input
                                type="text"
                                className="mail_text_1"
                                placeholder="Add a note (optional)"
                                value={label}
                                onChange={(e) => setLabel(e.target.value)} />
                        </div>
                        <button type="submit" className="send_bt">Pay</button>
                    </form>
                )}
            </div>
        </div></>
    );
};

export default UpiPayment;
