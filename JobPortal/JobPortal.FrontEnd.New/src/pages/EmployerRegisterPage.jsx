import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { authFetch } from '../utils/authFetch';
import { apiUrl } from '../utils/apiBase';
import './RegisterPage.css';

/**
 * Renders the employer registration page.
 * This component provides a form for employers to register.
 * @returns {JSX.Element} The rendered employer registration page.
 */
const EmployerRegisterPage = () => {
    const [formData, setFormData] = useState({
        companyName: '',
        address: '',
        email: '',
        govRegNo: '',
        password: ''
    });
    const [isSubmitting, setIsSubmitting] = useState(false);

    /**
     * Handles changes in the form fields and updates the state.
     * @param {React.ChangeEvent<HTMLInputElement>} e - The change event.
     */
    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.id]: e.target.value });
    };

    /**
     * Handles the form submission for employer registration.
     * @param {React.FormEvent<HTMLFormElement>} e - The form submission event.
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (isSubmitting) {
            return;
        }
        setIsSubmitting(true);
        try {
            const response = await authFetch(apiUrl('/api/employers/registration'), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                console.log('Registration successful');
                // Redirect or show success message
            } else {
                console.error('Registration failed');
                // Handle error
            }
        } catch (error) {
            console.error('An error occurred:', error);
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="register-page">
            <div className="container">
                <div className="left-panel">
                </div>
                <div className="right-panel">
                    <form onSubmit={handleSubmit}>
                        <table>
                            <tbody>
                                <tr>
                                    <td>Company Name:</td>
                                    <td><input type="text" id="companyName" value={formData.companyName} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>Address:</td>
                                    <td><input type="text" id="address" value={formData.address} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>Email:</td>
                                    <td><input type="email" id="email" value={formData.email} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>Gov. Reg. No:</td>
                                    <td><input type="text" id="govRegNo" value={formData.govRegNo} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>Password:</td>
                                    <td><input type="password" id="password" value={formData.password} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td colSpan="2">
                                        <input type="submit" value={isSubmitting ? 'Registering...' : 'Register'} className="register" disabled={isSubmitting} />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                    <p>Already Registered? <Link to="/login">Sign in</Link></p>
                    <p>Job Seeker? <Link to="/register/job-seeker">Register</Link></p>
                </div>
            </div>
        </div>
    );
};

export default EmployerRegisterPage;
