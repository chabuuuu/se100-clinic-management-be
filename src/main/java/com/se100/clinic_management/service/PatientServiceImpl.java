package com.se100.clinic_management.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.se100.clinic_management.Interface.iPatientService;
import com.se100.clinic_management.model.Patient;
import com.se100.clinic_management.repository.PatientRepository;
import com.se100.clinic_management.specification.PatientSpecification;

@Service
public class PatientServiceImpl implements iPatientService {

  @Autowired
  private PatientRepository patientRepository;

  // Thêm bệnh nhân mới
  @Override
  public Patient savePatient(Patient patient) {
    // Xử lý logic trước khi lưu (nếu cần)
    patient.setCreateAt(java.time.LocalDateTime.now());
    return patientRepository.save(patient);
  }

  // Xóa bệnh nhân theo id
  @Override
  public void deletePatient(int id) {
    // Kiểm tra xem bệnh nhân có tồn tại không trước khi xóa (tuỳ chọn)
    if (patientRepository.existsById(id)) {
      patientRepository.deleteById(id);
    }
  }

  // Cập nhật thông tin bệnh nhân
  @Override
  public Patient updatePatient(int id, Patient patientDetails) {
    if (patientRepository.existsById(id)) {
      // Tìm bệnh nhân và cập nhật các trường
      Patient existingPatient = patientRepository.findById(id).orElseThrow();
      existingPatient.setFullname(patientDetails.getFullname());
      existingPatient.setGender(patientDetails.isGender());
      existingPatient.setBirthday(patientDetails.getBirthday());
      existingPatient.setPhoneNumber(patientDetails.getPhoneNumber());
      existingPatient.setUpdateAt(java.time.LocalDateTime.now());
      existingPatient.setUpdateBy(patientDetails.getUpdateBy());
      return patientRepository.save(existingPatient);
    } else {
      throw new RuntimeException("Patient not found");
    }
  }

  // Lấy danh sách bệnh nhân với phân trang, tìm kiếm và lọc
  @Override
  public Page<Patient> getPatients(String fullname, Boolean gender, String phoneNumber,
      LocalDate createdAfter, LocalDate createdBefore, Integer minAge, Integer maxAge, String search,
      Pageable pageable) {

    Specification<Patient> spec = Specification.where(PatientSpecification.fullnameContains(fullname))
        .and(PatientSpecification.hasGender(gender))
        .and(PatientSpecification.hasPhoneNumber(phoneNumber))
        .and(PatientSpecification.createdAfter(createdAfter))
        .and(PatientSpecification.createdBefore(createdBefore))
        .and(PatientSpecification.hasMinAge(minAge))
        .and(PatientSpecification.hasMaxAge(maxAge))
        .and(PatientSpecification.searchKeyword(search));

    return patientRepository.findAll(spec, pageable);
  }

}
