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
* Java 21+
* Maven as build tool

#### Installation
1. Clone the repository
2. Build the project with Maven
3. Package the project with Maven
```
mvn -B package --file pom.xml
```

#### Running Application
The application can be run locally with H2 database or PostgreSQL database:
1. Run with H2 database without Twilio Notification using `application-local.yml`
```
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

2. Run with PostgreSQL with Twilio Notification using `application.yml`
* Install [Docker Desktop](https://www.docker.com/products/docker-desktop/), [Docker CLI](https://docs.docker.com/engine/cli/completion/) and [Docker Compose](https://docs.docker.com/compose/install)
* Start PostgreSQL and pgAdmin docker containers
```
cd postgresql
docker compose up
```

* Open pgAdmin http://localhost:15433 and log in using the email and password specified in `docker-compose.yml`.
* Click on “Add New Server” under the “Quick Links” section. 
* In the “General” tab, enter a name for the server. 
* In the “Connection” tab, use the following details:
```
Host name/address: postgres
Username: The user specified in docker-compose.yml file
Password: The password specified in the docker-compose.yml file
```

* Click “Save” to add the server
* Register [Twilio](https://www.twilio.com/de-de) account to get account id, token and phone number
* Put your Twilio account id, token & phone number and enable notification in `application.yml`
* Run application
```
mvn spring-boot:run
```

* Check all the data stored in PostgreSQL database at `{serverName} -> Schemas -> public -> Tables`
* Once you finish running the application, do not forget to shutdown PostgreSQL & pgAdmin container
```
cd postgresql
docker compose down
```

#### Running Tests
```
mvn test
```

### API Documentation
* [OpenAPI Definition](http://localhost:8080/v3/api-docs)
* [Swagger API Documentation](http://localhost:8080/swagger-ui/index.html)

### Contributing
1. Fork the repository.
2. Create a feature branch `git checkout -b feature-branch`. 
3. Commit your changes `git commit -m 'Add new feature'`. 
4. Push to the branch `git push origin feature-branch`. 
5. Create a Pull Request.
