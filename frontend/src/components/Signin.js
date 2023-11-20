import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Signin() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const navigate = useNavigate();

  const handleSignin = async () => {
    try {
      const response = await axios.post('http://localhost:8090/api/users/signin', {
        username,
        password,
      });

      console.log('User signed in:', response.data);

      // Redirect to dashboard upon successful sign-in
      navigate('/dashboard', { state: { username } }); 

    } catch (error) {
      setError('Signin failed: ' + error.response.data);
      console.error('Signin failed:', error.response.data);

    }
  };

  return (
    <div>
      <h2>Sign In</h2>
      <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
      <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
      <button onClick={handleSignin}>Sign In</button>
      {error && <p>{error}</p>}
    </div>
  );
}

export default Signin;
