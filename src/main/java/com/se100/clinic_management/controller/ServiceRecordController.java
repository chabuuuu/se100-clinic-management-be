package com.se100.clinic_management.controller;

import com.se100.clinic_management.Interface.iServiceRecordService;
import com.se100.clinic_management.dto.ServiceRecordDetailDto;
import com.se100.clinic_management.dto.ServiceRecordDto;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.service_record.CreateServiceRecordReq;
import com.se100.clinic_management.model.ServiceRecord;
import com.se100.clinic_management.utils.ResponseEntityGenerator;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/service-records")
public class ServiceRecordController {
    @Autowired
    private iServiceRecordService serviceRecordService;

    @GetMapping("")
    public Page<ServiceRecordDto> getServiceRecords(
            @RequestParam(required = false) Integer serviceRecordId,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String receptionistName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
            @PageableDefault(size = Integer.MAX_VALUE, page = 0) Pageable pageable) {

        return serviceRecordService.getServiceRecords(serviceRecordId, patientName, receptionistName, fromDate, toDate,
                pageable);
    }

    @GetMapping("detail/{serviceRecordId}")
    public ServiceRecordDetailDto getServiceRecordDetail(
            @PathVariable int serviceRecordId) {
        return serviceRecordService.getServiceRecordDetail(serviceRecordId);
    }

    @PostMapping("")
    public ResponseEntity<ResponseVO> createServiceRecord(
            @RequestBody CreateServiceRecordReq serviceRecord) {
        ServiceRecordDto result = serviceRecordService.createServiceRecord(serviceRecord);

        return ResponseEntityGenerator.createdFormat(result);
    }

    @PutMapping("{serviceRecordId}")
    public ResponseEntity<ResponseVO> updateServiceRecord(
            @PathVariable int serviceRecordId,
            @RequestBody CreateServiceRecordReq serviceRecord) {
        serviceRecordService.updateServiceRecord(serviceRecordId, serviceRecord);

        return ResponseEntityGenerator.okFormat("Update service record successfully");
    }

    @DeleteMapping("{serviceRecordId}")
    public ResponseEntity<ResponseVO> deleteServiceRecord(
            @PathVariable int serviceRecordId) {
        serviceRecordService.deleteServiceRecord(serviceRecordId);

        return ResponseEntityGenerator.okFormat("Delete service record successfully");
    }
}
