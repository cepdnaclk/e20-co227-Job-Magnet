import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const DashboardRouter = () => {
    const { user } = useAuth();
    const role = user?.usertype;

    if (!role) {
        return <Navigate to="/login" replace />;
    }

    if (role === 'employer') {
        return <Navigate to="/dashboard/employer" replace />;
    }

    if (role === 'admin') {
        return <Navigate to="/dashboard/admin" replace />;
    }

    return <Navigate to="/dashboard/seeker" replace />;
};

export default DashboardRouter;
