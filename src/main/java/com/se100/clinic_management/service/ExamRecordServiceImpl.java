package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iExamRecordService;
import com.se100.clinic_management.dto.JwtTokenVo;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateReq;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordDetailRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordUpdateReq;
import com.se100.clinic_management.exception.BaseError;
import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.model.MedicineBatch;
import com.se100.clinic_management.model.ServiceRecord;
import com.se100.clinic_management.repository.ExamRecordRepository;
import com.se100.clinic_management.repository.ServiceRecordRepository;
import com.se100.clinic_management.specification.ExamRecordSpecification;
import com.se100.clinic_management.specification.MedicineBatchSpecification;
import com.se100.clinic_management.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExamRecordServiceImpl implements iExamRecordService {

    @Autowired
    private final ExamRecordRepository examRecordRepository;

    @Autowired
    private final ServiceRecordRepository serviceRecordRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ExamRecordCreateRes createExamRecord(ExamRecordCreateReq createExamRecordReq) {
        //Get currently logged receptionist
        JwtTokenVo jwtTokenVo = SecurityUtil.getSession();

        int receptionistId = jwtTokenVo.getUserId();


        ExamRecord examRecord = new ExamRecord();

        examRecord.setExamRoom(createExamRecordReq.getExamRoom());
        examRecord.setExamDate(createExamRecordReq.getExamDate());
        examRecord.setDoctorId(createExamRecordReq.getDoctorId());
        examRecord.setPatientId(createExamRecordReq.getPatientId());
        examRecord.setNumericalOrder(createExamRecordReq.getNumericalOrder());
        examRecord.setServiceTypeId(createExamRecordReq.getServiceTypeId());

        //Create new service_record
        ServiceRecord serviceRecord = new ServiceRecord();
        serviceRecord.setPatientId(createExamRecordReq.getPatientId());
        serviceRecord.setReceptionistId(receptionistId);

        //Save service record
        ServiceRecord savedServiceRecord = serviceRecordRepository.save(serviceRecord);

        //Set service record id to exam record
        examRecord.setServiceRecordId(savedServiceRecord.getId());

        //Set status
        examRecord.setStatus("WAITING_FOR_EXAM");

        //Set create by
        examRecord.setCreatedBy(receptionistId);

        ExamRecord createdExamRecord = examRecordRepository.save(examRecord);

        // Compare exam record id with latest exam record id in redis, get the one with
        // the highest id
        redisTemplate.opsForValue().set("latest_exam_record_id", createdExamRecord.getId());

        ExamRecordCreateRes examRecordCreateRes = new ExamRecordCreateRes();
        examRecordCreateRes.setId(createdExamRecord.getId());
        examRecordCreateRes.setExamDate(createdExamRecord.getExamDate());
        examRecordCreateRes.setNumericalOrder(createdExamRecord.getNumericalOrder());
        examRecordCreateRes.setExamRoom(createdExamRecord.getExamRoom());
        examRecordCreateRes.setDiagnose(createdExamRecord.getDiagnose());
        examRecordCreateRes.setSymptom(createdExamRecord.getSymptom());
        examRecordCreateRes.setStatus(createdExamRecord.getStatus());
        examRecordCreateRes.setPatientId(createdExamRecord.getPatientId());
        examRecordCreateRes.setDoctorId(createdExamRecord.getDoctorId());
        examRecordCreateRes.setCreateAt(createdExamRecord.getCreateAt());
        examRecordCreateRes.setUpdateAt(createdExamRecord.getUpdateAt());
        examRecordCreateRes.setCreatedBy(createdExamRecord.getCreatedBy());
        examRecordCreateRes.setUpdatedBy(createdExamRecord.getUpdatedBy());
        examRecordCreateRes.setServiceRecordId(createdExamRecord.getServiceRecordId());
        examRecordCreateRes.setServiceTypeId(createdExamRecord.getServiceTypeId());


        return examRecordCreateRes;
    }

    @Override
    public int getExamRecordId() {
        Integer latestExamRecordId = (Integer) redisTemplate.opsForValue().get("latest_exam_record_id");

        if (latestExamRecordId != null) {
            redisTemplate.opsForValue().set("latest_exam_record_id", latestExamRecordId + 1);

            return latestExamRecordId + 1;
        }

        return 1;
    }

    @Override
    public ExamRecord getExamRecordDetail(int examRecordId) {
        ExamRecord examRecord = examRecordRepository.findById(examRecordId).orElse(null);
        if (examRecord == null) {
            return null;
        }

        //Delete sensitive information
        examRecord.getDoctor().setPassword(null);

        return examRecord;
    }

    @Override
    public void updateExamRecord(ExamRecordUpdateReq examRecord, int examRecordId) {
        ExamRecord existingExamRecord = examRecordRepository.findById(examRecordId).orElse(null);
        if (existingExamRecord == null) {
            throw new RuntimeException("Exam record not found");
        }

        //Get currently logged receptionist
        JwtTokenVo jwtTokenVo = SecurityUtil.getSession();

        int loggedInUserId = jwtTokenVo.getUserId();

        existingExamRecord.setExamRoom(examRecord.getExamRoom());
        existingExamRecord.setExamDate(examRecord.getExamDate());
        existingExamRecord.setDoctorId(examRecord.getDoctorId());
        existingExamRecord.setPatientId(examRecord.getPatientId());
        existingExamRecord.setNumericalOrder(examRecord.getNumericalOrder());
        existingExamRecord.setServiceTypeId(examRecord.getServiceTypeId());
        existingExamRecord.setDiagnose(examRecord.getDiagnose());
        existingExamRecord.setSymptom(examRecord.getSymptom());
        existingExamRecord.setStatus(examRecord.getStatus());
        existingExamRecord.setUpdatedBy(loggedInUserId);

        examRecordRepository.save(existingExamRecord);
    }

    @Override
    public Page<ExamRecord> getExamRecords(Integer examRecordId, String patientName, String examRoom, Date fromDate, Date toDate, String doctorName, String status, Pageable pageable) {
        Specification<ExamRecord> spec = ExamRecordSpecification.filter(examRecordId, patientName, examRoom, doctorName, status, fromDate, toDate);
        return examRecordRepository.findAll(spec, pageable);
    }

    @SneakyThrows
    @Override
    public void deleteExamRecord(int examRecordId) {
        //Set delete at to current time
        ExamRecord existingExamRecord = examRecordRepository.findById(examRecordId).orElse(null);
        if (existingExamRecord == null) {
            throw new BaseError("EXAM_RECORD_NOT_FOUND", "Exam record not found", HttpStatus.NOT_FOUND);
        }

        LocalDateTime deleteAt = LocalDateTime.now();
        existingExamRecord.setDeleteAt(deleteAt);

        examRecordRepository.save(existingExamRecord);
    }
}
