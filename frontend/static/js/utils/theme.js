// Theme Manager - Dark/Light Mode Toggle
class ThemeManager {
    constructor() {
        this.init();
    }

    init() {
        // Get saved theme or default to light
        const savedTheme = localStorage.getItem('theme') || 'light';
        this.setTheme(savedTheme);
    }

    setTheme(theme) {
        const html = document.documentElement;
        
        if (theme === 'dark') {
            html.classList.remove('light');
            html.classList.add('dark');
        } else {
            html.classList.remove('dark');
            html.classList.add('light');
        }
        
        localStorage.setItem('theme', theme);
        this.updateToggleIcon(theme);
    }

    toggleTheme() {
        const currentTheme = localStorage.getItem('theme') || 'light';
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        this.setTheme(newTheme);
    }

    getCurrentTheme() {
        return localStorage.getItem('theme') || 'light';
    }

    updateToggleIcon(theme) {
        const icons = document.querySelectorAll('.theme-toggle-icon');
        icons.forEach(icon => {
            if (theme === 'dark') {
                icon.textContent = 'light_mode';
                icon.setAttribute('title', 'Switch to Light Mode');
            } else {
                icon.textContent = 'dark_mode';
                icon.setAttribute('title', 'Switch to Dark Mode');
            }
        });
    }
}

// Create global instance
const themeManager = new ThemeManager();

// Make toggle function globally available
window.toggleTheme = function() {
    themeManager.toggleTheme();
};

