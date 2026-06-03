package com.enterprise.employee.controller;

import com.enterprise.employee.model.Employee;
import com.enterprise.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeController controller;

    @Test
    void create_shouldReturnConflict_whenEmailExists() {
        Employee emp = new Employee();
        emp.setEmail("dup@example.com");
        when(service.emailExists("dup@example.com")).thenReturn(true);

        ResponseEntity<?> response = controller.create(emp);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Map body = (Map) response.getBody();
        assertEquals("failed", body.get("status"));
    }

    @Test
    void getByCode_shouldReturnEmployee() {
        Employee emp = new Employee();
        emp.setEmployeeCode("EMP1001");
        when(service.getByEmployeeCode("EMP1001")).thenReturn(Optional.of(emp));

        ResponseEntity<Employee> response = controller.getByCode("EMP1001");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("EMP1001", response.getBody().getEmployeeCode());
    }

    @Test
    void getByCode_shouldReturn404_whenNotFound() {
        when(service.getByEmployeeCode("NONEXIST")).thenReturn(Optional.empty());
        ResponseEntity<Employee> response = controller.getByCode("NONEXIST");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
