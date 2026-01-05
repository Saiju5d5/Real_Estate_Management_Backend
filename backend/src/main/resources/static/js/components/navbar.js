// Navbar Component
class Navbar {
    constructor() {
        this.isAuthenticated = authManager.isAuthenticated();
        this.user = authManager.getUser();
    }

    render() {
        const navbar = document.getElementById('navbar');
        if (!navbar) return;

        const role = this.user ? this.user.role : null;
        const email = this.user ? this.user.email : '';

        navbar.innerHTML = `
            <nav class="navbar">
                <div class="navbar-container">
                    <div class="navbar-brand">
                        <a href="/">REMS</a>
                    </div>
                    <ul class="navbar-menu">
                        <li><a href="/">Home</a></li>
                        ${this.isAuthenticated ? `
                            ${role === 'agent' ? `
                                <li><a href="/agent/dashboard">Dashboard</a></li>
                                <li><a href="/agent/add-property">Add Property</a></li>
                                <li><a href="/agent/my-properties">My Properties</a></li>
                            ` : ''}
                            ${role === 'client' ? `
                                <li><a href="/client/dashboard">Dashboard</a></li>
                                <li><a href="/client/favorites">Favorites</a></li>
                                <li><a href="/client/profile">Profile</a></li>
                            ` : ''}
                            <li class="navbar-user">
                                <span>${email}</span>
                                <button onclick="authService.logout()" class="btn-logout">Logout</button>
                            </li>
                        ` : `
                            <li><a href="/auth/login">Login</a></li>
                            <li><a href="/auth/register" class="btn-primary">Register</a></li>
                        `}
                    </ul>
                </div>
            </nav>
        `;
    }

    update() {
        this.isAuthenticated = authManager.isAuthenticated();
        this.user = authManager.getUser();
        this.render();
    }
}

// Initialize navbar when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    const navbar = new Navbar();
    navbar.render();
});

