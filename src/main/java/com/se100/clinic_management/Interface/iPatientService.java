package com.se100.clinic_management.Interface;

import com.se100.clinic_management.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface iPatientService {
  Patient savePatient(Patient patient);

  void deletePatient(int id);

  Patient updatePatient(int id, Patient patient);

  Patient getPatient(int id);

  Page<Patient> getPatients(String fullname, Boolean gender, String phoneNumber, LocalDate createdAfter,
      LocalDate createdBefore, Integer minAge, Integer maxAge, String search, Pageable pageable);
}
