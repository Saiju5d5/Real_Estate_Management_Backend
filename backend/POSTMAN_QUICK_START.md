# Postman Quick Start Guide

## 🚀 Quick Setup

### Step 1: Login and Get Token

**Request:**
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

Body:
{
  "email": "admin@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "admin@example.com",
  "roles": ["ADMIN"]
}
```

**⚠️ Copy the token value!**

### Step 2: Set Authorization Header

For all protected endpoints, add this header:
```
Authorization: Bearer <paste-your-token-here>
```

### Step 3: Test APIs

#### Create a Property (ADMIN only)
```
POST http://localhost:8080/api/properties
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Beautiful House",
  "city": "New York",
  "price": 500000.0,
  "type": "HOUSE",
  "status": "AVAILABLE"
}
```

#### Get All Properties
```
GET http://localhost:8080/api/properties
Authorization: Bearer <token>
```

#### Create a Booking
```
POST http://localhost:8080/api/bookings
Authorization: Bearer <token>
Content-Type: application/json

{
  "propertyId": 1,
  "userId": 1,
  "visitDate": "2024-01-15",
  "status": "PENDING"
}
```

## ✅ All Errors Fixed!

- ✅ 403 Forbidden - CORS properly configured
- ✅ 500 Internal Server Error - Exception handling improved
- ✅ Booking creation - Now uses simple IDs (propertyId, userId)
- ✅ All endpoints return consistent responses

## 📝 Common Issues

### Still Getting 403?
- Make sure you included `Authorization: Bearer <token>` header
- Check token hasn't expired (tokens expire after 1 hour)
- Verify user has required role (ADMIN, AGENT, etc.)

### Still Getting 500?
- Check request body format (must be valid JSON)
- Verify all required fields are present
- Check enum values are correct (PropertyType, PropertyStatus, BookingStatus)
- Look at server logs for detailed error message

### Token Expired?
- Login again to get a new token
- Tokens expire after 1 hour

## 🎯 Test Order

1. Register/Login → Get token
2. Create Property → Get property ID
3. Create Booking → Use property ID and user ID
4. Get Bookings → Verify booking was created

All APIs are now fully functional and testable in Postman!

