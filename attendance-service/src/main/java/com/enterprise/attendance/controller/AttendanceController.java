package com.enterprise.attendance.controller;

import com.enterprise.attendance.model.Attendance;
import com.enterprise.attendance.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) { this.attendanceService = attendanceService; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> request) {
        Long employeeId = Long.valueOf(request.get("employeeId").toString());
        String shift = (String) request.get("shift");
        Attendance attendance = attendanceService.register(employeeId, shift);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("attendanceId", attendance.getId(), "employeeId", attendance.getEmployeeId(),
                        "shift", attendance.getShift(), "status", attendance.getStatus()));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Long employeeId) {
        return attendanceService.getByEmployeeId(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
