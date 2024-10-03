// import React, { useState } from 'react';

// const ActivateBankAccount = () => {
//     const [upiId, setUpiId] = useState('');
//     const [upiPin, setUpiPin] = useState('');
//     const [confirmUpiPin, setConfirmUpiPin] = useState('');

//     const handleActivation = async (e) => {
//         e.preventDefault();
//         // Validate inputs, then send the request
//         if (upiPin !== confirmUpiPin) {
//             alert("UPI Pins do not match.");
//             return;
//         }

//         try {
//             const response = await fetch('http://localhost:8073/api/activate-bank-account', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify({ upiId, upiPin }),
//             });

//             if (response.ok) {
//                 alert("Bank account activated successfully!");
//             } else {
//                 alert("Failed to activate bank account.");
//                 const result = await response.text();
//                 alert(result);
//             }
//         } catch (error) {
//             console.error('Error:', error);
//         }
//     };

//     return (
//         <form onSubmit={handleActivation}>
//             <h2>Activate Bank Account</h2>
//             <input
//                 type="text"
//                 placeholder="Enter your UPI ID"
//                 value={upiId}
//                 onChange={(e) => setUpiId(e.target.value)}
//                 required
//             />
//             <input
//                 type="password"
//                 placeholder="Set your UPI Pin"
//                 value={upiPin}
//                 onChange={(e) => setUpiPin(e.target.value)}
//                 required
//             />
//             <input
//                 type="password"
//                 placeholder="Confirm your UPI Pin"
//                 value={confirmUpiPin}
//                 onChange={(e) => setConfirmUpiPin(e.target.value)}
//                 required
//             />
//             <button type="submit">Activate</button>
//         </form>
//     );
// };

// export default ActivateBankAccount;

import React, { useState } from 'react';
import './UpiPayment.css'; // Ensure you're using the same CSS file

const ActivateBankAccount = () => {
    const [upiId, setUpiId] = useState('');
    const [upiPin, setUpiPin] = useState('');
    const [confirmUpiPin, setConfirmUpiPin] = useState('');

    const handleActivation = async (e) => {
        e.preventDefault();
        if (upiPin !== confirmUpiPin) {
            alert("UPI Pins do not match.");
            return;
        }

        try {
            const response = await fetch('http://localhost:8073/api/activate-bank-account', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ upiId, upiPin }),
            });

            if (response.ok) {
                alert("Bank account activated successfully!");
            } else {
                alert("Failed to activate bank account.");
                const result = await response.text();
                alert(result);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div className="contact_section layout_padding">
            <div className="container-fluid">
                <h1 className="what_taital">Activate Bank Account</h1>
                <form onSubmit={handleActivation}>
                    <input
                        type="text"
                        className="mail_text_1"
                        placeholder="Enter your UPI ID"
                        value={upiId}
                        onChange={(e) => setUpiId(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        className="mail_text_1"
                        placeholder="Set your UPI Pin"
                        value={upiPin}
                        onChange={(e) => setUpiPin(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        className="mail_text_1"
                        placeholder="Confirm your UPI Pin"
                        value={confirmUpiPin}
                        onChange={(e) => setConfirmUpiPin(e.target.value)}
                        required
                    />
                    <button type="submit" className="send_bt">Activate</button>
                </form>
            </div>
        </div>
    );
};

export default ActivateBankAccount;
