INSERT INTO employees (id, employee_code, name, email, department, joining_date) VALUES
    (1, 'EMP1001', 'Ajim Patel', 'ajim@gmail.com', 'Engineering', '2024-01-15')
ON CONFLICT (id) DO NOTHING;
