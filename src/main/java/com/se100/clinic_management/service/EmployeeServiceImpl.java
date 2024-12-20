package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iEmployeeService;
import com.se100.clinic_management.dto.JwtTokenVo;
import com.se100.clinic_management.dto.employee.EmployeeLoginReq;
import com.se100.clinic_management.dto.employee.EmployeeLoginRes;
import com.se100.clinic_management.exception.BaseError;
import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.repository.EmployeeRepository;
import com.se100.clinic_management.specification.EmployeeSpecification;
import com.se100.clinic_management.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements iEmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @SneakyThrows
    @Override
    public EmployeeLoginRes login(EmployeeLoginReq employeeLoginReq) {
        Employee employee = employeeRepository.findByUsername(employeeLoginReq.getUsername()).orElse(null);

        if (employee == null) {
            throw new BaseError("USER_NOT_FOUND", "User not found", HttpStatus.BAD_REQUEST);
        }

        if (!employee.getPassword().equals(employeeLoginReq.getPassword())) {
            throw new BaseError("WRONG_PASSWORD", "Wrong password", HttpStatus.BAD_REQUEST);
        }

        JwtTokenVo jwtTokenVo = new JwtTokenVo(employee.getId(), employee.getUsername(), List.of(employee.getRole()));

        String accessToken = SecurityUtil.createToken(jwtTokenVo);

        return new EmployeeLoginRes(accessToken);
    }

    // Thêm nhân viên mới
    @Override
    public Employee addEmployee(Employee employee) {
        // Xử lý logic trước khi lưu (nếu cần, ví dụ mã hóa mật khẩu)
        // Đặt thời gian tạo nhân viên
        employee.setCreateAt(java.time.LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    // Xóa nhân viên theo id
    @Override
    public void deleteEmployee(int id) {
        // Kiểm tra xem nhân viên có tồn tại không trước khi xóa (tuỳ chọn)
        if (employeeRepository.existsById(id)) {
            Employee employee = employeeRepository.findById(id).orElse(null);
            if (employee != null) {
                employee.setDeleteAt(java.time.LocalDateTime.now());
                employeeRepository.save(employee);
            }
        }
    }

    // Cập nhật thông tin nhân viên
    @Override
    public Employee updateEmployee(int id, Employee employeeDetails) {
        // Kiểm tra xem nhân viên có tồn tại không
        if (employeeRepository.existsById(id)) {
            // Tìm nhân viên và cập nhật các trường
            Employee existingEmployee = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            // Cập nhật username nếu có giá trị mới
            if (employeeDetails.getUsername() != null) {
                existingEmployee.setUsername(employeeDetails.getUsername());
            }

            // Cập nhật password nếu có giá trị mới
            if (employeeDetails.getPassword() != null) {
                existingEmployee.setPassword(employeeDetails.getPassword());
            }

            // Cập nhật fullname nếu có giá trị mới
            if (employeeDetails.getFullname() != null) {
                existingEmployee.setFullname(employeeDetails.getFullname());
            }

            // Cập nhật email nếu có giá trị mới
            if (employeeDetails.getEmail() != null) {
                existingEmployee.setEmail(employeeDetails.getEmail());
            }

            // Cập nhật role nếu có giá trị mới
            if (employeeDetails.getRole() != null) {
                existingEmployee.setRole(employeeDetails.getRole());
            }

            // Cập nhật shift nếu có giá trị mới
            if (employeeDetails.getShift() != null) {
                existingEmployee.setShift(employeeDetails.getShift());
            }

            // Cập nhật dob nếu có giá trị mới
            if (employeeDetails.getDob() != null) {
                existingEmployee.setDob(employeeDetails.getDob());
            }

            // Cập nhật phoneNumber nếu có giá trị mới
            if (employeeDetails.getPhoneNumber() != null) {
                existingEmployee.setPhoneNumber(employeeDetails.getPhoneNumber());
            }

            // Cập nhật department nếu có giá trị mới
            if (employeeDetails.getDepartment() != null) {
                existingEmployee.setDepartment(employeeDetails.getDepartment());
            }

            // Cập nhật gender nếu có giá trị mới
            if (employeeDetails.isGender() != existingEmployee.isGender()) {
                existingEmployee.setGender(employeeDetails.isGender());
            }

            // Cập nhật avatar nếu có giá trị mới
            if (employeeDetails.getAvatar() != null) {
                existingEmployee.setAvatar(employeeDetails.getAvatar());
            }

            // Cập nhật updateAt và updateBy luôn luôn vì đó là thông tin cần thiết khi cập
            // nhật
            existingEmployee.setUpdateAt(java.time.LocalDateTime.now()); // Cập nhật thời gian hiện tại
            existingEmployee.setUpdateBy(employeeDetails.getUpdateBy()); // Cập nhật người thực hiện

            // Lưu và trả về đối tượng nhân viên đã cập nhật
            return employeeRepository.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    // Lấy thông tin nhân viên theo id
    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // Lấy danh sách nhân viên với phân trang, tìm kiếm và lọc
    @Override
    public Page<Employee> getEmployees(String fullname, String role, LocalDate createdAfter, LocalDate createdBefore,
            String search, Pageable pageable) {
        Specification<Employee> spec = Specification.where(EmployeeSpecification.fullnameContains(fullname))
                .and(EmployeeSpecification.hasRole(role))
                .and(EmployeeSpecification.createdAfter(createdAfter))
                .and(EmployeeSpecification.createdBefore(createdBefore))
                .and(EmployeeSpecification.searchKeyword(search));

        return employeeRepository.findAll(spec, pageable);
    }
}
