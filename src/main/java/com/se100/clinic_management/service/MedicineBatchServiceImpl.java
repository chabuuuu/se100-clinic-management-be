package com.se100.clinic_management.service;

import com.se100.clinic_management.model.MedicineBatch;
import com.se100.clinic_management.repository.MedicineBatchRepository;
import com.se100.clinic_management.specification.MedicineBatchSpecification;
import com.se100.clinic_management.Interface.iMedicineBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class MedicineBatchServiceImpl implements iMedicineBatchService {

  @Autowired
  private MedicineBatchRepository medicineBatchRepository;

  @Override
  public MedicineBatch getMedicineBatchById(int id) {
    Optional<MedicineBatch> optionalBatch = medicineBatchRepository.findById(id);
    return optionalBatch.orElseThrow(() -> new RuntimeException("Medicine batch not found"));
  }

  @Override
  public MedicineBatch createMedicineBatch(MedicineBatch medicineBatch) {
    medicineBatch.setCreateAt(LocalDateTime.now());
    return medicineBatchRepository.save(medicineBatch);
  }

  @Override
  public MedicineBatch updateMedicineBatch(int id, MedicineBatch medicineBatch) {
    MedicineBatch existingBatch = getMedicineBatchById(id);

    // Cập nhật amount nếu có giá trị mới
    existingBatch.setAmount(medicineBatch.getAmount());

    // Cập nhật price nếu có giá trị mới
    if (medicineBatch.getPrice() != null) {
      existingBatch.setPrice(medicineBatch.getPrice());
    }

    // Cập nhật quantity nếu có giá trị mới
    existingBatch.setQuantity(medicineBatch.getQuantity());

    // Cập nhật expireDate nếu có giá trị mới
    if (medicineBatch.getExpireDate() != null) {
      existingBatch.setExpireDate(medicineBatch.getExpireDate());
    }

    // Cập nhật medicineId nếu có giá trị mới
    if (medicineBatch.getMedicineId() != null) {
      existingBatch.setMedicineId(medicineBatch.getMedicineId());
    }

    // Cập nhật manufacturer nếu có giá trị mới
    if (medicineBatch.getManufacturer() != null) {
      existingBatch.setManufacturer(medicineBatch.getManufacturer());
    }

    // Cập nhật createAt nếu có giá trị mới (nếu cần, nếu không muốn thay đổi thì bỏ
    // qua)
    if (medicineBatch.getCreateAt() != null) {
      existingBatch.setCreateAt(medicineBatch.getCreateAt());
    }

    // Cập nhật updateAt luôn luôn vì đó là thông tin cần thiết khi cập nhật
    existingBatch.setUpdateAt(LocalDateTime.now()); // Cập nhật thời gian hiện tại

    // Cập nhật createBy nếu có giá trị mới
    if (medicineBatch.getCreateBy() != null) {
      existingBatch.setCreateBy(medicineBatch.getCreateBy());
    }

    // Cập nhật updateBy luôn luôn vì đó là thông tin cần thiết khi cập nhật
    existingBatch.setUpdateBy(medicineBatch.getUpdateBy());

    // Cập nhật deleteAt nếu có giá trị mới
    if (medicineBatch.getDeleteAt() != null) {
      existingBatch.setDeleteAt(medicineBatch.getDeleteAt());
    }

    // Lưu và trả về đối tượng MedicineBatch đã cập nhật
    return medicineBatchRepository.save(existingBatch);
  }

  @Override
  public void deleteMedicineBatch(int id) {
    MedicineBatch existingBatch = getMedicineBatchById(id);
    existingBatch.setDeleteAt(LocalDateTime.now());
    medicineBatchRepository.save(existingBatch);
  }

  // filter and pagination
  @Override
  public Page<MedicineBatch> getMedicineBatches(Integer medicineId,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Boolean isExpired,
      LocalDateTime startDate,
      LocalDateTime endDate,
      Boolean isActive,
      Integer minQuantity,
      Pageable pageable) {
    Specification<MedicineBatch> spec = MedicineBatchSpecification.filter(medicineId, minPrice,
        maxPrice, isExpired, endDate, endDate, isActive, minQuantity);
    return medicineBatchRepository.findAll(spec, pageable);
  }
}
