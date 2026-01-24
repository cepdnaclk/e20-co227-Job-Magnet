import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import JobSeekerRegisterPage from './pages/JobSeekerRegisterPage';
import EmployerRegisterPage from './pages/EmployerRegisterPage';
import EmployerDashboardPage from './pages/EmployerDashboardPage';
import SeekerDashboardPage from './pages/SeekerDashboardPage';
import AdminDashboardPage from './pages/AdminDashboardPage';
import DashboardRouter from './pages/DashboardRouter';
import AboutPage from './pages/AboutPage';
import ContactPage from './pages/ContactPage';
import MessagesPage from './pages/MessagesPage';
import ViewSeekerProfilePage from './pages/ViewSeekerProfilePage';
import ViewEmployerProfilePage from './pages/ViewEmployerProfilePage';
import Navbar from './components/Navbar';
import PrivateRoute from './components/PrivateRoute';
import './App.css';

/**
 * The main application component.
 * It sets up the router and defines the routes for the application.
 * @returns {JSX.Element} The rendered application component.
 */
function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/register/job-seeker" element={<JobSeekerRegisterPage />} />
        <Route path="/register/employer" element={<EmployerRegisterPage />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/contact" element={<ContactPage />} />
        <Route
          path="/dashboard"
          element={
            <PrivateRoute>
              <DashboardRouter />
            </PrivateRoute>
          }
        />
        <Route
          path="/dashboard/employer"
          element={
            <PrivateRoute>
              <EmployerDashboardPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/dashboard/seeker"
          element={
            <PrivateRoute>
              <SeekerDashboardPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/dashboard/admin"
          element={
            <PrivateRoute>
              <AdminDashboardPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/messages"
          element={
            <PrivateRoute>
              <MessagesPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/profile/seeker/:id"
          element={
            <PrivateRoute>
              <ViewSeekerProfilePage />
            </PrivateRoute>
          }
        />
        <Route
          path="/profile/employer/:id"
          element={
            <PrivateRoute>
              <ViewEmployerProfilePage />
            </PrivateRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
