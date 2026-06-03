package com.enterprise.attendance.service;

import com.enterprise.attendance.model.Attendance;
import com.enterprise.attendance.repository.AttendanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceRepository repository;

    @InjectMocks
    private AttendanceService service;

    @Test
    void register_shouldSetDefaultShift() {
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Attendance result = service.register(1L, null);
        assertEquals("GENERAL", result.getShift());
        assertEquals("ACTIVE", result.getStatus());
    }

    @Test
    void register_shouldUseProvidedShift() {
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Attendance result = service.register(1L, "NIGHT");
        assertEquals("NIGHT", result.getShift());
    }
}
