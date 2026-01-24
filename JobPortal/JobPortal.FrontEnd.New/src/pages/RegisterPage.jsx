import React from 'react';
import { Link } from 'react-router-dom';
import './RegisterPage.css';

/**
 * Renders the main registration page.
 * This page allows users to choose whether to register as a job seeker or an employer.
 * @returns {JSX.Element} The rendered registration page.
 */
const RegisterPage = () => {
    return (
        <div className="register-page">
            <div className="container">
                <div className="left-panel">
                </div>
                <div className="right-panel">
                    <h1>Register</h1>
                    <p>Are you a job seeker or an employer?</p>
                    <div className="register-links">
                        <Link to="/register/job-seeker" className="register-link">Register as a Job Seeker</Link>
                        <Link to="/register/employer" className="register-link">Register as an Employer</Link>
                    </div>
                    <p>Already Registered? <Link to="/login">Sign in</Link></p>
                </div>
            </div>
        </div>
    );
};

export default RegisterPage;
