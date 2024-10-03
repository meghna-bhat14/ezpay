// Author: Manvi
// Date: 18-Sept-2024

// Description: 
// The FilterInput component is a reusable input element designed for handling various filter inputs in the FilterCard component.
// It supports multiple types of inputs, and its value and onChange handler are passed via props to ensure dynamic behavior.
// The component also applies a custom CSS class for styling, which is defined in the 'FilterCard.css' file.


import React from 'react';
import '../Styles/FilterCard.css';

function FilterInput({ type, name, value, onChange, placeholder }) {
    return (
        <input
            type={type}
            name={name}
            value={value}
            onChange={onChange}
            placeholder={placeholder}
            className="filter-input"
        />
    );
}

export default FilterInput;