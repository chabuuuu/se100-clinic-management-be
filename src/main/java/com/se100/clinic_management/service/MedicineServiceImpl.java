package com.se100.clinic_management.service;

import com.se100.clinic_management.model.Medicine;
import com.se100.clinic_management.repository.MedicineRepository;
import com.se100.clinic_management.specification.MedicineSpecification;
import com.se100.clinic_management.Interface.iMedicineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class MedicineServiceImpl implements iMedicineService {

  @Autowired
  private MedicineRepository medicineRepository;

  @Override
  public Medicine createMedicine(Medicine medicine) {
    // find if medicine already exists
    Medicine existingMedicine = medicineRepository.findByName(medicine.getName());
    if (existingMedicine != null) {
      throw new RuntimeException("Medicine already exists with name: " + medicine.getName());
    }

    medicine.setCreateAt(LocalDateTime.now());
    return medicineRepository.save(medicine);
  }

  @Override
  public Medicine getMedicineById(int id) {
    Optional<Medicine> optionalMedicine = medicineRepository.findById(id);
    return optionalMedicine.orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
  }

  @Override
  public Medicine updateMedicine(int id, Medicine medicine) {
    Medicine existingMedicine = getMedicineById(id);
    existingMedicine.setName(medicine.getName());
    existingMedicine.setIngredient(medicine.getIngredient());
    existingMedicine.setDosageForm(medicine.getDosageForm());
    existingMedicine.setPrice(medicine.getPrice());
    existingMedicine.setUpdateAt(LocalDateTime.now());
    existingMedicine.setUpdateBy(medicine.getUpdateBy());
    return medicineRepository.save(existingMedicine);
  }

  @Override
  public void deleteMedicine(int id) {
    Medicine existingMedicine = getMedicineById(id);
    existingMedicine.setDeleteAt(LocalDateTime.now());
    medicineRepository.save(existingMedicine);
  }

  // Lấy danh sách thuốc với phân trang, lọc
  @Override
  public Page<Medicine> getMedicines(String name, Pageable pageable) {
    Specification<Medicine> spec = MedicineSpecification.hasName(name);
    return medicineRepository.findAll(spec, pageable);
  }
}
