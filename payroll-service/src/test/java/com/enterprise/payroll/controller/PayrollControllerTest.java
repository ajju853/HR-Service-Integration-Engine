package com.enterprise.payroll.controller;

import com.enterprise.payroll.service.PayrollService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayrollControllerTest {

    @Mock
    private PayrollService payrollService;

    @InjectMocks
    private PayrollController controller;

    @Test
    void create_shouldReturnConflict_whenPayrollExists() {
        when(payrollService.exists(1L)).thenReturn(true);
        ResponseEntity<?> response = controller.create(Map.of("employeeId", 1, "salary", 800000));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
