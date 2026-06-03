# Architecture Documentation

## Sequence Diagram — Employee Onboarding Flow

```mermaid
sequenceDiagram
    actor HR as HR Manager
    participant UI as HR Portal (React :3000)
    participant GW as API Gateway (Spring Cloud :8085)
    participant TIBCO as TIBCO BW CE (:8080)
    participant Auth as Auth Service (:8086)
    participant Emp as Employee Service (:8081)
    participant Pay as Payroll Service (:8082)
    participant Att as Attendance Service (:8083)
    participant Notif as Notification Service (:8084)
    participant DB as PostgreSQL (:5432)

    Note over HR,DB: === Step 1: Authentication ===
    HR->>UI: Enter credentials (hr.admin / password123)
    UI->>GW: POST /auth/login
    GW->>Auth: POST /auth/login
    Auth->>Auth: Validate credentials
    Auth-->>GW: { token: "eyJhbGci..." , role: "HR_MANAGER" }
    GW-->>UI: LoginResponse (JWT + role)
    UI->>UI: Store token in localStorage

    Note over HR,DB: === Step 2: Onboard Employee ===
    HR->>UI: Fill form (name, email, dept, salary)
    UI->>GW: POST /api/onboard-employee<br/>(Authorization: Bearer <JWT>)
    GW->>GW: JwtAuthFilter validates token
    GW->>TIBCO: POST /api/onboard-employee<br/>(with X-User-Id, X-User-Role headers)

    Note over TIBCO: === TIBCO BW Orchestration Process ===
    TIBCO->>TIBCO: HTTP Receiver — accept request
    TIBCO->>TIBCO: Validate Input (Java Snippet)
    
    TIBCO->>Emp: POST /employees (create employee)
    Emp->>DB: INSERT INTO employees
    DB-->>Emp: employee record
    Emp-->>TIBCO: { employeeId: "EMP1002", employeeCode: "EMP1002" }

    TIBCO->>Pay: POST /payroll/create
    Pay->>DB: INSERT INTO payroll
    DB-->>Pay: payroll record
    Pay-->>TIBCO: { payrollId: 1, employeeId: 1, status: "ACTIVE" }

    TIBCO->>Att: POST /attendance/register
    Att->>DB: INSERT INTO attendance
    DB-->>Att: attendance record
    Att-->>TIBCO: { attendanceId: 1, shift: "GENERAL", status: "PRESENT" }

    TIBCO->>Notif: POST /send-email
    Notif-->>TIBCO: { status: "sent", channel: "email" }

    TIBCO->>TIBCO: Aggregate Results (Java Snippet)
    TIBCO-->>GW: HTTP 200 — { employeeId, employeeCreated,<br/>payrollCreated, attendanceCreated, notificationSent }

    GW-->>UI: Onboarding response
    UI-->>HR: Success toast + result table

    Note over HR,DB: === Compensation (if Payroll fails) ===
    TIBCO->>Emp: DELETE /employees/{id}
    Emp->>DB: DELETE FROM employees
    DB-->>Emp: deleted
    Emp-->>TIBCO: 204 No Content
```

## Component Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    HR Manager (Browser)                      │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│              React Portal (hr-portal :3000)                  │
│    Login · Dashboard · OnboardEmployee · EmployeeList        │
└──────────────────────┬──────────────────────────────────────┘
                       │ HTTP (JWT Bearer)
┌──────────────────────▼──────────────────────────────────────┐
│          API Gateway (gateway :8085)                         │
│  JwtAuthFilter · Route to all services · 401 on bad token    │
└──┬────────┬──────────┬──────────┬──────────┬───────────────┘
   │        │          │          │          │
   ▼        ▼          ▼          ▼          ▼
┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐ ┌──────────┐
│ Auth  │ │ TIBCO│ │ Emp  │ │ Pay  │ │Notification│
│:8086 │ │ BW   │ │:8081 │ │:8082 │ │ :8084     │
│JWT   │ │ CE   │ │CRUD  │ │Mgmt  │ │ Email/SMS │
│Login │ │:8080 │ │Emp   │ │Payroll│ │ (mock)    │
└──────┘ └──────┘ └──┬───┘ └──┬───┘ └──────────┘
                      │        │
                      ▼        ▼
                    ┌──────────────┐
                    │  Attendance  │
                    │  :8083       │
                    │  Register    │
                    └──────────────┘
                           │
                           ▼
                    ┌──────────────┐
                    │  PostgreSQL  │
                    │  :5432       │
                    │hub-integration│
                    └──────────────┘
```

## Error Handling Strategy

| Step | Failure | Action | Status Code |
|------|---------|--------|-------------|
| 1. Create Employee | Service down | Return error to TIBCO | 503 |
| 2. Create Payroll | Employee ID invalid | Compensation: delete employee | 500 |
| 3. Register Attendance | Employee not found | Log warning, continue flow | 200 (warn) |
| 4. Send Notification | SMTP unavailable | Log warning, continue flow | 200 (warn) |
| Any | Unexpected exception | Global error handler | 500 |

## Service URLs (Local)

| Service | URL | Swagger |
|---------|-----|---------|
| API Gateway | http://localhost:8085 | http://localhost:8085/swagger-ui.html |
| Auth Service | http://localhost:8086 | http://localhost:8086/swagger-ui.html |
| Employee Service | http://localhost:8081 | http://localhost:8081/swagger-ui.html |
| Payroll Service | http://localhost:8082 | http://localhost:8082/swagger-ui.html |
| Attendance Service | http://localhost:8083 | http://localhost:8083/swagger-ui.html |
| Notification Service | http://localhost:8084 | http://localhost:8084/swagger-ui.html |
| TIBCO BW CE | http://localhost:8080 | — |
| HR Portal | http://localhost:3000 | — |
| PostgreSQL | localhost:5432 | — |
