package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iExamRecordService;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateReq;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordDetailRes;
import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.repository.ExamRecordRepository;
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
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ExamRecordCreateRes createExamRecord(ExamRecordCreateReq createExamRecordReq) {
        ExamRecord examRecord = new ExamRecord();

        examRecord.setExamRoom(createExamRecordReq.getExamRoom());
        examRecord.setExamDate(createExamRecordReq.getExamDate());
        examRecord.setDoctorId(createExamRecordReq.getDoctorId());
        examRecord.setPatientId(createExamRecordReq.getPatientId());
        examRecord.setNumericalOrder(createExamRecordReq.getNumericalOrder());

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
    public ExamRecordDetailRes getExamRecordDetail(int examRecordId) {
        ExamRecord examRecord = examRecordRepository.findById(examRecordId).orElse(null);
        if (examRecord == null) {
            return null;
        }

        ExamRecordDetailRes examRecordDetailRes = new ExamRecordDetailRes();
        examRecordDetailRes.setId(examRecord.getId());
        examRecordDetailRes.setExamDate(examRecord.getExamDate());
        examRecordDetailRes.setNumericalOrder(examRecord.getNumericalOrder());
        examRecordDetailRes.setExamRoom(examRecord.getExamRoom());
        examRecordDetailRes.setDiagnose(examRecord.getDiagnose());
        examRecordDetailRes.setSymptom(examRecord.getSymptom());
        examRecordDetailRes.setStatus(examRecord.getStatus());
        examRecordDetailRes.setDoctorId(examRecord.getDoctorId());
        examRecordDetailRes.setPatientId(examRecord.getPatientId());
        examRecordDetailRes.setCreateAt(examRecord.getCreateAt());
        examRecordDetailRes.setUpdateAt(examRecord.getUpdateAt());
        examRecordDetailRes.setCreatedBy(examRecord.getCreatedBy());
        examRecordDetailRes.setUpdatedBy(examRecord.getUpdatedBy());

        return examRecordDetailRes;
    }
}
