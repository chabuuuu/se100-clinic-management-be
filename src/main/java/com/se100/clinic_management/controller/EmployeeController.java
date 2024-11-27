package com.se100.clinic_management.controller;

import java.time.LocalDate;

import com.se100.clinic_management.Interface.iEmployeeService;
import com.se100.clinic_management.dto.employee.RefreshTokenReq;
import com.se100.clinic_management.dto.employee.RefreshTokenRes;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.employee.EmployeeLoginReq;
import com.se100.clinic_management.dto.employee.EmployeeLoginRes;
import com.se100.clinic_management.exception.BaseError;
import com.se100.clinic_management.utils.ResponseEntityGenerator;
import com.se100.clinic_management.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final iEmployeeService employeeService;

  @SneakyThrows
  @PostMapping("/refresh-token")
  public ResponseEntity<RefreshTokenRes> refreshAccessToken(@RequestBody RefreshTokenReq request) {
    try {
      String refreshToken = request.getRefreshToken();
      var decodedJWT = SecurityUtil.validate(refreshToken);
      var jwtTokenVo = SecurityUtil.getValueObject(decodedJWT);
      var newAccessToken = SecurityUtil.createToken(jwtTokenVo);

      RefreshTokenRes response = new RefreshTokenRes();
      response.setAccessToken(newAccessToken);
      response.setRefreshToken(refreshToken);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      throw new BaseError("INVALID_REFRESH_TOKEN", "Invalid refresh token", HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseVO> login(@RequestBody EmployeeLoginReq employeeLoginReq) {
    EmployeeLoginRes result = employeeService.login(employeeLoginReq);
    return ResponseEntityGenerator.okFormat(result);
  }

  @PostMapping("/add")
  public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
    Employee createdEmployee = employeeService.addEmployee(employee);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Employee created successfully with ID: " + createdEmployee.getId());
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
    employeeService.deleteEmployee(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body("Employee deleted successfully with ID: " + id);
  }

  @PutMapping("update/{id}")
  public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody Employee employeeDetails) {
    Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
    return ResponseEntity.ok("Employee updated successfully with ID: " + updatedEmployee.getId());
  }

  // url example:
  // api/employees?role=DOCTOR&createdAfter=2024-01-01&createdBefore=2024-11-30&page=0&size=5&sort=id,asc
  // Lọc nhân viên theo nhiều tham số (tên, vai trò, ngày tạo)
  @GetMapping
  public Page<Employee> getEmployees(
      @RequestParam(required = false) String fullname,
      @RequestParam(required = false) String role,
      @RequestParam(required = false) String createdAfter,
      @RequestParam(required = false) String createdBefore,
      @RequestParam(required = false) String search,
      Pageable pageable) {

    LocalDate createdAfterDate = createdAfter != null ? LocalDate.parse(createdAfter) : null;
    LocalDate createdBeforeDate = createdBefore != null ? LocalDate.parse(createdBefore) : null;

    return employeeService.getEmployees(fullname, role, createdAfterDate, createdBeforeDate, search, pageable);
  }
}