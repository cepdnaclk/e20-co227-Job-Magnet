import React from 'react';
import './ContactPage.css';

/**
 * Renders the contact page.
 * @returns {JSX.Element} The rendered contact page.
 */
const ContactPage = () => {
    return (
        <div className="contact-page page-fade">
            <section className="contact-hero">
                <div>
                    <p className="kicker">Get in touch</p>
                    <h1>We are here to help your next move.</h1>
                    <p>
                        Reach out for support, partnerships, or anything else. We respond
                        within one business day.
                    </p>
                    <div className="contact-cards">
                        <div>
                            <h3>Email</h3>
                            <p>support@jobmagnet.com</p>
                        </div>
                        <div>
                            <h3>Phone</h3>
                            <p>+94 11 234 5678</p>
                        </div>
                        <div>
                            <h3>Location</h3>
                            <p>Colombo, Sri Lanka</p>
                        </div>
                    </div>
                </div>
                <form className="contact-form">
                    <label>
                        Full name
                        <input type="text" placeholder="Your name" />
                    </label>
                    <label>
                        Email address
                        <input type="email" placeholder="you@email.com" />
                    </label>
                    <label>
                        Message
                        <textarea rows="5" placeholder="Tell us what you need"></textarea>
                    </label>
                    <button type="submit">Send message</button>
                </form>
            </section>
        </div>
    );
};

export default ContactPage;
