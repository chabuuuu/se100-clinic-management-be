package com.se100.clinic_management.Interface;

import com.se100.clinic_management.model.ServiceType;
import com.se100.clinic_management.model.ServiceTypeEnum;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface iServiceTypeService {

  ServiceType getServiceTypeById(int id);

  ServiceType createServiceType(ServiceType serviceType);

  ServiceType updateServiceType(int id, ServiceType serviceType);

  void deleteServiceType(int id);

  Page<ServiceType> getServiceTypes(
      String serviceName,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      ServiceTypeEnum type,
      Pageable pageable);
}
