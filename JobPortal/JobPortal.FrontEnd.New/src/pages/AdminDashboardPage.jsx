import React from 'react';
import { backendUrl } from '../utils/apiBase';
import './AdminDashboardPage.css';

const AdminDashboardPage = () => {
    return (
        <div className="admin-dashboard page-fade">
            <section className="admin-hero">
                <div>
                    <p className="kicker">Admin</p>
                    <h1>Manage the JobMagnet platform.</h1>
                    <p>Review listings, users, and employers from a single view.</p>
                </div>
            </section>

            <section className="admin-grid">
                <div className="admin-card">
                    <h3>Job Listings</h3>
                    <p>Review posted jobs and remove outdated listings.</p>
                    <a href={backendUrl('/AdminViewJobListings.html')}>View job listings</a>
                </div>
                <div className="admin-card">
                    <h3>Job Seekers</h3>
                    <p>Inspect seeker accounts and keep data accurate.</p>
                    <a href={backendUrl('/AdminViewJobSeekers.html')}>View job seekers</a>
                </div>
                <div className="admin-card">
                    <h3>Job Employers</h3>
                    <p>Monitor employer accounts and approvals.</p>
                    <a href={backendUrl('/AdminViewJobEmployers.html')}>View employers</a>
                </div>
            </section>
        </div>
    );
};

export default AdminDashboardPage;
