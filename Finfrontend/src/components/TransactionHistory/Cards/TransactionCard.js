/**
 * Author: Manvi
 * Date: 18-Sept-2024
 * 
 * Description:
 * TransactionCard is a React component that displays a list of transactions with pagination. 
 * It uses the 'useNavigate' hook to navigate to transaction details based on the transaction's ID.
 * Each transaction card has a border color based on its status (Pending, Completed, Failed, Cancelled).
 * It also includes pagination controls to navigate through multiple transaction pages.
 */

// import React from 'react';
// import { useNavigate } from 'react-router-dom'; // Import useNavigate hook
// import '../Styles/TransactionCard.css';
// import '../Styles/Pagination.css';

// function TransactionCard({ transactions, currentPage, totalPages, setCurrentPage }) {
//     const navigate = useNavigate(); // Initialize useNavigate hook
//     const getStatusColor = (status) => {
//         switch (status) {
//             case 0: return '#FFA500'; // Pending - Orange
//             case 1: return '#4CAF50'; // Completed - Green
//             case 2: return '#F44336'; // Failed - Red
//             default: return '#9E9E9E'; // Cancelled - Grey
//         }
//     };
    
//     const getStatusText = (status) => {
//         switch (status) {
//             case 0: return 'Pending';
//             case 1: return 'Completed';
//             case 2: return 'Failed';
//             default: return 'Cancelled';
//         }
//     };

//     const handlePrevPage = () => {
//         setCurrentPage(prev => Math.max(prev - 1, 1));
//     };

//     const handleNextPage = () => {
//         setCurrentPage(prev => Math.min(prev + 1, totalPages));
//     };
//     // Function to handle the onClick event and navigate to the details page
//     const handleCardClick = (transaction) => {
//     const transactionId = transaction.paymentId || transaction.transferId; // Prioritize bankTransactionId, but fallback to upiTransactionId
//     if (transactionId) {
//         navigate(`/transaction/${transactionId}`, { state: { transaction } }); // Navigate to transaction details
//     } else {
//         console.error("Transaction ID not found!"); // Handle case where neither ID is available
//     }
// };

//     return (
//         <div className="transaction-container">
//             <div className="transaction-list">
//                 {transactions.length === 0 ? (
//                     <div className='no-transaction-found'>Transaction Not Found</div>
//                 ) : (
//                     transactions.map((transaction, index) => (
//                         <div 
//                             key={index} 
//                             className="transaction-card"
//                             style={{borderLeft: `4px solid ${getStatusColor(transaction.status)}`}}
//                             onMouseEnter={(e) => e.currentTarget.style.transform = 'translateY(-2px)'}
//                             onMouseLeave={(e) => e.currentTarget.style.transform = 'translateY(0)'}
//                             onClick={() => handleCardClick(transaction)} // Trigger navigation on click
//                         >
//                             <div className="transaction-header">
//                                 <span className="transaction-type">
//                                     {transaction.paymentType === 'UPI' ? 'UPI Transfer' : 'Bank Payment'}
//                                 </span>
//                                 <span className="transaction-status" style={{backgroundColor: getStatusColor(transaction.status)}}>
//                                     {getStatusText(transaction.status)}
//                                 </span>
//                             </div>
//                             <div className="transaction-details">
//                                 <span className="transaction-amount">₹{transaction.amount.toFixed(2)}</span>
//                                 <span className="transaction-date">{new Date(transaction.timestamp).toLocaleString()}</span>
//                             </div>
//                             {/* <div className="transaction-info">
//                                 <p><strong>Remarks:</strong> {transaction.remark}</p>
//                                 <p><strong>Label:</strong> {transaction.label}</p>
//                             </div> */}
//                         </div>
//                     ))
//                 )}
//             </div>

//             {transactions.length === 0 ? (
//                 <div></div>
//                 ) : (
//                 <div className="pagination-controls">
//                     <button 
//                     className="pagination-button"
//                     onClick={handlePrevPage} 
//                     disabled={currentPage === 1}>
//                     Previous
//                     </button>

//                     <span className="pagination-info">
//                     Page {currentPage} of {totalPages}
//                     </span>

//                     <button 
//                     className="pagination-button"
//                     onClick={handleNextPage} 
//                     disabled={currentPage === totalPages}>
//                     Next
//                     </button>
//                 </div>
//                 )}

//         </div>
//     );
// }

// export default TransactionCard;







// import React from 'react';
// import { useNavigate } from 'react-router-dom';
// import '../Styles/TransactionCard.css';
// import '../Styles/Pagination.css';

// function TransactionCard({ transactions, currentPage, totalPages, setCurrentPage, customerId }) {
//     const navigate = useNavigate();

//     const getStatusColor = (status) => {
//         switch (status) {
//             case 0: return '#FFA500'; // Pending - Orange
//             case 1: return '#4CAF50'; // Completed - Green
//             case 2: return '#F44336'; // Failed - Red
//             default: return '#9E9E9E'; // Cancelled - Grey
//         }
//     };
    
//     const getStatusText = (status) => {
//         switch (status) {
//             case 0: return 'Pending';
//             case 1: return 'Completed';
//             case 2: return 'Failed';
//             default: return 'Cancelled';
//         }
//     };

//     const handlePrevPage = () => {
//         setCurrentPage(prev => Math.max(prev - 1, 1));
//     };

//     const handleNextPage = () => {
//         setCurrentPage(prev => Math.min(prev + 1, totalPages));
//     };

//     const handleCardClick = (transaction) => {
//         const transactionId = transaction.paymentId || transaction.transferId;
//         if (transactionId) {
//             const isDebitTransaction = isDebit(transaction);
//             navigate(`/transaction/${transactionId}`, { 
//                 state: { 
//                     transaction,
//                     isDebit: isDebitTransaction // Pass isDebit to the next page
//                 }});
//         } else {
//             console.error("Transaction ID not found!");
//         }
//     };

//     const isDebit = (transaction) => {
//         // console.log(`amount = ${transaction.amount}, passed coustomerid=${customerId} , cid=${transaction.sender.customerId}`);
//         // console.log(`type cid =${typeof(customerId)} , cid=${typeof(transaction.sender.customerId)}`);
//         return customerId===transaction.sender.customerId.toString();
//         // if (transaction.paymentType === 'UPI') {
//         //     return transaction.senderUpiId === transaction.sender.upiId;
//         // } else if (transaction.paymentType === 'Bank') {
//         //     return transaction.senderAccountNumber === transaction.sender.bankAccountNumber;
//         // }
//         // return false;
//     };

//     return (
//         <div className="transaction-container">
//             <div className="transaction-list">
//                 {transactions.length === 0 ? (
//                     <div className='no-transaction-found'>Transaction Not Found</div>
//                 ) : (
//                     transactions.map((transaction, index) => (
//                         <div 
//                             key={index} 
//                             className="transaction-card"
//                             style={{borderLeft: `0px solid ${getStatusColor(transaction.status)}`}}
//                             onMouseEnter={(e) => e.currentTarget.style.transform = 'translateY(-2px)'}
//                             onMouseLeave={(e) => e.currentTarget.style.transform = 'translateY(0)'}
//                             onClick={() => handleCardClick(transaction)}
//                         >
//                             <div className="transaction-header">
//                                 <span className="transaction-type">
//                                     {transaction.paymentType === 'UPI' ? 'UPI Transfer' : 'Bank Payment'}
//                                 </span>
//                                 <span className="transaction-status" style={{backgroundColor: getStatusColor(transaction.status)}}>
//                                     {getStatusText(transaction.status)}
//                                 </span>
//                             </div>
//                             <div className="transaction-details">
//                                 <span 
//                                     className={`transaction-amount ${isDebit(transaction) ? 'debit' : 'credit'}`}
//                                 >
//                                     {isDebit(transaction) ? '- ' : '+ '}
//                                     ₹{transaction.amount.toFixed(2)}
//                                 </span>
//                                 <span className="transaction-date">{new Date(transaction.timestamp).toLocaleString()}</span>
//                             </div>
//                         </div>
//                     ))
//                 )}
//             </div>

//             {transactions.length === 0 ? (
//                 <div></div>
//             ) : (
//                 <div className="pagination-controls">
//                     <button 
//                         className="pagination-button"
//                         onClick={handlePrevPage} 
//                         disabled={currentPage === 1}>
//                         Previous
//                     </button>

//                     <span className="pagination-info">
//                         Page {currentPage} of {totalPages}
//                     </span>

//                     <button 
//                         className="pagination-button"
//                         onClick={handleNextPage} 
//                         disabled={currentPage === totalPages}>
//                         Next
//                     </button>
//                 </div>
//             )}
//         </div>
//     );
// }

// export default TransactionCard;





import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../Styles/TransactionCard.css';
import '../Styles/Pagination.css';

function TransactionCard({ transactions, currentPage, totalPages, setCurrentPage, customerId }) {
    const navigate = useNavigate();

    const getStatusColor = (status) => {
        switch (status) {
            case 0: return '#FFA500'; // Pending - Orange
            case 1: return '#4CAF50'; // Completed - Green
            case 2: return '#F44336'; // Failed - Red
            default: return '#9E9E9E'; // Cancelled - Grey
        }
    };

    
    const getStatusText = (status) => {
        switch (status) {
            case 0: return 'Pending';
            case 1: return 'Completed';
            case 2: return 'Failed';
            default: return 'Cancelled';
        }
    };

    const handlePrevPage = () => {
        setCurrentPage(prev => Math.max(prev - 1, 1));
    };

    const handleNextPage = () => {
        setCurrentPage(prev => Math.min(prev + 1, totalPages));
    };

    const handleCardClick = (transaction) => {
        const transactionId = transaction.paymentId || transaction.transferId;
        if (transactionId) {
            const isDebitTransaction = isDebit(transaction);
            navigate(`/transaction/${transactionId}`, { 
                state: { 
                    transaction,
                    isDebit: isDebitTransaction
                }});
        } else {
            console.error("Transaction ID not found!");
        }
    };

    const isDebit = (transaction) => {
        return customerId === transaction.sender.customerId.toString();
    };

    return (
        <div className="transaction-container">
            <div className="transaction-list">
                <div className="transaction-header-row" style={{fontWeight: 'bold'}}>
                    <div className="header-cell">Payment Id</div>
                    <div className="header-cell">Date</div>
                    <div className="header-cell">Payment Type</div>
                    <div className="header-cell">Amount</div>
                    <div className="header-cell">Status</div>
                </div>
                {transactions.length === 0 ? (
                    <div className='no-transaction-found'>Transaction Not Found</div>
                ) : (
                    transactions.map((transaction, index) => (
                        <div 
                            key={index} 
                            className="transaction-card"
                            onClick={() => handleCardClick(transaction)}
                        >
                            <div className="transaction-cell">#{transaction.paymentId || transaction.transferId}</div>
                            <div className="transaction-cell">
                                {new Date(transaction.timestamp).toLocaleDateString('en-GB', {
                                    day: '2-digit',
                                    month: 'short',
                                    year: 'numeric'
                                })}
                                </div>
                            <div className="transaction-cell">{transaction.paymentType === 'UPI' ? 'UPI Payment' : 'Bank Transfer'}</div>
                            <div className={`transaction-cell ${isDebit(transaction) ? 'debit' : 'credit'}`}>
                                {isDebit(transaction) ? '- ' : '+ '}₹{transaction.amount.toFixed(2)}
                            </div>
                            <div className="transaction-cell">
                                <span className="transaction-status" style={{backgroundColor: getStatusColor(transaction.status)}}>
                                    {getStatusText(transaction.status)}
                                </span>
                            </div>
                        </div>
                    ))
                )}
            </div>

            {transactions.length > 0 && (
                <div className="pagination-controls">
                    <button 
                        className="pagination-button"
                        onClick={handlePrevPage} 
                        disabled={currentPage === 1}>
                        Previous
                    </button>

                    <span className="pagination-info">
                        Page {currentPage} of {totalPages}
                    </span>

                    <button 
                        className="pagination-button"
                        onClick={handleNextPage} 
                        disabled={currentPage === totalPages}>
                        Next
                    </button>
                </div>
            )}
        </div>
    );
}

export default TransactionCard;




