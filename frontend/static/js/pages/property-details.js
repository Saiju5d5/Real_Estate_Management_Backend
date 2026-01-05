// Property Details Page Logic

// Local placeholder image using SVG data URI (always works, no network required)
const PLACEHOLDER_IMAGE = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='400' height='300' viewBox='0 0 400 300'%3E%3Crect fill='%23e0e0e0' width='400' height='300'/%3E%3Ctext x='50%25' y='45%25' dominant-baseline='middle' text-anchor='middle' font-family='Arial, sans-serif' font-size='24' fill='%23888'%3E🏠%3C/text%3E%3Ctext x='50%25' y='60%25' dominant-baseline='middle' text-anchor='middle' font-family='Arial, sans-serif' font-size='16' fill='%23888'%3ENo Image%3C/text%3E%3C/svg%3E";

document.addEventListener('DOMContentLoaded', async () => {
    // Get property ID from URL
    const urlParams = new URLSearchParams(window.location.search);
    const propertyId = urlParams.get('id');

    if (!propertyId) {
        alert('Property ID is required');
        window.location.href = '/';
        return;
    }

    // Load property details
    await loadPropertyDetails(propertyId);

    // Setup favorite button for clients
    if (authManager.isClient()) {
        setupFavoriteButton(propertyId);
    }
});

async function loadPropertyDetails(id) {
    try {
        const property = await propertyService.getPropertyById(id);
        displayPropertyDetails(property);
    } catch (error) {
        alert('Failed to load property: ' + error.message);
        window.location.href = '/';
    }
}

function displayPropertyDetails(property) {
    const titleElement = document.getElementById('property-title');
    if (titleElement) {
        titleElement.textContent = property.title;
    }

    const locationElement = document.getElementById('property-location');
    if (locationElement) {
        locationElement.textContent = property.location;
    }

    const priceElement = document.getElementById('property-price');
    if (priceElement) {
        const price = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(property.price);
        priceElement.textContent = price;
    }

    const typeElement = document.getElementById('property-type');
    if (typeElement) {
        typeElement.textContent = property.type.toUpperCase();
    }

    const descriptionElement = document.getElementById('property-description');
    if (descriptionElement) {
        descriptionElement.textContent = property.description || 'No description available';
    }

    // Display images
    const imagesContainer = document.getElementById('property-images');
    if (imagesContainer) {
        if (property.images && property.images.length > 0) {
            imagesContainer.innerHTML = '';
            property.images.forEach((imageUrl, index) => {
                const img = document.createElement('img');
                // Fix image URL
                let imgSrc;
                if (imageUrl.startsWith('http://') || imageUrl.startsWith('https://')) {
                    imgSrc = imageUrl;
                } else if (imageUrl.startsWith('/uploads/')) {
                    imgSrc = imageUrl;
                } else {
                    imgSrc = '/uploads/images/' + imageUrl;
                }
                img.src = imgSrc;
                img.alt = `${property.title} - Image ${index + 1}`;
                img.onerror = function () {
                    this.src = PLACEHOLDER_IMAGE;
                };
                imagesContainer.appendChild(img);
            });
        } else {
            imagesContainer.innerHTML = `<img src="${PLACEHOLDER_IMAGE}" alt="No images available">`;
        }
    }
}

async function setupFavoriteButton(propertyId) {
    const favoriteBtn = document.getElementById('favorite-btn');
    if (!favoriteBtn) return;

    try {
        const isFavorited = await favoriteService.isFavorited(propertyId);
        updateFavoriteButton(favoriteBtn, isFavorited);

        favoriteBtn.addEventListener('click', async () => {
            try {
                if (isFavorited) {
                    await favoriteService.removeFavorite(propertyId);
                    updateFavoriteButton(favoriteBtn, false);
                    showNotification('Removed from favorites', 'success');
                } else {
                    await favoriteService.addFavorite(propertyId);
                    updateFavoriteButton(favoriteBtn, true);
                    showNotification('Added to favorites', 'success');
                }
            } catch (error) {
                showNotification(error.message, 'error');
            }
        });
    } catch (error) {
        console.error('Error checking favorite status:', error);
    }
}

function updateFavoriteButton(btn, isFavorited) {
    if (isFavorited) {
        btn.classList.add('active');
        btn.textContent = 'Remove from Favorites';
    } else {
        btn.classList.remove('active');
        btn.textContent = 'Add to Favorites';
    }
}

function showNotification(message, type) {
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.textContent = message;
    document.body.appendChild(notification);

    setTimeout(() => {
        notification.classList.add('show');
    }, 100);

    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => notification.remove(), 300);
    }, 3000);
}
