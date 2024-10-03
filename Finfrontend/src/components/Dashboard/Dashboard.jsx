import React, {  useState } from 'react';
// import '../styles/Services.css'; // Import the CSS for styling
import "./styles/Dashboard.css"
import Calendar from 'react-calendar';

import PieChart from './PieChart';
import FinancialTips from './FinancialTips';
import checkbal from "../../assets/checkbalance.jpg";
import upiimg from "../../assets/upitransaction.avif";
import bankimg from '../../assets/banktransaction.jpg';
import transactionhistory from '../../assets/transactionhistory.jpg';
import activateBank from '../../assets/ActivateBank.png';

import BarGraphComponent from './TransactionBarChart';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
import { Link } from 'react-router-dom';




const servicesData = [
  {
    title: "Activate Account",
    description: "Activate your Bank Account.",
    imageUrl: activateBank // Replace with actual image
  },
    {
      title: "Check Balance",
      description: "View your current account balance.",
      imageUrl: checkbal // Replace with actual image
    },
    {
      title: "Pay via UPI",
      description: "Instantly transfer money using a unique UPI ID.",
      imageUrl: upiimg
    },
    {
      title: "Bank Payment",
      description: "Securely send funds directly to bank accounts.",
      imageUrl: bankimg
    },
    {
      title: "Transaction History",
      description: "Access a detailed log of your past financial activities.",
      imageUrl: transactionhistory
    },
  ];
  
  // const tipsData = [
  //   "Tip 1: Save 10% of your income every month.",
  //   "Tip 2: Set up automatic transfers to your savings account.",
  //   "Tip 3: Avoid impulse buying by sticking to a budget.",
  // ];
  const Dashboard = () => {
    
    

    const [date, setDate] = useState(new Date()); // State to track selected date for calander
  
   

    
  
    return (<>
    <Header title="DashBoard"/>

<div className="main-container">
        
        <div className="services-container">
          <center><h2 style={{fontSize:30}}>Our Services</h2></center>
          <div className="services-grid">
            {servicesData.map((service, index) => (
              <div className="service-card" key={index}>
                {/* <img src={service.imageUrl} alt={service.title} className="service-image" /> */}
                {/* <h3>{service.title}</h3> */}


                {service.title==="Activate Account"?<Link to="/activate">
                  <img src={service.imageUrl} alt={service.title} className="service-image" />
                <h3>Activate Account</h3>
                <p>{service.description}</p></Link>:null}


                {service.title==="Check Balance"?<Link to="/checkbalance">
                  <img src={service.imageUrl} alt={service.title} className="service-image" />
                <h3>Check Balance</h3>
                <p>{service.description}</p></Link>:null}

                {service.title==="Pay via UPI"?<Link to="/upipayment">
                  <img src={service.imageUrl} alt={service.title} className="service-image" />
                <h3>{service.title}</h3>
                <p>{service.description}</p></Link>:null}
                
                {service.title==="Bank Payment"?<Link to="/bank-transaction">
                  <img src={service.imageUrl} alt={service.title} className="service-image" />
                <h3>{service.title}</h3>
                <p>{service.description}</p></Link>:null}

                {service.title==="Transaction History"?<Link to="/transactionhistory">
                  <img src={service.imageUrl} alt={service.title} className="service-image" />
                <h3>{service.title}</h3>
                <p>{service.description}</p></Link>:null}
                {/* <button className="details-button">Click</button> */}
              </div>
            ))}
          </div>
        </div>
  
        <div className="visualAndTips-container">
          
            <div>
             
              <center><h2 style={{fontSize:30}}>Statistics</h2></center>
            </div>
          <div className="chart-container">
            {/* <h3>Transaction Comparison</h3> */}
            {/* <Pie data={pieData} /> */}
            <PieChart/>
            
           
          </div>
          <div className="chart-container">
            
            <BarGraphComponent/>
          
            {/* <TransactionBarChart/> */}
           
          </div>
          <div className="calendar-container">
            <h3 className="calendar-heading">Calendar</h3>
            <Calendar
              onChange={setDate}
              value={date}
              tileClassName={({ date }) => {
                if (date.toDateString() === new Date().toDateString()) {
                  return 'highlight-today'; // Highlight today's date
                }
                return null;
              }}
            />
          </div>
  
          <div className="tips-container">
            <h3>Financial Tips</h3>
            <FinancialTips/>
          </div>
        </div>
      </div>
      <Footer/>
    
    </>
      
    );
  };
  
  export default Dashboard;