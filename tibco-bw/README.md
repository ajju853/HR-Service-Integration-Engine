# TIBCO BusinessWorks Studio Project

## Importing into TIBCO Studio

1. Install **TIBCO BusinessWorks Community Edition** from [TIBCO Downloads](https://download.tibco.com)
2. Launch **TIBCO Studio**
3. Go to `File → Import → Existing Projects into Workspace`
4. Select root directory: `tibco-bw/EnterpriseIntegrationHub`
5. Click **Finish**

## Project Contents

| File | Description |
|------|-------------|
| `.project` | Eclipse project definition |
| `.module` | TIBCO module metadata |
| `Processes/EmployeeOnboarding.bwp` | Onboarding orchestration (7-step process) |
| `Processes/EmployeeProfile.bwp` | Employee profile query process |
| `Resources/HTTPConnector.sharedresource` | HTTP connection pool (port 8080) |
| `Resources/JDBCConnector.sharedresource` | PostgreSQL JDBC connector |
| `Schemas/EmployeeSchema.xsd` | Request/response XML schemas |

## Deploying

1. Right-click process → `Run As → TIBCO BusinessWorks Application`
2. Ensure all Spring Boot services are running on ports 8081-8084
3. Test with Postman: `POST http://localhost:8080/api/onboard-employee`

## Flow: EmployeeOnboarding.bwp

```
HTTP Receive → Validate → Create Employee → Create Payroll → Register Attendance → Send Email → Aggregate → Return
                 ↓            ↓                 ↓                  ↓                ↓
             Error End   Catch+Retry    Catch+Compensate     Catch+Continue   Catch+Continue
```
