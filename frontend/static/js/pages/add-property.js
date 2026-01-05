// Add Property Page Logic
document.addEventListener('DOMContentLoaded', () => {
    // Check authentication and role
    if (!RoleGuard.requireAgent()) return;

    // Initialize property form
    const formContainer = document.querySelector('.form-container');
    if (formContainer) {
        const propertyForm = new PropertyForm();
        propertyForm.render(formContainer);
    }
});

