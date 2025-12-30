# Postman Testing Guide

## ✅ CORS Issue Fixed!

The 403 Forbidden error has been fixed. The server now:
- Allows all origins (including Postman)
- Properly configured CORS in Spring Security
- Accepts all HTTP methods and headers

## 🔐 Authentication Setup

### Step 1: Register a User (Optional)

**Endpoint**: `POST http://localhost:8080/api/auth/register`

**Headers**:
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "email": "admin@example.com",
  "password": "password123",
  "roles": ["ADMIN"]
}
```

### Step 2: Login to Get JWT Token

**Endpoint**: `POST http://localhost:8080/api/auth/login`

**Headers**:
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "email": "admin@example.com",
  "password": "password123"
}
```

**Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "admin@example.com",
  "roles": ["ADMIN"]
}
```

**⚠️ IMPORTANT**: Copy the `token` value from the response!

### Step 3: Use JWT Token in Protected Endpoints

For all protected endpoints, add the JWT token to the Authorization header:

**Headers**:
```
Authorization: Bearer <your-jwt-token>
Content-Type: application/json
```

**Example**:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 📋 API Endpoints

### Public Endpoints (No Auth Required)

1. **Register User**
   - `POST /api/auth/register`
   - No token needed

2. **Login**
   - `POST /api/auth/login`
   - No token needed

### Protected Endpoints (JWT Token Required)

#### Properties

1. **Get All Properties**
   - `GET /api/properties`
   - Roles: ADMIN, AGENT, CUSTOMER
   - Headers: `Authorization: Bearer <token>`

2. **Get Property by ID**
   - `GET /api/properties/{id}`
   - Roles: ADMIN, AGENT, CUSTOMER
   - Headers: `Authorization: Bearer <token>`

3. **Create Property**
   - `POST /api/properties`
   - Role: ADMIN only
   - Headers: `Authorization: Bearer <token>`, `Content-Type: application/json`
   - Body:
   ```json
   {
     "title": "Beautiful House",
     "city": "New York",
     "price": 500000.0,
     "type": "HOUSE",
     "status": "AVAILABLE"
   }
   ```

4. **Update Property**
   - `PUT /api/properties/{id}`
   - Role: ADMIN only
   - Headers: `Authorization: Bearer <token>`, `Content-Type: application/json`

5. **Delete Property**
   - `DELETE /api/properties/{id}`
   - Role: ADMIN only
   - Headers: `Authorization: Bearer <token>`

#### Users

1. **Get All Users**
   - `GET /api/users`
   - Role: ADMIN only
   - Headers: `Authorization: Bearer <token>`

2. **Get User by ID**
   - `GET /api/users/{id}`
   - Role: ADMIN only
   - Headers: `Authorization: Bearer <token>`

3. **Update User**
   - `PUT /api/users/{id}`
   - Role: ADMIN only
   - Headers: `Authorization: Bearer <token>`, `Content-Type: application/json`

4. **Delete User**
   - `DELETE /api/users/{id}`
   - Role: ADMIN only
   - Headers: `Authorization: Bearer <token>`

#### Bookings

1. **Get All Bookings**
   - `GET /api/bookings`
   - Roles: ADMIN, AGENT
   - Headers: `Authorization: Bearer <token>`

2. **Create Booking**
   - `POST /api/bookings`
   - Roles: ADMIN, AGENT, CUSTOMER, OWNER
   - Headers: `Authorization: Bearer <token>`, `Content-Type: application/json`
   - Body:
   ```json
   {
     "property": { "id": 1 },
     "user": { "id": 1 },
     "visitDate": "2024-01-15",
     "status": "PENDING"
   }
   ```

## 🎯 Postman Collection Setup

### Option 1: Manual Setup

1. Create a new request
2. Set the URL (e.g., `http://localhost:8080/api/properties`)
3. Go to **Headers** tab
4. Add:
   - Key: `Authorization`
   - Value: `Bearer <your-token>`
5. Add:
   - Key: `Content-Type`
   - Value: `application/json`

### Option 2: Environment Variables (Recommended)

1. Create a Postman Environment
2. Add variables:
   - `base_url`: `http://localhost:8080`
   - `token`: `<your-jwt-token>`
3. Use in requests:
   - URL: `{{base_url}}/api/properties`
   - Authorization header: `Bearer {{token}}`

### Option 3: Collection-Level Authorization

1. Create a Postman Collection
2. Go to Collection settings → **Authorization**
3. Type: **Bearer Token**
4. Token: `{{token}}` (use environment variable)
5. All requests in the collection will automatically use this token

## 🔍 Property Type & Status Values

### Property Types (enum):
- `APARTMENT`
- `HOUSE`
- `VILLA`
- `CONDO`
- `TOWNHOUSE`
- `LAND`
- `COMMERCIAL`
- `OFFICE`

### Property Status (enum):
- `AVAILABLE`
- `SOLD`
- `RENTED`
- `PENDING`
- `UNAVAILABLE`

### Booking Status (enum):
- `PENDING`
- `APPROVED`
- `REJECTED`
- `COMPLETED`
- `CANCELLED`

## ⚠️ Common Issues

### 403 Forbidden
- ✅ **FIXED**: CORS is now properly configured
- Check if JWT token is included in Authorization header
- Verify token hasn't expired (tokens expire after 1 hour)
- Ensure user has the required role

### 401 Unauthorized
- JWT token is missing or invalid
- Token has expired - login again to get a new token
- Token format is wrong - should be `Bearer <token>`

### 400 Bad Request
- Check request body format (must be valid JSON)
- Verify required fields are present
- Check enum values are correct (e.g., PropertyType, PropertyStatus)

### 500 Internal Server Error
- Check server logs
- Verify database is running and accessible
- Ensure database `rems_db` exists

## 🧪 Quick Test Flow

1. **Start Server**: `mvn spring-boot:run`
2. **Register/Login**: Get JWT token
3. **Test Protected Endpoint**: 
   - `GET /api/properties` with `Authorization: Bearer <token>`
4. **Should return**: List of properties (or empty array if none)

## 📝 Notes

- JWT tokens expire after 1 hour
- All protected endpoints require authentication
- Role-based access control is enforced
- CORS is configured to allow all origins (development mode)

