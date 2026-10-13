# Medora

**Concurrency-Safe OPD Appointment & Queue Management System**

Medora is a backend-focused healthcare application designed to manage doctor shifts, appointment queues, and sequential patient tokens with a strong emphasis on data consistency and concurrent booking operations.

## Key Features

- **Shift-Based Queue Management** — Organizes appointments around doctor availability and shift capacities.
- **Sequential Token Generation** — Maintains token ordering for registered patient appointments.
- **Concurrency Control** — Uses pessimistic database locking to coordinate concurrent booking requests.
- **Transactional Persistence** — Uses Spring Data JPA and Hibernate to manage database operations.
- **Layered Architecture** — Separates REST APIs, business logic, domain models, and persistence concerns.
- **Modular Design** — Organizes functionality into focused packages for maintainability and future extension.

## Tech Stack

| Component         | Technology                                |
| ----------------- | ----------------------------------------- |
| Language          | Java                                      |
| Backend Framework | Spring Boot                               |
| Persistence       | Spring Data JPA, Hibernate                |
| Database          | PostgreSQL                                |
| Concurrency       | Pessimistic Locking (`PESSIMISTIC_WRITE`) |
| Build Tool        | Maven                                     |

## Architecture

Medora follows a layered architecture with feature-oriented package organization.

```text
com.medora
├── DOCTOR
│   ├── web
│   ├── processing
│   └── persistence
│
├── QUEUE
│   ├── web
│   ├── processing
│   └── persistence
│
└── common
    ├── exceptions
    ├── mappers
    └── shared components
```

- **Web:** REST controllers, request/response DTOs, and API exception handling.
- **Processing:** Business rules, queue availability checks, and application services.
- **Persistence:** JPA entities, repositories, and database access.

## Concurrency Strategy

Appointment booking requires more than checking whether a queue has available capacity. Concurrent requests must not incorrectly allocate tokens or exceed the configured capacity.

Medora uses pessimistic write locking at the persistence layer to coordinate competing operations on shared queue records.

The intended booking workflow is:

1. Retrieve and lock the relevant queue record.
2. Validate queue status and remaining capacity.
3. Allocate the next token and update queue state.
4. Commit the changes within a transaction.

This approach helps prevent conflicting updates when multiple patients attempt to book simultaneously. Correctness also depends on transaction boundaries, lock scope, and database constraints.

## Getting Started

### Prerequisites

- JDK version compatible with the project
- Maven
- PostgreSQL
- Git

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/medora.git
cd medora
```

### 2. Configure the database

Create a PostgreSQL database and configure your connection in `src/main/resources/application.yml`.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/medora_db
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

Configure the corresponding environment variables before starting the application. Keep credentials out of version control.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## Roadmap

- [ ] Doctor and queue domain models
- [ ] Shift-based queue management
- [ ] Pessimistic locking for concurrent operations
- [ ] API documentation with OpenAPI / Swagger
- [ ] Automated concurrency and integration tests
- [ ] Queue wait-time estimation

## Engineering Focus

Medora explores practical backend engineering concerns, including transactional consistency, concurrency control, separation of concerns, domain modeling, and maintainable Spring Boot application design.

---

*Built with Java and Spring Boot.*
