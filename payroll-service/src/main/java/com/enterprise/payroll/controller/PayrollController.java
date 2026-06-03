package com.enterprise.payroll.controller;

import com.enterprise.payroll.model.Payroll;
import com.enterprise.payroll.service.PayrollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) { this.payrollService = payrollService; }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> request) {
        Long employeeId = Long.valueOf(request.get("employeeId").toString());
        BigDecimal salary = new BigDecimal(request.get("salary").toString());

        if (payrollService.exists(employeeId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("status", "failed", "message", "Payroll already exists"));
        }
        Payroll payroll = payrollService.create(employeeId, salary);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("payrollId", payroll.getId(), "employeeId", payroll.getEmployeeId(), "status", payroll.getStatus()));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Long employeeId) {
        return payrollService.getByEmployeeId(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
