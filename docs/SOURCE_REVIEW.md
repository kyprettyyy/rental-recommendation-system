# Source selection and repairs

All 1661 supplied files were inventoried and hashed locally, including compiled outputs. This is a complete file inventory, not a claim that every image or binary was visually reviewed. No ZIP/RAR archives were present in the two supplied trees. The original folders were not modified.

## Selected

- `毕设/HouseProjectBackend-改`: canonical backend; compared against the other backend, with differences in POM, CORS, recommendation service and configuration.
- `毕设/HouseProject`: rental streaming recommendation source.
- `毕设/项目5 - 副本`: rental frontend containing integrated behavior, recommendation and collection calls.
- `毕设/爬虫`: selected data-cleaning and collaborative-filtering notebooks, outputs cleared. The collaborative notebook is incomplete and not executed as an algorithm.
- `9001_flink_rent/origin_code/crawl`: collection references. Its Scrapy pipeline targets a different sale-property schema and is not a drop-in backend importer.
- `毕设/sql.sql`: CREATE TABLE definitions only; no original INSERT statements. The collection table was reconstructed from its JPA entity.

## Excluded

Build products, IDE files, duplicate frontend/backend variants, raw SQL/CSV dumps, original personal documents, cached notebook output, and exploratory notebooks containing cookies were excluded. The `proje` phone-shopping application, generic `wu` dashboard, incomplete `spark-rent` ALS TODO and education CDC/Phoenix Flink example are not assembled into the rental pipeline. The supplied architecture image describes JD product-comment crawling and is not used as a rental architecture diagram.

## November 2026 repairs

- Environment variables replace hardcoded database passwords and machine IPs.
- Backend frontend assets are packaged from one canonical directory, with same-origin API URLs.
- Classpath properties are loaded as streams so packaged JARs can read them.
- Removed an unused internal JDK import and duplicate Spring Boot Maven plugin; Maven Central replaces unnecessary snapshot repositories.
- Scala sources moved to the conventional directory and a compiler plugin was added; the Spark Kafka dependency uses the Apache group consistently.
- The content recommendation worker was repaired to scope history to a user even with one event, resolve clicked IDs to content, tokenize punctuation-separated attributes, preserve province data, exclude consumed IDs and close Redis connections on failure.

## Verification scope

Maven XML, notebook JSON, Python AST and browser-script syntax are checked locally. Raw data and credential exclusions are checked before upload. Java/Scala compilation runs in GitHub Actions; no local JVM toolchain is installed. No live multi-service run, scraping job, production readiness or recommendation quality benchmark is claimed. The original Spring context test requires live infrastructure and is skipped by the compile workflow.
