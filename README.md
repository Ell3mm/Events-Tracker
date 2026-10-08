# Edinburgh Events and Venues Tracker

A learning portfolio project for browsing and adding Edinburgh festivals, gigs, meetups, and other local events. It uses a Java and Spring Boot backend with a plain HTML, CSS, and JavaScript frontend.

**Author:** Lewis McDonald  
**Started:** 2026-10-03

## Current Status

The first end-to-end version is working locally. The page can load events from the API, search by event title or venue, filter by category, and create an event. The backend currently provides event listing and creation; update and delete operations are not implemented.

Events are stored in an in-memory H2 database during local development. All events are lost when the backend stops or restarts. The PostgreSQL driver is included, but PostgreSQL is not the active local database configuration yet.

## Technology

- Java 23
- Spring Boot 4.0.0 and Maven
- Spring Data JPA, with H2 for local development
- PostgreSQL JDBC driver for future database configuration
- HTML, CSS, and plain JavaScript
- JUnit and Mockito for backend tests

## Run Locally

### Prerequisites

- JDK 23
- Apache Maven
- Python 3 to serve the frontend files

Check the installed Java and Maven versions:

```powershell
java -version
mvn -version
```

### Start the backend

From the repository root, run this in a terminal:

```powershell
mvn -f backend/pom.xml spring-boot:run
```

The API starts at `http://localhost:8080`.

### Start the frontend

Open a second terminal at the repository root and run:

```powershell
python -m http.server 5500 --bind 127.0.0.1 --directory frontend
```

Open `http://127.0.0.1:5500/` in a browser. Keep both terminals running while using the app. The backend allows browser requests from `http://127.0.0.1:5500` and `http://localhost:5500`.

## Event API

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/api/events` | Return events ordered by start date and time |
| `POST` | `/api/events` | Create an event and return it with HTTP `201 Created` |

Example request body for `POST /api/events`:

```json
{
	"title": "Summer Sessions",
	"description": "Outdoor live music event in the city centre.",
	"category": "GIG",
	"venueName": "Princes Street Gardens",
	"startDateTime": "2026-10-15T19:00:00",
	"endDateTime": "2026-10-15T22:00:00",
	"price": 25.00,
	"websiteUrl": "https://example.com/events/summer-sessions"
}
```

The category must be one of `FESTIVAL`, `GIG`, `MEETUP`, `FOOD`, `SPORT`, `FAMILY`, or `ARTS`. Required fields are validated, the price cannot be negative, and the event end time must be later than its start time.

## Build and Test

Run the backend tests from the repository root:

```powershell
mvn -f backend/pom.xml test
```

The current tests focus on the event service. HTTP-level controller tests are still to be added.

## Project Layout

```text
backend/
	src/main/java/uk/co/lewis/mcdonald/events/  Event API, domain, service, and repositories
	src/main/resources/                         Local application settings and SQL placeholder
	src/test/java/                              Backend tests
	pom.xml                                     Maven configuration
frontend/
	index.html                                  Event browser and creation form
	styles.css                                  Page styles
	app.js                                      API calls, search, and category filtering
```

## Next Steps

- Add HTTP-level tests for event listing, creation, and invalid requests.
- Add saved events, starting with browser storage before introducing user accounts.
- Add persistent PostgreSQL configuration and sample data.
- Expand event filtering and implement event updates and deletion.
- Consider external Edinburgh event data and deployment after the core workflows are tested.