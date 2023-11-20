import axios from 'axios';
import React, { useState } from 'react';

function Signup() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSignup = async () => {
    try {
      const response = await axios.post('http://localhost:8090/api/users/signup', {
        username,
        email,
        password,
      });
      console.log('User signed up:', response.data);
      setSuccessMessage('User Registration Successful');
      setErrorMessage('');
    } catch (error) {
      console.error('Signup failed:', error.response.data);
      if (error.response.status === 409) {
        setErrorMessage('Username or email already exists');
      } else {
        setErrorMessage('Signup failed: ' + error.response.data);
      }
      setSuccessMessage('');
    }
  };

  return (
    <div>
      <h2>Sign Up</h2>
      <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
      <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
      <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
      <button onClick={handleSignup}>Sign Up</button>
      {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
    </div>
  );
}

export default Signup;