# Rental Recommendation System

[中文说明](README.zh-CN.md) · [Architecture](docs/ARCHITECTURE.md) · [Source review](docs/SOURCE_REVIEW.md) · [Run locally](docs/RUNNING.md)

Undergraduate rental recommendation project with a Spring Boot backend, a static HTML/JavaScript frontend and a Spark Streaming recommendation worker. User behavior is stored in MongoDB and published to Kafka. The worker builds content-based TF-IDF preferences from MySQL listings and user history, then writes ranked listing IDs to Redis for the backend to serve.

This repository curates supplied thesis-project folders from 2023. Cleanup and specific build/runtime repairs were made in November 2026; they are documented separately from the historical work. The résumé's “20% improvement in new-user matching efficiency” is not independently substantiated by the supplied files and is not presented as a measured repository result.

## Modules

| Directory | Contents |
| --- | --- |
| `backend/` | Spring Boot 2.7.7 API, MySQL/JPA, Kafka producer, MongoDB behavior storage, Redis recommendation lookup |
| `streaming/` | Scala 2.12 / Spark 3.1.1 worker with one-second microbatches |
| `frontend/` | Selected rental UI with listing filters, registration/login, favorites and recommendations |
| `database/` | Schema extracted without original user/listing rows; fictional seed listings |
| `notebooks/` | Historical cleaning and unfinished collaborative-filtering notes; outputs cleared |
| `reference/crawler/` | Historical Scrapy/scripting references, not automatically connected to the live pipeline |

The Flink sample in the supplied folders is an education-data CDC/Phoenix example. It is not the rental recommendation worker and is excluded. The running recommendation algorithm is content-based, not verified collaborative filtering; the notebook is only an unfinished experiment.

## Build

Use JDK 8 and Maven. From the repository root:

```sh
mvn -f backend/pom.xml package -DskipTests
mvn -f streaming/pom.xml package -DskipTests
```

The packaged backend includes the canonical `frontend/` under its static resources. Building does not require live databases; running requires MySQL, MongoDB, Redis and Kafka, a matching topic, and imported listings. See [full setup](docs/RUNNING.md) and [API details](docs/API.md).

## Verification and limitations

Local checks parse Maven/XML, JavaScript, Python and notebook JSON and review configuration/data exclusions. The machine has no JDK/Maven and Docker is not running, so a local JVM build and live multi-service validation are not claimed. GitHub Actions compiles both JVM modules; current status is visible in the Actions tab. This is a curated historical prototype, not a production deployment.

The original account endpoints do not provide production-grade authentication or authorization; passwords use the historical schema. Use only fictional accounts on localhost. Kafka publish and MongoDB writes are not transactional, and offset/cache handling is not exactly-once. Do not expose the prototype to the public internet without a separate security and reliability pass.

Source origins and excluded materials are described in [THIRD_PARTY.md](THIRD_PARTY.md). No blanket license is assigned to supplied third-party code or assets.
