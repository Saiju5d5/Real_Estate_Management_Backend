// Authentication Service
class AuthService {
    constructor() {
        this.baseUrl = buildApiUrl(API_CONFIG.ENDPOINTS.AUTH.REGISTER.split('/auth')[0]);
    }

    // Register new user
    async register(userData) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.AUTH.REGISTER), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Registration failed');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Login user
    async login(email, password) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.AUTH.LOGIN), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Login failed');
            }

            // Save token and user data
            authManager.saveToken(data.token);
            authManager.saveUser({
                email: data.email,
                role: data.role,
                userId: data.userId
            });

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Get current user
    async getCurrentUser() {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.AUTH.ME), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                }
            });

            const data = await response.json();

            if (!response.ok) {
                if (response.status === 401) {
                    authManager.removeToken();
                    window.location.href = '/auth/login';
                }
                throw new Error(data.message || 'Failed to get user');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Logout user
    logout() {
        try {
            authManager.removeToken();
            // Clear all localStorage
            localStorage.clear();
            // Redirect to home
            window.location.href = '/';
        } catch (error) {
            console.error('Logout error:', error);
            // Force redirect even if there's an error
            localStorage.clear();
            window.location.href = '/';
        }
    }
}

const authService = new AuthService();

