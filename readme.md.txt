# Banking Transaction Processor

A simple Spring Boot application that processes banking transactions for multiple accounts.

## Features

- Create accounts
- Deposit money
- Withdraw money
- Transfer money between accounts
- Prevent overdrafts
- Maintain transaction ledger with timestamps
- Retrieve transaction history
- Validation and exception handling

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito
- Maven

---

## Architecture

The application follows a layered architecture:

Controller -> Service -> Repository -> Database

### Packages

- controller
- service
- repository
- entity
- dto
- exception

---

## Design Decisions

### BigDecimal for Monetary Operations

Used BigDecimal instead of double to avoid floating point precision issues in financial calculations.

### Transactional Transfer

Transfer operation uses `@Transactional` to ensure debit and credit operations happen atomically.

### DTO Separation

DTOs are used to separate API contracts from persistence entities.

### Exception Handling

Centralized exception handling implemented using `@RestControllerAdvice`.

---

## APIs

### Create Account

POST /accounts

```json
{
  "initialBalance": 1000
}
```

---

### Get Account

GET /accounts/{accountId}

---

### Deposit

POST /accounts/{accountId}/deposit

```json
{
  "amount": 500
}
```

---

### Withdraw

POST /accounts/{accountId}/withdraw

```json
{
  "amount": 200
}
```

---

### Transfer

POST /accounts/transfer

```json
{
  "fromAccountId": "uuid",
  "toAccountId": "uuid",
  "amount": 100
}
```

---

### Transaction History

GET /accounts/{accountId}/transactions

---

## Testing

Unit tests added for:
- deposit scenarios
- withdrawal scenarios
- transfer scenarios
- validation and exception handling

Mockito was used to isolate service-layer business logic from repository/database interactions.

---

## How To Run

```bash
mvn clean install
mvn spring-boot:run
```

H2 Console:
```
http://localhost:8080/h2-console
```

JDBC URL:
```
jdbc:h2:mem:bankdb
```

Username:
```
admin
```

Password:
```
admin
```

---

## Future Improvements

- Optimistic locking for concurrent updates
- Pagination for transaction history
- Authentication and authorization
- Docker support
- API documentation using Swagger/OpenAPI
- Audit logging enhancements

---

## Assumptions

- Account IDs are UUIDs
- Negative and zero-value transactions are not allowed
- Transfers to same account are invalid