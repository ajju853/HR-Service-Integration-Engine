package com.enterprise.payroll.service;

import com.enterprise.payroll.model.Payroll;
import com.enterprise.payroll.repository.PayrollRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollService {

    private final PayrollRepository repository;

    public PayrollService(PayrollRepository repository) { this.repository = repository; }

    public Payroll create(Long employeeId, BigDecimal salary) {
        Payroll payroll = new Payroll();
        payroll.setEmployeeId(employeeId);
        payroll.setSalary(salary);
        payroll.setStatus("ACTIVE");
        return repository.save(payroll);
    }

    public Optional<Payroll> getByEmployeeId(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<Payroll> getAll() { return repository.findAll(); }

    public boolean exists(Long employeeId) { return repository.existsByEmployeeId(employeeId); }

    public void deleteByEmployeeId(Long employeeId) {
        repository.findByEmployeeId(employeeId).ifPresent(repository::delete);
    }
}
