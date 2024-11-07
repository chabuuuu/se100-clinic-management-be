package com.se100.clinic_management.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.service.EmployeeServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

  @Autowired
  private EmployeeServiceImpl employeeServiceImpl;

  @PostMapping("/add")
  public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
    Employee createdEmployee = employeeServiceImpl.addEmployee(employee);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Employee created successfully with ID: " + createdEmployee.getId());
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
    employeeServiceImpl.deleteEmployee(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body("Employee deleted successfully with ID: " + id);
  }

  @PutMapping("update/{id}")
  public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody Employee employeeDetails) {
    Employee updatedEmployee = employeeServiceImpl.updateEmployee(id, employeeDetails);
    return ResponseEntity.ok("Employee updated successfully with ID: " + updatedEmployee.getId());
  }

  // url example:
  // api/employees?role=DOCTOR&startDate=2024-01-01&endDate=2024-11-30&page=0&size=5&sort=id,asc
  // Lọc nhân viên theo nhiều tham số (tên, vai trò, ngày tạo)
  @GetMapping
  public Page<Employee> getEmployees(
      @RequestParam(required = false) String fullname,
      @RequestParam(required = false) String role,
      @RequestParam(required = false) String startDate,
      @RequestParam(required = false) String endDate,
      Pageable pageable) {

    LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
    LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;

    return employeeServiceImpl.getEmployees(fullname, role, start, end, pageable);
  }
}