import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { authFetch } from '../utils/authFetch';
import './ViewEmployerProfilePage.css';

const ViewEmployerProfilePage = () => {
    const { id } = useParams();
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const loadProfile = async () => {
            try {
                setLoading(true);
                setError('');
                const response = await authFetch(`http://localhost:8080/api/employers/findbyid/${id}`);
                if (!response.ok) {
                    throw new Error('Failed to load employer profile');
                }
                const data = await response.json();
                setProfile(data);
            } catch (err) {
                setError(err.message || 'Failed to load employer profile');
            } finally {
                setLoading(false);
            }
        };

        if (id) {
            loadProfile();
        }
    }, [id]);

    return (
        <div className="profile-page page-fade">
            <section className="profile-hero employer-hero">
                <div>
                    <p className="kicker">Employer profile</p>
                    <h1>{profile?.companyName || 'Company profile'}</h1>
                    <p>Learn about the company and its hiring focus.</p>
                </div>
                <div className="profile-avatar">
                    {profile?.companyLogo ? (
                        <img src={`data:image/png;base64,${profile.companyLogo}`} alt="Company logo" />
                    ) : (
                        <div className="avatar-fallback">CO</div>
                    )}
                </div>
            </section>

            {profile?.bannerImage && (
                <section className="profile-banner">
                    <img src={`data:image/png;base64,${profile.bannerImage}`} alt="Company banner" />
                </section>
            )}

            <section className="profile-content">
                {loading && <p>Loading profile...</p>}
                {!loading && error && <p className="error-text">{error}</p>}
                {!loading && profile && (
                    <div className="profile-grid">
                        <div className="profile-card">
                            <h3>Contact</h3>
                            <p>Email: {profile.email || 'Not listed'}</p>
                            <p>Address: {profile.address || 'Not listed'}</p>
                            <p>Reg No: {profile.govRegNo || 'Not listed'}</p>
                        </div>
                        <div className="profile-card">
                            <h3>Company</h3>
                            <p>Years: {profile.years ?? 'Not listed'}</p>
                            <p>Employees: {profile.no_of_Employees ?? 'Not listed'}</p>
                            <p>Projects: {profile.projectsCompleted ?? 'Not listed'}</p>
                        </div>
                        <div className="profile-card full">
                            <h3>About</h3>
                            <p>{profile.about || 'Not provided'}</p>
                        </div>
                        <div className="profile-card full">
                            <h3>Core values</h3>
                            <p>{profile.coreValues || 'Not provided'}</p>
                        </div>
                    </div>
                )}
            </section>

            <section className="profile-actions">
                <Link to="/messages">Message</Link>
                <Link to="/dashboard">Back to Dashboard</Link>
            </section>
        </div>
    );
};

export default ViewEmployerProfilePage;
