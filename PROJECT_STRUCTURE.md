# Project Structure

This document describes the reorganized project structure with separate `frontend/` and `backend/` folders.

## 📁 Directory Structure

```
real-estate/
│
├── backend/                          // Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/realestate/rems/
│   │   │   │       ├── config/      // Configuration classes
│   │   │   │       ├── controller/  // REST controllers
│   │   │   │       ├── dto/         // Data Transfer Objects
│   │   │   │       ├── exception/   // Exception handlers
│   │   │   │       ├── model/       // Entity models
│   │   │   │       ├── repository/  // JPA repositories
│   │   │   │       └── service/     // Business logic
│   │   │   └── resources/
│   │   │       ├── application.yml  // Application configuration
│   │   │       ├── static/          // Backup static files (optional)
│   │   │       └── templates/       // Backup templates (optional)
│   │   └── test/                    // Test files
│   ├── pom.xml                      // Maven dependencies
│   └── *.md                         // Documentation files
│
├── frontend/                         // Frontend (Java Full-Stack UI)
│   ├── static/                      // Static resources
│   │   ├── css/                     // Stylesheets
│   │   │   ├── base.css
│   │   │   ├── auth.css
│   │   │   ├── dashboard.css
│   │   │   ├── property.css
│   │   │   └── responsive.css
│   │   ├── js/                      // JavaScript files
│   │   │   ├── config/              // Configuration
│   │   │   │   ├── api.js
│   │   │   │   └── auth.js
│   │   │   ├── services/            // API services
│   │   │   │   ├── auth.service.js
│   │   │   │   ├── property.service.js
│   │   │   │   ├── favorite.service.js
│   │   │   │   ├── user.service.js
│   │   │   │   └── upload.service.js
│   │   │   ├── utils/               // Utilities
│   │   │   │   ├── storage.js
│   │   │   │   ├── role-guard.js
│   │   │   │   └── validators.js
│   │   │   ├── components/          // UI components
│   │   │   │   ├── navbar.js
│   │   │   │   ├── footer.js
│   │   │   │   ├── property-card.js
│   │   │   │   └── property-form.js
│   │   │   └── pages/               // Page logic
│   │   │       ├── login.js
│   │   │       ├── register.js
│   │   │       ├── landing.js
│   │   │       ├── client-dashboard.js
│   │   │       ├── agent-dashboard.js
│   │   │       ├── add-property.js
│   │   │       ├── update-property.js
│   │   │       ├── property-details.js
│   │   │       ├── favorites.js
│   │   │       ├── profile.js
│   │   │       └── my-properties.js
│   │   └── images/                  // Static images
│   │       └── placeholder.jpg
│   │
│   └── templates/                   // Thymeleaf HTML templates
│       ├── auth/
│       │   ├── login.html
│       │   └── register.html
│       ├── public/
│       │   └── landing.html
│       ├── client/
│       │   ├── dashboard.html
│       │   ├── favorites.html
│       │   └── profile.html
│       ├── agent/
│       │   ├── dashboard.html
│       │   ├── add-property.html
│       │   ├── update-property.html
│       │   └── my-properties.html
│       └── property/
│           └── details.html
│
└── README.md                        // Main documentation
```

## 🔗 How Frontend and Backend Connect

### Static Resources (CSS, JS, Images)

Static files are served from `frontend/static/` folder:

1. **Configuration**: `WebConfig.java` configures Spring Boot to serve static resources from:
   - Primary: `frontend/static/` (file system)
   - Fallback: `backend/src/main/resources/static/` (classpath)

2. **URL Mapping**:
   - `/css/**` → `frontend/static/css/`
   - `/js/**` → `frontend/static/js/`
   - `/images/**` → `frontend/static/images/`

### Thymeleaf Templates

HTML templates are loaded from `frontend/templates/` folder:

1. **Configuration**: `ThymeleafConfig.java` configures template resolver:
   - Primary: `frontend/templates/` (file system)
   - Fallback: `backend/src/main/resources/templates/` (classpath)

2. **Template Resolution**:
   - Checks if `frontend/templates/` exists
   - Uses frontend folder if available
   - Falls back to backend resources if not found

### API Endpoints

All frontend JavaScript services connect to backend REST API:

- Base URL: `http://localhost:8080/api`
- Configured in: `frontend/static/js/config/api.js`

### View Controllers

`ViewController.java` maps routes to templates:

- `/` → `public/landing`
- `/auth/login` → `auth/login`
- `/client/dashboard` → `client/dashboard`
- etc.

## 🚀 Running the Application

1. **Start Backend**:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

2. **Access Application**:
   - Frontend: `http://localhost:8080/`
   - API: `http://localhost:8080/api`
   - Swagger: `http://localhost:8080/swagger-ui.html`

## 📝 Important Notes

1. **File Paths**: The application uses absolute file system paths, so it must be run from the project root directory.

2. **Development vs Production**: 
   - In development, files are served directly from `frontend/` folder
   - Templates are cached: `false` for development
   - For production, consider copying files to classpath or using a build process

3. **Fallback Mechanism**: The application has fallback to `backend/src/main/resources/` if `frontend/` folder doesn't exist, ensuring backward compatibility.

4. **Image Uploads**: Uploaded images are stored in `uploads/images/` at the project root, not in frontend folder.

## ✅ Verification Checklist

- [x] Frontend folder structure created
- [x] Static files copied to `frontend/static/`
- [x] Templates copied to `frontend/templates/`
- [x] WebConfig updated to serve from frontend folder
- [x] ThymeleafConfig updated to load from frontend folder
- [x] Fallback mechanism implemented
- [x] All paths properly configured
- [x] README.md created at root

