package com.se100.clinic_management.controller;

import com.se100.clinic_management.Interface.iPatientService;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.model.Patient;
import com.se100.clinic_management.utils.ResponseEntityGenerator;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

  @Autowired
  private final iPatientService patientService;

  @PostMapping("/add")
  public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
    return new ResponseEntity<>(patientService.savePatient(patient), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Patient> getPatient(@PathVariable int id) {
    return ResponseEntity.ok(patientService.getPatient(id));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ResponseVO> deletePatient(@PathVariable int id) {
    patientService.deletePatient(id);
    return ResponseEntityGenerator.deleteFormat("Patient deleted successfully with ID: " + id);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<String> updatePatient(@PathVariable int id, @RequestBody Patient patient) {
    Patient updatedPatient = patientService.updatePatient(id, patient);
    return ResponseEntity.ok("Employee updated successfully with ID: " + updatedPatient.getId());
  }

  @GetMapping
  public Page<Patient> getPatients(
      @RequestParam(required = false) String fullname,
      @RequestParam(required = false) Boolean gender,
      @RequestParam(required = false) String phoneNumber,
      @RequestParam(required = false) String createdAfter,
      @RequestParam(required = false) String createdBefore,
      @RequestParam(required = false) Integer minAge,
      @RequestParam(required = false) Integer maxAge,
      @RequestParam(required = false) String search,
      @PageableDefault(size = Integer.MAX_VALUE, page = 0) Pageable pageable) {

    LocalDate createdAfterDate = createdAfter != null ? LocalDate.parse(createdAfter) : null;
    LocalDate createdBeforeDate = createdBefore != null ? LocalDate.parse(createdBefore) : null;

    return patientService.getPatients(fullname, gender, phoneNumber, createdAfterDate, createdBeforeDate, minAge,
        maxAge, search, pageable);
  }

}
