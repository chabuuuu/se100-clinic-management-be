package com.se100.clinic_management.controller;

import com.se100.clinic_management.model.Patient;
import com.se100.clinic_management.service.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

  @Autowired
  private PatientServiceImpl patientServiceImpl;

  @PostMapping("/add")
  public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
    return new ResponseEntity<>(patientServiceImpl.savePatient(patient), HttpStatus.CREATED);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deletePatient(@PathVariable int id) {
    patientServiceImpl.deletePatient(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body("Employee deleted successfully with ID: " + id);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<String> updatePatient(@PathVariable int id, @RequestBody Patient patient) {
    Patient updatedPatient = patientServiceImpl.updatePatient(id, patient);
    return ResponseEntity.ok("Employee updated successfully with ID: " + updatedPatient.getId());
  }

  // url example:
  // GET
  // /api/patients?fullname=Pham%20Hoang%20Quan&gender=true&phoneNumber=0123456789&createdAfter=2024-01-01&createdBefore=2024-12-31&minAge=25&maxAge=40&page=0&size=10&sort=fullname,asc
  // Lọc bệnh nhân theo nhiều tham số (tên, giới tính, số điện thoại, ngày tạo, độ
  // tuổi)
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
      Pageable pageable) {

    LocalDate createdAfterDate = createdAfter != null ? LocalDate.parse(createdAfter) : null;
    LocalDate createdBeforeDate = createdBefore != null ? LocalDate.parse(createdBefore) : null;

    return patientServiceImpl.getPatients(fullname, gender, phoneNumber, createdAfterDate, createdBeforeDate, minAge,
        maxAge, search, pageable);
  }

}
