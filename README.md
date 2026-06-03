<p align="center">
  <img src="https://img.shields.io/badge/TIBCO-BusinessWorks%20CE-0066CC?style=for-the-badge&logo=tibco&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-15-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
  <img src="https://img.shields.io/badge/JWT-Security-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" />
  <img src="https://img.shields.io/badge/CI-Passing-28a745?style=for-the-badge&logo=githubactions&logoColor=white" />
  <img src="https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=white" />
</p>

<h1 align="center">HR Service Integration Engine</h1>
<p align="center"><strong>TIBCO BusinessWorks Service Orchestration — Enterprise Integration Demo</strong></p>

<p align="center">
  <a href="#architecture">Architecture</a> •
  <a href="#use-case">Use Case</a> •
  <a href="#services">Services</a> •
  <a href="#tech-stack">Tech Stack</a> •
  <a href="#quick-start">Quick Start</a> •
  <a href="#screenshots">Screenshots</a> •
  <a href="#project-structure">Structure</a>
</p>

<br>

<p align="center">
  <a href="https://github.com/ajju853/HR-Service-Integration-Engine/actions"><img src="https://img.shields.io/github/actions/workflow/status/ajju853/HR-Service-Integration-Engine/.github%2Fworkflows%2Fci.yml?branch=main&label=CI%20Pipeline&logo=github" /></a>
</p>

---

## Overview

The **HR Service Integration Engine** is a production-style enterprise integration project demonstrating **service orchestration** using TIBCO BusinessWorks Community Edition. It solves a real-world problem: multiple enterprise systems (HR, Payroll, Attendance, Notification) that don't communicate directly. TIBCO BW acts as the middleware integration layer, orchestrating a seamless **New Employee Onboarding** flow across 4 microservices.

This project bridges the gap between **integration middleware** (TIBCO BW) and **modern microservices** (Spring Boot), wrapped in a React frontend with JWT security — exactly what enterprises run in production.

> See [`docs/architecture.md`](docs/architecture.md) for the full sequence diagram, component architecture, and service URLs.

### Key Features

- **JWT Authentication & Authorization** — Stateless Bearer token auth via Spring Security + jjwt
- **TIBCO BW Service Orchestration** — 7-step employee onboarding process with compensation
- **API Gateway Routing** — Spring Cloud Gateway with JWT validation on every request
- **Swagger/OpenAPI Documentation** — `/swagger-ui.html` on all 6 backend services
- **Dockerized Deployment** — 8 containers orchestrated via Docker Compose
- **PostgreSQL Persistence** — Employees, payroll, and attendance tables with seed data
- **GitHub Actions CI/CD** — Matrix build across all services + frontend, passing green
- **Centralized Error Handling** — `@ControllerAdvice` + TIBCO BW catch blocks
- **Compensation Transaction Pattern** — Rollback on payroll failure (delete created employee)
- **Graceful Degradation** — Non-critical failures (attendance, notification) log and continue

---

## 🏗️ Architecture

```
┌──────────────────────────────────────────────────────────────────┐
│                        HR Portal (React)                         │
│                    TypeScript + Material UI                       │
│                          Port 3000                                │
└────────────────────────────────┬─────────────────────────────────┘
                                 │
                                 ▼
┌──────────────────────────────────────────────────────────────────┐
│                    API Gateway (Spring Cloud Gateway)             │
│                     Port 8085 — Single Entry Point                │
│              JWT Validation · Rate Limiting · Routing             │
└────────────────────────────────┬─────────────────────────────────┘
                                 │
                                 ▼
┌──────────────────────────────────────────────────────────────────┐
│               TIBCO BusinessWorks Integration Layer               │
│           Service Orchestration · XML/JSON · Fault Handling       │
│                          Port 8080                                │
└──────────┬──────────────┬──────────────┬──────────────┬──────────┘
           │              │              │              │
           ▼              ▼              ▼              ▼
┌─────────────────┐ ┌─────────────┐ ┌──────────────┐ ┌────────────────┐
│ Employee Service│ │Payroll Svc  │ │Attendance Svc│ │Notification Svc│
│ Spring Boot     │ │Spring Boot  │ │Spring Boot   │ │Spring Boot     │
│ Port 8081       │ │Port 8082    │ │Port 8083     │ │Port 8084      │
├─────────────────┤ ├─────────────┤ ├──────────────┤ ├────────────────┤
│ POST /employees │ │POST /payroll│ │POST /att     │ │POST /send-email│
│ GET /employees  │ │   /create   │ │  /register   │ │POST /send-sms  │
│ PUT /employees  │ │GET /payroll │ │GET /att      │ │                │
│ DELETE /...     │ │  /{empId}   │ │  /{empId}    │ │                │
└────────┬────────┘ └──────┬──────┘ └──────┬───────┘ └───────┬────────┘
         │                 │               │                  │
         └─────────────────┴───────────────┴──────────────────┘
                           │
                           ▼
            ┌─────────────────────────────┐
            │     PostgreSQL Database      │
            │   integration_hub (port 5432)│
            │  employees · payroll · att   │
            └─────────────────────────────┘

┌──────────────────────────────────────────────────────────────────┐
│                    Auth Service (JWT)                             │
│                    Spring Security · Port 8086                    │
│              POST /auth/login → Bearer Token                      │
└──────────────────────────────────────────────────────────────────┘
```

---

## 🎯 Use Case: New Employee Onboarding

The primary business flow is **Employee Onboarding** — a classic service orchestration scenario.

### Step-by-Step Flow

| Step | Action | Service | Description |
|------|--------|---------|-------------|
| 1 | HR fills form | HR Portal (React) | Name, Email, Department, Salary |
| 2 | Request reaches TIBCO | API Gateway → TIBCO BW | `POST /api/onboard-employee` |
| 3 | Validate input | TIBCO (Java Snippet) | Email format, mandatory fields, duplicate check |
| 4 | Create employee | Employee Service → DB | `POST /employees` → `{ "employeeId":"EMP1001" }` |
| 5 | Create payroll | Payroll Service → DB | `POST /payroll/create` → salary account activated |
| 6 | Register attendance | Attendance Service → DB | `POST /attendance/register` → profile created |
| 7 | Send welcome email | Notification Service | `POST /send-email` → "Your Employee ID is EMP1001" |
| 8 | Aggregate & return | TIBCO BW | JSON response with all status flags |

### Sample Request

```json
POST /api/onboard-employee
{
  "name": "Ajim Patel",
  "email": "ajim@gmail.com",
  "department": "Engineering",
  "salary": 800000
}
```

### Sample Response

```json
{
  "employeeId": "EMP1001",
  "employeeCreated": true,
  "payrollCreated": true,
  "attendanceCreated": true,
  "notificationSent": true
}
```

---

## 🛠️ Services

| Service | Port | Technology | Key Endpoints | Swagger UI |
|---------|------|------------|---------------|------------|
| **HR Portal** | 3000 | React, TypeScript, MUI | `/login`, `/dashboard`, `/onboard` | — |
| **API Gateway** | 8085 | Spring Cloud Gateway | Routes `/api/*`, `/auth/*`, `/*/employees/*` | `/swagger-ui.html` |
| **TIBCO BW** | 8080 | TIBCO BusinessWorks CE | `POST /api/onboard-employee` | — |
| **Auth Service** | 8086 | Spring Boot + JWT | `POST /auth/login` → Bearer token | `/swagger-ui.html` |
| **Employee Service** | 8081 | Spring Boot + JPA | CRUD `/employees` | `/swagger-ui.html` |
| **Payroll Service** | 8082 | Spring Boot + JPA | `POST /payroll/create`, `GET /payroll/{id}` | `/swagger-ui.html` |
| **Attendance Service** | 8083 | Spring Boot + JPA | `POST /attendance/register`, `GET /attendance/{id}` | `/swagger-ui.html` |
| **Notification Service** | 8084 | Spring Boot | `POST /send-email`, `POST /send-sms` | `/swagger-ui.html` |

---

## 🧰 Tech Stack

| Layer | Technology |
|-------|-----------|
| **Frontend** | React 18, TypeScript, Material UI 5 |
| **Integration** | TIBCO BusinessWorks Community Edition |
| **Backend** | Java 17, Spring Boot 3.2, Spring Data JPA |
| **Security** | JWT (jjwt 0.12), Spring Security 6, Role-Based Access |
| **Database** | PostgreSQL 15 |
| **API Gateway** | Spring Cloud Gateway 2023.0 |
| **Build** | Maven 3.8+, npm |
| **DevOps** | Docker, Docker Compose, GitHub Actions |
| **Testing** | Postman Collection, JUnit 5 |
| **Version Control** | Git, GitHub |

---

## 🔐 Security

- **JWT Authentication** — Stateless Bearer token auth
- **Role-Based Access** — `HR_MANAGER`, `ADMIN` roles
- **API Gateway Validation** — All requests validated at gateway before routing
- **Spring Security** — Filter chain with `JwtAuthFilter`
- **Login Credentials**: `hr.admin` / `password123`

```http
POST /auth/login
Content-Type: application/json

{ "username": "hr.admin", "password": "password123" }

Response:
{ "token": "eyJhbGci...", "role": "HR_MANAGER" }
```

---

## ⚠️ Error Handling

| Scenario | TIBCO BW Action | HTTP Response |
|----------|----------------|---------------|
| **Payroll Service fails** | Compensation: delete employee record | `{ "status": "failed", "reason": "Payroll creation failed" }` |
| **Employee Service fails** | Return error to caller | `{ "status": "error", "message": "Employee service unavailable" }` |
| **Email notification fails** | Log warning, continue flow | `{ "notificationSent": false }` — partial success |
| **Attendance fails** | Log & continue (non-critical) | `{ "attendanceCreated": false }` — partial success |
| **Invalid input** | Validate early in Java Snippet | HTTP 400 with validation message |
| **Duplicate email** | Check before create | HTTP 409 `{ "status": "failed", "message": "Email already exists" }` |

### TIBCO BW Fault Handling Features Demonstrated
- Catch blocks per downstream service call
- Compensation/rollback transactions
- Global error handler for unhandled exceptions
- Graceful degradation (partial success responses)

---

## Monitoring

| Tool | What It Monitors |
|------|-----------------|
| **Spring Boot Actuator** | Health, metrics, info per service (e.g. `/actuator/health`) |
| **Gateway Actuator** | Route status, latency per route |
| **Application Logs** | `[Request Received → Employee Created → Payroll Created → Attendance Created → Email Sent → Completed]` |
| **Postman** | Manual endpoint testing with collection included |

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Node.js 18+
- Docker (optional, for PostgreSQL)
- TIBCO BusinessWorks CE (for BW integration)

### 1. Clone & Start Database

```bash
git clone https://github.com/ajju853/HR-Service-Integration-Engine.git
cd HR-Service-Integration-Engine

# Start PostgreSQL via Docker
docker compose -f docker/docker-compose.yml up postgres -d
```

### 2. Build All Backend Services

```bash
# From project root — builds all modules
mvn clean package -DskipTests
```

### 3. Run Backend Services (in separate terminals)

```bash
cd employee-service     && mvn spring-boot:run   # Port 8081
cd payroll-service      && mvn spring-boot:run   # Port 8082
cd attendance-service   && mvn spring-boot:run   # Port 8083
cd notification-service && mvn spring-boot:run   # Port 8084
cd auth-service         && mvn spring-boot:run   # Port 8086
cd gateway              && mvn spring-boot:run   # Port 8085
```

### 4. Start Frontend

```bash
cd hr-portal
npm install
npm start    # Opens http://localhost:3000
```

### 5. Login & Test

- **URL**: http://localhost:3000
- **Username**: `hr.admin`
- **Password**: `password123`
- Import Postman collection from `postman/` folder

---

## Screenshots

Visual proof of the working system. See `docs/screenshots/README.md` for capture instructions.

| Screenshot | What It Shows |
|-----------|---------------|
| `login-page.png` | HR Portal login form with username/password fields |
| `dashboard.png` | Dashboard with role badge, logout, Onboard + View cards |
| `onboarding-success.png` | Success response with employeeId and all flags = true |
| `employee-list.png` | Table of employees with codes, names, departments |
| `postman-login.png` | Postman showing JWT token response from `/auth/login` |
| `tibco-process.png` | TIBCO BW Studio — EmployeeOnboarding.process canvas |
| `ci-green.png` | GitHub Actions — all 7 jobs passing green |

---

## Project Structure

```
HR-Service-Integration-Engine/
│
├── hr-portal/                    # React 18 + TypeScript + Material UI
│   ├── src/
│   │   ├── components/           # LoginPage, Dashboard, OnboardEmployee
│   │   ├── services/             # Axios API client with JWT interceptor
│   │   └── App.tsx               # Router with auth guards
│   ├── public/index.html
│   ├── package.json
│   └── tsconfig.json
│
├── gateway/                      # Spring Cloud Gateway (port 8085)
│   ├── src/main/java/.../gateway/GatewayApplication.java
│   └── src/main/resources/application.yml   # Route definitions
│
├── auth-service/                 # JWT Auth Service (port 8086)
│   ├── src/main/java/.../auth/
│   │   ├── controller/AuthController.java
│   │   ├── model/LoginRequest.java, LoginResponse.java
│   │   └── security/JwtUtil.java, JwtAuthFilter.java, SecurityConfig.java
│   └── pom.xml
│
├── employee-service/             # Employee CRUD (port 8081)
│   ├── src/main/java/.../employee/
│   │   ├── controller/EmployeeController.java
│   │   ├── service/EmployeeService.java
│   │   ├── model/Employee.java
│   │   └── repository/EmployeeRepository.java
│   └── src/main/resources/data.sql
│
├── payroll-service/              # Payroll Management (port 8082)
│   ├── src/main/java/.../payroll/
│   │   ├── controller/PayrollController.java
│   │   ├── service/PayrollService.java
│   │   ├── model/Payroll.java
│   │   └── repository/PayrollRepository.java
│   └── pom.xml
│
├── attendance-service/           # Attendance Registration (port 8083)
│   ├── src/main/java/.../attendance/
│   │   ├── controller/AttendanceController.java
│   │   ├── service/AttendanceService.java
│   │   ├── model/Attendance.java
│   │   └── repository/AttendanceRepository.java
│   └── pom.xml
│
├── notification-service/         # Email/SMS Notifications (port 8084)
│   ├── src/main/java/.../notification/
│   │   └── controller/NotificationController.java
│   └── pom.xml
│
├── database/                     # PostgreSQL schema & seed data
│   └── init.sql
│
├── docker/                       # Docker Compose
│   └── docker-compose.yml        # 8 containers (PostgreSQL, 6 services, frontend)
├── employee-service/Dockerfile   # Multi-stage build
├── payroll-service/Dockerfile
├── attendance-service/Dockerfile
├── notification-service/Dockerfile
├── auth-service/Dockerfile
├── gateway/Dockerfile
│
├── postman/                      # API testing collection
│   └── enterprise-integration-hub.postman_collection.json
│
├── docs/                         # Architecture & process documentation
│   ├── architecture.md           # Sequence diagram, component diagram, URLs
│   ├── tibco-bw-flow.md          # TIBCO BW process specification
│   └── screenshots/              # Visual proof (see README for capture guide)
│
├── .github/workflows/            # CI/CD pipeline
│   └── ci.yml                    # Matrix build + frontend build
│
├── pom.xml                       # Parent Maven POM (6 modules)
├── .gitignore
└── README.md
```

---

## 🔄 CI/CD Pipeline

```yaml
GitHub Push → GitHub Actions → Build (Matrix: 6 Java services)
                              → Unit Tests
                              → Frontend Build (npm)
                              → Docker Build
                              → Ready for Deploy
```

---

## TIBCO BW Concepts Demonstrated

| Concept | Implementation |
|---------|---------------|
| **Service Orchestration** | Chained HTTP calls with data passing across 4 services |
| **REST Integration** | HTTP Receiver + REST Client palette items |
| **Process Flow Design** | Sequential orchestration with error branches |
| **Fault Handling** | Catch blocks per service call |
| **Compensation Logic** | Rollback on payroll failure (delete employee) |
| **Global Error Handling** | Top-level Catch for unhandled exceptions |
| **Shared Resources** | HTTPConnector shared resource |
| **Aggregator Pattern** | Composite response assembled from multiple service results |
| **Graceful Degradation** | Non-critical failures logged; flow continues |

---

## 🧪 Testing with Postman

Import `postman/enterprise-integration-hub.postman_collection.json` into Postman.

### Auth Flow
```
POST /auth/login → Get JWT token → Use in Authorization header
```

### Key Test Cases
| Test | Endpoint |
|------|----------|
| Login | `POST /auth/login` |
| Create Employee | `POST /employees` |
| Create Payroll | `POST /payroll/create` |
| Register Attendance | `POST /attendance/register` |
| Send Email | `POST /send-email` |
| Full Onboarding (via TIBCO) | `POST /api/onboard-employee` |

---

## 👨‍💻 About the Author

Built as a hands-on enterprise integration project demonstrating **TIBCO BusinessWorks service orchestration** combined with **Spring Boot microservices**, **React frontend**, and **JWT security**. This project is interview-ready and demonstrates production-grade skills in:

- Enterprise Integration & Middleware (TIBCO BW)
- Service-Oriented Architecture (SOA)
- Microservices Development (Spring Boot)
- Frontend Development (React + TypeScript)
- API Gateway & Security (JWT)
- Containerization & DevOps (Docker, CI/CD)
- Database Design (PostgreSQL)
- API Testing (Postman)

---

## 📄 License

MIT License — free to use, modify, and distribute.

---

<p align="center">
  <strong>Built with ❤️ for Enterprise Integration Excellence</strong>
  <br />
  <sub>TIBCO BusinessWorks · Spring Boot · React · PostgreSQL · Docker</sub>
</p>
