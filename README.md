# 🔗 URL Shortener Service

A simple yet powerful web application built with Spring Boot and Thymeleaf that allows users to shorten long URLs (similar to Bitly or TinyURL), generate unique short links, and track click statistics. The service provides both a user-friendly web interface and a comprehensive REST API for programmatic access.

## 🌟 Features

- **URL Shortening**: Transform long URLs into compact, shareable links (`http://localhost:8080/abc123`)
- **Smart Redirects**: Seamless redirection using HTTP 302 Found status codes
- **Click Analytics**: Track and monitor click statistics for each shortened link
- **Dual Interface**: Both REST API (JSON) and Thymeleaf frontend available
- **Custom Aliases**: Support for personalized short codes
- **Expiration Control**: Optional time-based expiration for shortened URLs
- **Robust Persistence**: Spring Data JPA with H2 (development) and MySQL/PostgreSQL (production)

## 🎯 Learning Outcomes

This project is part of **ProjectAI** → *URL Shortener Service* challenge featuring 94 comprehensive tasks covering full-stack Spring Boot development.

**Key Skills Developed:**
- Spring Boot MVC & REST Controllers
- Spring Data JPA for database operations
- HTTP status code implementation (`302 Found`)
- Unique short code generation algorithms
- Frontend integration with Thymeleaf templates
- Database design and optimization

## 🛠️ Technology Stack

| Component | Technology |
|-----------|------------|
| **Backend** | Java 17+, Spring Boot 3.x |
| **Database** | H2 (development), MySQL/PostgreSQL (production) |
| **ORM** | Spring Data JPA |
| **Frontend** | Thymeleaf Templates |
| **Build Tool** | Maven 3.9+ |
| **Testing** | JUnit 5, Spring Boot Test |

## 🚀 Quick Start Guide

### Prerequisites

Ensure you have the following installed:
- ☕ **Java 17+**
- 🔧 **Maven 3.9+**
- 📦 **Git**

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/rahul-18r/url-shortener.git
   cd url-shortener
   ```

2. **Build the Project**
   ```bash
   mvn clean package
   ```

3. **Run the Application**
   ```bash
   java -jar target/url-shortener-0.0.1-SNAPSHOT.jar
   ```
   
   *Alternatively, run directly with Maven:*
   ```bash
   mvn spring-boot:run
   ```

4. **Access the Application**
   - 🌐 **Web UI**: http://localhost:8080/
   - 🗄️ **H2 Console**: http://localhost:8080/h2-console

## ⚙️ Configuration

The application uses an in-memory H2 database by default. Customize settings in `src/main/resources/application.yml`:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:shortener;DB_CLOSE_DELAY=-1;MODE=PostgreSQL
    driver-class-name: org.h2.Driver
    username: sa
    password: ""
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  h2:
    console:
      enabled: true
      path: /h2-console

app:
  base-url: http://localhost:8080/
  code-length: 7
  max-retries: 5
```

## 📡 API Documentation

### 1. Shorten URL

**Endpoint:** `POST /api/v1/url/shorten`

**Request Body:**
```json
{
  "url": "https://example.com/very/long/path/to/resource",
  "customAlias": "mylink",
  "hoursToExpire": 24
}
```

**Response (201 Created):**
```json
{
  "shortUrl": "http://localhost:8080/mylink"
}
```

### 2. URL Redirection

**Endpoint:** `GET /{shortCode}`

**Behavior:**
- ✅ **Success**: Redirects to original URL with `302 Found`
- ❌ **Not Found**: Returns `404 Not Found` for invalid codes
- ⏰ **Expired**: Returns `410 Gone` for expired links

### 3. Get Statistics

**Endpoint:** `GET /api/v1/url/stats/{shortCode}`

**Response (200 OK):**
```json
{
  "longUrl": "https://example.com/very/long/path/to/resource",
  "shortUrl": "http://localhost:8080/mylink",
  "clicks": 42,
  "createdAt": "2025-08-19T12:00:00",
  "expiresAt": "2025-08-20T12:00:00"
}
```

## 🖥️ Web Interface

The Thymeleaf-powered frontend at `/` provides:

- **URL Shortening Form**: Input long URLs with optional custom aliases
- **Statistics Lookup**: View click analytics by entering short codes
- **Result Display**: Clear presentation of shortened links and statistics
- **Error Handling**: User-friendly error messages and validation

## 🧪 Testing

Run the comprehensive test suite:

```bash
mvn test
```

The test suite includes:
- Unit tests for service layer logic
- Integration tests for REST endpoints
- Repository layer testing
- Custom exception handling verification

## 📁 Project Architecture

```
src/main/java/com/example/urlshortener/
├── controller/          # Web and REST controllers
│   ├── PageController.java     # Thymeleaf frontend controller
│   └── UrlController.java      # REST API endpoints
├── service/             # Business logic layer
│   └── UrlShortenerService.java
├── repository/          # Data access layer
│   └── UrlRepository.java
├── dto/                 # Data transfer objects
│   ├── ShortenRequest.java
│   └── UrlStatsResponse.java
├── exception/           # Custom exception classes
│   ├── UrlNotFoundException.java
│   └── UrlExpiredException.java
└── UrlShortenerApplication.java

src/main/resources/
├── templates/           # Thymeleaf templates
│   └── index.html
├── static/             # Static web assets
│   ├── css/
│   └── js/
└── application.yml     # Application configuration
```

## 🔧 Production Deployment

For production environments, consider:

1. **Database Configuration**: Switch to MySQL or PostgreSQL
2. **Environment Variables**: Use external configuration for sensitive data
3. **Logging**: Configure appropriate log levels
4. **Security**: Implement rate limiting and input validation
5. **Monitoring**: Add health checks and metrics

Example production configuration:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/urlshortener
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

