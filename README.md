# User Microservice with Bearer Token Authentication and 2FA

This is a Spring Boot microservice that provides user management with JWT-based Bearer token authentication and Two-Factor Authentication (2FA) using TOTP.

## Features

- User registration and login
- JWT-based Bearer token authentication
- Two-Factor Authentication (2FA) using Google Authenticator TOTP
- RESTful API endpoints for user management
- In-memory H2 database (can be easily switched to a persistent database)

## Technologies Used

- Spring Boot 3.5.6
- Spring Security
- Spring Data JPA
- JWT (JSON Web Tokens) - io.jsonwebtoken:jjwt
- Google Authenticator (warrenstrange:googleauth)
- H2 Database
- Lombok

## Building and Running

### Build the application
```bash
./gradlew build
```

### Run the application
```bash
./gradlew bootRun
```

The application will start on port 8080.

### Using Docker
```bash
docker build -t teste-docker .
docker run -p 8080:8080 teste-docker
```

## API Endpoints

### Public Endpoints (No authentication required)

#### Health Check
```bash
GET /health
GET /hello
```

#### Register a new user
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123"
}
```

#### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "johndoe",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "username": "johndoe",
  "email": "john@example.com",
  "twoFactorEnabled": false,
  "requiresTwoFactor": false,
  "message": null
}
```

#### Login with 2FA (when enabled)
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "johndoe",
  "password": "password123",
  "twoFactorCode": "123456"
}
```

### Protected Endpoints (Require Bearer token authentication)

All protected endpoints require an `Authorization` header with a Bearer token:
```
Authorization: Bearer {token}
```

#### Get current user
```bash
GET /api/users/me
Authorization: Bearer {token}
```

#### Get all users
```bash
GET /api/users
Authorization: Bearer {token}
```

#### Get user by ID
```bash
GET /api/users/{id}
Authorization: Bearer {token}
```

#### Delete user
```bash
DELETE /api/users/{id}
Authorization: Bearer {token}
```

#### Enable 2FA
```bash
POST /api/users/2fa/enable
Authorization: Bearer {token}
```

Response:
```json
{
  "secret": "U4FFJK3QNVGF3UPSG42RPQJASLR2XZZQ",
  "qrCodeUrl": "https://api.qrserver.com/v1/create-qr-code/?data=otpauth://totp/..."
}
```

Scan the QR code with Google Authenticator or any TOTP-compatible app.

#### Disable 2FA
```bash
POST /api/users/2fa/disable
Authorization: Bearer {token}
```

## 2FA Setup Guide

1. Register a new user using `/api/auth/register`
2. Login to get a JWT token using `/api/auth/login`
3. Enable 2FA using `/api/users/2fa/enable` with the Bearer token
4. Scan the QR code from the response using Google Authenticator or Authy
5. For subsequent logins, provide the 6-digit code from your authenticator app

## Configuration

The application can be configured in `src/main/resources/application.properties`:

```properties
# JWT Configuration
jwt.secret=mySecretKeyForJWTAuthenticationAndAuthorizationThatMustBeAtLeast256BitsLong
jwt.expiration=86400000  # 24 hours in milliseconds

# Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
```

## Security

- Passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours (configurable)
- 2FA uses TOTP (Time-based One-Time Password) algorithm
- Protected endpoints require valid JWT Bearer tokens
- Public endpoints: `/api/auth/**`, `/hello`, `/health`
- All other endpoints require authentication

## Database

The application uses an in-memory H2 database by default. The H2 console is available at:
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (empty)
```

## Testing Examples

### Register a user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","email":"demo@example.com","password":"demo123"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","password":"demo123"}'
```

### Access protected endpoint
```bash
TOKEN="your-jwt-token-here"
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer $TOKEN"
```

### Enable 2FA
```bash
TOKEN="your-jwt-token-here"
curl -X POST http://localhost:8080/api/users/2fa/enable \
  -H "Authorization: Bearer $TOKEN"
```

## License

This project is open source and available under the MIT License.
