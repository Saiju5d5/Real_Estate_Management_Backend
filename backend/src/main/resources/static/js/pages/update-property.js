// Update Property Page Logic
document.addEventListener('DOMContentLoaded', async () => {
    // Check authentication and role
    if (!RoleGuard.requireAgent()) return;

    // Get property ID from URL
    const urlParams = new URLSearchParams(window.location.search);
    const propertyId = urlParams.get('id');

    if (!propertyId) {
        alert('Property ID is required');
        window.location.href = '/agent/my-properties';
        return;
    }

    // Load property data
    await loadProperty(propertyId);
});

async function loadProperty(id) {
    try {
        const property = await propertyService.getPropertyById(id);

        // Verify ownership (agent should only edit their own properties)
        const user = authManager.getUser();
        // Note: Backend should handle this, but we can add a check here too

        // Initialize property form with existing data
        const formContainer = document.querySelector('.form-container');
        if (formContainer) {
            const propertyForm = new PropertyForm(property);
            propertyForm.render(formContainer);
        }
    } catch (error) {
        alert('Failed to load property: ' + error.message);
        window.location.href = '/agent/my-properties';
    }
}

