import React from 'react';
import { useLocation } from 'react-router-dom';
// import Header from '../components/Header/Header';
import Header from '../Header/Header';
// import Footer from '../components/Footer/Footer';
import Footer from '../Footer/Footer';
import './BalancePage.css';

const BalancePage = () => {
    const location = useLocation();
    const balance = location.state?.balance ?? null; // Get balance
    const upiId = location.state?.upiId ?? 'UPI ID not available'; // Get UPI ID
    const isSuccess = balance !== null; // Determine if the fetch was successful

    return (
        <>
            <Header title="Your Account Balance" />
            <div className="container">
                <div className="balance-card">
                    <div className={`status-icon ${isSuccess ? 'status-icon-success' : 'status-icon-failure'}`}>
                        {isSuccess ? '✅' : '❌'}
                    </div>
                    <p className="balance-message">
                        {isSuccess 
                            ? `${balance}.`
                            : 'Unable to fetch balance. Please try again.'}
                    </p>
                </div>
            </div>
            <Footer />
        </>
    );
};

export default BalancePage;
