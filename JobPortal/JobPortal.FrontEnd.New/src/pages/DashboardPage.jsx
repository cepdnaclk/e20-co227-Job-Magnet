import React from 'react';
import { useAuth } from '../contexts/AuthContext';
import './DashboardPage.css';

/**
 * Renders the user's dashboard page.
 * It displays a welcome message and a logout button.
 * @returns {JSX.Element} The rendered dashboard page.
 */
const DashboardPage = () => {
    const { user, logout } = useAuth();

    return (
        <div className="dashboard-page page-fade">
            <section className="dashboard-hero">
                <div>
                    <p className="kicker">Your workspace</p>
                    <h1>Welcome back{user?.email ? `, ${user.email}` : ''}.</h1>
                    <p>Track your activity, manage your profile, and plan your next move.</p>
                </div>
                <button className="dashboard-logout" onClick={logout}>Logout</button>
            </section>

            <section className="dashboard-grid">
                <div className="dashboard-card">
                    <h3>Applications</h3>
                    <p>0 active applications</p>
                    <button>Browse roles</button>
                </div>
                <div className="dashboard-card">
                    <h3>Messages</h3>
                    <p>No unread messages</p>
                    <button>Open inbox</button>
                </div>
                <div className="dashboard-card">
                    <h3>Profile strength</h3>
                    <p>Complete your profile to get matched faster.</p>
                    <button>Update profile</button>
                </div>
            </section>
        </div>
    );
};

export default DashboardPage;
