# TIBCO BusinessWorks Studio Project

## Important Note

The `.bwp` files in this project define the orchestration logic but are hand-crafted reference files. **You must open them in TIBCO Studio and re-save them** before they can be used in production. TIBCO Studio will regenerate the proper internal XML format when it opens and re-saves each process.

## Importing into TIBCO Studio

1. Install **TIBCO BusinessWorks Community Edition** from [TIBCO Downloads](https://download.tibco.com)
2. Launch **TIBCO Studio**
3. Go to `File → Import → Existing Projects into Workspace`
4. Select root directory: `tibco-bw/EnterpriseIntegrationHub`
5. Click **Finish**
6. Open each `.bwp` file in the Process Editor and re-save it (Studio upgrades the palette format)
7. Fix any shared resource links (HTTPConnector, JDBCConnector) to point to your local TIBCO installation

## Project Contents

| File | Description |
|------|-------------|
| `.project` | Eclipse project definition |
| `.module` | TIBCO module metadata |
| `Processes/EmployeeOnboarding.bwp` | Onboarding orchestration (7-step process with compensation) |
| `Processes/EmployeeProfile.bwp` | Employee profile query process |
| `Resources/HTTPConnector.sharedresource` | HTTP connection pool (port 8080) |
| `Resources/JDBCConnector.sharedresource` | PostgreSQL JDBC connector |
| `Schemas/EmployeeSchema.xsd` | Request/response XML schemas |

## Deploying

1. Build EAR: Right-click project → `Build EAR`
2. Deploy to TIBCO Runtime
3. Ensure all Spring Boot services are running on ports 8081-8084
4. Test: `POST http://localhost:8080/api/onboard-employee`

## Flow: EmployeeOnboarding.bwp

```
HTTP Receive → Validate → Create Employee → Create Payroll → Register Attendance → Send Email → Aggregate → Return
                 ↓            ↓                 ↓                  ↓                ↓
             Error End   Catch+Retry    Catch+Compensate     Catch+Continue   Catch+Continue
```

## Error Handling Matrix

| Step | Failure | Action |
|------|---------|--------|
| 1. Create Employee | Service down | Return error (503) |
| 2. Create Payroll | Invalid ID | **Compensation**: delete created employee (500) |
| 3. Register Attendance | Not found | Log warning, continue flow |
| 4. Send Email | SMTP fail | Log warning, continue flow |
| Any | Unexpected | Global error handler (500) |
