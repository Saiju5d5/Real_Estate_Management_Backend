# Spring Boot Server Startup Script
# This script sets up environment variables and starts the server

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Real Estate Management System" -ForegroundColor Cyan
Write-Host "  Spring Boot Server Startup" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Set database password (change this to your MySQL password)
$env:DB_PASSWORD = "saiju5d5"

# Set JWT secret (optional - uses default from application.yml if not set)
if (-not $env:JWT_SECRET) {
    $env:JWT_SECRET = "your-256-bit-secret-key-change-this-in-production-minimum-32-characters"
    Write-Host "Using default JWT_SECRET. For production, set a secure value." -ForegroundColor Yellow
}

Write-Host "Environment variables set:" -ForegroundColor Green
Write-Host "  DB_PASSWORD: [SET]" -ForegroundColor Green
Write-Host "  JWT_SECRET: [SET]" -ForegroundColor Green
Write-Host ""

Write-Host "Starting Spring Boot server..." -ForegroundColor Yellow
Write-Host ""

# Start the server
mvn spring-boot:run

