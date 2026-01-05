// Form validation utilities
const Validators = {
    // Validate email
    email(email) {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    },

    // Validate password (min 8 chars, letters, numbers, special char)
    password(password) {
        const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
        return regex.test(password);
    },

    // Validate required field
    required(value) {
        return value && value.trim().length > 0;
    },

    // Validate min length
    minLength(value, min) {
        return value && value.length >= min;
    },

    // Validate number
    isNumber(value) {
        return !isNaN(value) && !isNaN(parseFloat(value));
    },

    // Validate positive number
    isPositive(value) {
        return Validators.isNumber(value) && parseFloat(value) > 0;
    },

    // Show validation error
    showError(input, message) {
        const errorDiv = input.parentElement.querySelector('.error-message');
        if (errorDiv) {
            errorDiv.textContent = message;
            errorDiv.style.display = 'block';
        } else {
            const errDiv = document.createElement('div');
            errDiv.className = 'error-message';
            errDiv.textContent = message;
            errDiv.style.color = '#dc3545';
            errDiv.style.fontSize = '0.875rem';
            errDiv.style.marginTop = '0.25rem';
            input.parentElement.appendChild(errDiv);
        }
        input.classList.add('error');
    },

    // Clear validation error
    clearError(input) {
        const errorDiv = input.parentElement.querySelector('.error-message');
        if (errorDiv) {
            errorDiv.remove();
        }
        input.classList.remove('error');
    },

    // Validate and show errors
    validateField(input, rules) {
        const value = input.value.trim();
        let isValid = true;

        for (const rule of rules) {
            if (!rule.validator(value)) {
                Validators.showError(input, rule.message);
                isValid = false;
                break;
            }
        }

        if (isValid) {
            Validators.clearError(input);
        }

        return isValid;
    }
};

