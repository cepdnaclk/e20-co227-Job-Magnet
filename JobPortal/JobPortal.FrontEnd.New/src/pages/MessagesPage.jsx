import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { authFetch } from '../utils/authFetch';
import { apiUrl } from '../utils/apiBase';
import './MessagesPage.css';

const MessagesPage = () => {
    const { user } = useAuth();
    const [searchParams] = useSearchParams();
    const [unreadMessages, setUnreadMessages] = useState([]);
    const [profilesByEmail, setProfilesByEmail] = useState({});
    const [selectedSender, setSelectedSender] = useState(null);
    const [conversation, setConversation] = useState([]);
    const [messageText, setMessageText] = useState('');
    const [attachment, setAttachment] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    const userEmail = user?.email;
    const userType = user?.usertype;
    const preferredRecipient = searchParams.get('recipient');

    const senderEmails = useMemo(() => {
        const unique = new Set(unreadMessages.map((message) => message.senderEmail));
        return Array.from(unique);
    }, [unreadMessages]);

    const conversationEmails = useMemo(() => {
        const unique = new Set(senderEmails);
        if (preferredRecipient) {
            unique.add(preferredRecipient);
        }
        return Array.from(unique);
    }, [senderEmails, preferredRecipient]);

    const inboxCards = useMemo(() => {
        if (!preferredRecipient) {
            return unreadMessages;
        }
        const hasRecipient = unreadMessages.some((message) => message.senderEmail === preferredRecipient);
        if (hasRecipient) {
            return unreadMessages;
        }
        return [
            {
                id: `direct-${preferredRecipient}`,
                senderEmail: preferredRecipient,
                content: 'Start a conversation',
                isDirect: true
            },
            ...unreadMessages
        ];
    }, [preferredRecipient, unreadMessages]);

    useEffect(() => {
        let intervalId;
        const loadUnread = async () => {
            if (!userEmail) {
                setLoading(false);
                return;
            }
            try {
                setLoading(true);
                setError('');
                const response = await authFetch(
                    apiUrl(`/api/messages/unread/${encodeURIComponent(userEmail)}`)
                );
                if (!response.ok) {
                    throw new Error('Failed to load unread messages');
                }
                const data = await response.json();
                setUnreadMessages(data);
            } catch (err) {
                setError(err.message || 'Failed to load messages');
            } finally {
                setLoading(false);
            }
        };
        loadUnread();
        intervalId = setInterval(loadUnread, 30000);
        return () => clearInterval(intervalId);
    }, [userEmail]);

    useEffect(() => {
        const loadProfiles = async () => {
            if (!conversationEmails.length || !userType) {
                return;
            }
            const endpointBase =
                userType === 'employer'
                    ? apiUrl('/api/jobseekers/profile?email=')
                    : apiUrl('/api/employers/profile?email=');

            const profileEntries = await Promise.all(
                conversationEmails.map(async (email) => {
                    try {
                        const response = await authFetch(`${endpointBase}${encodeURIComponent(email)}`);
                        if (!response.ok) {
                            return [email, null];
                        }
                        const profile = await response.json();
                        return [email, profile];
                    } catch {
                        return [email, null];
                    }
                })
            );

            setProfilesByEmail((prev) => ({ ...prev, ...Object.fromEntries(profileEntries) }));
        };

        loadProfiles();
    }, [conversationEmails, userType]);

    const handleOpenConversation = useCallback(async (senderEmail) => {
        if (!userEmail) {
            return;
        }
        setSelectedSender(senderEmail);
        try {
            const response = await authFetch(
                apiUrl(
                    `/api/messages/conversation/${encodeURIComponent(userEmail)}/${encodeURIComponent(senderEmail)}`
                )
            );
            if (!response.ok) {
                throw new Error('Failed to load conversation');
            }
            const data = await response.json();
            setConversation(data);
        } catch (err) {
            setError(err.message || 'Failed to load conversation');
        }
    }, [userEmail]);

    useEffect(() => {
        if (!preferredRecipient || !userEmail) {
            return;
        }
        handleOpenConversation(preferredRecipient);
    }, [preferredRecipient, userEmail, handleOpenConversation]);

    const handleMarkAsRead = async (messageId) => {
        try {
            await authFetch(apiUrl(`/api/messages/mark-as-read/${messageId}`), {
                method: 'POST'
            });
            setUnreadMessages((prev) => prev.filter((message) => message.id !== messageId));
            window.dispatchEvent(new Event('messages:refresh'));
        } catch {
            setError('Failed to mark message as read');
        }
    };

    const handleSendMessage = async (event) => {
        event.preventDefault();
        if (!selectedSender || !userEmail || !messageText.trim()) {
            return;
        }

        const formData = new FormData();
        formData.append('senderEmail', userEmail);
        formData.append('recipientEmail', selectedSender);
        formData.append('content', messageText.trim());
        if (attachment) {
            formData.append('file', attachment);
        }

        try {
            const response = await authFetch(apiUrl('/api/messages/messages'), {
                method: 'POST',
                body: formData
            });
            if (!response.ok) {
                throw new Error('Failed to send message');
            }
            setMessageText('');
            setAttachment(null);
            window.dispatchEvent(new Event('messages:refresh'));
            await handleOpenConversation(selectedSender);
        } catch (err) {
            setError(err.message || 'Failed to send message');
        }
    };

    const renderAttachment = (message) => {
        if (!message.attachment) {
            return null;
        }
        const byteCharacters = atob(message.attachment);
        const byteNumbers = Array.from(byteCharacters, (char) => char.charCodeAt(0));
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: message.attachmentMimeType || 'application/octet-stream' });
        const url = URL.createObjectURL(blob);
        return (
            <a className="attachment-link" href={url} download={message.attachmentName || 'attachment'}>
                Download attachment
            </a>
        );
    };

    return (
        <div className="messages-page page-fade">
            <section className="messages-hero">
                <div>
                    <p className="kicker">Messages</p>
                    <h1>Your inbox</h1>
                    <p>Stay connected with {userType === 'employer' ? 'job seekers' : 'employers'} in one place.</p>
                </div>
            </section>

            <section className="messages-layout">
                <div className="messages-list">
                    <h2>Unread</h2>
                    {loading && <p>Loading messages...</p>}
                    {!loading && error && <p className="error-text">{error}</p>}
                    {!loading && !inboxCards.length && <p>No unread messages.</p>}
                    {inboxCards.map((message) => {
                        const profile = profilesByEmail[message.senderEmail];
                        const displayName =
                            userType === 'employer'
                                ? profile ? `${profile.fname || ''} ${profile.lname || ''}`.trim() : message.senderEmail
                                : profile?.companyName || message.senderEmail;
                        const avatar = userType === 'employer' ? profile?.profilePicture : profile?.companyLogo;
                        const profileLink = profile?.id
                            ? userType === 'employer'
                                ? `/profile/seeker/${profile.id}`
                                : `/profile/employer/${profile.id}`
                            : null;
                        return (
                            <div className="message-card" key={message.id}>
                                <div className="message-sender">
                                    {avatar ? (
                                        <img src={`data:image/png;base64,${avatar}`} alt={displayName} />
                                    ) : (
                                        <div className="avatar-fallback">{displayName.slice(0, 1).toUpperCase()}</div>
                                    )}
                                    <div>
                                        <h3>{displayName}</h3>
                                        <p>{message.senderEmail}</p>
                                    </div>
                                </div>
                                <p className="message-preview">{message.content}</p>
                                {profileLink && (
                                    <Link className="profile-link" to={profileLink}>View profile</Link>
                                )}
                                <div className="message-actions">
                                    <button onClick={() => handleOpenConversation(message.senderEmail)}>Open</button>
                                    {!message.isDirect && (
                                        <button className="ghost" onClick={() => handleMarkAsRead(message.id)}>
                                            Mark read
                                        </button>
                                    )}
                                </div>
                            </div>
                        );
                    })}
                </div>

                <div className="conversation-panel">
                    <h2>Conversation</h2>
                    {!selectedSender && <p>Select a message to view the conversation.</p>}
                    {!!selectedSender && (
                        <>
                            <div className="conversation-thread">
                                {conversation.length === 0 && <p>No messages yet.</p>}
                                {conversation.map((message) => (
                                    <div
                                        className={`conversation-bubble ${message.senderEmail === userEmail ? 'outgoing' : 'incoming'}`}
                                        key={message.id}
                                    >
                                        <p>{message.content}</p>
                                        {renderAttachment(message)}
                                    </div>
                                ))}
                            </div>
                            <form className="message-form" onSubmit={handleSendMessage}>
                                <textarea
                                    rows="3"
                                    value={messageText}
                                    onChange={(event) => setMessageText(event.target.value)}
                                    placeholder="Write a reply"
                                />
                                <div className="message-form-actions">
                                    <input type="file" onChange={(event) => setAttachment(event.target.files[0])} />
                                    <button type="submit">Send</button>
                                </div>
                            </form>
                        </>
                    )}
                </div>
            </section>
        </div>
    );
};

export default MessagesPage;
