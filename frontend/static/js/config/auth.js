// JWT Token Management
class AuthManager {
    constructor() {
        this.TOKEN_KEY = 'rems_jwt_token';
        this.USER_KEY = 'rems_user_data';
    }

    // Save token to localStorage
    saveToken(token) {
        localStorage.setItem(this.TOKEN_KEY, token);
    }

    // Get token from localStorage
    getToken() {
        return localStorage.getItem(this.TOKEN_KEY);
    }

    // Remove token from localStorage
    removeToken() {
        localStorage.removeItem(this.TOKEN_KEY);
        localStorage.removeItem(this.USER_KEY);
    }

    // Save user data
    saveUser(userData) {
        localStorage.setItem(this.USER_KEY, JSON.stringify(userData));
    }

    // Get user data
    getUser() {
        const userData = localStorage.getItem(this.USER_KEY);
        return userData ? JSON.parse(userData) : null;
    }

    // Check if user is authenticated
    isAuthenticated() {
        return this.getToken() !== null;
    }

    // Get authorization header
    getAuthHeader() {
        const token = this.getToken();
        return token ? { 'Authorization': `Bearer ${token}` } : {};
    }

    // Get user role
    getUserRole() {
        const user = this.getUser();
        return user ? user.role : null;
    }

    // Check if user has specific role
    hasRole(role) {
        const userRole = this.getUserRole();
        return userRole === role;
    }

    // Check if user is agent
    isAgent() {
        return this.hasRole('agent');
    }

    // Check if user is client
    isClient() {
        return this.hasRole('client');
    }
}

// Create global instance
const authManager = new AuthManager();

// Make logout globally accessible
window.logout = function() {
    if (typeof authService !== 'undefined') {
        authService.logout();
    } else {
        // Fallback if authService not loaded yet
        authManager.removeToken();
        localStorage.clear();
        window.location.href = '/';
    }
};

