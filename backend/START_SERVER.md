# How to Start the Server

## Quick Start

### Option 1: Using PowerShell Script (Recommended)

```powershell
cd backend
.\start-server.ps1
```

### Option 2: Manual Start

1. **Set Environment Variables** (in PowerShell):
   ```powershell
   $env:DB_PASSWORD='saiju5d5'
   $env:JWT_SECRET='your-256-bit-secret-key-change-this-in-production-minimum-32-characters'
   ```

2. **Start the Server**:
   ```powershell
   cd backend
   mvn spring-boot:run
   ```

## Prerequisites

1. **MySQL Database** must be running
2. **Database `rems_db`** must exist:
   ```sql
   CREATE DATABASE rems_db;
   ```
3. **Maven** must be installed and in PATH

## Verify Server is Running

Once started, you should see:
```
Started RealEstateApplication in X.XXX seconds
```

Then access:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **Base URL**: http://localhost:8080/api

## Troubleshooting

### Database Connection Error
- Ensure MySQL is running
- Verify database `rems_db` exists
- Check password is correct: `$env:DB_PASSWORD='your_password'`

### Port Already in Use
- Change port in `application.yml`: `server.port: 8081`
- Or stop the process using port 8080

### Maven Not Found
- Install Maven: https://maven.apache.org/download.cgi
- Add to PATH

