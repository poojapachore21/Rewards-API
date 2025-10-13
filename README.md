# Rewards API (Updated)

This project is a Spring Boot based REST API that calculates customer reward points based on purchases.

## Features added / fixes 
- Single REST endpoint `/api/rewards` that returns monthly and total points for a customer.
- Proper Spring Data JPA usage (H2 in-memory DB) with `Customer` and `Purchase` entities.
- Logging added to controller and service.
- Global exception handling using `@RestControllerAdvice` and an `ErrorDto`.
- Asynchronous simulation for fetching transactions using `@Async`.
- Input validation for request parameters.
- Test cases using JUnit + Mockito for service and controller layers.
- Improved README, package naming, formatting and removed unused/boilerplate code.
- Sample data loaded at startup via `DataLoader`.

## How to run
1. Java 17+ and Maven installed.
2. Build and run:
```
mvn spring-boot:run
```
3. The H2 console is available at `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:rewardsdb`)

## API
**GET** `/api/rewards?customerId={customerId}&start={startDate}&end={endDate}`

- Request parameters:
  - `customerId` (required) — numeric id of the customer
  - `start` (required) — start date inclusive in `yyyy-MM-dd` format
  - `end` (required) — end date inclusive in `yyyy-MM-dd` format

- Example:
```
GET http://localhost:8080/api/rewards?customerId=1&start=2025-01-01&end=2025-03-31
```

- Sample response (JSON):
```json
{
  "customerId": 1,
  "customerName": "Pooja Pachore",
  "monthlyRewards": {
    "JANUARY": { "totalPoints": 120, "totalAmount": 200.0 },
    "FEBRUARY": { "totalPoints": 150, "totalAmount": 150.0 }
  },
  "transactions": [
    { "id": 1, "amount": 120.0, "date": "2025-01-15" },
    { "id": 2, "amount": 80.0, "date": "2025-01-20" },
    { "id": 3, "amount": 150.0, "date": "2025-02-05" }
  ]
}
```

## Tests
- Unit tests and controller tests are present under `src/test/java`.
- Run them with:
```
mvn test
```

## Notes on design & changes
- Reward logic implemented in service — 2 points per dollar spent >100, 1 point per dollar between 50 and 100 — dollars rounded down.
- Inclusive date range for `start` and `end`.
- Well-formed error responses returned on bad input / missing data.

## SCM & commits
Please follow standard Git practice; commit messages should be descriptive, e.g.:
- `feat: implement RewardService and calculation logic`
- `chore: add sample data loader and README updates`
- `fix: improve exception handling and standardize DTOs`

