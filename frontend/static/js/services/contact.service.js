// Contact Service
class ContactService {
    async sendContactMessage(propertyId, contactData) {
        try {
            const response = await fetch(buildApiUrl(`/properties/${propertyId}/contact`), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    ...authManager.getAuthHeader()
                },
                body: JSON.stringify(contactData)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to send message');
            }

            return data;
        } catch (error) {
            throw error;
        }
    }
}

const contactService = new ContactService();

