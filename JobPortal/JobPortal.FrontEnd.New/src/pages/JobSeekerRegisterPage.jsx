import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { authFetch } from '../utils/authFetch';
import './RegisterPage.css';

/**
 * Renders the job seeker registration page.
 * This component provides a form for job seekers to register.
 * @returns {JSX.Element} The rendered job seeker registration page.
 */
const JobSeekerRegisterPage = () => {
    const [formData, setFormData] = useState({
        fname: '',
        lname: '',
        email: '',
        dob: '',
        password: '',
        experience: '',
        skills: '',
        bio: '',
        phone: '',
        about: ''
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
     * Handles the form submission for job seeker registration.
     * @param {React.FormEvent<HTMLFormElement>} e - The form submission event.
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (isSubmitting) {
            return;
        }
        setIsSubmitting(true);
        try {
            const response = await authFetch('http://localhost:8080/api/jobseekers/registration', {
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
                                    <td>First Name:</td>
                                    <td><input type="text" id="fname" value={formData.fname} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>Last Name:</td>
                                    <td><input type="text" id="lname" value={formData.lname} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>Email:</td>
                                    <td><input type="email" id="email" value={formData.email} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>Date of Birth:</td>
                                    <td><input type="date" id="dob" value={formData.dob} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>Password:</td>
                                    <td><input type="password" id="password" value={formData.password} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>Experience:</td>
                                    <td><input type="text" id="experience" value={formData.experience} onChange={handleChange} /></td>
                                </tr>
                                <tr>
                                    <td>Skills:</td>
                                    <td><input type="text" id="skills" value={formData.skills} onChange={handleChange} /></td>
                                </tr>
                                <tr>
                                    <td>Bio:</td>
                                    <td><input type="text" id="bio" value={formData.bio} onChange={handleChange} /></td>
                                </tr>
                                <tr>
                                    <td>Phone:</td>
                                    <td><input type="text" id="phone" value={formData.phone} onChange={handleChange} required /></td>
                                </tr>
                                <tr>
                                    <td>About:</td>
                                    <td><input type="text" id="about" value={formData.about} onChange={handleChange} /></td>
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
                    <p>Employer? <Link to="/register/employer">Register</Link></p>
                </div>
            </div>
        </div>
    );
};

export default JobSeekerRegisterPage;
