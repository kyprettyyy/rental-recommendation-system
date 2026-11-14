# Local setup

This is a historical local prototype. Use fictional accounts and listings. JDK 8 and Maven are required for the archived Spark 3.1.1/Scala 2.12 and Spring Boot 2.7.7 stack. MySQL 8, Kafka, MongoDB and Redis must be supplied separately; no services are installed or started by this repository.

## Database

Create database `housedatabase` and a local user with permissions on it, then import:

```sh
mysql -u YOUR_USER -p housedatabase < database/schema.sql
mysql -u YOUR_USER -p housedatabase < database/demo.sql
```

The seed contains three fictional listings and no accounts. Registration uses the historical prototype endpoint. Some original UI image/detail renderers expect richer listing fields than the minimal seed supplies; a full validated import and UI pass are still needed.

## Configuration

Set these variables for BOTH backend and worker processes:

| Variable | Default / purpose |
| --- | --- |
| `MYSQL_URL` | JDBC URL for `housedatabase` |
| `MYSQL_USER` | `rent` |
| `MYSQL_PASSWORD` | empty; supply locally |
| `KAFKA_BOOTSTRAP_SERVERS` | `localhost:9092` |
| `KAFKA_TOPIC` | `test`; create this topic before starting |
| `REDIS_HOST`, `REDIS_PORT` | `localhost`, `6379` |
| `REDIS_PASSWORD` | empty |
| `REDIS_RECOMMEND_KEY` | `recommend` |
| `MONGODB_URI` | backend: `mongodb://localhost:27017/test_collection`; worker: `mongodb://localhost:27017/test_collection.userBehavior` |
| `SPARK_MASTER` | worker only, `local[*]` |

Set `MONGODB_URI` independently for the two processes: the Mongo Spark connector URI includes the collection while the backend URI selects a database. For shell sessions without the variable, each module's documented default is already appropriate. The backend binds to `127.0.0.1:8080` by default.

## Build and launch

```sh
mvn -f backend/pom.xml package -DskipTests
mvn -f streaming/pom.xml package -DskipTests
java -jar backend/target/HouseProjectBackend-0.0.1-SNAPSHOT.jar
```

In a second configured terminal:

```sh
mvn -f streaming/pom.xml exec:java -Dexec.mainClass=org.HouseProject.HouseRecommendation
```

Open `http://127.0.0.1:8080/index.html`. Register a fictional user, browse imported listings, and request recommendations after the worker processes behavior events. A valid cache can be inspected with `redis-cli HGET recommend USER_ID`. The default MongoDB collection is `userBehavior`.

The full pipeline has not been run in this environment. Legacy authentication sends login fields through a GET endpoint, stores passwords using the original schema and trusts client-supplied user IDs; it must be redesigned before exposure beyond local study. Do not use personal passwords. The reference crawler is not run at application startup and is not a live validated source of data. Review its schemas and selectors before any manual use.
