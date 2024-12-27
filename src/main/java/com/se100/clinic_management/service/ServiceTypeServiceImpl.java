package com.se100.clinic_management.service;

import com.se100.clinic_management.model.ServiceType;
import com.se100.clinic_management.model.ServiceTypeEnum;
import com.se100.clinic_management.repository.ServiceTypeRepository;
import com.se100.clinic_management.specification.ServiceTypeSpecification;
import com.se100.clinic_management.Interface.iServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ServiceTypeServiceImpl implements iServiceTypeService {

  @Autowired
  private ServiceTypeRepository serviceTypeRepository;

  @Override
  public ServiceType getServiceTypeById(int id) {
    Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(id);

    ServiceType serviceType = optionalServiceType.orElseThrow(() -> new RuntimeException("Service type not found"));

    if (serviceType.getDeleteAt() != null) {
      throw new RuntimeException("Service type has been deleted");
    }

    return serviceType;
  }

  @Override
  public ServiceType createServiceType(ServiceType serviceType) {
    serviceType.setCreateAt(LocalDateTime.now());
    serviceType.setUpdateAt(LocalDateTime.now());
    // Kiểm tra xem type có hợp lệ không
    if (serviceType.getType() == null || !isValidServiceType(serviceType.getType())) {
      throw new IllegalArgumentException("Invalid service type: " + serviceType.getType());
    }
    return serviceTypeRepository.save(serviceType);
  }

  // Phương thức kiểm tra xem type có hợp lệ không
  private boolean isValidServiceType(ServiceTypeEnum type) {
    // Kiểm tra nếu type nằm trong danh sách các giá trị hợp lệ
    return type == ServiceTypeEnum.MEDICAL_CONSULTATION_SERVICE ||
        type == ServiceTypeEnum.PHARMACY_SERVICE ||
        type == ServiceTypeEnum.LABORATORY_TESTING_SERVICE;
  }

  @Override
  public ServiceType updateServiceType(int id, ServiceType serviceType) {
    ServiceType existingServiceType = getServiceTypeById(id);
    existingServiceType.setServiceName(serviceType.getServiceName());
    existingServiceType.setPrice(serviceType.getPrice());
    existingServiceType.setCreateAt(serviceType.getCreateAt());
    existingServiceType.setUpdateAt(LocalDateTime.now());
    existingServiceType.setCreateBy(serviceType.getCreateBy());
    existingServiceType.setUpdateBy(serviceType.getUpdateBy());
    existingServiceType.setDeleteAt(serviceType.getDeleteAt());
    existingServiceType.setType(serviceType.getType());
    return serviceTypeRepository.save(existingServiceType);
  }

  @Override
  public void deleteServiceType(int id) {
    ServiceType existingServiceType = getServiceTypeById(id);
    existingServiceType.setDeleteAt(LocalDateTime.now());
    serviceTypeRepository.save(existingServiceType);
  }

  @Override
  public Page<ServiceType> getServiceTypes(String serviceName, BigDecimal minPrice, BigDecimal maxPrice,
      ServiceTypeEnum type,
      Pageable pageable) {
    Specification<ServiceType> spec = ServiceTypeSpecification.filter(serviceName, minPrice, maxPrice, type);
    return serviceTypeRepository.findAll(spec, pageable);
  }
}
