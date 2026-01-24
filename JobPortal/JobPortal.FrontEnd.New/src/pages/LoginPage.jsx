import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { authFetch } from '../utils/authFetch';
import './LoginPage.css';

/**
 * Renders the login page.
 * This component provides a form for users to log in.
 * @returns {JSX.Element} The rendered login page.
 */
const LoginPage = () => {
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    });
    const { login } = useAuth();
    const navigate = useNavigate();

    /**
     * Handles changes in the form fields and updates the state.
     * @param {React.ChangeEvent<HTMLInputElement>} e - The change event.
     */
    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.id]: e.target.value });
    };

    /**
     * Handles the form submission for user login.
     * @param {React.FormEvent<HTMLFormElement>} e - The form submission event.
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await authFetch('http://localhost:8080/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                const data = await response.json();
                login(data);
                navigate('/dashboard');
            } else {
                console.error('Login failed');
                // Handle failed login
            }
        } catch (error) {
            console.error('An error occurred:', error);
        }
    };

    return (
        <div className="login-page">
            <div className="wrapper">
                <div className="title">
                    Login
                </div>
                <hr className="custom-hr" />
                <form onSubmit={handleSubmit}>
                    <div className="field">
                        <input type="email" id="email" value={formData.email} onChange={handleChange} required />
                        <label>Email Address</label>
                    </div>
                    <div className="field">
                        <input type="password" id="password" value={formData.password} onChange={handleChange} required />
                        <label>Password</label>
                    </div>
                    <div className="field">
                        <input type="submit" value="Login" />
                    </div>
                    <div className="content">
                        <div className="pass-link">
                            <Link to="/reset-password">Forgot password?</Link>
                        </div>
                    </div>
                    <div className="signup-link">
                        Not a member? <Link to="/register">Signup now</Link>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default LoginPage;
