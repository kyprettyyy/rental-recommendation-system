# Rental Recommendation System

An undergraduate thesis project that recommends rental listings based on user preferences and browsing behavior. The system combines a Spring Boot backend, a web frontend, and a Spark Streaming recommendation service.

Kafka carries behavior events, MongoDB stores browsing history, and Redis caches the top ten recommendation IDs per user. New users receive province-filtered listings when no cached recommendations exist.

## Core implementation

- **Backend:** Spring Boot APIs for rental search, user accounts, favorites, behavior collection, and recommendation retrieval.
- **Recommendation:** Spark Streaming consumes Kafka events in one-second microbatches; Spark ML builds 150-dimensional TF-IDF listing vectors and ranks candidates by cosine similarity.
- **Behavior feedback:** MongoDB stores browsing history; the worker excludes previously viewed listings and writes the top ten IDs to Redis. Reading cached results requests a history-based refresh.
- **Cold start:** province-filtered MySQL results provide a fallback before a personalized cache exists.

## Scope and evidence

This undergraduate prototype implements content-based recommendation. It does not establish collaborative filtering, exactly-once event processing, production scalability, or a measured 20% improvement. A one-second microbatch interval is a configuration value, not a measured end-to-end latency guarantee. See [architecture and limitations](docs/ARCHITECTURE.md).

The GitHub language bar is configured to summarize backend and recommendation source code: the template-based `frontend/` is excluded from language statistics, and `reference/` is treated as reference code. Frontend files remain in the repository. Percentages describe this selected source scope, not engineering effort or technology importance.

## Features

- User registration, login, and favorite listings.
- Rental search with location, price, floor area, and rental-type filters.
- Browsing behavior collection and recommendation updates.
- Content-based ranking using TF-IDF listing features and cosine similarity.
- Redis-backed recommendation retrieval, with location-based results for new users.

## Architecture

```text
Web frontend → Spring Boot → MySQL rental listings
                    │
                    ├── MongoDB: browsing history
                    └── Kafka: behavior events
                              │
                        Spark Streaming
                              │
                            Redis
                              │
                         Spring Boot → Web frontend
```

The backend stores browsing events in MongoDB and publishes them to Kafka. Spark Streaming combines these events with rental attributes from MySQL, calculates recommendation scores, and writes the top ten listing IDs to Redis. The frontend retrieves the results through the backend API.

## Tech stack

Java 8 · Spring Boot 2.7.7 · Scala 2.12 · Spark Streaming 3.1.1 · Kafka · Redis · MongoDB · MySQL · HTML / JavaScript

## Project structure

```text
backend/             Spring Boot API
streaming/           Spark Streaming recommendation service
frontend/            Rental search and recommendation pages
database/            Table definitions and sample listings
notebooks/           Data processing and recommendation experiments
reference/crawler/   Data collection scripts
```

## Getting started

Install JDK 8 and Maven, and configure MySQL, MongoDB, Redis, and Kafka. Import `database/schema.sql` and, optionally, `database/demo.sql` into the `housedatabase` database.

Configure service connections through environment variables, then build both modules:

```sh
mvn -f backend/pom.xml package -DskipTests
mvn -f streaming/pom.xml package -DskipTests
```

Start the backend:

```sh
java -jar backend/target/HouseProjectBackend-0.0.1-SNAPSHOT.jar
```

Start the recommendation service in another terminal:

```sh
mvn -f streaming/pom.xml exec:java -Dexec.mainClass=org.HouseProject.HouseRecommendation
```

Open `http://127.0.0.1:8080/index.html`.

[Configuration and setup](docs/RUNNING.md) · [API](docs/API.md) · [Acknowledgements](THIRD_PARTY.md)
