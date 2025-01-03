package com.se100.clinic_management.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.se100.clinic_management.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>, JpaSpecificationExecutor<Patient> {
  int countByCreateAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
