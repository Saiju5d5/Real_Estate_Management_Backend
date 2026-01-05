// Footer Component
class Footer {
    render() {
        const footer = document.getElementById('footer');
        if (!footer) return;

        footer.innerHTML = `
            <footer class="footer">
                <div class="footer-container">
                    <div class="footer-content">
                        <div class="footer-section">
                            <h3>REMS</h3>
                            <p>Real Estate Management System</p>
                        </div>
                        <div class="footer-section">
                            <h4>Quick Links</h4>
                            <ul>
                                <li><a href="/">Home</a></li>
                                <li><a href="/auth/login">Login</a></li>
                                <li><a href="/auth/register">Register</a></li>
                            </ul>
                        </div>
                        <div class="footer-section">
                            <h4>About</h4>
                            <p>Manage properties efficiently with our platform</p>
                        </div>
                    </div>
                    <div class="footer-bottom">
                        <p>&copy; ${new Date().getFullYear()} REMS. All rights reserved.</p>
                    </div>
                </div>
            </footer>
        `;
    }
}

// Initialize footer when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    const footer = new Footer();
    footer.render();
});

