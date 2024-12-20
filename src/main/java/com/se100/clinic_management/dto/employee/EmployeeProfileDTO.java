package com.se100.clinic_management.dto.employee;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileDTO {
  private int id;
  private String username;
  private String fullname;
  private String email;
  private String role;
  private String shift;
  private LocalDate dob;
  private String phoneNumber;
  private String department;
  private boolean gender;
  private String avatar;
}