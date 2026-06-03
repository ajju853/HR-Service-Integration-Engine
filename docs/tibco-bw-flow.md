# HR Service Integration Engine — TIBCO BW Process Design

## High-Level Architecture

```
HR Portal (React / TypeScript / MUI)  →  port 3000
       |
       v
API Gateway (Spring Cloud Gateway)    →  port 8085
       |
       v
TIBCO BusinessWorks Integration Layer →  port 8080
       |
  +----+----+----+
  |    |    |    |
  v    v    v    v
Emp  Pay  Att   Notif
(81) (82) (83)  (84)
```

## Onboarding Flow: POST /api/onboard-employee

### Step 1 — Receive Request (HTTP Receiver)
- **Resource**: `POST /api/onboard-employee`
- **Input**:
```json
{
  "name":"Ajim Patel",
  "email":"ajim@gmail.com",
  "department":"Engineering",
  "salary":800000
}
```

### Step 2 — Validate Input
- Email format, mandatory fields, duplicate email check via `GET /employees?email=...`

### Step 3 — Call Employee Service
```
POST http://localhost:8081/employees
{
  "name":"Ajim Patel",
  "email":"ajim@gmail.com",
  "department":"Engineering"
}
```
Response: `{ "employeeId":"EMP1001" }` → store in process variable

### Step 4 — Call Payroll Service
```
POST http://localhost:8082/payroll/create
{ "employeeId":1, "salary":800000 }
```

### Step 5 — Call Attendance Service
```
POST http://localhost:8083/attendance/register
{ "employeeId":1 }
```

### Step 6 — Send Email
```
POST http://localhost:8084/send-email
{ "to":"ajim@gmail.com", "subject":"Welcome", "body":"Your Employee ID is EMP1001" }
```

### Step 7 — Aggregate & Return
```json
{
  "employeeId":"EMP1001",
  "employeeCreated":true,
  "payrollCreated":true,
  "attendanceCreated":true,
  "notificationSent":true
}
```

## Error Handling

| Scenario              | Action                               |
|-----------------------|--------------------------------------|
| Employee Service down | Retry (2x, 1s) → return error        |
| Payroll Service down  | Compensation: delete employee record |
| Attendance failure    | Log & continue (non-critical)        |
| Email failure         | Log & retry                          |

## TIBCO BW Palette Usage

| Item               | Purpose                         |
|--------------------|---------------------------------|
| HTTP Receiver      | Start — POST /api/onboard-employee |
| REST Client (×4)   | Call Employee/Payroll/Attendance/Notification |
| XML Parse          | Parse XML input (legacy)        |
| JSON Transform     | XML ↔ JSON conversion           |
| Mapper             | Map fields between schemas      |
| Java Snippet       | Validation logic                |
| Catch              | Fault handler per service call  |
| End Event          | Return HTTP response            |

## Monitoring Logs
```
Request Received → Employee Created → Payroll Created → Attendance Created → Email Sent → Workflow Completed
```
