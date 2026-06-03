package com.enterprise.employee.service;

import com.enterprise.employee.model.Employee;
import com.enterprise.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final AtomicLong counter = new AtomicLong(1001);

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll() { return repository.findAll(); }

    public Optional<Employee> getById(Long id) { return repository.findById(id); }

    public Optional<Employee> getByEmployeeCode(String code) { return repository.findByEmployeeCode(code); }

    public boolean emailExists(String email) { return repository.existsByEmail(email); }

    public Employee create(Employee employee) {
        if (employee.getJoiningDate() == null) {
            employee.setJoiningDate(LocalDate.now());
        }
        employee.setEmployeeCode("EMP" + counter.getAndIncrement());
        return repository.save(employee);
    }

    public Optional<Employee> update(Long id, Employee updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            existing.setDepartment(updated.getDepartment());
            existing.setJoiningDate(updated.getJoiningDate());
            return repository.save(existing);
        });
    }

    public void delete(Long id) { repository.deleteById(id); }
}
