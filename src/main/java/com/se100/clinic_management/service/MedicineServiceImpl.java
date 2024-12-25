package com.se100.clinic_management.service;

import com.se100.clinic_management.model.Medicine;
import com.se100.clinic_management.repository.MedicineRepository;
import com.se100.clinic_management.specification.MedicineSpecification;
import com.se100.clinic_management.Interface.iMedicineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    // Cập nhật name nếu có giá trị mới
    if (medicine.getName() != null) {
      existingMedicine.setName(medicine.getName());
    }

    // Cập nhật ingredient nếu có giá trị mới
    if (medicine.getIngredient() != null) {
      existingMedicine.setIngredient(medicine.getIngredient());
    }

    // Cập nhật dosageForm nếu có giá trị mới
    if (medicine.getDosageForm() != null) {
      existingMedicine.setDosageForm(medicine.getDosageForm());
    }

    // Cập nhật price nếu có giá trị mới
    if (medicine.getPrice() != null) {
      existingMedicine.setPrice(medicine.getPrice());
    }

    // Cập nhật updateAt và updateBy luôn luôn vì đó là thông tin cần thiết khi cập
    // nhật
    existingMedicine.setUpdateAt(LocalDateTime.now()); // Cập nhật thời gian hiện tại
    existingMedicine.setUpdateBy(medicine.getUpdateBy()); // Cập nhật người thực hiện

    // Lưu và trả về đối tượng thuốc đã cập nhật
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
  public Page<Medicine> getMedicines(String name,
      String createBy,
      LocalDateTime updatedAfter,
      String ingredient,
      String dosageForm,
      BigDecimal minPrice,
      BigDecimal maxPrice, Pageable pageable) {
    Specification<Medicine> spec = MedicineSpecification.filter(name, dosageForm, updatedAfter, dosageForm, dosageForm,
        maxPrice, maxPrice);
    return medicineRepository.findAll(spec, pageable);
  }
}
