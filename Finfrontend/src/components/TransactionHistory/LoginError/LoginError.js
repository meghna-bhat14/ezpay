import React from "react";
import "./LoginError.css";
import { useLocation } from "react-router-dom";
import Header from "../../Header/Header";
import Footer from "../../Footer/Footer";

const LoginError = () => {
  const location = useLocation();
  const message =
    location.state?.message || "An error occurred. Please try again.";

  return (
    <>
      <Header />
      <div className="login-error-container">
        <div className="login-error">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="error-icon"
            viewBox="0 0 24 24"
          >
            <path fill="none" d="M0 0h24v24H0z" />
            <path
              fill="currentColor"
              d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm-1-13h2v6h-2zm0 8h2v2h-2z"
            />
          </svg>
          <span className="error-message">{message}</span>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default LoginError;

