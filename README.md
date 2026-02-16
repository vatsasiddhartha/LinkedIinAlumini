# 🎓 Alumni Searcher — Spring Boot Assignment

A Spring Boot backend application that fetches alumni data from PhantomBuster API, filters it based on user criteria, stores it in PostgreSQL, and exposes REST APIs for search and retrieval.

This project demonstrates:

* API integration
* JSON parsing
* Database persistence
* Filtering logic
* Unit testing using JUnit & Mockito
* Clean service architecture

---

## 🚀 Tech Stack

* Java 17
* Spring Boot
* PostgreSQL
* JPA / Hibernate
* PhantomBuster API
* Jackson JSON Parser
* JUnit 5
* Mockito
* Maven

---

## 📦 Features

✅ Fetch alumni data from PhantomBuster API
✅ Parse JSON response
✅ Filter by university and designation
✅ Store alumni data in PostgreSQL
✅ Avoid duplicate processing logic
✅ REST API endpoints
✅ Unit testing with Mockito
✅ Clean layered architecture

---

## 🏗 Project Architecture

Controller → Handles API requests
Service → Business logic & parsing
Repository → Database operations
Entity → Database mapping
DTO → Request/Response objects

---

## ⚙️ Setup & Run Application

### 1️⃣ Clone repository

```
git clone https://github.com/YOUR_USERNAME/Alumni_Searcher.git
cd Alumni_Searcher
```

---

### 2️⃣ Configure PostgreSQL

Create database:

```
CREATE DATABASE alumni_db;
```

---

### 3️⃣ Update application.properties

```
spring.datasource.url=jdbc:postgresql://localhost:5432/alumni_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 4️⃣ Run application

```
mvn spring-boot:run
```

App runs on:

```
http://localhost:8080
```

---

## 🔌 API Endpoints

### ✅ Search Alumni (Fetch + Save)

```
POST /search
```

### Request Body

```
{
  "university": "IIT Delhi",
  "designation": "Software Engineer",
  "passoutYear": 2023
}
```

### Response

Returns filtered alumni and stores in database.

---

### ✅ Get All Alumni

```
GET /all
```

Returns all stored alumni.

---

## 🗄 Database Verification

Check saved data:

```
SELECT * FROM alumini;
```

---

## 🧪 Unit Testing

Test framework:

* JUnit 5
* Mockito

### Run tests

```
mvn test
```

### Test Coverage

* API response parsing
* Filtering logic
* Empty results handling
* Multiple results handling
* Case insensitive matching
* Repository save verification

---

## 📊 Test Scenarios Covered

* Successful API fetch and save
* No matching results
* Multiple alumni filtering
* Empty Phantom response
* Case-insensitive filtering
* Partial university matching
* Null filters handling
* Database save verification

---

## 🔄 Application Flow

1. User sends search request
2. Service fetches data from Phantom API
3. JSON response parsed
4. Data filtered based on request
5. Records stored in PostgreSQL
6. Response returned

---



📸 Screenshots
✅ POST API — Search & Save Alumni

This endpoint fetches alumni data from PhantomBuster API, filters results based on user input, and stores matching records in the PostgreSQL database.

Endpoint

POST /search


What this screenshot shows

Request body with filters (university, designation, passoutYear)

API response with filtered alumni data

Successful data storage confirmation

Example Screenshot

screenshots/post-api.png

✅ GET API — Fetch All Alumni

This endpoint retrieves all stored alumni records from the database.

Endpoint

GET /all


What this screenshot shows

Request execution

All saved alumni records

JSON response from database

Example Screenshot

screenshots/get-api.png

📁 Screenshot Folder Structure
project-root/
 ├── screenshots/
 │   ├── post-api.png
 │   └── get-api.png

## 📌 Assumptions

* PhantomBuster API returns data in JSON format
* Filtering done using contains matching
* Database auto-updated using JPA

---

## 👨‍💻 Author

Siddhartha Vatsa
Android & Backend Developer

---

## ⭐ Future Improvements

* Duplicate detection
* Pagination
* Better error handling
* Logging system
* Integration tests
* Docker support
