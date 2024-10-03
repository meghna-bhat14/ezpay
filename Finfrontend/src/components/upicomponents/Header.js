import './Services.css';
export default function Header(){
    return(
        <>
        
{/* <!--header section start --> */}
    {/* <div className="header_section header_bg">
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="logo"><img src='/images/img-1.png' alt="Logo" /></div>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav mr-auto">
                <li className="nav-item">
                    <a className="nav-link" href='/activate'>Bank</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href='/upi-payment'>UPI </a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href="services.html">Services</a>
                </li>
            </ul>
        </div>
    </nav>
</div> */}


    {/* <!--header section end --> */}

     {/* Header Section Start */}
     {/* <div className="header_section header_bg"> */}
                {/* <nav className="navbar">
                    <div className="logo"><img src='/images/img-1.png' alt="Logo" /></div>
                    <div className="navbar-items">
                        <a className="nav-link" href='/activate'>Bank</a>
                        <a className="nav-link" href='/upi-payment'>UPI</a>
                        <a className="nav-link" href="services.html">Services</a>
                    </div>
                </nav>
            </div> */}

<div className="custom-header">
                <nav className="custom-navbar">
                    <div className="custom-logo">
                        <img src='/images/round-1.png' alt="Logo" />
                    </div>
                    <div className="custom-navbar-items">
                        <a className="custom-nav-link" href='/activate'>Bank Activation</a>
                        <a className="custom-nav-link" href='/upi-payment'>UPI Transaction</a>
                        <a className="custom-nav-link" href="services.html">Bank Transaction</a>
                    </div>
                </nav>
            </div>

        </>
    )
}