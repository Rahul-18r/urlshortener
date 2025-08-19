# URL Shortener Service (Spring Boot)

Build a web application similar to Bitly or TinyURL. Users submit a long URL and receive a unique, shortened URL. When the short URL is accessed, the service redirects the user to the original long URL and tracks click counts.

> Built as part of the **ProjectAI — URL Shortener Service** (Full-Stack Spring Boot, 94 tasks, *basic*).

---

## ✨ Features
- Shorten any valid long URL into a compact code (e.g., `https://your.app/abc123`).
- **302 Found** redirect from short URL to original URL.
- **Click tracking** for each short link.
- RESTful API with JSON responses.
- Basic frontend using **Thymeleaf** for submitting and viewing links.
- Persistence via **Spring Data JPA** (H2 by default; can switch to MySQL/PostgreSQL).
- Collision-safe short code generation (Base62-style).
- Validation for URL format and optional expiration.

## 🎯 Learning Outcomes (from ProjectAI)
- Core Spring Boot concepts (MVC, REST Controllers)
- Spring Data JPA for database interaction
- HTTP status codes (especially **302** for redirection)
- Algorithm design for generating **unique short codes**
- Basic frontend integration with **Thymeleaf**

---

## 🧰 Tech Stack
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database** (dev) / MySQL or PostgreSQL (prod)
- **Maven** (or Gradle)

---

## 🚀 Getting Started (Local, no Docker)

### Prerequisites
- Java 17+
- Maven 3.9+ (or Gradle 8+)
- Git

### Clone
```bash
git clone <your-repo-url>.git
cd url-shortener
Configuration

Default: H2 in-memory database.
Optional src/main/resources/application.yml:

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
  base-url: http://localhost:8080
  code-length: 7
  max-retries: 5

Build & Run
# Maven
mvn clean package
java -jar target/url-shortener-0.0.1-SNAPSHOT.jar

# Or run directly
mvn spring-boot:run


Open:

UI → http://localhost:8080/

H2 console (dev) → http://localhost:8080/h2-console

🧭 API Endpoints
1. Shorten a URL

POST /api/v1/shorten
Request:

{
  "longUrl": "https://example.com/some/very/long/path",
  "customCode": "myalias",
  "expireAt": "2025-12-31T23:59:59Z"
}


Response 201 Created:

{
  "code": "abc1234",
  "shortUrl": "http://localhost:8080/abc1234",
  "longUrl": "https://example.com/some/very/long/path",
  "clicks": 0
}

2. Redirect

GET /{code} → 302 Found → redirects to long URL.
Errors: 404 Not Found / 410 Gone

3. Stats

GET /api/v1/links/{code} → JSON stats about link.

🖥️ Frontend (Thymeleaf)

Basic form at / for submitting long URLs.

Displays shortened link + copy button.

Table view of recent links with click counts.

✅ Tests

Run:

mvn test


Includes:

Service tests

Controller redirect tests

Repository tests with H2

📁 Project Structure
src/main/java/...
  └─ com.example.shortener
       ├─ controller
       ├─ service
       ├─ repository
       ├─ model
       ├─ dto
       └─ ShortenerApplication.java
src/main/resources/
  ├─ templates/
  ├─ static/
  └─ application.yml

📜 License
