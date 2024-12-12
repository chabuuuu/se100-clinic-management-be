package com.se100.clinic_management.controller;

import com.se100.clinic_management.model.ServiceType;
import com.se100.clinic_management.model.ServiceTypeEnum;
import com.se100.clinic_management.utils.ResponseEntityGenerator;
import com.se100.clinic_management.Interface.iServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/service-types")
public class ServiceTypeController {

  @Autowired
  private iServiceTypeService serviceTypeService;

  // Get service type by ID
  @GetMapping("/{id}")
  public ResponseEntity<ResponseVO> getServiceTypeById(@PathVariable int id) {
    ServiceType serviceType = serviceTypeService.getServiceTypeById(id);
    return ResponseEntityGenerator.findOneFormat(serviceType);
  }

  // Create new service type
  @PostMapping
  public ResponseEntity<ResponseVO> createServiceType(@RequestBody ServiceType serviceType) {
    ServiceType createdServiceType = serviceTypeService.createServiceType(serviceType);
    return ResponseEntityGenerator.createdFormat(createdServiceType);
  }

  // Update an existing service type
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> updateServiceType(@PathVariable int id, @RequestBody ServiceType serviceType) {
    ServiceType updatedServiceType = serviceTypeService.updateServiceType(id, serviceType);
    return ResponseEntityGenerator.updateFormat(updatedServiceType);
  }

  // Delete a service type
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseVO> deleteServiceType(@PathVariable int id) {
    serviceTypeService.deleteServiceType(id);
    return ResponseEntityGenerator.deleteFormat(serviceTypeService.getServiceTypeById(id));
  }

  // Get service types with filters
  @GetMapping
  public ResponseEntity<Page<ServiceType>> getServiceTypes(
      @RequestParam(required = false) String serviceName,
      @RequestParam(required = false) BigDecimal minPrice,
      @RequestParam(required = false) BigDecimal maxPrice,
      @RequestParam(required = false) ServiceTypeEnum type,
      Pageable pageable) {
    Page<ServiceType> serviceTypes = serviceTypeService.getServiceTypes(serviceName, minPrice, maxPrice, type,
        pageable);
    return new ResponseEntity<>(serviceTypes, HttpStatus.OK);
  }
}
