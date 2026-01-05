// Favorites Page Logic (Client)
document.addEventListener('DOMContentLoaded', async () => {
    // Check authentication and role
    if (!RoleGuard.requireClient()) return;

    // Load favorites
    await loadFavorites();
});

async function loadFavorites() {
    try {
        const favorites = await favoriteService.getFavorites();
        displayFavorites(favorites);
    } catch (error) {
        console.error('Error loading favorites:', error);
        showError('Failed to load favorites');
    }
}

function displayFavorites(favorites) {
    const container = document.getElementById('favorites-container');
    if (!container) return;

    if (!favorites || favorites.length === 0) {
        container.innerHTML = '<p class="no-favorites">You haven\'t favorited any properties yet.</p>';
        return;
    }

    container.innerHTML = '';
    favorites.forEach(favorite => {
        const card = new PropertyCard(favorite.property);
        container.appendChild(card.render());
    });
}

function showError(message) {
    const errorDiv = document.getElementById('error-message');
    if (errorDiv) {
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
        setTimeout(() => {
            errorDiv.style.display = 'none';
        }, 5000);
    }
}

