import React, { createContext, useState, useContext } from 'react';

const AuthContext = createContext(null);

/**
 * Provides authentication context to child components.
 * This component manages the user's authentication state and provides functions to log in and log out.
 * @param {object} props - The component props.
 * @param {React.ReactNode} props.children - The child components that can access the context.
 */
export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem('user');
        if (!storedUser) {
            return null;
        }
        try {
            return JSON.parse(storedUser);
        } catch {
            localStorage.removeItem('user');
            return null;
        }
    });
    const token = user?.token || null;

    /**
     * Logs the user in by storing user data in local storage and updating the state.
     * @param {object} userData - The user data to store.
     */
    const login = (userData) => {
        localStorage.setItem('user', JSON.stringify(userData));
        setUser(userData);
    };

    /**
     * Logs the user out by removing user data from local storage and resetting the state.
     */
    const logout = () => {
        localStorage.removeItem('user');
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

/**
 * Custom hook to use the authentication context.
 * @returns {object} The authentication context, containing the user object and login/logout functions.
 */
export const useAuth = () => {
    return useContext(AuthContext);
};
