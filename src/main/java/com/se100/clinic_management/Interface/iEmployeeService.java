package com.se100.clinic_management.Interface;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.se100.clinic_management.model.Employee;

public interface iEmployeeService {
    Employee addEmployee(Employee employee);

    void deleteEmployee(int id);

    Employee updateEmployee(int id, Employee employeeDetails);

    Page<Employee> getEmployees(String role, String name, LocalDate createdAfter, LocalDate createdBefore,
            String search, Pageable pageable);
}
