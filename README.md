# Library Management System

This is a REST API that manages the bookings in a library store.

## Getting Started

### Prerequisites

- Java 25
- Maven
- PostgreSQL

### Database Setup

1. Make sure PostgreSQL is running locally
2. Create a new database:
```sql
   CREATE DATABASE libms;
```

### Configuration

In `src/main/resources/application.properties`, configure your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/libms
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application

2. Install dependencies and build:
```bash
   mvn clean install
```

3. Run the application:
```bash
   mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## API Reference

### Authors `/api/authors`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/authors` | Retrieve all authors |
| GET | `/api/authors/{id}` | Retrieve a specific author by ID |
| POST | `/api/authors` | Create a new author |
| PUT | `/api/authors/{id}` | Update an existing author by ID |
| DELETE | `/api/authors/{id}` | Delete an author by ID |

### Books `/api/books`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/books` | Retrieve all books (supports search/filter via query params) |
| GET | `/api/books/{id}` | Retrieve a specific book by ID |
| POST | `/api/books` | Create a new book |
| POST | `/api/books/bulk` | Create multiple books at once |
| PUT | `/api/books/{id}` | Update an existing book by ID |
| DELETE | `/api/books/{id}` | Delete a book by ID |

### Borrowers `/api/borrowers`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/borrowers` | Retrieve all borrowers |
| GET | `/api/borrowers/{id}` | Retrieve a specific borrower by ID |
| POST | `/api/borrowers` | Create a new borrower |
| PUT | `/api/borrowers/{id}` | Update an existing borrower by ID |
| DELETE | `/api/borrowers/{id}` | Delete a borrower by ID |

### Borrowings `/api/borrowing`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/borrowing` | Retrieve all borrowing records |
| POST | `/api/borrowing` | Create a new borrowing record |
| GET | `/api/borrowing/return/{id}` | Mark a borrowing as returned |
| DELETE | `/api/borrowing/{id}` | Delete a borrowing record by ID |

