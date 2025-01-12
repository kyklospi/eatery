# Eatery Backend

## Project Overview
Eatery Backend project is a Spring Boot-based backend designed to serve as the core application layer for 
managing customer eatery reservations, its data persistence, API services and notification about changes in their reservation status.
It is built following modern architectural patterns 
such as RESTful services, MVC, and Layered Architecture to ensure scalability, maintainability, and ease of testing.

The backend integrates with an H2 and PostgreSQL database through JPA for data persistence, and Spring Data for simplifying data access.

### Key Features
* REST API: Provides a set of RESTful endpoints for interacting with the backend services.
* Spring Data JPA: ORM-based database interaction with support for various relational databases (PostgreSQL, H2).
* Notification: Sending of notification using Twilio Java API.
* Exception Handling: Global exception handling using @ControllerAdvice.
* Logging: Integrated logging using SLF4J with Logback.
* Test Coverage: Integration tests using SpringBoot tests and JUnit.
* API Documentation: Auto-generated API documentation using Swagger (SpringDoc OpenAPI).

### Getting Started
#### Prerequisites
* Java 17+
* Maven as build tool

#### Installation
1. Clone the repository
2. Build the project with Maven
3. Run the application
```
mvn spring-boot:run
```

#### Running Tests
```
mvn test
```

### API Documentation
* [OpenAPI Definition](http://localhost:8080/v3/api-docs)
* [Swagger API Documentation](http://localhost:8080/swagger-ui/index.html)

### Git-Crypt
* Git-Crypt must be installed on your machine
* Unlock repository (for decryption):
```
git crypt unlock
```
* Check which files are encrypted:
```
git crypt status
```

### Contributing
1. Fork the repository.
2. Create a feature branch `git checkout -b feature-branch`. 
3. Commit your changes `git commit -m 'Add new feature'`. 
4. Push to the branch `git push origin feature-branch`. 
5. Create a Pull Request.