// User Service
class UserService {
    // Get user profile
    async getProfile() {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.USERS.PROFILE), {
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
                throw new Error(data.message || 'Failed to fetch profile');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Update user profile
    async updateProfile(profileData) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.USERS.PROFILE), {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                },
                body: JSON.stringify(profileData)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to update profile');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }
}

const userService = new UserService();

