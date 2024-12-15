package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iExamRecordService;
import com.se100.clinic_management.dto.JwtTokenVo;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateReq;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordDetailRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordUpdateReq;
import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.model.ServiceRecord;
import com.se100.clinic_management.repository.ExamRecordRepository;
import com.se100.clinic_management.repository.ServiceRecordRepository;
import com.se100.clinic_management.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
}
