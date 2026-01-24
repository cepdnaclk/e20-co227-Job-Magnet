import React, { useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import { authFetch } from '../utils/authFetch';
import './SeekerDashboardPage.css';

const SeekerDashboardPage = () => {
    const [jobs, setJobs] = useState([]);
    const [query, setQuery] = useState('');
    const [searchOption, setSearchOption] = useState('type');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [companyCache, setCompanyCache] = useState({});

    const loadJobs = async () => {
        try {
            setLoading(true);
            setError('');
            const response = await authFetch('http://localhost:8080/api/seekers/search-jobs/all');
            if (!response.ok) {
                throw new Error('Failed to load jobs');
            }
            const data = await response.json();
            setJobs(Array.isArray(data) ? data : []);
        } catch (err) {
            setError(err.message || 'Failed to load jobs');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadJobs();
    }, []);

    useEffect(() => {
        const fetchCompany = async (companyId) => {
            if (!companyId || companyCache[companyId]) {
                return;
            }
            try {
                const response = await authFetch(`http://localhost:8080/api/employers/findbyid/${companyId}`);
                if (!response.ok) {
                    return;
                }
                const data = await response.json();
                setCompanyCache((prev) => ({ ...prev, [companyId]: data }));
            } catch {
                return;
            }
        };

        jobs.forEach((job) => fetchCompany(job.companyId));
    }, [jobs, companyCache]);

    const handleSearch = async (event) => {
        event.preventDefault();
        const trimmed = query.trim();
        if (!trimmed) {
            loadJobs();
            return;
        }
        try {
            setLoading(true);
            const response = await authFetch(
                `http://localhost:8080/api/seekers/search-jobs/search?search_option=${encodeURIComponent(searchOption)}&query=${encodeURIComponent(trimmed)}`
            );
            const data = await response.json();
            setJobs(Array.isArray(data) ? data : []);
            setError('');
        } catch (err) {
            setError(err.message || 'Search failed');
        } finally {
            setLoading(false);
        }
    };

    const jobCards = useMemo(() => {
        return jobs.map((job) => {
            const company = companyCache[job.companyId] || {};
            return {
                ...job,
                companyName: company.companyName || 'Unknown',
                companyLogo: company.companyLogo || ''
            };
        });
    }, [jobs, companyCache]);

    return (
        <div className="seeker-dashboard page-fade">
            <section className="seeker-hero">
                <div>
                    <p className="kicker">Seeker home</p>
                    <h1>Discover roles that match your goals.</h1>
                    <p>Search roles, compare employers, and apply faster.</p>
                </div>
                <form className="seeker-search" onSubmit={handleSearch}>
                    <input
                        type="text"
                        placeholder="Search jobs or companies"
                        value={query}
                        onChange={(event) => setQuery(event.target.value)}
                    />
                    <select value={searchOption} onChange={(event) => setSearchOption(event.target.value)}>
                        <option value="type">Job Type</option>
                        <option value="title">Title</option>
                        <option value="position">Position</option>
                        <option value="location">Location</option>
                    </select>
                    <button type="submit">Search</button>
                </form>
            </section>

            <section className="seeker-grid">
                {loading && <p>Loading jobs...</p>}
                {!loading && error && <p className="error-text">{error}</p>}
                {!loading && !jobCards.length && !error && <p>No jobs found.</p>}
                {jobCards.map((job) => (
                    <div className="seeker-card" key={job.jobId || `${job.companyId}-${job.jobTitle}`}>
                        <div className="seeker-card-header">
                            {job.companyLogo ? (
                                <img src={`data:image/png;base64,${job.companyLogo}`} alt={job.companyName} />
                            ) : (
                                <div className="avatar-fallback">{job.companyName.slice(0, 1)}</div>
                            )}
                            <div>
                                <h3>{job.jobPosition || 'Role'}</h3>
                                <p>{job.companyName}</p>
                            </div>
                        </div>
                        <p>Title: {job.jobTitle || 'Not listed'}</p>
                        <p>Location: {job.location || 'Not listed'}</p>
                        <p>Job Type: {job.jobType || 'Not listed'}</p>
                        <p>Salary: {job.salary || 'Not listed'}</p>
                        <div className="card-actions">
                            <Link to={`/profile/employer/${job.companyId}`}>Company</Link>
                            <a href={`/ViewJobProfile.html?jobId=${job.jobId}`}>Job</a>
                        </div>
                    </div>
                ))}
            </section>
        </div>
    );
};

export default SeekerDashboardPage;
