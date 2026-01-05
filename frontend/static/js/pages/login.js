// Login Page Logic
document.addEventListener('DOMContentLoaded', () => {
    // Redirect if already authenticated
    RoleGuard.redirectIfAuthenticated();

    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }

    // Setup form validation
    setupValidation();
});

function setupValidation() {
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');

    emailInput.addEventListener('blur', () => {
        Validators.validateField(emailInput, [
            { validator: Validators.required, message: 'Email is required' },
            { validator: Validators.email, message: 'Please enter a valid email' }
        ]);
    });

    passwordInput.addEventListener('blur', () => {
        Validators.validateField(passwordInput, [
            { validator: Validators.required, message: 'Password is required' }
        ]);
    });
}

async function handleLogin(e) {
    e.preventDefault();

    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const submitBtn = e.target.querySelector('button[type="submit"]');

    // Validate form
    const emailValid = Validators.validateField(emailInput, [
        { validator: Validators.required, message: 'Email is required' },
        { validator: Validators.email, message: 'Please enter a valid email' }
    ]);

    const passwordValid = Validators.validateField(passwordInput, [
        { validator: Validators.required, message: 'Password is required' }
    ]);

    if (!emailValid || !passwordValid) {
        return;
    }

    try {
        submitBtn.disabled = true;
        submitBtn.textContent = 'Logging in...';

        const email = emailInput.value.trim();
        const password = passwordInput.value;

        await authService.login(email, password);

        // Redirect based on role
        RoleGuard.redirectByRole();

    } catch (error) {
        showError(error.message || 'Login failed. Please check your credentials.');
        submitBtn.disabled = false;
        submitBtn.textContent = 'Login';
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

