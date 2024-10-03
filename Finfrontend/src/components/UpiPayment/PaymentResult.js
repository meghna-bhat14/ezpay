import { useLocation, useNavigate } from 'react-router-dom';
// import './UpiPayment.css';
import html2canvas from 'html2canvas';
import { useEffect, useRef } from 'react';
import Header from '../Header/Header';
import './UpiPayment.css';

/**
 * PaymentResult Component
 * 
 * This component displays the result of a payment transaction, including payment status, amount,
 * sender and receiver UPI IDs, and an option to retry the payment or check the balance.
 * It also allows the user to take a screenshot of the transaction details.
 * 
 * @author Meghna Bhat
 * @date 24th September 2024
 */

export default function PaymentResult() {
    const navigate = useNavigate();
    const { state } = useLocation();
    
    // Destructure properties from state with default values
    const {
        senderUpiId = 'Sender UPI not available',
        receiverUpiId = 'Receiver UPI not available',
        amount = '0.00',
        paymentStatus = 'Unknown',
        reason = 'No reason available',
        paymentId = 'No payment ID available' 
    } = state || {};

    const cardRef = useRef(null); // Create a ref for the card

    // Function to handle clicking the "Pay Again" button
    const handlePayAgainClick = () => {
        navigate('/upi-payment');
    };

    // Function to navigate to the Check Balance page with sender UPI ID
    const checkBalance = () => {
        navigate('/checkbalance', { state: { senderUpiId } });
    };

    // Function to navigate to the Bank Activation page with sender UPI ID
    const handleActivationClick = () => {
        navigate('/activate', { state: { senderUpiId } });
    };

    // Function to capture a screenshot of the current document body
    const captureScreenshot = () => {
        if (cardRef.current) { // Ensure the ref is defined
            html2canvas(document.body).then((canvas) => {
                const link = document.createElement('a');
                link.href = canvas.toDataURL();
                link.download = 'screenshot.png';
                link.click();
            });
        } else {
            console.error("Card element not found");
        }
    };


     // Effect to perform cleanup actions on component unmount
     useEffect(() => {
        // Log or handle any necessary cleanup actions here
        console.log("PaymentResult component mounted.");

        // Cleanup function to reset or clear any values when the component is unmounted
        return () => {
            console.log("PaymentResult component unmounted. Cleaning up...");
        sessionStorage.removeItem('transactionStatus');
        sessionStorage.removeItem('paymentId');
        sessionStorage.removeItem('reason');
        sessionStorage.removeItem('receiverUpiId');
        sessionStorage.removeItem('amount');
        sessionStorage.removeItem('paymentStatus');
        sessionStorage.removeItem('senderUpiId');

        };
    }, []);

    return (
        <>
        <Header title="Payment Result"/>
           
<div className="payment-result">
    <h1 className="ezpay-title" id='EZPAY'>EZPAY</h1>
    <div className="result-container">
        <div className="result-card"  id = "resultt" style={{ cursor: 'pointer' }} ref={cardRef}>
            <h3 className="result-header">
                {paymentStatus === 'success' ? 'THANK YOU!' : 'PLEASE TRY AGAIN!'}
            </h3>

            <img
                    src={paymentStatus === 'success' ? '/images/Success.gif' : '/images/Error.gif'}
                    alt={paymentStatus === 'success' ? 'Payment Successful' : 'Payment Failed'} 
                    className='status-image'
                />
            <div className="status-section">
                <h3 className="amount">Rs {amount}</h3>
            </div>
            
            <p className="transaction-details" style={{ marginBottom: 'auto', textAlign: 'center', color: '#1b5a8d' }}>
                From: {senderUpiId} <br/>
                To: {receiverUpiId}<br/>
                Payment ID: {paymentId}
            </p>
            <p className="transaction-date" style={{ marginBottom: 'auto', textAlign: 'center', color: '#1b5a8d' }}>
                {new Date().toLocaleString(undefined, {
                    month: 'short', day: '2-digit', hour: '2-digit', minute: '2-digit', hour12: true
                })}
            </p>
            <div className="button-container" style={{ display: 'flex', gap: '10px' }}>
                <button onClick={captureScreenshot} className="share-button">
                    Share
                </button>
                <button onClick={checkBalance} className="check-balance-button">
                    Check Balance
                </button>
                <button onClick={handlePayAgainClick} className="check-balance-button">
                    Pay Again
                </button>
            </div>
        </div>
        <h1 
            style={{ 
                textAlign: 'center', 
                color: paymentStatus === 'success' ? 'green' : 'red' ,
                marginTop:'20px',
            }}
        >
            PAYMENT {paymentStatus.toUpperCase()}
        </h1>
        {paymentStatus === 'failed' && (
            <>
                <p className="error-reason">Reason: {reason}</p>
                {reason.includes("Bank account not activated") && (
                    <button onClick={handleActivationClick} className="activate-bank">
                        Would you like to activate your account?
                    </button>
                )}
            </>
        )}
    </div>
</div>

        </>
    );
}