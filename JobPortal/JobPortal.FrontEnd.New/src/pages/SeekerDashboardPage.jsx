import React, { useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { authFetch } from '../utils/authFetch';
import { apiUrl } from '../utils/apiBase';
import './SeekerDashboardPage.css';

const SeekerDashboardPage = () => {
    const { user } = useAuth();
    const [jobs, setJobs] = useState([]);
    const [query, setQuery] = useState('');
    const [searchOption, setSearchOption] = useState('type');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [companyCache, setCompanyCache] = useState({});
    const [unreadMessages, setUnreadMessages] = useState([]);
    const [messagesLoading, setMessagesLoading] = useState(true);
    const [messagesError, setMessagesError] = useState('');

    const loadJobs = async () => {
        try {
            setLoading(true);
            setError('');
            const response = await authFetch(apiUrl('/api/seekers/search-jobs/all'));
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
        const loadUnread = async () => {
            if (!user?.email) {
                setMessagesLoading(false);
                return;
            }
            try {
                setMessagesLoading(true);
                setMessagesError('');
                const response = await authFetch(
                    apiUrl(`/api/messages/unread/${encodeURIComponent(user.email)}`)
                );
                if (!response.ok) {
                    throw new Error('Failed to load messages');
                }
                const data = await response.json();
                setUnreadMessages(Array.isArray(data) ? data : []);
            } catch (err) {
                setMessagesError(err.message || 'Failed to load messages');
            } finally {
                setMessagesLoading(false);
            }
        };

        loadUnread();
    }, [user]);

    useEffect(() => {
        const fetchCompany = async (companyId) => {
            if (!companyId || companyCache[companyId]) {
                return;
            }
            try {
                const response = await authFetch(apiUrl(`/api/employers/findbyid/${companyId}`));
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
                apiUrl(
                    `/api/seekers/search-jobs/search?search_option=${encodeURIComponent(searchOption)}&query=${encodeURIComponent(trimmed)}`
                )
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
                companyLogo: company.companyLogo || '',
                companyEmail: company.email || ''
            };
        });
    }, [jobs, companyCache]);

    const previewMessages = useMemo(() => unreadMessages.slice(0, 3), [unreadMessages]);

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

            <section className="dashboard-messages">
                <div className="messages-header">
                    <div>
                        <p className="kicker">Messages</p>
                        <h2>Recruiter replies</h2>
                        <p>Stay on top of employer conversations.</p>
                    </div>
                    <Link to="/messages" className="messages-cta">Open inbox</Link>
                </div>
                <div className="messages-list">
                    {messagesLoading && <p>Loading messages...</p>}
                    {!messagesLoading && messagesError && <p className="error-text">{messagesError}</p>}
                    {!messagesLoading && !previewMessages.length && !messagesError && (
                        <p>No unread messages yet.</p>
                    )}
                    {previewMessages.map((message) => (
                        <div className="message-preview-card" key={message.id}>
                            <div>
                                <h3>{message.senderEmail}</h3>
                                <p>{message.content}</p>
                            </div>
                            <Link to={`/messages?recipient=${encodeURIComponent(message.senderEmail)}`}>Reply</Link>
                        </div>
                    ))}
                </div>
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
                            {job.companyEmail && (
                                <Link to={`/messages?recipient=${encodeURIComponent(job.companyEmail)}`}>Message</Link>
                            )}
                        </div>
                    </div>
                ))}
            </section>
        </div>
    );
};

export default SeekerDashboardPage;
