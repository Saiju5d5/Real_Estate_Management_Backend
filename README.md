# Real Estate Management System (REMS)

A full-stack Java application for managing real estate properties with Spring Boot backend and Thymeleaf frontend.

## 📁 Project Structure

```
real-estate/
│
├── backend/                // Spring Boot Backend
│   ├── src/
│   │   └── main/
│   │       ├── java/       // Java source code
│   │       └── resources/  // Configuration files
│   └── pom.xml            // Maven dependencies
│
├── frontend/               // Frontend (Java Full-Stack UI)
│   ├── static/            // Static resources (CSS, JS, images)
│   │   ├── css/           // Stylesheets
│   │   ├── js/            // JavaScript files
│   │   └── images/        // Images
│   └── templates/         // Thymeleaf HTML templates
│       ├── auth/          // Authentication pages
│       ├── public/        // Public pages
│       ├── client/        // Client dashboard pages
│       ├── agent/         // Agent dashboard pages
│       └── property/      // Property pages
│
└── README.md
```

## 🚀 Features

- **User Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control (Agent/Client)
  - Secure password encryption

- **Property Management**
  - CRUD operations for properties
  - Property image uploads
  - Advanced search and filtering
  - Property favorites for clients

- **Dashboard**
  - Agent dashboard with statistics
  - Client dashboard for browsing
  - Profile management

## 🛠️ Technology Stack

**Backend:**
- Spring Boot 3.2.5
- Java 17
- MySQL 8.0+
- JWT Authentication
- Spring Security
- Spring Data JPA
- Thymeleaf

**Frontend:**
- Thymeleaf Templates
- Vanilla JavaScript
- CSS3 (Responsive Design)
- Fetch API

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🚀 Setup Instructions

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE rems_db;
```

### 2. Environment Configuration

Set environment variables (PowerShell):

```powershell
$env:DB_PASSWORD='your_database_password'
$env:JWT_SECRET='your-256-bit-secret-key-change-this-in-production-minimum-32-characters'
```

### 3. Application Configuration

Database credentials are configured in `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/rems_db
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:your_password}
```

### 4. Build and Run

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Or use the provided PowerShell script:

```powershell
cd backend
.\start-server.ps1
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

Once the application is running, access Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

API documentation is also available at:

```
http://localhost:8080/v3/api-docs
```

## 🌐 Application Routes

### Public Routes
- `/` - Landing page
- `/auth/login` - Login page
- `/auth/register` - Registration page

### Client Routes (ROLE_client)
- `/client/dashboard` - Browse properties
- `/client/favorites` - View favorites
- `/client/profile` - Manage profile

### Agent Routes (ROLE_agent)
- `/agent/dashboard` - Agent dashboard
- `/agent/add-property` - Add new property
- `/agent/update-property` - Update property
- `/agent/my-properties` - Manage properties

### Shared Routes
- `/property/details` - Property details page

## 🔐 Default Roles

- **agent** - Can create, update, and delete properties
- **client** - Can browse properties and add favorites

## 📝 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token
- `GET /api/auth/me` - Get current user

### Properties
- `GET /api/properties` - Get all properties (with filters)
- `GET /api/properties/{id}` - Get property by ID
- `POST /api/properties` - Create property (agent only)
- `PUT /api/properties/{id}` - Update property (agent only)
- `DELETE /api/properties/{id}` - Delete property (agent only)

### Favorites
- `GET /api/favorites` - Get user favorites
- `POST /api/favorites/{propertyId}` - Add favorite
- `DELETE /api/favorites/{propertyId}` - Remove favorite

### Users
- `GET /api/users/profile` - Get profile
- `PUT /api/users/profile` - Update profile

### Upload
- `POST /api/upload` - Upload image file

## 📦 Project Architecture

The application follows a layered architecture:

```
Controllers (REST API)
    ↓
Services (Business Logic)
    ↓
Repositories (Data Access)
    ↓
Database (MySQL)
```

Frontend structure:
```
Templates (Thymeleaf)
    ↓
JavaScript Services
    ↓
Backend API
```

## 🧪 Testing

The application includes:
- Unit tests for backend services
- Integration tests for API endpoints
- Postman collection for API testing

See `backend/POSTMAN_TESTING_GUIDE.md` for API testing instructions.

## 📖 Documentation

- `WORKFLOW_AND_MODULES.md` - Complete system workflow and module documentation
- `backend/FRONTEND_SETUP.md` - Frontend setup and configuration guide
- `backend/START_SERVER.md` - Server startup instructions

## 🔧 Configuration Notes

### Static Resources
Static files (CSS, JS, images) are served from `frontend/static/` folder.
The application automatically falls back to `backend/src/main/resources/static/` if the frontend folder doesn't exist.

### Templates
Thymeleaf templates are loaded from `frontend/templates/` folder.
The application automatically falls back to `backend/src/main/resources/templates/` if the frontend folder doesn't exist.

### Image Uploads
Uploaded images are stored in `uploads/images/` directory at the project root.

## 🐛 Troubleshooting

### Database Connection Error
- Ensure MySQL is running
- Verify database `rems_db` exists
- Check password is correct in environment variables

### Port Already in Use
- Change port in `application.yml`: `server.port: 8081`
- Or stop the process using port 8080

### Frontend Not Loading
- Verify `frontend/static/` folder exists
- Check browser console for errors
- Ensure files are copied correctly

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

Real Estate Management System Team

## 🙏 Acknowledgments

- Spring Boot team
- Thymeleaf community
- All contributors

