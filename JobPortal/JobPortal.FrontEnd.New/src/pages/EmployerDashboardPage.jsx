import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { authFetch } from '../utils/authFetch';
import './EmployerDashboardPage.css';

const EmployerDashboardPage = () => {
    const [seekers, setSeekers] = useState([]);
    const [query, setQuery] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    const loadSeekers = async () => {
        try {
            setLoading(true);
            setError('');
            const response = await authFetch('http://localhost:8080/api/jobseekers/getall');
            if (!response.ok) {
                throw new Error('Failed to load seekers');
            }
            const data = await response.json();
            setSeekers(Array.isArray(data) ? data : []);
        } catch (err) {
            setError(err.message || 'Failed to load seekers');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadSeekers();
    }, []);

    const handleSearch = async (event) => {
        event.preventDefault();
        const trimmed = query.trim();
        if (!trimmed) {
            loadSeekers();
            return;
        }
        try {
            setLoading(true);
            setError('');
            const response = await authFetch(
                `http://localhost:8080/api/search-seekers/search/${encodeURIComponent(trimmed)}`
            );
            const data = await response.json();
            if (typeof data === 'string') {
                setSeekers([]);
                setError(data);
            } else {
                setSeekers(Array.isArray(data) ? data : []);
                setError('');
            }
        } catch (err) {
            setError(err.message || 'Search failed');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="employer-dashboard page-fade">
            <section className="employer-hero">
                <div>
                    <p className="kicker">Employer home</p>
                    <h1>Find the right talent, faster.</h1>
                    <p>Search job seekers and build your shortlist.</p>
                </div>
                <form className="employer-search" onSubmit={handleSearch}>
                    <input
                        type="text"
                        placeholder="Search by name, skill, or keyword"
                        value={query}
                        onChange={(event) => setQuery(event.target.value)}
                    />
                    <button type="submit">Search</button>
                </form>
            </section>

            <section className="employer-grid">
                {loading && <p>Loading seekers...</p>}
                {!loading && error && <p className="error-text">{error}</p>}
                {!loading && !seekers.length && !error && <p>No seekers found.</p>}
                {seekers.map((seeker) => (
                    <div className="employer-card" key={seeker.id}>
                        <h3>{`${seeker.fname || ''} ${seeker.lname || ''}`.trim()}</h3>
                        <p>Degree: {seeker.degree || 'Not listed'}</p>
                        <p>Location: {seeker.country || 'Not listed'}</p>
                        <div className="card-actions">
                            <Link to={`/profile/seeker/${seeker.id}`}>Details</Link>
                        </div>
                    </div>
                ))}
            </section>
        </div>
    );
};

export default EmployerDashboardPage;
