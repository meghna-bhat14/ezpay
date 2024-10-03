import "./styles/Header.css";
// import ezpayLogo from "../assets/EZPAY-LOGO.png";
import ezpayLogo from "../../assets/EZPAY-LOGO.png"
import profileImage from "../../assets/image.png";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";


const Header = ({title}) => {
    const [isDropdownVisible, setIsDropdownVisible] = useState(false);
  
    const toggleDropdown = () => {
      setIsDropdownVisible(!isDropdownVisible);
    };
  
    const closeDropdown = (e) => {
      // Close dropdown if the click is outside the profile container
      if (!document.querySelector('.profile-container').contains(e.target)) {
        setIsDropdownVisible(false);
      }
    };

  
    useEffect(() => {
      // Add a global click listener to detect clicks outside the dropdown
      document.addEventListener('click', closeDropdown);
      return () => {
        document.removeEventListener('click', closeDropdown);
      };
    }, []);
  
    return (
      <header className="header">
        <div className="logo-container">
         <Link to="/"><img src={ezpayLogo} alt="Logo" className="logo-image" /></Link> 
          <Link to="/"><div className="logo-text">
           EZPAY
          </div>
          </Link> 
        </div>
  
        <div className="dashboard-title">{title}</div>
  
        <div
          className="profile-container"
          onClick={toggleDropdown}
          onMouseEnter={() => setIsDropdownVisible(true)}
          onMouseLeave={() => !isDropdownVisible && setIsDropdownVisible(false)}
        >
          <img src={profileImage} alt="Profile" className="profile-image" />
          <span className="profile-text">Profile</span>
  
          {/* Show dropdown when visible */}
          {isDropdownVisible && (
            <div className="profile-dropdown">
              <ul>
                <li><Link to="/editprofile">Edit Profile</Link> </li>
                <li>Logout</li>
              </ul>
            </div>
          )}
        </div>
      </header>
    );
  };
  
  export default Header;