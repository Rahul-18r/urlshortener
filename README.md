# 🔗 URL Shortener Service (Spring Boot + Thymeleaf)

A simple web application (like Bitly or TinyURL) where users can shorten long URLs, get a unique short link, and track click statistics. This project includes both:
- 🌐 A **Thymeleaf frontend** for end users
- ⚡ A **REST API** for programmatic access

## ✨ Features
- Shorten any valid long URL into a compact code (`http://localhost:8080/abc123`)
- Redirect with **302 Found** to the original URL
- Track **click counts** for each shortened link
- REST API (JSON) and Thymeleaf frontend
- Custom alias support
- Optional expiration for shortened URLs
- Persistence with **Spring Data JPA** (H2 in dev, MySQL/PostgreSQL in prod)

## 🎯 Learning Outcomes
Built as part of **ProjectAI** → *URL Shortener Service* (94 tasks, Full-Stack Spring Boot).  
You’ll gain practical experience with:
- Spring Boot MVC & REST Controllers
- Spring Data JPA for database interaction
- HTTP status codes (`302 Found`)
- Algorithm design for generating **unique short codes**
- Basic frontend integration using **Thymeleaf**

## 🧰 Tech Stack
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database** (dev) / MySQL or PostgreSQL (prod)
- **Thymeleaf** (frontend)
- **Maven** (build tool)

## 🚀 Getting Started (No Docker)

### Prerequisites
- Java 17+
- Maven 3.9+
- Git

### Clone Repository
git clone https://github.com/<your-username>/url-shortener.git  
cd url-shortener

### Configuration
By default, the project uses an **in-memory H2 database**. To customize, edit `src/main/resources/application.yml`:

server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:shortener;DB_CLOSE_DELAY=-1;MODE=PostgreSQL
    driver-class-name: org.h2.Driver
    username: sa
    password:
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

### Build & Run
mvn clean package  
java -jar target/url-shortener-0.0.1-SNAPSHOT.jar  
# Or run directly  
mvn spring-boot:run  

### Open
- UI → http://localhost:8080/  
- H2 Console → http://localhost:8080/h2-console  

## 🧭 API Endpoints

### 1. Shorten a URL
POST /api/v1/url/shorten  
Request:  
{ "url": "https://example.com/long/path", "customAlias": "myalias", "hoursToExpire": 24 }  

Response (201 Created):  
{ "shortUrl": "http://localhost:8080/myalias" }  

### 2. Redirect
GET /{shortCode} → Redirects with 302 Found  
- ✅ Success → Redirect to original URL  
- ❌ Errors → 404 Not Found (invalid code) or 410 Gone (expired)  

### 3. Get Stats
GET /api/v1/url/stats/{shortCode}  
Response:  
{ "longUrl": "https://example.com/long/path", "shortUrl": "http://localhost:8080/myalias", "clicks": 42, "createdAt": "2025-08-19T12:00:00", "expiresAt": "2025-08-20T12:00:00" }  

## 🖥️ Frontend (Thymeleaf)
At `/`, users can:
- Submit a long URL (with optional custom alias)
- Receive a shortened link
- View statistics by entering a short code

The `index.html` page includes:
- Form for URL shortening
- Form for stats lookup
- Display of results or error messages

## ✅ Tests
mvn test  

## 📁 Project Structure
src/main/java/com/example/urlshortener  
 ├─ controller/        # PageController (Thymeleaf) + UrlController (REST)  
 ├─ service/           # Business logic  
 ├─ repository/        # Spring Data JPA repositories  
 ├─ dto/               # Request/Response DTOs  
 ├─ exception/         # Custom exceptions  
 └─ UrlShortenerApplication.java  

src/main/resources/  
 ├─ templates/         # Thymeleaf templates (index.html)  
 ├─ static/            # Static assets  
 └─ application.yml    # Config  

## 📜 License
This project is licensed under the **MIT License**.
