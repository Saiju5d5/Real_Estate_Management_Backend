// My Properties Page Logic (Agent)
document.addEventListener('DOMContentLoaded', async () => {
    // Check authentication and role
    if (!RoleGuard.requireAgent()) return;

    // Load agent's properties
    await loadMyProperties();
});

async function loadMyProperties() {
    try {
        let user = authManager.getUser();
        if (!user || !user.userId) {
            const profile = await userService.getProfile();
            if (!user) {
                user = {};
                authManager.saveUser(user);
            }
            user.userId = profile.id;
            authManager.saveUser(user);
        }

        const properties = await propertyService.getPropertiesByAgentId(user.userId);
        displayProperties(properties);
    } catch (error) {
        console.error('Error loading properties:', error);
        showError('Failed to load properties');
    }
}

function displayProperties(properties) {
    const container = document.getElementById('properties-container');
    if (!container) return;

    if (properties.length === 0) {
        container.innerHTML = '<p class="no-properties">You haven\'t added any properties yet. <a href="/agent/add-property" class="btn-primary">Add Your First Property</a></p>';
        return;
    }

    container.innerHTML = '';
    properties.forEach(property => {
        const card = new PropertyCard(property, true); // Show actions
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

