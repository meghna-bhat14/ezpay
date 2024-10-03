
import './App.css';
// import Header from './components/Header';
import Dashboard from './components/Dashboard/Dashboard';
// import Footer from './components/Footer';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

// import UpiPayment  from "./components/upicomponents/UpiPayment";
import {PaymentResult} from './components/upicomponents/PaymentResult';
// import UserBalance from './components/UserBalance';
import CheckBalance from './components/upicomponents/CheckBalance';
import BankPayment from './components/Dashboard/BankPayment';
import TransactionHistory from './components/Dashboard/TransactionHistory';
import EditProfile from './components/Dashboard/EditProfile';
import BalancePage from './components/upicomponents/BalancePage';
import ActivateBankAccount from './components/ActivateBankAccount/ActivateBankAccount';



// After Integration with uc2
// import ActivateBankAccount from './components/ActivateBankAccount/ActivateBankAccount';
import UpiPayment from './components/UpiPayment/UpiPayment';
import BankTransaction from './components/BankTransaction/BankTransaction';
import BankTransactionResult from './components/BankTransaction/BankTransactionResult'; 
//import  PaymentResult from './components/UpiPayment/PaymentResult';  //  -----> Issue Encountered
// import CheckUserBalance from './components/UpiPayment/CheckUserBalance'; ----> Done used earlier check balance

// import Footer from './components/Footer/Footer'; ---->Already added
import TransactionDetail from './components/TransactionHistory/Cards/TransactionDetail';
import LoginError from './components/TransactionHistory/LoginError/LoginError';
import TransactionDashboard from './components/TransactionHistory/Dashboard/TransactionDashboard';




function App() {
  
  const routerConfig =createBrowserRouter([
    {"path":"/",element: <Dashboard></Dashboard>},
    {"path":"/upipayment",element:<UpiPayment></UpiPayment>},
    {"path":"/payment-result",element:<PaymentResult></PaymentResult>},
    // {"path":"/bankpayment",element:<BankPayment></BankPayment>},
    // {"path":"/checkbalance",element: <UserBalance></UserBalance>},
    {"path":"/checkbalance",element:<CheckBalance/>},
    {"path":"/balance", element:<BalancePage />},
    {"path":"/trasactionhistory",element: <TransactionHistory></TransactionHistory>},
    {"path":"/editprofile",element: <EditProfile></EditProfile>},
    {
      path: '/activate',
      element: <ActivateBankAccount/>,
  },
  {
    path: '/bank-transaction',
    element: <BankTransaction />,
},{
  path: '/bank-transaction-result',
  element: <BankTransactionResult />,
},
{
  path: "/transactionhistory",
  element: <TransactionDashboard />
},
{
  path:"/transaction/:id",
  element: <TransactionDetail />
},  
{ 
  path:"/LoginError",
  element: <LoginError /> 
}


])
  return (<>
  

 <RouterProvider router={routerConfig}></RouterProvider>
 
  </>
   
  );
}

export default App;
