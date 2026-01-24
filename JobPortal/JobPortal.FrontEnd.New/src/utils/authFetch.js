export const getAuthToken = () => {
    const storedUser = localStorage.getItem('user');
    if (!storedUser) {
        return null;
    }
    try {
        const user = JSON.parse(storedUser);
        return user?.token || null;
    } catch {
        localStorage.removeItem('user');
        return null;
    }
};

export const authFetch = (url, options = {}) => {
    const token = getAuthToken();
    const headers = new Headers(options.headers || {});
    if (token) {
        headers.set('Authorization', `Bearer ${token}`);
    }
    return fetch(url, { ...options, headers });
};
