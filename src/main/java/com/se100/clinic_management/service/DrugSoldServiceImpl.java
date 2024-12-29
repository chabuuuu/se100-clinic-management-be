package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iDrugSoldService;
import com.se100.clinic_management.model.Prescription;
import com.se100.clinic_management.model.PrescriptionDetail;
import com.se100.clinic_management.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.se100.clinic_management.Interface.iDrugSoldService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DrugSoldServiceImpl implements iDrugSoldService {
  @Autowired
  private PrescriptionRepository prescriptionRepository;

  @Override
  public int getTotalDrugsSold(LocalDateTime startDate, LocalDateTime endDate) {
    // Lấy danh sách các đơn thuốc với trạng thái PAID trong khoảng thời gian
    List<Prescription> paidPrescriptions = prescriptionRepository.findByStatusAndCreateAtBetween(
        "PAID", startDate, endDate);

    // Tính tổng amount từ prescriptionDetails
    return paidPrescriptions.stream()
        .flatMap(prescription -> prescription.getPrescriptionDetails().stream())
        .mapToInt(PrescriptionDetail::getAmount)
        .sum();
  }
}
