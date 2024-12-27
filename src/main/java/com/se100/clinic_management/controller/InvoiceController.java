package com.se100.clinic_management.controller;

import com.se100.clinic_management.Interface.iInvoiceService;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.invoice.CreateInvoiceReq;
import com.se100.clinic_management.dto.invoice.InvoiceDetailDto;
import com.se100.clinic_management.dto.invoice.InvoiceDto;
import com.se100.clinic_management.utils.ResponseEntityGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final iInvoiceService invoiceService;

    @GetMapping("{invoiceId}")
    public ResponseEntity<ResponseVO> getInvoiceDetail(
            @PathVariable  int invoiceId
            ) {
        InvoiceDetailDto result = invoiceService.getInvoiceDetail(invoiceId);

        return ResponseEntityGenerator.findOneFormat(result);
    }

    @GetMapping("")
    public ResponseEntity<Page<InvoiceDto>> getInvoices(
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String receptionistName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            Pageable pageable
            ) {
        Page<InvoiceDto> result = invoiceService.getInvoices(patientName, receptionistName, startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseVO> createInvoice(
            @RequestBody CreateInvoiceReq createInvoiceReq
            ) {
        invoiceService.createInvoice(createInvoiceReq);
        return ResponseEntityGenerator.createdFormat("Create invoice successfully");
    }
}
