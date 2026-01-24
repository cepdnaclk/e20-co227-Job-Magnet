import React from 'react';
import './AboutPage.css';

/**
 * Renders the about page.
 * @returns {JSX.Element} The rendered about page.
 */
const AboutPage = () => {
    return (
        <div className="about-page page-fade">
            <section className="about-hero">
                <div className="about-hero-content">
                    <p className="kicker">Who we are</p>
                    <h1>JobMagnet connects people with work that matters.</h1>
                    <p>
                        We build a clearer path between talent and opportunity. Our platform
                        helps job seekers move faster and helps employers hire with confidence.
                    </p>
                </div>
                <div className="about-hero-card">
                    <h2>Built for growth</h2>
                    <p>Profiles, shortlists, and hiring insights in one place.</p>
                    <div className="about-tags">
                        <span>Smart matching</span>
                        <span>Faster hiring</span>
                        <span>Clear workflows</span>
                    </div>
                </div>
            </section>

            <section className="about-stats">
                <div className="stat-card">
                    <h3>12k+</h3>
                    <p>Active seekers</p>
                </div>
                <div className="stat-card">
                    <h3>3k+</h3>
                    <p>Employers</p>
                </div>
                <div className="stat-card">
                    <h3>18k+</h3>
                    <p>Successful matches</p>
                </div>
            </section>

            <section className="about-values">
                <div className="section-title">
                    <p className="kicker">Our values</p>
                    <h2>Designing for clarity and trust</h2>
                </div>
                <div className="value-grid">
                    <div className="value-card">
                        <h3>Transparency</h3>
                        <p>Clear job details and consistent communication from day one.</p>
                    </div>
                    <div className="value-card">
                        <h3>Speed</h3>
                        <p>Streamlined flows that reduce the time between search and hire.</p>
                    </div>
                    <div className="value-card">
                        <h3>Support</h3>
                        <p>Guided steps for both seekers and employers to stay aligned.</p>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default AboutPage;
