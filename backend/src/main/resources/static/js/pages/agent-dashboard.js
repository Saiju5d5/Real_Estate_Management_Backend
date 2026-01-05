// Agent Dashboard Logic
document.addEventListener('DOMContentLoaded', async () => {
    // Check authentication
    if (!RoleGuard.requireAgent()) return;

    // Load user data
    await loadUserData();

    // Load agent's properties
    await loadMyProperties();

    // Setup stats
    await loadStats();
});

async function loadUserData() {
    try {
        const user = await userService.getProfile();
        displayUserInfo(user);
    } catch (error) {
        console.error('Error loading user data:', error);
    }
}

function displayUserInfo(user) {
    const userNameElement = document.getElementById('user-name');
    if (userNameElement && user.name) {
        userNameElement.textContent = user.name;
    }

    const userEmailElement = document.getElementById('user-email');
    if (userEmailElement) {
        userEmailElement.textContent = user.email;
    }
}

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
        container.innerHTML = '<p class="no-properties">You haven\'t added any properties yet. <a href="/agent/add-property">Add your first property</a></p>';
        return;
    }

    container.innerHTML = '';
    properties.slice(0, 6).forEach(property => {
        const card = new PropertyCard(property, true); // Show actions
        container.appendChild(card.render());
    });
}

async function loadStats() {
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

        // Update stats
        const totalProperties = document.getElementById('total-properties');
        if (totalProperties) {
            totalProperties.textContent = properties.length;
        }

        const rentProperties = document.getElementById('rent-properties');
        if (rentProperties) {
            rentProperties.textContent = properties.filter(p => p.type === 'rent').length;
        }

        const buyProperties = document.getElementById('buy-properties');
        if (buyProperties) {
            buyProperties.textContent = properties.filter(p => p.type === 'buy').length;
        }
    } catch (error) {
        console.error('Error loading stats:', error);
    }
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

