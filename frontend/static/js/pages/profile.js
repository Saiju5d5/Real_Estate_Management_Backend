// Profile Page Logic
document.addEventListener('DOMContentLoaded', async () => {
    // Check authentication
    if (!RoleGuard.requireAuth()) return;

    // Load user profile
    await loadProfile();

    // Setup form
    setupProfileForm();
});

async function loadProfile() {
    try {
        const user = await userService.getProfile();
        displayProfile(user);
    } catch (error) {
        console.error('Error loading profile:', error);
        showError('Failed to load profile');
    }
}

function displayProfile(user) {
    const nameInput = document.getElementById('name');
    if (nameInput) {
        nameInput.value = user.name || '';
    }

    const emailInput = document.getElementById('email');
    if (emailInput) {
        emailInput.value = user.email;
        emailInput.disabled = true; // Email cannot be changed
    }

    const roleInput = document.getElementById('role');
    if (roleInput) {
        roleInput.value = user.role;
        roleInput.disabled = true; // Role cannot be changed
    }
}

function setupProfileForm() {
    const form = document.getElementById('profile-form');
    if (!form) return;

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const nameInput = document.getElementById('name');
        const passwordInput = document.getElementById('password');
        const confirmPasswordInput = document.getElementById('confirm-password');
        const submitBtn = form.querySelector('button[type="submit"]');

        // Validate name
        if (!Validators.required(nameInput.value)) {
            Validators.showError(nameInput, 'Name is required');
            return;
        }

        // Validate password if provided
        if (passwordInput.value) {
            if (!Validators.password(passwordInput.value)) {
                Validators.showError(passwordInput, 'Password must be at least 8 characters with letters, numbers, and special characters');
                return;
            }

            if (passwordInput.value !== confirmPasswordInput.value) {
                Validators.showError(confirmPasswordInput, 'Passwords do not match');
                return;
            }
        }

        try {
            submitBtn.disabled = true;
            submitBtn.textContent = 'Updating...';

            const updateData = {
                name: nameInput.value.trim()
            };

            // Only include password if provided
            if (passwordInput.value) {
                updateData.password = passwordInput.value;
            }

            await userService.updateProfile(updateData);

            alert('Profile updated successfully!');
            window.location.reload();

        } catch (error) {
            showError(error.message || 'Failed to update profile');
            submitBtn.disabled = false;
            submitBtn.textContent = 'Update Profile';
        }
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

