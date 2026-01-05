// Register Page Logic
document.addEventListener('DOMContentLoaded', () => {
    // Redirect if already authenticated
    RoleGuard.redirectIfAuthenticated();

    const registerForm = document.getElementById('register-form');
    if (registerForm) {
        registerForm.addEventListener('submit', handleRegister);
    }

    // Setup form validation
    setupValidation();
});

function setupValidation() {
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirm-password');
    const nameInput = document.getElementById('name');
    const roleInput = document.getElementById('role');

    emailInput.addEventListener('blur', () => {
        Validators.validateField(emailInput, [
            { validator: Validators.required, message: 'Email is required' },
            { validator: Validators.email, message: 'Please enter a valid email' }
        ]);
    });

    passwordInput.addEventListener('blur', () => {
        Validators.validateField(passwordInput, [
            { validator: Validators.required, message: 'Password is required' },
            { validator: (value) => Validators.password(value), message: 'Password must be at least 8 characters with letters, numbers, and special characters' }
        ]);

        // Re-validate confirm password
        if (confirmPasswordInput.value) {
            validateConfirmPassword();
        }
    });

    confirmPasswordInput.addEventListener('blur', validateConfirmPassword);

    nameInput.addEventListener('blur', () => {
        Validators.validateField(nameInput, [
            { validator: Validators.required, message: 'Name is required' }
        ]);
    });

    roleInput.addEventListener('change', () => {
        Validators.clearError(roleInput);
    });
}

function validateConfirmPassword() {
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirm-password');

    if (!confirmPasswordInput.value) {
        Validators.showError(confirmPasswordInput, 'Please confirm your password');
        return false;
    }

    if (passwordInput.value !== confirmPasswordInput.value) {
        Validators.showError(confirmPasswordInput, 'Passwords do not match');
        return false;
    }

    Validators.clearError(confirmPasswordInput);
    return true;
}

async function handleRegister(e) {
    e.preventDefault();

    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirm-password');
    const nameInput = document.getElementById('name');
    const roleInput = document.getElementById('role');
    const submitBtn = e.target.querySelector('button[type="submit"]');

    // Validate all fields
    const emailValid = Validators.validateField(emailInput, [
        { validator: Validators.required, message: 'Email is required' },
        { validator: Validators.email, message: 'Please enter a valid email' }
    ]);

    const passwordValid = Validators.validateField(passwordInput, [
        { validator: Validators.required, message: 'Password is required' },
        { validator: (value) => Validators.password(value), message: 'Password must be at least 8 characters with letters, numbers, and special characters' }
    ]);

    const confirmPasswordValid = validateConfirmPassword();

    const nameValid = Validators.validateField(nameInput, [
        { validator: Validators.required, message: 'Name is required' }
    ]);

    const roleValid = Validators.validateField(roleInput, [
        { validator: Validators.required, message: 'Please select a role' }
    ]);

    if (!emailValid || !passwordValid || !confirmPasswordValid || !nameValid || !roleValid) {
        return;
    }

    try {
        submitBtn.disabled = true;
        submitBtn.textContent = 'Registering...';

        const userData = {
            email: emailInput.value.trim(),
            password: passwordInput.value,
            name: nameInput.value.trim(),
            role: roleInput.value
        };

        await authService.register(userData);

        alert('Registration successful! Please login.');
        window.location.href = '/auth/login';

    } catch (error) {
        showError(error.message || 'Registration failed. Please try again.');
        submitBtn.disabled = false;
        submitBtn.textContent = 'Register';
    }
}

function showError(message) {
    const errorDiv = document.getElementById('error-message');
    if (errorDiv) {
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
    } else {
        alert(message);
    }
}

