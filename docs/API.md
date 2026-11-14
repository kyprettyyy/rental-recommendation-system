# Historical API map

| Method | Path | Purpose |
| --- | --- | --- |
| GET | `/data/{userId}/{province}` | Read cached recommendations or query province fallback; request history refresh |
| POST | `/data/{userId}/{content}` | Persist behavior and publish Kafka event |
| GET | `/availability/Province` | List available provinces/cities |
| GET | `/availability/{id}` | Listing detail |
| GET | `/availability/ComplexQueries` | Original listing-filter query; see controller for case-sensitive parameter names |
| GET / POST | `/user/` | Historical login / registration endpoints |
| GET / POST / DELETE | `/collection/...` | Collection operations; see controller for parameter order |

Kafka payload is the historical string `userId,goods`, split only at the first comma. `goods` may be a listing ID, a feature string, or `history` for refresh. MongoDB documents use `userId`, `goods`, `behaviorTime` and an ID. Redis is a hash from user ID to a JSON array of recommended listing IDs. Returned rows are fetched through JPA, whose default result ordering may not preserve the Redis ranking order; this needs review before a user-facing quality claim.
