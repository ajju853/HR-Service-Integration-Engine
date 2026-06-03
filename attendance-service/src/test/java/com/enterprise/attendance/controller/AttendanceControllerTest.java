package com.enterprise.attendance.controller;

import com.enterprise.attendance.model.Attendance;
import com.enterprise.attendance.service.AttendanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceControllerTest {

    @Mock
    private AttendanceService attendanceService;

    @InjectMocks
    private AttendanceController controller;

    @Test
    void register_shouldReturnCreated() {
        Attendance att = new Attendance();
        att.setId(1L);
        att.setEmployeeId(1L);
        att.setShift("GENERAL");
        att.setStatus("ACTIVE");
        when(attendanceService.register(anyLong(), any())).thenReturn(att);

        ResponseEntity<?> response = controller.register(Map.of("employeeId", 1, "shift", "GENERAL"));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
