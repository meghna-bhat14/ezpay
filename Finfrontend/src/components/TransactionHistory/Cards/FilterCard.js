// Author: Manvi
// Date: 18-Sept-2024

// Description: 
// The FilterCard component is a dynamic filter panel for transaction records. 
// It allows users to filter transactions by date, amount, description, status, and type using collapsible sections. 
// The component includes icons for improved UI, real-time filter handling, and buttons to apply or reset filters.


import React, { useState } from 'react';
import { FaFilter, FaChevronDown, FaChevronUp } from "react-icons/fa";
import { MdDateRange, MdOutlineCurrencyRupee, MdOutlineDescription } from "react-icons/md";
import { RiProgress6Line } from "react-icons/ri";
import { AiFillBank } from "react-icons/ai";
import '../Styles/FilterCard.css'

const FilterCard = ({ filters, resetFilters, applyFilters, handleSelectChange, handleFilterChange, filterMessage }) => {
  // State to manage the open/close status of each filter section
  const [openSections, setOpenSections] = useState({});

  // Toggles the visibility of each filter section when clicked
  const toggleSection = (section) => {
    setOpenSections(prev => ({ ...prev, [section]: !prev[section] }));
  };

  // Renders an input field for the filters, with collapsible functionality
  const renderFilterInput = (name, type, placeholder, icon) => (
    <div className="filter-section">
      <div 
        className="filter-heading" 
        onClick={() => toggleSection(name)}
      >
        {icon}
        <span>{placeholder}</span>
        {openSections[name] ? <FaChevronUp /> : <FaChevronDown />}
      </div>
      {openSections[name] && (
        <input
          type={type}
          name={name}
          value={filters[name]}
          onChange={handleFilterChange}
          placeholder={placeholder}
          className="filter-input"
        />
      )}
    </div>
  );

  return (
    <div className="filter-container">
      <div className="filter-card">
      <center><h2 className="filter-title"><FaFilter /> Filters</h2></center>
      <div className="filter-divider"></div>
        {renderFilterInput("startDate", "datetime-local", "Start Date", <MdDateRange />)}
        <div className="filter-divider"></div>
        {renderFilterInput("endDate", "datetime-local", "End Date", <MdDateRange />)}
        <div className="filter-divider"></div>
        {renderFilterInput("exactAmount", "text", "Amount", <MdOutlineCurrencyRupee />)}
        <div className="filter-divider"></div>
        {renderFilterInput("minAmount", "text", "Minimum Amount", <MdOutlineCurrencyRupee />)}
        <div className="filter-divider"></div>
        {renderFilterInput("maxAmount", "text", "Maximum Amount", <MdOutlineCurrencyRupee />)}
        <div className="filter-divider"></div>
        {renderFilterInput("label", "text", "Search keyword", <MdOutlineDescription />)}
        <div className="filter-divider"></div>

        <div className="filter-section">
          <div className="filter-heading" onClick={() => toggleSection('status')}>
            <RiProgress6Line />
            <span>Status</span>
            {openSections['status'] ? <FaChevronUp /> : <FaChevronDown />}
          </div>
          {openSections['status'] && (
            <select
              name="status"
              value={filters.status} // Controlled select input
              onChange={(e) => handleSelectChange('status', e.target.value)}
              className="filter-select"
            >
              <option value="">Select Status</option>
              <option value="0">Pending</option>
              <option value="1">Completed</option>
              <option value="2">Failed</option>
              <option value="3">Cancelled</option>
            </select>
          )}
        </div>
        <div className="filter-divider"></div>
        <div className="filter-section">
          <div className="filter-heading" onClick={() => toggleSection('type')}>
            <AiFillBank />
            <span>Type</span>
            {openSections['type'] ? <FaChevronUp /> : <FaChevronDown />}
          </div>
          {openSections['type'] && (
            <select
              name="type"
              value={filters.type} // Controlled select input for transaction type
              onChange={(e) => handleSelectChange('type', e.target.value)}
              className="filter-select"
            >
              <option value="">Select Type</option>
              <option value="BANK">Bank</option>
              <option value="UPI">UPI</option>
            </select>
          )}
        </div>
        <div className="filter-divider"></div>
        <button onClick={applyFilters} className="filter-button apply-button">
          Apply Filters
        </button>
        <button onClick={resetFilters} className="filter-button reset-button">
          Reset Filters
        </button>
        
        {filterMessage && (
          <div className="filter-message">{filterMessage}</div>
        )}
      </div>
    </div>
  );
};

export default FilterCard;



