package com.se100.clinic_management.service;

import com.se100.clinic_management.model.MedicineBatch;
import com.se100.clinic_management.repository.MedicineBatchRepository;
import com.se100.clinic_management.specification.MedicineBatchSpecification;
import com.se100.clinic_management.Interface.iMedicineBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
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
    return medicineBatchRepository.save(medicineBatch);
  }

  @Override
  public MedicineBatch updateMedicineBatch(int id, MedicineBatch medicineBatch) {
    MedicineBatch existingBatch = getMedicineBatchById(id);
    existingBatch.setAmount(medicineBatch.getAmount());
    existingBatch.setPrice(medicineBatch.getPrice());
    existingBatch.setQuantity(medicineBatch.getQuantity());
    existingBatch.setExpireDate(medicineBatch.getExpireDate());
    existingBatch.setMedicineId(medicineBatch.getMedicineId());
    existingBatch.setManufacturer(medicineBatch.getManufacturer());
    existingBatch.setCreateAt(medicineBatch.getCreateAt());
    existingBatch.setUpdateAt(medicineBatch.getUpdateAt());
    existingBatch.setCreateBy(medicineBatch.getCreateBy());
    existingBatch.setUpdateBy(medicineBatch.getUpdateBy());
    existingBatch.setDeleteAt(medicineBatch.getDeleteAt());
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
      Date startDate,
      Date endDate,
      Boolean isActive,
      Integer minQuantity,
      Pageable pageable) {
    Specification<MedicineBatch> spec = MedicineBatchSpecification.filter(medicineId, minPrice,
        maxPrice, isExpired, endDate, endDate, isActive, minQuantity);
    return medicineBatchRepository.findAll(spec, pageable);
  }
}
