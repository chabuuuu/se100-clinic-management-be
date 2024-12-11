package com.se100.clinic_management.controller;

import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.model.MedicineBatch;
import com.se100.clinic_management.service.MedicineBatchServiceImpl;
import com.se100.clinic_management.utils.ResponseEntityGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicine-batches")
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
}
