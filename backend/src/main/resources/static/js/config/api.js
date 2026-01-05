// API Configuration
const API_CONFIG = {
    BASE_URL: 'http://localhost:8080/api',
    IMAGE_BASE_URL: 'http://localhost:8080',
    ENDPOINTS: {
        AUTH: {
            REGISTER: '/auth/register',
            LOGIN: '/auth/login',
            ME: '/auth/me'
        },
        PROPERTIES: {
            BASE: '/properties',
            BY_ID: (id) => `/properties/${id}`,
            BY_AGENT: (agentId) => `/properties/agent/${agentId}`
        },
        FAVORITES: {
            BASE: '/favorites',
            BY_PROPERTY: (propertyId) => `/favorites/${propertyId}`
        },
        USERS: {
            PROFILE: '/users/profile'
        },
        UPLOAD: '/upload'
    }
};

// Helper function to build full URL
function buildApiUrl(endpoint) {
    return `${API_CONFIG.BASE_URL}${endpoint}`;
}

// Helper function to show notification - Make it global
window.showNotification = function(message, type = 'success') {
    // Remove existing notifications
    const existing = document.querySelectorAll('.notification-toast');
    existing.forEach(n => n.remove());
    
    const notification = document.createElement('div');
    notification.className = `notification-toast fixed top-4 right-4 z-[9999] px-6 py-4 rounded-lg shadow-lg text-white font-medium flex items-center gap-2 ${
        type === 'success' ? 'bg-green-500' : 'bg-red-500'
    }`;
    notification.innerHTML = `
        <span class="material-symbols-outlined">${type === 'success' ? 'check_circle' : 'error'}</span>
        <span>${message}</span>
    `;
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.style.opacity = '0';
        notification.style.transition = 'opacity 0.3s';
        setTimeout(() => notification.remove(), 300);
    }, 3000);
};

