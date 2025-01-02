package com.se100.clinic_management.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import com.se100.clinic_management.Interface.iEmployeeService;
import com.se100.clinic_management.dto.employee.RefreshTokenReq;
import com.se100.clinic_management.dto.employee.RefreshTokenRes;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.employee.EmployeeLoginReq;
import com.se100.clinic_management.dto.employee.EmployeeLoginRes;
import com.se100.clinic_management.dto.employee.EmployeeProfileDTO;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.se100.clinic_management.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {

  @Autowired
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

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ResponseVO> deleteEmployee(@PathVariable int id) {
    employeeService.deleteEmployee(id);
    return ResponseEntityGenerator.deleteFormat("Employee deleted successfully with ID: " + id);
  }

  @PutMapping("update/{id}")
  public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody Employee employeeDetails) {
    Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
    return ResponseEntity.ok("Employee updated successfully with ID: " + updatedEmployee.getId());
  }

  // Lấy toàn bộ thông tin của employee
  @GetMapping("get/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
    Employee employee = employeeService.getEmployeeById(id);
    return ResponseEntity.ok(employee);
  }

  // Đường dẫn thư mục lưu trữ tệp tải lên
  @Value("${file.upload-dir}")
  private String uploadDir;

  // Tải lên avatar cho employee
  @PostMapping("/upload-avatar")
  public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file) {

    var jwtTokenVo = SecurityUtil.getSession();

    int id = jwtTokenVo.getUserId();

    Employee employee = employeeService.getEmployeeById(id);

    if (employee == null) {
      return ResponseEntity.notFound().build();
    }

    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("No file selected");
    }

    try {
      // Lưu tệp vào thư mục
      String filename = id + "_" + file.getOriginalFilename();
      Path path = Paths.get(uploadDir + "/" + filename);
      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

      // Cập nhật avatar cho employee
      String avatarLink = ServletUriComponentsBuilder.fromCurrentContextPath()
          .path("api/files/view-image/")
          .path(filename)
          .toUriString();

      employee.setAvatar(avatarLink);
      employeeService.updateEmployee(id, employee); // Lưu employee với avatar mới

      return ResponseEntity.ok("Avatar uploaded successfully");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
    }
  }

  // Lấy thông tin profile của employee
  @GetMapping("/profile")
  public ResponseEntity<EmployeeProfileDTO> getProfile() {

    var jwtTokenVo = SecurityUtil.getSession();

    int id = jwtTokenVo.getUserId();

    Employee employee = employeeService.getEmployeeById(id);

    if (employee == null) {
      return ResponseEntity.notFound().build();
    }

    EmployeeProfileDTO profile = new EmployeeProfileDTO(
        employee.getId(),
        employee.getUsername(),
        employee.getFullname(),
        employee.getEmail(),
        employee.getRole(),
        employee.getShift(),
        employee.getDob(),
        employee.getPhoneNumber(),
        employee.getDepartment(),
        employee.isGender(),
        employee.getAvatar());
    return ResponseEntity.ok(profile);
  }

  // Lấy danh sách employee với phân trang, tìm kiếm và lọc
  @GetMapping
  public Page<Employee> getEmployees(
      @RequestParam(required = false) String fullname,
      @RequestParam(required = false) String role,
      @RequestParam(required = false) String createdAfter,
      @RequestParam(required = false) String createdBefore,
      @RequestParam(required = false) String search,
      @PageableDefault(size = Integer.MAX_VALUE, page = 0) Pageable pageable) {

    LocalDate createdAfterDate = createdAfter != null ? LocalDate.parse(createdAfter) : null;
    LocalDate createdBeforeDate = createdBefore != null ? LocalDate.parse(createdBefore) : null;

    return employeeService.getEmployees(fullname, role, createdAfterDate, createdBeforeDate, search, pageable);
  }
}