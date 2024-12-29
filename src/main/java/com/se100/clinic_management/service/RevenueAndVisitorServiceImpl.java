package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iRevenueAndVisitorService;
import com.se100.clinic_management.model.Invoice;
import com.se100.clinic_management.repository.InvoiceRepository;
import com.se100.clinic_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RevenueAndVisitorServiceImpl implements iRevenueAndVisitorService {
  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  private PatientRepository patientRepository;

  @Override
  public Float calculateRevenue(LocalDate startDate, LocalDate endDate) {
    LocalDateTime startDateTime = startDate.atStartOfDay();
    LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

    // Lấy danh sách hóa đơn có trạng thái PAID trong khoảng thời gian
    List<Invoice> paidInvoices = invoiceRepository.findByStatusAndCreateAtBetween("PAID", startDateTime, endDateTime);

    // Tính tổng doanh thu
    return paidInvoices.stream()
        .map(Invoice::getTotal)
        .reduce(0f, Float::sum);
  }

  @Override
  public int countVisitors(LocalDate startDate, LocalDate endDate) {
    LocalDateTime startDateTime = startDate.atStartOfDay();
    LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

    // Lấy danh sách bệnh nhân được tạo trong khoảng thời gian
    return patientRepository.countByCreateAtBetween(startDateTime, endDateTime);
  }

}
