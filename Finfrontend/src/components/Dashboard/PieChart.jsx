import React, { useState, useEffect } from 'react';
import { Pie } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
} from 'chart.js';

import './styles/PieChart.css'

// Register necessary components
ChartJS.register(ArcElement, Tooltip, Legend);

const PieChart = () => {
  const [upiAmount, setUpiAmount] = useState(0);  // State for UPI amount
  const [bankAmount, setBankAmount] = useState(0);  // State for Bank amount
  const [loading, setLoading] = useState(true);  // Loading state

  // Fetch the data from Spring Boot API
  useEffect(() => {
    const fetchTransactionData = async () => {
      try {
        let customerId=localStorage.getItem('customer_id');
        const upiResponse = await fetch('http://localhost:9020/api/totalupi/'+customerId);
        const bankResponse = await fetch('http://localhost:9020/API/totalbank/'+customerId);

        const upiData = await upiResponse.json();
        const bankData = await bankResponse.json();

        setUpiAmount(upiData);
        setBankAmount(bankData);
        setLoading(false);
        console.log(upiAmount);
        console.log(bankAmount);
      } catch (error) {
        console.error('Error fetching transaction data:', error);
      }
    };

    fetchTransactionData();
  }, [bankAmount,upiAmount]); // Empty dependency array to run only once on component mount

  // Chart data
  const data = {
    labels: ['UPI Transactions', 'Bank Transactions'],
    datasets: [
      {
        data: [upiAmount, bankAmount],
        backgroundColor: ['#007bff', '#28a745'], // Colors for the segments
        hoverBackgroundColor: ['#0056b3', '#218838'],
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
    },
  };

  // Loading state
  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="pie-chart-container" style={{ width: '300px', height: '430px' }}>
      <h3>Total amount paid by UPI Payment and Bank Payment</h3>
      <Pie data={data} options={options} />
    </div>
  );
};

export default PieChart;
