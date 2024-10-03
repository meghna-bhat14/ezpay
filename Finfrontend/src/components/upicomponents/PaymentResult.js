import { useLocation, useNavigate } from 'react-router-dom';
import html2canvas from 'html2canvas';
import { useEffect, useRef } from 'react';
// import Header from '../components/Header/Header';
import Header from '../Header/Header';
import '../UpiPayment/UpiPayment.css';

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './ToastStyles.css'; // Create this CSS file for custom styles
import DoneImg from "../../assets/Done.png";
import FailedImg from "../../assets/failed.png";

export function PaymentResult() {
    const navigate = useNavigate();
    const { state } = useLocation();
    
    const {
        senderUpiId = 'Sender UPI not available',
        receiverUpiId = 'Receiver UPI not available',
        amount = '0.00',
        paymentStatus = 'Unknown',
        reason = 'No reason provided',
        paymentId='No payment Id available'
    } = state || {};

    const cardRef = useRef(null);

    const handleActivationClick = () => {
        navigate('/activate');
    };

    const handlePayAgainClick = () => {
        navigate('/upipayment');
    };

    const checkBalance = () => {
        navigate('/checkbalance', { state: { senderUpiId } });
    };

    const captureScreenshot = () => {
        if (cardRef.current) {
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

    useEffect(() => {
        if (paymentStatus === 'success') {
            toast.success('Payment successful! Check your email for details.', {
                autoClose: 5000, // Toast will last for 5 seconds
                className: 'custom-toast' // Apply custom class
            });
        } else if (paymentStatus === 'failed') {
            toast.error(`Payment failed. Reason: ${reason}`, {
                autoClose: 5000, // Toast will last for 5 seconds
                className: 'custom-toast' // Apply custom class
            });
        }
    }, [paymentStatus, reason]);

    return (
        // <>
        //     <Header title="Payment Result"/>
        //     <ToastContainer />
        //     <div className="paymentresult">
        //         <div className="container">
        //             <div className="what_we_do_section_2">
        //                 <h1 className="what_taital" id='EZPAY'>EZPAY</h1>
        //                 <center>
        //                 <div className="card_box" style={{ cursor: 'pointer' }} ref={cardRef}>
        //                     <h3 className="accounting_text2">THANK YOU!</h3>
        //                     <div className="icon_12">
        //                         <h3 className="accounting_text2">Rs {amount}</h3>
        //                         <img
        //                             src={paymentStatus === 'success' ? DoneImg : FailedImg}
        //                             alt={paymentStatus === 'success' ? 'Payment Successful' : 'Payment Failed'} className='status-image'
        //                         />
        //                     </div>
        //                     <p className="lorem_text" style={{ marginBottom: 'auto', textAlign: 'center', color: '#1b5a8d' }}>
        //                         From {senderUpiId} <br />
        //                         To {receiverUpiId}
        //                     </p>
        //                     <p className="lorem_text" style={{ marginBottom: 'auto', textAlign: 'center', color: '#1b5a8d' }}>
        //                         {new Date().toLocaleString(undefined, {
        //                             month: 'short', day: '2-digit', hour: '2-digit', minute: '2-digit', hour12: true
        //                         })}
        //                     </p>
        //                     <div className="moremore_bt_1" style={{ display: 'flex', gap: '10px' }}>
        //                         <button onClick={captureScreenshot} className="share-button">Share</button>
        //                         <button onClick={checkBalance} className="check-balance-button">Check Balance</button>
        //                         <button onClick={handlePayAgainClick} className="check-balance-button">Pay Again</button>
        //                     </div>
        //                 </div>
        //                 </center>

        //                 <h1
        //                     style={{
        //                         textAlign: 'center',
        //                         color: paymentStatus === 'success' ? 'green' : 'red'
        //                     }}
        //                 >
        //                    <div style={{marginTop:'1.5rem'}}>
        //                    PAYMENT {paymentStatus.toUpperCase()}
        //                     </div>
        //                 </h1>
        //                 {paymentStatus === 'failed' && (
        //                     <p className="what_text">Reason: {reason}</p>
        //                 )}
        //             </div>
        //         </div>
        //     </div>
        // </>

        <>
    <div 
        className="payment-result" 
        style={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            height: '100vh',
            backgroundColor: '#f2f2f2',
        }}
    >
        <h1 
            className="ezpay-title" 
            id='EZPAY' 
            style={{
                fontSize: '2em',
                color: '#2c3e50',
                marginBottom: '10px',
            }}
        >
            EZPAY
        </h1>
        <div 
            className="result-container" 
            style={{
                maxWidth: '100%',
                margin: '0 auto',
                padding: '20px',
                justifyContent: 'center',
            }}
        >
            <div 
                className="result-card" 
                id="resultt" 
                style={{
                    cursor: 'pointer',
                    width: '350px',
                    maxWidth: '500px',
                    backgroundColor: '#ffffff',
                    padding: '40px',
                    borderRadius: '8px',
                    textAlign: 'center',
                    boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '400px',
                    marginLeft: '17px',
                }}
                ref={cardRef}
            >
                <h3 
                    className="result-header" 
                    style={{
                        color: '#2c3e50',
                        marginRight: '10px',
                        fontSize: '15px',
                    }}
                >
                    {paymentStatus === 'success' ? 'THANK YOU!' : 'PLEASE TRY AGAIN!'}
                </h3>

                <img
                    src={paymentStatus === 'success' ? '/images/Success.gif' : '/images/Error.gif'}
                    alt={paymentStatus === 'success' ? 'Payment Successful' : 'Payment Failed'} 
                    className='status-image'
                    style={{
                        width: '50px',
                        height: 'auto',
                    }}
                />
                <div 
                    className="status-section" 
                    style={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                    }}
                >
                    <h3 
                        className="amount" 
                        style={{
                            color: '#2c3e50',
                            marginRight: '10px',
                            fontSize: '30px',
                            marginTop: '2px',
                        }}
                    >
                        Rs {amount}
                    </h3>
                </div>

                <p 
                    className="transaction-details" 
                    style={{
                        marginBottom: '10px',
                        textAlign: 'center',
                        color: '#2c3e50',
                    }}
                >
                    From: {senderUpiId} <br />
                    To: {receiverUpiId} <br />
                    Payment ID: {paymentId}
                </p>

                <p 
                    className="transaction-date" 
                    style={{
                        marginBottom: '10px',
                        textAlign: 'center',
                        color: '#2c3e50',
                    }}
                >
                    {new Date().toLocaleString(undefined, {
                        month: 'short', day: '2-digit', hour: '2-digit', minute: '2-digit', hour12: true
                    })}
                </p>

                <div 
                    className="button-container" 
                    style={{
                        display: 'flex',
                        gap: '10px',
                        marginTop: '30px',
                        justifyContent: 'center',
                    }}
                >
                    <button 
                        onClick={captureScreenshot} 
                        className="share-button" 
                        style={{
                            width: '80px',
                            fontSize: '11px',
                            color: '#ffffff',
                            textAlign: 'center',
                            padding: '5px 20px',
                            borderRadius: '40px',
                            backgroundColor: '#2c3e50',
                        }}
                    >
                        Share
                    </button>

                    <button 
                        onClick={checkBalance} 
                        className="check-balance-button" 
                        style={{
                            width: '80px',
                            fontSize: '11px',
                            color: '#ffffff',
                            textAlign: 'center',
                            padding: '5px 20px',
                            borderRadius: '40px',
                            backgroundColor: '#2c3e50',
                        }}
                    >
                        Check Balance
                    </button>

                    <button 
                        onClick={handlePayAgainClick} 
                        className="check-balance-button" 
                        style={{
                            width: '80px',
                            fontSize: '11px',
                            color: '#ffffff',
                            textAlign: 'center',
                            padding: '5px 20px',
                            borderRadius: '40px',
                            backgroundColor: '#2c3e50',
                        }}
                    >
                        Pay Again
                    </button>
                </div>
            </div>

            <h1 
                style={{ 
                    textAlign: 'center', 
                    color: paymentStatus === 'success' ? 'green' : 'red' ,
                    marginTop: '20px',
                }}
            >
                PAYMENT {paymentStatus.toUpperCase()}
            </h1>

            {paymentStatus === 'failed' && (
                <>
                    <p 
                        className="error-reason" 
                        style={{
                            marginTop: '-10px',
                            color: '#f44336',
                            textAlign: 'center',
                        }}
                    >
                        Reason: {reason}
                    </p>
                    {reason.includes("Bank account not activated") && (
                        <button 
                            onClick={handleActivationClick} 
                            className="activate-bank" 
                            style={{
                                marginTop: '10px',
                                padding: '10px 20px',
                                backgroundColor: '#f44336',
                                color: '#fff',
                                border: 'none',
                                borderRadius: '5px',
                                cursor: 'pointer',
                            }}
                        >
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

