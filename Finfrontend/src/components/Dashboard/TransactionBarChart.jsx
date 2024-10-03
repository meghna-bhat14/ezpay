import React, { useEffect, useState } from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, BarElement, CategoryScale, LinearScale, Tooltip, Legend } from 'chart.js';

// Register the necessary components for Bar chart
ChartJS.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend);

const BarChart = () => {
  // Data for the bar chart
  
  
  const [lastMonUPI, setlastMonUPI] = useState(0);  // State for UPI amount
  const [lastMonBank, setlastMonBank] = useState(0);  // State for Bank amount
  const [loading, setLoading] = useState(true);  // Loading state

  // Fetch the data from Spring Boot API
  useEffect(() => {
    const fetchTransactionData = async () => {
      try {
        let customerId=localStorage.getItem('customer_id');
        const upiResponse = await fetch('http://localhost:9020/api/previousmonthupicount/'+customerId);
        const bankResponse = await fetch('http://localhost:9020/API/previousmonthbankcount/'+customerId);

        const upiData = await upiResponse.json();
        const bankData = await bankResponse.json();

        setlastMonUPI(upiData);
        setlastMonBank(bankData);
        setLoading(false);
        console.log(lastMonUPI);
        console.log(lastMonBank);
      } catch (error) {
        console.error('Error fetching transaction data:', error);
      }
    };

    fetchTransactionData();
  }, [lastMonBank,lastMonUPI]);
  
  const barData = {
   // labels: ['January', 'February', 'March', 'April', 'May'],
   labels:['Last Month'],
    datasets: [
      {
        label: 'UPI Transactions',
       // data: [50, 60, 70, 180, 190,],
       data:[lastMonUPI],
        backgroundColor: '#0b7285',
        borderColor: '#086e75',
        borderWidth: 1,
      },
      {
        label: 'Bank Transactions',
        // data: [20, 30, 40, 90, 100],
        data:[lastMonBank],
        backgroundColor: '#ffcc00',
        borderColor: '#ffb700',
        borderWidth: 1,
      },
    ],
  };

  // Options for the bar chart
  const barOptions = {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  };

  return (
    <div style={{ width: '100%', maxWidth: '600px' }}>
      <h3>Transaction Comparison</h3>
      <Bar data={barData} options={barOptions} />
    </div>
  );
};

export default BarChart;
