import React, { useEffect, useState } from 'react';
import './styles/FinancialTips.css';  // Import the CSS file

// Sample financial tips array
const financialTips = [
  { id: 1, tip: "Create a monthly budget to track your expenses.", details: "Budgeting helps you ensure that you're saving enough and not overspending." },
  { id: 2, tip: "Set up an emergency fund for unexpected expenses.", details: "An emergency fund helps you avoid debt when unexpected expenses come up." },
  { id: 3, tip: "Pay off high-interest debt as soon as possible.", details: "The longer you have high-interest debt, the more you pay in interest." },
  { id: 4, tip: "Invest a portion of your income for future goals.", details: "Investing helps grow your wealth over time for long-term goals like retirement." },
  { id: 5, tip: "Review your financial statements regularly.", details: "Monitoring your financial activities helps identify areas to cut back or improve." }
];

const FinancialTips = () => {
  const [currentTipIndex, setCurrentTipIndex] = useState(0);
  const [showDetails, setShowDetails] = useState(false);

  // Function to change the tip to the next one
  const nextTip = () => {
    setCurrentTipIndex((prevIndex) => (prevIndex + 1) % financialTips.length);
    setShowDetails(false);
  };

  // Function to go to the previous tip
  const prevTip = () => {
    setCurrentTipIndex((prevIndex) => 
      prevIndex === 0 ? financialTips.length - 1 : prevIndex - 1
    );
    setShowDetails(false);
  };

  // Function to toggle details view
  // const toggleDetails = () => {
  //   setShowDetails((prevState) => !prevState);
  // };
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentTipIndex((prevTip) => (prevTip + 1) % financialTips.length);
    }, 3000); // Swaps every 3 seconds
    return () => clearInterval(interval); // Cleanup interval on component unmount
  }, []);

  return (
    <div className="financial-tips-card">
   
      <p className="tips-text">{financialTips[currentTipIndex].tip}</p>

      {showDetails && (
        <p className="tips-details">{financialTips[currentTipIndex].details}</p>
      )}

      {/* <button className="details-button" onClick={toggleDetails}>
        {showDetails ? "Hide Details" : "Show Details"}
      </button> */}

      <div className="navigation-buttons">
        <button className="arrow-button" onClick={prevTip}>←</button>
        <button className="arrow-button" onClick={nextTip}>→</button>
      </div>
    </div>
  );
};

export default FinancialTips;
