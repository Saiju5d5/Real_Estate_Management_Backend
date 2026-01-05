// Property Form Component
class PropertyForm {
    constructor(property = null) {
        this.property = property;
        this.isEditMode = property !== null;
        this.uploadedImages = property ? (property.images || []) : [];
    }

    render(formContainer) {
        formContainer.innerHTML = `
            <form id="property-form" class="property-form">
                <div class="form-group">
                    <label for="title">Title *</label>
                    <input type="text" id="title" name="title" required 
                           value="${this.property ? this.property.title : ''}">
                    <span class="error-message"></span>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="4">${this.property ? (this.property.description || '') : ''}</textarea>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="price">Price *</label>
                        <input type="number" id="price" name="price" step="0.01" min="0" required
                               value="${this.property ? this.property.price : ''}">
                        <span class="error-message"></span>
                    </div>

                    <div class="form-group">
                        <label for="type">Type *</label>
                        <select id="type" name="type" required>
                            <option value="">Select type</option>
                            <option value="rent" ${this.property && this.property.type === 'rent' ? 'selected' : ''}>Rent</option>
                            <option value="buy" ${this.property && this.property.type === 'buy' ? 'selected' : ''}>Buy</option>
                        </select>
                        <span class="error-message"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="location">Location *</label>
                    <input type="text" id="location" name="location" required
                           value="${this.property ? this.property.location : ''}">
                    <span class="error-message"></span>
                </div>

                <div class="form-group">
                    <label for="images">Images</label>
                    <input type="file" id="images" name="images" multiple accept="image/*">
                    <small>Upload up to 5 images (Max 10MB each)</small>
                    <div id="image-preview" class="image-preview"></div>
                    ${this.uploadedImages.length > 0 ? `
                        <div class="existing-images">
                            <p>Current images:</p>
                            <div class="image-list">
                                ${this.uploadedImages.map(img => {
                                    const imgSrc = img.startsWith('http') ? img : (API_CONFIG.IMAGE_BASE_URL + (img.startsWith('/') ? img : '/' + img));
                                    return `
                                        <div class="image-item">
                                            <img src="${imgSrc}" alt="Property image">
                                            <button type="button" onclick="propertyForm.removeImage('${img}')" class="btn-remove-image">×</button>
                                        </div>
                                    `;
                                }).join('')}
                            </div>
                        </div>
                    ` : ''}
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-primary">${this.isEditMode ? 'Update Property' : 'Create Property'}</button>
                    <a href="${this.isEditMode ? '/agent/my-properties' : '/agent/dashboard'}" class="btn-secondary">Cancel</a>
                </div>
            </form>
        `;

        this.attachEventListeners();
    }

    attachEventListeners() {
        const form = document.getElementById('property-form');
        const imagesInput = document.getElementById('images');
        const imagePreview = document.getElementById('image-preview');

        // Handle image preview
        imagesInput.addEventListener('change', (e) => {
            this.handleImagePreview(e.target.files, imagePreview);
        });

        // Handle form submission
        form.addEventListener('submit', (e) => {
            e.preventDefault();
            this.handleSubmit();
        });
    }

    handleImagePreview(files, previewContainer) {
        previewContainer.innerHTML = '';

        Array.from(files).forEach((file, index) => {
            const validation = uploadService.validateImage(file);
            if (!validation.valid) {
                alert(validation.error);
                return;
            }

            const reader = new FileReader();
            reader.onload = (e) => {
                const imgDiv = document.createElement('div');
                imgDiv.className = 'preview-image-item';
                imgDiv.innerHTML = `
                    <img src="${e.target.result}" alt="Preview ${index + 1}">
                    <button type="button" onclick="this.parentElement.remove()" class="btn-remove-preview">×</button>
                `;
                previewContainer.appendChild(imgDiv);
            };
            reader.readAsDataURL(file);
        });
    }

    removeImage(imageUrl) {
        this.uploadedImages = this.uploadedImages.filter(img => img !== imageUrl);
        // Re-render form
        const formContainer = document.querySelector('.form-container');
        if (formContainer) {
            this.render(formContainer);
        }
    }

    async handleSubmit() {
        const form = document.getElementById('property-form');
        const formData = new FormData(form);

        // Validate required fields
        const title = document.getElementById('title').value.trim();
        const price = document.getElementById('price').value;
        const location = document.getElementById('location').value.trim();
        const type = document.getElementById('type').value;

        if (!title || !price || !location || !type) {
            alert('Please fill in all required fields');
            return;
        }

        if (!Validators.isPositive(price)) {
            alert('Price must be a positive number');
            return;
        }

        try {
            // Show loading
            const submitBtn = form.querySelector('button[type="submit"]');
            const originalText = submitBtn.textContent;
            submitBtn.disabled = true;
            submitBtn.textContent = 'Processing...';

            // Upload images
            const imageFiles = document.getElementById('images').files;
            let imageUrls = [...this.uploadedImages];

            if (imageFiles.length > 0) {
                const uploadedUrls = await uploadService.uploadImages(imageFiles);
                imageUrls = [...imageUrls, ...uploadedUrls];
            }

            // Prepare property data
            const propertyData = {
                title: title,
                description: document.getElementById('description').value.trim(),
                price: parseFloat(price),
                location: location,
                type: type,
                images: imageUrls
            };

            let result;
            if (this.isEditMode) {
                result = await propertyService.updateProperty(this.property.id, propertyData);
                alert('Property updated successfully!');
                window.location.href = `/agent/my-properties`;
            } else {
                result = await propertyService.createProperty(propertyData);
                alert('Property created successfully!');
                window.location.href = `/agent/my-properties`;
            }

        } catch (error) {
            alert('Error: ' + error.message);
        } finally {
            const submitBtn = form.querySelector('button[type="submit"]');
            submitBtn.disabled = false;
            submitBtn.textContent = this.isEditMode ? 'Update Property' : 'Create Property';
        }
    }
}

// Export for use in other files
const propertyForm = PropertyForm;

