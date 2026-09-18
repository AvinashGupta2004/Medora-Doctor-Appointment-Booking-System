<div align="center">

# 🩺 Medora

**A Modern, Concurrency-Safe OPD Appointment & Queue Management Engine**

*Optimizing Outpatient Department workflows with shift-based capacities, sequential token generation, and enterprise-grade transaction safety.*

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-Persistence-blue?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-data-jpa)
</div>

---

## 📌 Overview

Traditional clinic scheduling often relies on rigid, clock-bound time slots that break down under real-world hospital delays and high patient volume. **Medora** reinvents outpatient department (OPD) management by implementing a **token-driven, shift-capacity model**.

By replacing rigid scheduling with dynamic shift windows and sequential token issuance, Medora ensures deterministic, fair, and high-concurrency patient flow management while protecting backend data integrity.

---

## ✨ Key Features

- **🎟️ Token-Based OPD Queue Engine:** Simplifies patient scheduling by moving away from hard-coded time slots to shift-based capacity and sequential token generation.
- **🔒 High-Concurrency Protection:** Utilizes **Spring Data JPA with pessimistic locking** at the persistence layer to safely handle concurrent booking spikes and eliminate double-booking bugs.
- **🏗️ Three-Layer Architecture:** Features a clean separation of concerns:
    - **Web Layer:** Controllers, DTO request/response models, global exception handlers.
    - **Processing Layer:** Core domain models, appointment state machines, and triage rules.
    - **Persistence Layer:** Database entities, JPA repositories, and concurrency configurations.
- **📦 Package-by-Feature Structure:** Organized into modular, feature-focused packages to reduce coupling and ease long-term maintenance.
- **🤖 AI-Ready Foundation:** Architected with clean extension hooks to incorporate predictive wait-time forecasting and automated triage algorithms in future releases.

---

## 🏛️ Project Architecture

```text
com.medora
├── web           # REST APIs, Request/Response DTOs, Exception Handlers
├── processing    # Business logic, Appointment State Machines, Queue Triage
└── persistence   # JPA Entities, Repositories with Pessimistic Locks
```

---

## 🛠️ Tech Stack

- **Language:** Java 17+
- **Framework:** Spring Boot 3.x
- **ORM / Persistence:** Spring Data JPA / Hibernate
- **Database:** PostgreSQL / MySQL
- **Concurrency Strategy:** JPA Pessimistic Read/Write Locks (`PESSIMISTIC_WRITE`)

---

## 🚀 Getting Started

### Prerequisites

- JDK 17 or higher
- Maven or Gradle
- PostgreSQL or MySQL server instance running locally or hosted

### Setup & Run

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/medora.git
   cd medora
   ```

2. **Configure Database Connection**
   Update your database credentials in `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/medora_db
       username: your_db_user
       password: your_db_password
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
   ```

3. **Build and Start Application**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🗺️ Product Roadmap

- [x] Core Entities (`Patient`, `Doctor`, `Shift`, `Appointment`, `Token`)
- [x] Shift-based token generation algorithm
- [x] Concurrency control using pessimistic locking
- [ ] Swagger / OpenAPI REST API documentation
- [ ] Machine-learning based queue delay forecasting engine

---
avatar
Ask In Chat
Ask In Chat<div align="center">

# 🩺 Medora

**A Modern, Concurrency-Safe OPD Appointment & Queue Management Engine**

*Optimizing Outpatient Department workflows with shift-based capacities, sequential token generation, and enterprise-grade transaction safety.*

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-Persistence-blue?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-data-jpa)
</div>

---

## 📌 Overview

Traditional clinic scheduling often relies on rigid, clock-bound time slots that break down under real-world hospital delays and high patient volume. **Medora** reinvents outpatient department (OPD) management by implementing a **token-driven, shift-capacity model**.

By replacing rigid scheduling with dynamic shift windows and sequential token issuance, Medora ensures deterministic, fair, and high-concurrency patient flow management while protecting backend data integrity.

---

## ✨ Key Features

- **🎟️ Token-Based OPD Queue Engine:** Simplifies patient scheduling by moving away from hard-coded time slots to shift-based capacity and sequential token generation.
- **🔒 High-Concurrency Protection:** Utilizes **Spring Data JPA with pessimistic locking** at the persistence layer to safely handle concurrent booking spikes and eliminate double-booking bugs.
- **🏗️ Three-Layer Architecture:** Features a clean separation of concerns:
    - **Web Layer:** Controllers, DTO request/response models, global exception handlers.
    - **Processing Layer:** Core domain models, appointment state machines, and triage rules.
    - **Persistence Layer:** Database entities, JPA repositories, and concurrency configurations.
- **📦 Package-by-Feature Structure:** Organized into modular, feature-focused packages to reduce coupling and ease long-term maintenance.
- **🤖 AI-Ready Foundation:** Architected with clean extension hooks to incorporate predictive wait-time forecasting and automated triage algorithms in future releases.

---

## 🏛️ Project Architecture

```text
com.medora
├── web           # REST APIs, Request/Response DTOs, Exception Handlers
├── processing    # Business logic, Appointment State Machines, Queue Triage
└── persistence   # JPA Entities, Repositories with Pessimistic Locks
```

---

## 🛠️ Tech Stack

- **Language:** Java 17+
- **Framework:** Spring Boot 3.x
- **ORM / Persistence:** Spring Data JPA / Hibernate
- **Database:** PostgreSQL / MySQL
- **Concurrency Strategy:** JPA Pessimistic Read/Write Locks (`PESSIMISTIC_WRITE`)

---

## 🚀 Getting Started

### Prerequisites

- JDK 17 or higher
- Maven or Gradle
- PostgreSQL or MySQL server instance running locally or hosted

### Setup & Run

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/medora.git
   cd medora
   ```

2. **Configure Database Connection**
   Update your database credentials in `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/medora_db
       username: your_db_user
       password: your_db_password
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
   ```

3. **Build and Start Application**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🗺️ Product Roadmap

- [x] Core Entities (`Patient`, `Doctor`, `Shift`, `Appointment`, `Token`)
- [x] Shift-based token generation algorithm
- [x] Concurrency control using pessimistic locking
- [ ] Swagger / OpenAPI REST API documentation
- [ ] Machine-learning based queue delay forecasting engine

---