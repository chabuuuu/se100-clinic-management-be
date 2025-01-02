package com.se100.clinic_management.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.model.Medicine;
import com.se100.clinic_management.service.MedicineServiceImpl;
import com.se100.clinic_management.utils.ResponseEntityGenerator;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

  @Autowired
  private MedicineServiceImpl medicineService;

  @PostMapping
  public ResponseEntity<ResponseVO> createMedicine(@RequestBody Medicine medicine) {
    return ResponseEntityGenerator.createdFormat(medicineService.createMedicine(medicine));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseVO> getMedicineById(@PathVariable int id) {
    return ResponseEntityGenerator.findOneFormat(medicineService.getMedicineById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> updateMedicine(@PathVariable int id, @RequestBody Medicine medicine) {
    return ResponseEntityGenerator.updateFormat(medicineService.updateMedicine(id, medicine));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseVO> deleteMedicine(@PathVariable int id) {
    medicineService.deleteMedicine(id);
    return ResponseEntityGenerator.deleteFormat(medicineService.getMedicineById(id));
  }

  // find medicine by name
  @GetMapping
  public Page<Medicine> getMedicineByName(@RequestParam(required = false) String name,
      @RequestParam(required = false) String createBy,
      @RequestParam(required = false) LocalDateTime updatedAfter,
      @RequestParam(required = false) String ingredient,
      @RequestParam(required = false) String dosageForm,
      @RequestParam(required = false) BigDecimal minPrice,
      @RequestParam(required = false) BigDecimal maxPrice,
      @PageableDefault(size = Integer.MAX_VALUE, page = 0) Pageable pageable) {

    return medicineService.getMedicines(name, dosageForm, updatedAfter, dosageForm, dosageForm, maxPrice, maxPrice,
        pageable);
  }
}
