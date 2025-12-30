# Real Estate Management System (REMS)

A comprehensive Spring Boot backend application for managing real estate properties, users, and bookings.

## 🚀 Features

- **User Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control (ADMIN, AGENT, OWNER, CUSTOMER, TENANT)
  - Secure password encryption with BCrypt

- **Property Management**
  - CRUD operations for properties
  - Property types: Apartment, House, Villa, Condo, Townhouse, Land, Commercial, Office
  - Property status tracking: Available, Sold, Rented, Pending, Unavailable

- **Booking System**
  - Create and manage property visit bookings
  - Booking status management: Pending, Approved, Rejected, Completed, Cancelled
  - Filter bookings by user, property, or status

- **User Management**
  - User registration and profile management
  - Role-based user access control

- **API Documentation**
  - Swagger/OpenAPI documentation available at `/swagger-ui.html`

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🛠️ Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Real_Estate_Management
```

### 2. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE rems_db;
```

### 3. Environment Configuration

Create a `.env` file in the `backend` directory (or set environment variables):

```bash
# Database Configuration
DB_USERNAME=root
DB_PASSWORD=your_database_password

# JWT Configuration
JWT_SECRET=your-256-bit-secret-key-change-this-in-production-minimum-32-characters
```

**Important**: 
- Replace `your_database_password` with your MySQL root password
- Replace `JWT_SECRET` with a secure random string (minimum 32 characters)
- Never commit the `.env` file to version control

### 4. Application Configuration

The application uses `application.yml` for configuration. Database credentials can be set via environment variables:

- `DB_USERNAME` (default: root)
- `DB_PASSWORD` (required)
- `JWT_SECRET` (required - minimum 32 characters)

### 5. Build the Project

```bash
cd backend
mvn clean install
```

### 6. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/rems-1.0.0.jar
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

## 🔐 API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user (Public)
- `POST /api/auth/login` - Login and get JWT token (Public)

### Properties

- `GET /api/properties` - Get all properties (ADMIN, AGENT, CUSTOMER)
- `GET /api/properties/{id}` - Get property by ID (ADMIN, AGENT, CUSTOMER)
- `POST /api/properties` - Create property (ADMIN only)
- `PUT /api/properties/{id}` - Update property (ADMIN only)
- `DELETE /api/properties/{id}` - Delete property (ADMIN only)

### Users

- `GET /api/users` - Get all users (ADMIN only)
- `GET /api/users/{id}` - Get user by ID (ADMIN only)
- `PUT /api/users/{id}` - Update user (ADMIN only)
- `DELETE /api/users/{id}` - Delete user (ADMIN only)

### Bookings

- `GET /api/bookings` - Get all bookings (ADMIN, AGENT)
- `GET /api/bookings/{id}` - Get booking by ID (ADMIN, AGENT, CUSTOMER, OWNER)
- `GET /api/bookings/user/{userId}` - Get bookings by user (ADMIN, AGENT)
- `GET /api/bookings/property/{propertyId}` - Get bookings by property (ADMIN, AGENT, OWNER)
- `GET /api/bookings/status/{status}` - Get bookings by status (ADMIN, AGENT)
- `POST /api/bookings` - Create booking (ADMIN, AGENT, CUSTOMER, OWNER)
- `PUT /api/bookings/{id}` - Update booking (ADMIN, AGENT)
- `PUT /api/bookings/{id}/status` - Update booking status (ADMIN, AGENT)
- `DELETE /api/bookings/{id}` - Delete booking (ADMIN, AGENT)

## 🔑 Authentication

All protected endpoints require a JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

### Example Login Request

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

### Example Authenticated Request

```bash
curl -X GET http://localhost:8080/api/properties \
  -H "Authorization: Bearer <your-jwt-token>"
```

## 👥 User Roles

- **ADMIN**: Full system access
- **AGENT**: Can manage properties and bookings
- **OWNER**: Can view and manage own properties
- **CUSTOMER**: Can view properties and create bookings
- **TENANT**: Can view properties

## 📦 Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/realestate/rems/
│   │   │   ├── config/          # Security, JWT, CORS configuration
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── service/         # Business logic
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── model/           # Entity models
│   │   │   ├── dto/             # Data transfer objects
│   │   │   └── exception/       # Exception handlers
│   │   └── resources/
│   │       └── application.yml  # Application configuration
│   └── test/                     # Test files
└── pom.xml                       # Maven dependencies
```

## 🧪 Testing

Run tests with:

```bash
mvn test
```

## 🔒 Security Features

- JWT token-based authentication
- Password encryption with BCrypt
- Role-based access control (RBAC)
- CORS configuration for frontend integration
- Input validation with Jakarta Validation
- Secure exception handling

## 🐛 Troubleshooting

### Database Connection Issues

- Ensure MySQL is running
- Verify database credentials in environment variables
- Check database exists: `CREATE DATABASE rems_db;`

### JWT Token Issues

- Ensure `JWT_SECRET` is set (minimum 32 characters)
- Token expires after 1 hour (configurable in `application.yml`)

### Port Already in Use

Change the port in `application.yml`:

```yaml
server:
  port: 8081
```

## 📝 Notes

- The application uses Hibernate auto-DDL (`ddl-auto: update`) for development
- For production, consider using Flyway or Liquibase for database migrations
- Default CORS allows `http://localhost:3000` - update in `CorsConfig.java` for production

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

Real Estate Management System - Spring Boot Backend

---

For detailed API documentation, visit `/swagger-ui.html` when the application is running.

