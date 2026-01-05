// Favorite Service
class FavoriteService {
    // Get all favorites for current user
    async getFavorites() {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.FAVORITES.BASE), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                }
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to fetch favorites');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Add property to favorites
    async addFavorite(propertyId) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.FAVORITES.BY_PROPERTY(propertyId)), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                }
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to add favorite');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Remove property from favorites
    async removeFavorite(propertyId) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.FAVORITES.BY_PROPERTY(propertyId)), {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                }
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to remove favorite');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Check if property is favorited (by checking favorites list)
    async isFavorited(propertyId) {
        try {
            const favorites = await this.getFavorites();
            return favorites.some(fav => fav.property.id === propertyId);
        } catch (error) {
            return false;
        }
    }
}

const favoriteService = new FavoriteService();

