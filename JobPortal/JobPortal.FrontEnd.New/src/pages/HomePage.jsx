import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './HomePage.css';

/**
 * Renders the home page of the application.
 * It features a hero section with a slideshow, a role selection section, featured jobs, testimonials, and a call to action.
 * @returns {JSX.Element} The rendered home page.
 */
const HomePage = () => {
    const [currentImageIndex, setCurrentImageIndex] = useState(0);
    const images = ['/images/homeimg1.jpg', '/images/homeimg2.jpg', '/images/homeimg3.jpg', '/images/homeimg4.jpg'];

    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentImageIndex((prevIndex) => (prevIndex + 1) % images.length);
        }, 5000);
        return () => clearInterval(interval);
    }, [images.length]);

    useEffect(() => {
        const revealElements = Array.from(document.querySelectorAll('.reveal'));
        if (!('IntersectionObserver' in window) || revealElements.length === 0) {
            revealElements.forEach((el) => el.classList.add('is-visible'));
            return undefined;
        }

        const observer = new IntersectionObserver(
            (entries) => {
                entries.forEach((entry) => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('is-visible');
                        observer.unobserve(entry.target);
                    }
                });
            },
            { threshold: 0.18, rootMargin: '0px 0px -8% 0px' }
        );

        revealElements.forEach((el) => observer.observe(el));
        return () => observer.disconnect();
    }, []);

    /**
     * Shows the next image in the slideshow.
     */
    const showNextImage = () => {
        setCurrentImageIndex((prevIndex) => (prevIndex + 1) % images.length);
    };

    /**
     * Shows the previous image in the slideshow.
     */
    const showPreviousImage = () => {
        setCurrentImageIndex((prevIndex) => (prevIndex - 1 + images.length) % images.length);
    };

    return (
        <>
            <section className="hero">
                {images.map((src, index) => (
                    <img key={src} src={src} alt={`Hero Image ${index + 1}`} className={index === currentImageIndex ? 'active' : ''} />
                ))}
                <div className="hero-overlay" />
                <div className="hero-content">
                    <span className="hero-badge">JobMagnet 2024</span>
                    <h2>Find Work That Fits Your Life</h2>
                    <p>Smart matching for seekers, sharper reach for employers.</p>
                    <div className="hero-search">
                        <input type="text" placeholder="Job Title or Keywords" />
                        <select>
                            <option value="">Select Location</option>
                            <option value="city1">City 1</option>
                            <option value="city2">City 2</option>
                        </select>
                        <button>Search</button>
                    </div>
                    <div className="hero-actions">
                        <Link className="hero-cta" to="/register/job-seeker">Start as Job Seeker</Link>
                        <Link className="hero-ghost" to="/register/employer">I am Hiring</Link>
                    </div>
                </div>
                <div className="nav-buttons">
                    <button id="prev-button" onClick={showPreviousImage}>Prev</button>
                    <button id="next-button" onClick={showNextImage}>Next</button>
                </div>
            </section>

            <section id="role-selection" className="role-selection reveal">
                <div className="section-title">
                    <p className="kicker">Choose your path</p>
                    <h2>Select Your Role</h2>
                </div>
                <div className="role-grid">
                    <Link to="/register/job-seeker" className="role-card reveal">
                        <img src="/images/seekicon.png" alt="Job Seeker" />
                        <h3>Job Seeker</h3>
                        <p>Build a profile, discover roles, apply fast.</p>
                        <span className="role-link">Get started</span>
                    </Link>
                    <Link to="/register/employer" className="role-card reveal">
                        <img src="/images/empicon.png" alt="Employer" />
                        <h3>Employer</h3>
                        <p>Post jobs, shortlist candidates, hire with clarity.</p>
                        <span className="role-link">Post a role</span>
                    </Link>
                </div>
            </section>

            <section className="featured-jobs reveal">
                <div className="section-title">
                    <p className="kicker">Trending picks</p>
                    <h2>Featured Jobs</h2>
                </div>
                <div className="job-grid">
                    <div className="job-item reveal">
                        <div className="job-meta">Full Time</div>
                        <h3>Software Engineer</h3>
                        <p className="job-company">Company A</p>
                        <p className="job-location">City A</p>
                        <button>Apply Now</button>
                    </div>
                    <div className="job-item reveal">
                        <div className="job-meta">Remote</div>
                        <h3>Data Analyst</h3>
                        <p className="job-company">Company B</p>
                        <p className="job-location">City B</p>
                        <button>Apply Now</button>
                    </div>
                    <div className="job-item reveal">
                        <div className="job-meta">Hybrid</div>
                        <h3>Project Manager</h3>
                        <p className="job-company">Company C</p>
                        <p className="job-location">City C</p>
                        <button>Apply Now</button>
                    </div>
                    <div className="job-item reveal">
                        <div className="job-meta">Contract</div>
                        <h3>UX Designer</h3>
                        <p className="job-company">Company D</p>
                        <p className="job-location">City D</p>
                        <button>Apply Now</button>
                    </div>
                </div>
            </section>

            <section className="testimonials reveal">
                <div className="section-title">
                    <p className="kicker">Real outcomes</p>
                    <h2>What Our Users Say</h2>
                </div>
                <div className="testimonial-grid">
                    <div className="testimonial-item reveal">
                        <p>"JobMagnet helped me find my dream job!"</p>
                        <p>- User A</p>
                    </div>
                    <div className="testimonial-item reveal">
                        <p>"The platform is user-friendly and efficient."</p>
                        <p>- User B</p>
                    </div>
                    <div className="testimonial-item reveal">
                        <p>"I love the variety of job listings!"</p>
                        <p>- User C</p>
                    </div>
                </div>
            </section>

            <section className="cta reveal">
                <h2>Ready to Start Your Journey?</h2>
                <p>Join today and take the first step toward your next role.</p>
                <div className="cta-actions">
                    <Link to="/register/job-seeker">
                        <button>Get Started</button>
                    </Link>
                    <Link to="/register/employer" className="cta-link">Post a Job</Link>
                </div>
            </section>

            <footer>
                <div className="footer-content">
                    <div>
                        <h3>JobMagnet</h3>
                        <p>Talent meets opportunity with clarity.</p>
                    </div>
                    <div className="footer-links">
                        <Link to="/about">About</Link>
                        <Link to="/contact">Contact</Link>
                        <Link to="/register">Register</Link>
                    </div>
                    <div className="social-media">
                        <a href="#"><img src="/images/facebook.png" alt="Facebook" /></a>
                        <a href="#"><img src="/images/logos.png" alt="Twitter" /></a>
                        <a href="#"><img src="/images/linkedin.png" alt="LinkedIn" /></a>
                    </div>
                </div>
                <p className="footer-note">&copy; 2024 JobMagnet. All rights reserved.</p>
            </footer>
        </>
    );
};

export default HomePage;
