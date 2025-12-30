# Complete API Testing Guide - Postman

## 🚀 Quick Start

### Step 1: Start the Server
```bash
cd backend
$env:DB_PASSWORD='saiju5d5'
mvn spring-boot:run
```

### Step 2: Test Authentication

#### Register a User
```
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "password123",
  "roles": ["ADMIN"]
}
```

**Expected Response:**
```json
{
  "id": 1,
  "email": "admin@example.com",
  "enabled": true,
  "roles": ["ADMIN"]
}
```
✅ Password is NOT returned (security)

#### Login
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "password123"
}
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "admin@example.com",
  "roles": ["ADMIN"],
  "userId": 1
}
```

**⚠️ Copy the token!**

---

## 📋 All API Endpoints

### Authentication (Public - No Token)

#### 1. Register User
```
POST /api/auth/register
Content-Type: application/json

Body:
{
  "email": "user@example.com",
  "password": "password123",
  "roles": ["CUSTOMER"]  // Optional, defaults to CUSTOMER
}
```

**Test Cases:**
- ✅ Valid registration
- ✅ Duplicate email (should return 400)
- ✅ Invalid email format (should return 400)
- ✅ Password too short (should return 400)

#### 2. Login
```
POST /api/auth/login
Content-Type: application/json

Body:
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Test Cases:**
- ✅ Valid credentials
- ✅ Invalid email (should return 404)
- ✅ Invalid password (should return 401)
- ✅ Disabled account (should return 401)

---

### Properties (Protected - Token Required)

**Add Header:**
```
Authorization: Bearer <your-token>
```

#### 1. Get All Properties
```
GET /api/properties
Authorization: Bearer <token>
```

**Roles:** ADMIN, AGENT, CUSTOMER

#### 2. Get Property by ID
```
GET /api/properties/1
Authorization: Bearer <token>
```

**Roles:** ADMIN, AGENT, CUSTOMER

#### 3. Create Property (ADMIN only)
```
POST /api/properties
Authorization: Bearer <token>
Content-Type: application/json

Body:
{
  "title": "Beautiful House",
  "city": "New York",
  "price": 500000.0,
  "type": "HOUSE",
  "status": "AVAILABLE"
}
```

**Property Types:**
- APARTMENT
- HOUSE
- VILLA
- CONDO
- TOWNHOUSE
- LAND
- COMMERCIAL
- OFFICE

**Property Status:**
- AVAILABLE
- SOLD
- RENTED
- PENDING
- UNAVAILABLE

#### 4. Update Property (ADMIN only)
```
PUT /api/properties/1
Authorization: Bearer <token>
Content-Type: application/json

Body:
{
  "title": "Updated Title",
  "city": "Los Angeles",
  "price": 600000.0,
  "type": "VILLA",
  "status": "SOLD"
}
```

#### 5. Delete Property (ADMIN only)
```
DELETE /api/properties/1
Authorization: Bearer <token>
```

---

### Users (Protected - ADMIN only)

**Add Header:**
```
Authorization: Bearer <token>
```

#### 1. Get All Users
```
GET /api/users
Authorization: Bearer <token>
```

**Roles:** ADMIN only

#### 2. Get User by ID
```
GET /api/users/1
Authorization: Bearer <token>
```

**Roles:** ADMIN only

#### 3. Update User (ADMIN only)
```
PUT /api/users/1
Authorization: Bearer <token>
Content-Type: application/json

Body:
{
  "email": "newemail@example.com",  // Optional
  "password": "newpassword123",     // Optional - only updates if provided
  "roles": ["ADMIN", "AGENT"],      // Optional
  "enabled": true                    // Optional
}
```

**Note:** All fields are optional. Only provided fields will be updated.

#### 4. Delete User (ADMIN only)
```
DELETE /api/users/1
Authorization: Bearer <token>
```

---

### Bookings (Protected - Token Required)

**Add Header:**
```
Authorization: Bearer <token>
```

#### 1. Create Booking
```
POST /api/bookings
Authorization: Bearer <token>
Content-Type: application/json

Body:
{
  "propertyId": 1,
  "userId": 1,
  "visitDate": "2024-01-15",
  "status": "PENDING"  // Optional, defaults to PENDING
}
```

**Roles:** ADMIN, AGENT, CUSTOMER, OWNER

**Booking Status:**
- PENDING
- APPROVED
- REJECTED
- COMPLETED
- CANCELLED

#### 2. Get All Bookings
```
GET /api/bookings
Authorization: Bearer <token>
```

**Roles:** ADMIN, AGENT

#### 3. Get Booking by ID
```
GET /api/bookings/1
Authorization: Bearer <token>
```

**Roles:** ADMIN, AGENT, CUSTOMER, OWNER

#### 4. Get Bookings by User ID
```
GET /api/bookings/user/1
Authorization: Bearer <token>
```

**Roles:** ADMIN, AGENT

#### 5. Get Bookings by Property ID
```
GET /api/bookings/property/1
Authorization: Bearer <token>
```

**Roles:** ADMIN, AGENT, OWNER

#### 6. Get Bookings by Status
```
GET /api/bookings/status/PENDING
Authorization: Bearer <token>
```

**Roles:** ADMIN, AGENT

#### 7. Update Booking Status
```
PUT /api/bookings/1/status?status=APPROVED
Authorization: Bearer <token>
```

**Roles:** ADMIN, AGENT

#### 8. Update Booking
```
PUT /api/bookings/1
Authorization: Bearer <token>
Content-Type: application/json

Body:
{
  "visitDate": "2024-01-20",
  "status": "APPROVED"
}
```

**Roles:** ADMIN, AGENT

#### 9. Delete Booking
```
DELETE /api/bookings/1
Authorization: Bearer <token>
```

**Roles:** ADMIN, AGENT

---

## 🔍 Error Responses

### 400 Bad Request
```json
{
  "field": "error message"
}
```
or
```json
{
  "success": false,
  "message": "Email already exists: user@example.com"
}
```

### 401 Unauthorized
```json
{
  "success": false,
  "message": "Invalid credentials"
}
```
or
```json
{
  "success": false,
  "message": "Token has expired. Please login again."
}
```

### 403 Forbidden
```json
{
  "success": false,
  "message": "Access denied. You don't have permission to perform this action."
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "Property not found with id: 1"
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "Internal Server Error: [detailed message]"
}
```

---

## ✅ Complete Test Flow

### 1. Setup
1. Start server
2. Register admin user
3. Login and get token

### 2. Test Properties
1. Create property (as ADMIN)
2. Get all properties
3. Get property by ID
4. Update property
5. Delete property

### 3. Test Users
1. Register another user
2. Get all users (as ADMIN)
3. Update user
4. Delete user

### 4. Test Bookings
1. Create booking (use property and user IDs)
2. Get all bookings
3. Get booking by ID
4. Update booking status
5. Delete booking

---

## 🎯 Postman Collection Setup

### Environment Variables
Create a Postman environment with:
- `base_url`: `http://localhost:8080`
- `token`: `<your-jwt-token>`

### Collection-Level Authorization
1. Create a collection
2. Go to Authorization tab
3. Type: Bearer Token
4. Token: `{{token}}`

All requests will automatically use the token!

---

## 🔒 Security Features

✅ Passwords never returned in responses
✅ JWT token authentication
✅ Role-based access control
✅ Input validation
✅ Error messages don't leak sensitive info
✅ CORS properly configured
✅ Account status checks

---

## 📝 Notes

- Tokens expire after 1 hour
- All protected endpoints require `Authorization: Bearer <token>` header
- Password updates are optional in user update
- Email must be unique
- All enum values must match exactly (case-sensitive)

---

## 🚨 Common Issues & Solutions

### Issue: 403 Forbidden
**Solution:** 
- Check token is included in Authorization header
- Verify token hasn't expired
- Ensure user has required role

### Issue: 400 Bad Request
**Solution:**
- Check request body format (must be valid JSON)
- Verify all required fields are present
- Check enum values are correct

### Issue: 500 Internal Server Error
**Solution:**
- Check server logs for detailed error
- Verify database is running
- Check all required fields are provided

### Issue: Token Expired
**Solution:**
- Login again to get new token
- Tokens expire after 1 hour

---

All APIs are now fully functional and ready for testing! 🎉

