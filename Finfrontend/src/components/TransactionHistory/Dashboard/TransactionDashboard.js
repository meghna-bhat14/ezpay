import React, { useState, useEffect } from 'react';
import TransactionCard from '../Cards/TransactionCard';
import FilterCard from '../Cards/FilterCard';

import { useNavigate } from 'react-router-dom';
import Header from '../../Header/Header';
import Footer from '../../Footer/Footer';

const API_BASE_URL = 'http://localhost:9020/transactions';
const ITEMS_PER_PAGE = 8;

const TransactionDashboard = () => {
  const navigate = useNavigate();
  const [transactions, setTransactions] = useState([]);
  const [filteredTransactions, setFilteredTransactions] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [filterMessage, setFilterMessage] = useState('');
  const [customerId, setCustomerId] = useState('');
  const [filters, setFilters] = useState({
    startDate: '',
    endDate: '',
    exactAmount: '',
    minAmount: '',
    maxAmount: '',
    status: '',
    type: '',
    label: ''
  });
  const [loginFailed, setLoginFailed] = useState(false);

  useEffect(() => {
    if (!localStorage.getItem('customer_id')) { 
      setLoginFailed(true);
      return;
    }
    
    const savedCustomerId = localStorage.getItem('customer_id');
    setCustomerId(savedCustomerId);

    const savedFilters = localStorage.getItem('filters');
    const savedTransactions = localStorage.getItem('transactions');
    const savedCurrentPage = localStorage.getItem('currentPage');

    if (savedFilters && savedTransactions) {
      setFilters(JSON.parse(savedFilters));
      setTransactions(JSON.parse(savedTransactions));
      setCurrentPage(JSON.parse(savedCurrentPage) || 1);
    } else {
      fetchTransactions(savedCustomerId);
    }
  }, []);
 
  useEffect(() => {
    const filteredTx = filterCompletedCreditTransactions(transactions);
    setFilteredTransactions(filteredTx);
  }, [transactions, customerId]);

  const fetchTransactions = async (userId, url = `${API_BASE_URL}/all`) => {
    try {
      const userSpecificUrl = `${url}?customerId=${userId}`;
      const response = await fetch(userSpecificUrl);
      const data = await response.json();
      setTransactions(data);
    } catch (error) {
      console.error('Error fetching transactions:', error);
    }
  };

  useEffect(() => {
    if (loginFailed) {
      navigate('/LoginError', {
        state: {
          message: "You are not logged in. Please log in to view transactions."
        }
      });
    }
  }, [loginFailed, navigate]);
 
  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters(prevFilters => ({ ...prevFilters, [name]: value }));
  };

  const handleSelectChange = (name, value) => {
    setFilters(prevFilters => ({ ...prevFilters, [name]: value }));
  };

  // Validate filter inputs before applying filters
  const validateFilters = () => {
    const { startDate, endDate, exactAmount, minAmount, maxAmount } = filters;

    if (startDate && endDate && new Date(startDate) > new Date(endDate)) {
      setFilterMessage('Start date must be before end date.');
      return false;
    }
    if (exactAmount && (isNaN(exactAmount) || exactAmount <= 0)) {
      setFilterMessage('Exact amount must be a positive number.');
      return false;
    }
    if (minAmount && (isNaN(minAmount) || minAmount < 0)) {
      setFilterMessage('Minimum amount must be a non-negative number.');
      return false;
    }
    if (maxAmount && (isNaN(maxAmount) || maxAmount < 0)) {
      setFilterMessage('Maximum amount must be a non-negative number.');
      return false;
    }
    if (minAmount && maxAmount && Number(minAmount) > Number(maxAmount)) {
      setFilterMessage('Minimum amount must be less than or equal to maximum amount.');
      return false;
    }
    setFilterMessage('');
    return true;
  };


  const applyFilters = async () => {
    if (!validateFilters()) return;

    const fetchPromises = [];
    let filterUrls = [];

    if (filters.startDate && filters.endDate) {
      filterUrls.push(`${API_BASE_URL}/dateRange?start=${filters.startDate}&end=${filters.endDate}&customerId=${customerId}`);
    }
    if (filters.exactAmount) {
      filterUrls.push(`${API_BASE_URL}/amount/exact?amount=${filters.exactAmount}&customerId=${customerId}`);
    }
    if (filters.minAmount) {
      filterUrls.push(`${API_BASE_URL}/amount/min?minAmount=${filters.minAmount}&customerId=${customerId}`);
    }
    if (filters.maxAmount) {
      filterUrls.push(`${API_BASE_URL}/amount/max?maxAmount=${filters.maxAmount}&customerId=${customerId}`);
    }
    if (filters.status) {
      filterUrls.push(`${API_BASE_URL}/status?status=${filters.status}&customerId=${customerId}`);
    }
    if (filters.type) {
      filterUrls.push(`${API_BASE_URL}/type/${filters.type}?customerId=${customerId}`);
    }
    if (filters.label) {
      filterUrls.push(`${API_BASE_URL}/description?keyword=${filters.label}&customerId=${customerId}`);
    }

    filterUrls.forEach(url => {
      // Append the user ID to each filter URL
      // const userSpecificUrl = ${url}&customerId=${customerId};
      fetchPromises.push(fetch(url).then(res => res.json()));
    });


    const results = await Promise.all(fetchPromises);

    if (results.length > 0) {
      let intersectedTransactions = results[0];
      for (let i = 1; i < results.length; i++) {
        const currentFilterResult = results[i];
        intersectedTransactions = intersectedTransactions.filter(tx =>
          currentFilterResult.some(filteredTx => (filteredTx.paymentId === tx.paymentId && filteredTx.transferId === tx.transferId))
        );
      }

      setTransactions(intersectedTransactions);
      setCurrentPage(1);
      localStorage.setItem('filters', JSON.stringify(filters));
      localStorage.setItem('transactions', JSON.stringify(intersectedTransactions));
      localStorage.setItem('currentPage', JSON.stringify(1));
    } else {
      setFilterMessage('No filters added. Please add at least one filter.');
    }
  };

  const resetFilters = () => {
    setFilters({
      startDate: '',
      endDate: '',
      exactAmount: '',
      minAmount: '',
      maxAmount: '',
      status: '',
      type: '',
      label: ''
    });
    setFilterMessage('');
    fetchTransactions(customerId);
    setCurrentPage(1);
    localStorage.removeItem('filters');
    localStorage.removeItem('transactions');
    localStorage.removeItem('currentPage');
  };

  const filterCompletedCreditTransactions = (transactions) => {
    return transactions.filter(transaction => {
      if (customerId === transaction.sender.customerId.toString()) {
        return true; // Show all debit transactions
      } else {
        return transaction.status === 1; // For credit transactions, only show if status is Completed (1)
      }
    });
  };

  const getPaginatedData = () => {
    const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
    const endIndex = startIndex + ITEMS_PER_PAGE;
    return filteredTransactions.slice(startIndex, endIndex);
  };

  const totalPages = Math.ceil(filteredTransactions.length / ITEMS_PER_PAGE);

  return (
    
    <>
    <Header title="Transaction History"/>
    
    <div style={{backgroundColor: '#FFFFFF'}}>
      <Header/>
      <div style={{ display: 'flex', backgroundColor: '#FFFFFF', justifyContent: 'space-around',}}>
        <FilterCard 
          filters={filters}
          resetFilters={resetFilters} 
          applyFilters={applyFilters} 
          handleSelectChange={handleSelectChange} 
          handleFilterChange={handleFilterChange}
          filterMessage={filterMessage}
        />
        <TransactionCard 
          transactions={getPaginatedData()} 
          currentPage={currentPage}
          totalPages={totalPages}
          setCurrentPage={setCurrentPage} 
          customerId={customerId}
        />
      </div>
      <Footer/>
    </div>
    </>
  );
}

export default TransactionDashboard;