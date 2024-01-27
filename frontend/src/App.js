import logo from './logo.svg';
import { Router, Routes, Route, BrowserRouter, Navigate } from 'react-router-dom';
import './App.css';
import Report from './components/Report';
import SportEvents from './components/SportEvents';
import Payment from './components/Payment';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path='/report/:sportEventId' element={<Report />} />
          <Route path='/' element={<SportEvents/>}/>
          <Route path='/payment' element={<Payment />}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
