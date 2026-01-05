// Client Dashboard Logic
document.addEventListener('DOMContentLoaded', async () => {
    // Check authentication
    if (!RoleGuard.requireClient()) return;

    // Load user data
    await loadUserData();

    // Load properties
    await loadProperties();

    // Setup filters
    setupFilters();
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

async function loadProperties(filters = {}) {
    try {
        const properties = await propertyService.getAllProperties(filters);
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
        container.innerHTML = '<p class="no-properties">No properties found.</p>';
        return;
    }

    container.innerHTML = '';
    properties.forEach(property => {
        const card = new PropertyCard(property);
        container.appendChild(card.render());
    });
}

function setupFilters() {
    const searchForm = document.getElementById('search-form');
    if (!searchForm) return;

    searchForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const filters = {
            search: document.getElementById('search-input')?.value.trim() || undefined,
            type: document.getElementById('type-filter')?.value || undefined,
            minPrice: document.getElementById('min-price')?.value || undefined,
            maxPrice: document.getElementById('max-price')?.value || undefined
        };

        await loadProperties(filters);
    });

    // Reset filters
    const resetBtn = document.getElementById('reset-filters');
    if (resetBtn) {
        resetBtn.addEventListener('click', async () => {
            document.getElementById('search-form').reset();
            await loadProperties();
        });
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

