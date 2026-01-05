// Upload Service
class UploadService {
    // Upload image file
    async uploadImage(file) {
        try {
            const formData = new FormData();
            formData.append('file', file);

            const response = await fetch(buildApiUrl(API_CONFIG.ENDPOINTS.UPLOAD), {
                method: 'POST',
                headers: {
                    ...authManager.getAuthHeader()
                },
                body: formData
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Failed to upload image');
            }

            // Return the image URL from the response
            return data.message; // The API returns {success: true, message: "/uploads/images/..."}
        } catch (error) {
            throw error;
        }
    }

    // Upload multiple images
    async uploadImages(files) {
        try {
            const uploadPromises = Array.from(files).map(file => this.uploadImage(file));
            const imageUrls = await Promise.all(uploadPromises);
            return imageUrls;
        } catch (error) {
            throw error;
        }
    }

    // Validate image file
    validateImage(file) {
        const maxSize = 10 * 1024 * 1024; // 10MB
        const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp'];

        if (!file) {
            return { valid: false, error: 'No file selected' };
        }

        if (!allowedTypes.includes(file.type)) {
            return { valid: false, error: 'Invalid file type. Only images are allowed' };
        }

        if (file.size > maxSize) {
            return { valid: false, error: 'File size exceeds 10MB limit' };
        }

        return { valid: true };
    }
}

const uploadService = new UploadService();

