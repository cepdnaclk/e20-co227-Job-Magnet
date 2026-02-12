import React, { useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { authFetch } from '../utils/authFetch';
import { apiUrl } from '../utils/apiBase';
import './EmployerDashboardPage.css';

const EmployerDashboardPage = () => {
    const { user } = useAuth();
    const [seekers, setSeekers] = useState([]);
    const [query, setQuery] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [unreadMessages, setUnreadMessages] = useState([]);
    const [messagesLoading, setMessagesLoading] = useState(true);
    const [messagesError, setMessagesError] = useState('');

    const loadSeekers = async () => {
        try {
            setLoading(true);
            setError('');
            const response = await authFetch(apiUrl('/api/jobseekers/getall'));
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

    const previewMessages = useMemo(() => unreadMessages.slice(0, 3), [unreadMessages]);

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
                apiUrl(`/api/search-seekers/search/${encodeURIComponent(trimmed)}`)
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

            <section className="dashboard-messages">
                <div className="messages-header">
                    <div>
                        <p className="kicker">Messages</p>
                        <h2>Latest from job seekers</h2>
                        <p>Reply quickly to keep candidates engaged.</p>
                    </div>
                    <Link to="/messages" className="messages-cta">Open inbox</Link>
                </div>
                <div className="messages-list">
                    {messagesLoading && <p>Loading messages...</p>}
                    {!messagesLoading && messagesError && <p className="error-text">{messagesError}</p>}
                    {!messagesLoading && !previewMessages.length && !messagesError && (
                        <p>No unread messages right now.</p>
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
                            {seeker.email && (
                                <Link to={`/messages?recipient=${encodeURIComponent(seeker.email)}`}>Message</Link>
                            )}
                        </div>
                    </div>
                ))}
            </section>
        </div>
    );
};

export default EmployerDashboardPage;
