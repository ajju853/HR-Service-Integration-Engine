-- HR Service Integration Engine - Database Schema
-- PostgreSQL

CREATE DATABASE integration_hub;

\c integration_hub;

-- Employees
CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    employee_code VARCHAR(20),
    name VARCHAR(100),
    email VARCHAR(100),
    department VARCHAR(50),
    joining_date DATE
);

-- Payroll
CREATE TABLE payroll (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT,
    salary NUMERIC,
    status VARCHAR(20)
);

-- Attendance
CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT,
    shift VARCHAR(20),
    status VARCHAR(20)
);

-- Seed
INSERT INTO employees (employee_code, name, email, department, joining_date) VALUES
    ('EMP1001', 'Ajim Patel', 'ajim@gmail.com', 'Engineering', '2024-01-15');
INSERT INTO payroll (employee_id, salary, status) VALUES (1, 800000, 'ACTIVE');
INSERT INTO attendance (employee_id, shift, status) VALUES (1, 'GENERAL', 'ACTIVE');
