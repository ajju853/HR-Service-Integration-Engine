package com.enterprise.employee.service;

import com.enterprise.employee.model.Employee;
import com.enterprise.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    @Test
    void create_shouldSetEmployeeCode() {
        Employee input = new Employee();
        input.setName("Test User");
        input.setEmail("test@example.com");
        input.setDepartment("Engineering");

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Employee result = service.create(input);
        assertNotNull(result.getEmployeeCode());
        assertTrue(result.getEmployeeCode().startsWith("EMP"));
    }

    @Test
    void emailExists_shouldReturnTrue_whenEmailTaken() {
        when(repository.existsByEmail("test@example.com")).thenReturn(true);
        assertTrue(service.emailExists("test@example.com"));
    }

    @Test
    void getByEmployeeCode_shouldReturnEmployee() {
        Employee emp = new Employee();
        emp.setEmployeeCode("EMP1001");
        when(repository.findByEmployeeCode("EMP1001")).thenReturn(Optional.of(emp));
        Optional<Employee> result = service.getByEmployeeCode("EMP1001");
        assertTrue(result.isPresent());
        assertEquals("EMP1001", result.get().getEmployeeCode());
    }
}
