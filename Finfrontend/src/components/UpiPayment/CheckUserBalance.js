// 

/**
@author Meghna Bhat 
* Date: 22nd September 2024
* Description: This component provides a user interface for checking the bank account balance.
* t allows the user to enter a UPI ID and PIN, or pre-fills the UPI ID if provided via the state.
* The balance is retrieved from the backend API and displayed to the user.
*/

import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';


const CheckUserBalance = () => {
    const { state } = useLocation();
    const senderUpiIdFromState = state?.senderUpiId; // Get senderUpiId from state if passed via navigation

    // State variables to manage user input and messages
    const [upiId, setUpiId] = useState(senderUpiIdFromState || ''); // Initialize UPI ID with state or empty
    const [transactionPin, setTransactionPin] = useState(''); // State for transaction PIN
    const [balanceMessage, setBalanceMessage] = useState(''); // State to store balance response from API
    const [error, setError] = useState(''); // State to store error messages

    /**
     * getBalance
     * @description Fetches the balance of the bank account associated with the UPI ID.
     * It sends a GET request to the backend API with the provided UPI ID and PIN,
     * and updates the state based on the response.
     */
    const getBalance = async () => {
        try {
            // Reset any previous error messages and balance message
            setError('');
            setBalanceMessage('');

            // Make a GET request to the backend API to fetch the balance
            const response = await fetch(`http://localhost:9020/api/get-balance?senderUpiId=${encodeURIComponent(upiId)}&SenderUpiPin=${encodeURIComponent(transactionPin)}`);

            // Check if the response status is not ok (>= 400)
            if (!response.ok) {
                // Read error message from response and throw an error
                const errorText = await response.text();
                throw new Error(errorText);
            }

            // Parse the response text to get the balance information
            const balanceText = await response.text();

            // Set the balance message state to display the balance information
            setBalanceMessage(balanceText);
        } catch (err) {
            // Set the error state with the error message if an exception occurs
            setError(`Error fetching balance: ${err.message}`);
        }
    };

    return (
        // <div style={{
        //     textAlign: 'center',
        //     marginTop: '50px',
        // }}>
        //     {/* Heading outside the card */}
        //     <h2 style={{
        //         marginBottom: '20px',
        //         fontSize: '28px',
        //         color: '#34495e',
        //         fontWeight: 'bold'
        //     }}>Check Bank Account Balance</h2>
        
        //     <div style={{
        //         padding: '30px',
        //         backgroundColor: 'white',
        //         // boxShadow: '0px0 2px 10px rgba(0, 0, 0, 0.1)', // Card-like shadow
        //         boxShadow: '0px 4px 15px #5e768e',
        //         borderRadius: '10px',
        //         maxWidth: '500px', // Set a maximum width for the card
        //         marginLeft: 'auto',
        //         marginRight: 'auto',
        //         border: '1px solid #1d4369', // Full border color for the card
                
        //     }}>
        //         {/* Display UPI ID if provided from state or show input for entering UPI ID */}
        //         {senderUpiIdFromState ? (
        //             <p style={{
        //                 color: '#34495e',
        //                 fontSize: '16px',
        //                 margin: '10px 0'
        //             }}>UPI ID: {senderUpiIdFromState}</p> // Display the fetched UPI ID
        //         ) : (
        //             <input
        //                 type="text"
        //                 value={upiId}
        //                 onChange={(e) => setUpiId(e.target.value)}
        //                 placeholder="Enter UPI ID"
        //                 style={{
        //                     padding: '10px',
        //                     width: '100%', // Full width of the card
        //                     margin: '10px 0',
        //                     border: '1px solid #1d4369', // Border color
        //                     borderRadius: '5px' // Rounded corners
        //                 }}
        //             />
        //         )}
        //         <br />
                
        //         {/* Input field for entering UPI PIN */}
        //         <input
        //             type="password"
        //             value={transactionPin}
        //             onChange={(e) => setTransactionPin(e.target.value)}
        //             placeholder="Enter UPI PIN"
        //             style={{
        //                 padding: '10px',
        //                 width: '100%', // Full width of the card
        //                 marginTop: '20px',
        //                 border: '1px solid #1d4369', // Consistent border color
        //                 borderRadius: '5px' // Rounded corners
        //             }}
        //         />
        //         <br />
                
        //         {/* Button to trigger the getBalance function */}
        //         <button
        //             onClick={getBalance}
        //             style={{
        //                 padding: '10px 15px',
        //                 marginTop: '20px',
        //                 cursor: 'pointer',
        //                 backgroundColor: '#2c3e50',
        //                 color: 'white',
        //                 border: 'none',
        //                 borderRadius: '5px',
        //                 width: '100%' // Full width of the card
        //             }}
        //         >
        //             Check Balance
        //         </button>
        
                
        //     </div>
        //     {/* Display the balance message or error */}
        //     {balanceMessage && <p style={{ marginTop: '20px', color: 'green' }}>{balanceMessage}</p>}
        //         {error && <p style={{ marginTop: '20px', color: 'red' }}>{error}</p>}
        // </div>

        <div style={{
            textAlign: 'center',
            marginTop: '50px',
        }}>
            {/* Heading outside the card */}
            <h2 style={{
                marginBottom: '20px',
                fontSize: '28px',
                color: '#34495e',
                fontWeight: 'bold'
            }}>Check Bank Account Balance</h2>
        
            <div style={{
                padding: '30px',
                backgroundColor: 'white',
                // boxShadow: '0px0 2px 10px rgba(0, 0, 0, 0.1)', // Card-like shadow
                boxShadow: '0px 4px 15px #5e768e',
                borderRadius: '10px',
                maxWidth: '500px', // Set a maximum width for the card
                marginLeft: 'auto',
                marginRight: 'auto',
                border: '1px solid #1d4369', // Full border color for the card
                
            }}>
                {/* Display UPI ID if provided from state or show input for entering UPI ID */}
                {senderUpiIdFromState ? (
                    <p style={{
                        color: '#34495e',
                        fontSize: '16px',
                        margin: '10px 0',
                    
                        
                    }}>UPI ID: {senderUpiIdFromState}</p> // Display the fetched UPI ID
                ) : (
                    <input
                        type="text"
                        value={upiId}
                        onChange={(e) => setUpiId(e.target.value)}
                        placeholder="Enter UPI ID"
                        style={{
                            padding: '10px',
                            width: '470px', // Full width of the card
                            margin: '10px -10px',
                            border: '1px solid #1d4369', // Border color
                            borderRadius: '5px' // Rounded corners
                        }}
                    />
                )}
                <br />
                
                {/* Input field for entering UPI PIN */}
                <input
                    type="password"
                    value={transactionPin}
                    onChange={(e) => setTransactionPin(e.target.value)}
                    placeholder="Enter UPI PIN"
                    style={{
                        padding: '10px',
                        width: '470px', // Full width of the card
                        marginTop: '20px',
                        border: '1px solid #1d4369', // Consistent border color
                        margin: '10px -10px',
                        borderRadius: '5px' // Rounded corners
                    }}
                />
                <br />
                
                {/* Button to trigger the getBalance function */}
                <button
                    onClick={getBalance}
                    style={{
                        padding: '10px 15px',
                        marginTop: '20px',
                        cursor: 'pointer',
                        backgroundColor: '#2c3e50',
                        color: 'white',
                        border: 'none',
                        borderRadius: '5px',
                        width: '100%' // Full width of the card
                    }}
                >
                    Check Balance
                </button>
        
                
            </div>
            {/* Display the balance message or error */}
            {balanceMessage && <p style={{ marginTop: '20px', color: 'green' }}>{balanceMessage}</p>}
                {error && <p style={{ marginTop: '20px', color: 'red' }}>{error}</p>}
        </div>
        
    )}
export default CheckUserBalance;


