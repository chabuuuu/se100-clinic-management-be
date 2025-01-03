package com.se100.clinic_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.se100.clinic_management.model.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByUsername(String username);
}
