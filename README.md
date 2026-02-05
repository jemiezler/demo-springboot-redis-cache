# Redis Caching for Master Data

## Overview
This project implements Redis as a caching layer for selected master data to improve system performance and reduce load on the master database.  
The master database remains the single source of truth, while Redis is used for fast read access.

---

## Objective
- Improve response time for frequently accessed master data
- Reduce repetitive queries to the master database
- Maintain data consistency by invalidating cache when master data changes

---

## Scope
Redis caching will be applied only to selected master data that:
- Is read frequently
- Changes infrequently
- Does not require real-time consistency

Examples:
- Country / Province
- Configuration data
- Role / Permission
- Product master data

---

## Architecture Overview
```

Client
↓
Spring Boot Application
↓
Redis Cache (Read First)
↓ (Cache Miss)
Master Database

```

---

## Functional Requirements

1. The system must retrieve master data from Redis before querying the master database.
2. If data does not exist in Redis (cache miss), the system must:
   - Retrieve data from the master database
   - Store the retrieved data in Redis
   - Return the data to the client
3. Cached data must be invalidated or updated when the master data is modified.
4. Cache expiration time (TTL) must be configurable.

---

## Non-Functional Requirements

- Redis must not affect existing business logic.
- System must fall back to the master database if Redis is unavailable.
- Cached data must be serialized in a readable and maintainable format (e.g. JSON).
- Redis usage must be transparent to API consumers.

---

## Cache Strategy

### Cache Type
- Read-through cache

### Cache Key Convention
```

master:{entity}:{identifier}

```

Example:
```

master:country:TH

```

### TTL (Time To Live)
- Default TTL: 24 hours
- TTL must be configurable via application configuration

---

## Cache Flow

### Read Flow
1. Client requests master data
2. Application checks Redis
3. If cache hit:
   - Return data from Redis
4. If cache miss:
   - Query master database
   - Store result in Redis
   - Return data

### Update Flow
1. Master data is updated
2. Related Redis cache is invalidated
3. Subsequent read requests fetch fresh data from master database

---

## Error Handling
- If Redis is unavailable, the system must retrieve data directly from the master database.
- Redis failures must not cause system downtime.

---

## Technology Stack
- Java 17
- Spring Boot
- Spring Data Redis
- Redis
- Maven
- Docker (optional for Redis)

---

## Out of Scope
- Transactional data caching
- Real-time data synchronization
- Redis clustering and replication configuration

---

## Summary
Redis is used as a performance optimization layer for master data access.  
The master database remains the authoritative source, while Redis improves response time and system scalability.
