package com.se100.clinic_management.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "username", nullable = false, unique = true, length = 50)
  private String username;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "fullname", nullable = false, length = 100)
  private String fullname;

  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Column(name = "role", length = 100)
  private String role;

  @Column(name = "shift")
  private String shift;

  @Column(name = "dob")
  @Temporal(TemporalType.DATE)
  private LocalDate dob;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "department")
  private String department;

  @Column(name = "gender")
  private boolean gender;

  @Column(name = "create_at")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createAt;

  @Column(name = "update_at")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updateAt;

  @Column(name = "create_by", length = 70)
  private String createBy;

  @Column(name = "update_by", length = 70)
  private String updateBy;

  @Column(name = "delete_at")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime deleteAt;
}
