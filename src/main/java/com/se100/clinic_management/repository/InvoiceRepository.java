package com.se100.clinic_management.repository;

import com.se100.clinic_management.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO invoices (receptionist_id, total, service_record_id, status) " +
            "VALUES (:receptionistId, :total, :serviceRecordId, :status)", nativeQuery = true)
    void insertInvoice(Integer receptionistId, Float total, Integer serviceRecordId, String status);
}
