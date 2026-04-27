# 📋 Job Application Tracker API

> A production-grade RESTful API built with Java, Spring Boot, and SQL

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green.svg)](https://spring.io)
[![Maven](https://img.shields.io/badge/Maven-3.8-red.svg)](https://maven.apache.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 📌 Overview

A full-featured REST API to track job applications through every stage of the hiring lifecycle — from bookmarking to offer accepted. Built to demonstrate production Spring Boot patterns including layered architecture, JPA repositories, input validation, and RESTful design.

---

## 🏗️ Architecture

```
job-tracker/
│
├── src/main/java/com/kiran/jobtracker/
│   ├── JobTrackerApplication.java      # Entry point
│   ├── controller/
│   │   └── JobApplicationController.java  # REST endpoints
│   ├── service/
│   │   └── JobApplicationService.java     # Business logic
│   ├── repository/
│   │   └── JobApplicationRepository.java  # Data access (JPA)
│   └── model/
│       ├── JobApplication.java            # Entity
│       └── ApplicationStatus.java         # Status enum
│
├── src/main/resources/
│   └── application.properties
│
└── pom.xml
```

---

## 🔗 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/applications` | Add new application |
| `GET` | `/api/applications` | Get all applications |
| `GET` | `/api/applications/{id}` | Get single application |
| `PUT` | `/api/applications/{id}` | Update application |
| `DELETE` | `/api/applications/{id}` | Delete application |
| `PATCH` | `/api/applications/{id}/status?status=PHONE_SCREEN` | Update status only |
| `GET` | `/api/applications/status/{status}` | Filter by status |
| `GET` | `/api/applications/search?company=google` | Search by company |
| `GET` | `/api/applications/followups` | Get follow-ups due today |
| `GET` | `/api/applications/dashboard` | Stats dashboard |

---

## 📊 Application Statuses

```
BOOKMARKED → APPLIED → PHONE_SCREEN → TECHNICAL_INTERVIEW
           → FINAL_INTERVIEW → OFFER_RECEIVED → OFFER_ACCEPTED
           → REJECTED | WITHDRAWN | NO_RESPONSE
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+

### Run
```bash
git clone https://github.com/rahuldev7-web/job-tracker-api.git
cd job-tracker-api
mvn spring-boot:run
```

API runs at: `http://localhost:8080`
H2 Console: `http://localhost:8080/h2-console`

---

## 📝 Sample Requests

### Add an application
```bash
curl -X POST http://localhost:8080/api/applications \
  -H "Content-Type: application/json" \
  -d '{
    "company": "Home Depot",
    "jobTitle": "Software Engineer",
    "location": "Remote",
    "status": "APPLIED",
    "salaryRange": "$50,000 - $130,000",
    "jobUrl": "https://careers.homedepot.com",
    "notes": "Applied via company website. Resume tailored."
  }'
```

### Update status
```bash
curl -X PATCH "http://localhost:8080/api/applications/1/status?status=PHONE_SCREEN"
```

### Get dashboard
```bash
curl http://localhost:8080/api/applications/dashboard
```

### Sample dashboard response
```json
{
  "totalApplications": 7,
  "activeApplications": 5,
  "followUpsDue": 2,
  "statusBreakdown": {
    "APPLIED": 4,
    "PHONE_SCREEN": 1,
    "TECHNICAL_INTERVIEW": 1,
    "REJECTED": 1
  }
}
```

---

## 🛣️ Roadmap
- [ ] PostgreSQL production database config
- [ ] JWT authentication
- [ ] Email reminder for follow-ups
- [ ] React frontend dashboard
- [ ] Docker + docker-compose setup
- [ ] Unit & integration tests

---

## 👤 Author

**Kiran Rahul Pabboju**
M.S. Computer and Information Systems Security
📧 kiranrahulpabboju@gmail.com
🔗 [LinkedIn](https://linkedin.com/in/pabboju-kiran-rahul)
🐙 [GitHub](https://github.com/rahuldev7-web)

---

## 📄 License
MIT License
