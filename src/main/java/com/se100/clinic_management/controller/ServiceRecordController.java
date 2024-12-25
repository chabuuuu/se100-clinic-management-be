package com.se100.clinic_management.controller;

import com.se100.clinic_management.Interface.iServiceRecordService;
import com.se100.clinic_management.dto.ServiceRecordDetailDto;
import com.se100.clinic_management.dto.ServiceRecordDto;
import com.se100.clinic_management.model.ServiceRecord;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
            Pageable pageable
    ) {
        return serviceRecordService.getServiceRecords(serviceRecordId, patientName, receptionistName, fromDate, toDate, pageable);
    }

    @GetMapping("detail/{serviceRecordId}")
    public ServiceRecordDetailDto getServiceRecordDetail(
            @PathVariable int serviceRecordId
    ) {
        return serviceRecordService.getServiceRecordDetail(serviceRecordId);
    }
}
