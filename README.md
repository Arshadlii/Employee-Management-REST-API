# Codenzic Employee Management REST API

A REST API built using Java 21, Spring Boot, Spring Data JPA, and PostgreSQL. This project demonstrates CRUD operations, request validation, duplicate-email handling, and structured exception responses.

## Features

- Create, retrieve, update, and delete employees.
- Layered architecture: Controller, Service, Repository, Entity, DTO, and Exception Handler.
- PostgreSQL integration using Spring Data JPA and Hibernate.
- Input validation for names, email, and salary.
- Duplicate email prevention.
- Centralized exception handling with consistent JSON responses.
- Postman collection for API testing.

## Technology Stack

- Java 21
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- Jakarta Validation
- PostgreSQL
- Postman

## Prerequisites

- JDK 21
- PostgreSQL
- Git
- Postman

Check Java:

```bash
java -version
```

## Database Setup

Create the database in PostgreSQL:

```sql
CREATE DATABASE employee_management;
```

Configure the following environment variables before running the application.

**Windows PowerShell:**

```powershell
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "your_local_postgres_password"
```

**Windows Command Prompt:**

```bat
set DB_USERNAME=postgres
set DB_PASSWORD=your_local_postgres_password
```

Replace the example password with your local PostgreSQL password. Never commit real credentials.

Ensure `src/main/resources/application.properties` contains:

```properties
spring.application.name=employee-management
spring.datasource.url=jdbc:postgresql://localhost:5432/employee_management
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
server.port=8080
```

## Run the Application

From the project root:

**Windows:**

```bat
mvnw.cmd clean test
mvnw.cmd spring-boot:run
```

**macOS/Linux:**

```bash
./mvnw clean test
./mvnw spring-boot:run
```

Base URL: `http://localhost:8080`

## REST API Endpoints

| Method | Endpoint | Success Status | Description |
|---|---|---|---|
| POST | `/api/employees` | 201 Created | Create an employee |
| GET | `/api/employees` | 200 OK | Retrieve all employees |
| GET | `/api/employees/{id}` | 200 OK | Retrieve an employee by ID |
| PUT | `/api/employees/{id}` | 200 OK | Update an employee |
| DELETE | `/api/employees/{id}` | 204 No Content | Delete an employee |

### Example Request

```json
{
  "firstName": "Arshad",
  "lastName": "Ali",
  "email": "arshad@example.com",
  "department": "Engineering",
  "salary": 35000.00
}
```

### Error Handling

The API returns structured JSON errors for:

- `400 Bad Request` — invalid fields, malformed JSON, or invalid ID format.
- `404 Not Found` — employee does not exist.
- `409 Conflict` — duplicate email or database constraint conflict.
- `500 Internal Server Error` — unexpected server error.

## Postman Testing

Import `Codenzic_Employee_Management_API.postman_collection.json` into Postman and run the collection against `http://localhost:8080`.

The collection tests:

1. Create employee.
2. Retrieve all employees.
3. Retrieve an employee by ID.
4. Update an employee.
5. Reject duplicate email.
6. Reject invalid employee data.
7. Handle missing employee IDs.
8. Reject malformed JSON.
9. Reject invalid ID formats.
10. Delete an employee.
11. Confirm the deleted employee returns 404.

To verify database persistence, create an employee, restart Spring Boot, and retrieve the same employee by ID. The record should remain available.

## Project Structure

```text
src/main/java/com/codenzic/employeemanagement/
├── controller/
├── dto/
├── entity/
├── exception/
├── repository/
└── service/
```

## GitHub Submission

Before pushing, ensure `.gitignore` excludes build output, IDE files, and environment secrets.

Run:

```bash
git status
git add .
git commit -m "Complete employee management REST API"
git push
```

If there are no new changes to commit, you can skip the commit command.

Verify that the GitHub repository contains the source code, `pom.xml`, `README.md`, `.gitignore`, and Postman collection. Do not commit database passwords or API keys.
