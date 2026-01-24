import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { authFetch } from '../utils/authFetch';
import './Navbar.css';

/**
 * Renders the navigation bar for the application.
 * The Navbar displays different links based on the user's authentication status.
 * It uses the AuthContext to determine if a user is logged in.
 * @returns {JSX.Element} The rendered Navbar component.
 */
const Navbar = () => {
    const { user, logout } = useAuth();
    const [unreadCount, setUnreadCount] = useState(0);

    useEffect(() => {
        let intervalId;
        const loadUnreadCount = async () => {
            if (!user?.email) {
                setUnreadCount(0);
                return;
            }
            try {
                const response = await authFetch(
                    `http://localhost:8080/api/messages/unread/${encodeURIComponent(user.email)}`
                );
                if (!response.ok) {
                    return;
                }
                const data = await response.json();
                setUnreadCount(Array.isArray(data) ? data.length : 0);
            } catch {
                setUnreadCount(0);
            }
        };

        const handleRefresh = () => loadUnreadCount();

        loadUnreadCount();
        intervalId = setInterval(loadUnreadCount, 30000);
        window.addEventListener('messages:refresh', handleRefresh);
        return () => {
            clearInterval(intervalId);
            window.removeEventListener('messages:refresh', handleRefresh);
        };
    }, [user?.email]);

    return (
        <header>
            <a href="/">
                <img src="/images/logo.png" alt="Jobmagnet Logo" className="logo" />
            </a>
            <nav>
                <Link to="/">HOME</Link>
                {user ? (
                    <>
                        <Link to="/dashboard">DASHBOARD</Link>
                        <Link to="/messages" className="messages-link">
                            MESSAGES
                            {unreadCount > 0 && <span className="unread-badge">{unreadCount}</span>}
                        </Link>
                        <a href="#" onClick={logout}>LOGOUT</a>
                    </>
                ) : (
                    <>
                        <Link to="/register">REGISTER</Link>
                        <Link to="/login">LOGIN</Link>
                    </>
                )}
                <Link to="/about">ABOUT US</Link>
                <Link to="/contact">CONTACT</Link>
            </nav>
        </header>
    );
};

export default Navbar;
