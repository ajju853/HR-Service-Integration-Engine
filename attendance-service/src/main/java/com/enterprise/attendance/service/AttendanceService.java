package com.enterprise.attendance.service;

import com.enterprise.attendance.model.Attendance;
import com.enterprise.attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttendanceService {

    private final AttendanceRepository repository;

    public AttendanceService(AttendanceRepository repository) { this.repository = repository; }

    public Attendance register(Long employeeId, String shift) {
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setShift(shift != null ? shift : "GENERAL");
        attendance.setStatus("ACTIVE");
        return repository.save(attendance);
    }

    public Optional<Attendance> getByEmployeeId(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public void deleteByEmployeeId(Long employeeId) {
        repository.findByEmployeeId(employeeId).ifPresent(repository::delete);
    }
}
