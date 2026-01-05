// Property Service
class PropertyService {
    // Get all properties with optional filters
    async getAllProperties(filters = {}) {
        try {
            let url = buildApiUrl(API_CONFIG.ENDPOINTS.PROPERTIES.BASE);
            const params = new URLSearchParams();

            if (filters.search) params.append('search', filters.search);
            if (filters.minPrice) params.append('minPrice', filters.minPrice);
            if (filters.maxPrice) params.append('maxPrice', filters.maxPrice);
            if (filters.type) params.append('type', filters.type);

            if (params.toString()) {
                url += '?' + params.toString();
            }

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                }
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to fetch properties');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Get property by ID
    async getPropertyById(id) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.PROPERTIES.BY_ID(id)), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                }
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to fetch property');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Get properties by agent ID
    async getPropertiesByAgentId(agentId) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.PROPERTIES.BY_AGENT(agentId)), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                }
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to fetch properties');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Create property (agent only)
    async createProperty(propertyData) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.PROPERTIES.BASE), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                },
                body: JSON.stringify(propertyData)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to create property');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Update property (agent only)
    async updateProperty(id, propertyData) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.PROPERTIES.BY_ID(id)), {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                },
                body: JSON.stringify(propertyData)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to update property');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }

    // Delete property (agent only)
    async deleteProperty(id) {
        try {
            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.PROPERTIES.BY_ID(id)), {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                }
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to delete property');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }
}

const propertyService = new PropertyService();

