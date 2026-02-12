import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { authFetch } from '../utils/authFetch';
import { apiUrl } from '../utils/apiBase';
import './ViewSeekerProfilePage.css';

const ViewSeekerProfilePage = () => {
    const { id } = useParams();
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const loadProfile = async () => {
            try {
                setLoading(true);
                setError('');
                const response = await authFetch(apiUrl(`/api/jobseekers/searchbyid/${id}`));
                if (!response.ok) {
                    throw new Error('Failed to load seeker profile');
                }
                const data = await response.json();
                setProfile(data);
            } catch (err) {
                setError(err.message || 'Failed to load seeker profile');
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
            <section className="profile-hero seeker-hero">
                <div>
                    <p className="kicker">Seeker profile</p>
                    <h1>{profile ? `${profile.fname || ''} ${profile.lname || ''}`.trim() : 'Profile'}</h1>
                    <p>Review skills, background, and contact details.</p>
                </div>
                <div className="profile-avatar">
                    {profile?.profilePicture ? (
                        <img src={`data:image/png;base64,${profile.profilePicture}`} alt="Seeker avatar" />
                    ) : (
                        <div className="avatar-fallback">JS</div>
                    )}
                </div>
            </section>

            <section className="profile-content">
                {loading && <p>Loading profile...</p>}
                {!loading && error && <p className="error-text">{error}</p>}
                {!loading && profile && (
                    <div className="profile-grid">
                        <div className="profile-card">
                            <h3>Contact</h3>
                            <p>Email: {profile.email || 'Not listed'}</p>
                            <p>Phone: {profile.phone || 'Not listed'}</p>
                            <p>Country: {profile.country || 'Not listed'}</p>
                        </div>
                        <div className="profile-card">
                            <h3>Education</h3>
                            <p>University: {profile.university || 'Not listed'}</p>
                            <p>Degree: {profile.degree || 'Not listed'}</p>
                            <p>Class: {profile.dclass || 'Not listed'}</p>
                        </div>
                        <div className="profile-card">
                            <h3>Experience</h3>
                            <p>Experience: {profile.experience || 'Not listed'}</p>
                            <p>Skills: {profile.skills || 'Not listed'}</p>
                            <p>Projects: {profile.projects || 'Not listed'}</p>
                        </div>
                        <div className="profile-card full">
                            <h3>About</h3>
                            <p>{profile.about || profile.bio || 'Not provided'}</p>
                        </div>
                    </div>
                )}
            </section>

            <section className="profile-actions">
                {profile?.email ? (
                    <Link to={`/messages?recipient=${encodeURIComponent(profile.email)}`}>Message</Link>
                ) : (
                    <Link to="/messages">Message</Link>
                )}
                <Link to="/dashboard">Back to Dashboard</Link>
            </section>
        </div>
    );
};

export default ViewSeekerProfilePage;
