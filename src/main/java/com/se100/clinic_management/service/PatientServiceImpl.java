package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iPatientService;
import com.se100.clinic_management.model.Patient;
import com.se100.clinic_management.repository.PatientRepository;
import com.se100.clinic_management.specification.PatientSpecification;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
      Patient patient = patientRepository.findById(id).orElse(null);
      if (patient != null) {
        patient.setDeleteAt(java.time.LocalDateTime.now());
        patientRepository.save(patient);
      }
    }
  }

  // Cập nhật thông tin bệnh nhân
  @Override
  public Patient updatePatient(int id, Patient patientDetails) {
    // Kiểm tra xem bệnh nhân có tồn tại không
    if (patientRepository.existsById(id)) {
      // Tìm bệnh nhân và cập nhật các trường
      Patient existingPatient = patientRepository.findById(id)
          .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

      // Cập nhật fullname nếu có giá trị mới
      if (patientDetails.getFullname() != null) {
        existingPatient.setFullname(patientDetails.getFullname());
      }

      // Cập nhật gender nếu có giá trị mới
      if (patientDetails.isGender() != existingPatient.isGender()) {
        existingPatient.setGender(patientDetails.isGender());
      }

      // Cập nhật birthday nếu có giá trị mới
      if (patientDetails.getBirthday() != null) {
        existingPatient.setBirthday(patientDetails.getBirthday());
      }

      // Cập nhật phoneNumber nếu có giá trị mới
      if (patientDetails.getPhoneNumber() != null) {
        existingPatient.setPhoneNumber(patientDetails.getPhoneNumber());
      }

      // Cập nhật weight nếu có giá trị mới (kiểm tra không null)
      if (patientDetails.getWeight() != null) {
        existingPatient.setWeight(patientDetails.getWeight());
      }

      // Cập nhật bloodGroup nếu có giá trị mới
      if (patientDetails.getBloodGroup() != null) {
        existingPatient.setBloodGroup(patientDetails.getBloodGroup());
      }

      // Cập nhật medicalHistory nếu có giá trị mới
      if (patientDetails.getMedicalHistory() != null) {
        existingPatient.setMedicalHistory(patientDetails.getMedicalHistory());
      }

      // Cập nhật updateAt và updateBy luôn luôn vì đó là thông tin cần thiết khi cập
      // nhật
      existingPatient.setUpdateAt(java.time.LocalDateTime.now()); // Cập nhật thời gian hiện tại
      existingPatient.setUpdateBy(patientDetails.getUpdateBy()); // Cập nhật người thực hiện

      // Lưu và trả về đối tượng bệnh nhân đã cập nhật
      return patientRepository.save(existingPatient);
    } else {
      throw new EntityNotFoundException("Patient not found");
    }
  }

  // Lấy thông tin bệnh nhân theo id
  @Override
  public Patient getPatient(int id) {
    return patientRepository.findById(id).orElse(null);
  }

  // Lấy danh sách bệnh nhân với phân trang, tìm kiếm và lọc
  @Override
  public Page<Patient> getPatients(String fullname, Boolean gender, String phoneNumber,
      LocalDate createdAfter, LocalDate createdBefore, Integer minAge, Integer maxAge, String search,
      Pageable pageable) {

    Specification<Patient> spec = Specification.where(PatientSpecification.isNotDeleted())
        .and(PatientSpecification.fullnameContains(fullname))
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
