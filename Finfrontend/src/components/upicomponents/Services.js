import { useNavigate } from 'react-router-dom';
import './Services.css';
import Header from './Header';

export function Services() {
    const navigate = useNavigate();
    
    const handleActivationClick = () => {
        navigate('/activate');
    };

    const handleUpiPaymentClick = () => {
        navigate('/upi-payment');
    };
    
    // const handleTestClick=()=>{
    //     navigate('/meghna')
    // }

    // const handlePaymentResultClick=()=>{
    //     navigate('/payment-result')
    // }


    return (
        <>
            <Header/>
         <div className="what_we_do_section layout_padding">
      <div className="container">
        <h1 className="what_taital">OUR SERVICES</h1>
        <p className="what_text">Bank Smarter, Not Harder with our secure, swift and seamless banking services.</p>
        <div className="what_we_do_section_2">
            <div className="row">
                <div className="col-lg-3 col-sm-6">
                    <div className="box_main" onClick={handleActivationClick} style={{ cursor: 'pointer' }}>
                        <div className="icon_1">
                            <img src='/images/icon-1.png' alt="bank activation" />

                        </div>
                        <h3 className="accounting_text">BANK ACTIVATION</h3>
                        <p className="lorem_text">Activate your bank account seamlessly to start using UPI and bank services</p>
                        {/* <div class="moremore_bt_1"><a href="#">Click Me </a></div> */}
                    </div>
                </div>
                <div className="col-lg-3 col-sm-6">
                    <div className="box_main" onClick={handleUpiPaymentClick} style={{ cursor: 'pointer' }}>
                        <div className="icon_1">
                            <img src='/images/icon-4.png' alt="upi payment" />
                        </div>
                        <h3 className="accounting_text">UPI TRANSACTION</h3>
                        <p className="lorem_text"> Effortlessly make UPI transactions anytime, anywhere with just one click.</p>
                        {/* <div class="moremore_bt_1"><a href="#">Click Me </a></div> */}
                    </div>
                </div>
                        <div className="col-lg-3 col-sm-6">
                    <div className="box_main" style={{ cursor: 'pointer' }}>
                        <div className="icon_1">
                            <img src='/images/icon-4.png' alt="upi payment" />
                        </div>
                        <h3 className="accounting_text">Bank Transaction</h3>
                        <p className="lorem_text"> Effortlessly make Bank transactions anytime, anywhere with just one click.</p>
                
                    </div>
                </div>

                {/* <div className="col-lg-3 col-sm-6">
                    <div className="box_main" onClick={handlePaymentResultClick} style={{ cursor: 'pointer' }}>
                        <div className="icon_1">
                            <img src='/images/icon-4.png' alt="upi payment" />
                        </div>
                        <h3 className="accounting_text">Payment Result</h3>
                        <p className="lorem_text"> check status</p>
                        {/* <div class="moremore_bt_1"><a href="#">Click Me </a></div>
                    </div>
                </div> */}

            </div>
            </div>
            </div>
            </div>
        </>
    );
}
