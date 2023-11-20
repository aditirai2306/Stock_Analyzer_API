import axios from 'axios';
import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';

const Dashboard = () => {
  const location = useLocation();
  const { state } = location;
  const { username } = state || {}; // Access username from location state

  const [ticker, setTicker] = useState('');
  const [date, setDate] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [dailyReturn, setDailyReturn] = useState('');
  const [averagePerformance, setAveragePerformance] = useState('');
  const [showDailyReturn, setShowDailyReturn] = useState(true);
  const [ticker1, setTicker1] = useState('');
  const [ticker2, setTicker2] = useState('');
  const [comparisonResult, setComparisonResult] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [showComparisonInput, setShowComparisonInput] = useState(false);

  const getDailyReturn = async () => {
    try {
      const response = await axios.get(`http://localhost:8090/api/stock-performance/daily-return`, {
        params: {
          ticker: ticker,
          date: date
        }
      });
      setDailyReturn(response.data);
    } catch (error) {
      console.error('Error:', error);
      setDailyReturn('Error fetching data');
    }
  };

  const getAveragePerformance = async () => {
    try {
      const response = await axios.get(`http://localhost:8090/api/stock-performance/average-performance`, {
        params: {
          ticker: ticker,
          startDate: startDate,
          endDate: endDate
        }
      });
      setAveragePerformance(response.data);
    } catch (error) {
      console.error('Error:', error);
      setAveragePerformance('Error fetching data');
    }
  };

  const compareDailyReturn = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`http://localhost:8090/api/stock-comparison/compare-daily-return`, {
        params: {
          ticker1: ticker1,
          ticker2: ticker2,
          startDate: startDate,
          endDate: endDate
        }
      });
      setComparisonResult(response.data);
    } catch (error) {
      setError('Error fetching data');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>Welcome, {username || 'User'}!</h2>
      {showDailyReturn ? (
        <div>
          <h2>Daily Return</h2>
          <label htmlFor="ticker">Ticker:</label>
          <input type="text" id="ticker" value={ticker} onChange={(e) => setTicker(e.target.value)} placeholder="Enter Ticker" />
          <label htmlFor="date">Date:</label>
          <input type="date" id="date" value={date} onChange={(e) => setDate(e.target.value)} />
          <button onClick={getDailyReturn}>Get Daily Return</button>
          <p>{`Daily Return: ${dailyReturn}`}</p>
          <button onClick={() => setShowDailyReturn(false)}>Calculate Average Performance</button>
          <button onClick={() => setShowComparisonInput(true)}>Compare Daily Return</button>
        </div>
      ) : (
        <div>
          <h2>Average Performance</h2>
          <label htmlFor="tickerAvg">Ticker:</label>
          <input type="text" id="tickerAvg" value={ticker} onChange={(e) => setTicker(e.target.value)} placeholder="Enter Ticker" />
          <label htmlFor="startDate">Start Date:</label>
          <input type="date" id="startDate" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
          <label htmlFor="endDate">End Date:</label>
          <input type="date" id="endDate" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
          <button onClick={getAveragePerformance}>Get Average Performance</button>
          <p>{`Average Performance: ${averagePerformance}`}</p>
          <button onClick={() => setShowDailyReturn(true)}>Calculate Daily Return</button>
        </div>
      )}
      {showComparisonInput && (
        <div>
          <h2>Compare Daily Return</h2>
          <label htmlFor="ticker1">Ticker 1:</label>
          <input type="text" id="ticker1" value={ticker1} onChange={(e) => setTicker1(e.target.value)} placeholder="Enter Ticker 1" />
          <label htmlFor="ticker2">Ticker 2:</label>
          <input type="text" id="ticker2" value={ticker2} onChange={(e) => setTicker2(e.target.value)} placeholder="Enter Ticker 2" />
          <label htmlFor="startDate">Start Date:</label>
          <input type="date" id="startDate" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
          <label htmlFor="endDate">End Date:</label>
          <input type="date" id="endDate" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
          <button onClick={compareDailyReturn}>Compare Daily Return</button>
          {/* Display loading and error messages */}
          {loading && <p>Loading...</p>}
          {error && <p>{error}</p>}
          {/* Display comparison results */}
          {Object.keys(comparisonResult).length > 0 && (
            <div>
              <h3>Daily Return Comparison</h3>
              <p>{`Ticker 1 (${ticker1})- Daily Return: ${comparisonResult[ticker1]}`}</p>
              <p>{`Ticker 2 (${ticker2})- Daily Return: ${comparisonResult[ticker2]}`}</p>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default Dashboard;