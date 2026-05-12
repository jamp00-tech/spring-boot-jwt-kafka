# Spring Boot Kafka

**Project overview**

Spring Boot API built to practice:

* JWT authentication
* User registration
* Kafka producer
* Kafka consumer
* Event-driven messaging
* Retry/error handling

---

**Architecture**

```text
Client
 ↓
REST API
 ↓
Service
 ↓
Kafka Producer
 ↓
Kafka Topic
 ↓
Kafka Consumer
```

---

**Tech stack**

* Java 17
* Spring Boot 3
* Spring Security
* JWT
* Spring Kafka
* H2 Database
* Docker
* Kafka UI

---

**Run Kafka locally**

```bash
docker compose up -d
```

Kafka UI:

```text
http://localhost:8085
```

---

**Run application**

```bash
mvn spring-boot:run
```

---

**Endpoints**

```text
POST /token
EX:
curl.exe -i http://localhost:9090/token/ \
-H "Content-Type: application/json" \
-d '{\"email\":\"a@a.com\",\"password\":\"Ab12\"}'

POST /usuario/registra
EX:
curl.exe -i -X POST http://localhost:9090/usuario/registra \
-H "Authorization: Bearer tokentokentoken" \
-H "Content-Type: application/json" \
-d '{\"email\":\"a@a.com\",\"password\":\"Ab12\"}'

GET /usuario/ping1
EX:
curl.exe -i http://localhost:9090/usuario/ping1

GET /kafka/demo
EX:
curl.exe http://localhost:9090/kafka/demo
```

---

**Kafka flow**

When a new user is created:

```text
POST /usuario/registra
    ↓
save user
    ↓
publish UserCreatedEvent
    ↓
Kafka topic: user-created
    ↓
consume event
    ↓
GET /kafka/demo
    ↓
view last produced/consumed event
```

---

**Event payload**

```json
{
  "idUsuario": "uuid",
  "email": "user@email.com"
}
```

This avoids sending sensitive data like:

* password
* token

---

**Retry and error handling**

Consumer retries failed messages:

* 3 retries
* 2 seconds between retries

If retries fail:

* logs topic
* logs offset
* logs error message

---

**Future improvements**

* Dead Letter Topic (DLT)
* Integration tests
* Dockerized Spring application
* Multiple consumers

---

**Version history**

* v1.0.0 → JWT + User API
* v1.1.0 → Kafka producer/consumer + events
* v1.1.1 → Introduced UserCreatedEvent for cleaner event-driven communication
* v1.1.2 → Added Kafka retry/error handling (3 retries + 2s backoff)

**Unreleased**
* Fixed `/token` endpoint mapping (`/token` and `/token/`)
* Added actuator endpoint access
* Added Kafka demo endpoint to view last produced and consumed events

