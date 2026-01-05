# Real Estate Management System - Workflow and Modules

## 📋 Table of Contents
1. [System Overview](#system-overview)
2. [Architecture](#architecture)
3. [Modules](#modules)
4. [Workflow](#workflow)
5. [API Endpoints Summary](#api-endpoints-summary)

---

## 🎯 System Overview

**Real Estate Management System (REMS)** is a Spring Boot-based backend application that manages:
- User authentication and authorization (JWT-based)
- Property listings and management
- User favorites (property bookmarks)
- Profile management
- Image uploads

**Technology Stack:**
- **Framework:** Spring Boot 3.2.5
- **Language:** Java 17
- **Database:** MySQL 8.0+
- **Authentication:** JWT (JSON Web Tokens)
- **Security:** Spring Security with BCrypt password encryption
- **API Documentation:** Swagger/OpenAPI

---

## 🏗️ Architecture

The application follows a **layered architecture** pattern:

```
┌─────────────────────────────────────────┐
│         Controllers (REST API)          │
│  (Auth, Property, Favorite, Profile)    │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          Services (Business Logic)      │
│  (Auth, Property, Favorite, User)       │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│      Repositories (Data Access)         │
│  (User, Property, Favorite Repository)  │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          Database (MySQL)               │
│  (users, properties, favorites tables)  │
└─────────────────────────────────────────┘
```

**Security Layer:**
- JWT Authentication Filter (intercepts requests)
- Security Config (defines access rules)
- Method-level security (`@PreAuthorize`)

---

## 📦 Modules

### 1. **Authentication Module** (`/api/auth`)

**Purpose:** User registration, login, and JWT token management

**Components:**
- **Controller:** `AuthController.java`
- **Service:** `AuthService.java`
- **DTOs:**
  - `UserRegistrationDTO.java` - Registration data
  - `LoginRequestDTO.java` - Login credentials
  - `LoginResponseDTO.java` - Login response (token + user info)

**Key Features:**
- User registration with email/password
- Login with JWT token generation
- Password encryption using BCrypt
- Get current authenticated user profile

**Endpoints:**
- `POST /api/auth/register` - Register new user (Public)
- `POST /api/auth/login` - Login and get JWT token (Public)
- `GET /api/auth/me` - Get current user (Authenticated)

---

### 2. **Property Management Module** (`/api/properties`)

**Purpose:** CRUD operations for real estate properties

**Components:**
- **Controller:** `PropertyController.java`
- **Service:** `PropertyService.java`
- **Model:** `Property.java`
- **Repository:** `PropertyRepository.java`

**Key Features:**
- Create, read, update, delete properties
- Search and filter properties (by price, type, location)
- Property images management
- Agent-based property ownership

**Property Model Fields:**
- `id` - Unique identifier
- `title` - Property title
- `description` - Property description
- `price` - Property price (BigDecimal)
- `location` - Property location
- `type` - Property type ('rent' or 'buy')
- `images` - List of image URLs
- `agent` - Reference to User (agent)
- `createdAt` - Timestamp

**Endpoints:**
- `POST /api/properties` - Create property (ROLE_agent)
- `GET /api/properties` - Get all properties (Public, with optional filters)
- `GET /api/properties/{id}` - Get property by ID (Public)
- `GET /api/properties/agent/{agentId}` - Get properties by agent (Public)
- `PUT /api/properties/{id}` - Update property (ROLE_agent - own properties)
- `DELETE /api/properties/{id}` - Delete property (ROLE_agent - own properties)

---

### 3. **Favorites Module** (`/api/favorites`)

**Purpose:** Allow clients to save favorite properties

**Components:**
- **Controller:** `FavoriteController.java`
- **Service:** `FavoriteService.java`
- **Model:** `Favorite.java`
- **Repository:** `FavoriteRepository.java`

**Key Features:**
- Add property to favorites
- View user's favorite properties
- Remove property from favorites
- Prevents duplicate favorites (unique constraint)

**Favorite Model:**
- `id` - Unique identifier
- `property` - Reference to Property
- `client` - Reference to User (client)
- `createdAt` - Timestamp

**Endpoints:**
- `POST /api/favorites/{propertyId}` - Add to favorites (ROLE_client)
- `GET /api/favorites` - Get user's favorites (ROLE_client)
- `DELETE /api/favorites/{propertyId}` - Remove favorite (ROLE_client)

---

### 4. **Profile Management Module** (`/api/users`)

**Purpose:** User profile management

**Components:**
- **Controller:** `ProfileController.java`
- **Service:** `UserService.java`
- **DTO:** `UserUpdateDTO.java`
- **Model:** `User.java`

**Key Features:**
- View own profile
- Update profile information
- Password is never returned in responses

**User Model Fields:**
- `id` - Unique identifier
- `email` - Email address (unique)
- `password` - Encrypted password (never returned)
- `role` - User role ('agent' or 'client')
- `name` - User's name
- `enabled` - Account status (boolean)
- `createdAt` - Timestamp

**Endpoints:**
- `GET /api/users/profile` - Get own profile (Authenticated)
- `PUT /api/users/profile` - Update own profile (Authenticated)

---

### 5. **Image Upload Module** (`/api/upload`)

**Purpose:** Handle property image uploads

**Components:**
- **Controller:** `ImageUploadController.java`

**Key Features:**
- Upload images for properties
- File size limit: 10MB
- Supports multiple file formats

**Endpoints:**
- `POST /api/upload` - Upload image file

---

### 6. **Security & Configuration Module**

**Components:**

#### **Security Config** (`SecurityConfig.java`)
- Configures Spring Security
- Defines public vs protected endpoints
- Sets up JWT filter chain
- Enables method-level security

#### **JWT Authentication Filter** (`JwtAuthenticationFilter.java`)
- Intercepts HTTP requests
- Validates JWT tokens
- Sets authentication context for authorized requests

#### **JWT Utility** (`JwtUtil.java`)
- Generates JWT tokens
- Validates JWT tokens
- Extracts user information from tokens

#### **CORS Configuration** (`CorsConfig.java`)
- Configures Cross-Origin Resource Sharing
- Allows frontend (localhost:3000) to access API

#### **Swagger Configuration** (`SwaggerConfig.java`)
- Configures API documentation
- Available at `/swagger-ui.html`

---

### 7. **Exception Handling Module**

**Components:**
- **Global Exception Handler** (`GlobalExceptionHandler.java`)
- **Custom Exceptions:**
  - `ResourceNotFoundException.java`
  - `InvalidCredentialsException.java`

**Purpose:** Centralized exception handling with proper HTTP status codes

---

## 🔄 Workflow

### **1. User Registration & Authentication Flow**

```
┌──────────┐                    ┌─────────────┐                    ┌──────────┐
│  Client  │                    │   Backend   │                    │ Database │
└────┬─────┘                    └──────┬──────┘                    └────┬─────┘
     │                                  │                                │
     │  1. POST /api/auth/register     │                                │
     │     {email, password, role}     │                                │
     ├─────────────────────────────────>                                │
     │                                  │                                │
     │                                  │  2. Validate input             │
     │                                  │                                │
     │                                  │  3. Check if email exists      │
     │                                  ├───────────────────────────────>│
     │                                  │<───────────────────────────────┤
     │                                  │                                │
     │                                  │  4. Encrypt password (BCrypt)  │
     │                                  │                                │
     │                                  │  5. Save user to database      │
     │                                  ├───────────────────────────────>│
     │                                  │<───────────────────────────────┤
     │                                  │                                │
     │  6. Return User (no password)    │                                │
     │<─────────────────────────────────┤                                │
     │                                  │                                │
     │  7. POST /api/auth/login         │                                │
     │     {email, password}            │                                │
     ├─────────────────────────────────>                                │
     │                                  │                                │
     │                                  │  8. Find user by email         │
     │                                  ├───────────────────────────────>│
     │                                  │<───────────────────────────────┤
     │                                  │                                │
     │                                  │  9. Verify password (BCrypt)   │
     │                                  │                                │
     │                                  │  10. Generate JWT token        │
     │                                  │                                │
     │  11. Return {token, email, role} │                                │
     │<─────────────────────────────────┤                                │
```

### **2. Property Management Flow (Agent)**

```
┌──────────┐                    ┌─────────────┐                    ┌──────────┐
│  Agent   │                    │   Backend   │                    │ Database │
└────┬─────┘                    └──────┬──────┘                    └────┬─────┘
     │                                  │                                │
     │  1. POST /api/properties         │                                │
     │     + JWT Token in Header        │                                │
     │     {title, price, location...}  │                                │
     ├─────────────────────────────────>                                │
     │                                  │                                │
     │                                  │  2. Validate JWT token         │
     │                                  │  3. Check role = ROLE_agent    │
     │                                  │  4. Extract agent ID           │
     │                                  │                                │
     │                                  │  5. Validate property data     │
     │                                  │                                │
     │                                  │  6. Set agent ID on property   │
     │                                  │                                │
     │                                  │  7. Save property              │
     │                                  ├───────────────────────────────>│
     │                                  │<───────────────────────────────┤
     │                                  │                                │
     │  8. Return saved property        │                                │
     │<─────────────────────────────────┤                                │
```

### **3. Property Search & View Flow (Client)**

```
┌──────────┐                    ┌─────────────┐                    ┌──────────┐
│  Client  │                    │   Backend   │                    │ Database │
└────┬─────┘                    └──────┬──────┘                    └────┬─────┘
     │                                  │                                │
     │  1. GET /api/properties          │                                │
     │     ?minPrice=1000&type=rent     │                                │
     ├─────────────────────────────────>                                │
     │                                  │                                │
     │                                  │  2. Parse search parameters    │
     │                                  │                                │
     │                                  │  3. Query properties           │
     │                                  │     with filters               │
     │                                  ├───────────────────────────────>│
     │                                  │<───────────────────────────────┤
     │                                  │                                │
     │  4. Return filtered properties   │                                │
     │<─────────────────────────────────┤                                │
     │                                  │                                │
     │  5. POST /api/favorites/{id}     │                                │
     │     + JWT Token                  │                                │
     ├─────────────────────────────────>                                │
     │                                  │                                │
     │                                  │  6. Validate token             │
     │                                  │  7. Check role = ROLE_client   │
     │                                  │  8. Extract client ID          │
     │                                  │                                │
     │                                  │  9. Check if already favorited │
     │                                  ├───────────────────────────────>│
     │                                  │<───────────────────────────────┤
     │                                  │                                │
     │                                  │  10. Create favorite entry     │
     │                                  ├───────────────────────────────>│
     │                                  │<───────────────────────────────┤
     │                                  │                                │
     │  11. Return favorite record      │                                │
     │<─────────────────────────────────┤                                │
```

### **4. Request Authentication Flow (JWT)**

```
Request Flow:
1. Client sends request with JWT token in Authorization header:
   Authorization: Bearer <jwt_token>

2. JwtAuthenticationFilter intercepts request:
   ├─ Extract token from header
   ├─ Validate token signature and expiration
   ├─ Extract user email from token
   ├─ Load user details from database
   └─ Set authentication context

3. SecurityConfig checks:
   ├─ Is endpoint public? → Allow
   ├─ Is user authenticated? → Check @PreAuthorize
   └─ Does user have required role? → Allow/Deny

4. Controller method executes

5. Response returned to client
```

---

## 📡 API Endpoints Summary

### **Authentication Endpoints**
| Method | Endpoint | Auth Required | Role Required | Description |
|--------|----------|---------------|---------------|-------------|
| POST | `/api/auth/register` | ❌ No | - | Register new user |
| POST | `/api/auth/login` | ❌ No | - | Login and get JWT token |
| GET | `/api/auth/me` | ✅ Yes | - | Get current user |

### **Property Endpoints**
| Method | Endpoint | Auth Required | Role Required | Description |
|--------|----------|---------------|---------------|-------------|
| POST | `/api/properties` | ✅ Yes | ROLE_agent | Create property |
| GET | `/api/properties` | ❌ No | - | Get all properties (with filters) |
| GET | `/api/properties/{id}` | ❌ No | - | Get property by ID |
| GET | `/api/properties/agent/{agentId}` | ❌ No | - | Get properties by agent |
| PUT | `/api/properties/{id}` | ✅ Yes | ROLE_agent | Update property |
| DELETE | `/api/properties/{id}` | ✅ Yes | ROLE_agent | Delete property |

### **Favorites Endpoints**
| Method | Endpoint | Auth Required | Role Required | Description |
|--------|----------|---------------|---------------|-------------|
| POST | `/api/favorites/{propertyId}` | ✅ Yes | ROLE_client | Add to favorites |
| GET | `/api/favorites` | ✅ Yes | ROLE_client | Get user's favorites |
| DELETE | `/api/favorites/{propertyId}` | ✅ Yes | ROLE_client | Remove favorite |

### **Profile Endpoints**
| Method | Endpoint | Auth Required | Role Required | Description |
|--------|----------|---------------|---------------|-------------|
| GET | `/api/users/profile` | ✅ Yes | - | Get own profile |
| PUT | `/api/users/profile` | ✅ Yes | - | Update own profile |

### **Upload Endpoints**
| Method | Endpoint | Auth Required | Role Required | Description |
|--------|----------|---------------|---------------|-------------|
| POST | `/api/upload` | ✅ Yes | - | Upload image file |

---

## 🔐 Security Model

### **Roles**
- **ROLE_agent** - Can create, update, delete properties (own properties only)
- **ROLE_client** - Can view properties, add favorites, manage profile

### **Authentication**
- JWT tokens issued on login (1 hour expiration)
- Token sent in `Authorization: Bearer <token>` header
- Stateless authentication (no server-side session)

### **Password Security**
- Passwords encrypted with BCrypt (10 rounds)
- Passwords never returned in API responses

### **Public Endpoints**
- `/api/auth/register` - Registration
- `/api/auth/login` - Login
- `/api/properties` (GET) - View properties
- `/api/properties/{id}` (GET) - View single property
- Swagger UI and API docs

### **Protected Endpoints**
- All other endpoints require valid JWT token
- Method-level security via `@PreAuthorize` annotations

---

## 📊 Database Schema

### **users** table
- `id` (PK, Auto-increment)
- `email` (Unique, Not Null)
- `password` (Not Null, Encrypted)
- `role` (Not Null)
- `name`
- `enabled` (Boolean, Default: true)
- `created_at` (Timestamp)

### **properties** table
- `id` (PK, Auto-increment)
- `title` (Not Null)
- `description` (TEXT)
- `price` (Decimal, Not Null)
- `location` (Not Null)
- `type` (Not Null) - 'rent' or 'buy'
- `agent_id` (FK → users.id, Not Null)
- `created_at` (Timestamp)

### **property_images** table (ElementCollection)
- `property_id` (FK → properties.id)
- `image_url`

### **favorites** table
- `id` (PK, Auto-increment)
- `property_id` (FK → properties.id, Not Null)
- `client_id` (FK → users.id, Not Null)
- `created_at` (Timestamp)
- Unique constraint on (property_id, client_id)

---

## 🚀 Application Flow Summary

1. **Startup**
   - Spring Boot application starts
   - Database connection established (MySQL)
   - Security configuration loaded
   - JWT filter registered

2. **User Registration**
   - Client sends registration data
   - Email uniqueness validated
   - Password encrypted
   - User saved to database

3. **User Login**
   - Credentials validated
   - Password verified (BCrypt)
   - JWT token generated and returned

4. **Authenticated Requests**
   - JWT token extracted from header
   - Token validated
   - User context set
   - Authorization checked (role-based)
   - Business logic executed
   - Response returned

5. **Property Operations**
   - Agents can create/manage properties
   - Clients can view and favorite properties
   - Search/filter capabilities available

---

## 📝 Notes

- All timestamps are in `LocalDateTime` format
- Database uses Hibernate's `ddl-auto: update` for schema management
- File upload limit: 10MB per file
- CORS enabled for `localhost:3000` (frontend integration)
- API documentation available at `/swagger-ui.html`
- Server runs on port `8080` by default

