package com.se100.clinic_management.controller;

import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.model.MedicineBatch;
import com.se100.clinic_management.service.MedicineBatchServiceImpl;
import com.se100.clinic_management.utils.ResponseEntityGenerator;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

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
    return ResponseEntityGenerator.deleteFormat(medicineBatchService.getMedicineBatchById(id));
  }

  @GetMapping
  public Page<MedicineBatch> getMedicineBatches(@RequestParam(required = false) Integer medicineId,
      @RequestParam(required = false) BigDecimal minPrice,
      @RequestParam(required = false) BigDecimal maxPrice,
      @RequestParam(required = false) Boolean isExpired,
      @RequestParam(required = false) Date startDate,
      @RequestParam(required = false) Date endDate,
      @RequestParam(required = false) Boolean isActive,
      @RequestParam(required = false) Integer minQuantity,
      Pageable pageable) {
    return medicineBatchService.getMedicineBatches(medicineId, minPrice,
        maxPrice, isExpired, endDate, endDate, isActive, minQuantity, pageable);
  }
}