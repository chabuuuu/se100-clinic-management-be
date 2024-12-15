package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iServiceRecordService;
import com.se100.clinic_management.model.ServiceRecord;
import com.se100.clinic_management.repository.ServiceRecordRepository;
import com.se100.clinic_management.specification.ServiceRecordSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ServiceRecordServiceImpl implements iServiceRecordService {
    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    @Override
    public Page<ServiceRecord> getServiceRecords(Integer serviceRecordId, String patientName, String receptionistName, Date fromDate, Date toDate, Pageable pageable) {
        Specification<ServiceRecord> spec = ServiceRecordSpecification.filter(serviceRecordId, patientName, receptionistName, fromDate, toDate);


        Page<ServiceRecord> result = serviceRecordRepository.findAll(spec, pageable);

        //Remove sensitive information like password of receptionist
        result.forEach(serviceRecord -> {
            serviceRecord.getReceptionist().setPassword(null);
        });

        return result;
    }

    @Override
    public ServiceRecord getServiceRecordDetail(int serviceRecordId) {
        ServiceRecord result = serviceRecordRepository.findById(serviceRecordId).orElse(null);

        //Remove sensitive information like password of receptionist
        if (result != null) {
            result.getReceptionist().setPassword(null);
        }

        return result;
    }

}
