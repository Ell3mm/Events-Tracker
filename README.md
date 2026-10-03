# Event-Tracker

An Edinburgh events and venues tracker for discovering festivals, gigs, and meetups. The project is being built as a learning portfolio project using Java, Spring Boot, PostgreSQL, and a plain HTML, CSS, and JavaScript frontend.

**Author:** Lewis McDonald  
**Started:** 2026-10-03

## Project Status

This repository is currently a scaffold. The Maven project configuration is in place and `mvn test` completes successfully, but the application classes, database schema, frontend, and deployment files are still placeholders. No event API or web page is operational yet.

## Planned Features

- Create, read, update, and delete events with validation and useful error responses.
- Store events, venues, categories, users, and saved events in PostgreSQL with relationships and indexes.
- Search and filter by date, category, and price, with paginated results.
- Register and sign in with Spring Security and JWT; let users save events.
- Browse and filter events from a responsive HTML, CSS, and JavaScript page.
- Import public Edinburgh event data and cache the results.
- Test services with JUnit and Mockito and test REST endpoints with integration tests.
- Run the app and database with Docker Compose, then prepare a free-host deployment.

## Technology

- Java 23
- Spring Boot 4.0.0 and Maven
- PostgreSQL with Spring Data JPA
- HTML, CSS, and plain JavaScript
- JUnit and Mockito through Spring Boot's test dependencies
- Docker and Docker Compose planned for local database and application setup

## Project Layout

```text
backend/
	src/main/java/       API, domain, persistence, security, and service code
	src/main/resources/  Application configuration and database schema
	src/test/java/       Unit and integration tests
	pom.xml              Maven project configuration
frontend/              Browser page, styles, and JavaScript
docker-compose.yml     Planned local services
render.yaml            Planned Render deployment configuration
```

Most files under these folders are currently explanatory placeholders. They describe where planned code will live; they do not implement those features yet.

## Prerequisites

- JDK 23
- Apache Maven 3.10 or newer
- PostgreSQL or Docker for the database, once local database configuration is implemented

Check the installed Java and Maven versions:

```powershell
java -version
mvn -version
```

## Build and Test

From the repository root, run:

```powershell
mvn -f backend/pom.xml test
```

This checks the current Maven setup and compiles available source and test files. There are no implemented application or test classes yet, so a successful command does not mean the planned features have been tested.

## Development Roadmap

1. Create the Spring Boot application and the first event API endpoint.
2. Add event, venue, and category database models and event CRUD operations.
3. Add validation, consistent error responses, search, filters, and pagination.
4. Add registration, login, JWT security, and saved events.
5. Connect the frontend to the API.
6. Add an external Edinburgh events data source and caching.
7. Write unit and integration tests.
8. Complete Docker setup and deploy the working application.