const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080';
const BACKEND_BASE = import.meta.env.VITE_BACKEND_BASE || API_BASE.replace(/\/api$/, '');

export const apiUrl = (path) => {
    if (!path) {
        return API_BASE;
    }
    return `${API_BASE}${path.startsWith('/') ? path : `/${path}`}`;
};

export const backendUrl = (path) => {
    if (!path) {
        return BACKEND_BASE;
    }
    return `${BACKEND_BASE}${path.startsWith('/') ? path : `/${path}`}`;
};
