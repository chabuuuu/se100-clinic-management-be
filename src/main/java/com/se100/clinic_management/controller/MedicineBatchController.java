package com.se100.clinic_management.controller;

import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.model.MedicineBatch;
import com.se100.clinic_management.service.MedicineBatchServiceImpl;
import com.se100.clinic_management.utils.ResponseEntityGenerator;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicine-batches")
@RequiredArgsConstructor
public class MedicineBatchController {

  @Autowired
  private MedicineBatchServiceImpl medicineBatchService;

  @GetMapping("/{id}")
  public ResponseEntity<ResponseVO> getMedicineBatchById(@PathVariable int id) {
    MedicineBatch medicineBatch = medicineBatchService.getMedicineBatchById(id);
    // check if deletedAt is null
    if (medicineBatch.getDeleteAt() != null) {
      return ResponseEntityGenerator.deleteFormat("Medicine batch deleted");
    }
    return ResponseEntityGenerator.findOneFormat(medicineBatch);
  }

  @PostMapping
  public ResponseEntity<ResponseVO> createMedicineBatch(@RequestBody MedicineBatch medicineBatch) {
    MedicineBatch createdMedicineBatch = medicineBatchService.createMedicineBatch(medicineBatch);
    return ResponseEntityGenerator.createdFormat(createdMedicineBatch);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> updateMedicineBatch(@PathVariable int id,
      @RequestBody MedicineBatch medicineBatch) {
    MedicineBatch updatedMedicineBatch = medicineBatchService.updateMedicineBatch(id, medicineBatch);
    return ResponseEntityGenerator.updateFormat(updatedMedicineBatch);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseVO> deleteMedicineBatch(@PathVariable int id) {
    medicineBatchService.deleteMedicineBatch(id);
    return ResponseEntityGenerator.deleteFormat("Medicine batch deleted successfully");
  }

  @GetMapping
  public Page<MedicineBatch> getMedicineBatches(
      @RequestParam(required = false) Integer medicineId,
      @RequestParam(required = false) Boolean isExpired,
      @RequestParam(required = false) LocalDateTime startDate,
      @RequestParam(required = false) LocalDateTime endDate,
      @RequestParam(required = false) Boolean isActive,
      Pageable pageable) {
    return medicineBatchService.getMedicineBatches(medicineId, isExpired, startDate, endDate, isActive, pageable);
  }
}
