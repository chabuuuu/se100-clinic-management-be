package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iExamRecordService;
import com.se100.clinic_management.Interface.iPrescriptionService;
import com.se100.clinic_management.Interface.iServiceRecordService;
import com.se100.clinic_management.Interface.iTestRecordService;
import com.se100.clinic_management.dto.PatientDto;
import com.se100.clinic_management.dto.ServiceRecordDetailDto;
import com.se100.clinic_management.dto.ServiceRecordDto;
import com.se100.clinic_management.dto.employee.EmployeeProfileDTO;
import com.se100.clinic_management.dto.exam_record.ExamRecordDetailRes;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDetailDto;
import com.se100.clinic_management.dto.service_record.CreateServiceRecordReq;
import com.se100.clinic_management.dto.test_record.TestRecordDto;
import com.se100.clinic_management.model.*;
import com.se100.clinic_management.repository.ServiceRecordRepository;
import com.se100.clinic_management.specification.ServiceRecordSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServiceRecordServiceImpl implements iServiceRecordService {
    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    @Autowired iPrescriptionService prescriptionService;

    @Autowired
    iTestRecordService testRecordService;

    @Autowired
    iExamRecordService examRecordService;

    private ServiceRecordDetailDto convertToServiceRecordDetailDto(ServiceRecord serviceRecord) {
        ModelMapper modelMapper = new ModelMapper();
        ServiceRecordDetailDto serviceRecordDetailDto = new ServiceRecordDetailDto();
        List<PrescriptionDetailDto> prescriptionDetailDtos = new ArrayList<>();
        List<ExamRecordDetailRes> examRecordDetailRes = new ArrayList<>();
        List<TestRecordDto> testRecordDtos = new ArrayList<>();

        serviceRecordDetailDto.setId(serviceRecord.getId());
        serviceRecordDetailDto.setDescription(serviceRecord.getDescription());
        if (serviceRecord.getPatient() != null) {
            serviceRecordDetailDto.setPatient(modelMapper.map(serviceRecord.getPatient(), PatientDto.class));
        }
        if (serviceRecord.getReceptionist() != null) {
            serviceRecordDetailDto.setReceptionist(modelMapper.map(serviceRecord.getReceptionist(), EmployeeProfileDTO.class));
        }

        List<TestRecord> testRecords = serviceRecord.getTestRecords();
        for (TestRecord testRecord : testRecords) {
            testRecordDtos.add(testRecordService.convertToDto(testRecord));
        }

        List<Prescription> prescriptions = serviceRecord.getPrescriptions();

        for (Prescription prescription : prescriptions) {
            prescriptionDetailDtos.add(prescriptionService.convertPrescriptionToDto(prescription));
        }

        List<ExamRecord> examRecords = serviceRecord.getExamRecords();
        for (ExamRecord examRecord : examRecords) {
            examRecordDetailRes.add(examRecordService.convertToExamRecordDetailRes(examRecord));
        }

        //Set prescriptions
        serviceRecordDetailDto.setPrescriptions(prescriptionDetailDtos);

        //Set exam records
        serviceRecordDetailDto.setExamRecords(examRecordDetailRes);

        //Set test records
        serviceRecordDetailDto.setTestRecords(testRecordDtos);

        return serviceRecordDetailDto;
    }

    private ServiceRecordDto convertToServiceRecordDto(ServiceRecord serviceRecord) {
        ModelMapper modelMapper = new ModelMapper();
        ServiceRecordDto serviceRecordDto = new ServiceRecordDto();
        serviceRecordDto.setId(serviceRecord.getId());
        serviceRecordDto.setDescription(serviceRecord.getDescription());
        if (serviceRecord.getPatient() != null) {
            serviceRecordDto.setPatient(modelMapper.map(serviceRecord.getPatient(), PatientDto.class));
        }
        if (serviceRecord.getReceptionist() != null) {
            serviceRecordDto.setReceptionist(modelMapper.map(serviceRecord.getReceptionist(), EmployeeProfileDTO.class));
        }
        return serviceRecordDto;
    }

    @Override
    public Page<ServiceRecordDto> getServiceRecords(Integer serviceRecordId, String patientName, String receptionistName, Date fromDate, Date toDate, Pageable pageable) {
        Specification<ServiceRecord> spec = ServiceRecordSpecification.filter(serviceRecordId, patientName, receptionistName, fromDate, toDate);


        Page<ServiceRecord> result = serviceRecordRepository.findAll(spec, pageable);



        return result.map(this::convertToServiceRecordDto);
    }

    @Override
    public ServiceRecordDetailDto getServiceRecordDetail(int serviceRecordId) {
        ServiceRecord result = serviceRecordRepository.findById(serviceRecordId).orElse(null);

        //Remove sensitive information like password of receptionist
        if (result != null) {
            result.getReceptionist().setPassword(null);
        }
        //Caculate total
        Float total = 0f;
        Float totalPrescription = 0f;
        Float totalTest = 0f;
        Float totalExam = 0f;

        //Caculate total of prescriptions
        for (Prescription prescription : result.getPrescriptions()) {
            if (prescription.getTotal() != null) {
                total += prescription.getTotal();
                totalPrescription += prescription.getTotal();
            }
        }

        //Caculate total of test records
        for (TestRecord testRecord : result.getTestRecords()) {
            ServiceType serviceType = testRecord.getServiceType();
            total += serviceType.getPrice().floatValue();
            totalTest += serviceType.getPrice().floatValue();
        }

        //Caculate total of exam records
        for (ExamRecord examRecord : result.getExamRecords()) {
            ServiceType serviceType = examRecord.getServiceType();
            total += serviceType.getPrice().floatValue();
            totalExam += serviceType.getPrice().floatValue();
        }

        ServiceRecordDetailDto serviceRecordDetailDto = convertToServiceRecordDetailDto(result);

        //Set total
        if (serviceRecordDetailDto == null) {
            serviceRecordDetailDto = new ServiceRecordDetailDto();
        }
        serviceRecordDetailDto.setTotal(total);
        serviceRecordDetailDto.setExamTotal(totalPrescription);
        serviceRecordDetailDto.setTestTotal(totalTest);
        serviceRecordDetailDto.setExamTotal(totalExam);

        return serviceRecordDetailDto;
    }

    @Override
    public ServiceRecordDto createServiceRecord(CreateServiceRecordReq serviceRecord) {
        ServiceRecord newServiceRecord = new ServiceRecord();
        newServiceRecord.setDescription(serviceRecord.getDescription());
        newServiceRecord.setPatientId(serviceRecord.getPatientId());
        newServiceRecord.setReceptionistId(serviceRecord.getReceptionistId());
        newServiceRecord.setCreateAt(serviceRecord.getCreateAt());

        ServiceRecord savedServiceRecord = serviceRecordRepository.save(newServiceRecord);

        return convertToServiceRecordDto(savedServiceRecord);
    }

    @Override
    public void updateServiceRecord(int serviceRecordId, CreateServiceRecordReq serviceRecord) {
        ServiceRecord serviceRecordToUpdate = serviceRecordRepository.findById(serviceRecordId).orElse(null);

        if (serviceRecordToUpdate != null) {
            serviceRecordToUpdate.setDescription(serviceRecord.getDescription());
            serviceRecordToUpdate.setPatientId(serviceRecord.getPatientId());
            serviceRecordToUpdate.setReceptionistId(serviceRecord.getReceptionistId());
            serviceRecordToUpdate.setCreateAt(serviceRecord.getCreateAt());

            serviceRecordRepository.save(serviceRecordToUpdate);
        }
    }

    @Override
    public void deleteServiceRecord(int serviceRecordId) {
        ServiceRecord serviceRecord = serviceRecordRepository.findById(serviceRecordId).orElse(null);
        if (serviceRecord != null) {
            serviceRecord.setDeleteAt(LocalDateTime.now());

            serviceRecordRepository.save(serviceRecord);
        }
    }

}
