package com.se100.clinic_management.service;
import com.se100.clinic_management.Interface.iTestRecordService;
import com.se100.clinic_management.dto.PatientDto;
import com.se100.clinic_management.dto.employee.EmployeeProfileDTO;
import com.se100.clinic_management.dto.test_record.TestRecordDto;
import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.model.Patient;
import com.se100.clinic_management.model.ServiceType;
import com.se100.clinic_management.model.TestRecord;
import com.se100.clinic_management.repository.TestRecordRepository;
import com.se100.clinic_management.specification.ServiceTypeSpecification;
import com.se100.clinic_management.specification.TestRecordSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TestRecordServiceImpl implements iTestRecordService {
    @Autowired
    private TestRecordRepository testRecordRepository;

    @Override
    public TestRecordDto convertToDto(TestRecord testRecord) {
        ModelMapper modelMapper = new ModelMapper();
        TestRecordDto testRecordDto = new TestRecordDto();
        testRecordDto.setId(testRecord.getId());
        testRecordDto.setTestDate(testRecord.getTestDate());
        testRecordDto.setNumericalOrder(testRecord.getNumericalOrder());
        testRecordDto.setTestArtachments(testRecord.getTestArtachments());
        testRecordDto.setDiagnose(testRecord.getDiagnose());
        testRecordDto.setState(testRecord.getState());
        testRecordDto.setServiceType(testRecord.getServiceType());
        testRecordDto.setServiceRecordId(testRecord.getServiceRecordId());
        Patient patient = testRecord.getPatient();
        if (patient != null){
            testRecordDto.setPatient(modelMapper.map(patient, PatientDto.class));
        }
        Employee technician = testRecord.getTechnician();
        if (technician != null){
            testRecordDto.setTechnician(modelMapper.map(technician, EmployeeProfileDTO.class));
        }

        return testRecordDto;
    }

    @Override
    public TestRecordDto createTestRecord(TestRecord testRecordDto) {
        TestRecord testRecord = testRecordRepository.save(testRecordDto);
        ModelMapper modelMapper = new ModelMapper();
        return convertToDto(testRecord);
    }

    @Override
    public void updateTestRecord(int id, TestRecord testRecordDto) {
        TestRecord testRecord = testRecordRepository.findById(id).orElse(null);
        if (testRecord != null){
            testRecord.setTestDate(testRecordDto.getTestDate());
            testRecord.setNumericalOrder(testRecordDto.getNumericalOrder());
            testRecord.setTestArtachments(testRecordDto.getTestArtachments());
            testRecord.setDiagnose(testRecordDto.getDiagnose());
            testRecord.setState(testRecordDto.getState());
            testRecord.setServiceType(testRecordDto.getServiceType());
            testRecord.setServiceRecordId(testRecordDto.getServiceRecordId());
            testRecordRepository.save(testRecord);
        }
    }

    @Override
    public void deleteTestRecord(int id) {
        //Set deletedAt = now
        TestRecord testRecord = testRecordRepository.findById(id).orElse(null);
        if (testRecord != null) {
            testRecord.setDeleteAt(LocalDateTime.now());
            testRecordRepository.save(testRecord);
        }
    }

    @Override
    public TestRecordDto getTestRecordById(int id) {
        TestRecord testRecord = testRecordRepository.findById(id).orElse(null);
        if (testRecord != null) {
            return convertToDto(testRecord);
        }
        return null;
    }

    @Override
    public Page<TestRecordDto> getTestRecords(Integer id, String patientName, String technicianName, String state, Date fromDate, Date toDate, Pageable pageable) {
        Specification<TestRecord> spec = TestRecordSpecification.filter(id, patientName, technicianName, state, fromDate, toDate);

        Page<TestRecord> testRecords = testRecordRepository.findAll(spec, pageable);

        return testRecords.map(testRecord -> convertToDto(testRecord));
    }
}
