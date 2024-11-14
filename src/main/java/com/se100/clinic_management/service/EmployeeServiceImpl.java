package com.se100.clinic_management.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.se100.clinic_management.specification.EmployeeSpecification;
import com.se100.clinic_management.Interface.iEmployeeService;
import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements iEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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
            employeeRepository.deleteById(id);
        }
    }

    // Cập nhật thông tin nhân viên
    @Override
    public Employee updateEmployee(int id, Employee employeeDetails) {
        if (employeeRepository.existsById(id)) {
            // Tìm nhân viên và cập nhật các trường
            Employee existingEmployee = employeeRepository.findById(id).orElseThrow();
            existingEmployee.setUsername(employeeDetails.getUsername());
            existingEmployee.setPassword(employeeDetails.getPassword());
            existingEmployee.setFullname(employeeDetails.getFullname());
            existingEmployee.setEmail(employeeDetails.getEmail());
            existingEmployee.setRole(employeeDetails.getRole());
            existingEmployee.setUpdateAt(java.time.LocalDateTime.now());
            existingEmployee.setUpdateBy(employeeDetails.getUpdateBy());
            return employeeRepository.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee not found");
        }
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
