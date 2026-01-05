// Property Card Component
class PropertyCard {
    constructor(property, showActions = false) {
        this.property = property;
        this.showActions = showActions;
    }

    // Local placeholder image using SVG data URI (always works, no network required)
    static PLACEHOLDER_IMAGE = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='400' height='300' viewBox='0 0 400 300'%3E%3Crect fill='%23e0e0e0' width='400' height='300'/%3E%3Ctext x='50%25' y='45%25' dominant-baseline='middle' text-anchor='middle' font-family='Arial, sans-serif' font-size='24' fill='%23888'%3E🏠%3C/text%3E%3Ctext x='50%25' y='60%25' dominant-baseline='middle' text-anchor='middle' font-family='Arial, sans-serif' font-size='16' fill='%23888'%3ENo Image%3C/text%3E%3C/svg%3E";

    render() {
        const card = document.createElement('div');
        card.className = 'property-card';
        card.dataset.propertyId = this.property.id;

        // Fix image URL - simplified approach
        const getImageUrl = (url) => {
            if (!url) return PropertyCard.PLACEHOLDER_IMAGE;
            if (url.startsWith('http://') || url.startsWith('https://')) {
                return url;
            } else if (url.startsWith('/uploads/')) {
                return url;
            } else {
                return '/uploads/images/' + url;
            }
        };

        const mainImage = this.property.images && this.property.images.length > 0
            ? getImageUrl(this.property.images[0])
            : PropertyCard.PLACEHOLDER_IMAGE;

        const price = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(this.property.price);

        card.innerHTML = `
            <div class="property-card-image">
                <img src="${mainImage}" 
                     alt="${this.property.title}"
                     onerror="this.src='${PropertyCard.PLACEHOLDER_IMAGE}'">
                <span class="property-type-badge">${this.property.type}</span>
            </div>
            <div class="property-card-content">
                <h3 class="property-card-title">${this.property.title}</h3>
                <p class="property-card-location">${this.property.location}</p>
                <p class="property-card-description">${this.property.description || ''}</p>
                <div class="property-card-footer">
                    <span class="property-card-price">${price}</span>
                    <a href="/property/details?id=${this.property.id}" class="btn-view">View Details</a>
                </div>
            </div>
        `;

        // Add favorite button for clients
        if (authManager.isClient() && !this.showActions) {
            const favoriteBtn = document.createElement('button');
            favoriteBtn.className = 'btn-favorite';
            favoriteBtn.innerHTML = '<i class="icon-heart"></i>';
            favoriteBtn.onclick = () => this.toggleFavorite();
            card.querySelector('.property-card-content').appendChild(favoriteBtn);
        }

        // Add action buttons for agents (edit/delete)
        if (this.showActions && authManager.isAgent()) {
            const actionsDiv = document.createElement('div');
            actionsDiv.className = 'property-card-actions';
            actionsDiv.innerHTML = `
                <a href="/agent/update-property?id=${this.property.id}" class="btn-edit">Edit</a>
                <button onclick="propertyCard.deleteProperty(${this.property.id})" class="btn-delete">Delete</button>
            `;
            card.appendChild(actionsDiv);
        }

        return card;
    }

    async toggleFavorite() {
        try {
            const propertyId = this.property.id;
            const isFavorited = await favoriteService.isFavorited(propertyId);

            if (isFavorited) {
                await favoriteService.removeFavorite(propertyId);
                this.showNotification('Removed from favorites', 'success');
            } else {
                await favoriteService.addFavorite(propertyId);
                this.showNotification('Added to favorites', 'success');
            }
        } catch (error) {
            this.showNotification(error.message, 'error');
        }
    }

    static async deleteProperty(id) {
        if (!confirm('Are you sure you want to delete this property?')) {
            return;
        }

        try {
            await propertyService.deleteProperty(id);
            const propertyCard = document.querySelector(`[data-property-id="${id}"]`);
            if (propertyCard) {
                propertyCard.remove();
            }
            const notification = new PropertyCard(null);
            notification.showNotification('Property deleted successfully', 'success');
        } catch (error) {
            const notification = new PropertyCard(null);
            notification.showNotification(error.message, 'error');
        }
    }

    showNotification(message, type) {
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
}

// Export for use in other files
const propertyCard = PropertyCard;
