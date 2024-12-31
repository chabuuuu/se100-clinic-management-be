package com.se100.clinic_management.Interface;

import com.se100.clinic_management.dto.invoice.CreateInvoiceReq;
import com.se100.clinic_management.dto.invoice.InvoiceDetailDto;
import com.se100.clinic_management.dto.invoice.InvoiceDto;
import com.se100.clinic_management.dto.invoice.UpdateInvoiceStatusReq;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface iInvoiceService {
    void createInvoice(CreateInvoiceReq request);

    Page<InvoiceDto> getInvoices(String patientName, String receptionistName, Date startDate, Date endDate,
            Pageable pageable);

    InvoiceDetailDto getInvoiceDetail(int invoiceId);

    void updateInvoiceStatus(int invoiceId, UpdateInvoiceStatusReq updateInvoiceStatusReq);
}
