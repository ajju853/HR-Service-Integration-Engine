package com.enterprise.payroll.service;

import com.enterprise.payroll.model.Payroll;
import com.enterprise.payroll.repository.PayrollRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayrollServiceTest {

    @Mock
    private PayrollRepository repository;

    @InjectMocks
    private PayrollService service;

    @Test
    void create_shouldSetActiveStatus() {
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Payroll result = service.create(1L, new BigDecimal("800000"));
        assertEquals("ACTIVE", result.getStatus());
        assertEquals(1L, result.getEmployeeId());
    }

    @Test
    void exists_shouldReturnTrue_whenPayrollExists() {
        when(repository.existsByEmployeeId(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }
}
