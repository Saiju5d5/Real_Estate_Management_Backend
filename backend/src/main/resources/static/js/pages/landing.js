// Landing Page Logic
document.addEventListener('DOMContentLoaded', async () => {
    // Redirect if already authenticated
    RoleGuard.redirectIfAuthenticated();

    // Load featured properties
    await loadFeaturedProperties();

    // Setup search functionality
    setupSearch();
});

async function loadFeaturedProperties() {
    try {
        const properties = await propertyService.getAllProperties();
        displayProperties(properties.slice(0, 6)); // Show first 6 properties
    } catch (error) {
        console.error('Error loading properties:', error);
    }
}

function displayProperties(properties) {
    const container = document.getElementById('properties-container');
    if (!container) return;

    if (properties.length === 0) {
        container.innerHTML = '<p class="no-properties">No properties available at the moment.</p>';
        return;
    }

    container.innerHTML = '';
    properties.forEach(property => {
        const card = new PropertyCard(property);
        container.appendChild(card.render());
    });
}

function setupSearch() {
    const searchForm = document.getElementById('search-form');
    if (!searchForm) return;

    searchForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const searchInput = document.getElementById('search-input');
        const typeSelect = document.getElementById('type-filter');
        const minPriceInput = document.getElementById('min-price');
        const maxPriceInput = document.getElementById('max-price');

        const filters = {
            search: searchInput.value.trim() || undefined,
            type: typeSelect.value || undefined,
            minPrice: minPriceInput.value || undefined,
            maxPrice: maxPriceInput.value || undefined
        };

        try {
            const properties = await propertyService.getAllProperties(filters);
            displayProperties(properties);
            
            // Scroll to properties section
            document.getElementById('properties-section').scrollIntoView({ behavior: 'smooth' });
        } catch (error) {
            alert('Error searching properties: ' + error.message);
        }
    });
}

