// Role-based access control utilities
class RoleGuard {
    // Redirect user based on role
    static redirectByRole() {
        if (!authManager.isAuthenticated()) {
            window.location.href = '/';
            return;
        }

        const role = authManager.getUserRole();
        
        if (role === 'agent') {
            window.location.href = '/agent/dashboard';
        } else if (role === 'client') {
            window.location.href = '/client/dashboard';
        } else {
            window.location.href = '/';
        }
    }

    // Check if user can access agent pages
    static requireAgent() {
        if (!authManager.isAuthenticated() || !authManager.isAgent()) {
            window.location.href = '/';
            return false;
        }
        return true;
    }

    // Check if user can access client pages
    static requireClient() {
        if (!authManager.isAuthenticated() || !authManager.isClient()) {
            window.location.href = '/';
            return false;
        }
        return true;
    }

    // Check if user can access authenticated pages
    static requireAuth() {
        if (!authManager.isAuthenticated()) {
            window.location.href = '/auth/login';
            return false;
        }
        return true;
    }

    // Redirect if already authenticated
    static redirectIfAuthenticated() {
        if (authManager.isAuthenticated()) {
            RoleGuard.redirectByRole();
            return true;
        }
        return false;
    }
}

